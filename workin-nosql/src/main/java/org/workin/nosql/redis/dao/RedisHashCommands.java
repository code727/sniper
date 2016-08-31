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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis哈希命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisHashCommands {
	
	/**
	 * 在当前库中执行hSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param field 域
	 * @param value 值
	 * @return
	 */
	public <K, F, V> Boolean hSet(K key, F field, V value);
	
	/**
	 * 在当前库中执行hSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param field
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, F, V> Boolean hSet(K key, F field, V value, long expireSeconds);
	
	/**
	 * 在指定索引库中执行hSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex 库索引
	 * @param key 键
	 * @param field 域
	 * @param value 值
	 * @return
	 */
	public <K, F, V> Boolean hSet(int dbIndex, K key, F field, V value);
	
	/**
	 * 在当前库中执行hSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param field
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, F, V> Boolean hSet(int dbIndex, K key, F field, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行hSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param field 域
	 * @param value 值
	 * @return
	 */
	public <K, F, V> Boolean hSetNX(K key, F field, V value);
	
	/**
	 * 在当前库中执行hSetNX命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param field
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, F, V> Boolean hSetNX(K key, F field, V value, long expireSeconds);
	
	/**
	 * 在指定索引库中执行hSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex 库索引
	 * @param key 键
	 * @param field 域
	 * @param value 值
	 * @return
	 */
	public <K, F, V> Boolean hSetNX(int dbIndex, K key, F field, V value);
	
	/**
	 * 在指定索引库中执行hSetNX命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param field
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, F, V> Boolean hSetNX(int dbIndex, K key, F field, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行hMSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param fValues 域-值映射集
	 */
	public <K, F, V> void hMSet(K key, Map<F, V> fValues);
	
	/**
	 * 在当前库中执行hMSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param fValues
	 * @param expireSeconds
	 */
	public <K, F, V> void hMSet(K key, Map<F, V> fValues, long expireSeconds);
	
	/**
	 * 在指定索引库中执行hMSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex 库索引
	 * @param key 键
	 * @param fValues 域-值映射集
	 */
	public <K, F, V> void hMSet(int dbIndex, K key, Map<F, V> fValues);
	
	/**
	 * 在当前库中执行hMSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param fValues
	 * @param expireSeconds
	 */
	public <K, F, V> void hMSet(int dbIndex, K key, Map<F, V> fValues, long expireSeconds);
	
	/**
	 * 删除当前库指定键对应的域值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param filed
	 * @return
	 */
	public <K, F> Long hDel(K key, F filed);
	
	/**
	 * 删除指定库键对应的域值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param filed
	 * @return
	 */
	public <K, F> Long hDel(int dbIndex, K key, F filed);
	
	/**
	 * 删除当前库指定键对应的多个域值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param fileds
	 * @return
	 */
	public <K, F> Long hDel(K key, F[] fileds);
	
	/**
	 * 删除指定库键对应的多个域值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param fileds
	 * @return
	 */
	public <K, F> Long hDel(int dbIndex, K key, F[] fileds);
	
	/**
	 * 删除当前库指定键对应的多个域值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param fileds
	 * @return
	 */
	public <K, F> Long hDel(K key, Collection<F> fileds);
	
	/**
	 * 删除指定库键对应的多个域值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param fileds
	 * @return
	 */
	public <K, F> Long hDel(int dbIndex, K key, Collection<F> fileds);
	
	/**
	 * 判断当前库指定键对应域是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param filed
	 * @return
	 */
	public <K, F> Boolean hExists(K key, F filed);
	
	/**
	 * 判断指定库键对应域是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param filed
	 * @return
	 */
	public <K, F> Boolean hExists(int dbIndex, K key, F filed);
	
	/**
	 * 在当前库中执行hGet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param filed
	 * @return
	 */
	public <K, F, V> V hGet(K key, F filed);
	
	/**
	 * 在指定库中执行hGet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param filed
	 * @return
	 */
	public <K, F, V> V hGet(int dbIndex, K key, F filed);
	
	/**
	 * 在当前库中执行hGetAll命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return 域值映射集
	 */
	public <K, F, V> Map<F, V> hGetAll(K key);
	
	/** 
	 * 在指定库中执行hGetAll命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K, F, V> Map<F, V> hGetAll(int dbIndex, K key);
	
	/**
	 * 在当前库中执行hKeys命令，获取键对应的所有域
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, F> Set<F> hKeys(K key);
	
	/**
	 * 在指定库中执行hKeys命令，获取键对应的所有域
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K, F> Set<F> hKeys(int dbIndex, K key);
	
	/**
	 * 在当前库中执行hLen命令，获取键对应的域个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long hLen(K key);
	
	/**
	 * 在指定库中执行hLen命令，获取键对应的域个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Long hLen(int dbIndex, K key);
	
	/**
	 * 在当前库中执行hMGet命令，获取键对应的多个域的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param fields
	 * @return
	 */
	public <K, F, V> List<V> hMGet(K key, F[] fields);
	
	/**
	 * 在指定库中执行hMGet命令，获取键对应的多个域的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param fields
	 * @return
	 */
	public <K, F, V> List<V> hMGet(int dbIndex, K key, F[] fields);
	
	/**
	 * 在当前库中执行hMGet命令，获取键对应的多个域的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param fields
	 * @return
	 */
	public <K, F, V> List<V> hMGet(K key, Collection<F> fields);
	
	/**
	 * 在指定库中执行hMGet命令，获取键对应的多个域的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param fields
	 * @return
	 */
	public <K, F, V> List<V> hMGet(int dbIndex, K key, Collection<F> fields);
	
	/**
	 * 在当前库中执行hVals命令，获取键对应的所有域的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> List<V> hVals(K key);
	
	/**
	 * 在指定库中执行hMGet命令，获取键对应的所有域的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K, V> List<V> hVals(int dbIndex, K key);

}
