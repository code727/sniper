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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.beans.PropertyConverter;
import org.sniper.beans.PropertyUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.nosql.redis.RedisRepositoryManager;
import org.sniper.nosql.redis.model.xscan.HScanResult;
import org.sniper.nosql.redis.model.xscan.IndexedScanResult;
import org.sniper.nosql.redis.model.xscan.MappedScanResult;
import org.sniper.nosql.redis.model.xscan.SScanResult;
import org.sniper.nosql.redis.model.xscan.ScanResult;
import org.sniper.nosql.redis.model.xscan.ZScanResult;
import org.sniper.nosql.redis.option.ScanOption;
import org.sniper.serialization.Serializer;
import org.sniper.serialization.TypedSerializer;
import org.sniper.serialization.jdk.JdkSerializer;
import org.sniper.serialization.jdk.StringSerializer;

/**
 * Redis支持类
 * @author  Daniele
 * @version 1.0
 */
public abstract class RedisSupport extends RedisAccessor {
	
	protected final Logger logger;
	
	/** set命令名称 */
	protected static final String SET_COMMAND_NAME;
	
	/** GEODIST命令名称 */
	protected static final String GEODIST_COMMAND_NAME;
	
	/** SCAN命令名称 */
	protected static final String SCAN_COMMAND_NAME;
	
	/** SSCAN命令名称 */
	protected static final String SSCAN_COMMAND_NAME;
	
	/** HSCAN命令名称 */
	protected static final String HSCAN_COMMAND_NAME;
	
	/** ZSCAN命令名称 */
	protected static final String ZSCAN_COMMAND_NAME;
	
	/** ping命令名称 */
	protected static final String PING_COMMAND_NAME;
	
	/** NX命令名称字节数组 */
	protected static final byte[] NX_COMMAND_BYTES;
	
	/** EX命令名称字节数组 */
	protected static final byte[] EX_COMMAND_BYTES;
	
	/** PX命令名称字节数组 */
	protected static final byte[] PX_COMMAND_BYTES;
	
	protected static final byte[] MATCH_COMMAND_BYTES;
	
	protected static final byte[] COUNT_COMMAND_BYTES;
	
	/** Redis库管理 */
	protected RedisRepositoryManager repositoryManager;
	
	/** 是否为集群环境 */
	protected boolean cluster;
		
	/** 默认连接库索引 */
	protected int defaultDbIndex;
	
	/** 字符串序列化器 */
	protected final Serializer stringSerializer = new StringSerializer();
	
	/** 默认的序列化器 */
	private Serializer defaultSerializer;
	
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
	
	/** 预留，是否维持原子性的操作 */
	private boolean keepAtomic;
	
	static {
		SET_COMMAND_NAME = "set";
		GEODIST_COMMAND_NAME = "geoDist";
		SCAN_COMMAND_NAME = "scan";
		SSCAN_COMMAND_NAME = "sscan";
		HSCAN_COMMAND_NAME = "hscan";
		ZSCAN_COMMAND_NAME = "zscan";
		PING_COMMAND_NAME = "ping";
		NX_COMMAND_BYTES = "NX".getBytes();
		EX_COMMAND_BYTES = "EX".getBytes();
		PX_COMMAND_BYTES = "PX".getBytes();
		MATCH_COMMAND_BYTES = "MATCH".getBytes();
		COUNT_COMMAND_BYTES = "COUNT".getBytes();
	}
	
