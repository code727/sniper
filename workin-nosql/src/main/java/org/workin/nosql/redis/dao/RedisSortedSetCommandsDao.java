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
	 * @param index
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public <K, V> Boolean zAdd(int index, K key, double score, V member);
	
	/**
	 * @description 在默认第0库中批量执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public <K, V> Boolean zAdd(K key, Map<Double, V> scoreMembers);
	
	/**
	 * @description 在指定索引库中批量执行zAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param scoreMembers
	 * @return
	 */
	public <K, V> Boolean zAdd(int index, K key, Map<Double, V> scoreMembers);

}
