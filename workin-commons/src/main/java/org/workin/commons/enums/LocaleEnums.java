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
 * Create Date : 2015-11-17
 */

package org.workin.commons.enums;

/**
 * 本地化消息枚举对象接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface LocaleEnums<K> {
	
	/**
	 * 获取当前枚举对象值的本地化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getMessage();
	
	/**
	 * 获取当前枚举对象值的本地化消息，未获取到时返回默认的消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(String defaultMessage);
	
	/**
	 * 获取当前枚举对象值的本地参数化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param params
	 * @return
	 */
	public String getMessage(Object[] params);
	
	/**
	 * 获取当前枚举对象值的本地参数化消息，未获取到时返回默认的消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param params
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(Object[] params, String defaultMessage);

}
