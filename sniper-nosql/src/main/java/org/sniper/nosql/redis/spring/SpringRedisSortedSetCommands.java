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
 * Create Date : 2016-8-29
 */

package org.sniper.nosql.redis.spring;

import java.util.Collection;
import java.util.Set;

import org.sniper.nosql.redis.command.RedisSortedSetCommands;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;

/**
 * Spring Redis有序集合命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SpringRedisSortedSetCommands extends RedisSortedSetCommands {
	
	/**
	 * 在当前库中执行zRangeByScore命令，获取有序集合在 [minScore, maxScore]区间范围内的Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRangeByScoreWithScores(K key, double minScore, double maxScore);
	
	/**
	 * 在指定库中执行zRangeByScore命令，获取有序集合在 [minScore, maxScore]区间范围内的Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore);
	
	/**
	 * 在当前库中执行zRangeByScore命令，从offset开始定位，获取有序集合在 [minScore, maxScore]区间范围内最多count个Tuple对象
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
	 * 在指定库中执行zRangeByScore命令，从offset开始定位，获取有序集合在 [minScore, maxScore]区间范围内最多count个Tuple对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRangeByScoreWithScores(String dbName, K key, double minScore, double maxScore, long offset, long count);
	
	/**
	 * 在当前库中执行zRevRangeByScore命令，按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内的Tuple对象集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Set<Tuple> zRevRangeByScoreWithScores(K key, double minScore, double maxScore);
	
	/**
	 * 在指定库中执行zRevRangeByScore命令，按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内的Tuple对象集
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
	 * 在当前库中执行zRevRangeByScore命令，从offset开始定位，按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内最多count个Tuple对象集
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
	 * 在指定库中执行zRevRangeByScore命令，从offset开始定位，按score值降序方式返回指定键集在 [minScore, maxScore]区间范围内最多count个Tuple对象集
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
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, K[] keys);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, K[] keys, long expireSeconds);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Aggregate aggregate, int[] weights, K[] keys);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Aggregate aggregate, int[] weights, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys);
	
	/**
	 * 在当前库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Aggregate aggregate, int[] weights, Collection<K> keys);
	
	/**
	 * 在指定库中执行zUnionStore命令，将多个键的并集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zUnionStore(String dbName, K destKey, Aggregate aggregate, int[] weights, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, K[] keys);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, K[] keys, long expireSeconds);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Aggregate aggregate, int[] weights, K[] keys);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Aggregate aggregate, int[] weights, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys);
	
	/**
	 * 在当前库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(K destKey, Aggregate aggregate, int[] weights, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Aggregate aggregate, int[] weights, Collection<K> keys);
	
	/**
	 * 在指定库中执行zInterStore命令，将多个键的交集存入到目标键中，并设置过期秒数后返回存入的个数返回存入的个数</P>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param aggregate
	 * @param weights
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long zInterStore(String dbName, K destKey, Aggregate aggregate, int[] weights, Collection<K> keys, long expireSeconds);
}
