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

import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * Redis数字生成器实现类，生成的结果具备如下特点：</P>
 * 1.同一个参数维度内生成的数字是唯一的；</P>
 * 2.同一个参数维度内生成的数字是趋势递增的；</P>
 * 3.同一个参数维度内生成的数字在整个集群环境中是连续的；</P>
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisNumberGenerator<P> extends AbstractRedisParameterizeGenerator<P> {

	public RedisNumberGenerator(RedisCommandsDao redisCommandsDao) {
		super(redisCommandsDao);
	}
	
	public RedisNumberGenerator(String dbName, RedisCommandsDao redisCommandsDao) {
		super(dbName, redisCommandsDao);
	}

	@Override
	protected Long generateByParameter(P parameter) {
		return redisCommandsDao.incr(dbName, parameter);
	}

}
