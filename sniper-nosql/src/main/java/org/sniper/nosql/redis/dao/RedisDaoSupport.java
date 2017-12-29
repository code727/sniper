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

package org.sniper.nosql.redis.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.nosql.redis.RedisRepositoryManager;
import org.sniper.serialization.Serializer;
import org.sniper.spring.beans.CheckableInitializingBean;

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
	 * 获取指定库对应的过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	protected long getExpireSeconds(String dbName) {
		if (repositoryManager == null)
			return 0L;
		
		RedisRepository repository = repositoryManager.getRepository(dbName);
		return repository != null ? getExpireSeconds(repository) : 0L;
	}
	
	/**
	 * 获取指定库设置的过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param repository
	 * @return
	 */
	protected long getExpireSeconds(RedisRepository repository) {
		return DateUtils.toSeconds(repository.getExpireTime(), repository.getTimeUnit());
	}
	
	/**
	 * 选择指定库的键序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return 
	 */
	protected Serializer selectKeySerializer(String dbName) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbName);
			if (repository != null) {
				Serializer keySerializer = repository.getKeySerializer();
				return keySerializer != null ? keySerializer : globalKeySerializer;
			} else
				return globalKeySerializer;
		} else
			return globalKeySerializer;
	}
	
	/**
	 * 选择指定库的值序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	protected Serializer selectValueSerializer(String dbName) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbName);
			if (repository != null) {
				Serializer valueSerializer = repository.getValueSerializer();
				return valueSerializer != null ? valueSerializer : globalValueSerializer;
			} else
				return globalValueSerializer;
		} else
			return globalValueSerializer;
	}
	
	/**
	 * 选择指定库的哈希键序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	protected Serializer selectHashKeySerializer(String dbName) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbName);
			if (repository != null) {
				Serializer hashKeySerializer = repository.getHashKeySerializer();
				return hashKeySerializer != null ? hashKeySerializer : globalHashKeySerializer;
			} else
				return globalHashKeySerializer;
		} else
			return globalHashKeySerializer;
	}
	
	/**
	 * 选择指定库的哈希值序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	protected Serializer selectHashValueSerializer(String dbName) {
		if (repositoryManager != null) {
			RedisRepository repository = repositoryManager.getRepository(dbName);
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
	 * @param dbName
	 * @param kValues
	 * @return 
	 */
	protected <K, V> Map<byte[], byte[]> serializeKeyValueToByteMap(String dbName, Map<K, V> kValues) {
		Serializer fieldKeySerializer = selectKeySerializer(dbName);
		Serializer valueSerializer = selectValueSerializer(dbName);
		
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
	 * @param dbName
	 * @param fValues
	 * @return
	 */
	protected <F, V> Map<byte[], byte[]> serializeFiledValueToByteMap(String dbName, Map<F, V> fValues) {
		Serializer fieldKeySerializer = selectHashKeySerializer(dbName);
		Serializer valueSerializer = selectHashValueSerializer(dbName);
		
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
	 * @param dbName
	 * @param keys
	 * @return
	 */
	protected <K> byte[][] serializeKeysToArray(String dbName, K[] keys) {
		Serializer keySerializer = selectKeySerializer(dbName);
		List<byte[]> list = CollectionUtils.newArrayList();
		for (K key : keys)
			list.add(keySerializer.serialize(key));
		
		return CollectionUtils.toArray(list);
	}
	
	/**
	 * 将指定库的多个值字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param values
	 * @return
	 */
	protected <V> byte[][] serializeValuesToArray(String dbName, V[] values) {
		Serializer valueSerializer = selectValueSerializer(dbName);
		List<byte[]> list = CollectionUtils.newArrayList();
		for (V value : values)
			list.add(valueSerializer.serialize(value));
		
		return CollectionUtils.toArray(list);
	}
	
	/**
	 * 将指定库的多个域字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param fileds
	 * @return
	 */
	protected <F> byte[][] serializeFiledsToArray(String dbName, F[] fileds) {
		Serializer keySerializer = selectHashKeySerializer(dbName);
		List<byte[]> list = CollectionUtils.newArrayList();
		for (F filed : fileds)
			list.add(keySerializer.serialize(filed));
		
		return CollectionUtils.toArray(list);
	}
	
	/**
	 * 将指定库的多个值字节反序列化到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param valueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeValueByteToList(String dbName, Collection<byte[]> valueBytes) {
		Serializer valueSerializer = selectValueSerializer(dbName);
		List<V> list = CollectionUtils.newArrayList();
		for (byte[] valueByte : valueBytes) 
			list.add((V) valueSerializer.deserialize(valueByte));
		return list;
	}
	
	/**
	 * 将指定库的多个值字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param valueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> Set<V> deserializeValueByteToSet(String dbName, Collection<byte[]> valueBytes) {
		Serializer valueSerializer = selectValueSerializer(dbName);
		Set<V> set = CollectionUtils.newLinkedHashSet();
		for (byte[] valueByte : valueBytes) 
			set.add((V) valueSerializer.deserialize(valueByte));
		return set;
	}
	
	/**
	 * 将指定库的多个哈希值字节反序列化到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashValueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeHashValueByteToList(String dbName, List<byte[]> hashValueBytes) {
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		List<V> list = CollectionUtils.newArrayList();
		for (byte[] hashValueByte : hashValueBytes) 
			list.add((V) hashValueSerializer.deserialize(hashValueByte));
		return list;
	}
	
	/**
	 * 将指定库的多个域值字节反序列化到映射集中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param fieldValueBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <F, V> Map<F, V> deserializeFiledValueByteToMap(String dbName, Map<byte[], byte[]> fieldValueBytes) {
		Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
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
	 * @param dbName
	 * @param fieldBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <F> Set<F> deserializeFiledBytesToSet(String dbName, Set<byte[]> fieldBytes) {
		Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		Set<F> set = CollectionUtils.newLinkedHashSet();
		for (byte[] fieldByte : fieldBytes) 
			set.add((F) hashKeySerializer.deserialize(fieldByte));
		return set;
	}

}
