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
 * Create Date : 2015-6-9
 */

package org.sniper.nosql.redis.connection;

import java.util.Map;

import org.sniper.spring.beans.CheckableInitializingBeanAdapter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;

/**
 * 多路由RedisConnectionFactory抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractRoutingConnectionFactory extends CheckableInitializingBeanAdapter
		implements RedisConnectionFactory {
	
	/** 可用的RedisConnectionFactory对象映射集 */
	private Map<Object, RedisConnectionFactory> targetConnectionFactories;
	
	/** 当前默认使用的RedisConnectionFactory对象 */
	private RedisConnectionFactory resolvedDefaultConnectionFactory;
		
	public Map<Object, RedisConnectionFactory> getTargetConnectionFactories() {
		return targetConnectionFactories;
	}

	public void setTargetConnectionFactories(
			Map<Object, RedisConnectionFactory> targetConnectionFactories) {
		this.targetConnectionFactories = targetConnectionFactories;
	}

	public RedisConnectionFactory getResolvedDefaultConnectionFactory() {
		return resolvedDefaultConnectionFactory;
	}

	public void setResolvedDefaultConnectionFactory(
			RedisConnectionFactory resolvedDefaultConnectionFactory) {
		this.resolvedDefaultConnectionFactory = resolvedDefaultConnectionFactory;
	}
	
	@Override
	public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
		return determineConnectionFactory().translateExceptionIfPossible(ex);
	}

	@Override
	public RedisConnection getConnection() {
		return determineConnectionFactory().getConnection();
	}
	
	@Override
	public RedisClusterConnection getClusterConnection() {
		return determineConnectionFactory().getClusterConnection();
	}
	
	@Override
	public RedisSentinelConnection getSentinelConnection() {
		return determineConnectionFactory().getSentinelConnection();
	}
	
	@Override
	public boolean getConvertPipelineAndTxResults() {
		return determineConnectionFactory().getConvertPipelineAndTxResults();
	}
	
	@Override
	protected void checkProperties() {
		if (this.resolvedDefaultConnectionFactory == null) {
			throw new IllegalArgumentException("Property 'resolvedDefaultConnectionFactory' is required");
		}
	}
	
	/**
	 * 确定RedisConnectionFactory对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected RedisConnectionFactory determineConnectionFactory() {
		Object lookupKey = determineCurrentLookupKey();
		RedisConnectionFactory connectionFactory = this.targetConnectionFactories.get(lookupKey);
		
		if (connectionFactory == null) 
			connectionFactory = this.resolvedDefaultConnectionFactory;
		
		if (connectionFactory == null) 
			throw new IllegalStateException(String.format(
					"Cannot determine target RedisConnectionFactory for lookup key [%s]", lookupKey));
		
		return connectionFactory;
	}
	
	/** 
	 * 查找RedisConnectionFactory对象的标识键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected abstract Object determineCurrentLookupKey();

}
