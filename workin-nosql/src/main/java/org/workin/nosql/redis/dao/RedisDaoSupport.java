/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016年8月1日
 */

package org.workin.nosql.redis.dao;

import org.springframework.beans.factory.InitializingBean;
import org.workin.nosql.redis.RedisRepositoryManager;

/**
 * @description RedisDao支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class RedisDaoSupport implements InitializingBean {
	
	protected RedisRepositoryManager repositoryManager;
	
	/** 是否为集群环境 */
	protected boolean cluster;
		
	/** 默认连接库索引 */
	protected int defaultDbIndex;
	
	public RedisRepositoryManager getRepositoryManager() {
		return repositoryManager;
	}

	public void setRepositoryManager(RedisRepositoryManager repositoryManager) {
		this.repositoryManager = repositoryManager;
	}
	
	public boolean isCluster() {
		return cluster;
	}

	public void setCluster(boolean cluster) {
		this.cluster = cluster;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.repositoryManager == null)
			throw new IllegalArgumentException(
					"RedisRepositoryManager object can not be null, please inject to spring container.");
		
	}

}
