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
 * Create Date : 2015-3-28
 */

package org.sniper.nosql.redis.command;

import java.util.Collection;
import java.util.List;

import org.sniper.nosql.redis.enums.ListPosition;


/**
 * Redis列表命令接口
 * @author  Daniele
 * @version 1.0
 */
public interface RedisListCommands {
	
	/**
	 * 在当前库中执行lInsert命令
	 * @author Daniele 
	 * @param key
	 * @param where
	 * @param pivot 位置值
	 * @param value
	 * @return
	 */
	public <K, V> Long lInsert(K key, ListPosition where, V pivot, V value);
	
	/**
	 * 在当前库中执行lInsert命令，并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lInsert(K key, ListPosition where, V pivot, V value, long expireSeconds);
	
	/**
	 * 在指定索引库中执行lInsert命令
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param where
	 * @param pivot 位置值
	 * @param value
	 * @return
	 */
	public <K, V> Long lInsertIn(String dbName, K key, ListPosition where, V pivot, V value);
	
	/**
	 * 在指定索引库中执行lInsert命令，并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lInsert(String dbName, K key, ListPosition where, V pivot, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行lSet命令
	 * @author Daniele 
	 * @param key
	 * @param posttion
	 * @param value
	 */
	public <K, V> void lSet(K key, long posttion, V value);
	
	/**
	 * 在当前库中执行lSet命令，并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param posttion
	 * @param value
	 * @param expireSeconds
	 */
	public <K, V> void lSet(K key, long posttion, V value, long expireSeconds);
	
	/**
	 * 在指定库中执行lSet命令
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param posttion
	 * @param value
	 */
	public <K, V> void lSetIn(String dbName, K key, long posttion, V value);
	
