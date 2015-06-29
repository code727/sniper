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
 * @description 应用上下文工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ApplicationContextHolder {
		
	private static final ApplicationContext<Object, Object> holder = newMapThreadLocalContext();
	
	/**
	 * @description 清除上下文
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public static void clear() {
		holder.clear();
	}
	
	/**
	 * @description 根据名称获取线程局部变量的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public static Object getAttribute(Object name) {
		 return holder.getAttribute(name);
	}
	
	/**
	 * @description 设置线程局部变量的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param value
	 */
	public static void setAttribute(Object name, Object value) {
		holder.setAttribute(name, value);
	}
	
	/**
	 * @description 根据名称删除对应的局部变量属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public static Object removeAttribute(Object name) {
		return holder.removeAttribute(name);
	}
		
	/**
	 * @description 创建基于Map类型的线程局部变量
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K,V> MapThreadLocalContext<K,V> newMapThreadLocalContext() {
		return new MapThreadLocalContext<K, V>();
	}
	
}
