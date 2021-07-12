/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-5-14
 */

package org.sniper.support.counter;

/**
 * 计数器接口
 * @author  Daniele
 * @version 1.0
 */
public interface Counter<V> {
	
	/**
	 * 获取起始值
	 * @author Daniele 
	 * @return
	 */
	public V getStart();
	
	/**
	 * 设置起始值
	 * @author Daniele 
	 * @param start
	 */
	public void setStart(V start);
	
	/**
	 * 累加默认值
	 * @author Daniele 
	 * @return
	 */
	public V increment();
	
	/**
	 * 累加指定值
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public V increment(V value);
	
	/**
	 * 累减默认值
	 * @author Daniele 
	 * @return
	 */
	public V decrement();
	
	/**
	 * 累减指定值
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public V decrement(V value);
	
	/**
	 * 获取当前计数值
	 * @author Daniele 
	 * @return
	 */
	public V get();

}
