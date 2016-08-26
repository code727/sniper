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
 * Create Date : 2016-8-26
 */

package org.workin.nosql.redis.jedis;

import java.util.Collection;
import java.util.List;

import org.springframework.data.redis.connection.DataType;
import org.workin.nosql.redis.dao.RedisCommandsDao;

import redis.clients.jedis.SortingParams;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.BinaryClient.LIST_POSITION;

/**
 * @description Jedis命令行数据访问接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JedisCommandsDao extends RedisCommandsDao {
	
	/**
	 * @description 将当前库的键按照指定的规则进行排序
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @return
	 */
	public <K, V> List<V> sort(K key, SortingParams params);
	
	/**
	 * @description 将指定库的键按照规则进行排序
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @return
	 */
	public <K, V> List<V> sort(int dbIndex, K key, SortingParams params);
	
	/**
	 * @description 将当前库的键按照指定的规则进行排序后返回存入目标键的结果个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 
	 */
	public <K, V> Long sortCount(K key, SortingParams params, K targetKey);
	
	/**
	 * @description 将指定库的键按照规则进行排序后返回存入目标键的结果个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 存入目标键的结果个数
	 */
	public <K, V> Long sortCount(int dbIndex, K key, SortingParams params, K targetKey);
	
	/**
	 * @description 将当前库的键按照指定的规则进行排序后返回存入目标键的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 
	 */
	public <K, V> List<V> sortResult(K key, SortingParams params, K targetKey);
	
	/**
	 * @description 将指定库的键按照规则进行排序后返回存入目标键的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 存入目标键的结果个数
	 */
	public <K, V> List<V> sortResult(int dbIndex, K key, SortingParams params, K targetKey);
	
	/**
	 * @description 获取当前库的键对应的值类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> DataType type(K key);
	
	/**
	 * @description 获取指定库的键对应的值类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> DataType type(int dbIndex, K key);
	
	/**
	 * @description 在当前库中执行lInsert命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param where
	 * @param pivot 位置值
	 * @param value
	 * @return
	 */
	public <K, V> Long lInsert(K key, LIST_POSITION where, V pivot, V value);
	
	/**
	 * @description 在当前库中执行lInsert命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lInsert(K key, LIST_POSITION where, V pivot, V value, long expireSeconds);
	
	/**
	 * @description 在指定索引库中执行lInsert命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param where
	 * @param pivot 位置值
	 * @param value
	 * @return
	 */
	public <K, V> Long lInsert(int dbIndex, K key, LIST_POSITION where, V pivot, V value);
	
	/**
	 * @description 在指定索引库中执行lInsert命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lInsert(int dbIndex, K key, LIST_POSITION where, V pivot, V value, long expireSeconds);
	
	/**
	 * @description 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param params
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, ZParams params, int[] weights, K[] keys);
	
	/**
	 * @description 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param params
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(int dbIndex, K destKey, ZParams params, int[] weights, K[] keys);
	
	/**
	 * @description 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param params
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, ZParams params, int[] weights, Collection<K> keys);
	
	/**
	 * @description 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param params
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(int dbIndex, K destKey, ZParams params, int[] weights, Collection<K> keys);
	
	/**
	 * @description 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param params
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(K destKey, ZParams params, int[] weights, K[] keys);
	
	/**
	 * @description 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param params
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(int dbIndex, K destKey, ZParams params, int[] weights, K[] keys);
	
	/**
	 * @description 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param params
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(K destKey, ZParams params, int[] weights, Collection<K> keys);
	
	/**
	 * @description 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param params
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(int dbIndex, K destKey, ZParams params, int[] weights, Collection<K> keys);

}
