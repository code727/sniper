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
 * @description Redis哈希命令行数据访问接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisHashCommandsDao {
	
	/**
	 * @description 在默认第0库中执行hSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param field 域
	 * @param value 值
	 * @return
	 */
	public <K, F, V> Boolean hSet(K key, F field, V value);
	
	/**
	 * @description 在指定索引库中执行hSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index 库索引
	 * @param key 键
	 * @param field 域
	 * @param value 值
	 * @return
	 */
	public <K, F, V> Boolean hSet(int index, K key, F field, V value);
	
	/**
	 * @description 在默认第0库中执行hSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param field 域
	 * @param value 值
	 * @return
	 */
	public <K, F, V> Boolean hSetNX(K key, F field, V value);
	
	/**
	 * @description 在指定索引库中执行hSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index 库索引
	 * @param key 键
	 * @param field 域
	 * @param value 值
	 * @return
	 */
	public <K, F, V> Boolean hSetNX(int index, K key, F field, V value);
	
	/**
	 * @description 在默认第0库中执行hMSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param fValues 域-值映射集
	 */
	public <K, F, V> void hMSet(K key, Map<F, V> fValues);
	
	/**
	 * @description 在指定索引库中执行hMSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index 库索引
	 * @param key 键
	 * @param fValues 域-值映射集
	 */
	public <K, F, V> void hMSet(int index, K key, Map<F, V> fValues);

}
