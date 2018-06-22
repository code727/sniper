///*
// * Copyright 2016 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * 
// * Create Date : 2016-8-24
// */
//
//package org.sniper.nosql.redis.jedis;
//
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.sniper.commons.KeyValuePair;
//
//import redis.clients.jedis.BinaryClient.LIST_POSITION;
//import redis.clients.jedis.SortingParams;
//import redis.clients.jedis.ZParams;
//
///**
// * Jedis命令行数据访问实现类
// * @author  <a href="mailto:code727@gmail.com">杜斌</a>
// * @version 1.0
// */
//public class JedisCommandsDaoImpl extends JedisDaoSupport implements JedisCommandsDao {
//
//	@Override
//	public <K, V> void set(K key, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void set(K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void set2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void set2(String dbName, K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> Boolean setNX(K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> Boolean setNX(K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> Boolean setNX2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> Boolean setNX2(String dbName, K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> void setEx(K key, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void setEx(K key, long seconds, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void setEx2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void setEx2(String dbName, K key, long seconds, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void mSet(Map<K, V> kValues) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void mSet(Map<K, V> kValues, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void mSet(String dbName, Map<K, V> kValues) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void mSet(String dbName, Map<K, V> kValues, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void mSetNX(Map<K, V> kValues) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void mSetNX(Map<K, V> kValues, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void mSetNX(String dbName, Map<K, V> kValues) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void mSetNX(String dbName, Map<K, V> kValues, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void setRange(K key, long offset, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void setRange(K key, long offset, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void setRange2(String dbName, K key, long offset, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> void setRange2(String dbName, K key, long offset, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public <K, V> Long append(K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> Long append(K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> Long append2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> Long append2(String dbName, K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> V get(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> V get(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> V get2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> V get2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> V getRange(K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> V getRange(K key, long begin, long end, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> V getRange(String dbName, K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> V getRange(String dbName, K key, long begin, long end, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V, O> O getSet(K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V, O> O getSet(K key, V value, Class<O> oldValueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V, O> O getSet(K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V, O> O getSet(K key, V value, long expireSeconds, Class<O> oldValueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V, O> O getSet2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V, O> O getSet2(String dbName, K key, V value, Class<O> oldValueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V, O> O getSet2(String dbName, K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V, O> O getSet2(String dbName, K key, V value, long expireSeconds, Class<O> oldValueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> List<V> mGet(Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> List<V> mGet(Collection<K> keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> List<V> mGet(String dbName, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <K, V> List<V> mGet(String dbName, Collection<K> keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> mGet(K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> mGet(K[] keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> mGet(String dbName, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> mGet(String dbName, K[] keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long strLen(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long strLen(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long decr(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long decr(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K> Long decrBy(K key, long value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K> Long decrBy(String dbName, K key, long value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long incr(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long incr(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K> Long incrBy(K key, long value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K> Long incrBy(String dbName, K key, long value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKey
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Boolean hSet(K key, H hashKey, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKey
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Boolean hSet(K key, H hashKey, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKey
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Boolean hSet2(String dbName, K key, H hashKey, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKey
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Boolean hSet2(String dbName, K key, H hashKey, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKey
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Boolean hSetNX(K key, H hashKey, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKey
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Boolean hSetNX(K key, H hashKey, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKey
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Boolean hSetNX2(String dbName, K key, H hashKey, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKey
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Boolean hSetNX2(String dbName, K key, H hashKey, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeyValues 
//	 */
//	@Override
//	public <K, H, V> void hMSet(K key, Map<H, V> hashKeyValues) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeyValues
//	 * @param expireSeconds 
//	 */
//	@Override
//	public <K, H, V> void hMSet(K key, Map<H, V> hashKeyValues, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeyValues 
//	 */
//	@Override
//	public <K, H, V> void hMSet(String dbName, K key, Map<H, V> hashKeyValues) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeyValues
//	 * @param expireSeconds 
//	 */
//	@Override
//	public <K, H, V> void hMSet(String dbName, K key, Map<H, V> hashKeyValues, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKey
//	 * @return 
//	 */
//	@Override
//	public <K, H> Long hDel(K key, H hashKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKey
//	 * @return 
//	 */
//	@Override
//	public <K, H> Long hDel(String dbName, K key, H hashKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeys
//	 * @return 
//	 */
//	@Override
//	public <K, H> Long hDel(K key, H[] hashKeys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeys
//	 * @return 
//	 */
//	@Override
//	public <K, H> Long hDel(String dbName, K key, H[] hashKeys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeys
//	 * @return 
//	 */
//	@Override
//	public <K, H> Long hDel(K key, Collection<H> hashKeys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeys
//	 * @return 
//	 */
//	@Override
//	public <K, H> Long hDel(String dbName, K key, Collection<H> hashKeys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKey
//	 * @return 
//	 */
//	@Override
//	public <K, H> Boolean hExists(K key, H hashKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKey
//	 * @return 
//	 */
//	@Override
//	public <K, H> Boolean hExists(String dbName, K key, H hashKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKey
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> V hGet(K key, H hashKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKey
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> V hGet(K key, H hashKey, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKey
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> V hGet2(String dbName, K key, H hashKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKey
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> V hGet2(String dbName, K key, H hashKey, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Map<H, V> hGetAll(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Map<H, V> hGetAll(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeyType
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Map<H, V> hGetAll(K key, Class<H> hashKeyType, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Map<H, V> hGetAll2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Map<H, V> hGetAll2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeyType
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> Map<H, V> hGetAll2(String dbName, K key, Class<H> hashKeyType, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, H> Set<H> hKeys(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeyType
//	 * @return 
//	 */
//	@Override
//	public <K, H> Set<H> hKeys(K key, Class<H> hashKeyType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, H> Set<H> hKeys2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeyType
//	 * @return 
//	 */
//	@Override
//	public <K, H> Set<H> hKeys2(String dbName, K key, Class<H> hashKeyType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long hLen(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long hLen(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeys
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> List<V> hMGet(K key, Collection<H> hashKeys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> List<V> hMGet(K key, Collection<H> hashKeys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeys
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> List<V> hMGet(String dbName, K key, Collection<H> hashKeys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> List<V> hMGet(String dbName, K key, Collection<H> hashKeys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeys
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> List<V> hMGet(K key, H[] hashKeys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param hashKeys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> List<V> hMGet(K key, H[] hashKeys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeys
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> List<V> hMGet(String dbName, K key, H[] hashKeys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param hashKeys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, H, V> List<V> hMGet(String dbName, K key, H[] hashKeys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> hVals(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> hVals(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> hVals2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> hVals2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param posttion
//	 * @param value 
//	 */
//	@Override
//	public <K, V> void lSet(K key, long posttion, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param posttion
//	 * @param value
//	 * @param expireSeconds 
//	 */
//	@Override
//	public <K, V> void lSet(K key, long posttion, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param posttion
//	 * @param value 
//	 */
//	@Override
//	public <K, V> void lSet2(String dbName, K key, long posttion, V value) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param posttion
//	 * @param value
//	 * @param expireSeconds 
//	 */
//	@Override
//	public <K, V> void lSet2(String dbName, K key, long posttion, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush2(String dbName, K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param values
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(K key, V[] values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param values
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(K key, V[] values, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param values
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(String dbName, K key, V[] values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param values
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(String dbName, K key, V[] values, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param values
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(K key, Collection<V> values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param values
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(K key, Collection<V> values, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param values
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(String dbName, K key, Collection<V> values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param values
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPush(String dbName, K key, Collection<V> values, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPushX(K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPushX(K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPushX2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lPushX2(String dbName, K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param index
//	 * @return 
//	 */
//	@Override
//	public <K, V> V lIndex(K key, long index) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param index
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V lIndex(K key, long index, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param index
//	 * @return 
//	 */
//	@Override
//	public <K, V> V lIndex(String dbName, K key, long index) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param index
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V lIndex(String dbName, K key, long index, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long lLen(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long lLen(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> V lPop(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V lPop(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> V lPop2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V lPop2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> lRange(K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> lRange(K key, long begin, long end, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> lRange(String dbName, K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> lRange(String dbName, K key, long begin, long end, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> lRangeAll(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> lRangeAll(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> lRangeAll2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> lRangeAll2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param count
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lRem(K key, long count, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param count
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lRem(String dbName, K key, long count, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lRemAll(K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lRemAll(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param begin
//	 * @param end 
//	 */
//	@Override
//	public <K> void lTrim(K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param begin
//	 * @param end 
//	 */
//	@Override
//	public <K> void lTrim(String dbName, K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush2(String dbName, K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param values
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(K key, V[] values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param values
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(K key, V[] values, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param values
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(String dbName, K key, V[] values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param values
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(String dbName, K key, V[] values, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param values
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(K key, Collection<V> values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param values
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(K key, Collection<V> values, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param values
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(String dbName, K key, Collection<V> values) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param values
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPush(String dbName, K key, Collection<V> values, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPushX(K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPushX(K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPushX2(String dbName, K key, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long rPushX2(String dbName, K key, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param srcKey
//	 * @param destKey
//	 * @return 
//	 */
//	@Override
//	public <S, T, V> V rPopLPush(S srcKey, T destKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param srcKey
//	 * @param destKey
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <S, T, V> V rPopLPush(S srcKey, T destKey, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param srcKey
//	 * @param destKey
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <S, T, V> V rPopLPush(S srcKey, T destKey, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param srcKey
//	 * @param destKey
//	 * @param expireSeconds
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <S, T, V> V rPopLPush(S srcKey, T destKey, long expireSeconds, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param srcKey
//	 * @param destKey
//	 * @return 
//	 */
//	@Override
//	public <S, T, V> V rPopLPush2(String dbName, S srcKey, T destKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param srcKey
//	 * @param destKey
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <S, T, V> V rPopLPush2(String dbName, S srcKey, T destKey, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param srcKey
//	 * @param destKey
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <S, T, V> V rPopLPush2(String dbName, S srcKey, T destKey, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param srcKey
//	 * @param destKey
//	 * @param expireSeconds
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <S, T, V> V rPopLPush2(String dbName, S srcKey, T destKey, long expireSeconds, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> V rPop(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V rPop(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> V rPop2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V rPop2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param member
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(K key, V member, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd2(String dbName, K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param member
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd2(String dbName, K key, V member, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(K key, V[] members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param members
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(K key, V[] members, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(String dbName, K key, V[] members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param members
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(String dbName, K key, V[] members, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(K key, Collection<V> members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param members
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(K key, Collection<V> members, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(String dbName, K key, Collection<V> members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param members
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sAdd(String dbName, K key, Collection<V> members, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long sCard(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long sCard(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sDiff(Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sDiff(Collection<K> keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sDiff(String dbName, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sDiff(String dbName, Collection<K> keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sDiff(K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sDiff(K[] keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sDiff(String dbName, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sDiff(String dbName, K[] keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sDiffStore(T destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sDiffStore(T destKey, Collection<K> keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sDiffStore(String dbName, T destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sDiffStore(String dbName, T destKey, Collection<K> keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sDiffStore(T destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sDiffStore(T destKey, K[] keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sDiffStore(String dbName, T destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sDiffStore(String dbName, T destKey, K[] keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sInter(Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sInter(Collection<K> keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sInter(String dbName, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sInter(String dbName, Collection<K> keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sInter(K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sInter(K[] keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sInter(String dbName, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sInter(String dbName, K[] keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sInterStore(T destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sInterStore(T destKey, Collection<K> keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sInterStore(String dbName, T destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sInterStore(String dbName, T destKey, Collection<K> keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sInterStore(T destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sInterStore(T destKey, K[] keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sInterStore(String dbName, T destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sInterStore(String dbName, T destKey, K[] keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sUnion(Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sUnion(Collection<K> keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sUnion(String dbName, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sUnion(String dbName, Collection<K> keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sUnion(K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sUnion(K[] keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sUnion(String dbName, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sUnion(String dbName, K[] keys, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sUnionStore(T destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sUnionStore(T destKey, Collection<K> keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sUnionStore(String dbName, T destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sUnionStore(String dbName, T destKey, Collection<K> keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sUnionStore(T destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sUnionStore(T destKey, K[] keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sUnionStore(String dbName, T destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <T, K> Long sUnionStore(String dbName, T destKey, K[] keys, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean sIsMember(K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean sIsMember(String dbName, K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sMembers(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sMembers(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sMembers2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> sMembers2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param srcKey
//	 * @param destKey
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, T, V> Boolean sMove(K srcKey, T destKey, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param srcKey
//	 * @param destKey
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, T, V> Boolean sMove(String dbName, K srcKey, T destKey, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> V sPop(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V sPop(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> V sPop2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V sPop2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> V sRandMember(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V sRandMember(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> V sRandMember2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> V sRandMember2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sRem(K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sRem(String dbName, K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sRem(K key, V[] members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sRem(String dbName, K key, V[] members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sRem(K key, Collection<V> members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sRem(String dbName, K key, Collection<V> members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param score
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean zAdd(K key, double score, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param score
//	 * @param member
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean zAdd(K key, double score, V member, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param score
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean zAdd2(String dbName, K key, double score, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param score
//	 * @param member
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean zAdd2(String dbName, K key, double score, V member, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param scoreMembers
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param scoreMembers
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param scoreMembers
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean zAdd(String dbName, K key, Map<Double, V> scoreMembers) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param scoreMembers
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Boolean zAdd(String dbName, K key, Map<Double, V> scoreMembers, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long zCard(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long zCard(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @return 
//	 */
//	@Override
//	public <K> Long zCount(K key, double minScore, double maxScore) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @return 
//	 */
//	@Override
//	public <K> Long zCount(String dbName, K key, double minScore, double maxScore) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRange(K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRange(K key, long begin, long end, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRange(String dbName, K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRange(String dbName, K key, long begin, long end, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeAll(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeAll(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeAll2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeAll2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @param offset
//	 * @param count
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, long offset, long count) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @param offset
//	 * @param count
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, long offset, long count,
//			Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @param offset
//	 * @param count
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, long offset,
//			long count) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @param offset
//	 * @param count
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, long offset, long count,
//			Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRank(K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRank(String dbName, K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRem(K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRem(String dbName, K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRem(K key, V[] members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRem(String dbName, K key, V[] members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRem(K key, Collection<V> members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param members
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRem(String dbName, K key, Collection<V> members) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @return 
//	 */
//	@Override
//	public <K> Long zRemRangeByRank(K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @return 
//	 */
//	@Override
//	public <K> Long zRemRangeByRank(String dbName, K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @return 
//	 */
//	@Override
//	public <K> Long zRemRangeByScore(K key, double minScore, double maxScore) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @return 
//	 */
//	@Override
//	public <K> Long zRemRangeByScore(String dbName, K key, double minScore, double maxScore) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRange(K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRange(K key, long begin, long end, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRange(String dbName, K key, long begin, long end) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param begin
//	 * @param end
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRange(String dbName, K key, long begin, long end, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRangeAll(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRangeAll(K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRangeAll2(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRangeAll2(String dbName, K key, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param minScore
//	 * @param maxScore
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRevRank(K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long zRevRank(String dbName, K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Double zScore(K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Double zScore(String dbName, K key, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(K destKey, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(String dbName, K destKey, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(K destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(String dbName, K destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(K destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param srcKey
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(K destKey, K srcKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param srcKey
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(String dbName, K destKey, K srcKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(K destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(String dbName, K destKey, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(K destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param destKey
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param increment
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Double zIncrBy(K key, double increment, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param increment
//	 * @param member
//	 * @return 
//	 */
//	@Override
//	public <K, V> Double zIncrBy(String dbName, K key, double increment, V member) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @return 
//	 */
//	@Override
//	public <K> Set<K> keys() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keyType
//	 * @return 
//	 */
//	@Override
//	public <K> Set<K> keys(Class<K> keyType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @return 
//	 */
//	@Override
//	public <K> Set<K> keys(String dbName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keyType
//	 * @return 
//	 */
//	@Override
//	public <K> Set<K> keys(String dbName, Class<K> keyType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param pattern
//	 * @return 
//	 */
//	@Override
//	public <K> Set<K> keysByPattern(String pattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param pattern
//	 * @param keyType
//	 * @return 
//	 */
//	@Override
//	public <K> Set<K> keysByPattern(String pattern, Class<K> keyType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param pattern
//	 * @return 
//	 */
//	@Override
//	public <K> Set<K> keysByPattern(String dbName, String pattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param pattern
//	 * @param keyType
//	 * @return 
//	 */
//	@Override
//	public <K> Set<K> keysByPattern(String dbName, String pattern, Class<K> keyType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long del(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long del(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long del(Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long del(String dbName, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long del(K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long del(String dbName, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean exists(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean exists(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> List<KeyValuePair<K, Boolean>> exists(K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> List<KeyValuePair<K, Boolean>> exists(String dbName, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> List<KeyValuePair<K, Boolean>> exists(Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> List<KeyValuePair<K, Boolean>> exists(String dbName, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param seconds
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean expire(K key, long seconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param seconds
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean expire(String dbName, K key, long seconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param timestamp
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean expireAt(K key, long timestamp) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param timestamp
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean expireAt(String dbName, K key, long timestamp) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param date
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean expireAt(K key, Date date) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param date
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean expireAt(String dbName, K key, Date date) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param targetIndex
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean move(K key, int targetIndex) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @param targetIndex
//	 * @return 
//	 */
//	@Override
//	public <K> Boolean move(String dbName, K key, int targetIndex) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long ttl(K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param key
//	 * @return 
//	 */
//	@Override
//	public <K> Long ttl(String dbName, K key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @return 
//	 */
//	@Override
//	public <V> Set<V> values() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <V> Set<V> values(Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @return 
//	 */
//	@Override
//	public <V> Set<V> values(String dbName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <V> Set<V> values(String dbName, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param pattern
//	 * @return 
//	 */
//	@Override
//	public <V> Set<V> valuesByPattern(String pattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param pattern
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <V> Set<V> valuesByPattern(String pattern, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param pattern
//	 * @return 
//	 */
//	@Override
//	public <V> Set<V> valuesByPattern(String dbName, String pattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @param pattern
//	 * @param valueType
//	 * @return 
//	 */
//	@Override
//	public <V> Set<V> valuesByPattern(String dbName, String pattern, Class<V> valueType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @return 
//	 */
//	@Override
//	public Long dbSize() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName
//	 * @return 
//	 */
//	@Override
//	public Long dbSize(String dbName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a>  
//	 */
//	@Override
//	public void flushAll() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a>  
//	 */
//	@Override
//	public void flushDb() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbName 
//	 */
//	@Override
//	public void flushDb(String dbName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a>  
//	 */
//	@Override
//	public void shutdown() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param params
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> sort(K key, SortingParams params) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param key
//	 * @param params
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> sort(int dbIndex, K key, SortingParams params) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param params
//	 * @param targetKey
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sortCount(K key, SortingParams params, K targetKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param key
//	 * @param params
//	 * @param targetKey
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long sortCount(int dbIndex, K key, SortingParams params, K targetKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param params
//	 * @param targetKey
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> sortResult(K key, SortingParams params, K targetKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param key
//	 * @param params
//	 * @param targetKey
//	 * @return 
//	 */
//	@Override
//	public <K, V> List<V> sortResult(int dbIndex, K key, SortingParams params, K targetKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param where
//	 * @param pivot
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lInsert(K key, LIST_POSITION where, V pivot, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @param where
//	 * @param pivot
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lInsert(K key, LIST_POSITION where, V pivot, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param key
//	 * @param where
//	 * @param pivot
//	 * @param value
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lInsert(int dbIndex, K key, LIST_POSITION where, V pivot, V value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param key
//	 * @param where
//	 * @param pivot
//	 * @param value
//	 * @param expireSeconds
//	 * @return 
//	 */
//	@Override
//	public <K, V> Long lInsert(int dbIndex, K key, LIST_POSITION where, V pivot, V value, long expireSeconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param params
//	 * @param weights
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(K destKey, ZParams params, int[] weights, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param destKey
//	 * @param params
//	 * @param weights
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(int dbIndex, K destKey, ZParams params, int[] weights, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param params
//	 * @param weights
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(K destKey, ZParams params, int[] weights, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param destKey
//	 * @param params
//	 * @param weights
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zUnionStore(int dbIndex, K destKey, ZParams params, int[] weights, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param params
//	 * @param weights
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(K destKey, ZParams params, int[] weights, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param destKey
//	 * @param params
//	 * @param weights
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(int dbIndex, K destKey, ZParams params, int[] weights, K[] keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param destKey
//	 * @param params
//	 * @param weights
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(K destKey, ZParams params, int[] weights, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param dbIndex
//	 * @param destKey
//	 * @param params
//	 * @param weights
//	 * @param keys
//	 * @return 
//	 */
//	@Override
//	public <K> Long zInterStore(int dbIndex, K destKey, ZParams params, int[] weights, Collection<K> keys) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//
//}
