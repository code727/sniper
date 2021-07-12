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
import java.util.Map;
import java.util.Set;

import org.sniper.nosql.redis.model.xscan.MappedScanResult;
import org.sniper.nosql.redis.model.zset.ZSetTuple;
import org.sniper.nosql.redis.option.Limit;
import org.sniper.nosql.redis.option.ScanOption;
import org.sniper.nosql.redis.option.ZStoreOptional;

/**
 * Redis有序集合命令接口
 * @author  Daniele
 * @version 1.0
 */
public interface RedisSortedSetCommands {
	
	/**
	 * 在当前库中执行zAdd命令
	 * @author Daniele 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, double score, V member);
	
	/**
	 * 在当前库中执行zAdd命令，并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param score
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, double score, V member, long expireSeconds);
	
	/**
	 * 在指定库中执行zAdd命令
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public <K, V> Boolean zAddIn(String dbName, K key, double score, V member);
	
	/**
	 * 在指定库中执行zAdd命令，并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param score
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Boolean zAdd(String dbName, K key, double score, V member, long expireSeconds);
	
	/**
	 * 在当前库中执行批量zAdd命令
	 * @author Daniele 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public <K, V> Long zAdd(K key, Map<V, Double> scoreMembers);
	
	/**
	 * 在当前库中执行批量zAdd命令，并设置过期秒数
	 * @author Daniele 
	 * @param key
	 * @param scoreMembers
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long zAdd(K key, Map<V, Double> scoreMembers, long expireSeconds);
	
	/**
	 * 在指定库中执行批量zAdd命令
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public <K, V> Long zAdd(String dbName, K key, Map<V, Double> scoreMembers);
	
	/**
	 * 在指定库中执行批量zAdd命令，并设置过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param scoreMembers
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long zAdd(String dbName, K key, Map<V, Double> scoreMembers, long expireSeconds);
		
	/**
	 * 在当前库中执行zCard命令，获取有序集合键对应的元素个数
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K> Long zCard(K key);
	
	/**
	 * 在指定库中执行sCard命令，获取有序集合键对应的元素个数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long zCard(String dbName, K key);
	
	/**
	 * 在当前库中执行zCount命令，获取有序集合在 (minScore, maxScore]区间范围内的成员数量
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zCount(K key, double minScore, double maxScore);
	
	/**
	 * 在指定库中执行zCount命令，获取有序集合在 (minScore, maxScore]区间范围内的成员数量。
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zCount(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRange命令，以score值升序的方式获取集合下标区间范围内的所有元素值
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRange(K key, long begin, long end);
	
	/**
	 * 在当前库中执行zRange命令，以score值升序的方式获取集合下标区间范围内的所有元素值
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRange(K key, long begin, long end, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRange命令，以score值升序的方式获取集合下标区间范围内的所有元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRange(String dbName, K key, long begin, long end);
	
	/**
	 * 在指定库中执行zRange命令，以score值升序的方式获取集合下标区间范围内的所有元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRange(String dbName, K key, long begin, long end, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRange命令，以score值升序的方式获取集合内的所有元素值
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRangeAll(K key);
	
	/**
	 * 在当前库中执行zRange命令，以score值升序的方式获取集合内的所有元素值
	 * @author Daniele 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRangeAll(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRange命令，以score值升序的方式获取集合内的所有元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRangeAllIn(String dbName, K key);
	
	/**
	 * 在指定库中执行zRange命令，以score值升序的方式获取集合内的所有元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRangeAll(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式获取有序集合在[minScore, maxScore]区间范围内所有的元素值
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式获取有序集合在[minScore, maxScore]区间范围内所有指定类型的元素值
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式限制获取有序集合在[minScore, maxScore]区间范围内所有的元素值
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, Limit limit);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定类型的元素值
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, Limit limit, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score升序方式获取有序集合在[minScore, maxScore]区间范围内所有的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score升序方式获取有序集合在[minScore, maxScore]区间范围内所有指定类型的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score升序方式限制获取有序集合在[minScore, maxScore]区间范围内所有的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, Limit limit);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score升序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定类型的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, Limit limit, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式获取有序集合在[minScore, maxScore]区间范围内所有的元组集
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(K key, double minScore, double maxScore, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式限制获取有序集合在[minScore, maxScore]区间范围内所有的元组集
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param offset
	 * @param count
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(K key, double minScore, double maxScore, Limit limit);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param offset
	 * @param count
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(K key, double minScore, double maxScore, Limit limit, Class<V> valueType);
			
	/**
	 * 在当前库中执行zRangeByScore命令，按score升序方式获取有序集合在[minScore, maxScore]区间范围内所有的元组集
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score升序方式获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score升序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Limit limit);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score升序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Limit limit, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRevRange命令，以score值降序的方式获取集合下标区间范围内的所有元素值
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRevRange(K key, long begin, long end);
	
	/**
	 * 在当前库中执行zRevRange命令，以score值降序的方式获取集合下标区间范围内的所有元素值
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRevRange(K key, long begin, long end, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRevRange命令，以score值降序的方式获取集合下标区间范围内的所有元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRevRange(String dbName, K key, long begin, long end);
	
	/**
	 * 在指定库中执行zRevRange命令，以score值降序的方式获取集合下标区间范围内的所有元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRevRange(String dbName, K key, long begin, long end, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRevRange命令，以score值降序的方式获取集合内所有的元素值
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K, V> Set<V> zRevRangeAll(K key);
	
	/**
	 * 在当前库中执行zRevRange命令，以score值降序的方式获取集合内所有的元素值
	 * @author Daniele 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRevRangeAll(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRevRange命令，按score值降序的方式获取集合内所有的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> Set<V> zRevRangeAllIn(String dbName, K key);
	
	/**
	 * 在指定库中执行zRevRange命令，按score值降序的方式获取集合内所有指定类型的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRevRangeAll(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRevRangeByScore命令，按score降序方式获取有序集合在[minScore, maxScore]区间范围内所有的元素值
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRevRangeByScore命令，按score降序的方式获取有序集合在[minScore, maxScore]区间范围内所有指定类型的元素值
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRevRangeByScore命令，按score降序方式限制获取有序集合在[minScore, maxScore]区间范围内所有的元素值
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore, Limit limit);
	
	/**
	 * 在当前库中执行zRevRangeByScore命令，按score降序的方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定类型的元素值
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore, Limit limit, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRevRangeByScore命令，按score降序方式获取有序集合在[minScore, maxScore]区间范围内所有的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在指定库中执行zRevRangeByScore命令，按score降序方式获取有序集合在[minScore, maxScore]区间范围内所有指定类型的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRevRangeByScore命令，按score降序方式限制获取有序集合在[minScore, maxScore]区间范围内所有的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore, Limit limit);
	
	/**
	 * 在指定库中执行zRevRangeByScore命令，按score降序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定类型的元素值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore, Limit limit, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score降序方式获取有序集合在[minScore, maxScore]区间范围内所有的元组集
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score降序方式获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(K key, double minScore, double maxScore, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score降序方式限制获取有序集合在[minScore, maxScore]区间范围内所有的元组集
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param offset
	 * @param count
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(K key, double minScore, double maxScore, Limit limit);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score降序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param offset
	 * @param count
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(K key, double minScore, double maxScore, Limit limit, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRangeByScore命令，按score降序方式获取有序集合在[minScore, maxScore]区间范围内所有的元组集
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score降序方式获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Class<V> valueType);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score降序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Limit limit);
	
	/**
	 * 在指定库中执行zRangeByScore命令，按score降序方式限制获取有序集合在[minScore, maxScore]区间范围内所有指定值类型的元组集
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param limit
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<ZSetTuple<V>> zRevRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, Limit limit, Class<V> valueType);
	
	/**
	 * 在当前库中执行zRank命令，获取有序键集中指定成员按score升序排列后的下标值
	 * @author Daniele 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRank(K key, V member);
	
	/**
	 * 在指定库中执行zRank命令，获取有序键集中指定成员按score升序排列后的下标值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRank(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行zRevRank命令，获取有序键集中指定成员按score降序排列后的下标值
	 * @author Daniele 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRevRank(K key, V member);
	
	/**
	 * 在指定库中执行zRevRank命令，获取有序键集中指定成员按score降序排列后的下标值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRevRank(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行zRem命令，删除指定键集中的成员
	 * @author Daniele 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRem(K key, V member);
	
	/**
	 * 在指定库中执行zRem命令，删除指定键集中的成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRem(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行zRem命令，删除指定键集中的多个成员
	 * @author Daniele 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long zRem(K key, V[] members);
	
	/**
	 * 在指定库中执行zRem命令，删除指定键集中的多个成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long zRem(String dbName, K key, V[] members);
	
	/**
	 * 在当前库中执行zRem命令，删除指定键集中的多个成员
	 * @author Daniele 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long zRem(K key, Collection<V> members);
	
	/**
	 * 在指定库中执行zRem命令，删除指定键集中的多个成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long zRem(String dbName, K key, Collection<V> members);
	
	/**
	 * 在当前库中执行zRem命令，删除指定键集中的所有成员
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K, V> Long zRemAll(K key);
	
	/**
	 * 在指定库中执行zRem命令，删除指定键集中的所有成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> Long zRemAll(String dbName, K key);
	
	/**
	 * 在当前库中执行zRemRangeByRank命令，删除指定下标范围内所有升序排列后的成员
	 * @author Daniele 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K> Long zRemRangeByRank(K key, long begin, long end);
	
	/**
	 * 在指定库中执行zRemRangeByRank命令，删除指定下标范围内所有升序排列后的成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K> Long zRemRangeByRank(String dbName, K key, long begin, long end);
	
	/**
	 * 在当前库中执行zRemRangeByScore命令，删除指定排名范围内的所有成员
	 * @author Daniele 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zRemRangeByScore(K key, double minScore, double maxScore);
	
	/**
	 * 在指定库中执行zRemRangeByRank命令，删除指定排名范围内的所有成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zRemRangeByScore(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zScore命令，获取有序键集中指定成员的score值
	 * @author Daniele 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Double zScore(K key, V member);
	
	/**
	 * 在指定库中执行zScore命令，获取有序键集中指定成员的score值
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Double zScore(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行zUnionStore命令，将单个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param key
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, K key);
	
	/**
	 * 在当前库中执行zUnionStore命令，将单个键的并集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param key
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, K key, long expireSeconds);
	
	/**
	 * 在指定库中执行zUnionStore命令，将单个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param key
	 * @return
	 */
	public <K> Long zUnionStoreIn(String dbName, K destKey, K key);
	
