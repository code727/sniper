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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.BooleanUtils;
import org.sniper.commons.util.CollectionUtils;
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
import org.springframework.data.redis.connection.RedisConnection;
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
public class SpringRedisCommands extends SpringRedisSupport {
	
	@Override
	public <K> Set<K> keysByPattern(final String dbName, final String pattern, final Class<K> keyType) {
		final byte[] patternByte = stringSerializer.serialize(StringUtils.isNotEmpty(pattern) ? pattern : StringUtils.ANY);
		Set<byte[]> keyBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {
			
			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.keys(patternByte);
			}
		});
		
		return deserializeKeyBytes(dbName, keyBytes, keyType);
	}
	
	@Override
	public <K> K randomKey(final String dbName, final Class<K> keyType) {
		final byte[] keyByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {
			
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.randomKey();
			}
		});
		
		return deserializeKeyByte(dbName, keyByte, keyType);
	}
	
	@Override
	public <K> Long del(final String dbName, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return 0L;
		
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.del(keyBytes);
			}
		});
	}

	@Override
	public <K> Boolean exists(final String dbName, final K key) {
		if (key == null)
			return false;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.exists(keyByte);
			}
		});
	}
	
	@Override
	public <K> Boolean expire(final String dbName, final K key, final long seconds) {
		if (key == null)
			return false;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.expire(keyByte, seconds);
			}
		});
	}
	
	@Override
	public <K> Boolean expireAt(final String dbName, final K key, final long unixTimestamp) {
		if (key == null)
			return false;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.expireAt(keyByte, unixTimestamp);
			}
		});
	}
	
	@Override
	public <K> Boolean pExpire(final String dbName, final K key, final long millis) {
		if (key == null)
			return false;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.pExpire(keyByte, millis);
			}
		});
	}

	@Override
	public <K> Boolean pExpireAt(final String dbName, final K key, final long timestamp) {
		if (key == null)
			return false;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.pExpireAt(keyByte, timestamp);
			}
		});
	}

	@Override
	public <K> Boolean persist(final String dbName, final K key) {
		if (key == null)
			return false;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.persist(keyByte);
			}
		});
	}
	
	@Override
	public <K> Boolean move(final String dbName, final K key, final int dbIndex) {
		if (key == null)
			return false;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.move(keyByte, dbIndex);
			}
		});
	}

	@Override
	public <K> Long ttl(final String dbName, final K key) {
		if (key == null)
			return -2L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.ttl(keyByte);
			}
		});
	}
	
	@Override
	public <K> Long pTtl(final String dbName, final K key) {
		if (key == null)
			return -2L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.pTtl(keyByte);
			}
		});
	}
	
	@Override
	public <K, V> List<V> sortByOptional(final String dbName, final K key, 
			final SortOptional optional, final Class<V> valueType) {
			
		AssertUtils.assertNotNull(key, "Key must not be null for command [sort]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final SortParameters sortParameters = toSortParameters(optional);
		List<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<List<byte[]>>() {

			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sort(keyByte, sortParameters);
			}
		});
		
		return deserializeValueBytesToList(dbName, valueBytes, valueType);
	}
	
	@Override
	public <K> Long sortStoreByOptional(final String dbName, final K key, final SortOptional optional, final K destKey, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [sort]");
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [sort]");
		
		Serializer keySerializer = selectKeySerializer(dbName);
		final byte[] keyByte = keySerializer.serialize(key);
		final byte[] destKeyByte = (destKey.equals(key) ? keyByte : keySerializer.serialize(destKey));
		final SortParameters sortParameters = toSortParameters(optional);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				Long count = connection.sort(keyByte, sortParameters, destKeyByte);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<DataType>() {

			@Override
			public DataType doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return DataType.resolve(connection.type(keyByte).code());
			}
		});
	}
	
	@Override
	public <V> List<V> valuesByPattern(final String dbName, final String pattern, final Class<V> valueType) {
		final byte[] patternByte = stringSerializer.serialize(
				StringUtils.isNotEmpty(pattern) ? pattern : StringUtils.ANY);
		
		List<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<List<byte[]>>() {
			
			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				/* 第一步：先获取匹配模式的键字节集合 */
				Set<byte[]> keyBytes = connection.keys(patternByte);
				if (CollectionUtils.isEmpty(keyBytes))
					return null;
				
				// 第二步：再根据键集合执行mGet命令，批量获取对应的值字节集合
				return connection.mGet(CollectionUtils.toArray(keyBytes, byte[].class));
			}
		});
		
		// 第三步：将多个值字节反序列化到列表中
		return deserializeValueBytesToList(dbName, valueBytes, valueType);
	}
	
	@Override
	public <K, V> void set(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [set]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [set]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		getRedisTemplate().execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				if (expireTime > 0)
					connection.setEx(keyByte, expireTime, valueByte);
				else
					connection.set(keyByte, valueByte);
				
				return null;
			}
		});
	}

	@Override
	public <K, V> Boolean setNX(final String dbName, final K key, final V value, final long expireTime, final TimeUnit timeUnit) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [setNX]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [setNX]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				long expireMillis = getExpireMillis(expireTime, timeUnit, repository);
				/* 如果设置的过期时间大于0，则执行原子性的setNX命令，设置键值的同时设置相应的过期时间 
				 * 注意：这里没有使用RedisConnection提供的原子性set方法，因为此方法在设计时没有声明返回结果，会对执行结果的判断造成误差
				 * 因此这里调用的是execute方法， 以客户端执行本地原生命令行的方式进行 */
				if (expireMillis > 0) 
					return connection.execute(SET_COMMAND_NAME, keyByte, valueByte, 
							NX_COMMAND_BYTES, PX_COMMAND_BYTES, stringSerializer.serialize(expireMillis)) != null;
							
				return connection.setNX(keyByte, valueByte);
			}
		});
	}
	
	@Override
	public <K, V> void setEx(final String dbName, final K key, final long seconds, final V value) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [setEx]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [setEx]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				long expireSeconds = getExpireSeconds(seconds, repository);
				if (expireSeconds > 0)
					connection.setEx(keyByte, expireSeconds, valueByte);
				else
					connection.set(keyByte, valueByte);
				
				return null;
			}
		});
	}
	
	@Override
	public <K, V> void pSetEx(final String dbName, final K key, final long millis, final V value) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [pSetEx]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [pSetEx]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				long expireMillis = getExpireMillis(millis, repository);
				if (expireMillis > 0)
					connection.pSetEx(keyByte, expireMillis, valueByte);
				else
					// 毫秒数小于等于0时永不过期
					connection.set(keyByte, valueByte);
				
				return null;
			}
		});
		
	}
	
	@Override
	public <K, V> void mSet(final String dbName, final Map<K, V> keyValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(keyValues, "Key values must not be empty for command [mSet");
		
		final Map<byte[], byte[]> keyValueBytes = serializeKeyValues(dbName, keyValues);
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				connection.mSet(keyValueBytes);
				setExpireTime(connection, repository, keyValueBytes.keySet(), expireSeconds);
				return null;
			}
		});
	}

	@Override
	public <K, V> Boolean mSetNX(final String dbName, final Map<K, V> keyValues, final long expireSeconds) {
		AssertUtils.assertNotEmpty(keyValues, "Key values must not be empty for command [mSetNX]");
		
		final Map<byte[], byte[]> keyValueBytes = serializeKeyValues(dbName, keyValues);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				/* 注意：键值设置和过期设置为非原子性操作，因此在实现诸如批量分布式锁这样的应用场景中，此命令可能并不合适。
				 * 另外，mSetNX方法如果返回为null，并不一定代表设置不成功，可能是由于connection使用的管道或队列的形式异步来发送命令的，
				 * 因此在这两种情况下，以同步的方式获取到的结果就为null(参考JedisConnection源代码)。
				 * Boolean判断认为返回为null时也不进行过期时间设置，因为这里的connection是以同步的方式来发送命令的，忽略掉null只是为了防止空指针异常    */
				Boolean success = connection.mSetNX(keyValueBytes);
				if (BooleanUtils.isTrue(success))
					setExpireTime(connection, repository, keyValueBytes.keySet(), expireSeconds);
				
				return success;
			}
		});
	}
	
	@Override
	public <K, V> void setRange(final String dbName, final K key, final long offset, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [setRange]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [setRange]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				connection.setRange(keyByte, valueByte, offset);
				setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return null;
			}
		});
	}

	@Override
	public <K, V> Long append(final String dbName, final K key, final V value, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [append]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [append]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long length = connection.append(keyByte, valueByte);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		byte[] valueByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.get(keyByte);
			}
		});
		
		return deserializeValueByte(dbName, valueByte, valueType);
	}

	@Override
	public <K, V> V getRange(final String dbName, final K key, final long begin, final long end, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		byte[] valueByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.getRange(keyByte, begin, end);
			}
		});
		
		return deserializeValueByte(dbName, valueByte, valueType);
	}

	@Override
	public <K, V, O> O getSet(final String dbName, final K key, final V value, final long expireSeconds, final Class<O> oldValueType) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [getSet]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [getSet]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		byte[] oldValueByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				byte[] oldValueByte = connection.getSet(keyByte, valueByte);
				setExpireTime(connection, repository, keyByte, expireSeconds);
				return oldValueByte;
			}
		});
		
		return deserializeValueByte(dbName, oldValueByte, oldValueType);
	}
	
	@Override
	public <K, V> List<V> mGet(final String dbName, final K[] keys, final Class<V> valueType) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		List<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<List<byte[]>>() {

			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.mGet(keyBytes);
			}
		});
		
		return deserializeValueBytesToList(dbName, valueBytes, valueType);
	}

	@Override
	public <K> Long strLen(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.strLen(keyByte);
			}
		});
	}
	
	@Override
	public <K> Long decr(final String dbName, final K key) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [decr]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.decr(keyByte);
			}
		});
	}

	@Override
	public <K> Long decrBy(final String dbName, final K key, final long value) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [decrBy]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.decrBy(keyByte, value);
			}
		});
	}

	@Override
	public <K> Long incr(final String dbName, final K key) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [incr]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.incr(keyByte);
			}
		});
	}

	@Override
	public <K> Long incrBy(final String dbName, final K key, final long value) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [incrBy]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.incrBy(keyByte, value);
			}
		});
	}
	
	@Override
	public <K, H, V> Boolean hSet(final String dbName, final K key,
			final H hashKey, final V value, final long expireSeconds) {
		
		AssertUtils.assertNotNull(key, "Key must not be null for command [hSet]");
		AssertUtils.assertNotNull(hashKey, "Hash key must not be null for command [hSet]");
		AssertUtils.assertNotNull(value, "Value must not be null for command [hSet]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] hashKeyByte = serializeHashKey(dbName, hashKey);
		final byte[] hashValueByte = serializeHashValue(dbName, value);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				Boolean success = connection.hSet(keyByte, hashKeyByte, hashValueByte);
				
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] hashKeyByte = serializeHashKey(dbName, hashKey);
		final byte[] hashValueByte = serializeHashValue(dbName, value);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Boolean success = connection.hSetNX(keyByte, hashKeyByte, hashValueByte);
				if (BooleanUtils.isTrue(success))
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
				
		final byte[] keyByte = serializeKey(dbName, key);
		final Map<byte[], byte[]> hashKeyValueBytes = serializeHashKeyValues(dbName, hashKeyValues);
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				connection.hMSet(keyByte, hashKeyValueBytes);
				setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return null;
			}
		});
	}

	@Override
	public <K, H> Long hDel(final String dbName, final K key, final H[] hashKeys) {
		if (key == null || ArrayUtils.isEmpty(hashKeys))
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] hashKeyBytes = serializeHashKeys(dbName, hashKeys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hDel(keyByte, hashKeyBytes);
			}
		});
	}

	@Override
	public <K, H> Boolean hExists(final String dbName, final K key, final H hashKey) {
		if (key == null || hashKey == null)
			return false;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] hashKeyByte = serializeHashKey(dbName, hashKey);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hExists(keyByte, hashKeyByte);
			}
		});
	}

	@Override
	public <K, H, V> V hGet(final String dbName, final K key, final H hashKey, final Class<V> valueType) {
		if (key == null || hashKey == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] hashKeyByte = serializeHashKey(dbName, hashKey);
		byte[] hashValueByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hGet(keyByte, hashKeyByte);
			} 
		});
		
		return deserializeHashValueByte(dbName, hashValueByte, valueType);
	}

	@Override
	public <K, H, V> Map<H, V> hGetAll(final String dbName, final K key, final Class<H> hashKeyType, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Map<byte[], byte[]> hashKeyValueBytes = getRedisTemplate().execute(new RedisCallback<Map<byte[], byte[]>>() {

			@Override
			public Map<byte[], byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hGetAll(keyByte);
			}
		});
		
		return deserializeHashKeyValueBytes(dbName, hashKeyValueBytes, hashKeyType, valueType);
	}

	@Override
	public <K, H> Set<H> hKeys(final String dbName, final K key, final Class<H> hashKeyType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Set<byte[]> hashKeyBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hKeys(keyByte);
			}
		});
		
		return deserializeHashKeyBytes(dbName, hashKeyBytes, hashKeyType);
	}

	@Override
	public <K> Long hLen(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hLen(keyByte);
			}
		});
	}
	
	@Override
	public <K, H, V> List<V> hMGet(final String dbName, final K key, final H[] hashKeys, final Class<V> valueType) {
		if (key == null || ArrayUtils.isEmpty(hashKeys))
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] hashKeyBytes = serializeHashKeys(dbName, hashKeys);
		List<byte[]> haseValueBytes = getRedisTemplate().execute(new RedisCallback<List<byte[]>>() {

			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hMGet(keyByte, hashKeyBytes);
			}
		});
		
		return deserializeHashValueBytesToList(dbName, haseValueBytes, valueType);
	}

	@Override
	public <K, V> List<V> hVals(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		List<byte[]> hashValueBytes = getRedisTemplate().execute(new RedisCallback<List<byte[]>>() {

			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hVals(keyByte);
			}
		});
		
		return deserializeHashValueBytesToList(dbName, hashValueBytes, valueType);
	}
	
	@Override
	public <K, H> Long hIncrBy(final String dbName, final K key, final H hashKey, final long value) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [hIncrBy]");
		AssertUtils.assertNotNull(hashKey, "Hash key must not be null for command [hIncrBy]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] hashKeyByte = serializeHashKey(dbName, hashKey);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.hIncrBy(keyByte, hashKeyByte, value);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		Serializer valueSerializer = selectValueSerializer(dbName);
		final byte[] pivotByte = valueSerializer.serialize(pivot);
		final byte[] valueByte = (pivot.equals(value) ? pivotByte : valueSerializer.serialize(value));
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.lInsert(keyByte, toPosition(where), pivotByte, valueByte);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				connection.lSet(keyByte, posttion, valueByte);
				setExpireTime(connection, repository, keyByte, expireSeconds);
				
				return null;
			}
		});
	}
	
	@Override
	public <K, V> Long lPush(final String dbName, final K key, final V[] values, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [lPush]");
		AssertUtils.assertNotEmpty(values, "Values must not be empty for command [lPush]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] valueBytes = serializeValues(dbName, values);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.lPush(keyByte, valueBytes);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		return getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.lPushX(keyByte, valueByte);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		byte[] valueByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.lIndex(keyByte, index);
			}
		});
		
		return deserializeValueByte(dbName, valueByte, valueType);
	}

	@Override
	public <K> Long lLen(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.lLen(keyByte);
			}
		});
	}

	@Override
	public <K, V> V lPop(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		byte[] valueByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.lPop(keyByte);
			}
		});
		
		return deserializeValueByte(dbName, valueByte, valueType);
	}

	@Override
	public <K, V> List<V> lRange(final String dbName, final K key, final long begin, final long end, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		List<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<List<byte[]>>() {

			@Override
			public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.lRange(keyByte, begin, end);
			}
		});
		
		return deserializeValueBytesToList(dbName, valueBytes, valueType);
	}
	
	@Override
	public <K, V> Long lRem(final String dbName, final K key, final long count, final V value) {
		if (key == null || value == null)
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.lRem(keyByte, count, valueByte);
			}
		});
	}

	@Override
	public <K> void lTrim(final String dbName, final K key, final long begin, final long end) {
		if (key == null)
			return;
		
		final byte[] keyByte = serializeKey(dbName, key);
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				connection.lTrim(keyByte, begin, end);
				return null;
			}
		});
	}
	
	@Override
	public <K, V> Long rPush(final String dbName, final K key, final V[] values, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [rPush]");
		AssertUtils.assertNotEmpty(values, "Values must not be empty for command [rPush]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] valueBytes = serializeValues(dbName, values);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.rPush(keyByte, valueBytes);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = serializeValue(dbName, value);
		return getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.rPushX(keyByte, valueByte);
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
		
		Serializer keySerializer = selectKeySerializer(dbName);
		final byte[] srcKeyByte = keySerializer.serialize(srcKey);
		final byte[] destKeyByte = (destKey.equals(srcKey) ? srcKeyByte : keySerializer.serialize(destKey));
		byte[] destValueByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				// 将源列表中最后一个元素出列后存入目标列表
				byte[] destValueByte = connection.rPopLPush(srcKeyByte, destKeyByte);
				// TODO 测试空判断
				if (destValueByte != null)
					// 只有当将源列表中出列的元素不为空时才设置目标键的过期时间，因为当为空时，表示源列表可能根本不存在
					setExpireTime(connection, repository, destKeyByte, expireSeconds);
				
				return destValueByte;
			}
		});
		
		return deserializeValueByte(dbName, destValueByte, valueType);
	}
	
	@Override
	public <K, V> V rPop(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] valueByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.rPop(keyByte);
			}
		});
		
		return deserializeValueByte(dbName, valueByte, valueType);
	}
	
	@Override
	public <K, V> Long sAdd(final String dbName, final K key, final V[] members, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [sAdd]");
		AssertUtils.assertNotEmpty(members, "Members must not be empty for command [sAdd]");
				
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.sAdd(keyByte, serializeValues(dbName, members));
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sCard(keyByte);
			}
		});
	}
	
	@Override
	public <K, V> Set<V> sDiff(final String dbName, final K[] keys, final Class<V> valueType) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		Set<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sDiff(keyBytes);
			}
		});
		
		return deserializeValueBytesToSet(dbName, valueBytes, valueType);
	}
	
	@Override
	public <T, K> Long sDiffStore(final String dbName, final T destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [sDiffStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [sDiffStore]");
		
		final byte[] destKeyByte = serializeKey(dbName, destKey);
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.sDiffStore(destKeyByte, keyBytes);
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
		
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		Set<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sInter(keyBytes);
			}
		});
		
		return deserializeValueBytesToSet(dbName, valueBytes, valueType);
	}
	
	@Override
	public <T, K> Long sInterStore(final String dbName, final T destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [sInterStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [sInterStore]");
		
		final byte[] destKeyByte = serializeKey(dbName, destKey);
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.sInterStore(destKeyByte, keyBytes);
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
		
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		Set<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sUnion(keyBytes);
				
			}
		});
		
		return deserializeValueBytesToSet(dbName, valueBytes, valueType);
	}
	
	@Override
	public <T, K> Long sUnionStore(final String dbName, final T destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [sUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [sUnionStore]");
		
		final byte[] destKeyByte = serializeKey(dbName, destKey);
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.sUnionStore(destKeyByte, keyBytes);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sIsMember(keyByte, memberByte);
			}
		});
	}

	@Override
	public <K, V> Set<V> sMembers(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Set<byte[]> memberBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.sMembers(keyByte);
			}
		});
		
		return deserializeValueBytesToSet(dbName, memberBytes, valueType);
	}

	public <K, T, V> Boolean sMove(final String dbName, final K srcKey, final T destKey, final V member, final long expireSeconds) {
		if (srcKey == null || destKey == null || member == null)
			return false;
		
		Serializer keySerializer = selectKeySerializer(dbName);
		final byte[] srcKeyByte = keySerializer.serialize(srcKey);
		final byte[] destKeyByte = (destKey.equals(srcKey) ? srcKeyByte : keySerializer.serialize(destKey));
		final byte[] memberByte = serializeValue(dbName, member);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Boolean success = connection.sMove(srcKeyByte, destKeyByte, memberByte);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		byte[] memberByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sPop(keyByte);
			}
		});
		
		return deserializeValueByte(dbName, memberByte, valueType);
	}

	@Override
	public <K, V> V sRandMember(final String dbName, final K key, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		byte[] memberByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sRandMember(keyByte);
			}
		});
		
		return deserializeValueByte(dbName, memberByte, valueType);
	}

	@Override
	public <K, V> Long sRem(final String dbName, final K key, final V[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] memberBytes = serializeValues(dbName, members);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.sRem(keyByte, memberBytes);
			}
		});
	}

	@Override
	public <K, V> Boolean zAdd(final String dbName, final K key, final double score, final V member, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [zAdd]");
		AssertUtils.assertNotNull(member, "Member must not be null for command [zAdd]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		return getRedisTemplate().execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Boolean success = connection.zAdd(keyByte, score, memberByte);
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		final Set<Tuple> tuples = serializeScoreMembers(dbName, scoreMembers);
		
		if (tuples.size() == 0)
			return 0L;
		
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);

				Long count = connection.zAdd(keyByte, tuples);
				if (count != null && count > 0)
					setExpireTime(connection, repository, keyByte, expireSeconds);

				return count;
			}
		});
	}
	
	@Override
	public <K> Long zCard(final String dbName, final K key) {
		if (key == null)
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zCard(keyByte);
			}
		});
	}

	@Override
	public <K> Long zCount(final String dbName, final K key, final double minScore, final double maxScore) {
		if (key == null)
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zCount(keyByte, minScore, maxScore);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRange(final String dbName, final K key, final long begin, final long end, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Set<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRange(keyByte, begin, end);
			}
		});
		
		return deserializeValueBytesToSet(dbName, valueBytes, valueType);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(final String dbName, final K key, final double minScore, final double maxScore,
			final Limit limit, final Class<V> valueType) {
			
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Set<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {
			
			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return limit == null ? connection.zRangeByScore(keyByte, minScore, maxScore)
						: connection.zRangeByScore(keyByte, minScore, maxScore, limit.getOffset(), limit.getCount());
			}
		});
		
		return deserializeValueBytesToSet(dbName, valueBytes, valueType);
	}
	
	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(final String dbName, final K key, final double minScore,
			final double maxScore, final Limit limit, final Class<V> valueType) {
		
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Set<Tuple> tuples = getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {
			
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return limit == null ? connection.zRangeByScoreWithScores(keyByte, minScore, maxScore)
						: connection.zRangeByScoreWithScores(keyByte, minScore, maxScore, limit.getOffset(), limit.getCount());
			}
		});
		
		return deserializeTuples(dbName, tuples, valueType);
	}

	@Override
	public <K, V> Set<V> zRevRange(final String dbName, final K key, final long begin, final long end, final Class<V> valueType) {
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Set<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRevRange(keyByte, begin, end);
			}
		});
		
		return deserializeValueBytesToSet(dbName, valueBytes, valueType);
	}

	@Override
	public <K, V> Set<V> zRevRangeByScore(final String dbName, final K key, final double minScore,
			final double maxScore, final Limit limit, final Class<V> valueType) {
		
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Set<byte[]> valueBytes = getRedisTemplate().execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return limit == null ? connection.zRevRangeByScore(keyByte, minScore, maxScore)
						: connection.zRevRangeByScore(keyByte, minScore, maxScore, limit.getOffset(), limit.getCount());
			}
		});
		
		return deserializeValueBytesToSet(dbName, valueBytes, valueType);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(final String dbName, final K key, final double minScore,
			final double maxScore, final Limit limit, final Class<V> valueType) {
		
		if (key == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		Set<Tuple> tuples = getRedisTemplate().execute(new RedisCallback<Set<Tuple>>() {
			
			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return limit == null ? connection.zRevRangeByScoreWithScores(keyByte, minScore, maxScore)
						: connection.zRevRangeByScoreWithScores(keyByte, minScore, maxScore, limit.getOffset(), limit.getCount());
			}
		});
		
		return deserializeTuples(dbName, tuples, valueType);
	}
	
	@Override
	public <K, V> Long zRank(final String dbName, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		return getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRank(keyByte, memberByte);
			}
		});
	}
	
	@Override
	public <K, V> Long zRevRank(final String dbName, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		return getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRevRank(keyByte, memberByte);
			}
		});
	}

	@Override
	public <K, V> Long zRem(final String dbName, final K key, final V[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] memberBytes = serializeValues(dbName, members);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zRem(keyByte, memberBytes);
			}
		});
	}

	@Override
	public <K> Long zRemRangeByRank(final String dbName, final K key, final long begin, final long end) {
		if (key == null)
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				// 并无zRemRange命令，内部实际上执行的是zRemRangeByRank命令
				return connection.zRemRange(keyByte, begin, end);
			}
		});
	}

	@Override
	public <K> Long zRemRangeByScore(final String dbName, final K key, final double minScore, final double maxScore) {
		if (key == null)
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);	
				return connection.zRemRangeByScore(keyByte, minScore, maxScore);
			}
		});
	}
	
	@Override
	public <K, V> Double zScore(final String dbName, final K key, final V member) {
		if (key == null || member == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		return getRedisTemplate().execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zScore(keyByte, memberByte);
			}
		});
	}

	@Override
	public <K> Long zUnionStore(final String dbName, final K destKey, final K[] keys, final long expireSeconds) {
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [zUnionStore]");
		AssertUtils.assertNotEmpty(keys, "Source keys must not be empty for command [zUnionStore]");
		
		final byte[] destKeyByte = serializeKey(dbName, destKey);
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.zUnionStore(destKeyByte, keyBytes);
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
		
		final byte[] destKeyByte = serializeKey(dbName, destKey);
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = (option == null ? connection.zUnionStore(destKeyByte, keyBytes)
						: connection.zUnionStore(destKeyByte, toAggregate(option.getAggregate()), option.getWeights(), keyBytes));
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
		
		final byte[] destKeyByte = serializeKey(dbName, destKey);
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = connection.zInterStore(destKeyByte, keyBytes);
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
		
		final byte[] destKeyByte = serializeKey(dbName, destKey);
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				
				Long count = (option == null ? connection.zInterStore(destKeyByte, keyBytes)
						: connection.zInterStore(destKeyByte, toAggregate(option.getAggregate()), option.getWeights(), keyBytes));
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
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		return getRedisTemplate().execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.zIncrBy(keyByte, increment, memberByte);
			}
		});
	}

	@Override
	public Long dbSize(final String dbName) {
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.dbSize();
			}
		});
	}

	@Override
	public void flushAll() {
		getRedisTemplate().execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushAll();
				return null;
			}
		});
	}

	@Override
	public void flushDb(final String dbName) {
		getRedisTemplate().execute(new RedisCallback<Object>() {

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
		getRedisTemplate().execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.shutdown();
				return null;
			}
		});
	}

}
