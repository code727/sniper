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
 * Create Date : 2015-3-27
 */

package org.sniper.nosql.redis.spring;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sniper.commons.KeyValuePair;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.serialization.Serializer;
import org.sniper.serialization.TypedSerializer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

/**
 * Spring Redis命令行数据访问实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class SpringRedisCommandsDaoImpl extends SpringRedisDaoSupport implements SpringRedisCommandsDao {
	
	@Override
	public <K> Set<K> keys() {
		return keys((String) null);
	}
	
	@Override
	public <K> Set<K> keys(Class<K> keyType) {
		return keys(null, keyType);
	}
	
	@Override
	public <K> Set<K> keys(String dbName) {
		return keys(dbName, null);
	}
	
	@Override
	public <K> Set<K> keys(String dbName, Class<K> keyType) {
		return keysByPattern(dbName, null, keyType);
	}

	@Override
	public <K> Set<K> keysByPattern(String pattern) {
		return keysByPattern(null, pattern);
	}
	
	@Override
	public <K> Set<K> keysByPattern(String pattern, Class<K> keyType) {
		return keysByPattern(null, pattern, keyType);
	}
	
	@Override
	public <K> Set<K> keysByPattern(String dbName, String pattern) {
		return keysByPattern(dbName, pattern, null);
	}
	
	@Override
	public <K> Set<K> keysByPattern(final String dbName, final String pattern, final Class<K> keyType) {
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<K>>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public Set<K> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> keyBytes = connection.keys(stringSerializer.serialize(StringUtils.isNotEmpty(pattern) ? pattern : StringUtils.ANY));
				Set<K> keys = CollectionUtils.newLinkedHashSet();
				if (keySerializer.isTypedSerializer()) {
					TypedSerializer keyTypedSerializer = (TypedSerializer) keySerializer;
					for (byte[] key : keyBytes) {
						keys.add(keyTypedSerializer.deserialize(key, keyType));
					}
				} else {
					for (byte[] key : keyBytes) {
						keys.add((K) keySerializer.deserialize(key));
					}
				}
				return keys;
			}
		});
		
	}
	
	@Override
	public <K> Long del(K key) {
		return del(null, key);
	}
	
	@Override
	public <K> Long del(String dbName, K key) {
		return del(dbName, new Object[] { key });
	}
	
	@Override
	public <K> Long del(Collection<K> keys) {
		return del(null, keys);
	}

	@Override
	public <K> Long del(String dbName, Collection<K> keys) {
		return del(dbName, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long del(K[] keys) {
		return del(null, keys);
	}
	
	@Override
	public <K> Long del(final String dbName, final K[] keys) {
		
		if (ArrayUtils.isEmpty(keys))
			return 0L;
		
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.del(serializeKeysToArray(dbName, keys));
			}
		});
	}

	@Override
	public <K> Boolean exists(K key) {
		return exists(null, key);
	}

	@Override
	public <K> Boolean exists(final String dbName, final K key) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.exists(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K> List<KeyValuePair<K, Boolean>> exists(K[] keys) {
		return exists(null, keys);
	}

	@Override
	public <K> List<KeyValuePair<K, Boolean>> exists(String dbName, K[] keys) {
		return exists(dbName, ArrayUtils.toList(keys));
	}

	@Override
	public <K> List<KeyValuePair<K, Boolean>> exists(Collection<K> keys) {
		return exists(null, keys);
	}

	@Override
	public <K> List<KeyValuePair<K, Boolean>> exists(final String dbName, final Collection<K> keys) {
		if (CollectionUtils.isEmpty(keys))
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<KeyValuePair<K, Boolean>>>() {

			@Override
			public List<KeyValuePair<K, Boolean>> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				
				List<KeyValuePair<K, Boolean>> list = CollectionUtils.newArrayList();
				for (K key : keys) {
					list.add(new KeyValuePair<K, Boolean>(key, connection.exists(keySerializer.serialize(key))));
				}
				
				return list;
			}
		});
	}

	@Override
	public <K> Boolean expire(K key, long seconds) {
		return expire(null, seconds);
	}

	@Override
	public <K> Boolean expire(final String dbName, final K key, final long seconds) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.expire(keySerializer.serialize(key), seconds);
			}
		});
	}
	
	@Override
	public <K> Boolean expireAt(K key, long timestamp) {
		return expireAt(null, key, timestamp);
	}

	@Override
	public <K> Boolean expireAt(final String dbName, final K key, final long timestamp) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.expireAt(keySerializer.serialize(key), timestamp);
			}
		});
	}
	
	@Override
	public <K> Boolean expireAt(K key, Date date) {
		return expireAt(null, key, date);
	}

	@Override
	public <K> Boolean expireAt(String dbName, K key, Date date) {
		return expireAt(dbName, key, DateUtils.dateToUnixTimestamp(date));
	}
	
	@Override
	public <K> Boolean move(K key, int targetIndex) {
		return move(null, key, targetIndex);
	}

	@Override
	public <K> Boolean move(final String dbName, final K key, final int targetIndex) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.move(keySerializer.serialize(key), targetIndex);
			}
		});
	}

	@Override
	public <K> Long ttl(K key) {
		return ttl(null, key);
	}

	@Override
	public <K> Long ttl(final String dbName, final K key) {
		if (key == null)
			return -2L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.ttl(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K, V> List<V> sort(K key, SortParameters params) {
		return sort(key, params, null);
	}
	
	public <K, V> List<V> sort(K key, SortParameters params, Class<V> valueType) {
		return sort2(null, key, params, valueType);
	}

	@Override
	public <K, V> List<V> sort2(String dbName, K key, SortParameters params) {
		return sort2(null, key, params, null);
	}
	
	public <K, V> List<V> sort2(final String dbName, final K key, final SortParameters params, final Class<V> valueType) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort]");
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				List<byte[]> byteList = connection.sort(keySerializer.serialize(key), params);
				List<V> values = CollectionUtils.newArrayList();
				if (valueSerializer.isTypedSerializer()) {
					TypedSerializer valueTypedSerializer = (TypedSerializer) valueSerializer;
					for (byte[] bytes : byteList) {
						values.add(valueTypedSerializer.deserialize(bytes, valueType));
					}
				} else {
					for (byte[] bytes : byteList) {
						values.add((V) valueSerializer.deserialize(bytes));
					}
				}
				return values;
			}
		});
	}

	@Override
	public <K, V> Long sortCount(K key, SortParameters params, K targetKey) {
		return sortCount(null, key, params, targetKey);
	}

	@Override
	public <K, V> Long sortCount(final String dbName, final K key, final SortParameters params, final K targetKey) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort]");
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort]");
		AssertUtils.assertNotNull(targetKey, "Target key can not be null of command [sort]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sort(keySerializer.serialize(key), params, keySerializer.serialize(targetKey));
			}
		});
	}

	@Override
	public <K, V> List<V> sortResult(K key, SortParameters params, K targetKey) {
		return sortResult(key, params, targetKey, null);
	}
	
	@Override
	public <K, V> List<V> sortResult(K key, SortParameters params, K targetKey, Class<V> valueType) {
		return sortResult2(null, key, params, targetKey, valueType);
	}

	@Override
	public <K, V> List<V> sortResult2(String dbName, K key, SortParameters params, K targetKey) {
		return sortResult2(dbName, key, params, targetKey, null);
	}
	
	@Override
	public <K, V> List<V> sortResult2(final String dbName, final K key, final SortParameters params, final K targetKey, final Class<V> valueType) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort]");	
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort]");
		AssertUtils.assertNotNull(targetKey, "Target key can not be null of command [sort]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] targetKeyByte = keySerializer.serialize(targetKey);
				Long count = connection.sort(keySerializer.serialize(key), params, targetKeyByte);
				if (count != null && count > 0) {
					DataType dataType = connection.type(targetKeyByte);
					return listByDataType(dataType, connection, dbName, targetKeyByte, valueType);
				}
				return null;
			}
		});
	}

	@Override
	public <K> DataType type(K key) {
		return type(null, key);
	}

	@Override
	public <K> DataType type(final String dbName, final K key) {
		if (key == null)
			return DataType.NONE;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<DataType>() {

			@Override
			public DataType doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.type(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <V> Set<V> values() {
		return values((String) null);
	}
	
	@Override
	public <V> Set<V> values(Class<V> valueType) {
		return values(null, valueType);
	}
	
	@Override
	public <V> Set<V> values(String dbName) {
		return values(dbName, null);
	}
	
	@Override
	public <V> Set<V> values(String dbName, Class<V> valueType) {
		return valuesByPattern(dbName, null, valueType);
	}

	@Override
	public <V> Set<V> valuesByPattern(String pattern) {
		return valuesByPattern(null, pattern);
	}
	
	@Override
	public <V> Set<V> valuesByPattern(String pattern, Class<V> valueType) {
		return valuesByPattern(null, pattern, valueType);
	}
	
	@Override
	public <V> Set<V> valuesByPattern(String dbName, String pattern) {
		return valuesByPattern(dbName, pattern, null);
	}

	@Override
	public <V> Set<V> valuesByPattern(String dbName, final String pattern, final Class<V> valueType) {
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				// 获取匹配模式的键
				Set<byte[]> keySet = connection.keys(stringSerializer.serialize(StringUtils.isNotEmpty(pattern) ? pattern : StringUtils.ANY));
				Set<V> set = CollectionUtils.newHashSet();
				if (valueSerializer.isTypedSerializer()) {
					TypedSerializer typedValueSerializer = (TypedSerializer) valueSerializer;
					for (byte[] key : keySet) {
						set.add(typedValueSerializer.deserialize(connection.get(key), valueType));
					}
				} else {
					for (byte[] key : keySet) {
						set.add((V) valueSerializer.deserialize(connection.get(key)));
					}
				}
				return set;
			}
		});
	}
	
	@Override
	public <K, V> void set(K key, V value) {
		set(key, value, 0);
	}
	
	@Override
	public <K, V> void set(K key, V value, long expireSeconds) {
		set2(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> void set2(String dbName, K key, V value) {
		set2(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> void set2(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [set]");
		AssertUtils.assertNotNull(value, "Value can not be null of command [set]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				connection.set(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, V> Boolean setNX(K key, V value) {
		return setNX(key, value, 0);
	}
	
	@Override
	public <K, V> Boolean setNX(K key, V value, long expireSeconds) {
		return setNX2(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Boolean setNX2(String dbName, K key, V value) {
		return setNX2(dbName, key, value, 0);
	}	
	
	@Override
	public <K, V> Boolean setNX2(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setNX]");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setNX]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				Boolean result = connection.setNX(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> void setEx(K key, V value) {
		setEx(key, 0, value);
	}

	@Override
	public <K, V> void setEx(K key, long seconds, V value) {
		setEx2(null, key, seconds, value);
	}
	
	@Override
	public <K, V> void setEx2(String dbName, K key, V value) {
		setEx2(dbName, key, getExpireSeconds(dbName), value);	
	}

	@Override
	public <K, V> void setEx2(final String dbName, final K key, final long seconds, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setEx]");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setEx]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				if (seconds > 0)
					connection.setEx(keySerializer.serialize(key), seconds, valueSerializer.serialize(value));
				else
					// 秒数小于等于0时永不过期
					connection.set(keySerializer.serialize(key), valueSerializer.serialize(value));
				
				return null;
			}
		});
	}
	
	@Override
	public <K, V> void mSet(Map<K, V> kValues) {
		mSet(null, kValues);
	}
	
	@Override
	public <K, V> void mSet(Map<K, V> kValues, long expireSeconds) {
		mSet(null, kValues, expireSeconds);
	}

	@Override
	public <K, V> void mSet(String dbName, Map<K, V> kValues) {
		mSet(dbName, kValues, 0);
	}
	
	@Override
	public <K, V> void mSet(final String dbName, final Map<K, V> kValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(kValues, "Key-value map can not be empty of command [mSet");
		
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbName, kValues);
				RedisRepository repository = select(connection, dbName);
				connection.mSet(byteMap);
				setExpireTime(connection, repository, byteMap.keySet(), expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, V> void mSetNX(Map<K, V> kValues) {
		mSetNX(null, kValues);
	}
	
	@Override
	public <K, V> void mSetNX(Map<K, V> kValues, long expireSeconds) {
		mSetNX(null, kValues, expireSeconds);
	}

	@Override
	public <K, V> void mSetNX(String dbName, Map<K, V> kValues) {
		mSetNX(dbName, kValues, 0);
	}
	
	@Override
	public <K, V> void mSetNX(final String dbName, final Map<K, V> kValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(kValues, "Key-value map can not be empty of command [mSetNX].");
		
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbName, kValues);
				RedisRepository repository = select(connection, dbName);
				connection.mSetNX(byteMap);
				setExpireTime(connection, repository, byteMap.keySet(), expireSeconds);
				return null;
			}
		});
	}
	
	@Override
	public <K, V> void setRange(K key, long offset, V value) {
		setRange(key, offset, value, 0);
	}
	
	@Override
	public <K, V> void setRange(K key, long offset, V value, long expireSeconds) {
		setRange2(null, key, offset, value, expireSeconds);
	}

	@Override
	public <K, V> void setRange2(String dbName, K key, long offset, V value) {
		setRange2(dbName, key, offset, value, 0);
	}
	
	@Override
	public <K, V> void setRange2(final String dbName, final K key, final long offset, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setRange]");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setRange]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				connection.setRange(keyByte, valueSerializer.serialize(value), offset);
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, V> Long append(K key, V value) {
		return append(key, value, 0);
	}
	
	@Override
	public <K, V> Long append(K key, V value, long expireSeconds) {
		return append2(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long append2(String dbName, final K key, V value) {
		return append2(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long append2(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [append]");
		AssertUtils.assertNotNull(key, "Value can not be null of command [append]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				Long length = connection.append(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return length;
			}
		});
	}

	@Override
	public <K, V> V get(K key) {
		return get(key, null);
	}
	
	@Override
	public <K, V> V get(K key, Class<V> valueType) {
		return get2(null, key, valueType);
	}

	@Override
	public <K, V> V get2(String dbName, K key) {
		return get2(dbName, key, null);
	}
	
	@Override
	public <K, V> V get2(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] keyByte = keySerializer.serialize(key);
				return deserializeValueByte(dbName, connection.get(keyByte), valueType);
			}
		});
	}

	@Override
	public <K, V> V getRange(K key, long begin, long end) {
		return getRange(key, begin, end, null);
	}
	
	@Override
	public <K, V> V getRange(K key, long begin, long end, Class<V> valueType) {
		return getRange(null, key, begin, end, valueType);
	}

	@Override
	public <K, V> V getRange(String dbName, K key, long begin, long end) {
		return getRange(dbName, key, begin, end, null);
	}
	
	@Override
	public <K, V> V getRange(final String dbName, final K key, final long begin, final long end, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] keyByte = keySerializer.serialize(key);
				return deserializeValueByte(dbName, connection.getRange(keyByte, begin, end), valueType);
			}
		});
	}

	@Override
	public <K, V, O> O getSet(K key, V value) {
		return getSet(key, value, null);
	}
	
	@Override
	public <K, V, O> O getSet(K key, V value, Class<O> oldValueType) {
		return getSet2(null, key, value, oldValueType);
	}
	
	@Override
	public <K, V, O> O getSet(K key, V value, long expireSeconds) {
		return getSet(key, value, expireSeconds, null);
	}
	
	@Override
	public <K, V, O> O getSet(K key, V value, long expireSeconds, Class<O> oldValueType) {
		return getSet2(null, key, value, expireSeconds, oldValueType);
	}

	@Override
	public <K, V, O> O getSet2(String dbName, K key, V value) {
		return getSet2(dbName, key, value, null);
	}
	
	@Override
	public <K, V, O> O getSet2(String dbName, K key, V value, Class<O> oldValueType) {
		return getSet2(dbName, key, value, 0, oldValueType);
	}
	
	@Override
	public <K, V, O> O getSet2(String dbName, K key, V value, long expireSeconds) {
		return getSet2(dbName, key, value, expireSeconds, null);
	}
	
	@Override
	public <K, V, O> O getSet2(final String dbName, final K key, final V value, final long expireSeconds, final Class<O> oldValueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<O>() {

			@Override
			public O doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] keyByte = keySerializer.serialize(key);
				byte[] oldValueByte = connection.getSet(keyByte, valueSerializer.serialize(value));
				
				O result = null;
				if (valueSerializer.isTypedSerializer()) 
					result = ((TypedSerializer)valueSerializer).deserialize(oldValueByte, oldValueType);
				else
					result = valueSerializer.deserialize(oldValueByte);
				
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> List<V> mGet(Collection<K> keys) {
		return mGet(keys, null);
	}
	
	@Override
	public <K, V> List<V> mGet(Collection<K> keys, Class<V> valueType) {
		return mGet(null, keys, valueType);
	}

	@Override
	public <K, V> List<V> mGet(String dbName, Collection<K> keys) {
		return mGet(dbName, keys, null);
	}
	
	@Override
	public <K, V> List<V> mGet(String dbName, Collection<K> keys, Class<V> valueType) {
		return mGet(dbName, CollectionUtils.toObjectArray(keys), valueType);
	}
	
	@Override
	public <K, V> List<V> mGet(K[] keys) {
		return mGet(keys, null);
	}
	
	@Override
	public <K, V> List<V> mGet(K[] keys, Class<V> valueType) {
		return mGet(null, keys, valueType);
	}
	
	@Override
	public <K, V> List<V> mGet(String dbName, K[] keys) {
		return mGet(dbName, keys, null);
	}
	
	@Override
	public <K, V> List<V> mGet(final String dbName, final K[] keys, final Class<V> valueType) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				List<byte[]> valueBytes = connection.mGet(serializeKeysToArray(dbName, keys));
				return deserializeValueByteToList(dbName, valueBytes, valueType);
			}
		});
	}

	@Override
	public <K> Long strLen(K key) {
		return strLen(null, key);
	}

	@Override
	public <K> Long strLen(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.strLen(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K> Long decr(K key) {
		return decr(null, key);
	}

	@Override
	public <K> Long decr(final String dbName, final K key) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [decr].");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.decr(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Long decrBy(K key, long value) {
		return decrBy(null, key, value);
	}

	@Override
	public <K> Long decrBy(final String dbName, final K key, final long value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [decrBy].");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.decrBy(keySerializer.serialize(key), value);
			}
		});
	}

	@Override
	public <K> Long incr(K key) {
		return incr(null, key);
	}

	@Override
	public <K> Long incr(final String dbName, final K key) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [incr].");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.incr(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Long incrBy(K key, long value) {
		return incrBy(null, key, value);
	}

	@Override
	public <K> Long incrBy(final String dbName, final K key, final long value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [incrBy].");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.incrBy(keySerializer.serialize(key), value);
			}
		});
	}
	
	@Override
	public <K, F, V> Boolean hSet(K key, F field, V value) {
		return hSet(key, field, value, 0);
	}
	
	@Override
	public <K, F, V> Boolean hSet(K key, F field, V value, long expireSeconds) {
		return hSet2(null, key, field, value, expireSeconds);
	}

	@Override
	public <K, F, V> Boolean hSet2(String dbName, K key, F field, V value) {
		return hSet2(dbName, key, field, value, 0);
	}
	
	@Override
	public <K, F, V> Boolean hSet2(final String dbName, final K key,
			final F field, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hSet]");
		AssertUtils.assertNotNull(field, "Field can not be null of command [hSet]");
		AssertUtils.assertNotNull(value, "Value can not be null of command [hSet]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer fieldKeySerializer = selectHashKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				Boolean result = connection.hSet(keyByte, fieldKeySerializer.serialize(field),
						valueSerializer.serialize(value));
				
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}
	
	@Override
	public <K, F, V> Boolean hSetNX(K key, F field, V value) {
		return hSetNX(key, field, value, 0);
	}
	
	@Override
	public <K, F, V> Boolean hSetNX(K key, F field, V value, long expireSeconds) {
		return hSetNX2(null, key, field, value, expireSeconds);
	}

	@Override
	public <K, F, V> Boolean hSetNX2(String dbName, K key, F field, V value) {
		return hSetNX2(null, key, field, value, 0);
	}
	
	@Override
	public <K, F, V> Boolean hSetNX2(final String dbName, final K key,
			final F field, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hSetNX].");
		AssertUtils.assertNotNull(field, "Field can not be null of command [hSetNX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [hSetNX].");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer fieldKeySerializer = selectHashKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				Boolean result = connection.hSetNX(keyByte, fieldKeySerializer.serialize(field),
						valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}

	@Override
	public <K, F, V> void hMSet(K key, Map<F, V> fValues) {
		hMSet(null, key, fValues);
	}
	
	@Override
	public <K, F, V> void hMSet(K key, Map<F, V> fValues, long expireSeconds) {
		hMSet(null, key, fValues, expireSeconds);
	}

	@Override
	public <K, F, V> void hMSet(String dbName, K key, Map<F, V> fValues) {
		hMSet(dbName, key, fValues, 0);
	}
	
	@Override
	public <K, F, V> void hMSet(final String dbName, final K key,
			final Map<F, V> fValues, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hMSet]");
		AssertUtils.assertNotEmpty(fValues, "Field-value map can not be empty of command [hMSet]");
				
		final Serializer keySerializer = selectKeySerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				connection.hMSet(keyByte, serializeFiledValueToByteMap(dbName, fValues));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, F> Long hDel(K key, F filed) {
		return hDel(null, key, filed);
	}

	@Override
	public <K, F> Long hDel(String dbName, K key, F filed) {
		if (key == null || filed == null)
			return 0L;
		
		return hDel(dbName, key, new Object[] { filed });
	}

	@Override
	public <K, F> Long hDel(K key, F[] fileds) {
		return null;
	}

	@Override
	public <K, F> Long hDel(final String dbName, final K key, final F[] fileds) {
		if (key == null || ArrayUtils.isEmpty(fileds))
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return NumberUtils.safeLong(connection.hDel(keySerializer.serialize(key), 
						serializeFiledsToArray(dbName, fileds)));
			}
		});
	}

	@Override
	public <K, F> Long hDel(K key, Collection<F> fileds) {
		return hDel(null, key, fileds);
	}

	@Override
	public <K, F> Long hDel(String dbName, K key, Collection<F> fileds) {
		return hDel(dbName, key, CollectionUtils.toObjectArray(fileds));
	}

	@Override
	public <K, F> Boolean hExists(K key, F filed) {
		return hExists(null, key, filed);
	}

	@Override
	public <K, F> Boolean hExists(final String dbName, final K key, final F filed) {
		if (key == null || filed == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hExists(keySerializer.serialize(key), hashKeySerializer.serialize(filed));
			}
		});
	}

	@Override
	public <K, F, V> V hGet(K key, F filed) {
		return hGet(key, filed, null);
	}
	
	@Override
	public <K, F, V> V hGet(K key, F filed, Class<V> valueType) {
		return hGet2(null, key, filed, valueType);
	}

	@Override
	public <K, F, V> V hGet2(String dbName, K key, F filed) {
		return hGet2(dbName, key, filed, null);
	}
	
	@Override
	public <K, F, V> V hGet2(final String dbName, final K key, final F filed, final Class<V> valueType) {
		if (key == null || filed == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] hashValueByte = connection.hGet(keySerializer.serialize(key), hashKeySerializer.serialize(filed));
				return deserializeHashValueByte(dbName, hashValueByte, valueType);
			} 
		});
	}

	@Override
	public <K, F, V> Map<F, V> hGetAll(K key) {
		return hGetAll(key, null);
	}
	
	@Override
	public <K, F, V> Map<F, V> hGetAll(K key, Class<V> valueType) {
		return hGetAll(key, null, valueType);
	}
	
	@Override
	public <K, F, V> Map<F, V> hGetAll(K key, Class<F> fieldType, Class<V> valueType) {
		return hGetAll2(null, key, fieldType, valueType);
	}

	@Override
	public <K, F, V> Map<F, V> hGetAll2(String dbName, K key) {
		return hGetAll2(dbName, key, null);
	}
	
	@Override
	public <K, F, V> Map<F, V> hGetAll2(String dbName, K key, Class<V> valueType) {
		return hGetAll2(dbName, key, null, valueType);
	}
	
	@Override
	public <K, F, V> Map<F, V> hGetAll2(final String dbName, final K key, final Class<F> fieldType, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Map<F, V>>() {

			@Override
			public Map<F, V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Map<byte[], byte[]> fieldValueBytes = connection.hGetAll(keySerializer.serialize(key));
				return deserializeFiledValueBytesToMap(dbName, fieldValueBytes, fieldType, valueType);
			}
		});
	}

	@Override
	public <K, F> Set<F> hKeys(K key) {
		return hKeys(key, null);
	}
	
	@Override
	public <K, F> Set<F> hKeys(K key, Class<F> fieldType) {
		return hKeys2(null, key, fieldType);
	}

	@Override
	public <K, F> Set<F> hKeys2(String dbName, K key) {
		return hKeys2(dbName, key, null);
	}
	
	@Override
	public <K, F> Set<F> hKeys2(final String dbName, final K key, final Class<F> fieldType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<F>>() {

			@Override
			public Set<F> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> fieldBytes = connection.hKeys(keySerializer.serialize(key));
				return deserializeFiledBytesToSet(dbName, fieldBytes, fieldType);
			}
		});
	}

	@Override
	public <K> Long hLen(K key) {
		return hLen(null, key);
	}

	@Override
	public <K> Long hLen(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hLen(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K, F, V> List<V> hMGet(K key, Collection<F> fields) {
		return hMGet(key, fields, null);
	}
	
	@Override
	public <K, F, V> List<V> hMGet(K key, Collection<F> fields, Class<V> valueType) {
		return hMGet(null, key, fields, valueType);
	}

	@Override
	public <K, F, V> List<V> hMGet(String dbName, K key, Collection<F> fields) {
		return hMGet(dbName, key, fields, null);
	}
	
	@Override
	public <K, F, V> List<V> hMGet(String dbName, K key, Collection<F> fields, Class<V> valueType) {
		return hMGet(dbName, key, CollectionUtils.toObjectArray(fields), valueType);
	}

	@Override
	public <K, F, V> List<V> hMGet(K key, F[] fields) {
		return hMGet(key, fields, null);
	}
	
	@Override
	public <K, F, V> List<V> hMGet(K key, F[] fields, Class<V> valueType) {
		return hMGet(null, key, fields, valueType);
	}

	@Override
	public <K, F, V> List<V> hMGet(String dbName, K key, F[] fields) {
		return hMGet(dbName, key, fields, null);
	}
	
	@Override
	public <K, F, V> List<V> hMGet(final String dbName, final K key, final F[] fields, final Class<V> valueType) {
		if (key == null || ArrayUtils.isEmpty(fields))
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				List<byte[]> haseValueBytes = connection.hMGet(keySerializer.serialize(key), serializeFiledsToArray(dbName, fields));
				return deserializeHashValueBytesToList(dbName, haseValueBytes, valueType);
			}
		});
	}

	@Override
	public <K, V> List<V> hVals(K key) {
		return hVals(key, null);
	}
	
	@Override
	public <K, V> List<V> hVals(K key, Class<V> valueType) {
		return hVals2(null, key, valueType);
	}

	@Override
	public <K, V> List<V> hVals2(String dbName, K key) {
		return hVals2(dbName, key, null);
	}
	
	@Override
	public <K, V> List<V> hVals2(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				List<byte[]> hashValueBytes = connection.hVals(keySerializer.serialize(key));
				return deserializeHashValueBytesToList(dbName, hashValueBytes, valueType);
			}
		});
	}
	
	@Override
	public <K, V> Long lInsert(K key, Position where, V pivot, V value) {
		return lInsert(key, where, pivot, value, 0);
	}
	
	@Override
	public <K, V> Long lInsert(K key, Position where, V pivot, V value, long expireSeconds) {
		return lInsert2(null, key, where, pivot, value, expireSeconds);
	}

	@Override
	public <K, V> Long lInsert2(String dbName, K key, Position where, V pivot, V value) {
		return lInsert2(dbName, key, where, pivot, value, 0);
	}
	
	@Override
	public <K, V> Long lInsert2(final String dbName, final K key, final Position where, final V pivot,
			final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lInsert]");
		AssertUtils.assertNotNull(where, "Insert postion can not be null of command [lInsert]");
		AssertUtils.assertNotNull(pivot, "Postion value can not be null of command [lInsert]");
		AssertUtils.assertNotNull(value, "Insert value can not be null of command [lInsert]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				long count = NumberUtils.safeLong(connection.lInsert(keyByte, where, 
						valueSerializer.serialize(pivot), valueSerializer.serialize(value)));
				if (count > 0) 
					setExpireTime(connection, repository, keyByte, expireSeconds);
					
				return count;
			}
		});
	}
	
	@Override
	public <K, V> void lSet(K key, long posttion, V value) {
		lSet(key, posttion, value, 0);
	}
	
	@Override
	public <K, V> void lSet(K key, long posttion, V value, long expireSeconds) {
		lSet2(null, key, posttion, value, expireSeconds);
	}

	@Override
	public <K, V> void lSet2(String dbName, K key, long posttion, V value) {
		lSet2(dbName, key, posttion, value, 0);
	}
	
	@Override
	public <K, V> void lSet2(final String dbName, final K key,
			final long posttion, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lSet]");
		AssertUtils.assertNotNull(value, "Value can not be null of command [lSet]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				connection.lSet(keyByte, posttion, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, V> Long lPush(K key, V value) {
		return lPush(key, value, 0);
	}
	
	@Override
	public <K, V> Long lPush(K key, V value, long expireSeconds) {
		return lPush2(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long lPush2(String dbName, K key, V value) {
		return lPush2(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long lPush2(String dbName, K key, V value, long expireSeconds) {
		AssertUtils.assertNotNull(value, "Value can not be null of command [lPush]");
		return lPush(dbName, key, new Object[] { value }, expireSeconds);
	}
	
	@Override
	public <K, V> Long lPush(K key, V[] values) {
		return lPush(null, key, values);
	}
	
	@Override
	public <K, V> Long lPush(K key, V[] values, long expireSeconds) {
		return lPush(null, key, values, expireSeconds);
	}

	@Override
	public <K, V> Long lPush(String dbName, K key, V[] values) {
		return lPush(dbName, key, values, 0);
	}
	
	@Override
	public <K, V> Long lPush(final String dbName, final K key, final V[] values, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lPush]");
		AssertUtils.assertNotEmpty(values, "Values can not be empty of command [lPush]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] keyByte = keySerializer.serialize(key);
				
				long count = NumberUtils.safeLong(connection.lPush(keyByte, serializeValuesToArray(dbName, values)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> Long lPush(K key, Collection<V> values) {
		return lPush(null, key, values); 
	}
	
	@Override
	public <K, V> Long lPush(K key, Collection<V> values, long expireSeconds) {
		return lPush(null, key, values, expireSeconds); 
	}

	@Override
	public <K, V> Long lPush(String dbName, K key, Collection<V> values) {
		return lPush(dbName, key, CollectionUtils.toObjectArray(values), 0);
	}
	
	@Override
	public <K, V> Long lPush(String dbName, K key, Collection<V> values, long expireSeconds) {
		return lPush(dbName, key, CollectionUtils.toObjectArray(values), expireSeconds);
	}

	@Override
	public <K, V> Long lPushX(K key, V value) {
		return lPushX(key, value, 0);
	}
	
	@Override
	public <K, V> Long lPushX(K key, V value, long expireSeconds) {
		return lPushX2(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long lPushX2(String dbName, K key, V value) {
		return lPushX2(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long lPushX2(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lPushX]");
		AssertUtils.assertNotNull(value, "Value can not be null of command [lPushX]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				long count = NumberUtils.safeLong(connection.lPushX(keyByte, valueSerializer.serialize(value)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> V lIndex(K key, long index) {
		return lIndex(key, index, null);
	}
	
	@Override
	public <K, V> V lIndex(K key, long index, Class<V> valueType) {
		return lIndex(null, key, index, valueType);
	}

	@Override
	public <K, V> V lIndex(String dbName, K key, long index) {
		return lIndex(dbName, key, index, null);
	}
	
	@Override
	public <K, V> V lIndex(final String dbName, final K key, final long index, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] valueByte = connection.lIndex(keySerializer.serialize(key), index);
				return deserializeValueByte(dbName, valueByte, valueType);
			}
		});
	}

	@Override
	public <K> Long lLen(K key) {
		return lLen(null, key);
	}

	@Override
	public <K> Long lLen(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.lLen(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K, V> V lPop(K key) {
		return lPop(key, null);
	}
	
	@Override
	public <K, V> V lPop(K key, Class<V> valueType) {
		return lPop2(null, key, valueType);
	}

	@Override
	public <K, V> V lPop2(String dbName, K key) {
		return lPop2(dbName, key, null);
	}
	
	@Override
	public <K, V> V lPop2(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] valueByte = connection.lPop(keySerializer.serialize(key));
				return deserializeValueByte(dbName, valueByte, valueType);
			}
		});
	}

	@Override
	public <K, V> List<V> lRange(K key, long begin, long end) {
		return lRange(key, begin, end, null);
	}
	
	@Override
	public <K, V> List<V> lRange(K key, long begin, long end, Class<V> valueType) {
		return lRange(null, key, begin, end, valueType);
	}

	@Override
	public <K, V> List<V> lRange(String dbName, K key, long begin, long end) {
		return lRange(dbName, key, begin, end, null);
	}
	
	@Override
	public <K, V> List<V> lRange(final String dbName, final K key, final long begin, final long end, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				List<byte[]> valueBytes = connection.lRange(keySerializer.serialize(key), begin, end);
				return deserializeValueByteToList(dbName, valueBytes, valueType);
			}
		});
	}
	
	@Override
	public <K, V> List<V> lRangeAll(K key) {
		return lRangeAll(key, null);
	}
	
	@Override
	public <K, V> List<V> lRangeAll(K key, Class<V> valueType) {
		return lRangeAll2(null, key, valueType);
	}
	
	@Override
	public <K, V> List<V> lRangeAll2(String dbName, K key) {
		return lRangeAll2(dbName, key, null);
	}
	
	@Override
	public <K, V> List<V> lRangeAll2(String dbName, K key, Class<V> valueType) {
		return lRange(dbName, key, 0 , -1, valueType);
	}

	@Override
	public <K, V> Long lRem(K key, long count, V value) {
		return lRem(null, key, count, value);
	}

	@Override
	public <K, V> Long lRem(final String dbName, final K key, final long count, final V value) {
		if (key == null || value == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.lRem(keySerializer.serialize(key), count, valueSerializer.serialize(value));
			}
		});
	}

	@Override
	public <K, V> Long lRemAll(K key, V value) {
		return lRemAll(null, key, value);
	}

	@Override
	public <K, V> Long lRemAll(String dbName, K key, V value) {
		return lRem(dbName, key, 0L, value);
	}

	@Override
	public <K> void lTrim(K key, long begin, long end) {
		lTrim(null, key, begin, end);
	}

	@Override
	public <K> void lTrim(final String dbName, final K key, final long begin, final long end) {
		if (key == null)
			return;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				connection.lTrim(keySerializer.serialize(key), begin, end);
				return null;
			}
		});
	}
	
	@Override
	public <K, V> Long rPush(K key, V value) {
		return rPush(key, value, 0);
	}
	
	@Override
	public <K, V> Long rPush(K key, V value, long expireSeconds) {
		return rPush2(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long rPush2(String dbName, K key, V value) {
		return rPush2(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long rPush2(String dbName, K key, V value, long expireSeconds) {
		AssertUtils.assertNotNull(value, "Value can not be null of command [rPush]");
		return rPush(dbName, key, new Object[] { value }, expireSeconds);
	}
	
	@Override
	public <K, V> Long rPush(K key, V[] values) {
		return rPush(null, key, values);
	}
	
	@Override
	public <K, V> Long rPush(K key, V[] values, long expireSeconds) {
		return rPush(null, key, values, expireSeconds);
	}

	@Override
	public <K, V> Long rPush(String dbName, final K key, final V[] values) {
		return rPush(dbName, key, values, 0);
	}
	
	@Override
	public <K, V> Long rPush(final String dbName, final K key, final V[] values, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [rPush]");
		AssertUtils.assertNotEmpty(values, "Values can not be empty of command [rPush]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				long count = NumberUtils.safeLong(connection.rPush(keyByte, serializeValuesToArray(dbName, values)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> Long rPush(K key, Collection<V> values) {
		return rPush(null, key, values);
	}
	
	@Override
	public <K, V> Long rPush(K key, Collection<V> values, long expireSeconds) {
		return rPush(null, key, values, expireSeconds);
	}
	
	@Override
	public <K, V> Long rPush(String dbName, K key, Collection<V> values) {
		return rPush(dbName, key, values, 0);
	}

	@Override
	public <K, V> Long rPush(String dbName, K key, Collection<V> values, long expireSeconds) {
		return rPush(dbName, key, CollectionUtils.toObjectArray(values), expireSeconds);
	}

	@Override
	public <K, V> Long rPushX(K key, V value) {
		return rPushX(key, value, 0);
	}
	
	@Override
	public <K, V> Long rPushX(K key, V value, long expireSeconds) {
		return rPushX2(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long rPushX2(String dbName, K key, V value) {
		return rPushX2(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long rPushX2(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [rPushX]");
		AssertUtils.assertNotNull(value, "Value can not be null of command [rPushX]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				long count = NumberUtils.safeLong(connection.rPushX(keyByte, valueSerializer.serialize(value)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <S, T, V> V rPopLPush(S srcKey, T destKey) {
		return rPopLPush(srcKey, destKey, null);
	}

	@Override
  	public <S, T, V> V rPopLPush(S srcKey, T destKey, Class<V> valueType) {
  		return rPopLPush(srcKey, destKey, 0, valueType);
  	}
  		
	@Override
	public <S, T, V> V rPopLPush(S srcKey, T destKey, long expireSeconds) {
		return rPopLPush2(null, srcKey, destKey, expireSeconds);
	}
	
	@Override
	public <S, T, V> V rPopLPush(S srcKey, T destKey, long expireSeconds, Class<V> valueType) {
		return rPopLPush2(null, srcKey, destKey, expireSeconds, valueType);
	}

	@Override
	public <S, T, V> V rPopLPush2(String dbName, S srcKey, T destKey) {
		return rPopLPush2(dbName, srcKey, destKey, 0);
	}
	
	@Override
	public <S, T, V> V rPopLPush2(String dbName, S srcKey, T destKey, Class<V> valueType) {
		return rPopLPush2(dbName, srcKey, destKey, 0, valueType);
	}
	
	@Override
	public <S, T, V> V rPopLPush2(String dbName, S srcKey, T destKey, long expireSeconds) {
		return rPopLPush2(dbName, srcKey, destKey, expireSeconds, null);
	}
	
	@Override
	public <S, T, V> V rPopLPush2(final String dbName, final S srcKey, final T destKey, final long expireSeconds, final Class<V> valueType) {
		AssertUtils.assertNotNull(srcKey, "Source key can not be null of command [rPopLPush]");
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [rPopLPush]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] destKeyByte = keySerializer.serialize(destKey);
				RedisRepository repository = select(connection, dbName);
				// 将源列表中最后一个元素取出后存入目标列表
				byte[] destValueByte = connection.rPopLPush((srcKey.equals(destKey) ? 
						destKeyByte : keySerializer.serialize(srcKey)), destKeyByte);
				// 设置目标键的过期时间
				setExpireTime(connection, repository, destKeyByte, expireSeconds);
				return deserializeValueByte(dbName, destValueByte, valueType);
			}
		});
	}

	@Override
	public <K, V> V rPop(K key) {
		return rPop(key, null);
	}
	
	@Override
	public <K, V> V rPop(K key, Class<V> valueType) {
		return rPop2(null, key, valueType);
	}

	@Override
	public <K, V> V rPop2(String dbName, K key) {
		return rPop2(dbName, key, null);
	}
	
	@Override
	public <K, V> V rPop2(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] valueByte = connection.rPop(keySerializer.serialize(key));
				return deserializeValueByte(dbName, valueByte, valueType);
			}
		});
	}
	
	@Override
	public <K, V> Long sAdd(K key, V member) {
		return sAdd(key, member, 0);
	}
	
	@Override
	public <K, V> Long sAdd(K key, V member, long expireSeconds) {
		return sAdd2(null, key, member, expireSeconds);
	}

	@Override
	public <K, V> Long sAdd2(String dbName, K key, V member) {
		return sAdd2(dbName, key, member, 0);
	}
	
	@Override
	public <K, V> Long sAdd2(String dbName, K key, V member, long expireSeconds) {
		AssertUtils.assertNotNull(member, "Member can not be null of command [sAdd]");
		return sAdd(dbName, key, Collections.singletonList(member), expireSeconds);
	}
	
	@Override
	public <K, V> Long sAdd(K key, V[] members) {
		return sAdd(null, key, members);
	}
	
	@Override
	public <K, V> Long sAdd(K key, V[] members, long expireSeconds) {
		return sAdd(null, key, members, expireSeconds);
	}

	@Override
	public <K, V> Long sAdd(String dbName, K key, V[] members) {
		return sAdd(dbName, key, members, 0);
	}
	
	@Override
	public <K, V> Long sAdd(final String dbName, final K key, final V[] members, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sAdd]");
		AssertUtils.assertNotEmpty(members, "Members can not be empty of command [sAdd]");
				
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] keyByte = keySerializer.serialize(key);
				long count = NumberUtils.safeLong(connection.sAdd(keyByte, serializeValuesToArray(dbName, members)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> Long sAdd(K key, Collection<V> members) {
		return sAdd(null, key, members);
	}
	
	@Override
	public <K, V> Long sAdd(K key, Collection<V> members, long expireSeconds) {
		return sAdd(null, key, members, expireSeconds);
	}

	@Override
	public <K, V> Long sAdd(String dbName, K key, Collection<V> members) {
		return sAdd(dbName, key, members, 0);
	}
	
	@Override
	public <K, V> Long sAdd(String dbName, K key, Collection<V> members, long expireSeconds) {
		return sAdd(dbName, key, CollectionUtils.toObjectArray(members), expireSeconds);
	}

	@Override
	public <K> Long sCard(K key) {
		return sCard(null, key);
	}

	@Override
	public <K> Long sCard(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sCard(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K, V> Set<V> sDiff(Collection<K> keys) {
		return sDiff(keys, null);
	}
	
	@Override
	public <K, V> Set<V> sDiff(Collection<K> keys, Class<V> valueType) {
		return sDiff(null, keys, valueType);
	}
	
	@Override
	public <K, V> Set<V> sDiff(String dbName, Collection<K> keys) {
		return sDiff(dbName, keys, null);
	}
	
	@Override
	public <K, V> Set<V> sDiff(String dbName, Collection<K> keys, Class<V> valueType) {
		return sDiff(dbName, CollectionUtils.toObjectArray(keys), valueType);
	}

	@Override
	public <K, V> Set<V> sDiff(K[] keys) {
		return sDiff(keys, null);
	}
	
	@Override
	public <K, V> Set<V> sDiff(K[] keys, Class<V> valueType) {
		return sDiff(null, keys, valueType);
	}

	@Override
	public <K, V> Set<V> sDiff(String dbName, K[] keys) {
		return sDiff(dbName, keys, null);
	}
	
	@Override
	public <K, V> Set<V> sDiff(final String dbName, final K[] keys, final Class<V> valueType) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[][] keyBytes = serializeKeysToArray(dbName, keys);
				Set<byte[]> valueBytes = connection.sDiff(keyBytes);
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}
	
	@Override
	public <T, K> Long sDiffStore(T destKey, Collection<K> keys) {
		return sDiffStore(null, destKey, keys);
	}
	
	@Override
	public <T, K> Long sDiffStore(T destKey, Collection<K> keys, long expireSeconds) {
		return sDiffStore(null, destKey, keys, expireSeconds);
	}
	
	@Override
	public <T, K> Long sDiffStore(String dbName, T destKey, Collection<K> keys) {
		return sDiffStore(dbName, destKey, keys, 0);
	}

	@Override
	public <T, K> Long sDiffStore(String dbName, T destKey, Collection<K> keys, long expireSeconds) {
		return sDiffStore(dbName, destKey, CollectionUtils.toObjectArray(keys), expireSeconds);
	}
	
	@Override
	public <T, K> Long sDiffStore(T destKey, K[] keys) {
		return sDiffStore(null, destKey, keys);
	}
	
	@Override
	public <T, K> Long sDiffStore(T destKey, K[] keys, long expireSeconds) {
		return sDiffStore(null, destKey, keys, expireSeconds);
	}

	@Override
	public <T, K> Long sDiffStore(String dbName, T destKey, K[] keys) {
		return sDiffStore(dbName, destKey, keys, 0);
	}
	
	@Override
	public <T, K> Long sDiffStore(final String dbName, final T destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sDiffStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [sDiffStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sDiffStore(destKeyByte, serializeKeysToArray(dbName, keys));
				setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				return result;
			}
		});
	}
	
	@Override
	public <K, V> Set<V> sInter(Collection<K> keys) {
		return sInter(keys, null);
	}
	
	@Override
	public <K, V> Set<V> sInter(Collection<K> keys, Class<V> valueType) {
		return sInter(null, keys, valueType);
	}

	@Override
	public <K, V> Set<V> sInter(String dbName, Collection<K> keys) {
		return sInter(dbName, keys, null);
	}
	
	@Override
	public <K, V> Set<V> sInter(String dbName, Collection<K> keys, Class<V> valueType) {
		return sInter(dbName, CollectionUtils.toObjectArray(keys), valueType);
	}

	@Override
	public <K, V> Set<V> sInter(K[] keys) {
		return sInter(keys, null);
	}
	
	@Override
	public <K, V> Set<V> sInter(K[] keys, Class<V> valueType) {
		return sInter(null, keys, valueType);
	}

	@Override
	public <K, V> Set<V> sInter(String dbName, K[] keys) {
		return sInter(dbName, keys, null);
	}
	
	@Override
	public <K, V> Set<V> sInter(final String dbName, final K[] keys, final Class<V> valueType) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[][] keyBytes = serializeKeysToArray(dbName, keys);
				Set<byte[]> valueBytes = connection.sInter(keyBytes);
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}
	
	@Override
	public <T, K> Long sInterStore(T destKey, Collection<K> keys) {
		return sInterStore(null, destKey, keys);
	}
	
	@Override
	public <T, K> Long sInterStore(T destKey, Collection<K> keys, long expireSeconds) {
		return sInterStore(null, destKey, keys, expireSeconds);
	}

	@Override
	public <T, K> Long sInterStore(String dbName, T destKey, Collection<K> keys) {
		return sInterStore(dbName, destKey, keys, 0);
	}
	
	@Override
	public <T, K> Long sInterStore(String dbName, T destKey, Collection<K> keys, long expireSeconds) {
		return sInterStore(dbName, destKey, CollectionUtils.toObjectArray(keys), expireSeconds);
	}

	@Override
	public <T, K> Long sInterStore(T destKey, K[] keys) {
		return sInterStore(null, destKey, keys);
	}
	
	@Override
	public <T, K> Long sInterStore(T destKey, K[] keys, long expireSeconds) {
		return sInterStore(null, destKey, keys, expireSeconds);
	}

	@Override
	public <T, K> Long sInterStore(String dbName, T destKey, K[] keys) {
		return sInterStore(dbName, destKey, keys, 0);
	}
	
	@Override
	public <T, K> Long sInterStore(final String dbName, final T destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sInterStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [sInterStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sInterStore(destKeyByte, serializeKeysToArray(dbName, keys));
				setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				return result;
			}
		});
	}
	
	@Override
	public <K, V> Set<V> sUnion(Collection<K> keys) {
		return sUnion(keys, null);
	}
	
	@Override
	public <K, V> Set<V> sUnion(Collection<K> keys, Class<V> valueType) {
		return sUnion(null, keys, valueType);
	}

	@Override
	public <K, V> Set<V> sUnion(String dbName, Collection<K> keys) {
		return sUnion(dbName, keys, null);
	}
	
	@Override
	public <K, V> Set<V> sUnion(String dbName, Collection<K> keys, Class<V> valueType) {
		return sUnion(dbName, CollectionUtils.toObjectArray(keys), valueType);
	}

	@Override
	public <K, V> Set<V> sUnion(K[] keys) {
		return sUnion(keys, null);
	}
	
	@Override
	public <K, V> Set<V> sUnion(K[] keys, Class<V> valueType) {
		return sUnion(null, keys, valueType);
	}

	@Override
	public <K, V> Set<V> sUnion(String dbName, K[] keys) {
		return sUnion(dbName, keys, null);
	}
	
	@Override
	public <K, V> Set<V> sUnion(final String dbName, final K[] keys, final Class<V> valueType) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[][] keyBytes = serializeKeysToArray(dbName, keys);
				Set<byte[]> valueBytes = connection.sUnion(keyBytes);
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}
	
	@Override
	public <T, K> Long sUnionStore(T destKey, Collection<K> keys) {
		return sUnionStore(null, destKey, keys);
	}
	
	@Override
	public <T, K> Long sUnionStore(T destKey, Collection<K> keys, long expireSeconds) {
		return sUnionStore(null, destKey, keys, expireSeconds);
	}
	
	@Override
	public <T, K> Long sUnionStore(String dbName, T destKey, Collection<K> keys) {
		return sUnionStore(dbName, destKey, keys, 0);
	}

	@Override
	public <T, K> Long sUnionStore(String dbName, T destKey, Collection<K> keys, long expireSeconds) {
		return sUnionStore(dbName, destKey, CollectionUtils.toObjectArray(keys), expireSeconds);
	}

	@Override
	public <T, K> Long sUnionStore(T destKey, K[] keys) {
		return sUnionStore(null, destKey, keys);
	}
	
	@Override
	public <T, K> Long sUnionStore(T destKey, K[] keys, long expireSeconds) {
		return sUnionStore(null, destKey, keys, expireSeconds);
	}

	@Override
	public <T, K> Long sUnionStore(String dbName, T destKey, K[] keys) {
		return sUnionStore(dbName, destKey, keys, 0);
	}
	
	@Override
	public <T, K> Long sUnionStore(final String dbName, final T destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [sUnionStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sUnionStore(destKeyByte, serializeKeysToArray(dbName, keys));
				setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				return result;
			}
		});
	}

	@Override
	public <K, V> Boolean sIsMember(K key, V member) {
		return sIsMember(null, key, member);
	}

	@Override
	public <K, V> Boolean sIsMember(final String dbName, final K key, final V member) {
		if (key == null || member == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sIsMember(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> Set<V> sMembers(K key) {
		return sMembers(key, null);
	}
	
	@Override
	public <K, V> Set<V> sMembers(K key, Class<V> valueType) {
		return sMembers2(null, key, valueType);
	}

	@Override
	public <K, V> Set<V> sMembers2(String dbName, K key) {
		return sMembers2(dbName, key, null);
	}
	
	@Override
	public <K, V> Set<V> sMembers2(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				Set<byte[]> memberBytes = connection.sMembers(keySerializer.serialize(key));
				return deserializeValueByteToSet(dbName, memberBytes, valueType);
			}
		});
	}

	@Override
	public <K, T, V> Boolean sMove(K srcKey, T destKey, V member) {
		return sMove(null, srcKey, destKey, member);
	}

	@Override
	public <K, T, V> Boolean sMove(final String dbName, final K srcKey, final T destKey, final V member) {
		if (srcKey == null || destKey == null || member == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.sMove(keySerializer.serialize(srcKey),
						keySerializer.serialize(destKey), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> V sPop(K key) {
		return sPop(key, null);
	}
	
	@Override
	public <K, V> V sPop(K key, Class<V> valueType) {
		return sPop2(null, key, valueType);
	}

	@Override
	public <K, V> V sPop2(String dbName, K key) {
		return sPop2(dbName, key, null);
	}
	
	@Override
	public <K, V> V sPop2(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] memberByte = connection.sPop(keySerializer.serialize(key));
				return deserializeValueByte(dbName, memberByte, valueType);
			}
		});
	}

	@Override
	public <K, V> V sRandMember(K key) {
		return sRandMember(key, null);
	}
	
	@Override
	public <K, V> V sRandMember(K key, Class<V> valueType) {
		return sRandMember2(null, key, valueType);
	}
	
	@Override
	public <K, V> V sRandMember2(String dbName, K key) {
		return sRandMember2(dbName, key, null);
	}
	
	@Override
	public <K, V> V sRandMember2(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] memberByte = connection.sRandMember(keySerializer.serialize(key));
				return deserializeValueByte(dbName, memberByte, valueType);
			}
		});
	}

	@Override
	public <K, V> Long sRem(K key, V member) {
		return sRem(null, key, member);
	}

	@Override
	public <K, V> Long sRem(String dbName, K key, V member) {
		return sRem(null, key, new Object[] { member });
	}

	@Override
	public <K, V> Long sRem(K key, V[] members) {
		return sRem(null, key, members);
	}

	@Override
	public <K, V> Long sRem(final String dbName, final K key, final V[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return NumberUtils.safeLong(connection.sRem(keySerializer.serialize(key), 
						serializeValuesToArray(dbName, members)));
			}
		});
	}

	@Override
	public <K, V> Long sRem(K key, Collection<V> members) {
		return sRem(null, key, members);
	}

	@Override
	public <K, V> Long sRem(String dbName, K key, Collection<V> members) {
		return sRem(dbName, key, CollectionUtils.toObjectArray(members));
	}
	
	@Override
	public <K, V> Boolean zAdd(K key, double score, V member) {
		return zAdd(key, score, member, 0);
	}
	
	@Override
	public <K, V> Boolean zAdd(K key, double score, V member, long expireSeconds) {
		return zAdd2(null, key, score, expireSeconds);
	}

	@Override
	public <K, V> Boolean zAdd2(String dbName, K key, double score, V member) {
		return zAdd2(dbName, key, score, member, 0);
	}
	
	@Override
	public <K, V> Boolean zAdd2(String dbName, K key, double score, V member, long expireSeconds) {
		AssertUtils.assertNotNull(member, "Member can not be null of command [zAdd]");
		return zAdd(dbName, key, Collections.singletonMap(score, member), expireSeconds);
	}

	@Override
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers) {
		return zAdd(null, key, scoreMembers);
	}
	
	@Override
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers, long expireSeconds) {
		return zAdd(null, key, scoreMembers, expireSeconds);
	}

	@Override
	public <K, V> Boolean zAdd(String dbName, K key, Map<Double, V> scoreMembers) {
		return zAdd(dbName, key, scoreMembers, 0);
	}
	
	@Override
	public <K, V> Boolean zAdd(final String dbName, final K key, final Map<Double, V> scoreMembers, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [zAdd]");
		AssertUtils.assertNotEmpty(scoreMembers, "Score-member map can not be empty of command [zAdd]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				Iterator<Entry<Double, V>> iterator = scoreMembers.entrySet().iterator();
				boolean result = true;
				while (iterator.hasNext()) {
					Entry<Double, V> entry = iterator.next();
					result = result && connection.zAdd(keyByte, 
							NumberUtils.safeDouble(entry.getKey()), valueSerializer.serialize(entry.getValue()));
				}
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}

	@Override
	public <K> Long zCard(K key) {
		return zCard(null, key);
	}

	@Override
	public <K> Long zCard(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zCard(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Long zCount(K key, double minScore, double maxScore) {
		return zCount(null, minScore, maxScore);
	}

	@Override
	public <K> Long zCount(final String dbName, final K key, final double minScore, final double maxScore) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zCount(keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRange(K key, long begin, long end) {
		return zRange(key, begin, end, null);
	}
	
	@Override
	public <K, V> Set<V> zRange(K key, long begin, long end, Class<V> valueType) {
		return zRange(null, key, begin, end, valueType);
	}

	@Override
	public <K, V> Set<V> zRange(String dbName, K key, long begin, long end) {
		return zRange(dbName, key, begin, end, null);
	}
	
	@Override
	public <K, V> Set<V> zRange(final String dbName, final K key, final long begin, final long end, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> valueBytes = connection.zRange(keySerializer.serialize(key), begin, end);
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRangeAll(K key) {
		return zRangeAll(key, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeAll(K key, Class<V> valueType) {
		return zRangeAll2(null, key, valueType);
	}

	@Override
	public <K, V> Set<V> zRangeAll2(String dbName, K key) {
		return zRangeAll2(dbName, key, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeAll2(String dbName, K key, Class<V> valueType) {
		return zRange(dbName, key, 0, -1, valueType);
	}

	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore) {
		return zRangeByScore(key, minScore, maxScore, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, Class<V> valueType) {
		return zRangeByScore(null, key, minScore, maxScore, valueType);
	}

	@Override
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore) {
		return zRangeByScore(dbName, key, minScore, maxScore, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(final String dbName, final K key, final double minScore, final double maxScore, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {
			
			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> valueBytes = connection.zRangeByScore(
						keySerializer.serialize(key), minScore, maxScore);
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, long offset, long count) {
		return zRangeByScore(key, minScore, maxScore, offset, count, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, long offset, long count, Class<V> valueType) {
		return zRangeByScore(null, key, minScore, maxScore, offset, count, valueType);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore,
			double maxScore, long offset, long count) {
		return zRangeByScore(dbName, key, minScore, maxScore, offset, count, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(final String dbName, final K key, final double minScore, final double maxScore,
			final long offset, final long count, final Class<V> valueType) {
		
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {
			
			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> valueBytes = connection.zRangeByScore(
						keySerializer.serialize(key), minScore, maxScore, offset, count);
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore, double maxScore) {
		return zRangeByScoreWithScores(null, key, minScore, maxScore);
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(final String dbName,
			final K key, final double minScore, final double maxScore) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {
			
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRangeByScoreWithScores(keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore,
			double maxScore, long offset, long count) {
		return zRangeByScoreWithScores(null, key, minScore, maxScore, offset, count);
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(final String dbName, final K key,
			final double minScore, final double maxScore, final long offset, final long count) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {
			
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRangeByScoreWithScores(
						keySerializer.serialize(key), minScore, maxScore, offset, count);
			}
		});
	}

	@Override
	public <K, V> Long zRank(K key, V member) {
		return zRank(null, key, member);
	}

	@Override
	public <K, V> Long zRank(final String dbName, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRank(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> Long zRem(K key, V member) {
		return zRem(null, key, member);
	}

	@Override
	public <K, V> Long zRem(String dbName, K key, V member) {
		return zRem(dbName, key, new Object[] { member });
	}

	@Override
	public <K, V> Long zRem(K key, V[] members) {
		return zRem(null, key, members);
	}

	@Override
	public <K, V> Long zRem(final String dbName, final K key, final V[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return NumberUtils.safeLong(connection.zRem(keySerializer.serialize(key), 
						serializeValuesToArray(dbName, members)));
			}
		});
	}

	@Override
	public <K, V> Long zRem(K key, Collection<V> members) {
		return zRem(null, key, members);
	}

	@Override
	public <K, V> Long zRem(String dbName, K key, Collection<V> members) {
		return zRem(dbName, key, CollectionUtils.toObjectArray(members));
	}

	@Override
	public <K> Long zRemRangeByRank(K key, long begin, long end) {
		return zRemRangeByRank(null, key, begin, end);
	}

	@Override
	public <K> Long zRemRangeByRank(final String dbName, final K key, final long begin, final long end) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.zRemRange(keySerializer.serialize(key), begin, end);
			}
		});
	}

	@Override
	public <K> Long zRemRangeByScore(K key, double minScore, double maxScore) {
		return zRemRangeByScore(null, key, minScore, maxScore);
	}

	@Override
	public <K> Long zRemRangeByScore(final String dbName, final K key, final double minScore,
			final double maxScore) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.zRemRangeByScore(keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRevRange(K key, long begin, long end) {
		return zRevRange(key, begin, end, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRange(K key, long begin, long end, Class<V> valueType) {
		return zRevRange(null, key, begin, end, valueType);
	}

	@Override
	public <K, V> Set<V> zRevRange(String dbName, K key, long begin, long end) {
		return zRevRange(dbName, key, begin, end, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRange(final String dbName, final K key, final long begin, final long end, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> valueBytes = connection.zRevRange(keySerializer.serialize(key), begin, end);
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRevRangeAll(K key) {
		return zRevRangeAll(key, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeAll(K key, Class<V> valueType) {
		return zRevRangeAll2(null, key, valueType);
	}

	@Override
	public <K, V> Set<V> zRevRangeAll2(String dbName, K key) {
		return zRevRangeAll2(dbName, key, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeAll2(String dbName, K key, Class<V> valueType) {
		return zRevRange(dbName, key, 0, -1, valueType);
	}

	@Override
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore) {
		return zRevRangeByScore(key, minScore, maxScore, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore, Class<V> valueType) {
		return zRevRangeByScore(null, key, minScore, maxScore, valueType);
	}

	@Override
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore) {
		return zRevRangeByScore(dbName, key, minScore, maxScore, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeByScore(final String dbName, final K key, final double minScore, final double maxScore, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> valueBytes = connection.zRevRangeByScore(keySerializer.serialize(key), minScore, maxScore);
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore, double maxScore) {
		return zRevRangeByScoreWithScores(null, key, minScore, maxScore);
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(final String dbName, final K key,
			final double minScore, final double maxScore) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {
			
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRevRangeByScoreWithScores(
						keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore,
			double maxScore, long offset, long count) {
		return zRevRangeByScoreWithScores(null, key, minScore, maxScore, offset, count);
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(final String dbName, final K key,
			final double minScore, final double maxScore, final long offset, final long count) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {

			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRevRangeByScoreWithScores(
						keySerializer.serialize(key), minScore, maxScore, offset, count);
			}
		});
	}

	@Override
	public <K, V> Long zRevRank(K key, V member) {
		return zRevRank(null, key, member);
	}

	@Override
	public <K, V> Long zRevRank(final String dbName, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRevRank(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> Double zScore(K key, V member) {
		return zScore(null, key, member);
	}

	@Override
	public <K, V> Double zScore(final String dbName, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zScore(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K> Long zUnionStore(K destKey, K key) {
		return zUnionStore(null, destKey, key);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, K key) {
		AssertUtils.assertNotNull(key, "Source key can not be null of command [zUnionStore]");
		
		return zUnionStore(dbName, destKey, new Object[] { key });
	}

	@Override
	public <K> Long zUnionStore(K destKey, K[] keys) {
		return zUnionStore(null, destKey, keys);
	}

	@Override
	public <K> Long zUnionStore(final String dbName, final K destKey, final K[] keys) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [zUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [zUnionStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zUnionStore(keySerializer.serialize(destKey), 
						serializeKeysToArray(dbName, keys));
			}
		});
	}

	@Override
	public <K> Long zUnionStore(K destKey, Collection<K> keys) {
		return zUnionStore(null, destKey, keys);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys) {
		return zUnionStore(dbName, destKey, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, K[] keys) {
		return zUnionStore(null, destKey, aggregate, weights, keys);
	}

	@Override
	public <K> Long zUnionStore(final String dbName, final K destKey, final Aggregate aggregate,
			final int[] weights, final K[] keys) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [zUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [zUnionStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zUnionStore(keySerializer.serialize(destKey), 
						aggregate, weights, serializeKeysToArray(dbName, keys));
			}
		});
	}

	@Override
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys) {
		return zUnionStore(null, destKey, aggregate, weights, keys);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Aggregate aggregate,
			int[] weights, Collection<K> keys) {
		return zUnionStore(dbName, destKey, aggregate, weights, CollectionUtils.toObjectArray(keys));
	}
	
	@Override
	public <K> Long zInterStore(K destKey, K srcKey) {
		return zInterStore(null, destKey, srcKey);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, K srcKey) {
		AssertUtils.assertNotNull(srcKey, "Source key can not be null of command [zInterStore]");
		
		return zInterStore(dbName, destKey, new Object[] { srcKey });
	}

	@Override
	public <K> Long zInterStore(K destKey, K[] keys) {
		return zInterStore(null, destKey, keys);
	}

	@Override
	public <K> Long zInterStore(final String dbName, final K destKey, final K[] keys) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [zInterStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [zInterStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zInterStore(keySerializer.serialize(destKey), 
						serializeKeysToArray(dbName, keys));
			}
		});
	}

	@Override
	public <K> Long zInterStore(K destKey, Collection<K> keys) {
		return zInterStore(null, destKey, keys);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys) {
		return zInterStore(dbName, destKey, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, K[] keys) {
		return zInterStore(null, destKey, aggregate, weights, keys);
	}

	@Override
	public <K> Long zInterStore(final String dbName, final K destKey, final Aggregate aggregate,
			final int[] weights, final K[] keys) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [zInterStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [zInterStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zInterStore(keySerializer.serialize(destKey), 
						aggregate, weights, serializeKeysToArray(dbName, keys));
			}
		});
	}

	@Override
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys) {
		return zInterStore(null, destKey, aggregate, weights, keys);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, Aggregate aggregate,
			int[] weights, Collection<K> keys) {
		return zInterStore(dbName, destKey, aggregate, weights, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K, V> Double zIncrBy(K key, double increment, V member) {
		return zIncrBy(null, key, increment, member);
	}

	@Override
	public <K, V> Double zIncrBy(final String dbName, final K key, final double increment, final V member) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [zIncrBy]");
		AssertUtils.assertNotNull(key, "Member can not be null of command [zIncrBy]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zIncrBy(keySerializer.serialize(key), 
						increment, valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public Long dbSize() {
		return dbSize(null);
	}

	@Override
	public Long dbSize(final String dbName) {
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				select(connection, dbName);
				return connection.dbSize();
			}
		});
	}

	@Override
	public void flushAll() {
		super.getRedisTemplate().execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushAll();
				return null;
			}
		});
	}

	@Override
	public void flushDb() {
		this.flushDb(null);
	}

	@Override
	public void flushDb(final String dbName) {
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				select(connection, dbName);
				connection.flushDb();
				return null;
			}
		});
	}

	@Override
	public void shutdown() {
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.shutdown();
				return null;
			}
		});
	}

}
