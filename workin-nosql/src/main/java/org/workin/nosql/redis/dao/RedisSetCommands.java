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
import java.util.Set;

/**
 * Redis集合命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisSetCommands {
	
	/**
	 * 在当前库中执行sAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sAdd(K key, V member);
	
	/**
	 * 在当前库中执行sAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(K key, V member, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sAdd(int dbIndex, K key, V member);
	
	/**
	 * 在指定索引库中执行sAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param member
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(int dbIndex, K key, V member, long expireSeconds);
	
	/**
	 * 在当前库中执行sAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sAdd(K key, V[] members);
	
	/**
	 * 在当前库中执行sAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(K key, V[] members, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sAdd(int dbIndex, K key, V[] members);
	
	/**
	 * 在指定索引库中执行sAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param members
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(int dbIndex, K key, V[] members, long expireSeconds);
	
	/**
	 * 在当前库中执行sAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param memberss
	 * @return
	 */
	public <K, V> Long sAdd(K key, Collection<V> members);
	
	/**
	 * 在当前库中执行sAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(K key, Collection<V> members, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sAdd命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sAdd(int dbIndex, K key, Collection<V> members);
	
	/**
	 * 在指定索引库中执行sAdd命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param members
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long sAdd(int dbIndex, K key, Collection<V> members, long expireSeconds);
	
	/**
	 * 在当前库中执行sCard命令，获取集合键对应的元素个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long sCard(K key);
	
	/**
	 * 在指定索引库中执行sCard命令，获取集合键对应的元素个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Long sCard(int dbIndex, K key);
	
	/**
	 * 在当前库中执行sDiff命令，获取多个键集之间的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sDiff(K[] keys);
	
	/**
	 * 在指定索引库中执行sDiff命令，获取多个键集之间的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sDiff(int dbIndex, K[] keys);
	
	/**
	 * 在当前库中执行sDiff命令，获取多个键集之间的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sDiff(Collection<K> keys);
	
	/**
	 * 在指定索引库中执行sDiff命令，获取多个键集之间的差集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sDiff(int dbIndex, Collection<K> keys);
	
	/**
	 * 在当前库中执行sDiffStore命令，将返回的差集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sDiffStore(K destKey, K[] keys);
	
	/**
	 * 在当前库中执行sDiffStore命令，
	 * 				将返回的差集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sDiffStore(K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sDiffStore命令，将返回的差集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sDiffStore(int dbIndex, K destKey, K[] keys);
	
	/**
	 * 在指定索引库中执行sDiffStore命令，
	 * 				将返回的差集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sDiffStore(int dbIndex, K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sDiffStore命令，将返回的差集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sDiffStore(K destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行sDiffStore命令，
	 * 				将返回的差集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sDiffStore(K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sDiffStore命令，将返回的差集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sDiffStore(int dbIndex, K destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行sDiffStore命令，
	 * 				将返回的差集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sDiffStore(int dbIndex, K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sInter命令，获取多个键集之间的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sInter(K[] keys);
	
	/**
	 * 在指定索引库中执行sInter命令，获取多个键集之间的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sInter(int dbIndex, K[] keys);
	
	/**
	 * 在当前库中执行sInter命令，获取多个键集之间的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sInter(Collection<K> keys);
	
	/**
	 * 在指定索引库中执行sInter命令，获取多个键集之间的交集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sInter(int dbIndex, Collection<K> keys);
	
	/**
	 * 在当前库中执行sInterStore命令，将返回的交集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sInterStore(K destKey, K[] keys);
	
	/**
	 * 在当前库中执行sInterStore命令，
	 * 				将返回的交集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sInterStore(K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sInterStore命令，将返回的交集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sInterStore(int dbIndex, K destKey, K[] keys);
	
	/**
	 * 在指定索引库中执行sInterStore命令，
	 * 				将返回的交集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sInterStore(int dbIndex, K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sInterStore命令，将返回的交集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sInterStore(K destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行sInterStore命令，
	 * 				将返回的交集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sInterStore(K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sInterStore命令，将返回的交集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sInterStore(int dbIndex, K destKey, Collection<K> keys);
	
	/**
	 * 在指定索引库中执行sInterStore命令，
	 * 				将返回的交集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sInterStore(int dbIndex, K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sUnion命令，获取多个键集之间的并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sUnion(K[] keys);
	
	/**
	 * 在指定索引库中执行sUnion命令，获取多个键集之间的并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sUnion(int dbIndex, K[] keys);
	
	/**
	 * 在当前库中执行sInter命令，获取多个键集之间的并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sUnion(Collection<K> keys);
	
	/**
	 * 在指定索引库中执行sInter命令，获取多个键集之间的并集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K, V> Set<V> sUnion(int dbIndex, Collection<K> keys);
	
	/**
	 * 在当前库中执行sUnionStore命令，将返回的并集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sUnionStore(K destKey, K[] keys);
	
	/**
	 * 在当前库中执行sUnionStore命令，
	 * 				将返回的并集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sUnionStore(K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sUnionStore命令，将返回的并集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sUnionStore(int dbIndex, K destKey, K[] keys);
	
	/**
	 * 在指定索引库中执行sUnionStore命令，
	 * 				将返回的并集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sUnionStore(int dbIndex, K destKey, K[] keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sUnionStore命令，将返回的并集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sUnionStore(K destKey, Collection<K> keys);
	
	/**
	 * 在当前库中执行sUnionStore命令，
	 * 				将返回的并集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sUnionStore(K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在指定索引库中执行sUnionStore命令，将返回的并集存入指定的目标键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @return
	 */
	public <K> Long sUnionStore(int dbIndex, K destKey, Collection<K> keys);
	
	/**
	 * 在指定索引库中执行sUnionStore命令，
	 * 				将返回的并集存入指定的目标键，并设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param destKey
	 * @param keys
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sUnionStore(int dbIndex, K destKey, Collection<K> keys, long expireSeconds);
	
	/**
	 * 在当前库中执行sIsMember命令，判断集合键中是否存在指定的成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Boolean sIsMember(K key, V member);
	
	/**
	 * 在指定索引库中执行sIsMember命令，判断集合键中是否存在指定的成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Boolean sIsMember(int dbIndex, K key, V member);
	
	/**
	 * 在当前库中执行sMembers命令，获取集合键对应的所有成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> Set<V> sMembers(K key);
	
	/**
	 * 在指定索引库中执行sMembers命令，获取集合键对应的所有成员元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K, V> Set<V> sMembers(int dbIndex, K key);
	
	/**
	 * 在当前库中执行sMove命令，将源集合键指定的成员移到目标集合键中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param srcKey
	 * @param destKey
	 * @param member
	 * @return
	 */
	public <K, V> Boolean sMove(K srcKey, K destKey, V member);
	
	/**
	 * 在指定索引库中执行sMove命令，将源集合键指定的成员移到目标集合键中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param srcKey
	 * @param destKey
	 * @param member
	 * @return
	 */
	public <K, V> Boolean sMove(int dbIndex, K srcKey, K destKey, V member);
	
	/**
	 * 在当前库中执行sPop命令，删除并返回集合键中的一个随机成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> V sPop(K key);
	
	/**
	 * 在指定索引库中执行sPop命令，删除并返回集合键中的一个随机成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K, V> V sPop(int dbIndex, K key);
	
	/**
	 * 在当前库中执行sRandMember命令，获取集合键中的一个随机成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> V sRandMember(K key);
	
	/**
	 * 在指定索引库中执行sRandMember命令，获取集合键中的一个随机成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K, V> V sRandMember(int dbIndex, K key);
	
	/**
	 * 在当前库中执行sRem命令，删除集合键中指定的一个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sRem(K key, V member);
	
	/**
	 * 在指定索引库中执行sRem命令，删除集合键中指定的一个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, V> Long sRem(int dbIndex, K key, V member);
	
	/**
	 * 在当前库中执行sRem命令，删除集合键中指定的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sRem(K key, V[] members);
	
	/**
	 * 在指定索引库中执行sRem命令，删除集合键中指定的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sRem(int dbIndex, K key, V[] members);
	
	/**
	 * 在当前库中执行sRem命令，删除集合键中指定的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sRem(K key, Collection<V> members);
	
	/**
	 * 在指定索引库中执行sRem命令，删除集合键中指定的多个成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, V> Long sRem(int dbIndex, K key, Collection<V> members);
	
}
