/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-8-9
 */

package org.sniper.commons;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 多值Map接口
 * @author  Daniele
 * @version 1.0
 */
public interface MultiValueMap<K, V> extends Map<K, List<V>>, Serializable, Cloneable {
	
	/**
	 * 获取指定键对应的值的个数
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public int size(K key);
	
	/**
	 * 获取指定键对应的值是否为空
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public boolean isEmpty(K key);
	
	/**
	 * 获取指定键对应的值是否不为空
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public boolean isNotEmpty(K key);
	
	/**
	 * 获取指定键对应的第一个值
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public V getFirst(K key);
	
	/**
	 * 获取指定键对应的最后一个键
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public V getLast(K key);

	/**
	 * 添加键值
	 * @author Daniele 
	 * @param key
	 * @param value
	 */
	public void add(K key, V value);
	
	/**
	 * 添加多个键值
	 * @author Daniele 
	 * @param values
	 */
	public void add(Map<K, V> values);

	/**
	 * 覆盖设置键值
	 * @author Daniele 
	 * @param key
	 * @param value
	 */
	public void set(K key, V value);

	/**
	 * 覆盖设置多个键值
	 * @author Daniele 
	 * @param values
	 */
	public void set(Map<K, V> values);

	/**
	 * 转换成普通的Map对象
	 * @author Daniele 
	 * @return
	 */
	public Map<K, V> toSingleValueMap();
	
}
