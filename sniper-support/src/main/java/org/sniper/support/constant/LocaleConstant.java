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

package org.sniper.support.constant;

import java.util.Locale;

/**
 * 本地化常量接口
 * @author  Daniele
 * @version 1.0
 */
public interface LocaleConstant<K, V> extends Constant<K, V> {
	
	/**
	 * 获取当前枚举对象值的本地化消息
	 * @author Daniele 
	 * @return
	 */
	public String getMessage();
	
	/**
	 * 获取当前枚举对象值的本地化消息
	 * @author Daniele 
	 * @param locale
	 * @return
	 */
	public String getMessage(Locale locale);
	
	/**
	 * 获取当前枚举对象值的本地化消息，未获取到时返回默认的消息
	 * @author Daniele 
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(String defaultMessage);
	
	/**
	 * 获取当前枚举对象值的本地化消息，未获取到时返回默认的消息
	 * @author Daniele 
	 * @param locale
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(Locale locale, String defaultMessage);
	
	/**
	 * 获取当前枚举对象值的本地参数化消息
	 * @author Daniele 
	 * @param params
	 * @return
	 */
	public String getMessage(Object[] params);
	
	/**
	 * 获取当前枚举对象值的本地参数化消息
	 * @author Daniele 
	 * @param locale
	 * @param params
	 * @return
	 */
	public String getMessage(Locale locale, Object[] params);
	
	/**
	 * 获取当前枚举对象值的本地参数化消息，未获取到时返回默认的消息
	 * @author Daniele 
	 * @param params
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(Object[] params, String defaultMessage);
	
	/**
	 * 获取当前枚举对象值的本地参数化消息，未获取到时返回默认的消息
	 * @author Daniele 
	 * @param locale
	 * @param params
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(Locale locale, Object[] params, String defaultMessage);

}