	/**
	 * 在指定库中执行lSet命令，并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param posttion
	 * @param value
	 * @param expireSeconds
	 */
	public <K, V> void lSet(String dbName, K key, long posttion, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行lPush命令，入栈到键值列表的顶部
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lPush(K key, V value);
	
	/**
	 * 在当前库中执行lPush命令，入栈到键值列表的顶部并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lPush(K key, V value, long expireSeconds);
	
	/**
	 * 在指定库中执行lPush命令，入栈到键值列表的顶部
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lPushIn(String dbName, K key, V value);
	
	/**
	 * 在指定库中执行lPushX命令，入栈到键值列表的顶部并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lPush(String dbName, K key, V value, long expireSeconds);
		
	/**
	 * 在当前库中批量执行lPush命令，入栈到键值列表的顶部
	 * @author Daniele 
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long lPush(K key, V[] values);
	
	/**
	 * 在当前库中批量执行lPush命令，入栈到键值列表的顶部并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param values
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lPush(K key, V[] values, long expireSeconds);
	
	/**
	 * 在指定库中批量执行lPush命令，入栈到键值列表的顶部
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long lPush(String dbName, K key, V[] values);
	
	/**
	 * 在指定库中批量执行lPush命令，入栈到键值列表的顶部并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param values
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lPush(String dbName, K key, V[] values, long expireSeconds);
	
	/**
	 * 在当前库中批量执行lPush命令，入栈到键值列表的顶部
	 * @author Daniele 
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long lPush(K key, Collection<V> values);
	
	/**
	 * 在指定库中批量执行lPush命令，入栈到键值列表的顶部并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param values
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lPush(K key, Collection<V> values, long expireSeconds);
	
	/**
	 * 在指定库中批量执行lPush命令，入栈到键值列表的顶部
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long lPush(String dbName, K key, Collection<V> values);
	
	/**
	 * 在指定库中批量执行lPush命令，入栈到键值列表的顶部并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param values
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lPush(String dbName, K key, Collection<V> values, long expireSeconds);
	
	/**
	 * 在当前库中执行lPushX命令，当键存在时入栈到键值列表的顶部
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lPushX(K key, V value);
	
	/**
	 * 在当前库中执行lPushX命令，当键存在时入栈到键值列表的顶部并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lPushX(K key, V value, long expireSeconds);
	
	/**
	 * 在指定库中执行lPushX命令，当键存在时入栈到键值列表的顶部
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lPushXIn(String dbName, K key, V value);
	
	/**
	 * 在当前库中执行lPushX命令，当键存在时入栈到键值列表的顶部并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lPushX(String dbName, K key, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行lIndex命令，获取指定下标位对应的元素值
	 * @author Daniele 
	 * @param key
	 * @param index
	 * @return
	 */
	public <K, V> V lIndex(K key, long index);
	
	/**
	 * 在当前库中执行lIndex命令，获取指定下标位对应类型的元素值
	 * @author Daniele 
	 * @param key
	 * @param index
	 * @param valueType
	 * @return
	 */
	public <K, V> V lIndex(K key, long index, Class<V> valueType);
	
	/**
	 * 在指定库中执行lIndex命令，获取下标位对应的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param index
	 * @return
	 */
	public <K, V> V lIndex(String dbName, K key, long index);
	
	/**
	 * 在指定库中执行lIndex命令，获取下标位对应类型的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param index
	 * @param valueType
	 * @return
	 */
	public <K, V> V lIndex(String dbName, K key, long index, Class<V> valueType);
	
	/**
	 * 在当前库中执行lLen命令，获取指定键的元素合数
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K> Long lLen(K key);
	
	/**
	 * 在指定库中执行lLen命令，获取指定键的元素合数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long lLen(String dbName, K key);
	
	/**
	 * 在当前库中执行lPop命令，出栈指定键顶部的一个值
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K, V> V lPop(K key);
	
	/**
	 * 在当前库中执行lPop命令，出栈指定键顶部的一个值
	 * @author Daniele 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V lPop(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行lPop命令，出栈指定键顶部的一个值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> V lPopIn(String dbName, K key);
	
	/**
	 * 在指定库中执行lPop命令，出栈指定键顶部的一个值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V lPop(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行lRange命令，获取指定键列表下标在区间范围内的所有元素
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> List<V> lRange(K key, long begin, long end);
	
	/**
	 * 在当前库中执行lRange命令，获取指定键列表下标在区间范围内的所有元素
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> lRange(K key, long begin, long end, Class<V> valueType);
	
	/**
	 * 在指定库中执行lRange命令，获取指定键列表下标在区间范围内的所有元素
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> List<V> lRange(String dbName, K key, long begin, long end);
	
	/**
	 * 在指定库中执行lRange命令，获取指定键列表下标在区间范围内的所有元素
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> lRange(String dbName, K key, long begin, long end, Class<V> valueType);
	
	/**
	 * 在当前库中执行lRange命令，获取指定键列表中的所有元素
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> List<V> lRangeAll(K key);
	
	/**
	 * 在当前库中执行lRange命令，获取指定键列表中的所有元素
	 * @author Daniele 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> lRangeAll(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行lRange命令，获取指定键列表中的所有元素
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> List<V> lRangeAllIn(String dbName, K key);
	
	/**
	 * 在指定库中执行lRange命令，获取指定键列表中的所有元素
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> lRangeAll(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行lRem命令，删除键列表中count个与指定值相等的元素
	 * @author Daniele 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public <K, V> Long lRem(K key, long count, V value);
	
	/**
	 * 在指定库中执行lRem命令，删除键列表中count个与指定值相等的元素
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public <K, V> Long lRem(String dbName, K key, long count, V value);
	
	/**
	 * 在当前库中执行lRem命令，删除键列表中所有与指定相等的元素
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lRemAll(K key, V value);
	
	/**
	 * 在指定库中执行lRem命令，删除键列表中所有与指定相等的元素
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lRemAll(String dbName, K key, V value);
	
	/**
	 * 在当前库中执行lTrim命令，删除键列表下标在区间范围外的所有子元素
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 */
	public <K> void lTrim(K key, long begin, long end);
	
	/**
	 * 在指定库中执行lTrim命令，删除键列表下标在区间范围外的所有子元素
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 */
	public <K> void lTrim(String dbName, K key, long begin, long end);
	
	/**
	 * 在当前库中执行rPush命令，入栈到键值列表的末尾
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long rPush(K key, V value);
	
	/**
	 * 在当前库中执行rPush命令，入栈到键值列表的末尾并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long rPush(K key, V value, long expireSeconds);
	
	/**
	 * 在指定库中执行lPush命令，入栈到键值列表的末尾
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long rPushIn(String dbName, K key, V value);
	
	/**
	 * 在当前库中执行rPush命令，入栈到键值列表的末尾并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long rPush(String dbName, K key, V value, long expireSeconds);
	
	/**
	 * 在当前库中批量执行rPush命令，入栈到键值列表的末尾
	 * @author Daniele 
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long rPush(K key, V[] values);
	
	/**
	 * 在当前库中批量执行rPush命令，入栈到键值列表的末尾并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param values
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long rPush(K key, V[] values, long expireSeconds);
	
	/**
	 * 在指定库中批量执行rPush命令，入栈到键值列表的末尾
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long rPush(String dbName, K key, V[] values);
	
	/**
	 * 在当前库中批量执行rPush命令，入栈到键值列表的末尾并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param values
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long rPush(String dbName, K key, V[] values, long expireSeconds);
	
	/**
	 * 在当前库中批量执行rPush命令，入栈到键值列表的末尾
	 * @author Daniele 
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long rPush(K key, Collection<V> values);
	
	/**
	 * 在当前库中批量执行rPush命令，入栈到键值列表的末尾并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param values
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long rPush(K key, Collection<V> values, long expireSeconds);
	
	/**
	 * 在指定库中批量执行rPush命令，入栈到键值列表的末尾
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long rPush(String dbName, K key, Collection<V> values);
	
	/**
	 * 在指定库中批量执行rPush命令，入栈到键值列表的末尾并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param values
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long rPush(String dbName, K key, Collection<V> values, long expireSeconds);
	
	/**
	 * 在当前库中执行rPushX命令，入栈到键值列表的末尾
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long rPushX(K key, V value);
	
	/**
	 * 在当前库中执行rPushX命令，入栈到键值列表的末尾并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long rPushX(K key, V value, long expireSeconds);
	
	/**
	 * 在指定库中执行rPushX命令，入栈到键值列表的末尾
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long rPushXIn(String dbName, K key, V value);
	
	/**
	 * 在指定库中执行rPushX命令，入栈到键值列表的末尾并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long rPushX(String dbName, K key, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行rPopLPush命令，从源键值列表末尾出栈一个值后又重新入栈到目标键值列表的顶部
	 * @author Daniele 
	 * @param srcKey
	 * @param destKey
	 * @return
	 */
	public <S, T, V> V rPopLPush(S srcKey, T destKey);
	
	/**
	 * 在当前库中执行rPopLPush命令，从源键值列表末尾出栈一个值后又重新入栈到目标键值列表的顶部
	 * @author Daniele 
	 * @param srcKey
	 * @param destKey
	 * @param valueType
	 * @return
	 */
	public <S, T, V> V rPopLPush(S srcKey, T destKey, Class<V> valueType);
	
	/**
	 * 在当前库中执行rPopLPush命令，从源键值列表末尾出栈一个值后又重新入栈到目标键值列表的顶部，并设置过期秒数
	 * @author Daniele 
	 * @param srcKey
	 * @param destKey
	 * @param expireSeconds
	 * @return
	 */
	public <S, T, V> V rPopLPush(S srcKey, T destKey, long expireSeconds);
	
	/**
	 * 在当前库中执行rPopLPush命令，从源键值列表末尾出栈一个值后又重新入栈到目标键值列表的顶部，并设置过期秒数
	 * @author Daniele 
	 * @param srcKey
	 * @param destKey
	 * @param expireSeconds
	 * @param valueType
	 * @return
	 */
	public <S, T, V> V rPopLPush(S srcKey, T destKey, long expireSeconds, Class<V> valueType);
	
	/**
	 * 在指定库中执行rPopLPush命令，从源键值列表末尾出栈一个值后又重新入栈到目标键值列表的顶部
	 * @author Daniele 
	 * @param dbName
	 * @param srcKey
	 * @param destKey
	 * @return
	 */
	public <S, T, V> V rPopLPushIn(String dbName, S srcKey, T destKey);
	
	/**
	 * 指定库中执行rPopLPush命令，从源键值列表末尾出栈一个值后又重新入栈到目标键值列表的顶部
	 * @author Daniele 
	 * @param dbName
	 * @param srcKey
	 * @param destKey
	 * @param valueType
	 * @return
	 */
	public <S, T, V> V rPopLPushIn(String dbName, S srcKey, T destKey, Class<V> valueType);
	
	/**
	 * 在指定库中执行rPopLPush命令，从源键值列表末尾出栈一个值后又重新入栈到目标键值列表的顶部，并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param srcKey
	 * @param destKey
	 * @param expireSeconds
	 * @return
	 */
	public <S, T, V> V rPopLPush(String dbName, S srcKey, T destKey, long expireSeconds);
	
	/**
	 * 在指定库中执行rPopLPush命令，从源键值列表末尾出栈一个值后又重新入栈到目标键值列表的顶部，并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param srcKey
	 * @param destKey
	 * @param expireSeconds
	 * @param valueType
	 * @return
	 */
	public <S, T, V> V rPopLPush(String dbName, S srcKey, T destKey, long expireSeconds, Class<V> valueType);
	
	/**
	 * 在当前库中执行rPop命令，出栈指定键尾部的一个值
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K, V> V rPop(K key);
	
	/**
	 * 在当前库中执行rPop命令，出栈指定键尾部的一个值
	 * @author Daniele 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V rPop(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行rPop命令，出栈指定键尾部的一个值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> V rPopIn(String dbName, K key);
	
	/**
	 * 在指定库中执行rPop命令，出栈指定键尾部的一个值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V rPop(String dbName, K key, Class<V> valueType);
}
