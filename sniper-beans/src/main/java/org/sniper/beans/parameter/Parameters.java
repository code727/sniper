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
 * Create Date : 2017-8-31
 */

package org.sniper.beans.parameter;

import java.util.Map;

/**
 * 泛型参数接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Parameters<K, V> extends UnmodifiableParameters<K, V> {
	
	/**
	 * 设置参数映射项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameterItems
	 * @return
	 */
	public void setItems(Map<K, V> items);
	
	/**
	 * 新增参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param value
	 */
	public void add(K name, V value);
	
	/**
	 * 新增多个参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameterItems
	 */
	public void addAll(Map<K, V> parameterItems);
	
	/**
	 * 删除指定名称的参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 */
	public V remove(K name);
	
	/**
	 * 清除所有参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void clear();

}
