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
 * Create Date : 2015-3-30
 */

package org.workin.nosql.redis.dao;

import java.util.Collection;
import java.util.Set;

/**
 * @description Redis键命令行数据访问接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisKeyCommandsDao {
	
	/**
	 * @description 获取默认第0库中所有的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public <K> Set<K> keys();
	
	/**
	 * @description 获取指定库中所有的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return
	 */
	public <K> Set<K> keys(int dbIndex);
	
	/**
	 * @description 获取默认第0库中指定模式的键集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
	public <K> Set<K> keys(String pattern);
	
	/**
	 * @description 获取指定库中满足模式的键集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param pattern
	 * @return
	 */
	public <K> Set<K> keys(int dbIndex, String pattern);
	
	/**
	 * @description 删除默认第0库指定的多个键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K> Long del(K[] keys);
	
	/**
	 * @description 删除指定库多个键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K> Long del(int dbIndex, K[] keys);
	
	/**
	 * @description 删除默认第0库指定的多个键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K> Long del(Collection<K> keys);
	
	/**
	 * @description 删除指定库多个键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K> Long del(int dbIndex, Collection<K> keys);
	
	/**
	 * @description 判断默认第0库中指定键是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Boolean exists(K key);
	
	/**
	 * @description 判断指定库中的键是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Boolean exists(int dbIndex, K key);
	
	/**
	 * @description 设置默认第0库中指定键的过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public <K> Boolean expire(K key, long seconds);
	
	/**
	 * @description 设置指定库中键的过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param seconds
	 * @return
	 */
	public <K> Boolean expire(int dbIndex, K key, long seconds);
	
	

}
