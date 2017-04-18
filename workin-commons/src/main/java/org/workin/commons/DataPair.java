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
 * Create Date : 2017-3-31
 */

package org.workin.commons;

import java.io.Serializable;

/**
 * 数据对接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface DataPair<K, V> extends Serializable {
	
	/**
	 * 获取键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public K getKey();

	/**
	 * 设置键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 */
	public void setKey(K key);

	/**
	 * 获取值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public V getValue();

	/**
	 * 设置值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 */
	public void setValue(V value);

}