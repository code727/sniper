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
 * Create Date : 2015-7-5
 */

package org.workin.http;

import java.util.Map;

/**
 * @description 请求/响应头接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface HttpHeader {
	
	/**
	 * @description 设置指定名称的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name, Object value);
	
	/**
	 * @description 获取指定名称的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name);
	
	/**
	 * @description 获取指定名称和类型的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param value
	 * @return
	 */
	public <T> T getAttribute(String name, Class<T> clazz);
	
	/**
	 * @description 删除指定名称的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 */
	public void removeAttribute(String name);
	
	/**
	 * @description 删除所有属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void removeAll();
	
	/**
	 * @description 获取所有的属性名-值映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, Object> getAttributes();
	
}
