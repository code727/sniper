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

package org.sniper.nosql.redis.command;

import java.util.Collection;
import java.util.Set;

import org.sniper.nosql.redis.model.xscan.IndexedScanResult;
import org.sniper.nosql.redis.option.ScanOption;

/**
 * Redis集合命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisSetCommands {
	
	/**
	 * 在当前库中执行sAdd命令，将成员值加入到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sAdd(K key, V member);
	
	/**
	 * 在当前库中执行sAdd命令，将成员值加入到集合中并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(K key, V member, long expireSeconds);
	
	/**
	 * 在指定库中执行sAdd命令，将成员值加入到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sAddIn(String dbName, K key, V member);
	
	/**
	 * 在指定库中执行sAdd命令，将成员值加入到集合中并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(String dbName, K key, V member, long expireSeconds);
	
	/**
	 * 在当前库中执行sAdd命令，将多个成员值加入到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sAdd(K key, V[] members);
	
	/**
	 * 在当前库中执行sAdd命令，将多个成员值加入到集合中并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(K key, V[] members, long expireSeconds);
	
	/**
	 * 在指定库中执行sAdd命令，将多个成员值加入到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sAdd(String dbName, K key, V[] members);
	
	/**
	 * 在指定库中执行sAdd命令，将多个成员值加入到集合中并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(String dbName, K key, V[] members, long expireSeconds);
	
	/**
	 * 在当前库中执行sAdd命令，将多个成员值加入到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param memberss
	 * @return
	 */
	public <K, V> Long sAdd(K key, Collection<V> members);
	
	/**
	 * 在当前库中执行sAdd命令，将多个成员值加入到集合中并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(K key, Collection<V> members, long expireSeconds);
	
	/**
	 * 在指定库中执行sAdd命令，将多个成员值加入到集合中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sAdd(String dbName, K key, Collection<V> members);
	
	/**
	 * 在指定库中执行sAdd命令，将多个成员值加入到集合中并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(String dbName, K key, Collection<V> members, long expireSeconds);
	
	/**
	 * 在当前库中执行sCard命令，获取集合键对应的元素个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long sCard(K key);
	
	/**
	 * 在指定库中执行sCard命令，获取集合键对应的元素个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long sCard(String dbName, K key);
	
	/**
	 * 在当前库中执行sDiff命令，获取多个键集之间的值差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sDiff(Collection<K> keys);
	
	/**
	 * 在当前库中执行sDiff命令，获取多个键集之间的值差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sDiff(Collection<K> keys, Class<V> valueType);
	
	/**
	 * 在指定库中执行sDiff命令，获取多个键集之间的值差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sDiff(String dbName, Collection<K> keys);
	
	/**
	 * 在指定库中执行sDiff命令，获取多个键集之间的值差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sDiff(String dbName, Collection<K> keys, Class<V> valueType);
	
	/**
	 * 在当前库中执行sDiff命令，获取多个键集之间的值差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sDiff(K[] keys);
	
	/**
	 * 在当前库中执行sDiff命令，获取多个键集之间的值差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sDiff(K[] keys, Class<V> valueType);
	
	/**
	 * 在指定库中执行sDiff命令，获取多个键集之间的值差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sDiff(String dbName, K[] keys);
	
	/**
	 * 在指定库中执行sDiff命令，获取多个键集之间的值差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sDiff(String dbName, K[] keys, Class<V> valueType);
	
	/**
	 * 在当前库中执行sDiffStore命令，将返回的值差集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sDiffStore(T destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行sDiffStore命令，将返回的值差集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sDiffStore(T destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定库中执行sDiffStore命令，将返回的值差集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sDiffStore(String dbName, T destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行sDiffStore命令，将返回的值差集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sDiffStore(String dbName, T destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sDiffStore命令，将返回的值差集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sDiffStore(T destKey, K[] keys);
	
	/**
	 * 在当前库中执行sDiffStore命令，将返回的值差集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sDiffStore(T destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在指定库中执行sDiffStore命令，将返回的值差集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sDiffStore(String dbName, T destKey, K[] keys);
	
	/**
	 * 在指定库中执行sDiffStore命令，将返回的值差集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sDiffStore(String dbName, T destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sInter命令，获取多个键集之间的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sInter(Collection<K> keys);
	
	/**
	 * 在当前库中执行sInter命令，获取多个键集之间的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sInter(Collection<K> keys, Class<V> valueType);
	
	/**
	 * 在指定库中执行sInter命令，获取多个键集之间的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sInter(String dbName, Collection<K> keys);
	
	/**
	 * 在指定库中执行sInter命令，获取多个键集之间的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sInter(String dbName, Collection<K> keys, Class<V> valueType);
	
	/**
	 * 在当前库中执行sInter命令，获取多个键集之间的值交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sInter(K[] keys);
	
	/**
	 * 在当前库中执行sInter命令，获取多个键集之间的值交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sInter(K[] keys, Class<V> valueType);
	
	/**
	 * 在指定库中执行sInter命令，获取多个键集之间的值交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sInter(String dbName, K[] keys);
	
	/**
	 * 在指定库中执行sInter命令，获取多个键集之间的值交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sInter(String dbName, K[] keys, Class<V> valueType);
	
	/**
	 * 在当前库中执行sInterStore命令，将返回的值交集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sInterStore(T destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行sInterStore命令，将返回的值交集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sInterStore(T destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定库中执行sInterStore命令，将返回的值交集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sInterStore(String dbName, T destKey, Collection<K> keys);
	
	/**
	 * 在指定库中执行sInterStore命令，将返回的值交集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sInterStore(String dbName, T destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sInterStore命令，将返回的值交集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sInterStore(T destKey, K[] keys);
	
	/**
	 * 在当前库中执行sInterStore命令，将返回的值交集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sInterStore(T destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在指定库中执行sInterStore命令，将返回的值交集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sInterStore(String dbName, T destKey, K[] keys);
	
	/**
	 * 在指定库中执行sInterStore命令，将返回的值交集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sInterStore(String dbName, T destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sUnion命令，获取多个键集之间的值并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sUnion(Collection<K> keys);
	
	/**
	 * 在当前库中执行sUnion命令，获取多个键集之间的值并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sUnion(Collection<K> keys, Class<V> valueType);
	
	/**
	 * 在指定库中执行sUnion命令，获取多个键集之间的值并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sUnion(String dbName, Collection<K> keys);
	
	/**
	 * 在指定库中执行sUnion命令，获取多个键集之间的值并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sUnion(String dbName, Collection<K> keys, Class<V> valueType);
	
	/**
	 * 在当前库中执行sUnion命令，获取多个键集之间的值并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sUnion(K[] keys);
	
	/**
	 * 在当前库中执行sUnion命令，获取多个键集之间的值并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sUnion(K[] keys, Class<V> valueType);
	
	/**
	 * 在指定库中执行sUnion命令，获取多个键集之间的值并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sUnion(String dbName, K[] keys);
	
	/**
	 * 在指定库中执行sUnion命令，获取多个键集之间的值并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sUnion(String dbName, K[] keys, Class<V> valueType);
	
	/**
	 * 在当前库中执行sUnionStore命令，将返回的并集值存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sUnionStore(T destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行sUnionStore命令，将返回的并集值存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sUnionStore(T destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定库中执行sUnionStore命令，将返回的并集值存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sUnionStore(String dbName, T destKey, Collection<K> keys);
	
	/**
	 * 在指定库中执行sUnionStore命令，将返回的并集值存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sUnionStore(String dbName, T destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sUnionStore命令，将返回的并集值存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sUnionStore(T destKey, K[] keys);
	
	/**
	 * 在当前库中执行sUnionStore命令，将返回的并集值存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sUnionStore(T destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在指定库中执行sUnionStore命令，将返回的并集值存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <T, K> Long sUnionStore(String dbName, T destKey, K[] keys);
	
	/**
	 * 在指定库中执行sUnionStore命令，将返回的并集值存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <T, K> Long sUnionStore(String dbName, T destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sIsMember命令，判断集合键中是否存在指定的成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Boolean sIsMember(K key, V member);
	
	/**
	 * 在指定库中执行sIsMember命令，判断集合键中是否存在指定的成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Boolean sIsMember(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行sMembers命令，获取集合键对应的所有成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> Set<V> sMembers(K key);
	
	/**
	 * 在当前库中执行sMembers命令，获取集合键对应的所有指定类型的成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sMembers(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行sMembers命令，获取集合键对应的所有成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> Set<V> sMembersIn(String dbName, K key);
	
	/**
	 * 在指定库中执行sMembers命令，获取集合键对应的所有指定类型的成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> Set<V> sMembers(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行sMove命令，将源集合键指定的成员移到目标集合键中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param srcKey
	 * @param destKey
	 * @param member
	 * @return
	 */
	public <K, T, V> Boolean sMove(K srcKey, T destKey, V member);
	
	/**
	 * 在当前库中执行sMove命令，将源集合键指定的成员移到目标集合键中，并设置目标键的过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param srcKey
	 * @param destKey
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, T, V> Boolean sMove(K srcKey, T destKey, V member, long expireSeconds);
	
	/**
	 * 在指定库中执行sMove命令，将源集合键指定的成员移到目标集合键中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param srcKey
	 * @param destKey
	 * @param member
	 * @return
	 */
	public <K, T, V> Boolean sMoveIn(String dbName, K srcKey, T destKey, V member);
	
	/**
	 * 在指定库中执行sMove命令，将源集合键指定的成员移到目标集合键中，并设置目标键的过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param srcKey
	 * @param destKey
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, T, V> Boolean sMove(String dbName, K srcKey, T destKey, V member, long expireSeconds);
	
	/**
	 * 在当前库中执行sPop命令，随机出列指定键集合中的一个成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> V sPop(K key);
	
	/**
	 * 在当前库中执行sPop命令，随机出列指定键集合中的一个成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V sPop(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行sPop命令，随机出列指定键集合中的一个成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> V sPopIn(String dbName, K key);
	
	/**
	 * 在指定库中执行sPop命令，随机出列指定键集合中的一个成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V sPop(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行sRandMember命令，随机获取键集合中的一个成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> V sRandMember(K key);
	
	/**
	 * 在当前库中执行sRandMember命令，随机获取键集合中的一个成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V sRandMember(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行sRandMember命令，随机获取键集合中的一个成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> V sRandMemberIn(String dbName, K key);
	
	/**
	 * 在指定库中执行sRandMember命令，随机获取键集合中的一个成员值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V sRandMember(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行sRem命令，删除集合键中指定的一个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sRem(K key, V member);
	
	/**
	 * 在指定库中执行sRem命令，删除集合键中指定的一个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sRem(String dbName, K key, V member);
	
	/**
	 * 在当前库中执行sRem命令，删除集合键中指定的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sRem(K key, V[] members);
	
	/**
	 * 在指定库中执行sRem命令，删除集合键中指定的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sRem(String dbName, K key, V[] members);
	
	/**
	 * 在当前库中执行sRem命令，删除集合键中指定的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sRem(K key, Collection<V> members);
	
	/**
	 * 在指定库中执行sRem命令，删除集合键中指定的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sRem(String dbName, K key, Collection<V> members);
	
	/**
	 * 在当前库中执行sscan命令行，以增量迭代的方式获取所有的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(K key);
	
	/**
	 * 在当前库中执行sscan命令行，以增量迭代的方式获取所有指定类型的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行sscan命令行，从指定的游标处开始，以增量迭代的方式获取所有的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param cursorId
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(K key, long cursorId);
	
	/**
	 * 在当前库中执行sscan命令行，从指定的游标处开始，以增量迭代的方式获取所有指定类型的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param cursorId
	 * @param valueType
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(K key, long cursorId, Class<V> valueType);
	
	/**
	 * 在当前库中执行sscan命令行，以增量迭代的方式获取所有的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param option
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(K key, ScanOption option);
	
	/**
	 * 在当前库中执行sscan命令行，以增量迭代的方式获取所有指定类型的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param option
	 * @param valueType
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(K key, ScanOption option, Class<V> valueType);
	
	/**
	 * 在当前库中执行sscan命令行，从指定的游标处开始，以增量迭代的方式获取所有的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param cursorId
	 * @param option
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(K key, long cursorId, ScanOption option);
	
	/**
	 * 在当前库中执行sscan命令行，从指定的游标处开始，以增量迭代的方式获取所有指定类型的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param cursorId
	 * @param option
	 * @param valueType
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(K key, long cursorId, ScanOption option, Class<V> valueType);
	
	/**
	 * 在指定库中执行sscan命令行，以增量迭代的方式获取所有的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscanIn(String dbName, K key);
	
	/**
	 * 在指定库中执行sscan命令行，以增量迭代的方式获取所有指定类型的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行sscan命令行，从指定的游标处开始，以增量迭代的方式获取所有的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(String dbName, K key, long cursorId);
	
	/**
	 * 在指定库中执行sscan命令行，从指定的游标处开始，以增量迭代的方式获取所有指定类型的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @param valueType
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(String dbName, K key, long cursorId, Class<V> valueType);
	
	/**
	 * 在指定库中执行sscan命令行，以增量迭代的方式获取所有的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param option
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(String dbName, K key, ScanOption option);
	
	/**
	 * 在指定库中执行sscan命令行，以增量迭代的方式获取所有指定类型的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param option
	 * @param valueType
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(String dbName, K key, ScanOption option, Class<V> valueType);
	
	/**
	 * 在指定库中执行sscan命令行，从指定的游标处开始，以增量迭代的方式获取所有的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @param option
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(String dbName, K key, long cursorId, ScanOption option);
	
	/**
	 * 在指定库中执行sscan命令行，从指定的游标处开始，以增量迭代的方式获取所有指定类型的成员结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param cursorId
	 * @param option
	 * @param valueType
	 * @return
	 */
	public <K, V> IndexedScanResult<V> sscan(String dbName, K key, long cursorId, ScanOption option, Class<V> valueType);
	
}
