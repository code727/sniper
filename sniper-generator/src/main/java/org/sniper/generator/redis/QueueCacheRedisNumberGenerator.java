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
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.lock.ParameterizeLock;
import org.sniper.lock.jdk.JdkParameterizeLock;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * 基于队列缓存的Redis数字生成器实现类，实现原理如下：</P>
 * 以cacheSize的累加结果(redisSeed)作为Redis种子计数(redisSeed)来当成队尾元素(end)来存储，并以此计算出队头元素(start)，每当调用一次生成方法时，首先会判断对应的队列里是否还有未分配出去的元素，
 * 若没有，则先在队列里缓存[start+1, end+1]区间内连续cacheSize个数字后再返回生成结果。这样缓存的原因在于：</P>
 * 1.如果缓存的是[start, end]区间内的数字，当缓存分配结束后，则又要从刚分配好的缓存中取出队首元素，
 * 势必会多一次出队操作，而且实际上没有真正达到缓存cacheSize个元素的要求。</P>
 * 2.如果以[start+1, end+1]区间存储，则分配结束后获取到的生成结果则直接可通过"队首元素-1"来计算获取，因此cacheSize，start，end存在如下关系：</P>
 *  end = cacheSize * n(n表示同一个缓存的分配次数)</P>
 *  start = end - cacheSize + (end / cacheSize) + 1(其中end/cacheSize也表示同一个缓存的分配次数)</P>
 *  例如cacheSize=5时：</P>
 *  1)第1次调用时，缓存的队列为空，则先在队列中缓存如下元素后再返回生成结果</P>
 *    [2,3,4,5,6] -> 返回结果为1，这是通过队首元素直接计算得到的</P>
 *  2)第2次调用时，缓存的队列不为空，则直接获取队首元素即可，此时队列为如下形式</P>
 *    [3,4,5,6]</P>
 *  3)第7次调用时，缓存已分配完，则又先在同一队列中缓存如下元素后再返回生成结果</P>
 *    [8,9,10,11,12]</P> -> 返回结果为7，这又是通过队首元素直接计算得到的</P>
 *  4)以此类推......</P>
 *  此实现类生成的结果具备如下特点：</P>
 *  1)生成的数字是全局唯一的；</P>
 *  2)在单节点单线程无中断环境中生成的数字是连续递增的，当有中断时，也能保证是趋势递增的；</P>
 *  3)在单节点多线程环境中，由于共用的是同一个队列，因此当线程无中断交替执行时，能保证生成的数字是连续递增的，
 *    当有中断时，也能保证是趋势递增的，即后执行的线程生成的数字一定比先执行的线程生成的数字大</P>
 *  4)在其余环境(多节点单线程/多节点多线程)中生成的数字不能保证是连续的，也不能保证是趋势递增的；</P>
 *  5)由于此实现是基于JVM内存分配的，因此当服务重启再恢复后，新旧数字之间可能存在不连续的情况，断续长度取决于队列的缓存大小</P>
 *  此实现类的关键性能参数是cacheSize，它决定了每一次缓存数字时分配的队列大小：</P>
 *  1)cacheSize过大，虽然减少了缓存分配的频次，但每一次的分配时间（锁占用时间）也越长，
 *    还有可能超出JVM存储极限造成OOM，并且当服务重启再恢复后，造成不连续的断点区间可能也越大；</P>
 *  2)cacheSize过小，当面对高频次的生成调用时，缓存分配的频次以及锁的占用/释放频次也会提高。</P>
 *  因此需根据实际场景合理设置cacheSize的大小。</P>
 * 
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QueueCacheRedisNumberGenerator<K, P> extends CacheableRedisNumberGenerator<K, Queue<Long>, P> {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueCacheRedisNumberGenerator.class);
	
	protected final ParameterizeLock<P> lock;

	public QueueCacheRedisNumberGenerator(RedisCommandsDao redisCommandsDao) {
		this(null, redisCommandsDao);
	}
	
	public QueueCacheRedisNumberGenerator(RedisCommandsDao redisCommandsDao, ParameterizeLock<P> lock) {
		this(null, redisCommandsDao, lock);
	}

	public QueueCacheRedisNumberGenerator(String dbName, RedisCommandsDao redisCommandsDao) {
		this(dbName, redisCommandsDao, new JdkParameterizeLock<P>());
	}
	
	public QueueCacheRedisNumberGenerator(String dbName, RedisCommandsDao redisCommandsDao, ParameterizeLock<P> lock) {
		super(dbName, redisCommandsDao);
		AssertUtils.assertNotNull(lock, "Parameterize lock must not be null");
		this.lock = lock;
	}

	@Override
	protected Long generateByParameter(P parameter) {
		Queue<Long> queue = cache.get(parameter);
		if (queue == null) {
			lock.lock(parameter);
			try {
				// 双重检查，防止多线程环境针对同一参数同时创建多个队列
				if ((queue = cache.get(parameter)) == null) {
					queue = CollectionUtils.newConcurrentLinkedQueue();
					return cache(queue, parameter);
				}
			} finally {
				lock.unlock(parameter);
			}
		} 
		
		/* 由于isEmpty和cache方法组合在一起是非原子性的，
		 * 因此存在多线程"先检查后执行"问题，需加锁操作 */
		lock.lock(parameter);
		try {
			if (queue.isEmpty()) {
				return cache(queue, parameter);
			}
			return queue.poll();
		} finally {
			lock.unlock(parameter);
		}
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
		/* 不要以redis的自然累加(incr指令)结果作为种子来计算起始元素，
		 * 因为cacheSize是可变的，如果以这种方式来计算起始元素，则当cacheSize由大变小时，起始值(start)也会由大变小，
		 * 相当于出现了计数回溯的情况，从而导致前后两次生成的结果会有重复，并且也不能满足趋势递增性*/
//		long redisSeed = redisCommandsDao.incr(dbName, parameter);
//		long start = (redisSeed - 1) * cacheSize + ((redisSeed * cacheSize) / cacheSize) + 1;
		
		// 以cacheSize的累加结果作为计算种子
		long redisSeed = redisCommandsDao.incrBy(dbName, parameter, cacheSize);
		long start = redisSeed - cacheSize + (redisSeed / cacheSize) + 1;
		
		for (int i = 0; i < cacheSize; i++) {
			queue.add(start + i);
		}
		
		cache.put((K) parameter, queue);
		logger.debug("Parameter '{}' --- cache queue {}", parameter, queue);
		return start - 1;
	}
}
