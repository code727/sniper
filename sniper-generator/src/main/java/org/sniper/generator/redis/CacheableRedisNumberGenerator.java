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

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.generator.CacheableParameterizeGenerator;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * 可缓存的Redis数字生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class CacheableRedisNumberGenerator<K, V, P> extends RedisNumberGenerator<P>
		implements CacheableParameterizeGenerator<K, V, P, Long> {

	/** 缓存对象 */
	protected final Map<K, V> cache;
	
	/** 缓存大小 */
	protected int cacheSize = 10000;
	
	public CacheableRedisNumberGenerator(RedisCommandsDao redisCommandsDao) {
		this(null, redisCommandsDao);
	}
	
	public CacheableRedisNumberGenerator(String dbName, RedisCommandsDao redisCommandsDao) {
		super(dbName, redisCommandsDao);
		this.cache = MapUtils.newConcurrentHashMap();
	}
	
	@Override
	public Map<K, V> getCache() {
		return cache;
	}
	
	@Override		
	public int getCacheSize() {
		return cacheSize;
	}

	@Override
	public void setCacheSize(int cacheSize) {
		AssertUtils.assertTrue(cacheSize > 0, "Generator cache size must greater than 0");
		this.cacheSize = cacheSize;
	}
	
}
