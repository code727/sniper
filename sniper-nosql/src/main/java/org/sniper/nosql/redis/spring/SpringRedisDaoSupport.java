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

package org.sniper.nosql.redis.spring;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.nosql.redis.dao.RedisDaoSupport;
import org.sniper.nosql.redis.serializer.SpringRedisSerializerProxy;

/**
 * Redis数据访问接口支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class SpringRedisDaoSupport extends RedisDaoSupport {
			
	private RedisTemplate<?, ?> redisTemplate;
	
	public RedisTemplate<?, ?> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<?, ?> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	protected void checkProperties() {
		super.checkProperties();
		
		if (this.redisTemplate == null)
			throw new IllegalArgumentException("Property 'RedisTemplate' must not be null");
	}
	
	@Override
	protected void initializeDefaultDbIndex() throws Exception {
		RedisConnectionFactory connectionFactory = this.redisTemplate.getConnectionFactory();
		Field dbIndex = ReflectionUtils.getField(connectionFactory, "dbIndex");
		if (dbIndex != null)
			this.defaultDbIndex = ReflectionUtils.getFieldValue(connectionFactory, dbIndex);
		else
			this.defaultDbIndex = 0;
	}

	@Override
	protected void initializeGlobalSerializers() {
		if (getGlobalKeySerializer() == null)
			setGlobalKeySerializer(new SpringRedisSerializerProxy(this.redisTemplate.getKeySerializer()));
		
		if (getGlobalValueSerializer() == null)
			setGlobalValueSerializer(new SpringRedisSerializerProxy(this.redisTemplate.getValueSerializer()));
		
		if (getGlobalHashKeySerializer() == null)
			setGlobalHashKeySerializer(new SpringRedisSerializerProxy(this.redisTemplate.getHashKeySerializer()));
		
		if (getGlobalHashValueSerializer() == null)
			setGlobalHashValueSerializer(new SpringRedisSerializerProxy(this.redisTemplate.getHashValueSerializer()));
	}
	
	/**
	 * 选择并连接库
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param dbIndex
	 * @return
	 */
	protected RedisRepository select(RedisConnection connection, int dbIndex) {
		if (dbIndex != this.defaultDbIndex && !isCluster())
			// 非集群环境下redis的select命令才能被执行
			connection.select(dbIndex);
		
		return repositoryManager != null ? repositoryManager.getRepository(dbIndex) : null;
	}
		
	/**
	 * 设置当前库数据键的全局过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param repository
	 * @param key 数据键
	 */
	protected void setExpireTime(RedisConnection connection, RedisRepository repository, byte[] key) {
		if (repository != null) {
			String timeUnit = repository.getTimeUnit();
			long expireSeconds = DateUtils.getSecond(repository.getExpireTime(), 
					StringUtils.safeString(timeUnit).trim().toLowerCase());
			if (expireSeconds > 0) 
				connection.expire(key, expireSeconds);
		}
	}
	
	/**
	 * 设置当前库数据键的过期时间。当参数expireTime小于等于0时，则使用当前库设置的全局过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param repository
	 * @param key
	 * @param expireSeconds
	 */
	protected void setExpireTime(RedisConnection connection, RedisRepository repository, byte[] key, long expireSeconds) {
		if (expireSeconds > 0)
			connection.expire(key, expireSeconds);
		else
			this.setExpireTime(connection, repository, key);
	}
	
	/** 
	 * 设置当前库多个数据键的全局过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param repository
	 * @param keySet 数据键集
	 */
	protected void setExpireTime(RedisConnection connection, RedisRepository repository, Set<byte[]> keySet) {
		if (repository != null) {
			String timeUnit = repository.getTimeUnit();
			long expireTime = DateUtils.getSecond(repository.getExpireTime(), 
					StringUtils.safeString(timeUnit).trim().toLowerCase());
			if (expireTime > 0) {
				for (byte[] keyByte : keySet) 
					connection.expire(keyByte, expireTime);
			}
		}
	}
	
	/**
	 * 设置当前库多个数据键的过期时间。当参数expireTime小于等于0时，则使用当前库设置的全局过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param repository
	 * @param keySet
	 * @param expireSeconds
	 */
	protected void setExpireTime(RedisConnection connection, RedisRepository repository, Set<byte[]> keySet, long expireSeconds) {
		if (expireSeconds > 0) {
			for (byte[] keyByte : keySet) 
				connection.expire(keyByte, expireSeconds);
		} else 
			this.setExpireTime(connection, repository, keySet);
	}
	
	/**
	 * 获取不同类型键对应的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dataType
	 * @param connection
	 * @param dbIndex
	 * @param targetKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> listByDataType(DataType dataType, RedisConnection connection, int dbIndex, byte[] targetKey) {
		try {
			// 执行当前对象的xxxTypeList方法后返回结果，其中xxx表示DataType枚举的code值
			return (List<V>) ReflectionUtils.invokeMethod(this, dataType.code() + "TypeList", 
					new Class<?>[] { RedisConnection.class, int.class, byte[].class },
					new Object[] { connection, dbIndex, targetKey });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取none(不存在)键类型对应的数据列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param repository
	 * @param targetKey
	 * @return
	 */
	protected <V> List<V> noneTypeList(RedisConnection connection, int dbIndex, byte[] targetKey) {
		return null;
	}
	
	/**
	 * 获取string(字符串)键类型对应的数据列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param dbIndex
	 * @param targetKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> stringTypeList(RedisConnection connection, int dbIndex, byte[] targetKey) {
		RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		List<V> result = CollectionUtils.newArrayList();
		result.add(valueSerializer.deserialize(connection.get(targetKey)));
		return result;
	}
	
	/**
	 * 获取list(列表)键类型对应的数据列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param dbIndex
	 * @param targetKey
	 * @return
	 */
	protected <V> List<V> listTypeList(RedisConnection connection, int dbIndex, byte[] targetKey) {
		return deserializeValueByteToList(dbIndex, connection.lRange(targetKey, 0, -1));
	}
	
	/**
	 * 获取set(集合)键类型对应的数据列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param dbIndex
	 * @param targetKey
	 * @return
	 */
	protected <V> List<V> setTypeList(RedisConnection connection, int dbIndex, byte[] targetKey) {
		return deserializeValueByteToList(dbIndex, connection.sMembers(targetKey));
	}
	
	/**
	 * 获取zset(有序集合)键类型对应的数据列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param dbIndex
	 * @param targetKey
	 * @return
	 */
	protected <V> List<V> zsetTypeList(RedisConnection connection, int dbIndex, byte[] targetKey) {
		Set<byte[]> resultBytes = connection.zRange(targetKey, 0, -1);
		return deserializeValueByteToList(dbIndex, resultBytes);
	}
	
	/**
	 * 获取hash(哈希表)键类型对应的数据列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param dbIndex
	 * @param targetKey
	 * @return
	 */
	protected <V> List<V> hashTypeList(RedisConnection connection, int dbIndex, byte[] targetKey) {
		// 返回当前键所有域对应的值列表
		return deserializeHashValueByteToList(dbIndex, connection.hVals(targetKey));
	}

}
