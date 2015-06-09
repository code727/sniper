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

package org.workin.nosql.redis.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;
import org.workin.nosql.redis.RedisRepository;
import org.workin.nosql.redis.RedisRepositoryManager;
import org.workin.support.context.ApplicationContext;
import org.workin.support.context.DataSourceHolder;
import org.workin.support.context.ThreadLocalContext;

/**
 * @description Redis数据访问接口支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class RedisDaoSupport implements InitializingBean {
		
	@Autowired
	protected RedisTemplate<?, ?> redisTemplate;
	
	@Autowired
	protected RedisRepositoryManager repositoryManager;
	
	/** 当前库 */
	private ApplicationContext<String, Integer> currentDb;
	
	/** 全局键序列化器 */
	private RedisSerializer<?> globalKeySerializer;
	
	/** 全局值序列化器 */
	private RedisSerializer<?> globalValueSerializer;
	
	/** 全局哈希键序列化器 */
	private RedisSerializer<?> globalHashKeySerializer;
	
	/** 全局哈希值序列化器 */
	private RedisSerializer<?> globalHashValueSerializer;
	
	public RedisTemplate<?, ?> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<?, ?> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public RedisRepositoryManager getRepositoryManager() {
		return repositoryManager;
	}

	public void setRepositoryManager(RedisRepositoryManager repositoryManager) {
		this.repositoryManager = repositoryManager;
	}
		
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.redisTemplate == null)
			throw new IllegalArgumentException(
					"RedisTemplate object can not be null, please inject to spring container.");
		
		if (this.repositoryManager == null)
			throw new IllegalArgumentException(
					"RedisRepositoryManager object can not be null, please inject to spring container.");
		
		this.globalKeySerializer = this.redisTemplate.getKeySerializer();
		this.globalValueSerializer = this.redisTemplate.getValueSerializer();
		this.globalHashKeySerializer = this.redisTemplate.getHashKeySerializer();
		this.globalHashValueSerializer = this.redisTemplate.getHashValueSerializer();
		
		this.currentDb = new ThreadLocalContext<String, Integer>();
	}
	
	/**
	 * @description 获取指定索引库配置对应的全局过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndx
	 * @return
	 */
	protected long getExpireSecond(int dbIndx) {
		RedisRepository repository = this.repositoryManager.getRepository(dbIndx);
		return repository != null ? getExpireSecond(repository) : 0L;
	}
	
	/**
	 * @description 获取指定库配置对应的全局过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param repository
	 * @return
	 */
	protected long getExpireSecond(RedisRepository repository) {
		String timeUnit = repository.getTimeUnit();
		long expireTime = DateUtils.getSecond(repository.getExpireTime(),
				StringUtils.safeString(timeUnit).trim().toLowerCase());
		return expireTime;
	}
	
	/**
	 * @description 选择并连接库
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param dbIndex
	 * @return
	 */
	protected RedisRepository select(RedisConnection connection, int dbIndex) {
		Integer currentIndex = currentDb.getAttribute(DataSourceHolder.getDataSourceName());
		// 避免在同一个连接中重复多次选择同一个库，包括在数据源环境下
		if (currentIndex == null || currentIndex != dbIndex) {
			connection.select(dbIndex);
			currentDb.setAttribute(DataSourceHolder.getDataSourceName(), dbIndex);
		}
		return repositoryManager != null ? repositoryManager.getRepository(dbIndex) : null;
	}
		
	/**
	 * @description 设置当前库数据键的过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 * @param repository
	 * @param key 数据键
	 */
	protected void setExpireTime(RedisConnection connection, RedisRepository repository, byte[] key) {
		if (repository != null) {
			String timeUnit = repository.getTimeUnit();
			long expireTime = DateUtils.getSecond(repository.getExpireTime(), 
					StringUtils.safeString(timeUnit).trim().toLowerCase());
			if (expireTime > 0) 
				connection.expire(key, expireTime);
		}
	}
	
	/** 
	 * @description 设置当前库多个数据键的过期时间
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
	 * @description 选择指定索引库的键序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return 
	 */
	protected RedisSerializer<?> selectKeySerializer(int dbIndex) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbIndex);
			if (repository != null) {
				RedisSerializer<?> keySerializer = repository.getKeySerializer();
				return keySerializer != null ? keySerializer : globalKeySerializer;
			} else
				return globalKeySerializer;
		} else
			return globalKeySerializer;
	}
	
	/**
	 * @description 选择指定索引库的值序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return
	 */
	protected RedisSerializer<?> selectValueSerializer(int dbIndex) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbIndex);
			if (repository != null) {
				RedisSerializer<?> valueSerializer = repository.getValueSerializer();
				return valueSerializer != null ? valueSerializer : globalValueSerializer;
			} else
				return globalValueSerializer;
		} else
			return globalValueSerializer;
	}
	
	/**
	 * @description 选择指定索引库的哈希键序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return
	 */
	protected RedisSerializer<?> selectHashKeySerializer(int dbIndex) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbIndex);
			if (repository != null) {
				RedisSerializer<?> hashKeySerializer = repository.getHashKeySerializer();
				return hashKeySerializer != null ? hashKeySerializer : globalHashKeySerializer;
			} else
				return globalHashKeySerializer;
		} else
			return globalHashKeySerializer;
	}
	
	/**
	 * @description 选择指定索引库的哈希值序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return
	 */
	protected RedisSerializer<?> selectHashValueSerializer(int dbIndex) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbIndex);
			if (repository != null) {
				RedisSerializer<?> hashValueSerializer = repository.getHashValueSerializer();
				return hashValueSerializer != null ? hashValueSerializer : globalHashValueSerializer;
			} else
				return globalHashValueSerializer;
		} else
			return globalHashValueSerializer;
	}
	
	/**
	 * @description 将指定库的键值映射集序列化成Byte类型的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param kValues
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <K, V> Map<byte[], byte[]> serializeKeyValueToByteMap(int dbIndex, Map<K, V> kValues) {
		RedisSerializer<K> fieldKeySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		Map<byte[], byte[]> result = new HashMap<byte[], byte[]>();
		Iterator<Entry<K, V>> iterator = kValues.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<K, V> entry = iterator.next();
			result.put(fieldKeySerializer.serialize(entry.getKey()),
					valueSerializer.serialize(entry.getValue()));
		}
		return result;
	}
	
	/**
	 * @description 将指定库的域值映射集序列化成Byte类型的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param fValues
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <F, V> Map<byte[], byte[]> serializeFiledValueToByteMap(int dbIndex, Map<F, V> fValues) {
		RedisSerializer<F> fieldKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		Map<byte[], byte[]> result = new HashMap<byte[], byte[]>();
		Iterator<Entry<F, V>> iterator = fValues.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<F, V> entry = iterator.next();
			result.put(fieldKeySerializer.serialize(entry.getKey()),
					valueSerializer.serialize(entry.getValue()));
		}
		return result;
	}
	
	/**
	 * @description 将指定库的多个键字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <K> byte[][] serializeKeysToArray(int dbIndex, K[] keys) {
		RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		List<byte[]> list = CollectionUtils.newArrayList();
		for (K key : keys)
			list.add(keySerializer.serialize(key));
		
		return CollectionUtils.toArray(list);
	}
	
	/**
	 * @description 将指定库的多个域字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param fileds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <F> byte[][] serializeFiledsToArray(int dbIndex, F[] fileds) {
		RedisSerializer<F> keySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		List<byte[]> list = CollectionUtils.newArrayList();
		for (F filed : fileds)
			list.add(keySerializer.serialize(filed));
		
		return CollectionUtils.toArray(list);
	}
	
	/**
	 * @description 将指定库的多个值字节反序列化到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param valueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeValueByteToList(int dbIndex, Collection<byte[]> valueBytes) {
		RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		List<V> list = CollectionUtils.newArrayList();
		for (byte[] valueByte : valueBytes) 
			list.add(valueSerializer.deserialize(valueByte));
		return list;
	}
	
	/**
	 * @description 将指定库的多个值字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param valueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> Set<V> deserializeValueByteToSet(int dbIndex, Collection<byte[]> valueBytes) {
		RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		Set<V> set = CollectionUtils.newLinkedHashSet();
		for (byte[] valueByte : valueBytes) 
			set.add(valueSerializer.deserialize(valueByte));
		return set;
	}
	
	
	/**
	 * @description 将指定库的多个哈希值字节反序列化到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param hashValueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeHashValueByteToList(int dbIndex, List<byte[]> hashValueBytes) {
		RedisSerializer<V> hashValueSerializer = (RedisSerializer<V>) selectHashValueSerializer(dbIndex);
		List<V> list = CollectionUtils.newArrayList();
		for (byte[] hashValueByte : hashValueBytes) 
			list.add(hashValueSerializer.deserialize(hashValueByte));
		return list;
	}
	
	/**
	 * @description 将指定库的多个域值字节反序列化到映射集中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param fieldValueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <F, V> Map<F, V> deserializeFiledValueByteToMap(int dbIndex, Map<byte[], byte[]> fieldValueBytes) {
		RedisSerializer<F> hashKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		RedisSerializer<V> hashValueSerializer = (RedisSerializer<V>) selectHashValueSerializer(dbIndex);
		Map<F, V> map = MapUtils.newLinkedHashMap();
		if (MapUtils.isNotEmpty(fieldValueBytes)) {
			Set<Entry<byte[], byte[]>> entrySet = fieldValueBytes.entrySet();
			for (Entry<byte[], byte[]> entry : entrySet) 
				map.put(hashKeySerializer.deserialize(entry.getKey()),
						hashValueSerializer.deserialize(entry.getValue()));
		}
		return map;
	}
	
	/**
	 * @description 将指定库的多个域字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param fieldBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <F> Set<F> deserializeFiledBytesToSet(int dbIndex, Set<byte[]> fieldBytes) {
		RedisSerializer<F> hashKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		Set<F> set = CollectionUtils.newLinkedHashSet();
		for (byte[] fieldByte : fieldBytes) 
			set.add(hashKeySerializer.deserialize(fieldByte));
		return set;
	}
	
	/**
	 * @description 获取不同类型键对应的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dataType
	 * @param connection
	 * @param dbIndex
	 * @param targetKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> listByDataType(DataType dataType,
			RedisConnection connection, int dbIndex, byte[] targetKey) {
		try {
			// 执行当前对象的xxxTypeList方法后返回结果，其中xxx表示DataType枚举的code值
			return (List<V>) ReflectionUtils.invokeMethod(this, dataType.code() + "TypeList", 
					new Class<?>[] { RedisConnection.class, int.class, byte[].class },
					new Object[] { connection, dbIndex, targetKey });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description 获取none(不存在)键类型对应的数据列表
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
	 * @description 获取string(字符串)键类型对应的数据列表
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
	 * @description 获取list(列表)键类型对应的数据列表
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
	 * @description 获取set(集合)键类型对应的数据列表
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
	 * @description 获取zset(有序集合)键类型对应的数据列表
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
	 * @description 获取hash(哈希表)键类型对应的数据列表
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
