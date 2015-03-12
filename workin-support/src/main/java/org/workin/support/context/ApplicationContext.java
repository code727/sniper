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
 * Create Date : 2015-1-26
 */

package org.workin.support.context;

/**
 * @description 泛型应用上下文接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ApplicationContext<K, V> {
	
	/**
	 * @description 根据名称获取上下文属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public V getAttribute(K name);
	
	/**
	 * @description 设置上下文属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param value
	 */
	public void setAttribute(K name, V value);
	
	/**
	 * @description 根据名称删除上下文属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public V removeAttribute(K name);
	
	/**
	 * @description 清除上下文
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void clear();

}
