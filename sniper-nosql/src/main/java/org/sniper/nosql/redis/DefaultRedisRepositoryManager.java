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

package org.sniper.nosql.redis;

import java.util.Map;

/**
 *  Redis库配置管理默认实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefaultRedisRepositoryManager implements RedisRepositoryManager {
		
	private Map<String, RedisRepository> repositories;

	@Override
	public void setRepositories(Map<String, RedisRepository> repositories) {
		this.repositories = repositories;
	}

	public Map<String, RedisRepository> getRepositories() {
		return this.repositories;
	}

	@Override
	public RedisRepository getRepository(String dbName) {
		return repositories.get(dbName);
	}

}
