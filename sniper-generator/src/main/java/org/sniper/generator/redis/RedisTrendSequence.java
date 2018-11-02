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
 * Create Date : 2018-11-2
 */

package org.sniper.generator.redis;

import org.sniper.commons.util.AssertUtils;
import org.sniper.generator.sequence.AbstractKeyspaceTrendSequence;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * 基于Redis实现的趋势序列，生成的结果具备如下特点：</P>
 * 1.同一个键维度内生成的数字在单节点和分布式环境中都是唯一的；</P>
 * 2.同一个键维度内生成的数字在单节点和分布式环境中的连续性取决于设置的步长(stepSize)大小：</P>
 *   1)stepSize=1，则生成的数字是连续递增的；
 *   2)stepSize>1，则生成的数字是趋势递增的；
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisTrendSequence extends AbstractKeyspaceTrendSequence<Object, Long> {
	
	public static final String DEFAULT_KEYSPACE = "redis_sequence_key";
	
	/** 生成序列的库名 */
	protected final String dbName;
	
	protected final RedisCommandsDao redisCommandsDao;
	
	public RedisTrendSequence(RedisCommandsDao redisCommandsDao) {
		this((String) null, redisCommandsDao);
	}
	
	public RedisTrendSequence(RedisCommandsDao redisCommandsDao, Object defaultKeyspace) {
		this(null, redisCommandsDao, defaultKeyspace);
	}
	
	public RedisTrendSequence(String dbName, RedisCommandsDao redisCommandsDao) {
		this(dbName, redisCommandsDao, null);
	}
	
	public RedisTrendSequence(String dbName, RedisCommandsDao redisCommandsDao, Object defaultKeyspace) {
		super(defaultKeyspace != null ? defaultKeyspace : DEFAULT_KEYSPACE);
		AssertUtils.assertNotNull(redisCommandsDao, "Redis commands dao not be null");
		this.dbName = dbName;
		this.redisCommandsDao = redisCommandsDao;
	}

	@Override
	protected Long doUpdateByKey(Object key, int stepSize) {
		return redisCommandsDao.incrBy(key, stepSize);
	}

}
