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

package org.workin.nosql.redis.dao;

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
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.nosql.redis.RedisRepository;

/**
 * @description Redis数据访问实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisDaoImpl extends RedisDaoSupport implements RedisCommandsDao {

	@Override
	public <K, V> void set(K key, V value) {
		set(0, key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> void set(final int dbIndex, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [set].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [set].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		redisTemplate.execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				connection.set(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return null;
			}
		});
	}

	@Override
	public <K, V> Boolean setNX(K key, V value) {
		return setNX(0, key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Boolean setNX(final int dbIndex, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setNX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setNX].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				Boolean result = connection.setNX(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> void setEx(K key, V value) {
		setEx(0, key, value);
	}

	@Override
	public <K, V> void setEx(int dbIndex, K key, V value) {
		setEx(dbIndex, key, getExpireSecond(dbIndex), value);	
	}

	@Override
	public <K, V> void setEx(K key, long seconds, V value) {
		setEx(0, key, seconds, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> void setEx(final int dbIndex, final K key, final long seconds, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setEx].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setEx].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object  doInRedis(RedisConnection connection) throws DataAccessException {
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
		mSet(0, kValues);
	}

	@Override
	public <K, V> void mSet(final int dbIndex, final Map<K, V> kValues) {
		AssertUtils.assertTrue(MapUtils.isNotEmpty(kValues),
				"Key-value map can not be empty of command [mSet].");
		
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbIndex, kValues);
				RedisRepository repository = select(connection, dbIndex);
				connection.mSet(byteMap);
				setExpireTime(connection, repository, byteMap.keySet());
				return null;
			}
		});
	}

	@Override
	public <K, V> void mSetNX(Map<K, V> kValues) {
		mSetNX(0, kValues);
	}

	@Override
	public <K, V> void mSetNX(final int dbIndex, final Map<K, V> kValues) {
		AssertUtils.assertTrue(MapUtils.isNotEmpty(kValues),
				"Key-value map can not be empty of command [mSetNX].");
		
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(dbIndex, kValues);
				RedisRepository repository = select(connection, dbIndex);
				connection.mSetNX(byteMap);
				setExpireTime(connection, repository, byteMap.keySet());
				return null;
			}
		});
	}
	
	@Override
	public <K, V> void setRange(K key, long offset, V value) {
		setRange(0, key, offset, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> void setRange(final int dbIndex, final K key, final long offset, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setRange].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setRange].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				connection.setRange(keyByte, valueSerializer.serialize(value), offset);
				setExpireTime(connection, repository, keyByte);
				return null;
			}
			
		});
	}

	@Override
	public <K, F, V> Boolean hSet(K key, F field, V value) {
		return hSet(0, key, field, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F, V> Boolean hSet(final int dbIndex, final K key, final F field, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hSet].");
		AssertUtils.assertNotNull(field, "Field can not be null of command [hSet].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [hSet].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<F> fieldKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				Boolean result = connection.hSet(keyByte, fieldKeySerializer.serialize(field),
						valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, F, V> Boolean hSetNX(K key, F field, V value) {
		return hSetNX(0, field, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F, V> Boolean hSetNX(final int dbIndex, final K key, final F field, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hSetNX].");
		AssertUtils.assertNotNull(field, "Field can not be null of command [hSetNX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [hSetNX].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<F> fieldKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				Boolean result = connection.hSetNX(keyByte, fieldKeySerializer.serialize(field),
						valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}

	@Override
	public <K, F, V> void hMSet(K key, Map<F, V> fValues) {
		hMSet(0, key, fValues);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F, V> void hMSet(final int dbIndex, final K key, final Map<F, V> fValues) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hMSet].");
		AssertUtils.assertTrue(MapUtils.isNotEmpty(fValues),
				"Field-value map can not be empty of command [hMSet].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				connection.hMSet(keyByte, serializeFiledValueToByteMap(dbIndex, fValues));
				setExpireTime(connection, repository, keyByte);
				return null;
			}
		});
	}
	
	@Override
	public <K, V> Long lInsert(K key, Position where, V pivot, V value) {
		return lInsert(0, key, where, pivot, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long lInsert(final int dbIndex, final K key, final Position where, final V pivot, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lInsert].");
		AssertUtils.assertNotNull(where, "Insert postion can not be null of command [lInsert].");
		AssertUtils.assertNotNull(pivot, "Postion value can not be null of command [lInsert].");
		AssertUtils.assertNotNull(value, "Insert value can not be null of command [lInsert].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				Long result = connection.lInsert(keyByte, where,
						valueSerializer.serialize(pivot), valueSerializer.serialize(value));
				// 重新设置键的过期时间
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> void lSet(K key, long posttion, V value) {
		lSet(0, key, posttion, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> void lSet(final int dbIndex, final K key, final long posttion, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lSet].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [lSet].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, dbIndex);
				connection.lSet(keyByte, posttion, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return null;
			}
		});
	}

	@Override
	public <K, V> Long lPush(K key, V value) {
		return lPush(0, key, value);
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, V value) {
		AssertUtils.assertNotNull(value, "Value can not be null of command [lPush].");
		return lPush(dbIndex, key, new Object[] { value });
	}
	
	@Override
	public <K, V> Long lPush(K key, V[] values) {
		return lPush(0, key, values);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long lPush(final int dbIndex, final K key, final V[] values) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lPush].");
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(values), "Values can not be empty of command [lPush].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				
				long result = 0;
				for (V value : values) 
					result = result + NumberUtils.safeLong(connection.lPush(keyByte, valueSerializer.serialize(value)));
				
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> Long lPush(K key, Collection<V> values) {
		return lPush(0, key, values); 
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, Collection<V> values) {
		return lPush(dbIndex, key, CollectionUtils.toObjectArray(values));
	}

	@Override
	public <K, V> Long lPushX(K key, V value) {
		return lPushX(0, key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long lPushX(final int dbIndex, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lPushX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [lPushX].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				Long result = connection.lPushX(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> Long rPush(K key, V value) {
		return rPush(0, key, value);
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, V value) {
		AssertUtils.assertNotNull(value, "Value can not be null of command [rPush].");
		return rPush(dbIndex, key, new Object[] { value });
	}
	
	@Override
	public <K, V> Long rPush(K key, V[] values) {
		return rPush(0, key, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long rPush(final int dbIndex, final K key, final V[] values) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [rPush].");
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(values), "Values can not be empty of command [rPush].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				
				long result = 0;
				for (V value : values) 
					result = result + NumberUtils.safeLong(connection.rPush(keyByte, valueSerializer.serialize(value)));
				
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> Long rPush(K key, Collection<V> values) {
		return rPush(0, key, values);
	}

	@Override
	public <K, V> Long rPush(final int dbIndex, final K key, final Collection<V> values) {
		return rPush(dbIndex, key, CollectionUtils.toObjectArray(values));
	}

	@Override
	public <K, V> Long rPushX(K key, V value) {
		return rPushX(0, key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long rPushX(final int dbIndex, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [rPushX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [rPushX].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				Long result = connection.rPushX(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> V rPopLPush(K srcKey, K destKey) {
		return rPopLPush(0, srcKey, destKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V rPopLPush(final int dbIndex, final K srcKey, final K destKey) {
		AssertUtils.assertNotNull(srcKey, "Source key can not be null of command [rPopLPush].");
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [rPopLPush].");
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] destKeyByte = keySerializer.serialize(destKey);
				RedisRepository repository = select(connection, dbIndex);
				// 将源列表中最后一个元素取出后存入目标列表
				byte[] destValueByte = connection.rPopLPush(keySerializer.serialize(srcKey), destKeyByte);
				// 设置目标键的过期时间
				setExpireTime(connection, repository, destKeyByte);
				return (V) selectValueSerializer(dbIndex).deserialize(destValueByte);
			}
		});
	}

	@Override
	public <K, V> Boolean sAdd(K key, V member) {
		return sAdd(0, key, member);
	}

	@Override
	public <K, V> Boolean sAdd(int dbIndex, K key, V member) {
		AssertUtils.assertNotNull(member, "Member can not be null of command [sAdd].");
		Collection<V> members = CollectionUtils.newArrayList();
		members.add(member);
		return sAdd(dbIndex, key, members);
	}
	
	@Override
	public <K, V> Boolean sAdd(K key, V[] members) {
		return sAdd(0, key, members);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Boolean sAdd(final int dbIndex, final K key, final V[] members) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sAdd].");
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(members), "Members can not be empty of command [sAdd].");
				
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, dbIndex);
				
				boolean result = true;
				for (V member : members) 
					result = result && connection.sAdd(keyByte, valueSerializer.serialize(member));
				
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> Boolean sAdd(K key, Collection<V> members) {
		return sAdd(0, key, members);
	}

	@Override
	public <K, V> Boolean sAdd(int dbIndex, K key, Collection<V> members) {
		return sAdd(dbIndex, key, CollectionUtils.toObjectArray(members));
	}

	@Override
	public <K, V> Boolean zAdd(K key, double score, V member) {
		return zAdd(0, key, score, member);
	}

	@Override
	public <K, V> Boolean zAdd(int dbIndex, K key, double score, V member) {
		AssertUtils.assertNotNull(member, "Member can not be null of command [zAdd].");
		Map<Double, V> scoreMembers = MapUtils.newHashMap();
		scoreMembers.put(score, member);
		return zAdd(dbIndex, key, scoreMembers);
	}

	@Override
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers) {
		return zAdd(0, key, scoreMembers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Boolean zAdd(final int dbIndex, final K key, final Map<Double, V> scoreMembers) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [zAdd].");
		AssertUtils.assertTrue(MapUtils.isNotEmpty(scoreMembers), 
				"Score-member map can not be empty of command [zAdd].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

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
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}

	@Override
	public <K> Set<K> keys() {
		return keys(0);
	}

	@Override
	public <K> Set<K> keys(int dbIndex) {
		return keys(dbIndex, null);
	}

	@Override
	public <K> Set<K> keys(String pattern) {
		return keys(0, pattern);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Set<K> keys(final int dbIndex, final String pattern) {
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Set<K>>() {
			
			@Override
			public Set<K> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				Set<byte[]> keyBytes = connection.keys(StringUtils.isNotEmpty(pattern) ? 
						new StringRedisSerializer().serialize(pattern) : new StringRedisSerializer().serialize("*"));
				Set<K> keys = new HashSet<K>();
				for (byte[] key : keyBytes)
					keys.add(keySerializer.deserialize(key));
				return keys;
			}
		});
	}
	
	@Override
	public <K> Long del(K key) {
		return del(0, key);
	}
	
	@Override
	public <K> Long del(int dbIndex, K key) {
		return del(dbIndex, new Object[] { key });
	}

	@Override
	public <K> Long del(K[] keys) {
		return del(0, keys);
	}
	
	@Override
	public <K> Long del(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return 0L;
		
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.del(serializeKeysToArray(dbIndex, keys));
			}
		});
	}

	@Override
	public <K> Long del(Collection<K> keys) {
		return del(0, keys);
	}

	@Override
	public <K> Long del(int dbIndex, Collection<K> keys) {
		return del(dbIndex, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Boolean exists(K key) {
		return exists(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Boolean exists(final int dbIndex, final K key) {
		if (key == null)
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.exists(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Boolean expire(K key, long seconds) {
		return expire(0, seconds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Boolean expire(final int dbIndex, final K key, final long seconds) {
		if (key == null)
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.expire(keySerializer.serialize(key), seconds);
			}
		});
	}
	
	@Override
	public <K> Boolean expireAt(K key, long timestamp) {
		return expireAt(0, key, timestamp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Boolean expireAt(final int dbIndex, final K key, final long timestamp) {
		if (key == null)
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.expireAt(keySerializer.serialize(key), timestamp);
			}
		});
	}
	
	@Override
	public <K> Boolean expireAt(K key, Date date) {
		return expireAt(0, key, date);
	}

	@Override
	public <K> Boolean expireAt(int dbIndex, K key, Date date) {
		return expireAt(dbIndex, key, DateUtils.dateToUnixTimestamp(date));
	}
	
	@Override
	public <K> Boolean move(K key, int targetIndex) {
		return move(0, key, targetIndex);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Boolean move(final int dbIndex, final K key, final int targetIndex) {
		if (key == null)
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.move(keySerializer.serialize(key), targetIndex);
			}
		});
	}

	@Override
	public <K> Long ttl(K key) {
		return ttl(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long ttl(final int dbIndex, final K key) {
		if (key == null)
			return -2L;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.ttl(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K, V> List<V> sort(K key, SortParameters params) {
		return sort(0, key, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> List<V> sort(final int dbIndex, final K key, final SortParameters params) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort].");
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				List<byte[]> byteList = connection.sort(keySerializer.serialize(key), params);
				List<V> values = CollectionUtils.newArrayList();
				for (byte[] bytes : byteList)
					values.add(valueSerializer.deserialize(bytes));
				return values;
			}
		});
	}

	@Override
	public <K, V> Long sortCount(K key, SortParameters params, K targetKey) {
		return sortCount(0, key, params, targetKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long sortCount(final int dbIndex, final K key, final SortParameters params, final K targetKey) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort].");
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort].");
		AssertUtils.assertNotNull(targetKey, "Target key can not be null of command [sort].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.sort(keySerializer.serialize(key), params, keySerializer.serialize(targetKey));
			}
		});
	}

	@Override
	public <K, V> List<V> sortResult(K key, SortParameters params, K targetKey) {
		return sortResult(0, key, params, targetKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> List<V> sortResult(final int dbIndex, final K key, final SortParameters params, final K targetKey) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sort].");	
		AssertUtils.assertNotNull(params, "Sort parameters can not be null of command [sort].");
		AssertUtils.assertNotNull(targetKey, "Target key can not be null of command [sort].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] targetKeyByte = keySerializer.serialize(targetKey);
				Long count = connection.sort(keySerializer.serialize(key), params, targetKeyByte);
				if (count != null && count > 0) {
					DataType dataType = connection.type(targetKeyByte);
					return listByDateType(dataType, connection, dbIndex, targetKeyByte);
				}
				return null;
			}
		});
	}

	@Override
	public <K> DataType type(K key) {
		return type(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> DataType type(final int dbIndex, final K key) {
		if (key == null)
			return DataType.NONE;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<DataType>() {

			@Override
			public DataType doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.type(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K, V> Long append(K key, V value) {
		return append(0, key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long append(final int dbIndex, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [append].");
		AssertUtils.assertNotNull(key, "Value can not be null of command [append].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.append(keySerializer.serialize(key), valueSerializer.serialize(value));
			}
		});
	}

	@Override
	public <K, V> V get(K key) {
		return get(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V get(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

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
		return getRange(0, key, begin, end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V getRange(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

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
		return getSet(0, key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V getSet(final int dbIndex, final K key, final V value) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] keyByte = keySerializer.serialize(key);
				return valueSerializer.deserialize(connection.getSet(keyByte, valueSerializer.serialize(value)));
			}
		});
	}
	
	@Override
	public <K, V> List<V> mGet(K[] keys) {
		return mGet(0, keys);
	}

	@Override
	public <K, V> List<V> mGet(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return redisTemplate.execute(new RedisCallback<List<V>>() {

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
		return mGet(0, keys);
	}

	@Override
	public <K, V> List<V> mGet(int dbIndex, Collection<K> keys) {
		return mGet(dbIndex, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long strLen(K key) {
		return strLen(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long strLen(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.strLen(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K, F> Boolean hDel(K key, F filed) {
		return hDel(0, key, filed);
	}

	@Override
	public <K, F> Boolean hDel(int dbIndex, K key, F filed) {
		if (key == null || filed == null)
			return false;
		
		return hDel(dbIndex, key, new Object[] { filed });
	}

	@Override
	public <K, F> Boolean hDel(K key, F[] fileds) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F> Boolean hDel(final int dbIndex, final K key, final F[] fileds) {
		if (key == null || ArrayUtils.isEmpty(fileds))
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<F> hashKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] keyByte = keySerializer.serialize(key);
				boolean result = true;
				for (F filed : fileds) 
					result = result && connection.hDel(keyByte, hashKeySerializer.serialize(filed));
				return result;
			}
		});
	}

	@Override
	public <K, F> Boolean hDel(K key, Collection<F> fileds) {
		return hDel(0, key, fileds);
	}

	@Override
	public <K, F> Boolean hDel(int dbIndex, K key, Collection<F> fileds) {
		return hDel(dbIndex, key, CollectionUtils.toObjectArray(fileds));
	}

	@Override
	public <K, F> Boolean hExists(K key, F filed) {
		return hExists(0, key, filed);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F> Boolean hExists(final int dbIndex, final K key, final F filed) {
		if (key == null || filed == null)
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<F> hashKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.hExists(keySerializer.serialize(key), hashKeySerializer.serialize(filed));
			}
		});
	}

	@Override
	public <K, F, V> V hGet(K key, F filed) {
		return hGet(0, key, filed);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F, V> V hGet(final int dbIndex, final K key, final F filed) {
		if (key == null || filed == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<F> hashKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(dbIndex);
		final RedisSerializer<V> hashValueSerializer = (RedisSerializer<V>) selectHashValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

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
		return hGetAll(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F, V> Map<F, V> hGetAll(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Map<F, V>>() {

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
		return hKeys(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F> Set<F> hKeys(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Set<F>>() {

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
		return hLen(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long hLen(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.hLen(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K, F, V> List<V> hMGet(K key, F[] fields) {
		return hMGet(0, key, fields);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, F, V> List<V> hMGet(final int dbIndex, final K key, final F[] fields) {
		if (key == null || ArrayUtils.isEmpty(fields))
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<List<V>>() {

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
		return hMGet(0, key, fields);
	}

	@Override
	public <K, F, V> List<V> hMGet(int dbIndex, K key, Collection<F> fields) {
		return hMGet(dbIndex, key, CollectionUtils.toObjectArray(fields));
	}

	@Override
	public <K, V> List<V> hVals(K key) {
		return hVals(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> List<V> hVals(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				List<byte[]> hashValueBytes = connection.hVals(keySerializer.serialize(key));
				return deserializeHashValueByteToList(dbIndex, hashValueBytes);
			}
		});
	}

	@Override
	public <K, V> V lIndex(K key, long index) {
		return lIndex(0, key, index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V lIndex(final int dbIndex, final K key, final long index) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

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
		return lLen(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long lLen(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				return connection.lLen(keySerializer.serialize(key));
			}
			
		});
	}

	@Override
	public <K, V> V lPop(K key) {
		return lPop(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V lPop(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] valueByte = connection.lPop(keySerializer.serialize(key));
				return valueSerializer.deserialize(valueByte);
			}
		});
	}

	@Override
	public <K, V> List<V> lRange(K key, long start, long end) {
		return lRange(0, key, start, end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> List<V> lRange(final int dbIndex, final K key, final long start, final long end) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<List<V>>() {

			@Override
			public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);	
				List<byte[]> valueBytes = connection.lRange(keySerializer.serialize(key), start, end);
				return deserializeValueByteToList(dbIndex, valueBytes);
			}
		});
	}

	@Override
	public <K, V> Long lRem(K key, long count, V value) {
		return lRem(0, key, count, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long lRem(final int dbIndex, final K key, final long count, final V value) {
		if (key == null || value == null)
			return 0L;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

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
		return lRemAll(0, key, value);
	}

	@Override
	public <K, V> Long lRemAll(int dbIndex, K key, V value) {
		return lRem(dbIndex, key, 0L, value);
	}

	@Override
	public <K> void lTrim(K key, long begin, long end) {
		lTrim(0, key, begin, end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> void lTrim(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				connection.lTrim(keySerializer.serialize(key), begin, end);
				return null;
			}
		});
	}

	@Override
	public <K, V> V rPop(K key) {
		return rPop(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V rPop(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] valueByte = connection.rPop(keySerializer.serialize(key));
				return valueSerializer.deserialize(valueByte);
			}
		});
	}

	@Override
	public <K> Long sCard(K key) {
		return sCard(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long sCard(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.sCard(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K, V> Set<V> sDiff(K[] keys) {
		return sDiff(0, keys);
	}

	@Override
	public <K, V> Set<V> sDiff(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return redisTemplate.execute(new RedisCallback<Set<V>>() {

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
		return sDiff(0, keys);
	}

	@Override
	public <K, V> Set<V> sDiff(int dbIndex, Collection<K> keys) {
		return sDiff(dbIndex, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long sDiffStore(K[] keys, K destKey) {
		return sDiffStore(0, keys, destKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long sDiffStore(final int dbIndex, final K[] keys, final K destKey) {
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(keys), "Source keys can not be empty of command [sDiffStore]");
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sDiffStore].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sDiffStore(destKeyByte, serializeKeysToArray(dbIndex, keys));
				setExpireTime(connection, repository, destKeyByte);	
				return result;
			}
		});
	}

	@Override
	public <K> Long sDiffStore(Collection<K> keys, K destKey) {
		return sDiffStore(0, keys, destKey);
	}

	@Override
	public <K> Long sDiffStore(int dbIndex, Collection<K> keys, K destKey) {
		return sDiffStore(dbIndex, CollectionUtils.toObjectArray(keys), destKey);
	}

	@Override
	public <K, V> Set<V> sInter(K[] keys) {
		return sInter(0, keys);
	}

	@Override
	public <K, V> Set<V> sInter(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return redisTemplate.execute(new RedisCallback<Set<V>>() {

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
		return sInter(0, keys);
	}

	@Override
	public <K, V> Set<V> sInter(int dbIndex, Collection<K> keys) {
		return sInter(dbIndex, CollectionUtils.toObjectArray(keys));
	}

	@Override
	public <K> Long sInterStore(K[] keys, K destKey) {
		return sInterStore(0, keys, destKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long sInterStore(final int dbIndex, final K[] keys, final K destKey) {
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(keys), "Source keys can not be empty of command [sInterStore]");
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sInterStore].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sInterStore(destKeyByte, serializeKeysToArray(dbIndex, keys));
				setExpireTime(connection, repository, destKeyByte);	
				return result;
			}
		});
	}

	@Override
	public <K> Long sInterStore(Collection<K> keys, K destKey) {
		return sInterStore(0, keys, destKey);
	}

	@Override
	public <K> Long sInterStore(int dbIndex, Collection<K> keys, K destKey) {
		return sInterStore(dbIndex, CollectionUtils.toObjectArray(keys), destKey);
	}
	
	@Override
	public <K, V> Set<V> sUnion(K[] keys) {
		return sUnion(0, keys);
	}

	@Override
	public <K, V> Set<V> sUnion(final int dbIndex, final K[] keys) {
		if (ArrayUtils.isEmpty(keys))
			return null;
		
		return redisTemplate.execute(new RedisCallback<Set<V>>() {

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
		return sUnion(0, keys);
	}

	@Override
	public <K, V> Set<V> sUnion(int dbIndex, Collection<K> keys) {
		return sUnion(dbIndex, CollectionUtils.toObjectArray(keys));
	}
	
	@Override
	public <K> Long sUnionStore(K[] keys, K destKey) {
		return sUnionStore(0, keys, destKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long sUnionStore(final int dbIndex, final K[] keys, final K destKey) {
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(keys), "Source keys can not be empty of command [sUnionStore]");
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [sUnionStore].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisRepository repository = select(connection, dbIndex);
				byte[] destKeyByte = keySerializer.serialize(destKey);
				Long result = connection.sUnionStore(destKeyByte, serializeKeysToArray(dbIndex, keys));
				setExpireTime(connection, repository, destKeyByte);	
				return result;
			}
		});
	}

	@Override
	public <K> Long sUnionStore(Collection<K> keys, K destKey) {
		return sUnionStore(0, keys, destKey);
	}

	@Override
	public <K> Long sUnionStore(int dbIndex, Collection<K> keys, K destKey) {
		return sUnionStore(dbIndex, CollectionUtils.toObjectArray(keys), destKey);
	}

	@Override
	public <K, V> Boolean sIsMember(K key, V member) {
		return sIsMember(0, key, member);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Boolean sIsMember(final int dbIndex, final K key, final V member) {
		if (key == null || member == null)
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.sIsMember(keySerializer.serialize(key), valueSerializer.serialize(member));
			}
		});
	}

	@Override
	public <K, V> Set<V> sMembers(K key) {
		return sMembers(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Set<V> sMembers(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Set<V>>() {

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
		return sMove(0, srcKey, destKey, member);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Boolean sMove(final int dbIndex, final K srcKey, final K destKey, final V member) {
		if (srcKey == null || destKey == null || member == null)
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

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
		return sPop(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V sPop(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

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
		return sRandMember(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> V sRandMember(final int dbIndex, final K key) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				byte[] memberByte = connection.sRandMember(keySerializer.serialize(key));
				return valueSerializer.deserialize(memberByte);
			}
		});
	}

	@Override
	public <K, V> Boolean sRem(K key, V member) {
		return sRem(0, key, member);
	}

	@Override
	public <K, V> Boolean sRem(int dbIndex, K key, V member) {
		return sRem(0, key, new Object[] { member });
	}

	@Override
	public <K, V> Boolean sRem(K key, V[] members) {
		return sRem(0, key, members);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Boolean sRem(final int dbIndex, final K key, final V[] members) {
		if (key == null || ArrayUtils.isEmpty(members))
			return false;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				boolean result = true;
				for (V member : members) 
					result = result && connection.sRem(keySerializer.serialize(key), valueSerializer.serialize(member));
				return result;
			}
		});
	}

	@Override
	public <K, V> Boolean sRem(K key, Collection<V> members) {
		return sRem(0, key, members);
	}

	@Override
	public <K, V> Boolean sRem(int dbIndex, K key, Collection<V> members) {
		return sRem(0, key, CollectionUtils.toObjectArray(members));
	}

	@Override
	public <K> Long zCard(K key) {
		return zCard(0, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long zCard(final int dbIndex, final K key) {
		if (key == null)
			return 0L;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zCard(keySerializer.serialize(key));
			}
		});
	}

	@Override
	public <K> Long zCount(K key, double minScore, double maxScore) {
		return zCount(0, minScore, maxScore);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K> Long zCount(final int dbIndex, final K key, final double minScore, final double maxScore) {
		if (key == null)
			return 0L;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				select(connection, dbIndex);
				return connection.zCount(keySerializer.serialize(key), minScore, maxScore);
			}
		});
	}

	@Override
	public <K, V> Set<V> zRange(K key, long begin, long end) {
		return zRange(0, key, begin, end);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Set<V> zRange(final int dbIndex, final K key, final long begin, final long end) {
		if (key == null)
			return null;
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(dbIndex);
		return redisTemplate.execute(new RedisCallback<Set<V>>() {

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
		return zRangeAll(0, key);
	}

	@Override
	public <K, V> Set<V> zRangeAll(int dbIndex, K key) {
		return zRange(dbIndex, key, 0, -1);
	}
	
}
