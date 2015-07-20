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
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.SortParameters;

/**
 * @description Redis键命令行数据访问接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisKeyCommandsDao {
	
	/**
	 * @description 获取当前库中所有的键
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
	 * @description 获取当前库中指定模式的键集
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
	 * @description 删除当前库指定的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long del(K key);
	
	/**
	 * @description 删除指定库的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Long del(int dbIndex, K key);
	
	/**
	 * @description 删除当前库指定的多个键
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
	 * @description 删除当前库指定的多个键
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
	 * @description 判断当前库中指定键是否存在
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
	 * @description 设置当前库中指定键的过期秒数
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
	
	/**
	 * @description 设置当前库中指定键的过期时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public <K> Boolean expireAt(K key, long timestamp); 
	
	/**
	 * @description 设置指定库中键的过期时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public <K> Boolean expireAt(int dbIndex, K key, long timestamp); 
	
	/**
	 * @description 设置当前库中指定键的过期日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean expireAt(K key, Date date); 
	
	/**
	 * @description 设置指定库中键的过期时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean expireAt(int dbIndex, K key, Date date); 
		
	/**
	 * @description 将当前库的键移动到目标库
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param targetIndex
	 * @return
	 */
	public <K> Boolean move(K key, int targetIndex);
	
	/**
	 * @description 将指定库中的键移动到目标库
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param targetIndex
	 * @return
	 */
	public <K> Boolean move(int dbIndex, K key, int targetIndex);
	
	/**
	 * @description 获取当前库的指定键的剩余秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> Long ttl(K key);
	
	/**
	 * @description 获取指定库中的键的剩余秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> Long ttl(int dbIndex, K key);
	
	/**
	 * @description 将当前库的键按照指定的规则进行排序
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @return
	 */
	public <K, V> List<V> sort(K key, SortParameters params);
	
	/**
	 * @description 将指定库的键按照规则进行排序
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @return
	 */
	public <K, V> List<V> sort(int dbIndex, K key, SortParameters params);
	
	/**
	 * @description 将当前库的键按照指定的规则进行排序后返回存入目标键的结果个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 
	 */
	public <K, V> Long sortCount(K key, SortParameters params, K targetKey);
	
	/**
	 * @description 将指定库的键按照规则进行排序后返回存入目标键的结果个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 存入目标键的结果个数
	 */
	public <K, V> Long sortCount(int dbIndex, K key, SortParameters params, K targetKey);
	
	/**
	 * @description 将当前库的键按照指定的规则进行排序后返回存入目标键的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 
	 */
	public <K, V> List<V> sortResult(K key, SortParameters params, K targetKey);
	
	/**
	 * @description 将指定库的键按照规则进行排序后返回存入目标键的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 存入目标键的结果个数
	 */
	public <K, V> List<V> sortResult(int dbIndex, K key, SortParameters params, K targetKey);
	
	/**
	 * @description 获取当前库的键对应的值类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> DataType type(K key);
	
	/**
	 * @description 获取指定库的键对应的值类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> DataType type(int dbIndex, K key);
	
	/**
	 * @description 获取当前库中所有键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public <V> Set<V> values();
	
	/**
	 * @description 获取指定库中所有键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @return
	 */
	public <V> Set<V> values(int dbIndex);
	
	/**
	 * @description 获取当前库中匹配模式的键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
	public <V> Set<V> values(String pattern);
	
	/**
	 * @description 获取指定库中匹配模式的键对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param pattern
	 * @return
	 */
	public <V> Set<V> values(int dbIndex, String pattern);
	
}
