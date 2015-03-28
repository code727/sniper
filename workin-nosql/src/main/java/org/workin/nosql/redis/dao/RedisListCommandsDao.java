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

import org.springframework.data.redis.connection.RedisListCommands.Position;

/**
 * @description Redis列表命令行数据访问接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisListCommandsDao {
	
	/**
	 * @description 在默认第0库中执行lInsert命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param where
	 * @param pivot 位置值
	 * @param value
	 * @return
	 */
	public <K, V> Long lInsert(K key, Position where, V pivot, V value);
	
	/**
	 * @description 在指定索引库中执行lInsert命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param where
	 * @param pivot 位置值
	 * @param value
	 * @return
	 */
	public <K, V> Long lInsert(int index, K key, Position where, V pivot, V value);
	
	/**
	 * @description 在默认第0库中执行lSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param posttion
	 * @param value
	 */
	public <K, V> void lSet(K key, long posttion, V value);
	
	/**
	 * @description 在指定索引库中执行lSet命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param posttion
	 * @param value
	 */
	public <K, V> void lSet(int index, K key, long posttion, V value);
	
	/**
	 * @description 在默认第0库中执行lPush命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lPush(K key, V value);
	
	/**
	 * @description 在指定索引库中执行lPushX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lPush(int index, K key, V value);
	
	/**
	 * @description 在默认第0库中批量执行lPush命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long lPush(K key, Collection<V> values);
	
	/**
	 * @description 在指定索引库中批量执行lPushX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long lPush(int index, K key, Collection<V> values);
	
	/**
	 * @description 在默认第0库中执行lPushX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lPushX(K key, V value);
	
	/**
	 * @description 在指定索引库中执行lPushX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long lPushX(int index, K key, V value);
	
	/**
	 * @description 在默认第0库中执行rPush命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long rPush(K key, V value);
	
	/**
	 * @description 在指定索引库中执行lPush命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long rPush(int index, K key, V value);
	
	/**
	 * @description 在默认第0库中批量执行rPush命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long rPush(K key, Collection<V> values);
	
	/**
	 * @description 在指定索引库中批量执行lPushX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param values
	 * @return
	 */
	public <K, V> Long rPush(int index, K key, Collection<V> values);
	
	/**
	 * @description 在默认第0库中执行rPushX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long rPushX(K key, V value);
	
	/**
	 * @description 在指定索引库中执行lPushX命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param key
	 * @param value
	 * @return
	 */
	public <K, V> Long rPushX(int index, K key, V value);
	
	/**
	 * @description 在默认第0库中执行rPopLPush命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param srcKey
	 * @param destKey
	 * @return
	 */
	public <K, V> V rPopLPush(K srcKey, K destKey);
	
	/**
	 * @description 在指定索引库中执行rPopLPush命令
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param srcKey
	 * @param destKey
	 * @return
	 */
	public <K, V> V rPopLPush(int index, K srcKey, K destKey);

}
