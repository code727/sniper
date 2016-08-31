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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.nosql.redis.RedisRepository;
import org.workin.nosql.redis.RedisRepositoryManager;
import org.workin.serialization.Serializer;
import org.workin.spring.beans.CheckableInitializingBean;

/**
 * RedisDao支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class RedisDaoSupport extends CheckableInitializingBean {
	
	/** Redis库管理 */
	protected RedisRepositoryManager repositoryManager;
	
	/** 是否为集群环境 */
	protected boolean cluster;
		
	/** 默认连接库索引 */
	protected int defaultDbIndex;
	
	/** 全局键序列化器 */
	private Serializer globalKeySerializer;
	
	/** 全局值序列化器 */
	private Serializer globalValueSerializer;
	
	/** 全局哈希键序列化器 */
	private Serializer globalHashKeySerializer;
	
	/** 全局哈希值序列化器 */
	private Serializer globalHashValueSerializer;
	
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
	
	public Serializer getGlobalKeySerializer() {
		return globalKeySerializer;
	}

	public void setGlobalKeySerializer(Serializer globalKeySerializer) {
		this.globalKeySerializer = globalKeySerializer;
	}

	public Serializer getGlobalValueSerializer() {
		return globalValueSerializer;
	}

	public void setGlobalValueSerializer(Serializer globalValueSerializer) {
		this.globalValueSerializer = globalValueSerializer;
	}

	public Serializer getGlobalHashKeySerializer() {
		return globalHashKeySerializer;
	}

	public void setGlobalHashKeySerializer(Serializer globalHashKeySerializer) {
		this.globalHashKeySerializer = globalHashKeySerializer;
	}

	public Serializer getGlobalHashValueSerializer() {
		return globalHashValueSerializer;
	}

	public void setGlobalHashValueSerializer(Serializer globalHashValueSerializer) {
		this.globalHashValueSerializer = globalHashValueSerializer;
	}
	
	@Override
	protected void checkProperties() {
		if (this.repositoryManager == null)
			throw new IllegalArgumentException("Property 'repositoryManager' is required");
	}
	
	@Override
	protected void init() throws Exception {
		initializeDefaultDbIndex();
		initializeGlobalSerializers();
	}
	
	/**
	 * 初始化默认连接库索引
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws Exception
	 */
	protected abstract void initializeDefaultDbIndex() throws Exception;
	
	/**
	 * 初始化全局序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected abstract void initializeGlobalSerializers();
	
	/**
	 * 获取默认连接库的索引
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected int getDefaultDbIndex() {
		return this.defaultDbIndex;
	}
	
	/**
	 * 获取指定索引库配置对应的全局过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndx
	 * @return
	 */
	protected long getExpireSecond(int dbIndx) {
		RedisRepository repository = this.repositoryManager.getRepository(dbIndx);
		return repository != null ? getExpireSecond(repository) : 0L;
	}
	
	/**
	 * 获取指定库配置对应的全局过期秒数
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
	 * 选择指定索引库的键序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return 
	 */
	protected Serializer selectKeySerializer(int dbIndex) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbIndex);
			if (repository != null) {
				Serializer keySerializer = repository.getKeySerializer();
				return keySerializer != null ? keySerializer : globalKeySerializer;
			} else
				return globalKeySerializer;
		} else
			return globalKeySerializer;
	}
	
	/**
	 * 选择指定索引库的值序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return
	 */
	protected Serializer selectValueSerializer(int dbIndex) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbIndex);
			if (repository != null) {
				Serializer valueSerializer = repository.getValueSerializer();
				return valueSerializer != null ? valueSerializer : globalValueSerializer;
			} else
				return globalValueSerializer;
		} else
			return globalValueSerializer;
	}
	
	/**
	 * 选择指定索引库的哈希键序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return
	 */
	protected Serializer selectHashKeySerializer(int dbIndex) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbIndex);
			if (repository != null) {
				Serializer hashKeySerializer = repository.getHashKeySerializer();
				return hashKeySerializer != null ? hashKeySerializer : globalHashKeySerializer;
			} else
				return globalHashKeySerializer;
		} else
			return globalHashKeySerializer;
	}
	
	/**
	 * 选择指定索引库的哈希值序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return
	 */
	protected Serializer selectHashValueSerializer(int dbIndex) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbIndex);
			if (repository != null) {
				Serializer hashValueSerializer = repository.getHashValueSerializer();
				return hashValueSerializer != null ? hashValueSerializer : globalHashValueSerializer;
			} else
				return globalHashValueSerializer;
		} else
			return globalHashValueSerializer;
	}
	
	/** 
	 * 将指定库的键值映射集序列化成Byte类型的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param kValues
	 * @return 
	 */
	protected <K, V> Map<byte[], byte[]> serializeKeyValueToByteMap(int dbIndex, Map<K, V> kValues) {
		Serializer fieldKeySerializer = selectKeySerializer(dbIndex);
		Serializer valueSerializer = selectValueSerializer(dbIndex);
		
		Map<byte[], byte[]> result = new HashMap<byte[], byte[]>();
		Iterator<Entry<K, V>> iterator = kValues.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<K, V> entry = iterator.next();
			result.put(fieldKeySerializer.serialize(entry.getKey()),
					valueSerializer.serialize(entry.getValue()));
		}
		return result;
	}
	
	/**
	 * 将指定库的域值映射集序列化成Byte类型的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param fValues
	 * @return
	 */
	protected <F, V> Map<byte[], byte[]> serializeFiledValueToByteMap(int dbIndex, Map<F, V> fValues) {
		Serializer fieldKeySerializer = selectHashKeySerializer(dbIndex);
		Serializer valueSerializer = selectHashValueSerializer(dbIndex);
		
		Map<byte[], byte[]> result = new HashMap<byte[], byte[]>();
		Iterator<Entry<F, V>> iterator = fValues.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<F, V> entry = iterator.next();
			result.put(fieldKeySerializer.serialize(entry.getKey()),
					valueSerializer.serialize(entry.getValue()));
		}
		return result;
	}
	
	/**
	 * 将指定库的多个键字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	protected <K> byte[][] serializeKeysToArray(int dbIndex, K[] keys) {
		Serializer keySerializer = selectKeySerializer(dbIndex);
		List<byte[]> list = CollectionUtils.newArrayList();
		for (K key : keys)
			list.add(keySerializer.serialize(key));
		
		return CollectionUtils.toArray(list);
	}
	
	/**
	 * 将指定库的多个值字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param values
	 * @return
	 */
	protected <V> byte[][] serializeValuesToArray(int dbIndex, V[] values) {
		Serializer valueSerializer = selectValueSerializer(dbIndex);
		List<byte[]> list = CollectionUtils.newArrayList();
		for (V value : values)
			list.add(valueSerializer.serialize(value));
		
		return CollectionUtils.toArray(list);
	}
	
	/**
	 * 将指定库的多个域字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param fileds
	 * @return
	 */
	protected <F> byte[][] serializeFiledsToArray(int dbIndex, F[] fileds) {
		Serializer keySerializer = selectHashKeySerializer(dbIndex);
		List<byte[]> list = CollectionUtils.newArrayList();
		for (F filed : fileds)
			list.add(keySerializer.serialize(filed));
		
		return CollectionUtils.toArray(list);
	}
	
	/**
	 * 将指定库的多个值字节反序列化到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param valueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeValueByteToList(int dbIndex, Collection<byte[]> valueBytes) {
		Serializer valueSerializer = selectValueSerializer(dbIndex);
		List<V> list = CollectionUtils.newArrayList();
		for (byte[] valueByte : valueBytes) 
			list.add((V) valueSerializer.deserialize(valueByte));
		return list;
	}
	
	/**
	 * 将指定库的多个值字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param valueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> Set<V> deserializeValueByteToSet(int dbIndex, Collection<byte[]> valueBytes) {
		Serializer valueSerializer = selectValueSerializer(dbIndex);
		Set<V> set = CollectionUtils.newLinkedHashSet();
		for (byte[] valueByte : valueBytes) 
			set.add((V) valueSerializer.deserialize(valueByte));
		return set;
	}
	
	/**
	 * 将指定库的多个哈希值字节反序列化到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param hashValueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeHashValueByteToList(int dbIndex, List<byte[]> hashValueBytes) {
		Serializer hashValueSerializer = selectHashValueSerializer(dbIndex);
		List<V> list = CollectionUtils.newArrayList();
		for (byte[] hashValueByte : hashValueBytes) 
			list.add((V) hashValueSerializer.deserialize(hashValueByte));
		return list;
	}
	
	/**
	 * 将指定库的多个域值字节反序列化到映射集中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param fieldValueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <F, V> Map<F, V> deserializeFiledValueByteToMap(int dbIndex, Map<byte[], byte[]> fieldValueBytes) {
		Serializer hashKeySerializer = selectHashKeySerializer(dbIndex);
		Serializer hashValueSerializer = selectHashValueSerializer(dbIndex);
		Map<F, V> map = MapUtils.newLinkedHashMap();
		if (MapUtils.isNotEmpty(fieldValueBytes)) {
			Set<Entry<byte[], byte[]>> entrySet = fieldValueBytes.entrySet();
			for (Entry<byte[], byte[]> entry : entrySet) 
				map.put((F) hashKeySerializer.deserialize(entry.getKey()),
						(V) hashValueSerializer.deserialize(entry.getValue()));
		}
		return map;
	}
	
	/**
	 * 将指定库的多个域字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param fieldBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <F> Set<F> deserializeFiledBytesToSet(int dbIndex, Set<byte[]> fieldBytes) {
		Serializer hashKeySerializer = selectHashKeySerializer(dbIndex);
		Set<F> set = CollectionUtils.newLinkedHashSet();
		for (byte[] fieldByte : fieldBytes) 
			set.add((F) hashKeySerializer.deserialize(fieldByte));
		return set;
	}

}