	/**
	 * 在当指定库中执行zUnionStore命令，将单个键的并集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param key
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, K key, long expireSeconds);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, K[] keys);
	
	/**
	 * 当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, K[] keys);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Collection<K> keys);
	
	/**
	 * 当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param option
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, K[] keys, ZStoreOptional option);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param option
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, K[] keys, ZStoreOptional option, long expireSeconds);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param option
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, K[] keys, ZStoreOptional option);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param option
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, K[] keys, ZStoreOptional option, long expireSeconds);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param option
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Collection<K> keys, ZStoreOptional option);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param option
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Collection<K> keys, ZStoreOptional option, long expireSeconds);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param option
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys, ZStoreOptional option);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param option
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys, ZStoreOptional option, long expireSeconds);
	
	/**
	 * 在当前库中执行zInterStore命令，将单个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param srcKey
	 * @return
	 */
	public <K> Long zInterStore(K destKey, K srcKey);
	
	/**
	 * 在指定库中执行zInterStore命令，将单个键的交集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param srcKey
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(K destKey, K srcKey, long expireSeconds);
	
	/**
	 * 在指定库中执行zInterStore命令，将单个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param srcKey
	 * @return
	 */
	public <K> Long zInterStoreIn(String dbName, K destKey, K srcKey);
	
