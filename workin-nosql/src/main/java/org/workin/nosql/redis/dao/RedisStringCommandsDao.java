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
 * Create Date : 2015-3-26
 */

package org.workin.nosql.redis.dao;

import java.util.Map;

/**
 * @description Redis字符串命令行数据访问接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisStringCommandsDao extends RedisListCommandsDao {
	
	/**
	 * @description 在默认第0库中执行set命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param value 值
	 */
	public <K,V> void set(K key, V value);
	
	/**
	 * @description 在指定索引库中执行set命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index 库索引
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> void set(int index, K key, V value);
	
	/**
	 * @description 在默认第0库中执行setNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> Boolean setNX(K key, V value);
	
	/**
	 * @description 在指定索引库中执行setNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index 库索引
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> Boolean setNX(int index, K key, V value);
	
	/**
	 * @description 在默认第0库中执行mSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValues
	 */
	public <K, V> void mSet(Map<K, V> kValues);
	
	/**
	 * @description 在指定索引库中执行mSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param kValues
	 */
	public <K, V> void mSet(int index, Map<K, V> kValues);

	/**
	 * @description 在默认第0库中执行mSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValus
	 */
	public <K, V> void mSetNX(Map<K, V> kValues);
	
	/**
	 * @description 在指定索引库中执行mSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param kValues
	 */
	public <K, V> void mSetNX(int index, Map<K, V> kValues);
	
	/**
	 * @description 在默认第0库中执行setRange命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param offset
	 * @param value
	 */
	public <K, V> void setRange(K key, long offset, V value);
	
	/**
	 * @description 在指定索引库中执行setRange命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param offset
	 * @param value
	 */
	public <K, V> void setRange(int index, K key, long offset, V value);
	
}
