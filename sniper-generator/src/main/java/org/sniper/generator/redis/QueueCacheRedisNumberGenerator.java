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
 * Create Date : 2018-5-8
 */

package org.sniper.generator.redis;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * 基于队列缓存的Redis数字生成器实现类，实现原理如下：</P>
 * 以cacheSize为队尾(end)的种子元素，以此计算出队头(start)元素，每当调用一次生成方法时，首先会判断对应的队列里是否还有未分配出去的元素，
 * 若没有，则先在队列里缓存cacheSize中连续数字后在返回生成结果。</P>
 * 每次缓存的是[start+1, end+1]区间内连续cacheSize个数字，这样缓存的原因在于：</P>
 * 1.如果缓存的是[start, end]区间内的数字，当缓存分配结束后获取当前生成结果，则又要从刚分配好的缓存中取出队首元素，
 * 势必会多一次出队操作，而且实际上没有真正达到缓存cacheSize个元素的要求。</P>
 * 2.如果以[start+1, end+1]区间存储，则分配结束后获取到的生成结果则直接可通过"队首元素-1"来计算获取，因此cacheSize，start，end存在如下关系：</P>
 *  end = cacheSize * n(n表示同一个缓存的分配次数)</P>
 *  start = end - cacheSize + (end / cacheSize) + 1(end/cacheSize也表示同一个缓存的分配次数)</P>
 *  cacheSize=5时，举例如下：</P>
 *  1)第1次调用时，缓存的队列为空，则先在队列中缓存如下元素后再返回生成结果</P>
 *    [2,3,4,5,6] -> 返回结果为1，这是通过队首元素直接计算得到的</P>
 *  2)第2次调用时，缓存的队列不为空，则直接获取队首元素即可，此时队列为如下形式</P>
 *    [3,4,5,6]</P>
 *  3)第7次调用时，缓存已分配完，则又先在队列中缓存如下元素后再返回生成结果</P>
 *    [8,9,10,11]</P> -> 返回结果为7，这又是通过队首元素直接计算得到的</P>
 *  4)以此类推......</P>
 * 
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QueueCacheRedisNumberGenerator<K, P> extends CacheableRedisNumberGenerator<K, Queue<Long>, P> {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueCacheRedisNumberGenerator.class);
	
	protected static final Object lock = new Object();

	public QueueCacheRedisNumberGenerator(RedisCommandsDao redisCommandsDao) {
		super(redisCommandsDao);
	}

	public QueueCacheRedisNumberGenerator(String dbName, RedisCommandsDao redisCommandsDao) {
		super(dbName, redisCommandsDao);
	}

	@Override
	protected Long generateByParameter(P parameter) {
		Queue<Long> queue = cache.get(parameter);
		if (queue == null) {
			synchronized (lock) {
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((queue = cache.get(parameter)) == null) {
					queue = CollectionUtils.newConcurrentLinkedQueue();
					return cache(queue, parameter);
				}
			}
		} 
		
		if (queue.isEmpty())
			return cache(queue, parameter);
			
		return queue.poll();
	}
	
	/**
	 * 缓存[start+1,end+1]区间内连续cacheSize个数字后返回
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queue
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Long cache(Queue<Long> queue, P parameter) {
		long end = redisCommandsDao.incrBy(dbName, parameter, cacheSize);
		long start = end - cacheSize + (end / cacheSize) + 1;
		for (int i = 0; i < cacheSize; i++) {
			queue.add(start + i);
		}
		cache.put((K) parameter, queue);
		logger.debug("Parameter '{}' --- cache queue {}", parameter, queue);
		return start - 1;
	}
}
