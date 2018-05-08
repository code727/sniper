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
 * Create Date : 2018-5-4
 */

package org.sniper.generator.redis;

import java.util.Map;
import java.util.Queue;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * 
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CachedRedisSerialNumberGenerator extends RedisSerialNumberGenerator {

	private final Map<String, Queue<Long>> cache = MapUtils.newConcurrentHashMap();

	private int cacheSize = 10000;
	
	public CachedRedisSerialNumberGenerator(RedisCommandsDao redisCommandsDao) {
		super(redisCommandsDao);
	}

	public CachedRedisSerialNumberGenerator(String dbName, RedisCommandsDao redisCommandsDao) {
		super(dbName, redisCommandsDao);
	}
	
	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		AssertUtils.assertTrue(cacheSize > 0, "Generator cache size must greater than 0");
		this.cacheSize = cacheSize;
	}

	@Override
	protected Long generateByKey(String key) {
		Queue<Long> queue = cache.get(key);
		
		if (queue == null) 
			queue = CollectionUtils.newConcurrentLinkedQueue();

		/* 缓存[start,end]区间内连续cacheSize个数字，此算法的不足之处在于：
		 * 虽然缓存的是cacheSize个数字,但start元素可以不用缓存，因为在每一次queue分配时，start的入队操作，后续必然会带来一次额外的出队操作，
		 * 增加系统开销的同时，从调用方的角度来看，其实只缓存了cacheSize-1个数字;
		 * 算法的整改方案如下之一：
		 * 1）缓存[start,end+1]区间内连续cacheSize+1个数字；
		 * 2）缓存[start+1,end+1]区间内连续cacheSize个数字，在queue分配结束时直接返回start元素； */
		if (queue.isEmpty()) {
			long end = redisCommandsDao.incrBy(dbName, key, cacheSize);
			long start = end - cacheSize + 1;

			for (int i = 0; i < cacheSize + 1; i++) {
				queue.add(start + i);
			}
						
			cache.put(key, queue);
		}
				
		return queue.poll();
	}

	
}
