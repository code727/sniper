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

import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.sniper.nosql.redis.dao.RedisListCommands;

/**
 * Spring Redis列表命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SpringRedisListCommands extends RedisListCommands {
	
	/**
	 * 在当前库中执行lInsert命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param where
	 * @param pivot 位置值
	 * @param value
	 * @return
	 */
	public <K, V> Long lInsert(K key, Position where, V pivot, V value);
	
	/**
	 * 在当前库中执行lInsert命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lInsert(K key, Position where, V pivot, V value, long expireSeconds);
	
	/**
	 * 在指定索引库中执行lInsert命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param where
	 * @param pivot 位置值
	 * @param value
	 * @return
	 */
	public <K, V> Long lInsert(String dbName, K key, Position where, V pivot, V value);
	
	/**
	 * 在指定索引库中执行lInsert命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long lInsert(String dbName, K key, Position where, V pivot, V value, long expireSeconds);

}
