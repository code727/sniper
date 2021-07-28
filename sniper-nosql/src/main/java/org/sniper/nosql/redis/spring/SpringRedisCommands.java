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
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.BooleanUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.PropertiesUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.nosql.redis.enums.DataType;
import org.sniper.nosql.redis.enums.GeoDistanceUnit;
import org.sniper.nosql.redis.enums.ListPosition;
import org.sniper.nosql.redis.enums.Section;
import org.sniper.nosql.redis.model.geo.GeoCircle;
import org.sniper.nosql.redis.model.geo.GeoDistance;
import org.sniper.nosql.redis.model.geo.GeoLocations;
import org.sniper.nosql.redis.model.geo.GeoPoint;
import org.sniper.nosql.redis.model.geo.GeoRadiusResult;
import org.sniper.nosql.redis.model.xscan.IndexedScanResult;
import org.sniper.nosql.redis.model.xscan.MappedScanResult;
import org.sniper.nosql.redis.model.zset.ZSetTuple;
import org.sniper.nosql.redis.option.GeoRadiusOption;
import org.sniper.nosql.redis.option.Limit;
import org.sniper.nosql.redis.option.ScanOption;
import org.sniper.nosql.redis.option.SortOptional;
import org.sniper.nosql.redis.option.ZStoreOptional;
import org.sniper.serialization.Serializer;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