	/**
	 * 在指定库中执行zInterStore命令，将单个键的交集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param srcKey
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, K srcKey, long expireSeconds);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(K destKey, K[] keys);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, K[] keys);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param option
	 * @return
	 */
	public <K> Long zInterStore(K destKey, K[] keys, ZStoreOptional option);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param option
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(K destKey, K[] keys, ZStoreOptional option, long expireSeconds);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param option
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, K[] keys, ZStoreOptional option);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param option
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, K[] keys, ZStoreOptional option, long expireSeconds);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param option
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Collection<K> keys, ZStoreOptional option);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param destKey
	 * @param keys
	 * @param option
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Collection<K> keys, ZStoreOptional option, long expireSeconds);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param option
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys, ZStoreOptional option);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param option
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys, ZStoreOptional option, long expireSeconds);
	
	/**
	 * 在当前库中执行zIncrBy命令，为指定键集成员的socre值加上(increment>0)/减去(increment<0)增量
	 * @author Daniele 
	 * @param key
	 * @param increment
	 * @param member
	 * @return
	 */
	public <K, V> Double zIncrBy(K key, double increment, V member);
	
	/**
	 * 在当前库中执行zIncrBy命令，为指定键集成员的socre值加上(increment>0)/减去(increment<0)增量
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param increment
	 * @param member
	 * @return
	 */
	public <K, V> Double zIncrBy(String dbName, K key, double increment, V member);
	
	/**
	 * 在当前库中执行zScan命令，以增量迭代的方式获取所有的有序成员
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(K key);
	
	/**
	 * 在当前库中执行zScan命令，以增量迭代的方式获取所有指定类型的有序成员
	 * @author Daniele 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行zScan命令，从指定的游标处开始，以增量迭代的方式获取所有的有序成员
	 * @author Daniele 
	 * @param key
	 * @param cursorId
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(K key, long cursorId);
	
	/**
	 * 在当前库中执行zScan命令，从指定的游标处开始，以增量迭代的方式获取所有指定类型的有序成员
	 * @author Daniele 
	 * @param key
	 * @param cursorId
	 * @param valueType
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(K key, long cursorId, Class<V> valueType);
	
	/**
	 * 在当前库中执行zScan命令，以增量迭代的方式获取所有的有序成员
	 * @author Daniele 
	 * @param key
	 * @param option
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(K key, ScanOption option);
	
	/**
	 * 在当前库中执行zScan命令，以增量迭代的方式获取所有指定类型的有序成员
	 * @author Daniele 
	 * @param key
	 * @param option
	 * @param valueType
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(K key, ScanOption option, Class<V> valueType);
	
	/**
	 * 在当前库中执行zScan命令，从指定的游标处开始，以增量迭代的方式获取所有的有序成员
	 * @author Daniele 
	 * @param key
	 * @param cursorId
	 * @param option
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(K key, long cursorId, ScanOption option);
	
	/**
	 * 在当前库中执行zScan命令，从指定的游标处开始，以增量迭代的方式获取所有指定类型的有序成员
	 * @author Daniele 
	 * @param key
	 * @param cursorId
	 * @param option
	 * @param valueType
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(K key, long cursorId, ScanOption option, Class<V> valueType);
	
	/**
	 * 在指定库中执行zScan命令，以增量迭代的方式获取所有的有序成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScanIn(String dbName, K key);
	
	/**
	 * 在指定库中执行zScan命令，以增量迭代的方式获取所有指定类型的有序成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScanIn(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行zScan命令，从指定的游标处开始，以增量迭代的方式获取所有的有序成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(String dbName, K key, long cursorId);
	
	/**
	 * 在指定库中执行zScan命令，从指定的游标处开始，以增量迭代的方式获取所有指定类型的有序成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @param valueType
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(String dbName, K key, long cursorId, Class<V> valueType);
	
	/**
	 * 在指定库中执行zScan命令，以增量迭代的方式获取所有的有序成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param option
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(String dbName, K key, ScanOption option);
	
	/**
	 * 在指定库中执行zScan命令，以增量迭代的方式获取所有指定类型的有序成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param option
	 * @param valueType
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(String dbName, K key, ScanOption option, Class<V> valueType);
	
	/**
	 * 在指定库中执行zScan命令，从指定的游标处开始，以增量迭代的方式获取所有的有序成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @param option
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(String dbName, K key, long cursorId, ScanOption option);
	
	/**
	 * 在指定库中执行zScan命令，从指定的游标处开始，以增量迭代的方式获取所有指定类型的有序成员
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @param option
	 * @param valueType
	 * @return
	 */
	public <K, V> MappedScanResult<V, Double> zScan(String dbName, K key, long cursorId, ScanOption option, Class<V> valueType);
	
}
