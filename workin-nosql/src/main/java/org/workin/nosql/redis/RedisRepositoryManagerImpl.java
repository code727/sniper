/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-3-26
 */

package org.workin.nosql.redis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description  Redis库配置管理实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisRepositoryManagerImpl implements RedisRepositoryManager {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisRepositoryManagerImpl.class);
	
	private Map<Integer, RedisRepository> repositories;

	@Override
	public void setRepositories(Map<Integer, RedisRepository> repositories) {
		this.repositories = repositories;
	}

	public Map<Integer, RedisRepository> getRepositories() {
		return this.repositories;
	}

	@Override
	public RedisRepository getRepository(int index) {
		RedisRepository repository = repositories.get(index);
		if (repository == null)
			logger.warn("Redis repository [index:" + index + "] configuration has not exist.Will be return null.");
		return repository;
	}

}
