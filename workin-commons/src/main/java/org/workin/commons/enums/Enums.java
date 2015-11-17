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
 * Create Date : 2015-1-19
 */

package org.workin.commons.enums;

/**
 * @description 枚举对象类接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Enums<K, V> {
	
	/**
	 * @description 获取枚举键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public K getKey();
	
	/**
	 * @description 设置枚举键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 */
	public void setKey(K key);
	
	/**
	 * @description 获取枚举值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public V getValue();
	
	/**
	 * @description 设置枚举值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 */
	public void setValue(V value);
			
}