/**
 * Spring Redis命令行实现类
 * @author  Daniele
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.sort(keyByte, sortParameters, destKeyByte);
				if (expireTime > 0 && count != null && count > 0) 
					setExpireTime(connection, destKeyByte, expireTime);
				
				return count;
			}
		});
	}
	
	@Override
	public <K> IndexedScanResult<K> scan(final String dbName, final long cursorId, final ScanOption option, final Class<K> keyType) {
		byte[][] commandArgs = toCommandArgs(cursorId, option);
		List<Object> scanned = getRedisTemplate().execute(new RedisCallback<List<Object>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return (List<Object>) connection.execute(SCAN_COMMAND_NAME, commandArgs);
			}
		});
		
		return toScanResult(dbName, scanned, keyType);
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
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				connection.mSet(keyValueBytes);
				if (expireTime > 0) 
					batchSetExpireTime(connection, keyValueBytes.keySet(), expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				/* 注意：键值设置和过期设置为非原子性操作，因此在实现诸如批量分布式锁这样的应用场景中，此命令可能并不合适。
				 * 另外，mSetNX方法如果返回为null，并不一定代表设置不成功，可能是由于connection使用的管道或队列的形式异步来发送命令的，
				 * 因此在这两种情况下，以同步的方式获取到的结果就为null(参考JedisConnection源代码)。
				 * Boolean判断认为返回为null时也不进行过期时间设置，因为这里的connection是以同步的方式来发送命令的，忽略掉null只是为了防止空指针异常    */
				Boolean success = connection.mSetNX(keyValueBytes);
				if (expireTime > 0 && BooleanUtils.isTrue(success)) 
					batchSetExpireTime(connection, keyValueBytes.keySet(), expireTime);
					
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				connection.setRange(keyByte, valueByte, offset);
				if (expireTime > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long length = connection.append(keyByte, valueByte);
				// 有新的拼接结果才设置过期时间
				if (expireTime > 0 && length != null && length > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				byte[] oldValueByte = connection.getSet(keyByte, valueByte);
				if (expireTime > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Boolean success = connection.hSet(keyByte, hashKeyByte, hashValueByte);
				/* 如果返回为null，并不一定代表设置不成功，可能是由于connection使用的管道或队列的形式异步来发送命令的，
				 * 因此在这两种情况下，以同步的方式获取到的结果就为null(参考JedisConnection源代码)。
				 * Boolean判断认为返回为null时也不进行过期时间设置，因为这里的connection是以同步的方式来发送命令的，忽略掉null只是为了防止空指针异常  */
				if (expireTime > 0 && BooleanUtils.isTrue(success))
					setExpireTime(connection, keyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Boolean success = connection.hSetNX(keyByte, hashKeyByte, hashValueByte);
				if (expireTime > 0 && BooleanUtils.isTrue(success))
					setExpireTime(connection, keyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				connection.hMSet(keyByte, hashKeyValueBytes);
				if (expireTime > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
		
		return deserializeHashValueBytes(dbName, haseValueBytes, valueType);
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
		
		return deserializeHashValueBytes(dbName, hashValueBytes, valueType);
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
	public <K, H, V> MappedScanResult<H, V> hScan(final String dbName, final K key, final long cursorId,
			final ScanOption option, final Class<H> hashKeyType, final Class<V> valueType) {
		
		if (key == null)
			return null;
		
		byte[][] commandArgs = toCommandArgs(dbName, key, cursorId, option);
		List<Object> scanned = getRedisTemplate().execute(new RedisCallback<List<Object>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return (List<Object>) connection.execute(HSCAN_COMMAND_NAME, commandArgs);
			}
		});
		
		return toHScanResult(dbName, scanned, hashKeyType, valueType);
	}
	
	@Override
	public <K, V> MappedScanResult<V, Double> zScan(final String dbName, final K key, final long cursorId,
			final ScanOption option, Class<V> valueType) {
		
		if (key == null)
			return null;
		
		byte[][] commandArgs = toCommandArgs(dbName, key, cursorId, option);
		List<Object> scanned = getRedisTemplate().execute(new RedisCallback<List<Object>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return (List<Object>) connection.execute(ZSCAN_COMMAND_NAME, commandArgs);
			}
		});
		
		return toZScanResult(dbName, scanned, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.lInsert(keyByte, toPosition(where), pivotByte, valueByte);
				if (expireTime > 0 && count != null && count > 0) 
					setExpireTime(connection, keyByte, expireTime);
					
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				connection.lSet(keyByte, posttion, valueByte);
				if (expireTime > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.lPush(keyByte, valueBytes);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.lPushX(keyByte, valueByte);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.rPush(keyByte, valueBytes);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.rPushX(keyByte, valueByte);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, keyByte, expireTime);
				
				return count;
			}
		});
	}
	
	@SuppressWarnings("unlikely-arg-type")
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				// 将源列表中最后一个元素出列后存入目标列表
				byte[] destValueByte = connection.rPopLPush(srcKeyByte, destKeyByte);
				if (expireTime > 0 && destValueByte != null)
					// 只有当将源列表中出列的元素不为空时才设置目标键的过期时间，因为当为空时，表示源列表可能根本不存在
					setExpireTime(connection, destKeyByte, expireTime);
				
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
		final byte[][] memberBytes = serializeValues(dbName, members);
		return getRedisTemplate().execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.sAdd(keyByte, memberBytes);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, keyByte, expireTime);
				
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.sDiffStore(destKeyByte, keyBytes);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, destKeyByte, expireTime);	
				
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.sInterStore(destKeyByte, keyBytes);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, destKeyByte, expireTime);	
				
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.sUnionStore(destKeyByte, keyBytes);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, destKeyByte, expireTime);	
				
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
		
		return deserializeValueBytes(dbName, memberBytes, valueType);
	}

	@SuppressWarnings("unlikely-arg-type")
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Boolean success = connection.sMove(srcKeyByte, destKeyByte, memberByte);
				if (expireTime > 0 && BooleanUtils.isTrue(success)) 
					setExpireTime(connection, destKeyByte, expireTime);
				
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
	public <K, V> IndexedScanResult<V> sscan(final String dbName, final K key, final long cursorId, final ScanOption option, final Class<V> valueType) {
		if (key == null)
			return null;
		
		byte[][] commandArgs = toCommandArgs(dbName, key, cursorId, option);
		List<Object> scanned = getRedisTemplate().execute(new RedisCallback<List<Object>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return (List<Object>) connection.execute(SSCAN_COMMAND_NAME, commandArgs);
			}
		});
		
		return toSScanResult(dbName, scanned, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Boolean success = connection.zAdd(keyByte, score, memberByte);
				if (expireTime > 0 && BooleanUtils.isTrue(success))
					setExpireTime(connection, keyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.zAdd(keyByte, tuples);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, keyByte, expireTime);

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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
		
		return deserializeValueBytes(dbName, valueBytes, valueType);
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.zUnionStore(destKeyByte, keyBytes);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, destKeyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = (option == null ? connection.zUnionStore(destKeyByte, keyBytes)
						: connection.zUnionStore(destKeyByte, toAggregate(option.getAggregate()), option.getWeights(), keyBytes));
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, destKeyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.zInterStore(destKeyByte, keyBytes);
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, destKeyByte, expireTime);
				
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
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = (option == null ? connection.zInterStore(destKeyByte, keyBytes)
						: connection.zInterStore(destKeyByte, toAggregate(option.getAggregate()), option.getWeights(), keyBytes));
				if (expireTime > 0 && count != null && count > 0)
					setExpireTime(connection, destKeyByte, expireTime);
				
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
	public <K, V> Long pfAdd(final String dbName, final K key, final V[] elements, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [pfAdd]");
		AssertUtils.assertNotEmpty(elements, "Elements must not be empty for command [pfAdd]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] elementBytes = serializeValues(dbName, elements);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long result = connection.pfAdd(keyByte, elementBytes);
				if (expireTime > 0 && result != null && result > 0)
					setExpireTime(connection, keyByte, expireTime);
				
				return result;
			}
		});
	}
	
	@Override
	public <K> Long pfCount(final String dbName, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return 0L;
		
		final byte[][] keyBytes = serializeKeys(dbName, keys);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.pfCount(keyBytes);
			}
		});
	}
	
	@Override
	public <K> void pfMerge(String dbName, K destKey, K[] sourceKeys) {
		AssertUtils.assertNotNull(destKey, "Destination key must not be null for command [pfMerge]");
		AssertUtils.assertNotEmpty(sourceKeys, "Source keys not be empty for command [pfMerge]");
		
		final byte[] destKeyByte = serializeKey(dbName, destKey);
		final byte[][] sourceKeyBytes = serializeKeys(dbName, sourceKeys);
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.pfMerge(destKeyByte, sourceKeyBytes);
				return null;
			}
		});
	}
	
	@Override
	public <K, M> Long geoAdd(final String dbName, final K key, final M member, final GeoPoint point, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [geoAdd]");
		AssertUtils.assertNotNull(member, "Geo member must not be null for command [geoAdd]");
		AssertUtils.assertNotNull(point, "Geo point must not be null for command [geoAdd]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.geoAdd(keyByte, toPoint(point), memberByte);
				if (expireTime > 0 && count != null && count > 0) 
					setExpireTime(connection, keyByte, expireTime);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, M> Long geoAdd(final String dbName, final K key, final GeoLocations<M> locations, final long expireSeconds) {
		AssertUtils.assertNotNull(key, "Key must not be null for command [geoAdd]");
		AssertUtils.assertTrue(locations != null && locations.size() > 0,
				"Geo locations must not be empty for command [geoAdd]");
		
		final byte[] keyByte = serializeKey(dbName, key);
		final Map<byte[], Point> locationMap = toMemberPointMap(locations);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbName);
				long expireTime = getExpireSeconds(expireSeconds, repository);
				
				Long count = connection.geoAdd(keyByte, locationMap);
				if (expireTime > 0 && count != null && count > 0) 
					setExpireTime(connection, keyByte, expireTime);
				
				return count;
			}
		});
	}
	
	@Override
	public <K, M> GeoPoint geoPos(final String dbName, final K key, final M member) {
		if (key == null || member == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		Point point = getRedisTemplate().execute(new RedisCallback<Point>() {

			@Override
			public Point doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return CollectionUtils.get(connection.geoPos(keyByte, memberByte), 0);
			}
		});
		
		return toGeoPoint(point);
	}

	@Override
	public <K, M> GeoLocations<M> geoPos(final String dbName, final K key, final M[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] memberBytes = serializeValues(dbName, members);
		List<Point> points = getRedisTemplate().execute(new RedisCallback<List<Point>>() {

			@Override
			public List<Point> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.geoPos(keyByte, memberBytes);
			}
		});
		
		return toGeoLocations(members, points);
	}
	
	@Override
	public <K, M> GeoDistance geoDist(final String dbName, final K key, final M member1, final M member2,
			final GeoDistanceUnit unit) {
		
		if (key == null || member1 == null || member2 == null)
			return null;
		
		Serializer valueSerializer = selectValueSerializer(dbName);
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte1 = valueSerializer.serialize(member1);
		final byte[] memberByte2 = valueSerializer.serialize(member2);
		byte[] distanceByte = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				/* 注意：这里没有直接调用RedisConnection的geoDist方法来实现，因为当geoDist返回为空时，
				 * jedis的源码中存在BUG，详见redis.clients.jedis.BinaryJedis的geodist方法，
				 * 当返回空字符串时，new Double(dval)将出现NumberFormatException异常。
				 * 因此这里使用的原生命令行的方式来实现，当返回空字节结果时，最终返回空的GeoDistance对象*/
				if (unit == null)
					return (byte[]) connection.execute(GEODIST_COMMAND_NAME, keyByte, memberByte1, memberByte2);
				
				return (byte[]) connection.execute(GEODIST_COMMAND_NAME, keyByte, memberByte1, memberByte2,
						stringSerializer.serialize(unit.getAbbreviation()));
			}
		});
		
		return toGeoDistance(distanceByte, unit);
	}
	
	@Override
	public <K, M> GeoRadiusResult<M> geoRadius(String dbName, K key, final GeoCircle circle, final GeoRadiusOption option) {
		if (key == null || circle == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		GeoResults<GeoLocation<byte[]>> geoResults = getRedisTemplate().execute(new RedisCallback<GeoResults<GeoLocation<byte[]>>>() {
				
			@Override
			public GeoResults<GeoLocation<byte[]>> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return option == null ? connection.geoRadius(keyByte, toCircle(circle))
						: connection.geoRadius(keyByte, toCircle(circle), toGeoRadiusCommandArgs(option));
			}
		});
		
		return toGeoRadiusResult(dbName, geoResults);
	}
	
	@Override
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(final String dbName, final K key, final M member,
			final GeoDistance radius, final GeoRadiusOption option) {
		
		if (key == null || member == null || radius == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		GeoResults<GeoLocation<byte[]>> geoResults = getRedisTemplate().execute(new RedisCallback<GeoResults<GeoLocation<byte[]>>>() {

			@Override
			public GeoResults<GeoLocation<byte[]>> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				try {
					return option == null ? connection.geoRadiusByMember(keyByte, memberByte, toDistance(radius))
							: connection.geoRadiusByMember(keyByte, memberByte, toDistance(radius), toGeoRadiusCommandArgs(option));
				} catch (InvalidDataAccessApiUsageException e) {
					/* 当指定的成员不在成员列表中时，则此命令将出现"ERR could not decode requested zset member"这样的错误信息
					 * 并包装在InvalidDataAccessApiUsageException中被抛出。针对于这种情况，将捕获此异常后返回空结果 */
					return null;
				}
			}
		});
		
		return toGeoRadiusResult(dbName, geoResults);
	}
	
	@Override
	public <K, M> String geoHash(final String dbName, final K key, final M member) {
		if (key == null || member == null)
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[] memberByte = serializeValue(dbName, member);
		List<String> list = getRedisTemplate().execute(new RedisCallback<List<String>>() {

			@Override
			public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.geoHash(keyByte, memberByte);
			}
			
		});
		
		return CollectionUtils.getFirst(list);
	}

	@Override
	public <K, M> List<String> geoHash(final String dbName, final K key, final M[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return null;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] memberBytes = serializeValues(dbName, members);
		return getRedisTemplate().execute(new RedisCallback<List<String>>() {

			@Override
			public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.geoHash(keyByte, memberBytes);
			}
		});
	}
	
	@Override
	public <K, M> Long geoRemove(final String dbName, final K key, final M[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return 0L;
		
		final byte[] keyByte = serializeKey(dbName, key);
		final byte[][] memberBytes = serializeValues(dbName, members);
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbName);
				return connection.geoRemove(keyByte, memberBytes);
			}
		});
	}
	
	@Override
	public Properties info(final Section section) {
		return getRedisTemplate().execute(new RedisCallback<Properties>() {

			@Override
			public Properties doInRedis(RedisConnection connection) throws DataAccessException {
				return section != null ? connection.info(section.name()) : connection.info();
			}
			
		});
	}
	
	@Override
	public <T> T info(final String key, final Class<T> messageType) {
		if (StringUtils.isBlank(key))
			return null;
		
		Properties properties = getRedisTemplate().execute(new RedisCallback<Properties>() {

			@Override
			public Properties doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.info();
			}
		});
		
		String message = properties.getProperty(key);
		return getPropertyConverter().converte(message, messageType);
	}
	
	@Override
	public Properties configGet(final String pattern) {
		Properties properties = getRedisTemplate().execute(new RedisCallback<Properties>() {

			@Override
			public Properties doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.getConfig(StringUtils.isNotEmpty(pattern) ? pattern : StringUtils.ANY);
			}
		});
		return properties;
	}
	
	@Override
	public <V> V config(final String parameter, final Class<V> valueType) {
		if (StringUtils.isBlank(parameter))
			return null;
		
		Properties properties = getRedisTemplate().execute(new RedisCallback<Properties>() {

			@Override
			public Properties doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.getConfig(parameter);
			}
		});
		
		if (PropertiesUtils.isEmpty(properties))
			return null;
		
		AssertUtils.assertTrue(properties.size() == 2, String.format(
				"Not get a single config by parameter '%s'", parameter));
		return getPropertyConverter().converte(properties.get(parameter), valueType);
	}
	
	@Override
	public void configResetStat() {
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.resetConfigStats();
				return null;
			}
		});
	}
	
	@Override
	public void configSet(final String parameter, final Object value) {
		AssertUtils.assertNotBlank(parameter, "Parameter name must not be null or blank for command [config set]");
		AssertUtils.assertNotNull(value, "Parameter value not be null for command [config set]");
		
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setConfig(parameter, value.toString());
				return null;
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
	public Long lastSave() {
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.lastSave();
			}
		});
	}
	
	@Override
	public void save() {
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.save();
				return null;
			}
		});
	}
	
	@Override
	public void bgSave() {
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.bgSave();
				return null;
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
	public void slaveOf(final String host, final int port) {
		getRedisTemplate().execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.slaveOf(host, port);
				return null;
			}
		});
	}
	
	@Override
	public Long time() {
		return getRedisTemplate().execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.time();
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
	
	@Override
	public String echo(final String message) {
		final byte[] bytes = stringSerializer.serialize(message);
		byte[] echo = getRedisTemplate().execute(new RedisCallback<byte[]>() {
			
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.echo(bytes);
			}
		});
		return stringSerializer.deserialize(echo);
	}
	
	@Override
	public String ping(final String message) {
		if (message == null) {
			return getRedisTemplate().execute(new RedisCallback<String>() {

				@Override
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.ping();
					
				}
			});
		}
			
		final byte[] bytes = stringSerializer.serialize(message);
		byte[] echo = getRedisTemplate().execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return (byte[]) connection.execute(PING_COMMAND_NAME, bytes);
			}
			
		});
		
		return stringSerializer.deserialize(echo);
	}

}
