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

package org.sniper.nosql.redis.dao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;

/**
 * Redis有序集合命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisSortedSetCommands {
	
	/**
	 * 在当前库中执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, double score, V member);
	
	/**
	 * 在当前库中执行zAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param score
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, double score, V member, long expireSeconds);
	
	/**
	 * 在指定索引库中执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public <K, V> Boolean zAdd(String dbName, K key, double score, V member);
	
	/**
	 * 在指定索引库中执行zAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param score
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Boolean zAdd(String dbName, K key, double score, V member, long expireSeconds);
	
	/**
	 * 在当前库中执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers);
	
	/**
	 * 在当前库中执行zAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param scoreMembers
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers, long expireSeconds);
	
	/**
	 * 在指定索引库中执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public <K, V> Boolean zAdd(String dbName, K key, Map<Double, V> scoreMembers);
	
	/**
	 * 在指定索引库中执行zAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param scoreMembers
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Boolean zAdd(String dbName, K key, Map<Double, V> scoreMembers, long expireSeconds);
	
	/**
	 * 在当前库中执行zCard命令，获取有序集合键对应的元素个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long zCard(K key);
	
	/**
	 * 在指定索引库中执行sCard命令，获取有序集合键对应的元素个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long zCard(String dbName, K key);
	
	/**
	 * 在当前库中执行zCount命令，获取有序集合在 (minScore, maxScore]区间范围内的成员数量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zCount(K key, double minScore, double maxScore);
	
	/**
	 * 在指定索引库中执行zCount命令，获取有序集合在 (minScore, maxScore]区间范围内的成员数量。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zCount(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRange命令，获取有序集合下标在区间范围内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRange(K key, long begin, long end);
	
	/**
	 * 在指定索引库中执行zRange命令，获取有序集合下标在区间范围内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRange(String dbName, K key, long begin, long end);
	
	/**
	 * 在当前库中执行zRange命令，获取有序集合内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRangeAll(K key);
	
	/**
	 * 在指定索引库中执行zRange命令，获取有序集合内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRangeAll(String dbName, K key);
	
	/**
	 * 在当前库中执行zRangeByScore命令，
	 * 				获取有序集合在 [minScore, maxScore]区间范围内的成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore);
	
	/**
	 * 在指定索引库中执行zRangeByScore命令，
	 * 				获取有序集合在 [minScore, maxScore]区间范围内的成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRangeByScore命令，从offset开始定位，
	 * 				获取有序集合在 [minScore, maxScore]区间范围内最多count个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(K key, double minScore, double maxScore, long offset, long count);
	
	/**
	 * 在指定索引库中执行zRangeByScore命令，从offset开始定位，
	 * 				获取有序集合在 [minScore, maxScore]区间范围内最多count个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param offset
	 * @param count
	 * @return
	 */
	public <K, V> Set<V> zRangeByScore(String dbName, K key, double minScore, double maxScore, long offset, long count);
	
	/**
	 * 在当前库中执行zRangeByScore命令，
	 * 				获取有序集合在 [minScore, maxScore]区间范围内的Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore, double maxScore);
	
	/**
	 * 在指定索引库中执行zRangeByScore命令，
	 * 				获取有序集合在 [minScore, maxScore]区间范围内的Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRangeByScore命令，从offset开始定位，
	 * 				获取有序集合在 [minScore, maxScore]区间范围内最多count个Tuple对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param offset
	 * @param count
	 * @return
	 */
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore, double maxScore, long offset, long count);
	
	/**
	 * 在指定索引库中执行zRangeByScore命令，从offset开始定位，
	 * 				获取有序集合在 [minScore, maxScore]区间范围内最多count个Tuple对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, long offset, long count);

	/**
	 * 在当前库中执行zRank命令，
	 * 				获取有序键集中，指定成员按score值升序排列后的下标索引
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRank(K key, V member);
	
	/**
	 * 在指定索引库中执行zRank命令，
	 * 				获取有序键集中，指定成员按score值升序排列后的下标索引
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRank(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行zRem命令，删除指定键集中的成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRem(K key, V member);
	
	/**
	 * 在指定索引库中执行zRem命令，删除指定键集中的成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRem(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行zRem命令，删除指定键集中的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long zRem(K key, V[] members);
	
	/**
	 * 在指定索引库中执行zRem命令，删除指定键集中的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long zRem(String dbName, K key, V[] members);
	
	/**
	 * 在当前库中执行zRem命令，删除指定键集中的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long zRem(K key, Collection<V> members);
	
	/**
	 * 在指定索引库中执行zRem命令，删除指定键集中的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long zRem(String dbName, K key, Collection<V> members);
	
	/**
	 * 在当前库中执行zRemRangeByRank命令，删除指定下标索引范围内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K> Long zRemRangeByRank(K key, long begin, long end);
	
	/**
	 * 在指定索引库中执行zRemRangeByRank命令，删除指定下标索引范围内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K> Long zRemRangeByRank(String dbName, K key, long begin, long end);
	
	/**
	 * 在当前库中执行zRemRangeByScore命令，删除指定排名范围内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zRemRangeByScore(K key, double minScore, double maxScore);
	
	/**
	 * 在指定索引库中执行zRemRangeByRank命令，删除指定排名范围内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zRemRangeByScore(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRevRange命令，按score值降序方式返回指定键集范围内的成员集合
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRevRange(K key, long begin, long end);
	
	/**
	 * 在指定索引库中执行zRevRange命令，按score值降序方式返回指定键集范围内的成员集合
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRevRange(String dbName, K key, long begin, long end);
	
	/**
	 * 在当前库中执行zRevRange命令，按score值降序方式返回指定键集的所有成员集合
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> Set<V> zRevRangeAll(K key);
	
	/**
	 * 在指定索引库中执行zRevRange命令，按score值降序方式返回指定键集的所有成员集合
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> Set<V> zRevRangeAll(String dbName, K key);
	
	/**
	 * 在当前库中执行zRevRangeByScore命令，
	 * 				按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内的所有成员集合
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(K key, double minScore, double maxScore);
	
	/**
	 * 在指定索引库中执行zRevRangeByScore命令，
	 * 				按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内的所有成员集合
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K, V> Set<V> zRevRangeByScore(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRevRangeByScore命令，
	 * 				按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内的Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore, double maxScore);
	
	/**
	 * 在指定索引库中执行zRevRangeByScore命令，
	 * 				按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内的Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRevRangeByScoreWithScores(String dbName, K key,
			double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRevRangeByScore命令，从offset开始定位，
	 * 				按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内最多count个Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param offset
	 * @param count
	 * @return
	 */
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore,
			double maxScore, long offset, long count);
	
	/**
	 * 在指定索引库中执行zRevRangeByScore命令，从offset开始定位，
	 * 				按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内最多count个Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @param offset
	 * @param count
	 * @return
	 */
	public <K> Set<Tuple> zRevRangeByScoreWithScores(String dbName, K key,
			double minScore, double maxScore, long offset, long count);
	
	/**
	 * 在当前库中执行zRevRank命令，
	 * 				获取有序键集中，指定成员按score值降序排列后的下标索引
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRevRank(K key, V member);
	
	/**
	 * 在指定索引库中执行zRevRank命令，
	 * 				获取有序键集中，指定成员按score值降序排列后的下标索引
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long zRevRank(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行zScore命令，获取有序键集中，指定成员的score值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Double zScore(K key, V member);
	
	/**
	 * 在指定索引库中执行zScore命令，获取有序键集中，指定成员的score值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Double zScore(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param key
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, K key);
	
	/**
	 * 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param key
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, K key);
	
	/**
	 * 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, K[] keys);
	
	/**
	 * 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, K[] keys);
	
	/**
	 * 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Collection<K> keys);
	
	/**
	 * 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的并集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行zInterStore命令，
	 * 				获取指定目标键集与键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param srcKey
	 * @return
	 */
	public <K> Long zInterStore(K destKey, K srcKey);
	
	/**
	 * 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param srcKey
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, K srcKey);
	
	/**
	 * 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(K destKey, K[] keys);
	
	/**
	 * 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, K[] keys);
	
	/**
	 * 在当前库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Collection<K> keys);
	
	/**
	 * 在指定索引库中执行zUnionStore命令，
	 * 				获取指定目标键集与多个键集的交集后存入目标键集，并返回目标键集的基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行zIncrBy命令，
	 * 				为指定的键集成员的socre值加上(increment > 0)/减去(increment < 0)增量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param increment
	 * @param member
	 * @return
	 */
	public <K, V> Double zIncrBy(K key, double increment, V member);
	
	/**
	 * 在当前库中执行zIncrBy命令，
	 * 				为指定的键集成员的socre值加上(increment > 0)/减去(increment < 0)增量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param increment
	 * @param member
	 * @return
	 */
	public <K, V> Double zIncrBy(String dbName, K key, double increment, V member);
}
