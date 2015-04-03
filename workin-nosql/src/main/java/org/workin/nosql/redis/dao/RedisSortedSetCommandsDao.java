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

package org.workin.nosql.redis.dao;

import java.util.Map;
import java.util.Set;

/**
 * @description Redis有序集合命令行数据访问接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisSortedSetCommandsDao {
	
	/**
	 * @description 在默认第0库中执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, double score, V member);
	
	/**
	 * @description 在指定索引库中执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public <K, V> Boolean zAdd(int dbIndex, K key, double score, V member);
	
	/**
	 * @description 在默认第0库中执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers);
	
	/**
	 * @description 在指定索引库中执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public <K, V> Boolean zAdd(int dbIndex, K key, Map<Double, V> scoreMembers);
	
	/**
	 * @description 在默认第0库中执行zCard命令，获取有序集合键对应的元素个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long zCard(K key);
	
	/**
	 * @description 在指定索引库中执行sCard命令，获取有序集合键对应的元素个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Long zCard(int dbIndex, K key);
	
	/**
	 * @description 在默认第0库中执行zCount命令，获取有序集合在 (minScore, maxScore]区间范围内的成员数量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zCount(K key, double minScore, double maxScore);
	
	/**
	 * @description 在指定索引库中执行zCount命令，获取有序集合在 (minScore, maxScore]区间范围内的成员数量。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return
	 */
	public <K> Long zCount(int dbIndex, K key, double minScore, double maxScore);
	
	/**
	 * @description 在默认第0库中执行zRange命令，获取有序集合下标在区间范围内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRange(K key, long begin, long end);
	
	/**
	 * @description 在指定索引库中执行zRange命令，获取有序集合下标在区间范围内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRange(int dbIndex, K key, long begin, long end);
	
	/**
	 * @description 在默认第0库中执行zRange命令，获取有序集合内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRangeAll(K key);
	
	/**
	 * @description 在指定索引库中执行zRange命令，获取有序集合内的所有成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> Set<V> zRangeAll(int dbIndex, K key);

}
