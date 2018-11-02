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
import org.sniper.lock.ParameterizeLock;

/**
 * 基于队列的缓存序列生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QueueCacheSequenceGenerator<V> extends AbstractCacheableGenerator<Object, Queue<V>, V> {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueCacheSequenceGenerator.class);
	
	/** 代理的键空间生成器接口 */
	protected final KeyspaceGenerator<Object, V> keyspaceGenerator;
	
	public QueueCacheSequenceGenerator(KeyspaceGenerator<Object, V> keyspaceGenerator) {
		this(null, keyspaceGenerator);
	}

	public QueueCacheSequenceGenerator(ParameterizeLock<Object> keyLock, KeyspaceGenerator<Object, V> keyspaceGenerator) {
		super(keyLock, keyspaceGenerator.getDefaultKeyspace());
		this.keyspaceGenerator = keyspaceGenerator;
	}
	
	@Override
	protected V doGenerateByKey(Object key) {
		Queue<V> queue = cache.get(key);
		if (queue == null) {
			keyLock.lock(key);
			try {
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((queue = cache.get(key)) == null) {
					queue = CollectionUtils.newConcurrentLinkedQueue();
					/* 队列创建成功后立即进行缓存并返回生成结果，不延迟到下一个keyLock块中进行，
					 * 目的是减少一次加解锁过程提高性能 */
					return cacheAndPoll(queue, key);
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		/* 由于isEmpty和cache方法组合在一起是非原子性的，
		 * 因此存在多线程"先检查后执行"问题，需加锁操作 */
		keyLock.lock(key);
		try {
			if (queue.isEmpty()) {
				return cacheAndPoll(queue, key);
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
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((queue = cache.get(key)) == null) {
					queue = CollectionUtils.newConcurrentLinkedQueue();
					return cacheAndBatchPoll(queue, key, count);
				}
			} finally {
				keyLock.unlock(key);
			}
		}
		
		keyLock.lock(key);
		try {
			int queueRemain = queue.size();
			if (queueRemain < count) {
				return cacheAndBatchPoll(queue, key, queueRemain, count );
			}
			return batchPoll(queue, key, count);
		} finally {
			keyLock.unlock(key);
		}
	}
	
	/**
	 * 批量出列count个结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queue
	 * @param count
	 * @return
	 */
	private List<V> batchPoll(Queue<V> queue, Object key, int count) {
		List<V> list = CollectionUtils.newArrayList(count);
		for (int i = 0; i < count; i++) {
			list.add(queue.poll());
		}
		
		logger.debug("Keyspace '{}' batch polled {} elements from queue:{}", key, list.size(), list);
		return list;
	}
	
	/**
	 * 缓存并出列一个结果，大概有如下四种实现方式：</P>
	 * 1.利用键空间生成器批量生成cacheStepSize个元素，此后将这些元素先全部入列后再出列一个结果；</P>
	 * 2.利用键空间生成器批量生成cacheStepSize+1个元素，此后将这些元素先全部入列后再出列一个结果；</P>
	 * 3.利用键空间生成器批量生成cacheStepSize+1个元素并赋值给list，此后将list中除第一个元素之外的所有元素全部入列，
	 * 而最后的出列结果是直接从list中获取第一个元素所得；</P>
	 * 4.利用键空间生成器批量生成cacheStepSize个元素并赋值给list，此后将list中除第一个元素之外的所有元素全部入列，
	 * 而最后的出列结果是直接从list中获取第一个元素所得。</P>
	 * 对比如下四种实现方式：</P>
	 * 方式1：队列中实际上只缓存了cacheStepSize-1个元素，极端情况下，当cacheStepSize==1时，实际上队列是没有缓存任何元素的，导致缓存失去意义；</P>
	 * 方式2：队列中缓存了cacheStepSize个元素，满足需求，但当前出列元素会多进行一次入列的过程；</P>
	 * 方式3：队列中缓存了cacheStepSize个元素，满足需求，同时避免了当前出列元素多进行一次入列的过程；</P>
	 * 方式4：队列中实际上只缓存了cacheStepSize-1个元素，满足需求，虽然避免了当前出列元素会多一次入列的多余过程，但所存在的问题与方式1一样。</P>
	 * 备选将在第2和第3种方式之间选择，综合考虑，最终选择第3种实现方式。</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queue
	 * @param key
	 * @return
	 */
	private V cacheAndPoll(Queue<V> queue, Object key) {
		cache.put(key, queue);
		
		int batchCount = getCacheStepSize() + 1;
		List<V> list = this.keyspaceGenerator.batchGenerateByKey(key, batchCount);
		
		queue.addAll(list.subList(1, list.size()));
		logger.debug("Keyspace '{}' cache {} elements in queue", key, queue.size());
		
		V polled = list.get(0);
		logger.debug("Keyspace '{}' polled a element from list:[{}]", key, polled);
		
		return polled;
	}
	
	/**
	 * 缓存并出列count个结果，其实现方式与cacheAndPoll方法类似：</P>
	 * 利用键空间生成器批量生成cacheStepSize+count个元素并赋值给list，此后将list中从第count个元素以后的所有元素全部入列，
	 * 而最后的出列结果是直接从list中获取从第count个元素以前的所有元素所得；</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queue
	 * @param key
	 * @param count
	 * @return
	 */
	private List<V> cacheAndBatchPoll(Queue<V> queue, Object key, int count) {
		cache.put(key, queue);
		
		int batchCount = getCacheStepSize() + count;
		List<V> list = this.keyspaceGenerator.batchGenerateByKey(key, batchCount);
		
		queue.addAll(list.subList(count, list.size()));
		logger.debug("Keyspace '{}' cache {} elements in queue", key, queue.size());
		
		List<V> polled = list.subList(0, count);
		logger.debug("Keyspace '{}' batch polled {} elements from list:{}", key, polled.size(), polled);
		
		return polled;
	}
	
	/**
	 * 缓存并批量出列。当队列的剩余个数小于批量出列个数(queueRemain<count)时被调用，其实现方式如下：</P>
	 * 1.如果queueRemain==0，表明队列已经没有剩余元素了，缓存并出列count个结果；</P>
	 * 2.如果queueRemain!=0，表明队列还剩余有元素未出列，先批量出列这些元素后再缓存并出列count-queueRemain个结果。</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queue
	 * @param key
	 * @param queueRemain
	 * @param count
	 * @return
	 */
	private List<V> cacheAndBatchPoll(Queue<V> queue, Object key, int queueRemain, int count) {
		if (queueRemain == 0) {
			/* 如果queueRemain==0，表明队列已经没有剩余元素了，
			 * 将调用重载的cacheAndBatchPoll方法缓存并出列count个结果。 */
			logger.debug("Keyspace '{}' nothing remaining element in queue, will cache and batch poll {} elements", key, count);
			return cacheAndBatchPoll(queue, key, count);
		}
		
		cache.put(key, queue);
		
		// 1.计算出还需要补偿出列的个数(compensateCount)="指定生成的个数-队列剩余的个数"
		int compensateCount = count - queueRemain;
		
		/* 2.计算出批量生成的实际个数="缓存步长+补偿出列的个数"后利用代理的键空间生成器批量生成结果，
		 * 其目的是为了保证当前批次出列后，缓存队列中仍然还缓存有cacheStepSize个元素 */
		int batchCount = getCacheStepSize() + compensateCount;
		List<V> list = this.keyspaceGenerator.batchGenerateByKey(key, batchCount);
		logger.debug("Keyspace '{}' remaining {} elements in queue, compensate generate {} elements", queueRemain, compensateCount);
		
		/* 3.以compensateCount为界，将第compensateCount个元素以后的所有元素全部存入缓存队列 */
		List<V> queueSubList = list.subList(compensateCount, list.size());
		queue.addAll(queueSubList);
		logger.debug("Keyspace '{}' cache {} elements in queue", key, queueSubList.size());
		
		/* 4.从队列中出列前queueRemain个元素，即进行补偿生成前，缓存队列中剩余的元素。
		 * 注意：此步不要放在第2步批量生成结果之前进行，因为如果先进行了剩余元素的出列操作，而后再进行批量生成操作时由于某种原因(例如：异常)导致失败时，
		 * 由于基于队列的出列属于一次性的消费过程，这会导致已出列的剩余元素不能被调用方正常接收到，造成缓存丢失。*/
		List<V> polled = batchPoll(queue, key, queueRemain);
		
		/* 5.再次以compensateCount为界，第compensateCount个元素之前的所有元素是当前批次需要补偿出列的，因此进行出列操作 */
		polled.addAll(list.subList(0, compensateCount));
		logger.debug("Keyspace '{}' batch polled {} elements from queue and list:{}", key, polled.size(), polled);
		
		return polled;
	}
	
}
