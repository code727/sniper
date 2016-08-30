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

import org.workin.nosql.redis.dao.RedisListCommands;

import redis.clients.jedis.BinaryClient.LIST_POSITION;

/**
 * @description Jedis列表命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JedisListCommands extends RedisListCommands {
	
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

}
