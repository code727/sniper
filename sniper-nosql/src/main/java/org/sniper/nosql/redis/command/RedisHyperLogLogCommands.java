/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-2-20
 */

package org.sniper.nosql.redis.command;

import java.util.Collection;

/**
 * Redis HyperLogLog命令行接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisHyperLogLogCommands {
	
	/**
	 * 在当前库中执行PFADD命令，将元素添加到HyperLogLog后返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param element
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(K key, V element);
	
	/**
	 * 在当前库中执行PFADD命令，将元素添加到HyperLogLog后设置过期时间并返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param element
	 * @param expireSeconds 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 * @return
	 */
	public <K,V> Long pfAdd(K key, V element, long expireSeconds);
	
	/**
	 * 在指定库中执行PFADD命令，将元素添加到HyperLogLog后返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param element
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAddIn(String dbName, K key, V element);
	
	/**
	 * 在指定库中执行PFADD命令，将元素添加到HyperLogLog后设置过期时间并返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param element
	 * @param expireSeconds
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(String dbName, K key, V element, long expireSeconds);
	
	/**
	 * 在当前库中执行PFADD命令，将多个元素添加到HyperLogLog后返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param elements
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(K key, V[] elements);
	
	/**
	 * 在当前库中执行PFADD命令，将多个元素添加到HyperLogLog后设置过期时间并返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param elements
	 * @param expireSeconds
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(K key, V[] elements, long expireSeconds);
	
	/**
	 * 在指定库中执行PFADD命令，将多个元素添加到HyperLogLog后返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param elements
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(String dbName, K key, V[] elements);
	
	/**
	 * 在指定库中执行PFADD命令，将多个元素添加到HyperLogLog后设置过期时间并返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param elements
	 * @param expireSeconds
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(String dbName, K key, V[] elements, long expireSeconds);
	
	/**
	 * 在当前库中执行PFADD命令，将多个元素添加到HyperLogLog后返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param elements
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(K key, Collection<V> elements);
	
	/**
	 * 在指定库中执行PFADD命令，将多个元素添加到HyperLogLog后设置过期时间并返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param elements
	 * @param expireSeconds
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(K key, Collection<V> elements, long expireSeconds);
	
	/**
	 * 在指定库中执行PFADD命令，将多个元素添加到HyperLogLog后返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param elements
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(String dbName, K key, Collection<V> elements);
	
	/**
	 * 在指定库中执行PFADD命令，将多个元素添加到HyperLogLog后设置过期时间并返回出现的变化结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param elements
	 * @param expireSeconds
	 * @return 1:表示HyperLogLog的内部储存被修改了</P> 0:表示HyperLogLog的内部储存未被修改
	 */
	public <K,V> Long pfAdd(String dbName, K key, Collection<V> elements, long expireSeconds);
	
	/**
	 * 在当前库中获取指定键的HyperLogLog近似基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long pfCount(K key);
	
	/**
	 * 在指定库中获取指定键的HyperLogLog近似基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long pfCount(String dbName, K key);
	
	/**
	 * 在当前库中获取多个键的 HyperLogLog的并集近似基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K> Long pfCount(K[] keys);
	
	/**
	 * 在当前库中获取多个键的 HyperLogLog的并集近似基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K> Long pfCount(String dbName, K[] keys);
	
	/**
	 * 在当前库中获取多个键的HyperLogLog的并集近似基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K> Long pfCount(Collection<K> keys);
	
	/**
	 * 在指定库中获取多个键的 HyperLogLog的并集近似基数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K> Long pfCount(String dbName, Collection<K> keys);
	
	/**
	 * 在当前库中将源键的HyperLogLog合并到目标键的HyperLogLog
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param sourceKey
	 * @return
	 */
	public <K> void pfMerge(K destKey, K sourceKey);
		
	/**
	 * 在指定库中将源键的HyperLogLog合并到目标键的HyperLogLog
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param sourceKey
	 */
	public <K> void pfMerge(String dbName, K destKey, K sourceKey);
		
	/**
	 * 在当前库中将多个源键的HyperLogLog合并到目标键的HyperLogLog
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param sourceKeys
	 */
	public <K> void pfMerge(K destKey, K[] sourceKeys);
	
	/**
	 * 在指定库中将多个源键的HyperLogLog合并到目标键的HyperLogLog
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param sourceKeys
	 */
	public <K> void pfMerge(String dbName, K destKey, K[] sourceKeys);
	
	/**
	 * 在当前库中将多个源键的HyperLogLog合并到目标键的HyperLogLog
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param sourceKeys
	 */
	public <K> void pfMerge(K destKey, Collection<K> sourceKeys);
	
	/**
	 * 在指定库中将多个源键的HyperLogLog合并到目标键的HyperLogLog
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param sourceKeys
	 */
	public <K> void pfMerge(String dbName, K destKey, Collection<K> sourceKeys);
	
}
