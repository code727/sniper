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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NumberUtils;
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
	public <K, V> void set(final int index, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [set].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [set].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		redisTemplate.execute(new RedisCallback<Object>() {
			
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, index);
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
	public <K, V> Boolean setNX(final int index, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setNX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setNX].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, index);
				Boolean result = connection.setNX(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}
	
	@Override
	public <K, V> void mSet(Map<K, V> kValues) {
		mSet(0, kValues);
	}

	@Override
	public <K, V> void mSet(final int index, final Map<K, V> kValues) {
		AssertUtils.assertTrue(MapUtils.isNotEmpty(kValues),
				"Key-value map can not be empty of command [mSet].");
		
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(index, kValues);
				RedisRepository repository = select(connection, index);
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
	public <K, V> void mSetNX(final int index, final Map<K, V> kValues) {
		AssertUtils.assertTrue(MapUtils.isNotEmpty(kValues),
				"Key-value map can not be empty of command [mSetNX].");
		
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				Map<byte[], byte[]> byteMap = serializeKeyValueToByteMap(index, kValues);
				RedisRepository repository = select(connection, index);
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
	public <K, V> void setRange(final int index, final K key, final long offset, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [setRange].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [setRange].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, index);
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
	public <K, F, V> Boolean hSet(final int index, final K key, final F field, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hSet].");
		AssertUtils.assertNotNull(field, "Field can not be null of command [hSet].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [hSet].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<F> fieldKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, index);
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
	public <K, F, V> Boolean hSetNX(final int index, final K key, final F field, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hSetNX].");
		AssertUtils.assertNotNull(field, "Field can not be null of command [hSetNX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [hSetNX].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<F> fieldKeySerializer = (RedisSerializer<F>) selectHashKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, index);
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
	public <K, F, V> void hMSet(final int index, final K key, final Map<F, V> fValues) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [hMSet].");
		AssertUtils.assertTrue(MapUtils.isNotEmpty(fValues),
				"Field-value map can not be empty of command [hMSet].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, index);
				connection.hMSet(keyByte, serializeFiledValueToByteMap(index, fValues));
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
	public <K, V> Long lInsert(final int index, final K key, final Position where, final V pivot, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lInsert].");
		AssertUtils.assertNotNull(where, "Insert postion can not be null of command [lInsert].");
		AssertUtils.assertNotNull(pivot, "Postion value can not be null of command [lInsert].");
		AssertUtils.assertNotNull(value, "Insert value can not be null of command [lInsert].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, index);
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
	public <K, V> void lSet(final int index, final K key, final long posttion, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lSet].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [lSet].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);	
				RedisRepository repository = select(connection, index);
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
	public <K, V> Long lPush(int index, K key, V value) {
		AssertUtils.assertNotNull(value, "Value can not be null of command [lPush].");
		Collection<V> values = CollectionUtils.newArrayList();
		values.add(value);
		return lPush(index, key, values);
	}
	
	@Override
	public <K, V> Long lPush(K key, Collection<V> values) {
		return lPush(0, key, values); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long lPush(final int index, final K key, final Collection<V> values) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lPush].");
		AssertUtils.assertTrue(CollectionUtils.isNotEmpty(values), 
				"Values collection can not be empty of command [lPush].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, index);
				Iterator<V> iterator = values.iterator();
				long result = 0;
				while (iterator.hasNext()) {
					V value = iterator.next();
					result = result + NumberUtils.safeLong(connection.lPush(keyByte, valueSerializer.serialize(value)));
				}
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}

	@Override
	public <K, V> Long lPushX(K key, V value) {
		return lPushX(0, key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long lPushX(final int index, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [lPushX].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [lPushX].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, index);
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

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long rPush(final int index, final K key, final V value) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [rPush].");
		AssertUtils.assertNotNull(value, "Value can not be null of command [rPush].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, index);
				Long result = connection.rPush(keyByte, valueSerializer.serialize(value));
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}

	@Override
	public <K, V> Long rPushX(K key, V value) {
		return rPushX(0, key, value);
	}

	@Override
	public <K, V> Long rPushX(int index, K key, V value) {
		AssertUtils.assertNotNull(value, "Value can not be null of command [rPushX].");
		Collection<V> values = CollectionUtils.newArrayList();
		values.add(value);
		return rPushX(index, key, values);
	}
	
	@Override
	public <K, V> Long rPush(K key, Collection<V> values) {
		return rPush(0, key, values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Long rPush(final int index, final K key, final Collection<V> values) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [rPush].");
		AssertUtils.assertTrue(CollectionUtils.isNotEmpty(values), 
				"Values collection can not be empty of command [rPush].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, index);
				Iterator<V> iterator = values.iterator();
				long result = 0;
				while (iterator.hasNext()) {
					V value = iterator.next();
					result = result + NumberUtils.safeLong(connection.rPush(keyByte, valueSerializer.serialize(value)));
				}
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
	public <K, V> V rPopLPush(final int index, final K srcKey, final K destKey) {
		AssertUtils.assertNotNull(srcKey, "Source key can not be null of command [rPopLPush].");
		AssertUtils.assertNotNull(destKey, "Destination key can not be null of command [rPopLPush].");
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		return redisTemplate.execute(new RedisCallback<V>() {

			@Override
			public V doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] destKeyByte = keySerializer.serialize(destKey);
				RedisRepository repository = select(connection, index);
				// 将源列表中最后一个元素取出后存入目标列表
				byte[] destValueByte = connection.rPopLPush(keySerializer.serialize(srcKey), destKeyByte);
				// 设置目标键的过期时间
				setExpireTime(connection, repository, destKeyByte);
				return (V) selectValueSerializer(index).deserialize(destValueByte);
			}
		});
	}

	@Override
	public <K, V> Boolean sAdd(K key, V member) {
		return sAdd(0, key, member);
	}

	@Override
	public <K, V> Boolean sAdd(int index, K key, V member) {
		AssertUtils.assertNotNull(member, "Member can not be null of command [sAdd].");
		Collection<V> members = CollectionUtils.newArrayList();
		members.add(member);
		return sAdd(index, key, members);
	}
	
	@Override
	public <K, V> Boolean sAdd(K key, Collection<V> members) {
		return sAdd(0, key, members);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Boolean sAdd(final int index, final K key, final Collection<V> members) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [sAdd].");
		AssertUtils.assertTrue(CollectionUtils.isNotEmpty(members), 
				"Members collection can not be empty of command [sAdd].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, index);
				Iterator<V> iterator = members.iterator();
				boolean result = true;
				while (iterator.hasNext()) {
					V member = iterator.next();
					result = result && connection.sAdd(keyByte, valueSerializer.serialize(member));
				}
				setExpireTime(connection, repository, keyByte);
				return result;
			}
		});
	}

	@Override
	public <K, V> Boolean zAdd(K key, double score, V member) {
		return zAdd(0, key, score, member);
	}

	@Override
	public <K, V> Boolean zAdd(int index, K key, double score, V member) {
		AssertUtils.assertNotNull(member, "Member can not be null of command [zAdd].");
		Map<Double, V> scoreMembers = MapUtils.newHashMap();
		scoreMembers.put(score, member);
		return zAdd(index, key, scoreMembers);
	}

	@Override
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers) {
		return zAdd(0, key, scoreMembers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Boolean zAdd(final int index, final K key, final Map<Double, V> scoreMembers) {
		AssertUtils.assertNotNull(key, "Key can not be null of command [zAdd].");
		AssertUtils.assertTrue(MapUtils.isNotEmpty(scoreMembers), 
				"Score-member map can not be empty of command [zAdd].");
		
		final RedisSerializer<K> keySerializer = (RedisSerializer<K>) selectKeySerializer(index);
		final RedisSerializer<V> valueSerializer = (RedisSerializer<V>) selectValueSerializer(index);
		return redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = keySerializer.serialize(key);
				RedisRepository repository = select(connection, index);
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

}
