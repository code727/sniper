/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Create Date : 2018-11-1
 */

package org.sniper.generator.keyspace;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.concurrent.locks.ParameterizeLock;
import org.sniper.generator.sequence.KeyspaceTrendSequence;
import org.sniper.support.counter.AtomicLongIntervalCounter;
import org.sniper.support.counter.IntervalCounter;

/**
 * 基于计数器的缓存序列生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CounterCacheSequenceGenerator extends AbstractCacheableGenerator<Object, IntervalCounter<Long>, Long> {
	
	/** 代理的键空间趋势序列接口 */
	protected final KeyspaceTrendSequence<Object, Long> keyspaceTrendSequence;
	
	private final AbstractCacheableCounter cacheableCounter;
	
	public CounterCacheSequenceGenerator(KeyspaceTrendSequence<Object, Long> keyspaceTrendSequence) {
		this(keyspaceTrendSequence, true);
	}
	
	public CounterCacheSequenceGenerator(KeyspaceTrendSequence<Object, Long> keyspaceTrendSequence, boolean fixCounterCache) {
		this(null, keyspaceTrendSequence, fixCounterCache);
	}

	public CounterCacheSequenceGenerator(ParameterizeLock<Object> keyLock, KeyspaceTrendSequence<Object, Long> keyspaceTrendSequence) {
		this(keyLock, keyspaceTrendSequence, true);
	}
	
	public CounterCacheSequenceGenerator(ParameterizeLock<Object> keyLock, KeyspaceTrendSequence<Object, Long> keyspaceTrendSequence, boolean fixCounterCache) {
		super(keyLock, keyspaceTrendSequence.getDefaultKeyspace());
		this.keyspaceTrendSequence = keyspaceTrendSequence;
		this.cacheableCounter = (fixCounterCache ? new FixedCacheCounter() : new UnfixedCacheCounter());
	}

	@Override
	protected Long doGenerateByKey(Object key) {
		IntervalCounter<Long> counter = cache.get(key);
		if (counter == null) {
			keyLock.lock(key);
			try {
				// 双重检查，防止多线程环境针对同一参数同时调用多次
				if ((counter = cache.get(key)) == null) {
					return cacheableCounter.cacheAndIncrement(key);
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			Long value = counter.increment();
			if (value == null || value > counter.getMaximum()) {
				return cacheableCounter.updateAndIncrement(counter, key);
			}
			return value;
		} finally {
			keyLock.unlock(key);
		}
	}

	@Override
	protected List<Long> doBatchGenerateByKey(Object key, int count) {
		IntervalCounter<Long> counter = cache.get(key);
		if (counter == null) {
			keyLock.lock(key);
			try {
				if ((counter = cache.get(key)) == null) {
					return cacheableCounter.cacheAndBatchIncrement(key, count);
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			int counterRemain = (int) (counter.getMaximum() - counter.get());
			if (counterRemain < count) {
				return cacheableCounter.updateAndBatchIncrement(counter, key, counterRemain, count);
			} 
			return cacheableCounter.batchIncrement(counter, count);
		} finally {
			keyLock.unlock(key);
		}
		
	}
	
	/**
	 * 可缓存的计数器抽象类，其作用在于将依赖的键空间趋势序列(keyspaceTrendSequence)创建的计数器缓存到队列中，
	 * 从而通过累加计数器的方式获取指定个数(count)的结果。现提供如下两种实现方式，主要时在创建或更新计数器时设置的步长会有所区间：</P>
	 * 
	 * 1.设置计数器的步长为cacheStepSize+count，其中count表示当前消费方要求返回的结果个数。表示在计数区间内可以进行同等数量次数的累加操作， 
	 * 当要返回count个结果累加count次后，缓存中的计数器恰好还可以进行cacheStepSize次累加；</P>
	 * 2.设置计数器的步长固定为cacheStepSize。</P>
	 * 
	 * 方式1(FixedCacheCounter)：本地队列中缓存的计数器剩余可累加的次数固定为cacheStepSize，趋势序列生成的种子计数将无规律的增长。</P>
	 * 例如：cacheStepSize=5，当消费方需要生成10(count)个结果并且触发了计数器创建或更新时，首先利用cacheStepSize和count，
	 * 计算出计数器的实际步长stepSize=cacheStepSize+count，同时利用stepSize值生成种子计数seed=oldSeed+stepSize，
	 * 最后利用种子计数和实际步长计算出当前计数器的起始值start=seed-stepSize，因此当消费方要求返回count个结果时，
	 * 计数器从起始值累加count次即可，而此时计数器在缓存中剩余可累加的次数恰好为cacheStepSize</P>
	 * 优点：这种方式可以尽最大可能的让消费方从本地缓存队列里直接获取结果，减少计数器更新初始值和步长的频次，性能要优于方式2。</P>
	 * 缺点：由于趋势序列生成的种子会受消费方影响，因此在宕机和重启恢复的情况下，比方式2造成的丢失范围要大。另外也不方便根据趋势序列生成的种子计数推算出缓存批次。</P>
	 * 
	 * 方式2(UnfixedCacheCounter)：本地队列中缓存的计数器剩余可累加的次数不固定， 趋势序列生成的种子计数将按cacheStepSize的倍数规律的增长(n*cacheStepSize，其中n=count/cacheStepSize+0/1)</P>
	 * 优点：由于趋势序列生成的种子计数不会受消费方影响，因此这种方式在宕机和重启恢复的情况下，比方式1造成的丢失范围要小。另外可以很方便的根据趋势序列生成的种子计数和cacheStepSize推算出缓存批次。</P>
	 * 缺点：可能会使本地缓存的计数器失效， 例如：消费方如果每次要求生成的个数都大于cacheStepSize，则每次在返回结果之前，都会使计数器更新区间初始值和步长，性能将急剧下降。
	 * 极端情况当cacheStepSize=1或cacheStepSize=count时，缓存的计数器实际上并不能进行累加操作，这会导致缓存失去意义。</P>
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	protected abstract class AbstractCacheableCounter {
		
		protected final Logger logger;
		
		protected AbstractCacheableCounter() {
			this.logger = LoggerFactory.getLogger(getClass());
		}
		
		/**
		 * 缓存并累加出一个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param key
		 * @return
		 */
		protected Long cacheAndIncrement(Object key) {
			IntervalCounter<Long> counter = createCounter(key);
			cache.put(key, counter);
			
			Long value = counter.increment();
			logger.debug("Keyspace '{}' cache {} elements in counter and get one element", 
					key, counter.incrementSize());
			
			return value;
		}
		
		/**
		 * 更新计数器并累加出一个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param counter
		 * @param key
		 * @return
		 */
		protected Long updateAndIncrement(IntervalCounter<Long> counter, Object key) {
			counter.setStart(calculateStartValue(key));
			counter.setStepSize(calculateStepSize());
			
			Long value = counter.increment();
			logger.debug("Keyspace '{}' cache {} elements in counter and get one element", 
					key, counter.incrementSize());
					
			return value;
		}
		
		/**
		 * 缓存计数器并批量累加出count个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param key
		 * @param count
		 * @return
		 */
		protected List<Long> cacheAndBatchIncrement(Object key, int count) {
			IntervalCounter<Long> counter = createCounter(key, count);
			cache.put(key, counter);
			
			List<Long> list = batchIncrement(counter, count);
			logger.debug("Keyspace '{}' cache {} elements in counter and get {} element", 
					key, counter.incrementSize(), list.size());
			
			return list;
		}
		
		/**
		 * 更新计数器并批量累加出结果。当计数器的剩余个数小于批量个数(counterRemain<count)时被调用，其实现方式如下：</P>
		 * 1.如果counterRemain==0，表明计数器已经没有剩余元素了，更新计数器后累加出count个结果；</P>
		 * 2.如果counterRemain!=0，表明计数器还剩余有元素未出列，累加出包括剩余的counterRemain个元素在内的count(总计)个结果。</P>
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param counter
		 * @param key
		 * @param counterRemain
		 * @param count
		 * @return
		 */
		protected List<Long> updateAndBatchIncrement(IntervalCounter<Long> counter, Object key, int counterRemain, int count) {
			if (counterRemain == 0) {
				counter.setStart(calculateStartValue(key, count));
				counter.setStepSize(calculateStepSize(count));
				
				List<Long> list = batchIncrement(counter, count);
				logger.debug("Keyspace '{}' cache {} elements in counter and get {} element", 
						key, counter.incrementSize(), list.size());
				
				return list;
			} 
			
			// 1.计算出还需要补偿获取的个数(compensateCount)="指定生成的个数-计数器剩余的个数"
			int compensateCount = count - counterRemain;
			
			/* 2.计算出用于更新计数器的初始值和步长 */
			long start = calculateStartValue(key, compensateCount);
			int stepSize = calculateStepSize(compensateCount);
			logger.debug("Keyspace '{}' remain {} elements incrementing in counter, compensate {} elements incrementing, generated {} elements",
					key, counterRemain, compensateCount, stepSize);
			
			/* 3.获取计数器在更新之前剩余的所有元素 
			 * 注意：此步不要放在第2步计算之前计数器的初始值和步长进行，因为如果先进行剩余元素的获取操作，再进行计算时由于某种原因(例如：异常)导致失败，
			 * 由于基于计数器的获取属于单向递增的一次性消费过程，这会导致已获取的剩余元素不能被调用方正常接收到，造成缓存丢失。*/
			List<Long> list = batchIncrement(counter, counterRemain);
			
			/* 4.更新计数器后，再累加并获取前compensateCount个元素，从而使最终获取到的结果为count个。
			 * 在分布式环境中，list中呈现的出列元素可能不是连续的 */
			counter.setStart(start);
			counter.setStepSize(stepSize);
			list.addAll(batchIncrement(counter, compensateCount));
			
			logger.debug("Keyspace '{}' cache {} elements in counter and get {} elements {remaining:{},compensated:{}}", 
					key, counter.incrementSize(), list.size(), counterRemain, compensateCount);
			
			return list;
		}
		
		/**
		 * 从计数器中出列count个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param counter
		 * @param count
		 * @return
		 */
		protected List<Long> batchIncrement(IntervalCounter<Long> counter, int count) {
			List<Long> list = CollectionUtils.newArrayList(count);
			for (int i = 0; i < count; i++) {
				list.add(counter.increment());
			}
			
			return list;
		}
		
		/**
		 * 根据键创建计数器
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param key
		 * @return
		 */
		protected IntervalCounter<Long> createCounter(Object key) {
			long start = calculateStartValue(key);
			return new AtomicLongIntervalCounter(start, calculateStepSize());
		}
		
		/**
		 * 根据键和生成个数创建计数器
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param key
		 * @param count
		 * @return
		 */
		protected IntervalCounter<Long> createCounter(Object key, int count) {
			long start = calculateStartValue(key, count);
			return new AtomicLongIntervalCounter(start, calculateStepSize(count));
		}
		
		/**
		 * 根据键计算出计数器的起始值
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param key
		 * @return
		 */
		protected long calculateStartValue(Object key) {
			int stepSize = calculateStepSize();
			long seed = keyspaceTrendSequence.updateByKey(key, stepSize);
			return seed - stepSize;
		}
		
		/**
		 * 根据键和生成的个数，计算出计数器的起始值
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param key
		 * @param count
		 * @return
		 */
		protected long calculateStartValue(Object key, int count) {
			int stepSize = calculateStepSize(count);
			long seed = keyspaceTrendSequence.updateByKey(key, stepSize);
			return seed - stepSize;
		}
				
		/**
		 * 计算出计数器的步长
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @return
		 */
		protected abstract int calculateStepSize();
		
		/**
		 * 根据生成个数计算出计数器的步长
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param count
		 * @return
		 */
		protected abstract int calculateStepSize(int count);
		
	}
	
	private class FixedCacheCounter extends AbstractCacheableCounter {
		
		@Override
		protected int calculateStepSize() {
			return getCacheStepSize() + 1;
		}
		
		@Override
		protected int calculateStepSize(int count) {
			return getCacheStepSize() + count;
		}
		
	}
	
	private class UnfixedCacheCounter extends AbstractCacheableCounter {

		@Override
		protected int calculateStepSize() {
			return getCacheStepSize();
		}

		@Override
		protected int calculateStepSize(int count) {
			int cacheStepSize = getCacheStepSize();
			int batchCount = (count / cacheStepSize) + (count % cacheStepSize == 0 ? 0 : 1);
			return cacheStepSize * batchCount;
		}
		
	}
	
}
