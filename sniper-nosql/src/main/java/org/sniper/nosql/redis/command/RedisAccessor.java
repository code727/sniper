/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-1-24
 */

package org.sniper.nosql.redis.command;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.nosql.redis.enums.DataType;
import org.sniper.nosql.redis.enums.ListPosition;
import org.sniper.nosql.redis.enums.Section;
import org.sniper.nosql.redis.model.ZSetTuple;
import org.sniper.nosql.redis.option.Limit;
import org.sniper.nosql.redis.option.SortOptional;
import org.sniper.nosql.redis.option.ZStoreOptional;
import org.sniper.spring.beans.CheckableInitializingBean;

/**
 * Redis访问器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class RedisAccessor extends CheckableInitializingBean implements RedisCommands {
	
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
	public <K> K randomKey() {
		return randomKey((String) null);
	}
	
	@Override
	public <K> K randomKey(Class<K> keyType) {
		return randomKey(null, keyType);
	}
	
	@Override
	public <K> K randomKey(String dbName) {
		return randomKey(dbName, null);
	}
	
	@Override
	public <K> Long del(K key) {
		return del(null, key);
	}
	
	@Override
	public <K> Long del(String dbName, K key) {
		return del(dbName, ArrayUtils.toWrapperTypeArray(key));
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
	public <K> Boolean exists(K key) {
		return exists(null, key);
	}
		
	@Override
	public <K> Boolean expire(K key, long seconds) {
		return expire(null, seconds);
	}
	
	@Override
	public <K> Boolean expireAt(K key, long unixTimestamp) {
		return expireAt(null, key, unixTimestamp);
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
	public <K> Boolean pExpireAt(K key, long timestamp) {
		return pExpireAt(null, key, timestamp);
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
	public <K> Boolean persist(K key) {
		return persist(null, key);
	}
	
	@Override
	public <K> Boolean move(K key, int dbIndex) {
		return move(null, key, dbIndex);
	}
	
	@Override
	public <K> Long ttl(K key) {
		return ttl(null, key);
	}
	
	@Override
	public <K> Long pTtl(K key) {
		return pTtl(null, key);
	}
	
	@Override
	public <K, V> List<V> sort(K key) {
		return sort(null, key);
	}
	
	@Override
	public <K, V> List<V> sort(K key, Class<V> valueType) {
		return sort(null, key, valueType);
	}
	
	@Override
	public <K, V> List<V> sort(String dbName, K key) {
		return sort(dbName, key, null);
	}
	
	@Override
	public <K, V> List<V> sort(String dbName, K key, Class<V> valueType) {
		return sortByOptional(dbName, key, null, valueType);
	}
	
	@Override
	public <K, V> List<V> sortByOptional(K key, SortOptional optional) {
		return sortByOptional(key, optional, null);
	}
	
	@Override
	public <K, V> List<V> sortByOptional(K key, SortOptional optional, Class<V> valueType) {
		return sortByOptional(null, key, optional, valueType);
	}
	
	@Override
	public <K, V> List<V> sortByOptional(String dbName, K key, SortOptional optional) {
		return sortByOptional(null, key, optional, null);
	}
	
	@Override
	public <K> Long sortStore(K key, K destKey) {
		return sortStore(key, destKey, 0);
	}

	@Override
	public <K> Long sortStore(K key, K destKey, long expireSeconds) {
		return sortStore(null, key, destKey, expireSeconds);
	}

	@Override
	public <K> Long sortStoreIn(String dbName, K key, K destKey) {
		return sortStore(dbName, key, destKey, 0);
	}

	@Override
	public <K> Long sortStore(String dbName, K key, K destKey, long expireSeconds) {
		return sortStoreByOptional(dbName, key, null, destKey, expireSeconds);
	}

	@Override
	public <K> Long sortStoreByOptional(K key, SortOptional optional, K destKey) {
		return sortStoreByOptional(key, optional, destKey, 0);
	}

	@Override
	public <K> Long sortStoreByOptional(K key, SortOptional optional, K destKey, long expireSeconds) {
		return sortStoreByOptional(null, key, optional, destKey, expireSeconds);
	}

	@Override
	public <K> Long sortStoreByOptional(String dbName, K key, SortOptional optional, K destKey) {
		return sortStoreByOptional(dbName, key, optional, destKey, 0);
	}
	
	@Override
	public <K> DataType type(K key) {
		return type(null, key);
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
	public <K, V> void mSet(Map<K, V> keyValues) {
		mSet(null, keyValues);
	}
	
	@Override
	public <K, V> void mSet(Map<K, V> keyValues, long expireSeconds) {
		mSet(null, keyValues, expireSeconds);
	}

	@Override
	public <K, V> void mSet(String dbName, Map<K, V> keyValues) {
		mSet(dbName, keyValues, 0);
	}
	
	@Override
	public <K, V> Boolean mSetNX(Map<K, V> keyValues) {
		return mSetNX(null, keyValues);
	}
	
	@Override
	public <K, V> Boolean mSetNX(Map<K, V> keyValues, long expireSeconds) {
		return mSetNX(null, keyValues, expireSeconds);
	}

	@Override
	public <K, V> Boolean mSetNX(String dbName, Map<K, V> keyValues) {
		return mSetNX(dbName, keyValues, 0);
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
	public <K> Long strLen(K key) {
		return strLen(null, key);
	}
	
	@Override
	public <K> Long decr(K key) {
		return decr(null, key);
	}
	
	@Override
	public <K> Long decrBy(K key, long value) {
		return decrBy(null, key, value);
	}
	
	@Override
	public <K> Long incr(K key) {
		return incr(null, key);
	}
	
	@Override
	public <K> Long incrBy(K key, long value) {
		return incrBy(null, key, value);
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
	public <K, H> Long hDel(K key, H hashKey) {
		return hDel(null, key, hashKey);
	}

	@Override
	public <K, H> Long hDel(String dbName, K key, H hashKey) {
		if (hashKey == null)
			return 0L;
		
		return hDel(dbName, key, ArrayUtils.toWrapperTypeArray(hashKey));
	}

	@Override
	public <K, H> Long hDel(K key, H[] hashKeys) {
		return hDel(null, key, hashKeys);
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
	public <K> Long hLen(K key) {
		return hLen(null, key);
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
	public <K, V> Long lInsert(K key, ListPosition where, V pivot, V value) {
		return lInsert(key, where, pivot, value, 0);
	}
	
	@Override
	public <K, V> Long lInsert(K key, ListPosition where, V pivot, V value, long expireSeconds) {
		return lInsert(null, key, where, pivot, value, expireSeconds);
	}

	@Override
	public <K, V> Long lInsertIn(String dbName, K key, ListPosition where, V pivot, V value) {
		return lInsert(dbName, key, where, pivot, value, 0);
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
		return lPush(dbName, key, ArrayUtils.toWrapperTypeArray(value), expireSeconds);
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
	public <K, V> Long lPush(K key, Collection<V> values) {
		return lPush(null, key, values); 
	}
	
	@Override
	public <K, V> Long lPush(K key, Collection<V> values, long expireSeconds) {
		return lPush(null, key, values, expireSeconds); 
	}

	@Override
	public <K, V> Long lPush(String dbName, K key, Collection<V> values) {
		return lPush(dbName, key, values, 0);
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
	public <K> Long lLen(K key) {
		return lLen(null, key);
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
		return rPush(dbName, key, ArrayUtils.toWrapperTypeArray(value), expireSeconds);
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
		return sAdd(dbName, key, ArrayUtils.toWrapperTypeArray(member), expireSeconds);
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
	public <K, V> Boolean sIsMember(K key, V member) {
		return sIsMember(null, key, member);
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
	public <K, V> Long sRem(K key, V member) {
		return sRem(null, key, member);
	}

	@Override
	public <K, V> Long sRem(String dbName, K key, V member) {
		return sRem(null, key, ArrayUtils.toWrapperTypeArray(member));
	}

	@Override
	public <K, V> Long sRem(K key, V[] members) {
		return sRem(null, key, members);
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
	public <K> Long zCard(K key) {
		return zCard(null, key);
	}
	
	@Override
	public <K> Long zCount(K key, double minScore, double maxScore) {
		return zCount(null, key, minScore, maxScore);
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
		return zRangeByScore(key, minScore, maxScore, null, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, Class<V> valueType) {
		return zRangeByScore(key, minScore, maxScore, null, valueType);
	}

	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, Limit limit) {
		return zRangeByScore(key, minScore, maxScore, limit, null);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, Limit limit, Class<V> valueType) {
		return zRangeByScore(null, key, minScore, maxScore, limit, valueType);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore) {
		return zRangeByScore(dbName, key, minScore, maxScore, null, null);
	}

	@Override
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, Class<V> valueType) {
		return zRangeByScore(dbName, key, minScore, maxScore, null, valueType);
	}
	
	@Override
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, Limit limit) {
		return zRangeByScore(dbName, key, minScore, maxScore, limit, null);
	}
	
	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(K key, double minScore, double maxScore) {
		return zRangeByScoreWithScores(key, minScore, maxScore, null, null);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(K key, double minScore, double maxScore, Class<V> valueType) {
		return zRangeByScoreWithScores(key, minScore, maxScore, null, valueType);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(K key, double minScore, double maxScore, Limit limit) {
		return zRangeByScoreWithScores(key, minScore, maxScore, limit, null);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(K key, double minScore, double maxScore, Limit limit, Class<V> valueType) {
		return zRangeByScoreWithScores(null, key, minScore, maxScore, limit, valueType);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore) {
		return zRangeByScoreWithScores(dbName, key, minScore, maxScore, null, null);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Class<V> valueType) {
		return zRangeByScoreWithScores(dbName, key, minScore, maxScore, null, valueType);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Limit limit) {
		return zRangeByScoreWithScores(dbName, key, minScore, maxScore, limit, null);
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
		return zRevRangeByScore(key, minScore, maxScore, null, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore, Class<V> valueType) {
		return zRevRangeByScore(key, minScore, maxScore, null, valueType);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore, Limit limit) {
		return zRevRangeByScore(key, minScore, maxScore, limit, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore, Limit limit, Class<V> valueType) {
		return zRevRangeByScore(null, key, minScore, maxScore, limit, valueType);
	}

	@Override
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore) {
		return zRevRangeByScore(dbName, key, minScore, maxScore, null, null);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore, Class<V> valueType) {
		return zRevRangeByScore(dbName, key, minScore, maxScore, null, valueType);
	}
	
	@Override
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore, Limit limit) {
		return zRevRangeByScore(dbName, key, minScore, maxScore, limit, null);
	}
	
	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(K key, double minScore, double maxScore) {
		return zRevRangeByScoreWithScores(key, minScore, maxScore, null, null);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(K key, double minScore, double maxScore, Class<V> valueType) {
		return zRevRangeByScoreWithScores(key, minScore, maxScore, null, valueType);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(K key, double minScore, double maxScore, Limit limit) {
		return zRevRangeByScoreWithScores(key, minScore, maxScore, limit, null);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(K key, double minScore, double maxScore, Limit limit, Class<V> valueType) {
		return zRevRangeByScoreWithScores(null, key, minScore, maxScore, limit, valueType);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore) {
		return zRevRangeByScoreWithScores(dbName, key, minScore, maxScore, null, null);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Class<V> valueType) {
		return zRevRangeByScoreWithScores(dbName, key, minScore, maxScore, null, valueType);
	}

	@Override
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Limit limit) {
		return zRevRangeByScoreWithScores(dbName, key, minScore, maxScore, limit, null);
	}
	
	@Override
	public <K, V> Long zRank(K key, V member) {
		return zRank(null, key, member);
	}
	
	@Override
	public <K, V> Long zRevRank(K key, V member) {
		return zRevRank(null, key, member);
	}
	
	@Override
	public <K, V> Long zRem(K key, V member) {
		return zRem(null, key, member);
	}

	@Override
	public <K, V> Long zRem(String dbName, K key, V member) {
		return zRem(dbName, key, ArrayUtils.toWrapperTypeArray(member));
	}

	@Override
	public <K, V> Long zRem(K key, V[] members) {
		return zRem(null, key, members);
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
	public <K> Long zRemRangeByScore(K key, double minScore, double maxScore) {
		return zRemRangeByScore(null, key, minScore, maxScore);
	}
	
	@Override
	public <K, V> Double zScore(K key, V member) {
		return zScore(null, key, member);
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
		return zUnionStore(dbName, destKey, ArrayUtils.toWrapperTypeArray(key), expireSeconds);
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
	public <K> Long zUnionStore(K destKey, K[] keys, ZStoreOptional option) {
		return zUnionStore(destKey, keys, option, 0);
	}
	
	@Override
	public <K> Long zUnionStore(K destKey, K[] keys, ZStoreOptional option, long expireSeconds) {
		return zUnionStore(null, destKey, keys, option, expireSeconds);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, K[] keys, ZStoreOptional option) {
		return zUnionStore(dbName, destKey, keys, option, 0);
	}
	
	@Override
	public <K> Long zUnionStore(K destKey, Collection<K> keys, ZStoreOptional option) {
		return zUnionStore(destKey, keys, option, 0);
	}
	
	@Override
	public <K> Long zUnionStore(K destKey, Collection<K> keys, ZStoreOptional option, long expireSeconds) {
		return zUnionStore(null, destKey, keys, option, expireSeconds);
	}

	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys, ZStoreOptional option) {
		return zUnionStore(dbName, destKey, keys, option, 0);
	}
	
	@Override
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys, ZStoreOptional option, long expireSeconds) {
		return zUnionStore(dbName, destKey, CollectionUtils.toObjectArray(keys), option, expireSeconds);
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
		return zInterStore(dbName, destKey, ArrayUtils.toWrapperTypeArray(srcKey), expireSeconds);
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
	public <K> Long zInterStore(K destKey, K[] keys, ZStoreOptional option) {
		return zInterStore(destKey, keys, option, 0);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, K[] keys, ZStoreOptional option, long expireSeconds) {
		return zInterStore(null, destKey, keys, option, expireSeconds);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, K[] keys, ZStoreOptional option) {
		return zInterStore(dbName, destKey, keys, option, 0);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, Collection<K> keys, ZStoreOptional option) {
		return zInterStore(destKey, keys, option, 0);
	}
	
	@Override
	public <K> Long zInterStore(K destKey, Collection<K> keys, ZStoreOptional option, long expireSeconds) {
		return zInterStore(null, destKey, keys, option, expireSeconds);
	}

	@Override
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys, ZStoreOptional option) {
		return zInterStore(dbName, destKey, keys, option, 0);
	}
	
	@Override
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys, ZStoreOptional option, long expireSeconds) {
		return zInterStore(dbName, destKey, CollectionUtils.toObjectArray(keys), option, expireSeconds);
	}

	@Override
	public <K, V> Double zIncrBy(K key, double increment, V member) {
		return zIncrBy(null, key, increment, member);
	}
	
	@Override
	public <K,V> Long pfAdd(K key, V element) {
		return pfAdd(key, element, 0);
	}
	
	@Override
	public <K,V> Long pfAdd(K key, V element, long expireSeconds) {
		return pfAdd(null, key, element, expireSeconds);
	}
	
	@Override
	public <K,V> Long pfAddIn(String dbName, K key, V element) {
		return pfAdd(dbName, key, element, 0);
	}
	
	@Override
	public <K,V> Long pfAdd(String dbName, K key, V element, long expireSeconds) {
		return pfAdd(dbName, key, ArrayUtils.toWrapperTypeArray(element), expireSeconds);
	}
	
	@Override
	public <K,V> Long pfAdd(K key, V[] elements) {
		return pfAdd(key, elements, 0);
	}
	
	@Override
	public <K,V> Long pfAdd(K key, V[] elements, long expireSeconds) {
		return pfAdd(null, key, elements, expireSeconds);
	}
	
	@Override
	public <K,V> Long pfAdd(String dbName, K key, V[] elements) {
		return pfAdd(dbName, key, elements, 0);
	}
	
	@Override
	public <K,V> Long pfAdd(K key, Collection<V> elements) {
		return pfAdd(key, elements, 0);
	}
	
	@Override
	public <K,V> Long pfAdd(K key, Collection<V> elements, long expireSeconds) {
		return pfAdd(null, key, elements, expireSeconds);
	}
	
	@Override
	public <K,V> Long pfAdd(String dbName, K key, Collection<V> elements) {
		return pfAdd(dbName, key, elements, 0);
	}
	
	@Override
	public <K,V> Long pfAdd(String dbName, K key, Collection<V> elements, long expireSeconds) {
		return pfAdd(dbName, key, CollectionUtils.toObjectArray(elements), expireSeconds);
	}
	
	@Override
	public <K> Long pfCount(K key) {
		return pfCount(null, key);
	}
	
	@Override
	public <K> Long pfCount(String dbName, K key) {
		return pfCount(dbName, ArrayUtils.toWrapperTypeArray(key));
	}
	
	@Override
	public <K> Long pfCount(K[] keys) {
		return pfCount(null, keys);
	}
	
	@Override
	public <K> Long pfCount(Collection<K> keys) {
		return pfCount(null, keys);
	}
	
	@Override
	public <K> Long pfCount(String dbName, Collection<K> keys) {
		return pfCount(dbName, CollectionUtils.toObjectArray(keys));
	}
	
	@Override
	public <K> void pfMerge(K destKey, K sourceKey) {
		pfMerge(null, destKey, sourceKey);
	}
	
	@Override
	public <K> void pfMerge(String dbName, K destKey, K sourceKey) {
		pfMerge(dbName, destKey, ArrayUtils.toWrapperTypeArray(sourceKey));
	}
		
	@Override
	public <K> void pfMerge(K destKey, K[] sourceKeys) {
		pfMerge(null, destKey, sourceKeys);
	}
		
	@Override
	public <K> void pfMerge(K destKey, Collection<K> sourceKeys) {
		pfMerge(null, destKey, sourceKeys);
	}
	
	@Override
	public <K> void pfMerge(String dbName, K destKey, Collection<K> sourceKeys) {
		pfMerge(dbName, destKey, CollectionUtils.toArray(sourceKeys));
	}
		
	@Override
	public Properties info() {
		return info((Section) null);
	}
	
	@Override
	public <T> T info(String key) {
		return info(key, null);
	}
	
	@Override
	public Properties configGet() {
		return configGet(null);
	}
	
	@Override
	public <V> V config(String parameter) {
		return config(parameter, null);
	}
	
	@Override
	public Long dbSize() {
		return dbSize(null);
	}
	
	@Override
	public void flushDb() {
		this.flushDb(null);
	}
	
	@Override
	public String ping() {
		return ping(null);
	}
	
}
