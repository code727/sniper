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

package org.workin.nosql.redis.spring;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.nosql.redis.RedisRepository;
import org.workin.serialization.Serializer;
import org.workin.serialization.jdk.StringSerializer;

/**
 * Spring Redis命令行数据访问实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class SpringRedisCommandsDaoImpl extends SpringRedisDaoSupport implements SpringRedisCommandsDao {
	
	@Override
	public <K> Set<K> keys() {
		return keys(super.getDefaultDbIndex());
	}

	@Override
	public <K> Set<K> keys(int dbIndex) {
		return keys(dbIndex, null);
	}

	@Override
	public <K> Set<K> keys(String pattern) {
		return keys(super.getDefaultDbIndex(), pattern);
	}

	@Override
	public <K> Set<K> keys(final int dbIndex, final String pattern) {
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final StringSerializer stringSerializer = new StringSerializer();
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<K>>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public Set<K> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Set<byte[]> keyBytes = connection.keys(stringSerializer.serialize(StringUtils.isNotEmpty(pattern) ? pattern : "*"));
				Set<K> keys = new HashSet<K>();
				for (byte[] key : keyBytes)
					keys.add((K) keySerializer.deserialize(key));
				
				return keys;
			}
		});
	}
	
	@Override
	public <K> Long del(K key) {
		return del(super.getDefaultDbIndex(), key);
	}
	
	@Override
	public <K> Long del(int dbIndex, K key) {
		return del(dbIndex, new Object[] { key });
	}

	@Override
	public <K> Long del(K[] keys) {
		return del(super.getDefaultDbIndex(), keys);
	}
	
	@Override
	public <K> Long del(final int dbIndex, final K[] keys) {
		
		if (ArrayUtils.isEmpty(keys))
			return 0L;
		
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.del(serializeKeysToArray(dbIndex, keys));
			}
		});
	}

	@Override
	public <K> Long del(Collection<K> keys) {
		return del(super.getDefaultDbIndex(), keys);
	}

	@Override
	public <K> Long del(int dbIndex, Collection<K> keys) {
		return del(dbIndex, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Boolean exists(K key) {
		return exists(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Boolean exists(final int dbIndex, final K key) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.exists(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Boolean expire(K key, long seconds) {
		return expire(super.getDefaultDbIndex(), seconds);
	}

	@Override
	public <K> Boolean expire(final int dbIndex, final K key, final long seconds) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.expire(keySerializer.serialize(key), seconds);
			}
		});
	}
	
	@Override
	public <K> Boolean expireAt(K key, long timestamp) {
		return expireAt(super.getDefaultDbIndex(), key, timestamp);
	}

	@Override
	public <K> Boolean expireAt(final int dbIndex, final K key, final long timestamp) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.expireAt(keySerializer.serialize(key), timestamp);
			}
		});
	}
	
	@Override
	public <K> Boolean expireAt(K key, Date date) {
		return expireAt(super.getDefaultDbIndex(), key, date);
	}

	@Override
	public <K> Boolean expireAt(int dbIndex, K key, Date date) {
		return expireAt(dbIndex, key, DateUtils.dateToUnixTimestamp(date));
	}
	
	@Override
	public <K> Boolean move(K key, int targetIndex) {
		return move(super.getDefaultDbIndex(), key, targetIndex);
	}

	@Override
	public <K> Boolean move(final int dbIndex, final K key, final int targetIndex) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.move(keySerializer.serialize(key), targetIndex);
			}
		});
	}

	@Override
	public <K> Long ttl(K key) {
		return ttl(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Long ttl(final int dbIndex, final K key) {
		if (key == null)
			return -2L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.ttl(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K, V> List<V> sort(K key, SortParameters params) {
		return sort(super.getDefaultDbIndex(), key, params);
	}

	@Override
	public <K, V> List<V> sort(final int dbIndex, final K key, final SortParameters params) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort].");
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				List<byte[]> byteList = connection.sort(keySerializer.serialize(key), params);
				List<V> values = CollectionUtils.newArrayList();
				for (byte[] bytes : byteList)
					values.add((V) valueSerializer.deserialize(bytes));
				
				return values;
			}
		});
	}

	@Override
	public <K, V> Long sortCount(K key, SortParameters params, K targetKey) {
		return sortCount(super.getDefaultDbIndex(), key, params, targetKey);
	}

	@Override
	public <K, V> Long sortCount(final int dbIndex, final K key, final SortParameters params, final K targetKey) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort].");
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort].");
		AssertUtils.assertNotNull(targetKey, "Target key can not be null of command [sort].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.sort(keySerializer.serialize(key), params, keySerializer.serialize(targetKey));
			}
		});
	}

	@Override
	public <K, V> List<V> sortResult(K key, SortParameters params, K targetKey) {
		return sortResult(super.getDefaultDbIndex(), key, params, targetKey);
	}

	@Override
	public <K, V> List<V> sortResult(final int dbIndex, final K key, final SortParameters params, final K targetKey) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort].");	
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort].");
		AssertUtils.assertNotNull(targetKey, "Target key can not be null of command [sort].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] targetKeyByte = keySerializer.serialize(targetKey);
				Long count = connection.sort(keySerializer.serialize(key), params, targetKeyByte);
				if (count != null && count > 0) {
					DataType dataType = connection.type(targetKeyByte);
					return listByDataType(dataType, connection, dbIndex, targetKeyByte);
				}
				return null;
			}
		});
	}

	@Override
	public <K> DataType type(K key) {
		return type(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> DataType type(final int dbIndex, final K key) {
		if (key == null)
			return DataType.NONE;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<DataType>() {

			@Override
			public DataType doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.type(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <V> Set<V> values() {
		return values(super.getDefaultDbIndex());
	}

	@Override
	public <V> Set<V> values(int dbIndex) {
		return values(dbIndex, null);
	}

	@Override
	public <V> Set<V> values(String pattern) {
		return null;
	}

	@Override
	public <V> Set<V> values(int dbIndex, final String pattern) {
		final StringSerializer stringSerializer = new StringSerializer();
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				// 获取匹配模式的键
				Set<byte[]> keySet = connection.keys(stringSerializer
						.serialize(StringUtils.isNotEmpty(pattern) ? pattern : "*"));
				Set<V> set = CollectionUtils.newHashSet();
				for (byte[] key : keySet)
					set.add((V) valueSerializer.deserialize(connection.get(key)));
				
				return set;
			}
		});
	}

	@Override
	public <K, V> void set(K key, V value) {
		set(super.getDefaultDbIndex(), key, value);
	}
	
	@Override
	public <K, V> void set(K key, V value, long expireSeconds) {
		set(super.getDefaultDbIndex(), key, value, expireSeconds);
	}

	@Override
	public <K, V> void set(int dbIndex, K key, V value) {
		set(dbIndex, key, value, 0);
	}
	
	@Override
	public <K, V> void set(final int dbIndex, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [set].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [set].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				connection.set(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
		
	}

	@Override
	public <K, V> Boolean setNX(K key, V value) {
		return setNX(super.getDefaultDbIndex(), key, value);
	}
	
	@Override
	public <K, V> Boolean setNX(K key, V value, long expireSeconds) {
		return setNX(super.getDefaultDbIndex(), key, value, expireSeconds);
	}

	@Override
	public <K, V> Boolean setNX(int dbIndex, K key, V value) {
		return setNX(dbIndex, key, value, 0);
	}	
	
	@Override
	public <K, V> Boolean setNX(final int dbIndex, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setNX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setNX].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				Boolean result = connection.setNX(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> void setEx(K key, V value) {
		setEx(super.getDefaultDbIndex(), key, value);
	}

	@Override
	public <K, V> void setEx(int dbIndex, K key, V value) {
		setEx(dbIndex, key, getExpireSecond(dbIndex), value);	
	}

	@Override
	public <K, V> void setEx(K key, long seconds, V value) {
		setEx(super.getDefaultDbIndex(), key, seconds, value);
	}

	@Override
	public <K, V> void setEx(final int dbIndex, final K key, final long seconds, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setEx].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setEx].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
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
		mSet(super.getDefaultDbIndex(), kValues);
	}
	
	@Override
	public <K, V> void mSet(Map<K, V> kValues, long expireSeconds) {
		mSet(super.getDefaultDbIndex(), kValues, expireSeconds);
	}

	@Override
	public <K, V> void mSet(int dbIndex, Map<K, V> kValues) {
		mSet(dbIndex, kValues, 0);
	}
	
	@Override
	public <K, V> void mSet(final int dbIndex, final Map<K, V> kValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(kValues, "Key-value map can not be empty of command [mSet].");
		
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbIndex, kValues);
				RedisRepository repository = select(connection, dbIndex);
				connection.mSet(byteMap);
				setExpireTime(connection, repository, byteMap.keySet(), expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, V> void mSetNX(Map<K, V> kValues) {
		mSetNX(super.getDefaultDbIndex(), kValues);
	}
	
	@Override
	public <K, V> void mSetNX(Map<K, V> kValues, long expireSeconds) {
		mSetNX(super.getDefaultDbIndex(), kValues, expireSeconds);
	}

	@Override
	public <K, V> void mSetNX(int dbIndex, Map<K, V> kValues) {
		mSetNX(dbIndex, kValues, 0);
	}
	
	@Override
	public <K, V> void mSetNX(final int dbIndex, final Map<K, V> kValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(kValues, "Key-value map can not be empty of command [mSetNX].");
		
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbIndex, kValues);
				RedisRepository repository = select(connection, dbIndex);
				connection.mSetNX(byteMap);
				setExpireTime(connection, repository, byteMap.keySet(), expireSeconds);
				return null;
			}
		});
	}
	
	@Override
	public <K, V> void setRange(K key, long offset, V value) {
		setRange(super.getDefaultDbIndex(), key, offset, value);
	}
	
	@Override
	public <K, V> void setRange(K key, long offset, V value, long expireSeconds) {
		setRange(super.getDefaultDbIndex(), key, offset, value, expireSeconds);
	}

	@Override
	public <K, V> void setRange(int dbIndex, K key, long offset, V value) {
		setRange(dbIndex, key, offset, value, 0);
	}
	
	@Override
	public <K, V> void setRange(final int dbIndex, final K key, final long offset, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setRange].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setRange].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				connection.setRange(keyByte, valueSerializer.serialize(value), offset);
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, V> Long append(K key, V value) {
		return append(super.getDefaultDbIndex(), key, value);
	}
	
	@Override
	public <K, V> Long append(K key, V value, long expireSeconds) {
		return append(super.getDefaultDbIndex(), key, value, expireSeconds);
	}

	@Override
	public <K, V> Long append(int dbIndex, final K key, V value) {
		return append(dbIndex, key, value, 0);
	}
	
	@Override
	public <K, V> Long append(final int dbIndex, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [append].");
		AssertUtils.assertNotNull(key, "Value can not be null of command [append].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				Long length = connection.append(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return length;
			}
		});
	}

	@Override
	public <K, V> V get(K key) {
		return get(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> V get(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] keyByte = keySerializer.serialize(key);
				return valueSerializer.deserialize(connection.get(keyByte));
			}
		});
	}

	@Override
	public <K, V> V getRange(K key, long begin, long end) {
		return getRange(super.getDefaultDbIndex(), key, begin, end);
	}

	@Override
	public <K, V> V getRange(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] keyByte = keySerializer.serialize(key);
				return valueSerializer.deserialize(connection.getRange(keyByte, begin, end));
			}
		});
	}

	@Override
	public <K, V> V getSet(K key, V value) {
		return getSet(super.getDefaultDbIndex(), key, value);
	}
	
	@Override
	public <K, V> V getSet(K key, V value, long expireSeconds) {
		return getSet(super.getDefaultDbIndex(), key, value, expireSeconds);
	}

	@Override
	public <K, V> V getSet(int dbIndex, K key, V value) {
		return getSet(dbIndex, key, value, 0);
	}
	
	@Override
	public <K, V> V getSet(final int dbIndex, final K key, final V value, final long expireSeconds) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] keyByte = keySerializer.serialize(key);
				V result = valueSerializer.deserialize(connection.getSet(keyByte, valueSerializer.serialize(value)));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> List<V> mGet(K[] keys) {
		return mGet(super.getDefaultDbIndex(), keys);
	}

	@Override
	public <K, V> List<V> mGet(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				List<byte[]> valueBytes = connection.mGet(serializeKeysToArray(dbIndex, keys));
				return deserializeValueByteToList(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K, V> List<V> mGet(Collection<K> keys) {
		return mGet(super.getDefaultDbIndex(), keys);
	}

	@Override
	public <K, V> List<V> mGet(int dbIndex, Collection<K> keys) {
		return mGet(dbIndex, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long strLen(K key) {
		return strLen(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Long strLen(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.strLen(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K> Long decr(K key) {
		return decr(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Long decr(final int dbIndex, final K key) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [decr].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.decr(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Long decrBy(K key, long value) {
		return decrBy(super.getDefaultDbIndex(), key, value);
	}

	@Override
	public <K> Long decrBy(final int dbIndex, final K key, final long value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [decrBy].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.decrBy(keySerializer.serialize(key), value);
			}
		});
	}

	@Override
	public <K> Long incr(K key) {
		return incr(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Long incr(final int dbIndex, final K key) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [incr].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.incr(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Long incrBy(K key, long value) {
		return incrBy(super.getDefaultDbIndex(), key, value);
	}

	@Override
	public <K> Long incrBy(final int dbIndex, final K key, final long value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [incrBy].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.incrBy(keySerializer.serialize(key), value);
			}
		});
	}
	
	@Override
	public <K, F, V> Boolean hSet(K key, F field, V value) {
		return hSet(super.getDefaultDbIndex(), key, field, value);
	}
	
	@Override
	public <K, F, V> Boolean hSet(K key, F field, V value, long expireSeconds) {
		return hSet(super.getDefaultDbIndex(), key, field, value, expireSeconds);
	}

	@Override
	public <K, F, V> Boolean hSet(int dbIndex, K key, F field, V value) {
		return hSet(dbIndex, key, field, value, 0);
	}
	
	@Override
	public <K, F, V> Boolean hSet(final int dbIndex, final K key,
			final F field, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hSet].");
		AssertUtils.assertNotNull(field, "Field can not be null of command [hSet].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [hSet].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer fieldKeySerializer = selectHashKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				Boolean result = connection.hSet(keyByte, fieldKeySerializer.serialize(field),
						valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}
	
	@Override
	public <K, F, V> Boolean hSetNX(K key, F field, V value) {
		return hSetNX(super.getDefaultDbIndex(), key, field, value);
	}
	
	@Override
	public <K, F, V> Boolean hSetNX(K key, F field, V value, long expireSeconds) {
		return hSetNX(super.getDefaultDbIndex(), key, field, value, expireSeconds);
	}

	@Override
	public <K, F, V> Boolean hSetNX(int dbIndex, K key, F field, V value) {
		return hSetNX(super.getDefaultDbIndex(), key, field, value, 0);
	}
	
	@Override
	public <K, F, V> Boolean hSetNX(final int dbIndex, final K key,
			final F field, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hSetNX].");
		AssertUtils.assertNotNull(field, "Field can not be null of command [hSetNX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [hSetNX].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer fieldKeySerializer = selectHashKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				Boolean result = connection.hSetNX(keyByte, fieldKeySerializer.serialize(field),
						valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return result;
			}
		});
	}

	@Override
	public <K, F, V> void hMSet(K key, Map<F, V> fValues) {
		hMSet(super.getDefaultDbIndex(), key, fValues);
	}
	
	@Override
	public <K, F, V> void hMSet(K key, Map<F, V> fValues, long expireSeconds) {
		hMSet(super.getDefaultDbIndex(), key, fValues, expireSeconds);
	}

	@Override
	public <K, F, V> void hMSet(int dbIndex, K key, Map<F, V> fValues) {
		hMSet(dbIndex, key, fValues, 0);
	}
	
	@Override
	public <K, F, V> void hMSet(final int dbIndex, final K key,
			final Map<F, V> fValues, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hMSet].");
		AssertUtils.assertNotEmpty(fValues, "Field-value map can not be empty of command [hMSet].");
				
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				connection.hMSet(keyByte, serializeFiledValueToByteMap(dbIndex, fValues));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, F> Long hDel(K key, F filed) {
		return hDel(super.getDefaultDbIndex(), key, filed);
	}

	@Override
	public <K, F> Long hDel(int dbIndex, K key, F filed) {
		if (key == null || filed == null)
			return 0L;
		
		return hDel(dbIndex, key, new Object[] { filed });
	}

	@Override
	public <K, F> Long hDel(K key, F[] fileds) {
		return null;
	}

	@Override
	public <K, F> Long hDel(final int dbIndex, final K key, final F[] fileds) {
		if (key == null || ArrayUtils.isEmpty(fileds))
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return NumberUtils.safeLong(connection.hDel(keySerializer.serialize(key), 
						serializeFiledsToArray(dbIndex, fileds)));
			}
		});
	}

	@Override
	public <K, F> Long hDel(K key, Collection<F> fileds) {
		return hDel(super.getDefaultDbIndex(), key, fileds);
	}

	@Override
	public <K, F> Long hDel(int dbIndex, K key, Collection<F> fileds) {
		return hDel(dbIndex, key, CollectionUtils.toObjectArray(fileds));
	}

	@Override
	public <K, F> Boolean hExists(K key, F filed) {
		return hExists(super.getDefaultDbIndex(), key, filed);
	}

	@Override
	public <K, F> Boolean hExists(final int dbIndex, final K key, final F filed) {
		if (key == null || filed == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.hExists(keySerializer.serialize(key), hashKeySerializer.serialize(filed));
			}
		});
	}

	@Override
	public <K, F, V> V hGet(K key, F filed) {
		return hGet(super.getDefaultDbIndex(), key, filed);
	}

	@Override
	public <K, F, V> V hGet(final int dbIndex, final K key, final F filed) {
		if (key == null || filed == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbIndex);
		final Serializer hashValueSerializer = selectHashValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] hashValueByte = connection.hGet(keySerializer.serialize(key), hashKeySerializer.serialize(filed));
				return hashValueSerializer.deserialize(hashValueByte);
			}
		});
	}

	@Override
	public <K, F, V> Map<F, V> hGetAll(K key) {
		return hGetAll(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, F, V> Map<F, V> hGetAll(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Map<F, V>>() {

			@Override
			public Map<F, V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Map<byte[], byte[]> fieldValueBytes = connection.hGetAll(keySerializer.serialize(key));
				return deserializeFiledValueByteToMap(dbIndex, fieldValueBytes);
			}
		});
	}

	@Override
	public <K, F> Set<F> hKeys(K key) {
		return hKeys(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, F> Set<F> hKeys(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<F>>() {

			@Override
			public Set<F> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Set<byte[]> fieldBytes = connection.hKeys(keySerializer.serialize(key));
				return deserializeFiledBytesToSet(dbIndex, fieldBytes);
			}
		});
	}

	@Override
	public <K> Long hLen(K key) {
		return hLen(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Long hLen(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.hLen(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K, F, V> List<V> hMGet(K key, F[] fields) {
		return hMGet(super.getDefaultDbIndex(), key, fields);
	}

	@Override
	public <K, F, V> List<V> hMGet(final int dbIndex, final K key, final F[] fields) {
		if (key == null || ArrayUtils.isEmpty(fields))
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				List<byte[]> haseValueBytes = connection.hMGet(
						keySerializer.serialize(key), serializeFiledsToArray(dbIndex, fields));
				return deserializeHashValueByteToList(dbIndex, haseValueBytes);
			}
		});
	}

	@Override
	public <K, F, V> List<V> hMGet(K key, Collection<F> fields) {
		return hMGet(super.getDefaultDbIndex(), key, fields);
	}

	@Override
	public <K, F, V> List<V> hMGet(int dbIndex, K key, Collection<F> fields) {
		return hMGet(dbIndex, key, CollectionUtils.toObjectArray(fields));
	}

	@Override
	public <K, V> List<V> hVals(K key) {
		return hVals(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> List<V> hVals(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				List<byte[]> hashValueBytes = connection.hVals(keySerializer.serialize(key));
				return deserializeHashValueByteToList(dbIndex, hashValueBytes);
			}
		});
	}
	
	@Override
	public <K, V> Long lInsert(K key, Position where, V pivot, V value) {
		return lInsert(super.getDefaultDbIndex(), key, where, pivot, value);
	}
	
	@Override
	public <K, V> Long lInsert(K key, Position where, V pivot, V value, long expireSeconds) {
		return lInsert(super.getDefaultDbIndex(), key, where, pivot, value, expireSeconds);
	}

	@Override
	public <K, V> Long lInsert(int dbIndex, K key, Position where, V pivot, V value) {
		return lInsert(dbIndex, key, where, pivot, value, 0);
	}
	
	@Override
	public <K, V> Long lInsert(final int dbIndex, final K key, final Position where, final V pivot,
			final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lInsert].");
		AssertUtils.assertNotNull(where, "Insert postion can not be null of command [lInsert].");
		AssertUtils.assertNotNull(pivot, "Postion value can not be null of command [lInsert].");
		AssertUtils.assertNotNull(value, "Insert value can not be null of command [lInsert].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				
				long count = NumberUtils.safeLong(connection.lInsert(keyByte, 
						where, valueSerializer.serialize(pivot), valueSerializer.serialize(value)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> void lSet(K key, long posttion, V value) {
		lSet(super.getDefaultDbIndex(), key, posttion, value);
	}
	
	@Override
	public <K, V> void lSet(K key, long posttion, V value, long expireSeconds) {
		lSet(super.getDefaultDbIndex(), key, posttion, value, expireSeconds);
	}

	@Override
	public <K, V> void lSet(int dbIndex, K key, long posttion, V value) {
		lSet(dbIndex, key, posttion, value, 0);
	}
	
	@Override
	public <K, V> void lSet(final int dbIndex, final K key,
			final long posttion, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lSet].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [lSet].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				connection.lSet(keyByte, posttion, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, V> Long lPush(K key, V value) {
		return lPush(super.getDefaultDbIndex(), key, value);
	}
	
	@Override
	public <K, V> Long lPush(K key, V value, long expireSeconds) {
		return lPush(super.getDefaultDbIndex(), key, value, expireSeconds);
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, V value) {
		return lPush(dbIndex, key, value, 0);
	}
	
	@Override
	public <K, V> Long lPush(int dbIndex, K key, V value, long expireSeconds) {
		AssertUtils.assertNotNull(value, "Value can not be null of command [lPush].");
		return lPush(dbIndex, key, new Object[] { value }, expireSeconds);
	}
	
	@Override
	public <K, V> Long lPush(K key, V[] values) {
		return lPush(super.getDefaultDbIndex(), key, values);
	}
	
	@Override
	public <K, V> Long lPush(K key, V[] values, long expireSeconds) {
		return lPush(super.getDefaultDbIndex(), key, values, expireSeconds);
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, V[] values) {
		return lPush(dbIndex, key, values, 0);
	}
	
	@Override
	public <K, V> Long lPush(final int dbIndex, final K key, final V[] values, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lPush].");
		AssertUtils.assertNotEmpty(values, "Values can not be empty of command [lPush].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] keyByte = keySerializer.serialize(key);
				
				long count = NumberUtils.safeLong(connection.lPush(keyByte, serializeValuesToArray(dbIndex, values)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> Long lPush(K key, Collection<V> values) {
		return lPush(super.getDefaultDbIndex(), key, values); 
	}
	
	@Override
	public <K, V> Long lPush(K key, Collection<V> values, long expireSeconds) {
		return lPush(super.getDefaultDbIndex(), key, values, expireSeconds); 
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, Collection<V> values) {
		return lPush(dbIndex, key, CollectionUtils.toObjectArray(values), 0);
	}
	
	@Override
	public <K, V> Long lPush(int dbIndex, K key, Collection<V> values, long expireSeconds) {
		return lPush(dbIndex, key, CollectionUtils.toObjectArray(values), expireSeconds);
	}

	@Override
	public <K, V> Long lPushX(K key, V value) {
		return lPushX(super.getDefaultDbIndex(), key, value);
	}
	
	@Override
	public <K, V> Long lPushX(K key, V value, long expireSeconds) {
		return lPushX(super.getDefaultDbIndex(), key, value, expireSeconds);
	}

	@Override
	public <K, V> Long lPushX(int dbIndex, K key, V value) {
		return lPushX(dbIndex, key, value, 0);
	}
	
	@Override
	public <K, V> Long lPushX(final int dbIndex, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lPushX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [lPushX].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				
				long count = NumberUtils.safeLong(connection.lPushX(keyByte, valueSerializer.serialize(value)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> V lIndex(K key, long index) {
		return lIndex(super.getDefaultDbIndex(), key, index);
	}

	@Override
	public <K, V> V lIndex(final int dbIndex, final K key, final long index) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] valueByte = connection.lIndex(keySerializer.serialize(key), index);
				return valueSerializer.deserialize(valueByte);
			}
			
		});
	}

	@Override
	public <K> Long lLen(K key) {
		return lLen(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Long lLen(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				return connection.lLen(keySerializer.serialize(key));
			}
			
		});
	}

	@Override
	public <K, V> V lPop(K key) {
		return lPop(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> V lPop(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] valueByte = connection.lPop(keySerializer.serialize(key));
				return valueSerializer.deserialize(valueByte);
			}
		});
	}

	@Override
	public <K, V> List<V> lRange(K key, long begin, long end) {
		return lRange(super.getDefaultDbIndex(), key, begin, end);
	}

	@Override
	public <K, V> List<V> lRange(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				List<byte[]> valueBytes = connection.lRange(keySerializer.serialize(key), begin, end);
				return deserializeValueByteToList(dbIndex, valueBytes);
			}
		});
	}
	
	@Override
	public <K, V> List<V> lRangeAll(K key) {
		return lRangeAll(super.getDefaultDbIndex(), key);
	}
	
	@Override
	public <K, V> List<V> lRangeAll(int dbIndex, K key) {
		return lRange(dbIndex, key, 0 , -1);
	}

	@Override
	public <K, V> Long lRem(K key, long count, V value) {
		return lRem(super.getDefaultDbIndex(), key, count, value);
	}

	@Override
	public <K, V> Long lRem(final int dbIndex, final K key, final long count, final V value) {
		if (key == null || value == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				return connection.lRem(keySerializer.serialize(key), count,
						valueSerializer.serialize(value));
			}
		});
	}

	@Override
	public <K, V> Long lRemAll(K key, V value) {
		return lRemAll(super.getDefaultDbIndex(), key, value);
	}

	@Override
	public <K, V> Long lRemAll(int dbIndex, K key, V value) {
		return lRem(dbIndex, key, 0L, value);
	}

	@Override
	public <K> void lTrim(K key, long begin, long end) {
		lTrim(super.getDefaultDbIndex(), key, begin, end);
	}

	@Override
	public <K> void lTrim(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				connection.lTrim(keySerializer.serialize(key), begin, end);
				return null;
			}
		});
	}
	
	@Override
	public <K, V> Long rPush(K key, V value) {
		return rPush(super.getDefaultDbIndex(), key, value);
	}
	
	@Override
	public <K, V> Long rPush(K key, V value, long expireSeconds) {
		return rPush(super.getDefaultDbIndex(), key, value, expireSeconds);
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, V value) {
		return rPush(dbIndex, key, value, 0);
	}
	
	@Override
	public <K, V> Long rPush(int dbIndex, K key, V value, long expireSeconds) {
		AssertUtils.assertNotNull(value, "Value can not be null of command [rPush].");
		return rPush(dbIndex, key, new Object[] { value }, expireSeconds);
	}
	
	@Override
	public <K, V> Long rPush(K key, V[] values) {
		return rPush(super.getDefaultDbIndex(), key, values);
	}
	
	@Override
	public <K, V> Long rPush(K key, V[] values, long expireSeconds) {
		return rPush(super.getDefaultDbIndex(), key, values, expireSeconds);
	}

	@Override
	public <K, V> Long rPush(int dbIndex, final K key, final V[] values) {
		return rPush(dbIndex, key, values, 0);
	}
	
	@Override
	public <K, V> Long rPush(final int dbIndex, final K key, final V[] values, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [rPush].");
		AssertUtils.assertNotEmpty(values, "Values can not be empty of command [rPush].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				
				long count = NumberUtils.safeLong(connection.rPush(keyByte, serializeValuesToArray(dbIndex, values)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> Long rPush(K key, Collection<V> values) {
		return rPush(super.getDefaultDbIndex(), key, values);
	}
	
	@Override
	public <K, V> Long rPush(K key, Collection<V> values, long expireSeconds) {
		return rPush(super.getDefaultDbIndex(), key, values, expireSeconds);
	}
	
	@Override
	public <K, V> Long rPush(int dbIndex, K key, Collection<V> values) {
		return rPush(dbIndex, key, values, 0);
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, Collection<V> values, long expireSeconds) {
		return rPush(dbIndex, key, CollectionUtils.toObjectArray(values), expireSeconds);
	}

	@Override
	public <K, V> Long rPushX(K key, V value) {
		return rPushX(super.getDefaultDbIndex(), key, value);
	}
	
	@Override
	public <K, V> Long rPushX(K key, V value, long expireSeconds) {
		return rPushX(super.getDefaultDbIndex(), key, value, expireSeconds);
	}

	@Override
	public <K, V> Long rPushX(int dbIndex, K key, V value) {
		return rPushX(dbIndex, key, value, 0);
	}
	
	@Override
	public <K, V> Long rPushX(final int dbIndex, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [rPushX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [rPushX].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				
				long count = NumberUtils.safeLong(connection.rPushX(keyByte, valueSerializer.serialize(value)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> V rPopLPush(K srcKey, K destKey) {
		return rPopLPush(super.getDefaultDbIndex(), srcKey, destKey);
	}
	
	@Override
	public <K, V> V rPopLPush(K srcKey, K destKey, long expireSeconds) {
		return rPopLPush(super.getDefaultDbIndex(), srcKey, destKey, expireSeconds);
	}

	@Override
	public <K, V> V rPopLPush(int dbIndex, K srcKey, K destKey) {
		return rPopLPush(dbIndex, srcKey, destKey, 0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V rPopLPush(final int dbIndex, final K srcKey, final K destKey, final long expireSeconds) {
		AssertUtils.assertNotNull(srcKey, "Source key can not be null of command [rPopLPush].");
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [rPopLPush].");
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] destKeyByte = keySerializer.serialize(destKey);
				RedisRepository repository = select(connection, dbIndex);
				// 将源列表中最后一个元素取出后存入目标列表
				byte[] destValueByte = connection.rPopLPush(keySerializer.serialize(srcKey), destKeyByte);
				// 设置目标键的过期时间
				setExpireTime(connection, repository, destKeyByte, expireSeconds);
				return (V) selectValueSerializer(dbIndex).deserialize(destValueByte);
			}
		});
	}

	@Override
	public <K, V> V rPop(K key) {
		return rPop(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> V rPop(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] valueByte = connection.rPop(keySerializer.serialize(key));
				return valueSerializer.deserialize(valueByte);
			}
		});
	}
	
	@Override
	public <K, V> Long sAdd(K key, V member) {
		return sAdd(super.getDefaultDbIndex(), key, member);
	}
	
	@Override
	public <K, V> Long sAdd(K key, V member, long expireSeconds) {
		return sAdd(super.getDefaultDbIndex(), key, member, expireSeconds);
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, V member) {
		return sAdd(dbIndex, key, member, 0);
	}
	
	@Override
	public <K, V> Long sAdd(int dbIndex, K key, V member, long expireSeconds) {
		AssertUtils.assertNotNull(member, "Member can not be null of command [sAdd].");
		Collection<V> members = CollectionUtils.newArrayList();
		members.add(member);
		return sAdd(dbIndex, key, members, expireSeconds);
	}
	
	@Override
	public <K, V> Long sAdd(K key, V[] members) {
		return sAdd(super.getDefaultDbIndex(), key, members);
	}
	
	@Override
	public <K, V> Long sAdd(K key, V[] members, long expireSeconds) {
		return sAdd(super.getDefaultDbIndex(), key, members, expireSeconds);
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, V[] members) {
		return sAdd(dbIndex, key, members, 0);
	}
	
	@Override
	public <K, V> Long sAdd(final int dbIndex, final K key, final V[] members, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sAdd].");
		AssertUtils.assertNotEmpty(members, "Members can not be empty of command [sAdd].");
				
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] keyByte = keySerializer.serialize(key);
				
				long count = NumberUtils.safeLong(connection.sAdd(keyByte, serializeValuesToArray(dbIndex, members)));
				if (count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, V> Long sAdd(K key, Collection<V> members) {
		return sAdd(super.getDefaultDbIndex(), key, members);
	}
	
	@Override
	public <K, V> Long sAdd(K key, Collection<V> members, long expireSeconds) {
		return sAdd(super.getDefaultDbIndex(), key, members, expireSeconds);
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, Collection<V> members) {
		return sAdd(dbIndex, key, members, 0);
	}
	
	@Override
	public <K, V> Long sAdd(int dbIndex, K key, Collection<V> members, long expireSeconds) {
		return sAdd(dbIndex, key, CollectionUtils.toObjectArray(members), expireSeconds);
	}

	@Override
	public <K> Long sCard(K key) {
		return sCard(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Long sCard(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.sCard(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K, V> Set<V> sDiff(K[] keys) {
		return sDiff(super.getDefaultDbIndex(), keys);
	}

	@Override
	public <K, V> Set<V> sDiff(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[][] keyBytes = serializeKeysToArray(dbIndex, keys);
				Set<byte[]> valueBytes = connection.sDiff(keyBytes);
				return deserializeValueByteToSet(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K, V> Set<V> sDiff(Collection<K> keys) {
		return sDiff(super.getDefaultDbIndex(), keys);
	}
	
	@Override
	public <K, V> Set<V> sDiff(int dbIndex, Collection<K> keys) {
		return sDiff(dbIndex, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long sDiffStore(K destKey, K[] keys) {
		return sDiffStore(super.getDefaultDbIndex(), destKey, keys);
	}
	
	@Override
	public <K> Long sDiffStore(K destKey, K[] keys, long expireSeconds) {
		return sDiffStore(super.getDefaultDbIndex(), destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long sDiffStore(int dbIndex, K destKey, K[] keys) {
		return sDiffStore(dbIndex, destKey, keys, 0);
	}
	
	@Override
	public <K> Long sDiffStore(final int dbIndex, final K destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sDiffStore].");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [sDiffStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sDiffStore(destKeyByte, serializeKeysToArray(dbIndex, keys));
				setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				return result;
			}
		});
	}

	@Override
	public <K> Long sDiffStore(K destKey, Collection<K> keys) {
		return sDiffStore(super.getDefaultDbIndex(), destKey, keys);
	}
	
	@Override
	public <K> Long sDiffStore(K destKey, Collection<K> keys, long expireSeconds) {
		return sDiffStore(super.getDefaultDbIndex(), destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long sDiffStore(int dbIndex, K destKey, Collection<K> keys) {
		return sDiffStore(dbIndex, destKey, keys, 0);
	}
	
	@Override
	public <K> Long sDiffStore(int dbIndex, K destKey, Collection<K> keys, long expireSeconds) {
		return sDiffStore(dbIndex, destKey, CollectionUtils.toObjectArray(keys), expireSeconds);
	}

	@Override
	public <K, V> Set<V> sInter(K[] keys) {
		return sInter(super.getDefaultDbIndex(), keys);
	}

	@Override
	public <K, V> Set<V> sInter(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[][] keyBytes = serializeKeysToArray(dbIndex, keys);
				Set<byte[]> valueBytes = connection.sInter(keyBytes);
				return deserializeValueByteToSet(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K, V> Set<V> sInter(Collection<K> keys) {
		return sInter(super.getDefaultDbIndex(), keys);
	}

	@Override
	public <K, V> Set<V> sInter(int dbIndex, Collection<K> keys) {
		return sInter(dbIndex, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long sInterStore(K destKey, K[] keys) {
		return sInterStore(super.getDefaultDbIndex(), destKey, keys);
	}
	
	@Override
	public <K> Long sInterStore(K destKey, K[] keys, long expireSeconds) {
		return sInterStore(super.getDefaultDbIndex(), destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long sInterStore(int dbIndex, K destKey, K[] keys) {
		return sInterStore(dbIndex, destKey, keys, 0);
	}
	
	@Override
	public <K> Long sInterStore(final int dbIndex, final K destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sInterStore].");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [sInterStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sInterStore(destKeyByte, serializeKeysToArray(dbIndex, keys));
				setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				return result;
			}
		});
	}

	@Override
	public <K> Long sInterStore(K destKey, Collection<K> keys) {
		return sInterStore(super.getDefaultDbIndex(), destKey, keys);
	}
	
	@Override
	public <K> Long sInterStore(K destKey, Collection<K> keys, long expireSeconds) {
		return sInterStore(super.getDefaultDbIndex(), destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long sInterStore(int dbIndex, K destKey, Collection<K> keys) {
		return sInterStore(dbIndex, destKey, keys, 0);
	}
	
	@Override
	public <K> Long sInterStore(int dbIndex, K destKey, Collection<K> keys, long expireSeconds) {
		return sInterStore(dbIndex, destKey, CollectionUtils.toObjectArray(keys), expireSeconds);
	}
	
	@Override
	public <K, V> Set<V> sUnion(K[] keys) {
		return sUnion(super.getDefaultDbIndex(), keys);
	}

	@Override
	public <K, V> Set<V> sUnion(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[][] keyBytes = serializeKeysToArray(dbIndex, keys);
				Set<byte[]> valueBytes = connection.sUnion(keyBytes);
				return deserializeValueByteToSet(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K, V> Set<V> sUnion(Collection<K> keys) {
		return sUnion(super.getDefaultDbIndex(), keys);
	}

	@Override
	public <K, V> Set<V> sUnion(int dbIndex, Collection<K> keys) {
		return sUnion(dbIndex, CollectionUtils.toObjectArray(keys));
	}
	
	@Override
	public <K> Long sUnionStore(K destKey, K[] keys) {
		return sUnionStore(super.getDefaultDbIndex(), destKey, keys);
	}
	
	@Override
	public <K> Long sUnionStore(K destKey, K[] keys, long expireSeconds) {
		return sUnionStore(super.getDefaultDbIndex(), destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long sUnionStore(int dbIndex, K destKey, K[] keys) {
		return sUnionStore(dbIndex, destKey, keys, 0);
	}
	
	@Override
	public <K> Long sUnionStore(final int dbIndex, final K destKey,
			final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sUnionStore].");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [sUnionStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sUnionStore(destKeyByte, serializeKeysToArray(dbIndex, keys));
				setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				return result;
			}
		});
	}

	@Override
	public <K> Long sUnionStore(K destKey, Collection<K> keys) {
		return sUnionStore(super.getDefaultDbIndex(), destKey, keys);
	}
	
	@Override
	public <K> Long sUnionStore(K destKey, Collection<K> keys, long expireSeconds) {
		return sUnionStore(super.getDefaultDbIndex(), destKey, keys, expireSeconds);
	}
	
	@Override
	public <K> Long sUnionStore(int dbIndex, K destKey, Collection<K> keys) {
		return sUnionStore(dbIndex, destKey, keys, 0);
	}

	@Override
	public <K> Long sUnionStore(int dbIndex, K destKey, Collection<K> keys, long expireSeconds) {
		return sUnionStore(dbIndex, destKey, CollectionUtils.toObjectArray(keys), expireSeconds);
	}

	@Override
	public <K, V> Boolean sIsMember(K key, V member) {
		return sIsMember(super.getDefaultDbIndex(), key, member);
	}

	@Override
	public <K, V> Boolean sIsMember(final int dbIndex, final K key, final V member) {
		if (key == null || member == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.sIsMember(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> Set<V> sMembers(K key) {
		return sMembers(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> Set<V> sMembers(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				Set<byte[]> memberBytes = connection.sMembers(keySerializer.serialize(key));
				return deserializeValueByteToSet(dbIndex, memberBytes);
			}
		});
	}

	@Override
	public <K, V> Boolean sMove(K srcKey, K destKey, V member) {
		return sMove(super.getDefaultDbIndex(), srcKey, destKey, member);
	}

	@Override
	public <K, V> Boolean sMove(final int dbIndex, final K srcKey, final K destKey, final V member) {
		if (srcKey == null || destKey == null || member == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				return connection.sMove(keySerializer.serialize(srcKey),
						keySerializer.serialize(destKey), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> V sPop(K key) {
		return sPop(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> V sPop(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] memberByte = connection.sPop(keySerializer.serialize(key));
				return valueSerializer.deserialize(memberByte);
			}
		});
	}

	@Override
	public <K, V> V sRandMember(K key) {
		return sRandMember(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> V sRandMember(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] memberByte = connection.sRandMember(keySerializer.serialize(key));
				return valueSerializer.deserialize(memberByte);
			}
		});
	}

	@Override
	public <K, V> Long sRem(K key, V member) {
		return sRem(super.getDefaultDbIndex(), key, member);
	}

	@Override
	public <K, V> Long sRem(int dbIndex, K key, V member) {
		return sRem(super.getDefaultDbIndex(), key, new Object[] { member });
	}

	@Override
	public <K, V> Long sRem(K key, V[] members) {
		return sRem(super.getDefaultDbIndex(), key, members);
	}

	@Override
	public <K, V> Long sRem(final int dbIndex, final K key, final V[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return NumberUtils.safeLong(connection.sRem(keySerializer.serialize(key), 
						serializeValuesToArray(dbIndex, members)));
			}
		});
	}

	@Override
	public <K, V> Long sRem(K key, Collection<V> members) {
		return sRem(super.getDefaultDbIndex(), key, members);
	}

	@Override
	public <K, V> Long sRem(int dbIndex, K key, Collection<V> members) {
		return sRem(dbIndex, key, CollectionUtils.toObjectArray(members));
	}
	
	@Override
	public <K, V> Boolean zAdd(K key, double score, V member) {
		return zAdd(super.getDefaultDbIndex(), key, score, member);
	}
	
	@Override
	public <K, V> Boolean zAdd(K key, double score, V member, long expireSeconds) {
		return zAdd(super.getDefaultDbIndex(), key, score, expireSeconds);
	}

	@Override
	public <K, V> Boolean zAdd(int dbIndex, K key, double score, V member) {
		return zAdd(dbIndex, key, score, member, 0);
	}
	
	@Override
	public <K, V> Boolean zAdd(int dbIndex, K key, double score, V member, long expireSeconds) {
		AssertUtils.assertNotNull(member, "Member can not be null of command [zAdd].");
		Map<Double, V> scoreMembers = MapUtils.newHashMap();
		scoreMembers.put(score, member);
		return zAdd(dbIndex, key, scoreMembers, expireSeconds);
	}

	@Override
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers) {
		return zAdd(super.getDefaultDbIndex(), key, scoreMembers);
	}
	
	@Override
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers, long expireSeconds) {
		return zAdd(super.getDefaultDbIndex(), key, scoreMembers, expireSeconds);
	}

	@Override
	public <K, V> Boolean zAdd(int dbIndex, K key, Map<Double, V> scoreMembers) {
		return zAdd(dbIndex, key, scoreMembers, 0);
	}
	
	@Override
	public <K, V> Boolean zAdd(final int dbIndex, final K key, final Map<Double, V> scoreMembers, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [zAdd].");
		AssertUtils.assertNotEmpty(scoreMembers, "Score-member map can not be empty of command [zAdd].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
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
		return zCard(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K> Long zCard(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zCard(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Long zCount(K key, double minScore, double maxScore) {
		return zCount(super.getDefaultDbIndex(), minScore, maxScore);
	}

	@Override
	public <K> Long zCount(final int dbIndex, final K key, final double minScore, final double maxScore) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zCount(keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRange(K key, long begin, long end) {
		return zRange(super.getDefaultDbIndex(), key, begin, end);
	}

	@Override
	public <K, V> Set<V> zRange(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Set<byte[]> valueBytes = connection.zRange(keySerializer.serialize(key), begin, end);
				return deserializeValueByteToSet(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRangeAll(K key) {
		return zRangeAll(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> Set<V> zRangeAll(int dbIndex, K key) {
		return zRange(dbIndex, key, 0, -1);
	}

	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore) {
		return zRangeByScore(super.getDefaultDbIndex(), key, minScore, maxScore);
	}

	@Override
	public <K, V> Set<V> zRangeByScore(final int dbIndex, final K key, final double minScore, final double maxScore) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {
			
			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Set<byte[]> valueBytes = connection.zRangeByScore(
						keySerializer.serialize(key), minScore, maxScore);
				return deserializeValueByteToSet(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore,
			long offset, long count) {
		return zRangeByScore(super.getDefaultDbIndex(), key, minScore, maxScore, offset, count);
	}

	@Override
	public <K, V> Set<V> zRangeByScore(final int dbIndex, final K key, final double minScore,
			final double maxScore, final long offset, final long count) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {
			
			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Set<byte[]> valueBytes = connection.zRangeByScore(
						keySerializer.serialize(key), minScore, maxScore, offset, count);
				return deserializeValueByteToSet(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore, double maxScore) {
		return zRangeByScoreWithScores(super.getDefaultDbIndex(), key, minScore, maxScore);
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(final int dbIndex,
			final K key, final double minScore, final double maxScore) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {
			
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zRangeByScoreWithScores(keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore,
			double maxScore, long offset, long count) {
		return zRangeByScoreWithScores(super.getDefaultDbIndex(), key, minScore, maxScore, offset, count);
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(final int dbIndex, final K key,
			final double minScore, final double maxScore, final long offset, final long count) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {
			
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zRangeByScoreWithScores(
						keySerializer.serialize(key), minScore, maxScore, offset, count);
			}
		});
	}

	@Override
	public <K, V> Long zRank(K key, V member) {
		return zRank(super.getDefaultDbIndex(), key, member);
	}

	@Override
	public <K, V> Long zRank(final int dbIndex, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zRank(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> Long zRem(K key, V member) {
		return zRem(super.getDefaultDbIndex(), key, member);
	}

	@Override
	public <K, V> Long zRem(int dbIndex, K key, V member) {
		return zRem(dbIndex, key, new Object[] { member });
	}

	@Override
	public <K, V> Long zRem(K key, V[] members) {
		return zRem(super.getDefaultDbIndex(), key, members);
	}

	@Override
	public <K, V> Long zRem(final int dbIndex, final K key, final V[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return NumberUtils.safeLong(connection.zRem(keySerializer.serialize(key), 
						serializeValuesToArray(dbIndex, members)));
			}
		});
	}

	@Override
	public <K, V> Long zRem(K key, Collection<V> members) {
		return zRem(super.getDefaultDbIndex(), key, members);
	}

	@Override
	public <K, V> Long zRem(int dbIndex, K key, Collection<V> members) {
		return zRem(dbIndex, key, CollectionUtils.toObjectArray(members));
	}

	@Override
	public <K> Long zRemRangeByRank(K key, long begin, long end) {
		return zRemRangeByRank(super.getDefaultDbIndex(), key, begin, end);
	}

	@Override
	public <K> Long zRemRangeByRank(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				return connection.zRemRange(keySerializer.serialize(key), begin, end);
			}
		});
	}

	@Override
	public <K> Long zRemRangeByScore(K key, double minScore, double maxScore) {
		return zRemRangeByScore(super.getDefaultDbIndex(), key, minScore, maxScore);
	}

	@Override
	public <K> Long zRemRangeByScore(final int dbIndex, final K key, final double minScore,
			final double maxScore) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				return connection.zRemRangeByScore(keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRevRange(K key, long begin, long end) {
		return zRevRange(super.getDefaultDbIndex(), key, begin, end);
	}

	@Override
	public <K, V> Set<V> zRevRange(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Set<byte[]> valueBytes = connection.zRevRange(keySerializer.serialize(key), begin, end);
				return deserializeValueByteToSet(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRevRangeAll(K key) {
		return zRevRangeAll(super.getDefaultDbIndex(), key);
	}

	@Override
	public <K, V> Set<V> zRevRangeAll(int dbIndex, K key) {
		return zRevRange(dbIndex, key, 0, -1);
	}

	@Override
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore) {
		return zRevRangeByScore(super.getDefaultDbIndex(), key, minScore, maxScore);
	}

	@Override
	public <K, V> Set<V> zRevRangeByScore(final int dbIndex, final K key,
			final double minScore, final double maxScore) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Set<byte[]> valueBytes = connection.zRevRangeByScore(keySerializer.serialize(key), minScore, maxScore);
				return deserializeValueByteToSet(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore, double maxScore) {
		return zRevRangeByScoreWithScores(super.getDefaultDbIndex(), key, minScore, maxScore);
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(final int dbIndex, final K key,
			final double minScore, final double maxScore) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {
			
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zRevRangeByScoreWithScores(
						keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore,
			double maxScore, long offset, long count) {
		return zRevRangeByScoreWithScores(super.getDefaultDbIndex(), key, minScore, maxScore, offset, count);
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(final int dbIndex, final K key,
			final double minScore, final double maxScore, final long offset, final long count) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {

			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zRevRangeByScoreWithScores(
						keySerializer.serialize(key), minScore, maxScore, offset, count);
			}
		});
	}

	@Override
	public <K, V> Long zRevRank(K key, V member) {
		return zRevRank(super.getDefaultDbIndex(), key, member);
	}

	@Override
	public <K, V> Long zRevRank(final int dbIndex, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zRevRank(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> Double zScore(K key, V member) {
		return zScore(super.getDefaultDbIndex(), key, member);
	}

	@Override
	public <K, V> Double zScore(final int dbIndex, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zScore(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K> Long zUnionStore(K destKey, K key) {
		return zUnionStore(super.getDefaultDbIndex(), destKey, key);
	}

	@Override
	public <K> Long zUnionStore(int dbIndex, K destKey, K key) {
		AssertUtils.assertNotNull(key, "Source key can not be null of command [zUnionStore].");
		return zUnionStore(dbIndex, destKey, new Object[] { key });
	}

	@Override
	public <K> Long zUnionStore(K destKey, K[] keys) {
		return zUnionStore(super.getDefaultDbIndex(), destKey, keys);
	}

	@Override
	public <K> Long zUnionStore(final int dbIndex, final K destKey, final K[] keys) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [zUnionStore].");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [zUnionStore].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zUnionStore(keySerializer.serialize(destKey), 
						serializeKeysToArray(dbIndex, keys));
			}
		});
	}

	@Override
	public <K> Long zUnionStore(K destKey, Collection<K> keys) {
		return zUnionStore(super.getDefaultDbIndex(), destKey, keys);
	}

	@Override
	public <K> Long zUnionStore(int dbIndex, K destKey, Collection<K> keys) {
		return zUnionStore(dbIndex, destKey, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, K[] keys) {
		return zUnionStore(super.getDefaultDbIndex(), destKey, aggregate, weights, keys);
	}

	@Override
	public <K> Long zUnionStore(final int dbIndex, final K destKey, final Aggregate aggregate,
			final int[] weights, final K[] keys) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [zUnionStore].");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [zUnionStore].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zUnionStore(keySerializer.serialize(destKey), 
						aggregate, weights, serializeKeysToArray(dbIndex, keys));
			}
		});
	}

	@Override
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys) {
		return zUnionStore(super.getDefaultDbIndex(), destKey, aggregate, weights, keys);
	}

	@Override
	public <K> Long zUnionStore(int dbIndex, K destKey, Aggregate aggregate,
			int[] weights, Collection<K> keys) {
		return zUnionStore(dbIndex, destKey, aggregate, weights, CollectionUtils.toObjectArray(keys));
	}
	
	@Override
	public <K> Long zInterStore(K destKey, K srcKey) {
		return zInterStore(super.getDefaultDbIndex(), destKey, srcKey);
	}

	@Override
	public <K> Long zInterStore(int dbIndex, K destKey, K srcKey) {
		AssertUtils.assertNotNull(srcKey, "Source key can not be null of command [zInterStore].");
		return zInterStore(dbIndex, destKey, new Object[] {srcKey});
	}

	@Override
	public <K> Long zInterStore(K destKey, K[] keys) {
		return zInterStore(super.getDefaultDbIndex(), destKey, keys);
	}

	@Override
	public <K> Long zInterStore(final int dbIndex, final K destKey, final K[] keys) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [zInterStore].");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [zInterStore].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zInterStore(keySerializer.serialize(destKey), 
						serializeKeysToArray(dbIndex, keys));
			}
		});
	}

	@Override
	public <K> Long zInterStore(K destKey, Collection<K> keys) {
		return zInterStore(super.getDefaultDbIndex(), destKey, keys);
	}

	@Override
	public <K> Long zInterStore(int dbIndex, K destKey, Collection<K> keys) {
		return zInterStore(dbIndex, destKey, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, K[] keys) {
		return zInterStore(super.getDefaultDbIndex(), destKey, aggregate, weights, keys);
	}

	@Override
	public <K> Long zInterStore(final int dbIndex, final K destKey, final Aggregate aggregate,
			final int[] weights, final K[] keys) {
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [zInterStore].");
		AssertUtils.assertNotEmpty(keys, "Source keys can not be empty of command [zInterStore].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zInterStore(keySerializer.serialize(destKey), 
						aggregate, weights, serializeKeysToArray(dbIndex, keys));
			}
		});
	}

	@Override
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys) {
		return zInterStore(super.getDefaultDbIndex(), destKey, aggregate, weights, keys);
	}

	@Override
	public <K> Long zInterStore(int dbIndex, K destKey, Aggregate aggregate,
			int[] weights, Collection<K> keys) {
		return zInterStore(dbIndex, destKey, aggregate, weights, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K, V> Double zIncrBy(K key, double increment, V member) {
		return zIncrBy(super.getDefaultDbIndex(), key, increment, member);
	}

	@Override
	public <K, V> Double zIncrBy(final int dbIndex, final K key, final double increment, final V member) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [zIncrBy].");
		AssertUtils.assertNotNull(key, "Member can not be null of command [zIncrBy].");
		
		final Serializer keySerializer = selectKeySerializer(dbIndex);
		final Serializer valueSerializer = selectValueSerializer(dbIndex);
		return super.getRedisTemplate().execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zIncrBy(keySerializer.serialize(key), 
						increment, valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public Long dbSize() {
		return dbSize(super.getDefaultDbIndex());
	}

	@Override
	public Long dbSize(int dbIndex) {
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
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
		this.flushDb(super.getDefaultDbIndex());
	}

	@Override
	public void flushDb(final int dbIndex) {
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				select(connection, dbIndex);
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
