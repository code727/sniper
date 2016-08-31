/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-8-29
 */

package org.workin.nosql.redis.spring;

import java.util.List;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.SortParameters;
import org.workin.nosql.redis.dao.RedisKeyCommands;

/**
 * Spring Redis键命令接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SpringRedisKeyCommands extends RedisKeyCommands {
	
	/**
	 * 将当前库的键按照指定的规则进行排序
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @return
	 */
	public <K, V> List<V> sort(K key, SortParameters params);
	
	/**
	 * 将指定库的键按照规则进行排序
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @return
	 */
	public <K, V> List<V> sort(int dbIndex, K key, SortParameters params);
	
	/**
	 * 将当前库的键按照指定的规则进行排序后返回存入目标键的结果个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 
	 */
	public <K, V> Long sortCount(K key, SortParameters params, K targetKey);
	
	/**
	 * 将指定库的键按照规则进行排序后返回存入目标键的结果个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 存入目标键的结果个数
	 */
	public <K, V> Long sortCount(int dbIndex, K key, SortParameters params, K targetKey);
	
	/**
	 * 将当前库的键按照指定的规则进行排序后返回存入目标键的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 
	 */
	public <K, V> List<V> sortResult(K key, SortParameters params, K targetKey);
	
	/**
	 * 将指定库的键按照规则进行排序后返回存入目标键的结果列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @param params
	 * @param targetKey
	 * @return 存入目标键的结果个数
	 */
	public <K, V> List<V> sortResult(int dbIndex, K key, SortParameters params, K targetKey);
	
	/**
	 * 获取当前库的键对应的值类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public <K> DataType type(K key);
	
	/**
	 * 获取指定库的键对应的值类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbIndex
	 * @param key
	 * @return
	 */
	public <K> DataType type(int dbIndex, K key);

}
