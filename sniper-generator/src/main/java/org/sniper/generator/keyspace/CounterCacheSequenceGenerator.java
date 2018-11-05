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

import org.sniper.commons.util.CollectionUtils;
import org.sniper.generator.sequence.KeyspaceTrendSequence;
import org.sniper.lock.ParameterizeLock;
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
	
	public CounterCacheSequenceGenerator(KeyspaceTrendSequence<Object, Long> keyspaceTrendSequence) {
		this(null, keyspaceTrendSequence);
	}

	public CounterCacheSequenceGenerator(ParameterizeLock<Object> keyLock, KeyspaceTrendSequence<Object, Long> keyspaceTrendSequence) {
		super(keyLock, keyspaceTrendSequence.getDefaultKeyspace());
		this.keyspaceTrendSequence = keyspaceTrendSequence;
	}

	@Override
	protected Long doGenerateByKey(Object key) {
		IntervalCounter<Long> counter = cache.get(key);
		if (counter == null) {
			keyLock.lock(key);
			try {
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((counter = cache.get(key)) == null) {
					counter = createCounter(key, 1);
					cache.put(key, counter);
					return counter.getStart();
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			Long value = counter.increment();
			if (value == null || value > counter.getMaximum()) {
				long start = updateCounterStartValue(key, 1);
				counter.setStart(start);
				return start;
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
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((counter = cache.get(key)) == null) {
					counter = createCounter(key, count);
					cache.put(key, counter);
					
					long batchStartValue = counter.getStart() - count;
					List<Long> list = CollectionUtils.newArrayList(count);
					for (int i = 0; i < count; i++) {
						list.add(++batchStartValue);
					}
					return list;
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			long countRemain = counter.getMaximum() - counter.get();
			if (countRemain == 0) {
				long start = updateCounterStartValue(key, count);
				counter.setStart(start);
				
				long batchStartValue = start - count;
				List<Long> list = CollectionUtils.newArrayList(count);
				for (int i = 0; i < count; i++) {
					list.add(++batchStartValue);
				}
				
				return list;
			}
			
			if (countRemain < count) {
				int compensateCount = (int) (count - countRemain);
				long start = updateCounterStartValue(key, compensateCount);
				
				List<Long> list = CollectionUtils.newArrayList(count);
				for (int i = 0; i < countRemain; i++) {
					list.add(counter.increment());
				}
				
				counter.setStart(start);
				long batchStartValue = start - count + 1;
				for (int i = 0; i < compensateCount; i++) {
					list.add(++batchStartValue);
				}
				
				return list;
				
			} else {
				List<Long> list = CollectionUtils.newArrayList(count);
				for (int i = 0; i < count; i++) {
					list.add(counter.increment());
				}
				return list;
			}
		} finally {
			keyLock.unlock(key);
		}
		
	}
	
	/**
	 * 根据键和要生成的个数，更新计数器的起始值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param count
	 * @return
	 */
	private long updateCounterStartValue(Object key, int count) {
		int cacheStepSize = getCacheStepSize();
		long seed = keyspaceTrendSequence.updateByKey(key, cacheStepSize + count);
		return seed - cacheStepSize;
	}
	
	private IntervalCounter<Long> createCounter(Object key, int count) {
		long start = updateCounterStartValue(key, count);
		return new AtomicLongIntervalCounter(start, getCacheStepSize());
	}
	
}
