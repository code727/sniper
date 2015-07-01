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
 * Create Date : 2015-7-1
 */

package org.workin.spring.context.constant;

/**
 * @description 全局应用常量配置接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Constant {
	
	/**
	 * @description 设置全局常量配置项键值对
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value);
	
	/**
	 * @description 根据键获取Object类型的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public Object get(Object key);
	
	/**
	 * @description 根据键获取指定类型的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> V get(Object key, Class<V> clazz);
	
}
