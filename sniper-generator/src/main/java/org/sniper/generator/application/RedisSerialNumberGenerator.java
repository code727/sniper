/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-11-14
 */

package org.sniper.generator.application;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.generator.AbstractNumberGenerator;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * Redis流水号生成器实现类，生成结果满足几点要求：</P>
 * 1.在指定的维度区间内流水号是唯一的；</P>
 * 2.在指定的维度区间内流水号是趋势递增的；</P>
 * 3.在指定的维度区间内流水号是连续的；</P>
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisSerialNumberGenerator extends AbstractNumberGenerator {
	
	private final int dbIndex;
	
	private final RedisCommandsDao redisCommandsDao;
	
	public RedisSerialNumberGenerator(RedisCommandsDao redisCommandsDao) {
		this(0, redisCommandsDao);
	}
	
	public RedisSerialNumberGenerator(int dbIndex, RedisCommandsDao redisCommandsDao) {
		AssertUtils.assertNotNull(redisCommandsDao, "Redis commands dao not be null");
		
		this.dbIndex = NumberUtils.minLimit(dbIndex, 0);
		this.redisCommandsDao = redisCommandsDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T generateByDimension(String dimension) {
		return (T) redisCommandsDao.incr(dbIndex, dimension);
	}
			
}
