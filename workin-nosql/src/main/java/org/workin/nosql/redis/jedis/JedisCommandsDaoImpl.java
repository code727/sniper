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
 * Create Date : 2016-8-24
 */

package org.workin.nosql.redis.jedis;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.ZParams;

/**
 * @description Jedis命令行数据访问实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JedisCommandsDaoImpl extends JedisDaoSupport implements JedisCommandsDao {

	@Override
	public <K, V> void set(K key, V value) {
		
	}

	@Override
	public <K, V> void set(K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void set(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void set(int dbIndex, K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> Boolean setNX(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean setNX(K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean setNX(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean setNX(int dbIndex, K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> void setEx(K key, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void setEx(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void setEx(K key, long seconds, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void setEx(int dbIndex, K key, long seconds, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void mSet(Map<K, V> kValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void mSet(Map<K, V> kValues, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void mSet(int dbIndex, Map<K, V> kValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void mSet(int dbIndex, Map<K, V> kValues, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void mSetNX(Map<K, V> kValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void mSetNX(Map<K, V> kValues, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void mSetNX(int dbIndex, Map<K, V> kValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void mSetNX(int dbIndex, Map<K, V> kValues, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void setRange(K key, long offset, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void setRange(K key, long offset, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public <K, V> void setRange(int dbIndex, K key, long offset, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void setRange(int dbIndex, K key, long offset, V value,
			long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> Long append(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long append(K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long append(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long append(int dbIndex, K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V get(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V getRange(K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V getRange(int dbIndex, K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V getSet(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V getSet(K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V getSet(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V getSet(int dbIndex, K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> mGet(K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> mGet(int dbIndex, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> mGet(Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> mGet(int dbIndex, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long strLen(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long strLen(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long decr(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long decr(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long decrBy(K key, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long decrBy(int dbIndex, K key, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long incr(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long incr(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long incrBy(K key, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long incrBy(int dbIndex, K key, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Boolean hSet(K key, F field, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Boolean hSet(K key, F field, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Boolean hSet(int dbIndex, K key, F field, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Boolean hSet(int dbIndex, K key, F field, V value,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Boolean hSetNX(K key, F field, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Boolean hSetNX(K key, F field, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Boolean hSetNX(int dbIndex, K key, F field, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Boolean hSetNX(int dbIndex, K key, F field, V value,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> void hMSet(K key, Map<F, V> fValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, F, V> void hMSet(K key, Map<F, V> fValues, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, F, V> void hMSet(int dbIndex, K key, Map<F, V> fValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, F, V> void hMSet(int dbIndex, K key, Map<F, V> fValues,
			long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, F> Long hDel(K key, F filed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Long hDel(int dbIndex, K key, F filed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Long hDel(K key, F[] fileds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Long hDel(int dbIndex, K key, F[] fileds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Long hDel(K key, Collection<F> fileds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Long hDel(int dbIndex, K key, Collection<F> fileds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Boolean hExists(K key, F filed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Boolean hExists(int dbIndex, K key, F filed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> V hGet(K key, F filed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> V hGet(int dbIndex, K key, F filed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Map<F, V> hGetAll(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> Map<F, V> hGetAll(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Set<F> hKeys(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F> Set<F> hKeys(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long hLen(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long hLen(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> List<V> hMGet(K key, F[] fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> List<V> hMGet(int dbIndex, K key, F[] fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> List<V> hMGet(K key, Collection<F> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, F, V> List<V> hMGet(int dbIndex, K key, Collection<F> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> hVals(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> hVals(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lInsert(K key, LIST_POSITION where, V pivot, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lInsert(K key, LIST_POSITION where, V pivot, V value,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lInsert(int dbIndex, K key, LIST_POSITION where, V pivot,
			V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lInsert(int dbIndex, K key, LIST_POSITION where, V pivot,
			V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> void lSet(K key, long posttion, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void lSet(K key, long posttion, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void lSet(int dbIndex, K key, long posttion, V value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> void lSet(int dbIndex, K key, long posttion, V value,
			long expireSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> Long lPush(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(K key, V[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(K key, V[] values, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, V[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, V[] values, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(K key, Collection<V> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(K key, Collection<V> values, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, Collection<V> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPush(int dbIndex, K key, Collection<V> values,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPushX(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPushX(K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPushX(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lPushX(int dbIndex, K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V lIndex(K key, long index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V lIndex(int dbIndex, K key, long index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long lLen(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long lLen(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V lPop(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V lPop(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> lRange(K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> lRange(int dbIndex, K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> lRangeAll(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> lRangeAll(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lRem(K key, long count, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lRem(int dbIndex, K key, long count, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lRemAll(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long lRemAll(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> void lTrim(K key, long begin, long end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K> void lTrim(int dbIndex, K key, long begin, long end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K, V> Long rPush(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(K key, V[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(K key, V[] values, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, V[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, V[] values, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(K key, Collection<V> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(K key, Collection<V> values, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, Collection<V> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPush(int dbIndex, K key, Collection<V> values,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPushX(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPushX(K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPushX(int dbIndex, K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long rPushX(int dbIndex, K key, V value, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V rPopLPush(K srcKey, K destKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V rPopLPush(K srcKey, K destKey, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V rPopLPush(int dbIndex, K srcKey, K destKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V rPopLPush(int dbIndex, K srcKey, K destKey,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V rPop(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V rPop(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(K key, V member, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, V member, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(K key, V[] members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(K key, V[] members, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, V[] members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, V[] members, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(K key, Collection<V> members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(K key, Collection<V> members, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, Collection<V> members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sAdd(int dbIndex, K key, Collection<V> members,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sCard(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sCard(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sDiff(K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sDiff(int dbIndex, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sDiff(Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sDiff(int dbIndex, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sDiffStore(K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sDiffStore(K destKey, K[] keys, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sDiffStore(int dbIndex, K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sDiffStore(int dbIndex, K destKey, K[] keys,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sDiffStore(K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sDiffStore(K destKey, Collection<K> keys, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sDiffStore(int dbIndex, K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sDiffStore(int dbIndex, K destKey, Collection<K> keys,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sInter(K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sInter(int dbIndex, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sInter(Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sInter(int dbIndex, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sInterStore(K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sInterStore(K destKey, K[] keys, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sInterStore(int dbIndex, K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sInterStore(int dbIndex, K destKey, K[] keys,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sInterStore(K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sInterStore(K destKey, Collection<K> keys,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sInterStore(int dbIndex, K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sInterStore(int dbIndex, K destKey, Collection<K> keys,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sUnion(K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sUnion(int dbIndex, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sUnion(Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sUnion(int dbIndex, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sUnionStore(K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sUnionStore(K destKey, K[] keys, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sUnionStore(int dbIndex, K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sUnionStore(int dbIndex, K destKey, K[] keys,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sUnionStore(K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sUnionStore(K destKey, Collection<K> keys,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sUnionStore(int dbIndex, K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long sUnionStore(int dbIndex, K destKey, Collection<K> keys,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean sIsMember(K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean sIsMember(int dbIndex, K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sMembers(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> sMembers(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean sMove(K srcKey, K destKey, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean sMove(int dbIndex, K srcKey, K destKey, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V sPop(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V sPop(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V sRandMember(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> V sRandMember(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sRem(K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sRem(int dbIndex, K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sRem(K key, V[] members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sRem(int dbIndex, K key, V[] members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sRem(K key, Collection<V> members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sRem(int dbIndex, K key, Collection<V> members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean zAdd(K key, double score, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean zAdd(K key, double score, V member, long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean zAdd(int dbIndex, K key, double score, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean zAdd(int dbIndex, K key, double score, V member,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean zAdd(int dbIndex, K key, Map<Double, V> scoreMembers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Boolean zAdd(int dbIndex, K key, Map<Double, V> scoreMembers,
			long expireSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zCard(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zCard(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zCount(K key, double minScore, double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zCount(int dbIndex, K key, double minScore, double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRange(K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRange(int dbIndex, K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRangeAll(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRangeAll(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRangeByScore(int dbIndex, K key, double minScore,
			double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore,
			long offset, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRangeByScore(int dbIndex, K key, double minScore,
			double maxScore, long offset, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore,
			double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(int dbIndex, K key,
			double minScore, double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore,
			double maxScore, long offset, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<Tuple> zRangeByScoreWithScores(int dbIndex, K key,
			double minScore, double maxScore, long offset, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRank(K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRank(int dbIndex, K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRem(K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRem(int dbIndex, K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRem(K key, V[] members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRem(int dbIndex, K key, V[] members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRem(K key, Collection<V> members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRem(int dbIndex, K key, Collection<V> members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zRemRangeByRank(K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zRemRangeByRank(int dbIndex, K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zRemRangeByScore(K key, double minScore, double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zRemRangeByScore(int dbIndex, K key, double minScore,
			double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRevRange(K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRevRange(int dbIndex, K key, long begin, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRevRangeAll(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRevRangeAll(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore,
			double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Set<V> zRevRangeByScore(int dbIndex, K key, double minScore,
			double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore,
			double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(int dbIndex, K key,
			double minScore, double maxScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore,
			double maxScore, long offset, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<Tuple> zRevRangeByScoreWithScores(int dbIndex, K key,
			double minScore, double maxScore, long offset, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRevRank(K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long zRevRank(int dbIndex, K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Double zScore(K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Double zScore(int dbIndex, K key, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(K destKey, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(int dbIndex, K destKey, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(int dbIndex, K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(int dbIndex, K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(K destKey, ZParams params, int[] weights, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(int dbIndex, K destKey, ZParams params,
			int[] weights, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(K destKey, ZParams params, int[] weights,
			Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zUnionStore(int dbIndex, K destKey, ZParams params,
			int[] weights, Collection<K> keys) {
		return null;
	}

	@Override
	public <K> Long zInterStore(K destKey, K srcKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(int dbIndex, K destKey, K srcKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(int dbIndex, K destKey, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(int dbIndex, K destKey, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(K destKey, ZParams params, int[] weights,
			K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(int dbIndex, K destKey, ZParams params,
			int[] weights, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(K destKey, ZParams params, int[] weights,
			Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long zInterStore(int dbIndex, K destKey, ZParams params,
			int[] weights, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Double zIncrBy(K key, double increment, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Double zIncrBy(int dbIndex, K key, double increment, V member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<K> keys(int dbIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<K> keys(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Set<K> keys(int dbIndex, String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long del(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long del(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long del(K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long del(int dbIndex, K[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long del(Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long del(int dbIndex, Collection<K> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean exists(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean exists(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean expire(K key, long seconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean expire(int dbIndex, K key, long seconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean expireAt(K key, long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean expireAt(int dbIndex, K key, long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean expireAt(K key, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean expireAt(int dbIndex, K key, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean move(K key, int targetIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Boolean move(int dbIndex, K key, int targetIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long ttl(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> Long ttl(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> sort(K key, SortingParams params) {
		return null;
	}

	@Override
	public <K, V> List<V> sort(int dbIndex, K key, SortingParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sortCount(K key, SortingParams params, K targetKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Long sortCount(int dbIndex, K key, SortingParams params,
			K targetKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> sortResult(K key, SortingParams params, K targetKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> List<V> sortResult(int dbIndex, K key, SortingParams params,
			K targetKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> DataType type(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> DataType type(int dbIndex, K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Set<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Set<V> values(int dbIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Set<V> values(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Set<V> values(int dbIndex, String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long dbSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long dbSize(int dbIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flushAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flushDb() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flushDb(int dbIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
