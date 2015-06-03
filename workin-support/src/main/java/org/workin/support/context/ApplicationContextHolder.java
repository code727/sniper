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
	
	private static final ThreadLocal<Object> holder = new ThreadLocal<Object>(); 
	
	/**
	 * @description 设置线程变量值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 */
	public static void set(Object value) {
		holder.set(value);
	}
	
	/**
	 * @description 获取线程变量值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Object get() {
		return holder.get();
	}
	
	/**
	 * @description 清除上下文
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public static void clear() {
		holder.remove();
	}
	
	/**
	 * @description 创建线程局部变量上下文对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static <K,V> ThreadLocalContext<K,V> newThreadLocalContext() {
		return new ThreadLocalContext<K, V>();
	}
	
}