	protected RedisSupport() {
		this.logger = LoggerFactory.getLogger(this.getClass());
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
	
	public Serializer getDefaultSerializer() {
		return defaultSerializer;
	}

	public void setDefaultSerializer(Serializer defaultSerializer) {
		this.defaultSerializer = defaultSerializer;
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
	
	public boolean isKeepAtomic() {
		return keepAtomic;
	}

	public void setKeepAtomic(boolean keepAtomic) {
		this.keepAtomic = keepAtomic;
	}
	
	@Override
	protected void init() throws Exception {
		initializeDefaultDbIndex();
		initializeGlobalSerializers();
	}
	
	/**
	 * 初始化全局序列化器
	 * @author Daniele
	 */
	protected void initializeGlobalSerializers() {
		if (this.defaultSerializer == null)
			this.defaultSerializer = new JdkSerializer();
	}
	
	/**
	 * 初始化默认连接库索引
	 * @author Daniele 
	 * @throws Exception
	 */
	protected abstract void initializeDefaultDbIndex() throws Exception;
	
	/**
	 * 获取默认连接库的索引
	 * @author Daniele 
	 * @return
	 */
	protected int getDefaultDbIndex() {
		return this.defaultDbIndex;
	}
		
	/**
	 * 获取过期秒数
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	protected <K> byte[] serializeKey(String dbName, K key) {
		return selectKeySerializer(dbName).serialize(key);
	}
	
	/**
	 * 将指定库的多个键字节序列化到Byte类型的二维数组中
	 * @author Daniele 
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
	 * @author Daniele 
	 * @param dbName
	 * @param value
	 * @return
	 */
	protected <V> byte[] serializeValue(String dbName, V value) {
		return selectValueSerializer(dbName).serialize(value);
	}
	
	/**
	 * 将指定库的多个值字节序列化到Byte类型的二维数组中
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * @author Daniele 
	 * @param dbName
	 * @param hashKey
	 * @return
	 */
	protected <H> byte[] serializeHashKey(String dbName, H hashKey) {
		return selectHashKeySerializer(dbName).serialize(hashKey);
	}
	
	/**
	 * 将指定库的多个哈希键序列化到Byte类型的二维数组中
	 * @author Daniele 
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
	 * @author Daniele 
	 * @param dbName
	 * @param hashValue
	 * @return
	 */
	protected <V> byte[] serializeHashValue(String dbName, V hashValue) {
		return selectHashValueSerializer(dbName).serialize(hashValue);
	}
	
	/**
	 * 将指定库的哈希键值对进行序列化
	 * @author Daniele 
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
	 * @author Daniele 
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
		return propertyConverter.converte(key, keyType);		
	}
	
	/**
	 * 将指定库的多个键字节反序列化到集合中
	 * @author Daniele 
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
					K key = PropertyUtils.converte(propertyEditor, keySerializer.deserialize(keyByte));
					keys.add(key);
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
	 * 将指定库的多个键字节反序列化到列表中
	 * @author Daniele 
	 * @param dbName
	 * @param keyBytes
	 * @param keyType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <K> List<K> deserializeKeyBytes(String dbName, List<byte[]> keyBytes, Class<K> keyType) {
		if (CollectionUtils.isEmpty(keyBytes))
			return null;
		
		Serializer keySerializer = selectKeySerializer(dbName);
		List<K> keys = CollectionUtils.newArrayList(keyBytes.size());
		if (keySerializer.isTypedSerializer()) {
			TypedSerializer keyTypedSerializer = (TypedSerializer) keySerializer;
			for (byte[] keyByte : keyBytes) {
				keys.add(keyTypedSerializer.deserialize(keyByte, keyType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(keyType);
			if (propertyEditor != null) {
				for (byte[] keyByte : keyBytes) {
					K key = PropertyUtils.converte(propertyEditor, keySerializer.deserialize(keyByte));
					keys.add(key);
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
	 * @author Daniele 
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
		return propertyConverter.converte(value, valueType);
	}
	
	/**
	 * 将指定库的多个值字节反序列化到列表中
	 * @author Daniele 
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
		List<V> values = CollectionUtils.newArrayList(valueBytes.size());
		if (valueSerializer.isTypedSerializer()) {
			TypedSerializer valueTypedSerializer = (TypedSerializer) valueSerializer;
			for (byte[] valueByte : valueBytes) {
				values.add(valueTypedSerializer.deserialize(valueByte, valueType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(valueType);
			if (propertyEditor != null) {
				for (byte[] valueByte : valueBytes) {
					V value = PropertyUtils.converte(propertyEditor, valueSerializer.deserialize(valueByte));
					values.add(value);
				}
			} else {
				for (byte[] valueByte : valueBytes) {
					values.add((V) valueSerializer.deserialize(valueByte));
				}
			}
		}
		return values;
	}
	
	/**
	 * 将指定库的多个值字节反序列化到集合中
	 * @author Daniele 
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
		Set<V> values = CollectionUtils.newLinkedHashSet(valueBytes.size());
		if (valueSerializer.isTypedSerializer()) {
			TypedSerializer typedValueSerializer = (TypedSerializer) valueSerializer;
			for (byte[] valueByte : valueBytes) {
				values.add(typedValueSerializer.deserialize(valueByte, valueType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(valueType);
			if (propertyEditor != null) {
				for (byte[] valueByte : valueBytes) {
					V value = PropertyUtils.converte(propertyEditor, valueSerializer.deserialize(valueByte));
					values.add(value);
				}
			} else {
				for (byte[] valueByte : valueBytes) {
					values.add((V) valueSerializer.deserialize(valueByte));
				}
			}
		}
		return values;
	}
	
	/**
	 * 将指定库的多个域字节反序列化到集合中
	 * @author Daniele 
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
		Set<H> hashKeys = CollectionUtils.newLinkedHashSet(hashKeyBytes.size());
		
		if (hashKeySerializer.isTypedSerializer()) {
			TypedSerializer hashKeyTypedSerializer = (TypedSerializer) hashKeySerializer;
			for (byte[] hashKeyByte : hashKeyBytes) {
				hashKeys.add(hashKeyTypedSerializer.deserialize(hashKeyByte, hashKeyType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(hashKeyType);
			if (propertyEditor != null) {
				for (byte[] hashKeyByte : hashKeyBytes) {
					H hashKey = PropertyUtils.converte(propertyEditor, hashKeySerializer.deserialize(hashKeyByte));
					hashKeys.add(hashKey);
				}
			} else {
				for (byte[] hashKeyByte : hashKeyBytes) {
					hashKeys.add((H) hashKeySerializer.deserialize(hashKeyByte));
				}
			}
		}
		
		return hashKeys;
	}
	
	/**
	 * 将指定库的多个哈希键值对字节反序列化到映射集中
	 * @author Daniele 
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
		Map<H, V> hashKeyValues = MapUtils.newLinkedHashMap(hashKeyValueBytes.size());
		
		Set<Entry<byte[], byte[]>> entrySet = hashKeyValueBytes.entrySet();
		if (hashKeySerializer.isTypedSerializer() && hashValueSerializer.isTypedSerializer()) {
			TypedSerializer typedHashKeySerializer = (TypedSerializer) hashKeySerializer;
			TypedSerializer typedhashValueSerializer = (TypedSerializer) hashValueSerializer;
			for (Entry<byte[], byte[]> entry : entrySet) {
				hashKeyValues.put(typedHashKeySerializer.deserialize(entry.getKey(), hashKeyType),
						typedhashValueSerializer.deserialize(entry.getValue(), hashValueType));
			}
		} else if (hashKeySerializer.isTypedSerializer()) {
			TypedSerializer typedHashKeySerializer = (TypedSerializer) hashKeySerializer;
			PropertyEditor propertyEditor = propertyConverter.find(hashValueType);
			if (propertyEditor != null) {
				for (Entry<byte[], byte[]> entry : entrySet) {
					V hashValue = PropertyUtils.converte(propertyEditor, hashValueSerializer.deserialize(entry.getValue()));
					hashKeyValues.put(typedHashKeySerializer.deserialize(entry.getKey(), hashKeyType), hashValue);
				}
			} else {
				for (Entry<byte[], byte[]> entry : entrySet) {
					hashKeyValues.put(typedHashKeySerializer.deserialize(entry.getKey(), hashKeyType), 
							(V) hashValueSerializer.deserialize(entry.getValue()));
				}
			}
		} else if (hashValueSerializer.isTypedSerializer()) {
			TypedSerializer typedhashValueSerializer = (TypedSerializer) hashValueSerializer;
			PropertyEditor propertyEditor = propertyConverter.find(hashKeyType);
			if (propertyEditor != null) {
				for (Entry<byte[], byte[]> entry : entrySet) {
					H hashKey = PropertyUtils.converte(propertyEditor, hashKeySerializer.deserialize(entry.getKey()));
					hashKeyValues.put(hashKey, typedhashValueSerializer.deserialize(entry.getValue(), hashValueType));
				}
			} else {
				for (Entry<byte[], byte[]> entry : entrySet) {
					hashKeyValues.put((H) hashKeySerializer.deserialize(entry.getKey()), 
							typedhashValueSerializer.deserialize(entry.getValue(), hashValueType));
				}
			}
		} else {
			PropertyEditor hashKeyPropertyEditor = propertyConverter.find(hashKeyType);
			PropertyEditor hashValuePropertyEditor = propertyConverter.find(hashValueType);
			if (hashKeyPropertyEditor != null && hashValuePropertyEditor != null) {
				for (Entry<byte[], byte[]> entry : entrySet) {
					H hashKey = PropertyUtils.converte(hashKeyPropertyEditor, hashKeySerializer.deserialize(entry.getKey()));
					V hashValue = PropertyUtils.converte(hashValuePropertyEditor, hashValueSerializer.deserialize(entry.getValue()));
					hashKeyValues.put(hashKey, hashValue);
				}
			} else if (hashKeyPropertyEditor != null) {
				for (Entry<byte[], byte[]> entry : entrySet) {
					H hashKey = PropertyUtils.converte(hashKeyPropertyEditor, hashKeySerializer.deserialize(entry.getKey()));
					hashKeyValues.put(hashKey, (V) hashValueSerializer.deserialize(entry.getValue()));
				}
			} else if (hashValuePropertyEditor != null) {
				for (Entry<byte[], byte[]> entry : entrySet) {
					V hashValue = PropertyUtils.converte(hashValuePropertyEditor, hashValueSerializer.deserialize(entry.getValue()));
					hashKeyValues.put((H) hashKeySerializer.deserialize(entry.getKey()), hashValue);
				}
			} else {
				for (Entry<byte[], byte[]> entry : entrySet) {
					hashKeyValues.put((H) hashKeySerializer.deserialize(entry.getKey()), 
							(V) hashValueSerializer.deserialize(entry.getValue()));
				}
			}
		}
		return hashKeyValues;
	}
	
	/**
	 * 将指定库的哈希键值对列表反序列化到映射集中
	 * @author Daniele 
	 * @param dbName
	 * @param hashKeyValueBytes
	 * @param hashKeyType
	 * @param hashValueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <H, V> Map<H, V> deserializeHashKeyValueBytes(String dbName, List<byte[]> hashKeyValueBytes,
			Class<H> hashKeyType, Class<V> hashValueType) {
		
		if (CollectionUtils.isEmpty(hashKeyValueBytes))
			return null;
		
		AssertUtils.assertTrue(hashKeyValueBytes.size() % 2 == 0, "Hash key value list capacity must be even number");
		Map<H, V> hashKeyValues = MapUtils.newLinkedHashMap(hashKeyValueBytes.size() / 2);
		Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		
		if (hashKeySerializer.isTypedSerializer() && hashValueSerializer.isTypedSerializer()) {
			TypedSerializer typedHashKeySerializer = (TypedSerializer) hashKeySerializer;
			TypedSerializer typedhashValueSerializer = (TypedSerializer) hashValueSerializer;
			for (int i = 0; i < hashKeyValueBytes.size(); i++) {
				hashKeyValues.put(typedHashKeySerializer.deserialize(hashKeyValueBytes.get(i), hashKeyType),
						typedhashValueSerializer.deserialize(hashKeyValueBytes.get(++i), hashValueType));
			}
			
		} else if (hashKeySerializer.isTypedSerializer()) {
			TypedSerializer typedHashKeySerializer = (TypedSerializer) hashKeySerializer;
			PropertyEditor propertyEditor = propertyConverter.find(hashValueType);
			if (propertyEditor != null) {
				for (int i = 0; i < hashKeyValueBytes.size(); i++) {
					H hashKey = typedHashKeySerializer.deserialize(hashKeyValueBytes.get(i), hashKeyType);
					V hashValue = PropertyUtils.converte(propertyEditor, hashValueSerializer.deserialize(hashKeyValueBytes.get(++i)));
					hashKeyValues.put(hashKey, hashValue);
				}
			} else {
				for (int i = 0; i < hashKeyValueBytes.size(); i++) {
					hashKeyValues.put(typedHashKeySerializer.deserialize(hashKeyValueBytes.get(i), hashKeyType),
							(V) hashValueSerializer.deserialize(hashKeyValueBytes.get(++i)));
				}
			}
		} else if (hashValueSerializer.isTypedSerializer()) {
			TypedSerializer typedhashValueSerializer = (TypedSerializer) hashValueSerializer;
			PropertyEditor propertyEditor = propertyConverter.find(hashKeyType);
			if (propertyEditor != null) {
				for (int i = 0; i < hashKeyValueBytes.size(); i++) {
					H hashKey = PropertyUtils.converte(propertyEditor, hashKeySerializer.deserialize(hashKeyValueBytes.get(i)));
					hashKeyValues.put(hashKey, typedhashValueSerializer.deserialize(hashKeyValueBytes.get(++i), hashValueType));
				}
			} else {
				for (int i = 0; i < hashKeyValueBytes.size(); i++) {
					hashKeyValues.put((H) hashKeySerializer.deserialize(hashKeyValueBytes.get(i)), 
							typedhashValueSerializer.deserialize(hashKeyValueBytes.get(++i), hashValueType));
				}
			}
		} else {
			PropertyEditor hashKeyPropertyEditor = propertyConverter.find(hashKeyType);
			PropertyEditor hashValuePropertyEditor = propertyConverter.find(hashValueType);
			if (hashKeyPropertyEditor != null && hashValuePropertyEditor != null) {
				for (int i = 0; i < hashKeyValueBytes.size(); i++) {
					H hashKey = PropertyUtils.converte(hashKeyPropertyEditor, hashKeySerializer.deserialize(hashKeyValueBytes.get(i)));
					V hashValue = PropertyUtils.converte(hashValuePropertyEditor, hashValueSerializer.deserialize(hashKeyValueBytes.get(++i)));
					hashKeyValues.put(hashKey, hashValue);
				}
			} else if (hashKeyPropertyEditor != null) {
				for (int i = 0; i < hashKeyValueBytes.size(); i++) {
					H hashKey = PropertyUtils.converte(hashKeyPropertyEditor, hashKeySerializer.deserialize(hashKeyValueBytes.get(i)));
					hashKeyValues.put(hashKey, (V) hashValueSerializer.deserialize(hashKeyValueBytes.get(++i)));
				}
			} else if (hashValuePropertyEditor != null) {
				for (int i = 0; i < hashKeyValueBytes.size(); i++) {
					H hashKey = hashKeySerializer.deserialize(hashKeyValueBytes.get(i));
					V hashValue = PropertyUtils.converte(hashValuePropertyEditor, hashValueSerializer.deserialize(hashKeyValueBytes.get(++i)));
					hashKeyValues.put(hashKey, hashValue);
				}
			} else {
				for (int i = 0; i < hashKeyValueBytes.size(); i++) {
					hashKeyValues.put((H) hashKeySerializer.deserialize(hashKeyValueBytes.get(i)),
							(V) hashValueSerializer.deserialize(hashKeyValueBytes.get(++i)));
				}
			}
		}
		
		return hashKeyValues;
	}
	
	/**
	 * 将指定库的哈希值字节反序列后返回
	 * @author Daniele 
	 * @param dbName
	 * @param hashValueByte
	 * @param hashValueType
	 * @return
	 */
	protected <V> V deserializeHashValueByte(String dbName, byte[] hashValueByte, Class<V> hashValueType) {
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		if (hashValueSerializer.isTypedSerializer())
			return ((TypedSerializer) hashValueSerializer).deserialize(hashValueByte, hashValueType);
		
		V hashValue = hashValueSerializer.deserialize(hashValueByte);
		return propertyConverter.converte(hashValue, hashValueType);
	}
	
	/**
	 * 将指定库的多个哈希值字节反序列化到列表中
	 * @author Daniele 
	 * @param dbName
	 * @param hashValueBytes
	 * @param hashValueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> List<V> deserializeHashValueBytes(String dbName, List<byte[]> hashValueBytes, Class<V> hashValueType) {
		if (CollectionUtils.isEmpty(hashValueBytes))
			return null;
		
		Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		List<V> hashValues = CollectionUtils.newArrayList(hashValueBytes.size());
		
		if (hashValueSerializer.isTypedSerializer()) {
			TypedSerializer hashValueTypedSerializer = (TypedSerializer) hashValueSerializer;
			for (byte[] hashValueByte : hashValueBytes) {
				hashValues.add(hashValueTypedSerializer.deserialize(hashValueByte, hashValueType));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(hashValueType);
			if (propertyEditor != null) {
				for (byte[] hashValueByte : hashValueBytes) {
					V hashValue = PropertyUtils.converte(propertyEditor, hashValueSerializer.deserialize(hashValueByte));
					hashValues.add(hashValue);
				}
			} else {
				for (byte[] hashValueByte : hashValueBytes) {
					hashValues.add((V) hashValueSerializer.deserialize(hashValueByte));
				}
			}
		}
		return hashValues;
	}
	
	/**
	 * 将指定库的多个排名成员字节反序列化到映射集中
	 * @author Daniele 
	 * @param dbName
	 * @param scoreMemberBytes
	 * @param valueType
	 * @return
	 */
	protected <V> Map<V, Double> deserializeScoreMembers(String dbName, List<byte[]> scoreMemberBytes, Class<V> valueType) {
		if (CollectionUtils.isEmpty(scoreMemberBytes))
			return null;
		
		AssertUtils.assertTrue(scoreMemberBytes.size() % 2 == 0, "Score members capacity must be even number");
		Map<V, Double> scoreMembers = MapUtils.newLinkedHashMap(scoreMemberBytes.size() / 2);
		Serializer valueSerializer = selectValueSerializer(dbName);
		
		if (valueSerializer.isTypedSerializer()) {
			TypedSerializer typedValueSerializer = (TypedSerializer) valueSerializer;
			for (int i = 0; i < scoreMemberBytes.size(); i++) {
				scoreMembers.put(typedValueSerializer.deserialize(scoreMemberBytes.get(i), valueType),
						Double.parseDouble(stringSerializer.deserialize(scoreMemberBytes.get(++i))));
			}
		} else {
			PropertyEditor propertyEditor = propertyConverter.find(valueType);
			if (propertyEditor != null) {
				for (int i = 0; i < scoreMemberBytes.size(); i++) {
					V member = PropertyUtils.converte(propertyEditor, valueSerializer.deserialize(scoreMemberBytes.get(i)));
					scoreMembers.put(member, Double.parseDouble(stringSerializer.deserialize(scoreMemberBytes.get(++i))));
				}
			} else {
				for (int i = 0; i < scoreMemberBytes.size(); i++) {
					scoreMembers.put(valueSerializer.deserialize(scoreMemberBytes.get(i)),
							Double.parseDouble(stringSerializer.deserialize(scoreMemberBytes.get(++i))));
				}
			}
		}
		
		return scoreMembers;
	}
	
	/**
	 * 将已扫描得到的列表转换为键增量迭代结果
	 * @author Daniele 
	 * @param dbName
	 * @param scanned
	 * @param keyType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <K> IndexedScanResult<K> toScanResult(String dbName, List<Object> scanned, Class<K> keyType) {
		long cursorId = Long.valueOf(stringSerializer.deserialize((byte[]) scanned.get(0)));
		List<K> keys = deserializeKeyBytes(dbName, (List<byte[]>) scanned.get(1), keyType);
		return new ScanResult<K>(cursorId, keys);
	}
	
	/**
	 * 将已扫描得到的列表转换为集合增量迭代结果
	 * @author Daniele 
	 * @param dbName
	 * @param scanned
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> IndexedScanResult<V> toSScanResult(String dbName, List<Object> scanned, Class<V> valueType) {
		long cursorId = Long.valueOf(stringSerializer.deserialize((byte[]) scanned.get(0)));
		List<V> members = deserializeValueBytes(dbName, (List<byte[]>) scanned.get(1), valueType);
		return new SScanResult<V>(cursorId, members);
	}
	
	/**
	 * 将已扫描得到的列表转换为哈希增量迭代结果
	 * @author Daniele 
	 * @param dbName
	 * @param scanned
	 * @param hashKeyType
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <H, V> MappedScanResult<H, V> toHScanResult(String dbName, List<Object> scanned, 
			Class<H> hashKeyType, Class<V> valueType) {
		
		long cursorId = Long.valueOf(stringSerializer.deserialize((byte[]) scanned.get(0)));
		Map<H, V> mapped = deserializeHashKeyValueBytes(dbName, (List<byte[]>) scanned.get(1), hashKeyType, valueType);
		return new HScanResult<H, V>(cursorId, mapped);
	}
	
	/**
	 * 将已扫描得到的列表转换为有序集合增量迭代结果
	 * @author Daniele 
	 * @param dbName
	 * @param scanned
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> MappedScanResult<V, Double> toZScanResult(String dbName, List<Object> scanned, Class<V> valueType) {
		long cursorId = Long.valueOf(stringSerializer.deserialize((byte[]) scanned.get(0)));
		Map<V, Double> mapped = deserializeScoreMembers(dbName, (List<byte[]>) scanned.get(1), valueType);
		return new ZScanResult<V>(cursorId, mapped);
	}
	
	/**
	 * 将游标ID和增量迭代选项转换为命令行参数列表
	 * @author Daniele 
	 * @param cursorId
	 * @param option
	 * @return
	 */
	protected byte[][] toCommandArgs(long cursorId, ScanOption option) {
		List<byte[]> commandArgs = CollectionUtils.newArrayList();
		
		addSerializedCursorId(commandArgs, cursorId);
		addSerializedScanOption(commandArgs, option);
		
		return CollectionUtils.toArray(commandArgs, byte[].class);
	}
	
	/**
	 * 将键、游标ID和增量迭代选项转换为命令行参数列表
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @param option
	 * @return
	 */
	protected <K> byte[][] toCommandArgs(String dbName, K key, long cursorId, ScanOption option) {
		List<byte[]> commandArgs = CollectionUtils.newArrayList();
		
		addSerializedKey(commandArgs, dbName, key);
		addSerializedCursorId(commandArgs, cursorId);
		addSerializedScanOption(commandArgs, option);
		
		return CollectionUtils.toArray(commandArgs, byte[].class);
	}
	
	/**
	 * 将已序列化的键添加到命令行参数列表中
	 * @author Daniele 
	 * @param commandArgs
	 * @param dbName
	 * @param key
	 */
	private <K> void addSerializedKey(List<byte[]> commandArgs, String dbName, K key) {
		commandArgs.add(serializeKey(dbName, key));
	}
	
	/**
	 * 将已序列化的游标ID添加到命令行参数列表中
	 * @author Daniele 
	 * @param commandArgs
	 * @param cursorId
	 */
	private void addSerializedCursorId(List<byte[]> commandArgs, long cursorId) {
		commandArgs.add(stringSerializer.serialize(cursorId));
	}
	
	/**
	 * 将已序列化的增量迭代选项添加到命令行参数列表中
	 * @author Daniele 
	 * @param commandArgs
	 * @param option
	 */
	private void addSerializedScanOption(List<byte[]> commandArgs, ScanOption option) {
		if (option != null) {
			String pattern = option.getPattern();
			if (pattern != null) {
				commandArgs.add(MATCH_COMMAND_BYTES);
				commandArgs.add(stringSerializer.serialize(pattern));
			}
			
			Long count = option.getCount();
			if (count != null) {
				commandArgs.add(COUNT_COMMAND_BYTES);
				commandArgs.add(stringSerializer.serialize(count));
			}
		}
	}

}
