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
 * Create Date : 2018-10-30
 */

package org.sniper.generator.keyspace;

import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.concurrent.locks.KeyspaceLock;

/**
 * 基于队列的缓存序列生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QueueCacheSequenceGenerator<V> extends AbstractCacheableGenerator<Object, Queue<V>, V> {
		
	/** 代理的键空间生成器接口 */
	protected final KeyspaceGenerator<Object, V> keyspaceGenerator;
	
	private final AbstractCacheableQueue cacheableQueue;
	
	public QueueCacheSequenceGenerator(KeyspaceGenerator<Object, V> keyspaceGenerator) {
		this(keyspaceGenerator, true);
	}
	
	public QueueCacheSequenceGenerator(KeyspaceGenerator<Object, V> keyspaceGenerator, boolean fixQueueCache) {
		this(null, keyspaceGenerator, fixQueueCache);
	}

	public QueueCacheSequenceGenerator(KeyspaceLock<Object> keyLock, KeyspaceGenerator<Object, V> keyspaceGenerator) {
		this(keyLock, keyspaceGenerator, true);
	}
	
	public QueueCacheSequenceGenerator(KeyspaceLock<Object> keyLock, KeyspaceGenerator<Object, V> keyspaceGenerator, boolean fixQueueCache) {
		super(keyLock, keyspaceGenerator.getDefaultSpaceId());
		this.keyspaceGenerator = keyspaceGenerator;
		this.cacheableQueue = (fixQueueCache ? new FixedCacheQueue() : new UnfixedCacheQueue());
	}
	
	@Override
	protected V doGenerateByKey(Object key) {
		Queue<V> queue = cache.get(key);
		if (queue == null) {
			keyLock.lock(key);
			try {
				if ((queue = cache.get(key)) == null) {
					return cacheableQueue.cacheAndPoll(key);
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			if (queue.isEmpty()) {
				return cacheableQueue.updateAndPoll(queue, key);
			}
			return queue.poll();
		} finally {
			keyLock.unlock(key);
		}
	}

	@Override
	protected List<V> doBatchGenerateByKey(Object key, int count) {
		Queue<V> queue = cache.get(key);
		if (queue == null) {
			keyLock.lock(key);
			try {
				if ((queue = cache.get(key)) == null) {
					return cacheableQueue.cacheAndBatchPoll(key, count);
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			int queueRemain = queue.size();
			if (queueRemain < count) {
				return cacheableQueue.updateAndBatchPoll(queue, key, queueRemain, count);
			}
			return cacheableQueue.batchPoll(queue, key, count);
		} finally {
			keyLock.unlock(key);
		}
	}
	
	/**
	 * 可缓存的队列抽象类，其作用在于将键空间生成器(KeyspaceGenerator)生成的结果缓存到队列中，
	 * 可以获取指定个数(count)的结果。现提供如下两种实现方式：</P>
	 * 1.利用KeyspaceGenerator批量生成cacheStepSize+count个元素，将第count个元素之前所有的元素作为出列结果，而将之后所有的元素全部缓存入列。</P>
	 * 2.利用KeyspaceGenerator批量生成cacheStepSize个元素，将第count(count <= cacheStepSize)个元素之前所有的元素作为出列结果，而将之后所有的元素全部缓存入列。</P>
	 * 
	 * 方式1(FixedCacheQueue)：本地队列中缓存的元素个数固定为cacheStepSize，生成器生成的元素个数不固定。例如：cacheStepSize=5，当某一次消费方需要生成10(count)个结果时，
	 * 如果队列剩余的个数(queueRemain)不足以满足要求，则生成器实际会先生成cacheStepSize+(count-queueRemain)个元素(queueRemain=0表示队列无任何剩余，此时生成15个元素)
	 * 然后将队列剩余的queueRemain(0)个元素和新生成的前count-queueRemain(10)个元素作为出列结果，最后将第count-queueRemain(5，正好等于cacheStepSize)个元素之后的所有元素全部缓存入列。</P>
	 * 优点：这种方式可以尽最大可能的让消费方从本地缓存队列里直接获取结果，减少生成器的生成频次，性能要优于方式2。</P>
	 * 缺点：由于生成器生成的个数会受消费方影响，因此在宕机和重启恢复的情况下，比方式2造成的丢失范围要大。另外也不方便根据生成器已生成的个数推算出缓存批次。</P>
	 * 
	 * 方式2(UnfixedCacheQueue)：本地队列中缓存的元素个数不固定，生成器生成的元素个数固定为n*cacheStepSize个，其中n=count/cacheStepSize+0/1。
	 * 例如：cacheStepSize=3，当某一次消费方需要生成5(count)个结果时，如果队列剩余的个数(queueRemain=2)不足以满足要求， 则生成器会先生成固定的cacheStepSize(3)个元素，
	 * 然后将队列剩余的queueRemain(2)个元素和新生成的前count-queueRemain(3)个元素作为出列结果，最后将第count-queueRemain(3)个元素之后的所有元素全部缓存入列。</P>
	 * 优点：由于生成器生成的个数不会受消费方影响，因此这种方式在宕机和重启恢复的情况下，比方式1造成的丢失范围要小。另外可以很方便的根据生成器已生成的个数和cacheStepSize推算出缓存批次。</P>
	 * 缺点：可能会使本地队列缓存失效， 例如：消费方如果每次要求生成的个数都大于cacheStepSize，则每次在返回出列结果之前，都会使生成器重新新生新元素并进行缓存入列，性能将急剧下降。
	 * 极端情况当cacheStepSize=1或cacheStepSize=count时，队列实际上是没有缓存任何元素的，这会导致缓存失去意义。</P>
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	protected abstract class AbstractCacheableQueue {
		
		protected final Logger logger;
		
		protected AbstractCacheableQueue() {
			this.logger = LoggerFactory.getLogger(getClass());
		}
		
		/**
		 * 缓存并出列一个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @return
		 */
		protected V cacheAndPoll(Object key) {
			Queue<V> queue = CollectionUtils.newConcurrentLinkedQueue();
			cache.put(key, queue);
			return updateAndPoll(queue, key);
		}
		
		/**
		 * 更新队列并出列一个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @return
		 */
		protected V updateAndPoll(Queue<V> queue, Object key) {
			List<V> list = keyspaceGenerator.batchGenerateByKey(key, calculateBatchCount());
			
			queue.addAll(list.subList(1, list.size()));
			V polled = list.get(0);
			logger.debug("Keyspace '{}' cache {} elements in queue and polled one element from list", key, queue.size());
			
			return polled;
		}
		
		/**
		 * 缓存并批量出列count个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @param count
		 * @return
		 */
		protected List<V> cacheAndBatchPoll(Object key, int count) {
			Queue<V> queue = CollectionUtils.newConcurrentLinkedQueue();
			cache.put(key, queue);
			return updateAndBatchPoll(queue, key, count);
		}
		
		/**
		 * 更新队列并批量出列count个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @param count
		 * @return
		 */
		protected List<V> updateAndBatchPoll(Queue<V> queue, Object key, int count) {
			List<V> list = keyspaceGenerator.batchGenerateByKey(key, calculateBatchCount(count));
			
			queue.addAll(list.subList(count, list.size()));
			List<V> polledList = list.subList(0, count);
			logger.debug("Keyspace '{}' cache {} elements in queue and polled {} element from list", 
					key, queue.size(), polledList.size());
			
			return polledList;
		}
		
		/**
		 * 更新队列并批量出列。当队列的剩余个数小于批量出列个数(queueRemain<count)时被调用，其实现方式如下：</P>
		 * 1.如果queueRemain==0，表明队列已经没有剩余元素了，缓存并出列count个结果；</P>
		 * 2.如果queueRemain!=0，表明队列还剩余有元素未出列，缓存并批量出列包括剩余的queueRemain个元素在内的count(总计)个结果。</P>
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @param queueRemain
		 * @param count
		 * @return
		 */
		protected List<V> updateAndBatchPoll(Queue<V> queue, Object key, int queueRemain, int count) {
			if (queueRemain == 0) {
				// 如果queueRemain==0，表明队列已经没有剩余元素了， 将调用重载的updateAndBatchPoll方法更新队列并出列count个结果
				return updateAndBatchPoll(queue, key, count);
			}
			
			// 1.计算出还需要补偿出列的个数(compensateCount)="指定生成的个数-队列剩余的个数"
			int compensateCount = count - queueRemain;
			
			/* 2.计算出批量生成的实际个数后利用代理的键空间生成器批量生成结果 */
			List<V> list = keyspaceGenerator.batchGenerateByKey(key, calculateBatchCount(compensateCount));
			logger.debug("Keyspace '{}' remain {} elements polling in queue, compensate {} elements polling, generated {} elements",
					key, queueRemain, compensateCount, list.size());
			
			// 3.以compensateCount为界，将第compensateCount个元素以后的所有元素全部存入缓存队列 
			queue.addAll(list.subList(compensateCount, list.size()));
			
			/* 4.从队列中出列前queueRemain个元素，即进行补偿生成前，缓存队列中剩余的元素。
			 * 注意：此步不要放在第2步批量生成结果之前进行。因为如果先进行剩余元素的出列操作，再进行批量生成操作时由于某种原因(例如：异常)导致失败，
			 * 由于基于队列的出列属于一次性的消费过程，这会导致已出列的剩余元素不能被调用方正常接收到，造成缓存丢失。*/
			List<V> polledList = batchPoll(queue, key, queueRemain);
			
			/* 5.再次以compensateCount为界，第compensateCount个元素之前的所有元素是当前批次需要补偿出列的，
			 * 因此进行出列操作，从而使最终获取到的结果为count个。在分布式环境中，polledList中呈现的出列元素可能不是连续的 */
			List<V> polledSubList = list.subList(0, compensateCount);
			polledList.addAll(polledSubList);
			
			logger.debug("Keyspace '{}' cache {} elements in queue and polled {} elements from {originalQueue:{},list:{}}", 
					key, queue.size(), polledList.size(), queueRemain, polledSubList.size());
					
			return polledList;
		}
		
		/**
		 * 从队列中批量出列count个结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param queue
		 * @param key
		 * @param count
		 * @return
		 */
		protected List<V> batchPoll(Queue<V> queue, Object key, int count) {
			List<V> list = CollectionUtils.newArrayList(count);
			for (int i = 0; i < count; i++) {
				list.add(queue.poll());
			}
			
			return list;
		}
		
		/**
		 * 计算出实际需要批量生成的个数
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @return
		 */
		protected abstract int calculateBatchCount();
		
		/**
		 * 根据指定的个数计算出生成器实际要批量生成的个数
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param count
		 * @return
		 */
		protected abstract int calculateBatchCount(int count);
	}
	
	/**
	 * 固定步长队列缓存轮询器实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	private class FixedCacheQueue extends AbstractCacheableQueue {
		
		@Override
		protected int calculateBatchCount() {
			return getCacheStepSize() + 1;
		}
		
		@Override
		protected int calculateBatchCount(int count) {
			return getCacheStepSize() + count;
		}
		
	}
	
	/**
	 * 无固定步长队列缓存轮询器实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	private class UnfixedCacheQueue extends AbstractCacheableQueue {
		
		@Override
		protected int calculateBatchCount() {
			return getCacheStepSize();
		}
		
		@Override
		protected int calculateBatchCount(int count) {
			int cacheStepSize = getCacheStepSize();
			int batchCount = (count / cacheStepSize) + (count % cacheStepSize == 0 ? 0 : 1);
			return cacheStepSize * batchCount;
		}

	}
	
}
