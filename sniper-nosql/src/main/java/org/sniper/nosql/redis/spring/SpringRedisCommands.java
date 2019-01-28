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
import org.sniper.nosql.redis.enums.DataType;
import org.sniper.nosql.redis.enums.ListPosition;
import org.sniper.nosql.redis.model.ZSetTuple;
import org.sniper.nosql.redis.option.Limit;
import org.sniper.nosql.redis.option.SortOptional;
import org.sniper.nosql.redis.option.ZStoreOptional;
import org.sniper.serialization.Serializer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

/**
 * Spring Redis命令行实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Repository
public class SpringRedisCommands extends SpringRedisSupport {
	
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
	public <K> K randomKey(final String dbName, final Class<K> keyType) {
		return super.getRedisTemplate().execute(new RedisCallback<K>() {

			@Override
			public K doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return deserializeKeyByte(dbName, connection.randomKey(), keyType);
			}
		});
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
	public <K> Boolean persist(final String dbName, final K key) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.persist(keySerializer.serialize(key));
			}
		});
	}
	
	@Override
	public <K> Boolean move(final String dbName, final K key, final int dbIndex) {
		if (key == null)
			return false;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.move(keySerializer.serialize(key), dbIndex);
			}
		});
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
	public <K, V> List<V> sortByOptional(final String dbName, final K key, 
			final SortOptional optional, final Class<V> valueType) {
			
		AssertUtils.assertNotNull(key, "Key must not be null for command [sort]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				List<byte[]> valueBytes = connection.sort(keySerializer.serialize(key), toSortParameters(optional));
				return deserializeValueBytesToList(dbName, valueBytes, valueType);
			}
		});
	}
	
	@Override
	public <K> Long sortStoreByOptional(final String dbName, final K key, final SortOptional optional, final K destKey, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [sort]");
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [sort]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count = connection.sort(key.equals(destKey) ? destKeyByte : 
						keySerializer.serialize(key), toSortParameters(optional), destKeyByte);
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return count;
			}
		});
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
				return DataType.resolve(connection.type(keySerializer.serialize(key)).code());
			}
		});
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
	public <K, V> void mSet(final String dbName, final Map<K, V> keyValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(keyValues, "Key values must not be empty for command [mSet");
		
		super.getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbName, keyValues);
				RedisRepository repository = select(connection, dbName);
				
				connection.mSet(byteMap);
				setExpireTime(connection, repository, byteMap.keySet(), expireSeconds);
				
				return null;
			}
		});
	}

	@Override
	public <K, V> Boolean mSetNX(final String dbName, final Map<K, V> keyValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(keyValues, "Key values must not be empty for command [mSetNX]");
		
		return super.getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbName, keyValues);
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
	public <K, V, O> O getSet(final String dbName, final K key, final V value, final long expireSeconds, final Class<O> oldValueType) {
		// TODO 缺少对value为空时的判断
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
	public <K, V> Long lInsert(final String dbName, final K key, final ListPosition where, final V pivot,
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
				
				Long count = connection.lInsert(keyByte, toPosition(where), 
						valueSerializer.serialize(pivot), valueSerializer.serialize(value));
				if (count != null && count > 0) 
					setExpireTime(connection, repository, keyByte, expireSeconds);
					
				return count;
			}
		});
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
				
				Boolean success = connection.sMove(srcKey.equals(destKey) ? destKeyByte : 
						keySerializer.serialize(srcKey), destKeyByte, valueSerializer.serialize(member));
				if (BooleanUtils.isTrue(success)) 
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return success;
			}
		});
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
	public <K, V> Set<V> zRangeByScore(final String dbName, final K key, final double minScore, final double maxScore,
			final Limit limit, final Class<V> valueType) {
			
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {
			
			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> valueBytes = (limit == null
						? connection.zRangeByScore(keySerializer.serialize(key), minScore, maxScore)
						: connection.zRangeByScore(keySerializer.serialize(key), minScore, maxScore, limit.getOffset(), limit.getCount()));
								
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}
	
	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(final String dbName, final K key, final double minScore,
			final double maxScore, final Limit limit, final Class<V> valueType) {
		
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<ZSetTuple<V>>>() {
			
			@Override
			public Set<ZSetTuple<V>> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<Tuple> tuples = (limit == null
						? connection.zRangeByScoreWithScores(keySerializer.serialize(key), minScore, maxScore)
						: connection.zRangeByScoreWithScores(keySerializer.serialize(key), minScore, maxScore, limit.getOffset(), limit.getCount()));
								
				return deserializeTuplesToSet(dbName, tuples, valueType);
			}
		});
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
	public <K, V> Set<V> zRevRangeByScore(final String dbName, final K key, final double minScore,
			final double maxScore, final Limit limit, final Class<V> valueType) {
		
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<V>>() {

			@Override
			public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<byte[]> valueBytes = (limit == null
						? connection.zRevRangeByScore(keySerializer.serialize(key), minScore, maxScore)
						: connection.zRevRangeByScore(keySerializer.serialize(key), minScore, maxScore, limit.getOffset(), limit.getCount()));
				return deserializeValueByteToSet(dbName, valueBytes, valueType);
			}
		});
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(final String dbName, final K key, final double minScore,
			final double maxScore, final Limit limit, final Class<V> valueType) {
		
		if (key == null)
			return null;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Set<ZSetTuple<V>>>() {
			
			@Override
			public Set<ZSetTuple<V>> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				Set<Tuple> tuples = (limit == null
						? connection.zRevRangeByScoreWithScores(keySerializer.serialize(key), minScore, maxScore)
						: connection.zRevRangeByScoreWithScores(keySerializer.serialize(key), minScore, maxScore, limit.getOffset(), limit.getCount()));
								
				return deserializeTuplesToSet(dbName, tuples, valueType);
			}
		});
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
	public <K> Long zRemRangeByRank(final String dbName, final K key, final long begin, final long end) {
		if (key == null)
			return 0L;
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				// 并无zRemRange命令，内部实际上执行的是zRemRangeByRank命令
				return connection.zRemRange(keySerializer.serialize(key), begin, end);
			}
		});
	}

	@Override
	public <K> Long zRemRangeByScore(final String dbName, final K key, final double minScore, final double maxScore) {
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
	public <K> Long zUnionStore(final String dbName, final K destKey, final K[] keys, 
			final ZStoreOptional option, final long expireSeconds) {
		
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [zUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [zUnionStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count;
				if (option != null)
					count = connection.zUnionStore(destKeyByte, toAggregate(option.getAggregate()), 
							option.getWeights(), serializeKeysToArray(dbName, keys));
				else
					count = connection.zUnionStore(destKeyByte, serializeKeysToArray(dbName, keys));
				
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return count;
			}
		});
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
	public <K> Long zInterStore(final String dbName, final K destKey, final K[] keys, 
			final ZStoreOptional option, final long expireSeconds) {
			
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [zInterStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [zInterStore]");
		
		final Serializer keySerializer = selectKeySerializer(dbName);
		return super.getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				
				Long count;
				if (option != null) 
					count = connection.zInterStore(destKeyByte, toAggregate(option.getAggregate()), 
							option.getWeights(), serializeKeysToArray(dbName, keys));
				else
					count = connection.zInterStore(destKeyByte, serializeKeysToArray(dbName, keys));
				
				if (count != null && count > 0)
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return count;
			}
		});
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
