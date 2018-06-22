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
 * Create Date : 2018-5-14
 */

package org.sniper.generator.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.counter.AtomicLongIntervalCounter;
import org.sniper.commons.counter.IntervalCounter;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * 基于计数器缓存的Redis数字生成器实现类，实现原理如下：</P>
 * 以cacheSize的累加结果作为Redis种子计数(redisSeed)，并以此计算出计数器的起始值(start)，
 * 关系为start = redisSeed - cacheSize</P>
 * 而计数器的步长为cacheSize，即允许计数器在[start,start+cacheSize]区间内做有效的累加操作。</P>
 * 每次在累加前，首先判断当前计数是否大于等于有效区间的最大值，如果条件成立，则先要调整计数器的起始值后再做累加，过程如下：</P>
 * 1.再次以cacheSize的累加结果重新生成Redis种子计数;</P>
 * 2.再次以"start = redisSeed - cacheSize"的公式计算出新起始值后更新计数器。</P>
 * 此实现类生成的结果具备如下特点：</P>
 * 1)生成的数字是全局唯一的；</P>
 * 2)在单节点单线程无中断环境中生成的数字是连续递增的；</P>
 * 3)在其余环境(单节点多线程/多节点单线程/多节点多线程)中生成的数字不能保证是连续的，也不能保证是趋势递增的；</P>
 * 4)由于此实现是基于计数器起始值和步长参数计算出来的，因此当服务重启再恢复后，新旧数字之间可能存在不连续的情况，断续长度取决于计数器的步长设置；
 * 此实现类的关键性能参数是cacheSize，它决定了计数器在每一个计数区间的步长，从而影响更新频率：</P>
 * 1)cacheSize越大，并且当服务重启再恢复后，断续长度可能会越大，从而造成浪费；</P>
 * 2)cacheSize越小，当面对高频次的生成调用时，计数器更新(更新起始值、当前计数区间内的最大值和最小值)频率也就越大</P>
 * 因此需根据实际场景合理设置cacheSize的大小。</P>
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CounterCacheRedisNumberGenerator<K, P> extends CacheableRedisNumberGenerator<K, IntervalCounter<Long>, P> {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueCacheRedisNumberGenerator.class);
	
	protected static final Object lock;
	
	static {
		lock = new Object();
	}

	public CounterCacheRedisNumberGenerator(RedisCommandsDao redisCommandsDao) {
		super(redisCommandsDao);
	}
	
	public CounterCacheRedisNumberGenerator(String dbName, RedisCommandsDao redisCommandsDao) {
		super(dbName, redisCommandsDao);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Long generateByParameter(P parameter) {
		IntervalCounter<Long> counter = cache.get(parameter);
		if (counter == null) {
			synchronized (lock) {
				if ((counter = cache.get(parameter)) == null) {
					counter = new AtomicLongIntervalCounter(calculateStartValueByParameter(parameter), (long) cacheSize);
					cache.put((K) parameter, counter);
					logger.debug("Initialize counter start value '{}' for  parameter '{}' ", counter.getStart(), parameter);
				}
			}
		}
		
		Long value = counter.increment();
		if (value > counter.getMaximum()) {
			synchronized (lock) {
				if (value > counter.getMaximum()) {
					long start = calculateStartValueByParameter(parameter);
					counter = new AtomicLongIntervalCounter(start, (long) cacheSize);
					value = counter.increment();
				}
			}
		}		
		
//		if (value == null) {
//			synchronized (lock) {
//				if ((value = counter.increment()) == null) {
//					long start = calculateStartValueByParameter(parameter);
//					counter = new AtomicLongIntervalCounter(start, (long) cacheSize);
//					value = counter.increment();
//				}
//			}
//		}
	    return value;
	}
	
	/**
	 * 根据参数计算出计数器的起始值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @returnp
	 */
	protected long calculateStartValueByParameter(P parameter) {
		/* 同QueueCacheRedisNumberGenerator实现类一样，不要以redis的自然累加(incr指令)结果作为种子来计算起始元素，
		 * 因为cacheSize是可变的，如果以这种方式来计算起始元素，则当cacheSize由大变小时，起始值(start)也会由大变小，
		 * 相当于出现了计数回溯的情况，从而前后两次生成的结果会有重复，并且也不能满足趋势递增性*/
		long redisSeed = redisCommandsDao.incrBy(dbName, parameter, cacheSize);
		return redisSeed - cacheSize;
	}

}
