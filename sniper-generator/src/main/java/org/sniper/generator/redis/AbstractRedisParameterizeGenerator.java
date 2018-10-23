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

import java.util.List;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.AbstractParameterizeGenerator;
import org.sniper.nosql.redis.dao.RedisCommandsDao;

/**
 * 基于Redis的参数化生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractRedisParameterizeGenerator<P> extends AbstractParameterizeGenerator<P, Long> {
	
	/** 生成器库名 */
	protected final String dbName;
	
	protected final RedisCommandsDao redisCommandsDao;
	
	public AbstractRedisParameterizeGenerator(RedisCommandsDao redisCommandsDao) {
		this(null, redisCommandsDao);
	}
	
	public AbstractRedisParameterizeGenerator(String dbName, RedisCommandsDao redisCommandsDao) {
		AssertUtils.assertNotNull(redisCommandsDao, "Redis commands dao not be null");
		
		this.dbName = dbName;
		this.redisCommandsDao = redisCommandsDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Long generate(P parameter) {
		return generateByParameter(parameter != null ? parameter : (P) StringUtils.EMPTY);
	}
	
	@Override
	protected List<Long> doBatchGenerate(P parameter, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据参数来生成结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @return
	 */
	protected abstract Long generateByParameter(P parameter);

}
