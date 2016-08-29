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

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @description Redis字符串命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisStringCommands {
	
	/**
	 * @description 在当前库中执行set命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param value 值
	 */
	public <K,V> void set(K key, V value);
	
	/**
	 * @description 在当前库中执行set命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 */
	public <K,V> void set(K key, V value, long expireSeconds);
	
	/**
	 * @description 在指定索引库中执行set命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex 库索引
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> void set(int dbIndex, K key, V value);
	
	/**
	 * @description 在指定索引库中执行set命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @param expireSeconds
	 */
	public <K, V> void set(int dbIndex, K key, V value, long expireSeconds);
	
	/**
	 * @description 在当前库中执行setNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> Boolean setNX(K key, V value);
	
	/**
	 * @description  在当前库中执行setNX命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Boolean setNX(K key, V value, long expireSeconds);
	
	/**
	 * @description 在指定索引库中执行setNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex 库索引
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> Boolean setNX(int dbIndex, K key, V value);
	
	/**
	 * @description 在指定索引库中执行setNX命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Boolean setNX(int dbIndex, K key, V value, long expireSeconds);
	
	/**
	 * @description 在当前库中执行setEx命令，并设置当前库全局过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 */
	public <K, V> void setEx(K key, V value);
	
	/**
	 * @description 在指定索引库中执行setEx命令，并设置当前库全局过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 */
	public <K, V> void setEx(int dbIndex, K key, V value);
	
	/**
	 * @description 在当前库中执行setEx命令，并设置当前库过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public <K, V> void setEx(K key, long seconds, V value);
	
	/**
	 * @description 在指定索引库中执行setEx命令，并设置当前库过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public <K, V> void setEx(int dbIndex, K key, long seconds, V value);
	
	/**
	 * @description 在当前库中执行mSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValues
	 */
	public <K, V> void mSet(Map<K, V> kValues);
	
	/**
	 * @description 在当前库中执行mSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValues
	 * @param expireSeconds
	 */
	public <K, V> void mSet(Map<K, V> kValues, long expireSeconds);
	
	/**
	 * @description 在指定索引库中执行mSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param kValues
	 */
	public <K, V> void mSet(int dbIndex, Map<K, V> kValues);
	
	/**
	 * @description 在指定索引库中执行mSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param kValues
	 * @param expireSeconds
	 */
	public <K, V> void mSet(int dbIndex, Map<K, V> kValues, long expireSeconds);

	/**
	 * @description 在当前库中执行mSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValus
	 */
	public <K, V> void mSetNX(Map<K, V> kValues);
	
	/**
	 * @description 在当前库中执行mSetNX命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValues
	 * @param expireSeconds
	 */
	public <K, V> void mSetNX(Map<K, V> kValues, long expireSeconds);
	
	/**
	 * @description 在指定索引库中执行mSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param kValues
	 */
	public <K, V> void mSetNX(int dbIndex, Map<K, V> kValues);
	
	/**
	 * @description 在指定索引库中执行mSetNX命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param kValues
	 * @param expireSeconds
	 */
	public <K, V> void mSetNX(int dbIndex, Map<K, V> kValues, long expireSeconds);
	
	/**
	 * @description 在当前库中执行setRange命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param offset
	 * @param value
	 */
	public <K, V> void setRange(K key, long offset, V value);
	
	/**
	 * @description 在当前库中执行setRange命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param offset
	 * @param value
	 * @param expireSeconds
	 */
	public <K, V> void setRange(K key, long offset, V value, long expireSeconds);
	
	/**
	 * @description 在指定索引库中执行setRange命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param offset
	 * @param value
	 */
	public <K, V> void setRange(int dbIndex, K key, long offset, V value);
	
	/**
	 * @description 在指定索引库中执行setRange命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param offset
	 * @param value
	 * @param expireSeconds
	 */
	public <K, V> void setRange(int dbIndex, K key, long offset, V value, long expireSeconds);
	
	/**
	 * @description 将值追加到当前库键的原值后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long append(K key, V value);
	
	/**
	 * @description 将值追加到当前库键的原值后面，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long append(K key, V value, long expireSeconds);
	
	/**
	 * @description 将值追加到指定索引库键的原值后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long append(int dbIndex, K key, V value);
	
	/**
	 * @description 将值追加到指定索引库键的原值后面，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long append(int dbIndex, K key, V value, long expireSeconds);
	
	/**
	 * @description 在当前库中执行get命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> V get(K key);
	
	/**
	 * @description 在指定索引库中执行get命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K, V> V get(int dbIndex, K key);
	
	/**
	 * @description 在当前库中执行getRange命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> V getRange(K key, long begin, long end);
	
	/**
	 * @description 在指定索引库中执行getRange命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> V getRange(int dbIndex, K key, long begin, long end);
	
	/**
	 * @description 在当前库中执行getSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return 键对应的旧值
	 */
	public <K, V> V getSet(K key, V value);
	
	/**
	 * @description 在当前库中执行getSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> V getSet(K key, V value, long expireSeconds);
	
	/**
	 * @description 在指定索引库中执行getSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @return 键对应的旧值
	 */
	public <K, V> V getSet(int dbIndex, K key, V value);
	
	/**
	 * @description 在指定索引库中执行getSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> V getSet(int dbIndex, K key, V value, long expireSeconds);
	
	/**
	 * @description 在当前库中执行mGet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> List<V> mGet(K[] keys);
	
	/**
	 * @description 在指定索引库中执行mGet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K, V> List<V> mGet(int dbIndex, K[] keys);
	
	/**
	 * @description 在当前库中执行mGet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> List<V> mGet(Collection<K> keys);
	
	/**
	 * @description 在指定索引库中执行mGet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param keys
	 * @return
	 */
	public <K, V> List<V> mGet(int dbIndex, Collection<K> keys);
	
	/**
	 * @description 在当前库中执行strLen命令，获取键对应的字符串值的长度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long strLen(K key);
	
	/**
	 * @description 在指定索引库中执行getSet命令，获取键对应的字符串值的长度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Long strLen(int dbIndex, K key);
	
	/**
	 * @description 在当前库中执行decr命令，将键储存的数字值减一
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long decr(K key);
	
	/**
	 * @description 在指定索引库中执行decr命令，将键储存的数字值减一
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Long decr(int dbIndex, K key);
	
	/**
	 * @description 在当前库中执行decrBy命令，将键储存的数字值减去指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K> Long decrBy(K key, long value);
	
	/**
	 * @description 在指定索引库中执行decrBy命令，将键储存的数字值减去指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @return
	 */
	public <K> Long decrBy(int dbIndex, K key, long value);
	
	/**
	 * @description 在当前库中执行incr命令，将键储存的数字值加一
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long incr(K key);
	
	/**
	 * @description 在指定索引库中执行incr命令，将键储存的数字值加一
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Long incr(int dbIndex, K key);
	
	/**
	 * @description 在当前库中执行incrBy命令，将键储存的数字值加上指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K> Long incrBy(K key, long value);
	
	/**
	 * @description 在指定索引库中执行incrBy命令，将键储存的数字值加上指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param value
	 * @return
	 */
	public <K> Long incrBy(int dbIndex, K key, long value);
	
}
