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
 * Create Date : 2016-8-1
 */

package org.sniper.nosql.redis.command;

import java.beans.PropertyEditor;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.sniper.beans.PropertyConverter;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.nosql.redis.RedisRepositoryManager;
import org.sniper.serialization.Serializer;
import org.sniper.serialization.TypedSerializer;
import org.sniper.serialization.jdk.StringSerializer;

/**
 * Redis支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class RedisSupport extends RedisAccessor {
	
	/** set命令名称 */
	protected static final String SET_COMMAND_NAME;
	
	/** NX命令名称字节数组 */
	protected static final byte[] NX_COMMAND_BYTES;
	
	/** EX命令名称字节数组 */
	protected static final byte[] EX_COMMAND_BYTES;
	
	/** PX命令名称字节数组 */
	protected static final byte[] PX_COMMAND_BYTES;
	
	/** Redis库管理 */
	protected RedisRepositoryManager repositoryManager;
	
	/** 是否为集群环境 */
	protected boolean cluster;
		
	/** 默认连接库索引 */
	protected int defaultDbIndex;
	
	/** 全局默认的字符串序列化器 */
	protected final Serializer stringSerializer = new StringSerializer();
	
	/** 全局键序列化器 */
	private Serializer globalKeySerializer;
	
	/** 全局值序列化器 */
	private Serializer globalValueSerializer;
	
	/** 全局哈希键序列化器 */
	private Serializer globalHashKeySerializer;
	
	/** 全局哈希值序列化器 */
	private Serializer globalHashValueSerializer;
	
	/** 全局默认的属性转换器 */
	private PropertyConverter propertyConverter = new PropertyConverter();
	
	static {
		SET_COMMAND_NAME = "set";
		NX_COMMAND_BYTES = "NX".getBytes();
		EX_COMMAND_BYTES = "EX".getBytes();
		PX_COMMAND_BYTES = "PX".getBytes();
	}
	
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
	
	public PropertyConverter getPropertyConverter() {
		return propertyConverter;
	}

	public void setPropertyConverter(PropertyConverter propertyConverter) {
		this.propertyConverter = propertyConverter;
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
	 * 获取过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param expireSeconds
	 * @param repository
	 * @return
	 */
	protected long getExpireSeconds(long expireSeconds, RedisRepository repository) {
		if (expireSeconds > 0 || repository == null)
			return expireSeconds;
		
		return repository.toSeconds();
	}
	
	/**
	 * 获取过期毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param expireMillis
	 * @param repository
	 * @return
	 */
	protected long getExpireMillis(long expireMillis, RedisRepository repository) {
		if (expireMillis > 0 || repository == null)
			return expireMillis;
		
		return repository.toMillis();
	}
	
	/**
	 * 获取过期毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param expireTime
	 * @param timeUnit
	 * @param repository
	 * @return
	 */
	protected long getExpireMillis(long expireTime, TimeUnit timeUnit, RedisRepository repository) {
		if (expireTime > 0)
			return timeUnit != null ? timeUnit.toMillis(expireTime) : TimeUnit.SECONDS.toMillis(expireTime);
			
		if (repository == null)
			return expireTime;
		
		return repository.toMillis();
	}
	
	/**
	 * 选择指定库的键序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return 
	 */
	protected Serializer selectKeySerializer(String dbName) {
		if (repositoryManager == null)
			return globalKeySerializer;
		
		RedisRepository repository = repositoryManager.getRepository(dbName);
		if (repository == null)
			return globalKeySerializer;
		
		Serializer keySerializer = repository.getKeySerializer();
		return keySerializer != null ? keySerializer : globalKeySerializer;
	}
	
	/**
	 * 选择指定库的值序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	protected Serializer selectValueSerializer(String dbName) {
		if (repositoryManager == null)
			return globalValueSerializer;
		
		RedisRepository repository = repositoryManager.getRepository(dbName);
		if (repository == null)
			return globalValueSerializer;
		
		Serializer valueSerializer = repository.getValueSerializer();
		return valueSerializer != null ? valueSerializer : globalValueSerializer;
	}
	
	/**
	 * 选择指定库的哈希键序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	protected Serializer selectHashKeySerializer(String dbName) {
		if (repositoryManager == null)
			return globalHashKeySerializer;
		
		RedisRepository repository = repositoryManager.getRepository(dbName);
		if (repository == null)
			return globalHashKeySerializer;
		
		Serializer hashKeySerializer = repository.getHashKeySerializer();
		return hashKeySerializer != null ? hashKeySerializer : globalHashKeySerializer;
	}
	
	/**
	 * 选择指定库的哈希值序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @return
	 */
	protected Serializer selectHashValueSerializer(String dbName) {
		if (repositoryManager == null)
			return globalHashValueSerializer;
		
		RedisRepository repository = repositoryManager.getRepository(dbName);
		if (repository == null)
			return globalHashValueSerializer;
		
		Serializer hashValueSerializer = repository.getHashValueSerializer();
		return hashValueSerializer != null ? hashValueSerializer : globalHashValueSerializer;
	}
	
	/**
	 * 将指定库的键字节序列化到Byte类型的数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	protected <K> byte[] serializeKey(String dbName, K key) {
		return selectKeySerializer(dbName).serialize(key);
	}
	
	/**
	 * 将指定库的多个键字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	protected <K> byte[][] serializeKeys(String dbName, K[] keys) {
		Serializer keySerializer = selectKeySerializer(dbName);
		
		byte[][] keyBytes = new byte[keys.length][];
		for (int i = 0; i < keys.length; i++) {
			keyBytes[i] = keySerializer.serialize(keys[i]);
		}
		
		return keyBytes;
	}
	
	/**
	 * 将指定库的值字节序列化到Byte类型的数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param value
	 * @return
	 */
	protected <V> byte[] serializeValue(String dbName, V value) {
		return selectValueSerializer(dbName).serialize(value);
	}
	
	/**
	 * 将指定库的多个值字节序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param values
	 * @return
	 */
	protected <V> byte[][] serializeValues(String dbName, V[] values) {
		Serializer valueSerializer = selectValueSerializer(dbName);
		
		byte[][] valueBytes = new byte[values.length][];
		for (int i = 0; i < values.length; i++) {
			valueBytes[i] = valueSerializer.serialize(values[i]);
		}
			
		return valueBytes;
	}
	
	/** 
	 * 将指定库的键值映射集序列化成Byte类型的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param kValues
	 * @return 
	 */
	protected <K, V> Map<byte[], byte[]> serializeKeyValues(String dbName, Map<K, V> keyValues) {
		Serializer keySerializer = selectKeySerializer(dbName);
		Serializer valueSerializer = selectValueSerializer(dbName);
		
		Map<byte[], byte[]> keyValueBytes = MapUtils.newHashMap(keyValues.size());
		Iterator<Entry<K, V>> iterator = keyValues.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<K, V> entry = iterator.next();
			keyValueBytes.put(keySerializer.serialize(entry.getKey()),
					valueSerializer.serialize(entry.getValue()));
		}
		return keyValueBytes;
	}
	
	/**
	 * 将指定库的哈希键序列化到字节数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashKey
	 * @return
	 */
	protected <H> byte[] serializeHashKey(String dbName, H hashKey) {
		return selectHashKeySerializer(dbName).serialize(hashKey);
	}
	
	/**
	 * 将指定库的多个哈希键序列化到Byte类型的二维数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashKeys 哈希键数组
	 * @return
	 */
	protected <H> byte[][] serializeHashKeys(String dbName, H[] hashKeys) {
		Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		
		byte[][] hashKeyBytes = new byte[hashKeys.length][];
		for (int i = 0; i < hashKeys.length; i++) {
			hashKeyBytes[i] = hashKeySerializer.serialize(hashKeys[i]);
		}
		
		return hashKeyBytes;
	}
	
	/**
	 * 将指定库的哈希值序列化到字节数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashValue
	 * @return
	 */
	protected <V> byte[] serializeHashValue(String dbName, V hashValue) {
		return selectHashValueSerializer(dbName).serialize(hashValue);
	}
	
	/**
	 * 将指定库的哈希键值对进行序列化
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashKeyValues
	 * @return
	 */
	protected <H, V> Map<byte[], byte[]> serializeHashKeyValues(String dbName, Map<H, V> hashKeyValues) {
		Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		
		Map<byte[], byte[]> hashKeyValueBytes = MapUtils.newHashMap(hashKeyValues.size());
		Iterator<Entry<H, V>> iterator = hashKeyValues.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<H, V> entry = iterator.next();
			hashKeyValueBytes.put(hashKeySerializer.serialize(entry.getKey()),
					hashValueSerializer.serialize(entry.getValue()));
		}
		return hashKeyValueBytes;
	}
	
	/**
	 * 将指定库的多个键字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keyByte
	 * @param keyType
	 * @return 
	 */
	protected <K> K deserializeKeyByte(String dbName, byte[] keyByte, Class<K> keyType) {
		Serializer keySerializer = selectKeySerializer(dbName);
		if (keySerializer.isTypedSerializer()) {
			TypedSerializer keyTypedSerializer = (TypedSerializer) keySerializer;
			return keyTypedSerializer.deserialize(keyByte, keyType);
		}
		
		K key = keySerializer.deserialize(keyByte);
		PropertyEditor propertyEditor = propertyConverter.find(keyType);
		return propertyEditor != null ? PropertyConverter.converte(propertyEditor, key, keyType) : key;
	}
	
	/**
	 * 将指定库的多个键字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keyBytes
	 * @param keyType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <K> Set<K> deserializeKeyBytes(String dbName, Set<byte[]> keyBytes, Class<K> keyType) {
		if (CollectionUtils.isEmpty(keyBytes))
			return null;
		
		Serializer keySerializer = selectKeySerializer(dbName);
		Set<K> keys = CollectionUtils.newLinkedHashSet(keyBytes.size());
		if (keySerializer.isTypedSerializer()) {
			TypedSerializer keyTypedSerializer = (TypedSerializer) keySerializer;
			for (byte[] keyByte : keyBytes) {
				keys.add(keyTypedSerializer.deserialize(keyByte, keyType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(keyType);
			if (propertyEditor != null) {
				for (byte[] keyByte : keyBytes) {
					K key = keySerializer.deserialize(keyByte);
					keys.add(PropertyConverter.converte(propertyEditor, key, keyType));
				}
			} else {
				for (byte[] keyByte : keyBytes) {
					keys.add((K) keySerializer.deserialize(keyByte));
				}
			}
		}
		return keys;
		
	}
	
	/**
	 * 将指定库的值字节反序列后返回
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param valueByte
	 * @param valueType
	 * @return
	 */
	protected <V> V deserializeValueByte(String dbName, byte[] valueByte, Class<V> valueType) {
		Serializer valueSerializer = selectValueSerializer(dbName);
		if (valueSerializer.isTypedSerializer())
			return ((TypedSerializer) valueSerializer).deserialize(valueByte, valueType);
		
		V value = valueSerializer.deserialize(valueByte);
		PropertyEditor propertyEditor = propertyConverter.find(valueType);
		return propertyEditor != null ? PropertyConverter.converte(propertyEditor, value, valueType) : value;
	}
	
	/**
	 * 将指定库的多个值字节反序列化到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param valueBytes
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeValueBytes(String dbName, List<byte[]> valueBytes, Class<V> valueType) {
		if (CollectionUtils.isEmpty(valueBytes))
			return null;
		
		Serializer valueSerializer = selectValueSerializer(dbName);
		List<V> list = CollectionUtils.newArrayList(valueBytes.size());
		if (valueSerializer.isTypedSerializer()) {
			TypedSerializer valueTypedSerializer = (TypedSerializer) valueSerializer;
			for (byte[] valueByte : valueBytes) {
				list.add(valueTypedSerializer.deserialize(valueByte, valueType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(valueType);
			if (propertyEditor != null) {
				for (byte[] valueByte : valueBytes) {
					V value = valueSerializer.deserialize(valueByte);
					list.add(PropertyConverter.converte(propertyEditor, value, valueType));
				}
			} else {
				for (byte[] valueByte : valueBytes) {
					list.add((V) valueSerializer.deserialize(valueByte));
				}
			}
		}
		return list;
	}
	
	/**
	 * 将指定库的多个值字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param valueBytes
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> Set<V> deserializeValueBytes(String dbName, Set<byte[]> valueBytes, Class<V> valueType) {
		if (CollectionUtils.isEmpty(valueBytes))
			return null;
		
		Serializer valueSerializer = selectValueSerializer(dbName);
		Set<V> set = CollectionUtils.newLinkedHashSet(valueBytes.size());
		if (valueSerializer.isTypedSerializer()) {
			TypedSerializer typedValueSerializer = (TypedSerializer) valueSerializer;
			for (byte[] valueByte : valueBytes) {
				set.add(typedValueSerializer.deserialize(valueByte, valueType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(valueType);
			if (propertyEditor != null) {
				for (byte[] valueByte : valueBytes) {
					V value = valueSerializer.deserialize(valueByte);
					set.add(PropertyConverter.converte(propertyEditor, value, valueType));
				}
			} else {
				for (byte[] valueByte : valueBytes) {
					set.add((V) valueSerializer.deserialize(valueByte));
				}
			}
		}
		return set;
	}
	
	/**
	 * 将指定库的多个域字节反序列化到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashKeyBytes
	 * @param hashKeyBytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <H> Set<H> deserializeHashKeyBytes(String dbName, Set<byte[]> hashKeyBytes, Class<H> hashKeyType) {
		if (CollectionUtils.isEmpty(hashKeyBytes))
			return null;
		
		Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		Set<H> set = CollectionUtils.newLinkedHashSet(hashKeyBytes.size());
		
		if (hashKeySerializer.isTypedSerializer()) {
			TypedSerializer hashKeyTypedSerializer = (TypedSerializer) hashKeySerializer;
			for (byte[] hashKeyByte : hashKeyBytes) {
				set.add(hashKeyTypedSerializer.deserialize(hashKeyByte, hashKeyType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(hashKeyType);
			if (propertyEditor != null) {
				for (byte[] hashKeyByte : hashKeyBytes) {
					H hashKey = hashKeySerializer.deserialize(hashKeyByte);
					set.add(PropertyConverter.converte(propertyEditor, hashKey, hashKeyType));
				}
			} else {
				for (byte[] hashKeyByte : hashKeyBytes) {
					set.add((H) hashKeySerializer.deserialize(hashKeyByte));
				}
			}
		}
		
		return set;
	}
	
	/**
	 * 将指定库的多个哈希键值对字节反序列化到映射集中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashKeyValueBytes
	 * @param hashKeyType
	 * @param hashValueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <H, V> Map<H, V> deserializeHashKeyValueBytes(String dbName, Map<byte[], byte[]> hashKeyValueBytes,
			Class<H> hashKeyType, Class<V> hashValueType) {
		
		if (MapUtils.isEmpty(hashKeyValueBytes))
			return null;
		
		Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		Map<H, V> map = MapUtils.newLinkedHashMap(hashKeyValueBytes.size());
		
		if (MapUtils.isNotEmpty(hashKeyValueBytes)) {
			Set<Entry<byte[], byte[]>> entrySet = hashKeyValueBytes.entrySet();
			if (hashKeySerializer.isTypedSerializer() && hashValueSerializer.isTypedSerializer()) {
				TypedSerializer typedHashKeySerializer = (TypedSerializer) hashKeySerializer;
				TypedSerializer typedhashValueSerializer = (TypedSerializer) hashValueSerializer;
				for (Entry<byte[], byte[]> entry : entrySet) {
					map.put(typedHashKeySerializer.deserialize(entry.getKey(), hashKeyType),
							typedhashValueSerializer.deserialize(entry.getValue(), hashValueType));
				}
			} else if (hashKeySerializer.isTypedSerializer()) {
				TypedSerializer typedHashKeySerializer = (TypedSerializer) hashKeySerializer;
				PropertyEditor propertyEditor = propertyConverter.find(hashValueType);
				if (propertyEditor != null) {
					for (Entry<byte[], byte[]> entry : entrySet) {
						V hashValue = hashValueSerializer.deserialize(entry.getValue());
						map.put(typedHashKeySerializer.deserialize(entry.getKey(), hashKeyType),
								PropertyConverter.converte(propertyEditor, hashValue, hashValueType));
					}
				} else {
					for (Entry<byte[], byte[]> entry : entrySet) {
						map.put(typedHashKeySerializer.deserialize(entry.getKey(), hashKeyType), 
								(V) hashValueSerializer.deserialize(entry.getValue()));
					}
				}
			} else if (hashValueSerializer.isTypedSerializer()) {
				TypedSerializer typedhashValueSerializer = (TypedSerializer) hashValueSerializer;
				PropertyEditor propertyEditor = propertyConverter.find(hashKeyType);
				if (propertyEditor != null) {
					for (Entry<byte[], byte[]> entry : entrySet) {
						H hashKey = (H) hashKeySerializer.deserialize(entry.getKey());
						map.put(PropertyConverter.converte(propertyEditor, hashKey, hashKeyType), 
								typedhashValueSerializer.deserialize(entry.getValue(), hashValueType));
					}
				} else {
					for (Entry<byte[], byte[]> entry : entrySet) {
						map.put((H) hashKeySerializer.deserialize(entry.getKey()), 
								typedhashValueSerializer.deserialize(entry.getValue(), hashValueType));
					}
				}
			} else {
				PropertyEditor hashKeyPropertyEditor = propertyConverter.find(hashKeyType);
				PropertyEditor hashValuePropertyEditor = propertyConverter.find(hashValueType);
				if (hashKeyPropertyEditor != null && hashValuePropertyEditor != null) {
					for (Entry<byte[], byte[]> entry : entrySet) {
						H hashKey = hashKeySerializer.deserialize(entry.getKey());
						V hashValue = hashValueSerializer.deserialize(entry.getValue());
						map.put(PropertyConverter.converte(hashKeyPropertyEditor, hashKey, hashKeyType), 
								PropertyConverter.converte(hashValuePropertyEditor, hashValue, hashValueType));
					}
				} else if (hashKeyPropertyEditor != null) {
					for (Entry<byte[], byte[]> entry : entrySet) {
						H hashKey = hashKeySerializer.deserialize(entry.getKey());
						map.put(PropertyConverter.converte(hashKeyPropertyEditor, hashKey, hashKeyType), 
								(V) hashValueSerializer.deserialize(entry.getValue()));
					}
				} else if (hashValuePropertyEditor != null) {
					for (Entry<byte[], byte[]> entry : entrySet) {
						V hashValue = hashValueSerializer.deserialize(entry.getValue());
						map.put((H) hashKeySerializer.deserialize(entry.getKey()),
								PropertyConverter.converte(hashValuePropertyEditor, hashValue, hashValueType));
					}
				} else {
					for (Entry<byte[], byte[]> entry : entrySet) {
						map.put((H) hashKeySerializer.deserialize(entry.getKey()), 
								(V) hashValueSerializer.deserialize(entry.getValue()));
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 将指定库的哈希值字节反序列后返回
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashValueByte
	 * @param hashValueType
	 * @return
	 */
	protected <V> V deserializeHashValueByte(String dbName, byte[] hashValueByte, Class<V> hashValueType) {
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		if (hashValueSerializer.isTypedSerializer())
			return ((TypedSerializer) hashValueSerializer).deserialize(hashValueByte, hashValueType);
		
		V value = hashValueSerializer.deserialize(hashValueByte);
		PropertyEditor propertyEditor = propertyConverter.find(hashValueType);
		return propertyEditor != null ? PropertyConverter.converte(propertyEditor, value, hashValueType) : value;
	}
	
	/**
	 * 将指定库的多个哈希值字节反序列化到列表中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param hashValueBytes
	 * @param hashValueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeHashValueBytesToList(String dbName, List<byte[]> hashValueBytes, Class<V> hashValueType) {
		if (CollectionUtils.isEmpty(hashValueBytes))
			return null;
		
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		List<V> list = CollectionUtils.newArrayList(hashValueBytes.size());
		
		if (hashValueSerializer.isTypedSerializer()) {
			TypedSerializer hashValueTypedSerializer = (TypedSerializer) hashValueSerializer;
			for (byte[] hashValueByte : hashValueBytes) {
				list.add(hashValueTypedSerializer.deserialize(hashValueByte, hashValueType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(hashValueType);
			if (propertyEditor != null) {
				for (byte[] hashValueByte : hashValueBytes) {
					V value = hashValueSerializer.deserialize(hashValueByte);
					list.add(PropertyConverter.converte(propertyEditor, value, hashValueType));
				}
			} else {
				for (byte[] hashValueByte : hashValueBytes) {
					list.add((V) hashValueSerializer.deserialize(hashValueByte));
				}
			}
		}
		return list;
	}

}
