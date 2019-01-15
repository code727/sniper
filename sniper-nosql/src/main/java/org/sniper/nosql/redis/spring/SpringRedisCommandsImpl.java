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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.BooleanUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.serialization.Serializer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

/**
 * Spring Redis命令行实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class SpringRedisCommandsImpl extends SpringRedisSupport implements SpringRedisCommands {
	
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
		
		return super.getRedisTemplate().execute(new RedisCallback<Set<K>>() {
			
			@Override
			public Set<K> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] patternByte = stringSerializer.serialize(StringUtils.isNotEmpty(pattern) ? pattern : StringUtils.ANY);
				Set<byte[]> keyBytes = connection.keys(patternByte);
				return deserializeKeyBytesToSet(dbName, keyBytes, keyType);
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
	public <K> Map<K, Boolean> exists(K[] keys) {
		return exists(null, keys);
	}

	@Override
	public <K> Map<K, Boolean> exists(String dbName, K[] keys) {
		return exists(dbName, ArrayUtils.toList(keys));
	}

	@Override
	public <K> Map<K, Boolean> exists(Collection<K> keys) {
		return exists(null, keys);
	}

	@Override
	public <K> Map<K, Boolean> exists(final String dbName, final Collection<K> keys) {
		if (CollectionUtils.isEmpty(keys))
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Map<K, Boolean>>() {

			@Override
			public Map<K, Boolean> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				
				Map<K, Boolean> result = MapUtils.newLinkedHashMap();
				for (K key : keys) {
					if (key != null)
						result.put(key, connection.exists(keySerializer.serialize(key)));
					else
						result.put(key, false);
				}
				
				return result;
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
	public <K> Boolean expireAt(K key, long unixTimestamp) {
		return expireAt(null, key, unixTimestamp);
	}

	@Override
	public <K> Boolean expireAt(final String dbName, final K key, final long unixTimestamp) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.expireAt(keySerializer.serialize(key), unixTimestamp);
			}
		});
	}
	
	@Override
	public <K> Boolean expireAt(K key, Date date) {
		return expireAt(null, key, date);
	}

	@Override
	public <K> Boolean expireAt(String dbName, K key, Date date) {
		if (date == null)
			return false;
		
		return expireAt(dbName, key, date.getTime() / 1000);
	}
	
	@Override
	public <K> Boolean pExpire(K key, long millis) {
		return pExpire(null, millis);
	}

	@Override
	public <K> Boolean pExpire(final String dbName, final K key, final long millis) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.pExpire(keySerializer.serialize(key), millis);
			}
		});
	}

	@Override
	public <K> Boolean pExpireAt(K key, long timestamp) {
		return pExpireAt(null, key, timestamp);
	}

	@Override
	public <K> Boolean pExpireAt(final String dbName, final K key, final long timestamp) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.pExpireAt(keySerializer.serialize(key), timestamp);
			}
		});
	}

	@Override
	public <K> Boolean pExpireAt(K key, Date date) {
		return pExpireAt(null, key, date);
	}

	@Override
	public <K> Boolean pExpireAt(String dbName, K key, Date date) {
		if (date == null)
			return false;
		
		return pExpireAt(dbName, key, date.getTime());
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
	public <K> Long pTtl(K key) {
		return pTtl(null, key);
	}
	
	@Override
	public <K> Long pTtl(final String dbName, final K key) {
		if (key == null)
			return -2L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.pTtl(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K, V> List<V> sort(K key, SortParameters params) {
		return sort(key, params, null);
	}
	
	public <K, V> List<V> sort(K key, SortParameters params, Class<V> valueType) {
		return sort(null, key, params, valueType);
	}

	@Override
	public <K, V> List<V> sort(String dbName, K key, SortParameters params) {
		return sort(null, key, params, null);
	}
	
	public <K, V> List<V> sort(final String dbName, final K key, final SortParameters params, final Class<V> valueType) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [sort]");
		AssertUtils.assertNotNull(params, "Sort parameters must not be null for command [sort]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				List<byte[]> valueBytes = connection.sort(keySerializer.serialize(key), params);
				return deserializeValueBytesToList(dbName, valueBytes, valueType);
			}
		});
	}

	@Override
	public <K, V> Long sortCount(K key, SortParameters params, K targetKey) {
		return sortCount(null, key, params, targetKey);
	}

	@Override
	public <K, V> Long sortCount(final String dbName, final K key, final SortParameters params, final K targetKey) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [sort]");
		AssertUtils.assertNotNull(params, "Sort parameters must not be null for command [sort]");
		AssertUtils.assertNotNull(targetKey, "Target key must not be null for command [sort]");
		
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
		return sortResult(null, key, params, targetKey, valueType);
	}

	@Override
	public <K, V> List<V> sortResultIn(String dbName, K key, SortParameters params, K targetKey) {
		return sortResult(dbName, key, params, targetKey, null);
	}
	
	@Override
	public <K, V> List<V> sortResult(final String dbName, final K key, final SortParameters params, final K targetKey, final Class<V> valueType) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [sort]");	
		AssertUtils.assertNotNull(params, "Sort parameters must not be null for command [sort]");
		AssertUtils.assertNotNull(targetKey, "Target key must not be null for command [sort]");
		
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
	public <V> List<V> values() {
		return values((String) null);
	}
	
	@Override
	public <V> List<V> values(Class<V> valueType) {
		return values(null, valueType);
	}
	
	@Override
	public <V> List<V> values(String dbName) {
		return values(dbName, null);
	}
	
	@Override
	public <V> List<V> values(String dbName, Class<V> valueType) {
		return valuesByPattern(dbName, null, valueType);
	}

	@Override
	public <V> List<V> valuesByPattern(String pattern) {
		return valuesByPattern(null, pattern);
	}
	
	@Override
	public <V> List<V> valuesByPattern(String pattern, Class<V> valueType) {
		return valuesByPattern(null, pattern, valueType);
	}
	
	@Override
	public <V> List<V> valuesByPattern(String dbName, String pattern) {
		return valuesByPattern(dbName, pattern, null);
	}

	@Override
	public <V> List<V> valuesByPattern(final String dbName, final String pattern, final Class<V> valueType) {
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {
			
			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				/* 第一步：先获取匹配模式的键字节集合 */
				byte[] patternByte = stringSerializer.serialize(StringUtils.isNotEmpty(pattern) ? pattern : StringUtils.ANY);
				Set<byte[]> keyBytes = connection.keys(patternByte);
				
				if (CollectionUtils.isEmpty(keyBytes))
					return null;
				
				/* 第二步：再根据键集合执行mGet命令，批量获取对应的值字节集合，
				 * 最后再将多个值字节反序列化到列表中*/
				List<byte[]> valueBytes = connection.mGet(CollectionUtils.toArray(keyBytes, byte[].class));
				return deserializeValueBytesToList(dbName, valueBytes, valueType);
			}
		});
	}
	
	@Override
	public <K, V> void set(K key, V value) {
		set(key, value, 0);
	}
	
	@Override
	public <K, V> void set(K key, V value, long expireSeconds) {
		set(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> void setIn(String dbName, K key, V value) {
		set(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> void set(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [set]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [set]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				if (expireTime > 0)
					connection.setEx(keySerializer.serialize(key), expireTime, valueSerializer.serialize(value));
				else
					connection.set(keySerializer.serialize(key), valueSerializer.serialize(value));
				
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
		return setNX(key, value, expireSeconds, null);
	}
	
	@Override
	public <K, V> Boolean setNX(K key, V value, long expireTime, TimeUnit timeUnit) {
		return setNX(null, key, value, expireTime, timeUnit);
	}

	@Override
	public <K, V> Boolean setNXIn(String dbName, K key, V value) {
		return setNX(dbName, key, value, 0);
	}	
	
	@Override
	public <K, V> Boolean setNX(String dbName, K key, final V value, long expireSeconds) {
		return setNX(dbName, key, value, expireSeconds, null);
	}
	
	@Override
	public <K, V> Boolean setNX(final String dbName, final K key, final V value, final long expireTime, final TimeUnit timeUnit) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [setNX]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [setNX]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				
				long expireMillis = getExpireMillis(expireTime, timeUnit, repository);
				/* 如果设置的过期时间大于0，则执行原子性的setNX命令，设置键值的同时设置相应的过期时间 
				 * 注意：这里没有使用RedisConnection提供的原子性set方法，因为此方法在设计时没有声明返回结果，会对执行结果的判断造成误差
				 * 因此这里调用的是execute方法， 以客户端执行本地原生命令行的方式进行 */
				if (expireMillis > 0) 
					return connection.execute(SET_COMMAND_NAME, keyByte, valueSerializer.serialize(value), 
							NX_COMMAND_BYTES, PX_COMMAND_BYTES, stringSerializer.serialize(expireMillis)) != null;
				
				return connection.setNX(keyByte, valueSerializer.serialize(value));
			}
		});
	}
	
	@Override
	public <K, V> void setEx(K key, V value) {
		setEx(key, 0, value);
	}

	@Override
	public <K, V> void setEx(K key, long seconds, V value) {
		setEx(null, key, seconds, value);
	}
	
	@Override
	public <K, V> void setExIn(String dbName, K key, V value) {
		setEx(dbName, key, 0, value);	
	}

	@Override
	public <K, V> void setEx(final String dbName, final K key, final long seconds, final V value) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [setEx]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [setEx]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				long expireSeconds = getExpireSeconds(seconds, repository);
				
				if (expireSeconds > 0)
					connection.setEx(keySerializer.serialize(key), expireSeconds, valueSerializer.serialize(value));
				else
					connection.set(keySerializer.serialize(key), valueSerializer.serialize(value));
				
				return null;
			}
		});
	}
	
	@Override
	public <K, V> void pSetEx(K key, V value) {
		pSetEx(key, 0, value);
	}

	@Override
	public <K, V> void pSetEx(K key, long millis, V value) {
		pSetEx(null, key, millis, value);
	}

	@Override
	public <K, V> void pSetExIn(String dbName, K key, V value) {
		pSetEx(dbName, key, 0, value);
	}

	@Override
	public <K, V> void pSetEx(final String dbName, final K key, final long millis, final V value) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [pSetEx]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [pSetEx]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				long expireMillis = getExpireMillis(millis, repository);
				
				if (expireMillis > 0)
					connection.pSetEx(keySerializer.serialize(key), expireMillis, valueSerializer.serialize(value));
				else
					// 毫秒数小于等于0时永不过期
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
		AssertUtils.assertNotEmpty(kValues, "Key-value map must not be empty for command [mSet");
		
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
	public <K, V> Boolean mSetNX(Map<K, V> kValues) {
		return mSetNX(null, kValues);
	}
	
	@Override
	public <K, V> Boolean mSetNX(Map<K, V> kValues, long expireSeconds) {
		return mSetNX(null, kValues, expireSeconds);
	}

	@Override
	public <K, V> Boolean mSetNX(String dbName, Map<K, V> kValues) {
		return mSetNX(dbName, kValues, 0);
	}
	
	@Override
	public <K, V> Boolean mSetNX(final String dbName, final Map<K, V> kValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(kValues, "Key values must not be empty for command [mSetNX]");
		
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbName, kValues);
				RedisRepository repository = select(connection, dbName);
				
				/* 注意：键值设置和过期设置为非原子性操作，因此在实现诸如批量分布式锁这样的应用场景中，此命令可能并不合适。
				 * 另外，mSetNX方法如果返回为null，并不一定代表设置不成功，可能是由于connection使用的管道或队列的形式异步来发送命令的，
				 * 因此在这两种情况下，以同步的方式获取到的结果就为null(参考JedisConnection源代码)。
				 * Boolean判断认为返回为null时也不进行过期时间设置，因为这里的connection是以同步的方式来发送命令的，忽略掉null只是为了防止空指针异常    */
				Boolean success = connection.mSetNX(byteMap);
				if (BooleanUtils.isTrue(success))
					setExpireTime(connection, repository, byteMap.keySet(), expireSeconds);
				
				return success;
			}
		});
	}
	
	@Override
	public <K, V> void setRange(K key, long offset, V value) {
		setRange(key, offset, value, 0);
	}
	
	@Override
	public <K, V> void setRange(K key, long offset, V value, long expireSeconds) {
		setRange(null, key, offset, value, expireSeconds);
	}

	@Override
	public <K, V> void setRangeIn(String dbName, K key, long offset, V value) {
		setRange(dbName, key, offset, value, 0);
	}
	
	@Override
	public <K, V> void setRange(final String dbName, final K key, final long offset, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [setRange]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [setRange]");
		
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
		return append(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long appendIn(String dbName, final K key, V value) {
		return append(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long append(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [append]");
		AssertUtils.assertNotNull(key, "Value must not be null for command [append]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				
				Long length = connection.append(keyByte, valueSerializer.serialize(value));
				// 有新的拼接结果才设置过期时间
				if (length != null && length > 0)
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
		return get(null, key, valueType);
	}

	@Override
	public <K, V> V getIn(String dbName, K key) {
		return get(dbName, key, null);
	}
	
	@Override
	public <K, V> V get(final String dbName, final K key, final Class<V> valueType) {
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
		return getSet(key, value, 0, oldValueType);
	}
	
	@Override
	public <K, V, O> O getSet(K key, V value, long expireSeconds) {
		return getSet(key, value, expireSeconds, null);
	}
	
	@Override
	public <K, V, O> O getSet(K key, V value, long expireSeconds, Class<O> oldValueType) {
		return getSet(null, key, value, expireSeconds, oldValueType);
	}

	@Override
	public <K, V, O> O getSetIn(String dbName, K key, V value) {
		return getSetIn(dbName, key, value, null);
	}
	
	@Override
	public <K, V, O> O getSetIn(String dbName, K key, V value, Class<O> oldValueType) {
		return getSet(dbName, key, value, 0, oldValueType);
	}
	
	@Override
	public <K, V, O> O getSet(String dbName, K key, V value, long expireSeconds) {
		return getSet(dbName, key, value, expireSeconds, null);
	}
	
	@Override
	public <K, V, O> O getSet(final String dbName, final K key, final V value, final long expireSeconds, final Class<O> oldValueType) {
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
				
				O oldValue = deserializeValueByte(dbName, oldValueByte, oldValueType);
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return oldValue;
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
				return deserializeValueBytesToList(dbName, valueBytes, valueType);
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
		AssertUtils.assertNotNull(key, "Key must not be null for command [decr]");
		
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
		AssertUtils.assertNotNull(key, "Key must not be null for command [decrBy]");
		
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
		AssertUtils.assertNotNull(key, "Key must not be null for command [incr]");
		
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
		AssertUtils.assertNotNull(key, "Key must not be null for command [incrBy]");
		
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
	public <K, H, V> Boolean hSet(K key, H hashKey, V value) {
		return hSet(key, hashKey, value, 0);
	}
	
	@Override
	public <K, H, V> Boolean hSet(K key, H hashKey, V value, long expireSeconds) {
		return hSet(null, key, hashKey, value, expireSeconds);
	}

	@Override
	public <K, H, V> Boolean hSetIn(String dbName, K key, H hashKey, V value) {
		return hSet(dbName, key, hashKey, value, 0);
	}
	
	@Override
	public <K, H, V> Boolean hSet(final String dbName, final K key,
			final H hashKey, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [hSet]");
		AssertUtils.assertNotNull(hashKey, "Hash key must not be null for command [hSet]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [hSet]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		final Serializer hashValueSerializer = selectHashValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				
				Boolean success = connection.hSet(keyByte, 
						hashKeySerializer.serialize(hashKey), hashValueSerializer.serialize(value));
				/* 如果返回为null，并不一定代表设置不成功，可能是由于connection使用的管道或队列的形式异步来发送命令的，
				 * 因此在这两种情况下，以同步的方式获取到的结果就为null(参考JedisConnection源代码)。
				 * Boolean判断认为返回为null时也不进行过期时间设置，因为这里的connection是以同步的方式来发送命令的，忽略掉null只是为了防止空指针异常  */
				if (BooleanUtils.isTrue(success))
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return success;
			}
		});
	}
	
	@Override
	public <K, H, V> Boolean hSetNX(K key, H hashKey, V value) {
		return hSetNX(key, hashKey, value, 0);
	}
	
	@Override
	public <K, H, V> Boolean hSetNX(K key, H hashKey, V value, long expireSeconds) {
		return hSetNX(null, key, hashKey, value, expireSeconds);
	}

	@Override
	public <K, H, V> Boolean hSetNXIn(String dbName, K key, H hashKey, V value) {
		return hSetNX(dbName, key, hashKey, value, 0);
	}
	
	@Override
	public <K, H, V> Boolean hSetNX(final String dbName, final K key,
			final H hashKey, final V value, final long expireSeconds) {
		
		AssertUtils.assertNotNull(key, "Key must not be null for command [hSetNX]");
		AssertUtils.assertNotNull(hashKey, "Hash key must not be null for command [hSetNX]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [hSetNX]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				
				Boolean success = connection.hSetNX(keyByte, hashKeySerializer.serialize(hashKey), valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return success;
			}
		});
	}

	@Override
	public <K, H, V> void hMSet(K key, Map<H, V> hashKeyValues) {
		hMSet(null, key, hashKeyValues);
	}
	
	@Override
	public <K, H, V> void hMSet(K key, Map<H, V> hashKeyValues, long expireSeconds) {
		hMSet(null, key, hashKeyValues, expireSeconds);
	}

	@Override
	public <K, H, V> void hMSet(String dbName, K key, Map<H, V> hashKeyValues) {
		hMSet(dbName, key, hashKeyValues, 0);
	}
	
	@Override
	public <K, H, V> void hMSet(final String dbName, final K key,
			final Map<H, V> hashKeyValues, final long expireSeconds) {
		
		AssertUtils.assertNotNull(key, "Key must not be null for command [hMSet]");
		AssertUtils.assertNotEmpty(hashKeyValues, "Hash key values must not be empty for command [hMSet]");
				
		final Serializer keySerializer = selectKeySerializer(dbName);
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbName);
				connection.hMSet(keyByte, serializeHashKeyValuesToByteMap(dbName, hashKeyValues));
				
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, H> Long hDel(K key, H hashKey) {
		return hDel(null, key, hashKey);
	}

	@Override
	public <K, H> Long hDel(String dbName, K key, H hashKey) {
		if (key == null || hashKey == null)
			return 0L;
		
		return hDel(dbName, key, new Object[] { hashKey });
	}

	@Override
	public <K, H> Long hDel(K key, H[] hashKeys) {
		return hDel(null, key, hashKeys);
	}

	@Override
	public <K, H> Long hDel(final String dbName, final K key, final H[] hashKeys) {
		if (key == null || ArrayUtils.isEmpty(hashKeys))
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hDel(keySerializer.serialize(key), serializeHashKeysToArray(dbName, hashKeys));
			}
		});
	}

	@Override
	public <K, H> Long hDel(K key, Collection<H> hashKeys) {
		return hDel(null, key, hashKeys);
	}

	@Override
	public <K, H> Long hDel(String dbName, K key, Collection<H> hashKeys) {
		return hDel(dbName, key, CollectionUtils.toObjectArray(hashKeys));
	}

	@Override
	public <K, H> Boolean hExists(K key, H hashKey) {
		return hExists(null, key, hashKey);
	}

	@Override
	public <K, H> Boolean hExists(final String dbName, final K key, final H hashKey) {
		if (key == null || hashKey == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hExists(keySerializer.serialize(key), hashKeySerializer.serialize(hashKey));
			}
		});
	}

	@Override
	public <K, H, V> V hGet(K key, H hashKey) {
		return hGet(key, hashKey, null);
	}
	
	@Override
	public <K, H, V> V hGet(K key, H hashKey, Class<V> valueType) {
		return hGet(null, key, hashKey, valueType);
	}

	@Override
	public <K, H, V> V hGetIn(String dbName, K key, H hashKey) {
		return hGet(dbName, key, hashKey, null);
	}
	
	@Override
	public <K, H, V> V hGet(final String dbName, final K key, final H hashKey, final Class<V> valueType) {
		if (key == null || hashKey == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				byte[] hashValueByte = connection.hGet(keySerializer.serialize(key), hashKeySerializer.serialize(hashKey));
				return deserializeHashValueByte(dbName, hashValueByte, valueType);
			} 
		});
	}

	@Override
	public <K, H, V> Map<H, V> hGetAll(K key) {
		return hGetAll(key, null);
	}
	
	@Override
	public <K, H, V> Map<H, V> hGetAll(K key, Class<V> valueType) {
		return hGetAll(key, null, valueType);
	}
	
	@Override
	public <K, H, V> Map<H, V> hGetAll(K key, Class<H> hashKeyType, Class<V> valueType) {
		return hGetAll(null, key, hashKeyType, valueType);
	}

	@Override
	public <K, H, V> Map<H, V> hGetAllIn(String dbName, K key) {
		return hGetAllIn(dbName, key, null);
	}
	
	@Override
	public <K, H, V> Map<H, V> hGetAllIn(String dbName, K key, Class<V> valueType) {
		return hGetAll(dbName, key, null, valueType);
	}
	
	@Override
	public <K, H, V> Map<H, V> hGetAll(final String dbName, final K key, final Class<H> hashKeyType, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Map<H, V>>() {

			@Override
			public Map<H, V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Map<byte[], byte[]> hashKeyValueBytes = connection.hGetAll(keySerializer.serialize(key));
				return deserializeHashKeyValueBytesToMap(dbName, hashKeyValueBytes, hashKeyType, valueType);
			}
		});
	}

	@Override
	public <K, H> Set<H> hKeys(K key) {
		return hKeys(key, null);
	}
	
	@Override
	public <K, H> Set<H> hKeys(K key, Class<H> hashKeyType) {
		return hKeys(null, key, hashKeyType);
	}

	@Override
	public <K, H> Set<H> hKeysIn(String dbName, K key) {
		return hKeys(dbName, key, null);
	}
	
	@Override
	public <K, H> Set<H> hKeys(final String dbName, final K key, final Class<H> hashKeyType) {
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<H>>() {

			@Override
			public Set<H> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> hashKeyBytes = connection.hKeys(keySerializer.serialize(key));
				return deserializeHashKeyBytesToSet(dbName, hashKeyBytes, hashKeyType);
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
	public <K, H, V> List<V> hMGet(K key, Collection<H> hashKeys) {
		return hMGet(key, hashKeys, null);
	}
	
	@Override
	public <K, H, V> List<V> hMGet(K key, Collection<H> hashKeys, Class<V> valueType) {
		return hMGet(null, key, hashKeys, valueType);
	}

	@Override
	public <K, H, V> List<V> hMGet(String dbName, K key, Collection<H> hashKeys) {
		return hMGet(dbName, key, hashKeys, null);
	}
	
	@Override
	public <K, H, V> List<V> hMGet(String dbName, K key, Collection<H> hashKeys, Class<V> valueType) {
		return hMGet(dbName, key, CollectionUtils.toObjectArray(hashKeys), valueType);
	}

	@Override
	public <K, H, V> List<V> hMGet(K key, H[] hashKeys) {
		return hMGet(key, hashKeys, null);
	}
	
	@Override
	public <K, H, V> List<V> hMGet(K key, H[] hashKeys, Class<V> valueType) {
		return hMGet(null, key, hashKeys, valueType);
	}

	@Override
	public <K, H, V> List<V> hMGet(String dbName, K key, H[] hashKeys) {
		return hMGet(dbName, key, hashKeys, null);
	}
	
	@Override
	public <K, H, V> List<V> hMGet(final String dbName, final K key, final H[] hashKeys, final Class<V> valueType) {
		if (key == null || ArrayUtils.isEmpty(hashKeys))
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				List<byte[]> haseValueBytes = connection.hMGet(keySerializer.serialize(key), serializeHashKeysToArray(dbName, hashKeys));
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
		return hVals(null, key, valueType);
	}

	@Override
	public <K, V> List<V> hValsIn(String dbName, K key) {
		return hVals(dbName, key, null);
	}
	
	@Override
	public <K, V> List<V> hVals(final String dbName, final K key, final Class<V> valueType) {
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
	public <K, H> Long hIncr(K key, H hashKey) {
		return hIncr(null, key, hashKey);
	}
	
	@Override
	public <K, H> Long hIncr(String dbName, K key, H hashKey) {
		// 实际上Redis并没有提供hIncr命令，这里仍然执行的是hIncrBy命令，只是累加的增量为1
		return hIncrBy(dbName, key, hashKey, 1L);
	}
	
	@Override
	public <K, H> Long hIncrBy(K key, H hashKey, long value) {
		return hIncrBy(null, key, hashKey, value);
	}
	
	@Override
	public <K, H> Long hIncrBy(final String dbName, final K key, final H hashKey, final long value) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [hIncrBy]");
		AssertUtils.assertNotNull(hashKey, "Hash key must not be null for command [hIncrBy]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer hashKeySerializer = selectHashKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hIncrBy(keySerializer.serialize(key), hashKeySerializer.serialize(hashKey), value);
			}
		});
	}
	
	@Override
	public <K, H> Long hDecr(K key, H hashKey) {
		return hDecr(null, key, hashKey);
	}
	
	@Override
	public <K, H> Long hDecr(String dbName, K key, H hashKey) {
		// 实际上Redis并没有提供hDecr命令，这里仍然执行的是hIncrBy命令，只是累加的增量为-1
		return hIncrBy(dbName, key, hashKey, -1L);
	}
	
	@Override
	public <K, H> Long hDecrBy(K key, H hashKey, long value) {
		return hDecrBy(null, key, hashKey, value);
	}
	
	@Override
	public <K, H> Long hDecrBy(String dbName, K key, H hashKey, long value) {
		// 实际上Redis并没有提供hDecrBy命令，这里仍然执行的是hIncrBy命令，只是累加的增量为value的绝对值取反数
		return hIncrBy(dbName, key, hashKey, -value);
	}
	
	@Override
	public <K, V> Long lInsert(K key, Position where, V pivot, V value) {
		return lInsert(key, where, pivot, value, 0);
	}
	
	@Override
	public <K, V> Long lInsert(K key, Position where, V pivot, V value, long expireSeconds) {
		return lInsert(null, key, where, pivot, value, expireSeconds);
	}

	@Override
	public <K, V> Long lInsertIn(String dbName, K key, Position where, V pivot, V value) {
		return lInsert(dbName, key, where, pivot, value, 0);
	}
	
	@Override
	public <K, V> Long lInsert(final String dbName, final K key, final Position where, final V pivot,
			final V value, final long expireSeconds) {
		
		AssertUtils.assertNotNull(key, "Key must not be null for command [lInsert]");
		AssertUtils.assertNotNull(where, "Insert postion must not be null for command [lInsert]");
		AssertUtils.assertNotNull(pivot, "Postion value must not be null for command [lInsert]");
		AssertUtils.assertNotNull(value, "Insert value must not be null for command [lInsert]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.lInsert(keyByte, where, 
						valueSerializer.serialize(pivot), valueSerializer.serialize(value));
				if (count != null && count > 0) 
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
		lSet(null, key, posttion, value, expireSeconds);
	}

	@Override
	public <K, V> void lSetIn(String dbName, K key, long posttion, V value) {
		lSet(dbName, key, posttion, value, 0);
	}
	
	@Override
	public <K, V> void lSet(final String dbName, final K key,
			final long posttion, final V value, final long expireSeconds) {
		
		AssertUtils.assertNotNull(key, "Key must not be null for command [lSet]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [lSet]");
		
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
		return lPush(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long lPushIn(String dbName, K key, V value) {
		return lPush(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long lPush(String dbName, K key, V value, long expireSeconds) {
		AssertUtils.assertNotNull(value, "Value must not be null for command [lPush]");
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
		AssertUtils.assertNotNull(key, "Key must not be null for command [lPush]");
		AssertUtils.assertNotEmpty(values, "Values must not be empty for command [lPush]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] keyByte = keySerializer.serialize(key);
				
				Long count = connection.lPush(keyByte, serializeValuesToArray(dbName, values));
				if (count != null && count > 0)
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
		return lPushX(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long lPushXIn(String dbName, K key, V value) {
		return lPushX(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long lPushX(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [lPushX]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [lPushX]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.lPushX(keyByte, valueSerializer.serialize(value));
				if (count != null && count > 0)
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
		return lPop(null, key, valueType);
	}

	@Override
	public <K, V> V lPopIn(String dbName, K key) {
		return lPop(dbName, key, null);
	}
	
	@Override
	public <K, V> V lPop(final String dbName, final K key, final Class<V> valueType) {
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
				return deserializeValueBytesToList(dbName, valueBytes, valueType);
			}
		});
	}
	
	@Override
	public <K, V> List<V> lRangeAll(K key) {
		return lRangeAll(key, null);
	}
	
	@Override
	public <K, V> List<V> lRangeAll(K key, Class<V> valueType) {
		return lRangeAll(null, key, valueType);
	}
	
	@Override
	public <K, V> List<V> lRangeAllIn(String dbName, K key) {
		return lRangeAll(dbName, key, null);
	}
	
	@Override
	public <K, V> List<V> lRangeAll(String dbName, K key, Class<V> valueType) {
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
		return rPush(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long rPushIn(String dbName, K key, V value) {
		return rPush(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long rPush(String dbName, K key, V value, long expireSeconds) {
		AssertUtils.assertNotNull(value, "Value must not be null for command [rPush]");
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
		AssertUtils.assertNotNull(key, "Key must not be null for command [rPush]");
		AssertUtils.assertNotEmpty(values, "Values must not be empty for command [rPush]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.rPush(keyByte, serializeValuesToArray(dbName, values));
				if (count != null && count > 0)
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
		return rPushX(null, key, value, expireSeconds);
	}

	@Override
	public <K, V> Long rPushXIn(String dbName, K key, V value) {
		return rPushX(dbName, key, value, 0);
	}
	
	@Override
	public <K, V> Long rPushX(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [rPushX]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [rPushX]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.rPushX(keyByte, valueSerializer.serialize(value));
				if (count != null && count > 0)
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
		return rPopLPush(null, srcKey, destKey, expireSeconds);
	}
	
	@Override
	public <S, T, V> V rPopLPush(S srcKey, T destKey, long expireSeconds, Class<V> valueType) {
		return rPopLPush(null, srcKey, destKey, expireSeconds, valueType);
	}

	@Override
	public <S, T, V> V rPopLPushIn(String dbName, S srcKey, T destKey) {
		return rPopLPush(dbName, srcKey, destKey, 0);
	}
	
	@Override
	public <S, T, V> V rPopLPushIn(String dbName, S srcKey, T destKey, Class<V> valueType) {
		return rPopLPush(dbName, srcKey, destKey, 0, valueType);
	}
	
	@Override
	public <S, T, V> V rPopLPush(String dbName, S srcKey, T destKey, long expireSeconds) {
		return rPopLPush(dbName, srcKey, destKey, expireSeconds, null);
	}
	
	@Override
	public <S, T, V> V rPopLPush(final String dbName, final S srcKey, final T destKey, final long expireSeconds, final Class<V> valueType) {
		AssertUtils.assertNotNull(srcKey, "Source key must not be null for command [rPopLPush]");
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [rPopLPush]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] destKeyByte = keySerializer.serialize(destKey);
				RedisRepository repository = select(connection, dbName);
				
				// 将源列表中最后一个元素出列后存入目标列表
				byte[] destValueByte = connection.rPopLPush(srcKey.equals(destKey) ? 
						destKeyByte : keySerializer.serialize(srcKey), destKeyByte);
				
				V value = deserializeValueByte(dbName, destValueByte, valueType);
				if (value != null)
					// 只有当将源列表中出列的元素不为空时才设置目标键的过期时间，因为当为空时，表示源列表可能根本不存在
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return value;
			}
		});
	}

	@Override
	public <K, V> V rPop(K key) {
		return rPop(key, null);
	}
	
	@Override
	public <K, V> V rPop(K key, Class<V> valueType) {
		return rPop(null, key, valueType);
	}

	@Override
	public <K, V> V rPopIn(String dbName, K key) {
		return rPop(dbName, key, null);
	}
	
	@Override
	public <K, V> V rPop(final String dbName, final K key, final Class<V> valueType) {
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
		return sAdd(null, key, member, expireSeconds);
	}

	@Override
	public <K, V> Long sAddIn(String dbName, K key, V member) {
		return sAdd(dbName, key, member, 0);
	}
	
	@Override
	public <K, V> Long sAdd(String dbName, K key, V member, long expireSeconds) {
		AssertUtils.assertNotNull(member, "Member must not be null for command [sAdd]");
		return sAdd(dbName, key, new Object[] { member }, expireSeconds);
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
		AssertUtils.assertNotNull(key, "Key must not be null for command [sAdd]");
		AssertUtils.assertNotEmpty(members, "Members must not be empty for command [sAdd]");
				
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] keyByte = keySerializer.serialize(key);
				
				Long count = connection.sAdd(keyByte, serializeValuesToArray(dbName, members));
				if (count != null && count > 0)
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
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [sDiffStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [sDiffStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count = connection.sDiffStore(destKeyByte, serializeKeysToArray(dbName, keys));
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				
				return count;
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
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [sInterStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [sInterStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count = connection.sInterStore(destKeyByte, serializeKeysToArray(dbName, keys));
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				
				return count;
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
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [sUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [sUnionStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count = connection.sUnionStore(destKeyByte, serializeKeysToArray(dbName, keys));
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);	
				
				return count;
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
		return sMembers(null, key, valueType);
	}

	@Override
	public <K, V> Set<V> sMembersIn(String dbName, K key) {
		return sMembers(dbName, key, null);
	}
	
	@Override
	public <K, V> Set<V> sMembers(final String dbName, final K key, final Class<V> valueType) {
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
		return sMove(srcKey, destKey, member, 0);
	}
	
	@Override
	public <K, T, V> Boolean sMove(K srcKey, T destKey, V member, long expireSeconds) {
		return sMove(null, srcKey, destKey, member, expireSeconds);
	}

	@Override
	public <K, T, V> Boolean sMoveIn(final String dbName, final K srcKey, final T destKey, final V member) {
		return sMove(dbName, srcKey, destKey, member, 0);
	}
	
	public <K, T, V> Boolean sMove(final String dbName, final K srcKey, final T destKey, final V member, final long expireSeconds) {
		if (srcKey == null || destKey == null || member == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Boolean success = connection.sMove(srcKey.equals(destKey) ? 
						destKeyByte : keySerializer.serialize(srcKey), destKeyByte, valueSerializer.serialize(member));
				if (BooleanUtils.isTrue(success)) 
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return success;
			}
		});
	}

	@Override
	public <K, V> V sPop(K key) {
		return sPop(key, null);
	}
	
	@Override
	public <K, V> V sPop(K key, Class<V> valueType) {
		return sPop(null, key, valueType);
	}

	@Override
	public <K, V> V sPopIn(String dbName, K key) {
		return sPop(dbName, key, null);
	}
	
	@Override
	public <K, V> V sPop(final String dbName, final K key, final Class<V> valueType) {
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
		return sRandMember(null, key, valueType);
	}
	
	@Override
	public <K, V> V sRandMemberIn(String dbName, K key) {
		return sRandMember(dbName, key, null);
	}
	
	@Override
	public <K, V> V sRandMember(final String dbName, final K key, final Class<V> valueType) {
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
		if (member == null)
			return 0L;
		
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
				return connection.sRem(keySerializer.serialize(key), serializeValuesToArray(dbName, members));
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
		return zAdd(null, key, score, member, expireSeconds);
	}

	@Override
	public <K, V> Boolean zAddIn(String dbName, K key, double score, V member) {
		return zAdd(dbName, key, score, member, 0);
	}
	
	@Override
	public <K, V> Boolean zAdd(final String dbName, final K key, final double score, final V member, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [zAdd]");
		AssertUtils.assertNotNull(member, "Member must not be null for command [zAdd]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				Boolean success = connection.zAdd(keyByte, score, valueSerializer.serialize(member));
				if (BooleanUtils.isTrue(success))
					setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return success;
			}
		});
	}
	
	@Override
	public <K, V> Long zAdd(K key, Map<V, Double> scoreMembers) {
		return zAdd(null, key, scoreMembers);
	}

	@Override
	public <K, V> Long zAdd(K key, Map<V, Double> scoreMembers, long expireSeconds) {
		return zAdd(null, key, scoreMembers, expireSeconds);
	}

	@Override
	public <K, V> Long zAdd(String dbName, K key, Map<V, Double> scoreMembers) {
		return zAdd(dbName, key, scoreMembers, 0);
	}

	@Override
	public <K, V> Long zAdd(final String dbName, final K key, final Map<V, Double> scoreMembers, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [zAdd]");
		AssertUtils.assertNotEmpty(scoreMembers, "Score members must not be empty for command [zAdd]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				Set<Entry<V, Double>> entrySet = scoreMembers.entrySet();
				Set<Tuple> tuples = CollectionUtils.newLinkedHashSet();
				
				for (Entry<V, Double> entry : entrySet) {
					V member = entry.getKey();
					Double score = entry.getValue();
					
					if (member != null && score != null)
						tuples.add(new DefaultTuple(valueSerializer.serialize(member), score));
				}
				
				if (tuples.size() > 0) {
					byte[] keyByte = keySerializer.serialize(key);
					RedisRepository repository = select(connection, dbName);
					
					Long count = connection.zAdd(keyByte, tuples);
					if (count != null && count > 0)
						setExpireTime(connection, repository, keyByte, expireSeconds);
					
					return count;
				}
				
				return 0L;
			}
		});
	}

	@Override
	public <K, V> Map<V, Boolean> zAdds(K key, Map<V, Double> scoreMembers) {
		return zAdds(null, key, scoreMembers);
	}
	
	@Override
	public <K, V> Map<V, Boolean> zAdds(K key, Map<V, Double> scoreMembers, long expireSeconds) {
		return zAdds(null, key, scoreMembers, expireSeconds);
	}

	@Override
	public <K, V> Map<V, Boolean> zAdds(String dbName, K key, Map<V, Double> scoreMembers) {
		return zAdds(dbName, key, scoreMembers, 0);
	}
	
	@Override
	public <K, V> Map<V, Boolean> zAdds(final String dbName, final K key, final Map<V, Double> scoreMembers, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [zAdd]");
		AssertUtils.assertNotEmpty(scoreMembers, "Score members must not be empty for command [zAdd]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		final Serializer valueSerializer = selectValueSerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Map<V, Boolean>>() {

			@Override
			public Map<V, Boolean> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbName);
				
				Set<Entry<V, Double>> entrySet = scoreMembers.entrySet();
				Map<V, Boolean> result = MapUtils.newLinkedHashMap();
				for (Entry<V, Double> entry : entrySet) {
					V member = entry.getKey();
					Double score = entry.getValue();
					
					if (member != null && score != null)
						result.put(member, connection.zAdd(keyByte, score, valueSerializer.serialize(member)));
					else
						result.put(member, false);
				}
				
				if (result.size() > 0)
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
		return zCount(null, key, minScore, maxScore);
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
		return zRangeAll(null, key, valueType);
	}

	@Override
	public <K, V> Set<V> zRangeAllIn(String dbName, K key) {
		return zRangeAll(dbName, key, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeAll(String dbName, K key, Class<V> valueType) {
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
		if (member == null)
			return 0L;
		
		return zRem(dbName, key, new Object[] {member});
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
				return connection.zRem(keySerializer.serialize(key), serializeValuesToArray(dbName, members));
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
	public <K, V> Long zRemAll(K key) {
		return zRemAll(null, key);
	}
	
	@Override
	public <K, V> Long zRemAll(String dbName, K key) {
		return zRemRangeByRank(dbName, key, 0, -1);
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
				// Redis并无zRemRange命令，内部实际上执行的是zRemRangeByRank命令
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
		return zRevRangeAll(null, key, valueType);
	}

	@Override
	public <K, V> Set<V> zRevRangeAllIn(String dbName, K key) {
		return zRevRangeAll(dbName, key, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeAll(String dbName, K key, Class<V> valueType) {
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
		return zUnionStore(destKey, key, 0);
	}
	
	@Override
	public <K> Long zUnionStore(K destKey, K key, long expireSeconds) {
		return zUnionStore(null, destKey, key, expireSeconds);
	}

	@Override
	public <K> Long zUnionStoreIn(String dbName, K destKey, K key) {
		return zUnionStore(dbName, destKey, key, 0);
	}
	
	@Override
	public <K> Long zUnionStore(String dbName, K destKey, K key, long expireSeconds) {
		AssertUtils.assertNotNull(key, "Source key must not be null for command [zUnionStore]");
		return zUnionStore(dbName, destKey, new Object[] { key }, expireSeconds);
	}

	@Override
	public <K> Long zUnionStore(K destKey, K[] keys) {
		return zUnionStore(destKey, keys, 0);
	}
	
	@Override
	public <K> Long zUnionStore(K destKey, K[] keys, long expireSeconds) {
		return zUnionStore(null, destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, K[] keys) {
		return zUnionStore(dbName, destKey, keys, 0);
	}
	
	@Override
	public <K> Long zUnionStore(final String dbName, final K destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [zUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [zUnionStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] destKeyByte = keySerializer.serialize(destKey);
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.zUnionStore(destKeyByte, serializeKeysToArray(dbName, keys));
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return count;
			}
		});
	}

	@Override
	public <K> Long zUnionStore(K destKey, Collection<K> keys) {
		return zUnionStore(destKey, keys, 0);
	}
	
	@Override
	public <K> Long zUnionStore(K destKey, Collection<K> keys, long expireSeconds) {
		return zUnionStore(null, destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys) {
		return zUnionStore(dbName, destKey, keys, 0);
	}
	
	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys, long expireSeconds) {
		return zUnionStore(dbName, destKey, CollectionUtils.toObjectArray(keys), expireSeconds);
	}

	@Override
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, K[] keys) {
		return zUnionStore(destKey, aggregate, weights, keys, 0);
	}
	
	@Override
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, K[] keys, long expireSeconds) {
		return zUnionStore(null, destKey, aggregate, weights, keys, expireSeconds);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Aggregate aggregate, int[] weights, K[] keys) {
		return zUnionStore(dbName, destKey, aggregate, weights, keys, 0);
	}
	
	@Override
	public <K> Long zUnionStore(final String dbName, final K destKey, final Aggregate aggregate, 
			final int[] weights, final K[] keys, final long expireSeconds) {
		
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [zUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [zUnionStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count = connection.zUnionStore(destKeyByte, aggregate, weights, serializeKeysToArray(dbName, keys));
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return count;
			}
		});
		
	}

	@Override
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys) {
		return zUnionStore(destKey, aggregate, weights, keys, 0);
	}
	
	@Override
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys, long expireSeconds) {
		return zUnionStore(null, destKey, aggregate, weights, keys, expireSeconds);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Aggregate aggregate, int[] weights, Collection<K> keys) {
		return zUnionStore(dbName, destKey, aggregate, weights, keys, 0);
	}
	
	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Aggregate aggregate, int[] weights, 
			Collection<K> keys, long expireSeconds) {
		
		return zUnionStore(dbName, destKey, aggregate, weights, CollectionUtils.toObjectArray(keys), expireSeconds);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, K srcKey) {
		return zInterStore(destKey, srcKey, 0);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, K srcKey, long expireSeconds) {
		return zInterStore(null, destKey, srcKey, expireSeconds);
	}

	@Override
	public <K> Long zInterStoreIn(String dbName, K destKey, K srcKey) {
		return zInterStore(dbName, destKey, srcKey, 0);
	}
	
	@Override
	public <K> Long zInterStore(String dbName, K destKey, K srcKey, long expireSeconds) {
		AssertUtils.assertNotNull(srcKey, "Source key must not be null for command [zInterStore]");
		return zInterStore(dbName, destKey, new Object[] { srcKey }, expireSeconds);
	}

	@Override
	public <K> Long zInterStore(K destKey, K[] keys) {
		return zInterStore(destKey, keys, 0);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, K[] keys, long expireSeconds) {
		return zInterStore(null, destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, K[] keys) {
		return zInterStore(dbName, destKey, keys, 0);
	}
	
	@Override
	public <K> Long zInterStore(final String dbName, final K destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [zInterStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [zInterStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count = connection.zInterStore(destKeyByte, serializeKeysToArray(dbName, keys));
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return count;
			}
		});
	}

	@Override
	public <K> Long zInterStore(K destKey, Collection<K> keys) {
		return zInterStore(destKey, keys, 0);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, Collection<K> keys, long expireSeconds) {
		return zInterStore(null, destKey, keys, expireSeconds);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys) {
		return zInterStore(dbName, destKey, keys, 0);
	}
	
	@Override
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys, long expireSeconds) {
		return zInterStore(dbName, destKey, CollectionUtils.toObjectArray(keys), expireSeconds);
	}

	@Override
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, K[] keys) {
		return zInterStore(destKey, aggregate, weights, keys, 0);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, K[] keys, long expireSeconds) {
		return zInterStore(null, destKey, aggregate, weights, keys, expireSeconds);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, Aggregate aggregate,
			int[] weights, K[] keys) {
		
		return zInterStore(dbName, destKey, aggregate, weights, keys, 0);
	}
	
	@Override
	public <K> Long zInterStore(final String dbName, final K destKey, final Aggregate aggregate, final int[] weights,
			final K[] keys, final long expireSeconds) {
		
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [zInterStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [zInterStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count = connection.zInterStore(destKeyByte, aggregate, weights, serializeKeysToArray(dbName, keys));
				if (count != null && count > 0) 
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return count;
			}
		});
	}

	@Override
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys) {
		return zInterStore(destKey, aggregate, weights, keys, 0);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys, long expireSeconds) {
		return zInterStore(null, destKey, aggregate, weights, keys, expireSeconds);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, Aggregate aggregate,
			int[] weights, Collection<K> keys) {
		
		return zInterStore(dbName, destKey, aggregate, weights, keys, 0);
	}
	
	@Override
	public <K> Long zInterStore(String dbName, K destKey, Aggregate aggregate, 
			int[] weights, Collection<K> keys, long expireSeconds) {
		
		return zInterStore(dbName, destKey, aggregate, weights, CollectionUtils.toObjectArray(keys), expireSeconds);
	}

	@Override
	public <K, V> Double zIncrBy(K key, double increment, V member) {
		return zIncrBy(null, key, increment, member);
	}

	@Override
	public <K, V> Double zIncrBy(final String dbName, final K key, final double increment, final V member) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [zIncrBy]");
		AssertUtils.assertNotNull(member, "Member must not be null for command [zIncrBy]");
		
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
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.dbSize();
			}
		});
	}

	@Override
	public void flushAll() {
		super.getRedisTemplate().execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
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
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
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
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.shutdown();
				return null;
			}
		});
	}

}
