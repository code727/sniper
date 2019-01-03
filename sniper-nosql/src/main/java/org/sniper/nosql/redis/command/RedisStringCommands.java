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

package org.sniper.nosql.redis.command;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Redis字符串命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisStringCommands {
	
	/**
	 * 在当前库中执行set命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param value 值
	 */
	public <K,V> void set(K key, V value);
	
	/**
	 * 在当前库中执行set命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 */
	public <K,V> void set(K key, V value, long expireSeconds);
	
	/**
	 * 在指定库中执行set命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> void setIn(String dbName, K key, V value);
	
	/**
	 * 在指定库中执行set命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireSeconds
	 */
	public <K, V> void set(String dbName, K key, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行setNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> Boolean setNX(K key, V value);
	
	/**
	 *  在当前库中执行setNX命令，并设置过期毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireMillis
	 * @return
	 */
	public <K, V> Boolean setNX(K key, V value, long expireMillis);
	
	/**
	 * 在指定库中执行setNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key 键
	 * @param value 值
	 */
	public <K, V> Boolean setNXIn(String dbName, K key, V value);
	
	/**
	 * 在指定库中执行setNX命令，并设置过期毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireMillis
	 * @return
	 */
	public <K, V> Boolean setNX(String dbName, K key, V value, long expireMillis);
	
	/**
	 * 在当前库中执行setEx命令，并设置当前库全局过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 */
	public <K, V> void setEx(K key, V value);
	
	/**
	 * 在当前库中执行setEx命令，并设置当前库过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public <K, V> void setEx(K key, long seconds, V value);
	
	/**
	 * 在指定库中执行setEx命令，并设置当前库全局过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 */
	public <K, V> void setExIn(String dbName, K key, V value);
	
	/**
	 * 在指定库中执行setEx命令，并设置当前库过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public <K, V> void setEx(String dbName, K key, long seconds, V value);
	
	/**
	 * 在当前库中执行mSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValues
	 */
	public <K, V> void mSet(Map<K, V> kValues);
	
	/**
	 * 在当前库中执行mSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValues
	 * @param expireSeconds
	 */
	public <K, V> void mSet(Map<K, V> kValues, long expireSeconds);
	
	/**
	 * 在指定库中执行mSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param kValues
	 */
	public <K, V> void mSet(String dbName, Map<K, V> kValues);
	
	/**
	 * 在指定库中执行mSet命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param kValues
	 * @param expireSeconds
	 */
	public <K, V> void mSet(String dbName, Map<K, V> kValues, long expireSeconds);

	/**
	 * 在当前库中执行mSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValus
	 */
	public <K, V> void mSetNX(Map<K, V> kValues);
	
	/**
	 * 在当前库中执行mSetNX命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param kValues
	 * @param expireSeconds
	 */
	public <K, V> void mSetNX(Map<K, V> kValues, long expireSeconds);
	
	/**
	 * 在指定库中执行mSetNX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param kValues
	 */
	public <K, V> void mSetNX(String dbName, Map<K, V> kValues);
	
	/**
	 * 在指定库中执行mSetNX命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param kValues
	 * @param expireSeconds
	 */
	public <K, V> void mSetNX(String dbName, Map<K, V> kValues, long expireSeconds);
	
	/**
	 * 在当前库中执行setRange命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param offset
	 * @param value
	 */
	public <K, V> void setRange(K key, long offset, V value);
	
	/**
	 * 在当前库中执行setRange命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param offset
	 * @param value
	 * @param expireSeconds
	 */
	public <K, V> void setRange(K key, long offset, V value, long expireSeconds);
	
	/**
	 * 在指定库中执行setRange命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param offset
	 * @param value
	 */
	public <K, V> void setRangeIn(String dbName, K key, long offset, V value);
	
	/**
	 * 在指定库中执行setRange命令，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param offset
	 * @param value
	 * @param expireSeconds
	 */
	public <K, V> void setRange(String dbName, K key, long offset, V value, long expireSeconds);
	
	/**
	 * 将值追加到当前库键的原值后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long append(K key, V value);
	
	/**
	 * 将值追加到当前库键的原值后面，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long append(K key, V value, long expireSeconds);
	
	/**
	 * 将值追加到指定库键的原值后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long appendIn(String dbName, K key, V value);
	
	/**
	 * 将值追加到指定库键的原值后面，并设置过期秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V> Long append(String dbName, K key, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行get命令，获取指定键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K, V> V get(K key);
	
	/**
	 * 在当前库中执行get命令，获取指定键对应类型的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V get(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行get命令，获取指定键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> V getIn(String dbName, K key);
	
	/**
	 * 在指定库中执行get命令，获取指定键对应类型的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> V get(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行getRange命令，获取begin至end之间的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> V getRange(K key, long begin, long end);
	
	/**
	 * 在当前库中执行getRange命令，获取begin至end之间的类型值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param begin
	 * @param end
	 * @param valueType
	 * @return
	 */
	public <K, V> V getRange(K key, long begin, long end, Class<V> valueType);
	
	/**
	 * 在指定库中执行getRange命令，获取begin至end之间的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	public <K, V> V getRange(String dbName, K key, long begin, long end);
	
	/**
	 * 在指定库中执行getRange命令，获取begin至end之间的类型值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param begin
	 * @param end
	 * @param valueType
	 * @return
	 */
	public <K, V> V getRange(String dbName, K key, long begin, long end, Class<V> valueType);
	
	/**
	 * 在当前库中执行getSet命令，设置键值对后返回键对应的旧值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return 
	 */
	public <K, V, O> O getSet(K key, V value);
	
	/**
	 * 在当前库中执行getSet命令，设置键值对后返回键对应类型的旧值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param oldValueType
	 * @return
	 */
	public <K, V, O> O getSet(K key, V value, Class<O> oldValueType);
	
	/**
	 * 在当前库中执行getSet命令，设置具有时效的键值对后返回键对应的旧值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V, O> O getSet(K key, V value, long expireSeconds);
	
	/**
	 * 在当前库中执行getSet命令，设置具有时效的键值对后返回键对应类型的旧值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @param oldValueType
	 * @return
	 */
	public <K, V, O> O getSet(K key, V value, long expireSeconds, Class<O> oldValueType);
	
	/**
	 * 在指定库中执行getSet命令，设置键值对后返回键对应的旧值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return 
	 */
	public <K, V, O> O getSetIn(String dbName, K key, V value);
	
	/**
	 * 在指定库中执行getSet命令，设置键值对后返回键对应类型的旧值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param oldValueType
	 * @return
	 */
	public <K, V, O> O getSetIn(String dbName, K key, V value, Class<O> oldValueType);
	
	/**
	 * 在指定库中执行getSet命令，设置具有时效的键值对后返回键对应的旧值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return
	 */
	public <K, V, O> O getSet(String dbName, K key, V value, long expireSeconds);
	
	/**
	 * 在指定库中执行getSet命令，设置具有时效的键值对后返回键对应类型的旧值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @param oldValueType
	 * @return
	 */
	public <K, V, O> O getSet(String dbName, K key, V value, long expireSeconds, Class<O> oldValueType);
	
	/**
	 * 在当前库中执行mGet命令，批量获取多个键对应的值列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> List<V> mGet(Collection<K> keys);
	
	/**
	 * 在当前库中执行mGet命令，批量获取多个键对应类型的值列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> mGet(Collection<K> keys, Class<V> valueType);
	
	/**
	 * 在指定库中执行mGet命令，批量获取多个键对应的值列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K, V> List<V> mGet(String dbName, Collection<K> keys);
	
	/**
	 * 在指定库中执行mGet命令，批量获取多个键对应的值列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> mGet(String dbName, Collection<K> keys, Class<V> valueType);
	
	/**
	 * 在当前库中执行mGet命令，批量获取多个键对应的值列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @return
	 */
	public <K, V> List<V> mGet(K[] keys);
	
	/**
	 * 在当前库中执行mGet命令，批量获取多个键对应类型的值列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> mGet(K[] keys, Class<V> valueType);
	
	/**
	 * 在指定库中执行mGet命令，批量获取多个键对应的值列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K, V> List<V> mGet(String dbName, K[] keys);
	
	/**
	 * 在指定库中执行mGet命令，批量获取多个键对应类型的值列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param keys
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> mGet(String dbName, K[] keys, Class<V> valueType);
	
	/**
	 * 在当前库中执行strLen命令，获取键对应的字符串值的长度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long strLen(K key);
	
	/**
	 * 在指定库中执行getSet命令，获取键对应的字符串值的长度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long strLen(String dbName, K key);
	
	/**
	 * 在当前库中执行decr命令，将键储存的数字值减一
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long decr(K key);
	
	/**
	 * 在指定库中执行decr命令，将键储存的数字值减一
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long decr(String dbName, K key);
	
	/**
	 * 在当前库中执行decrBy命令，将键储存的数字值减去指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K> Long decrBy(K key, long value);
	
	/**
	 * 在指定库中执行decrBy命令，将键储存的数字值减去指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return
	 */
	public <K> Long decrBy(String dbName, K key, long value);
	
	/**
	 * 在当前库中执行incr命令，将键储存的数字值加一
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long incr(K key);
	
	/**
	 * 在指定库中执行incr命令，将键储存的数字值加一
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long incr(String dbName, K key);
	
	/**
	 * 在当前库中执行incrBy命令，将键储存的数字值加上指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K> Long incrBy(K key, long value);
	
	/**
	 * 在指定库中执行incrBy命令，将键储存的数字值加上指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param value
	 * @return
	 */
	public <K> Long incrBy(String dbName, K key, long value);
	
}
