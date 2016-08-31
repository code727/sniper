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

package org.workin.nosql.redis.jedis;

import java.util.Collection;

import org.workin.nosql.redis.dao.RedisSortedSetCommands;

import redis.clients.jedis.ZParams;

/**
 * Jedis有序集合命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JedisSortedSetCommands extends RedisSortedSetCommands {
	
	/**
	 * 在当前库中执行zUnionStore命令，
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
	 * 在指定索引库中执行zUnionStore命令，
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
	 * 在当前库中执行zUnionStore命令，
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
	 * 在指定索引库中执行zUnionStore命令，
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
	 * 在当前库中执行zUnionStore命令，
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
	 * 在指定索引库中执行zUnionStore命令，
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
	 * 在当前库中执行zUnionStore命令，
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
	 * 在指定索引库中执行zUnionStore命令，
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
