/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-7-31
 */

package org.workin.templet.message.resolver;

import java.util.Locale;

/**
 * 本地化消息解析器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface LocaleMessageResolver extends MessageResolver {
	
	/**
	 * 根据键获取对应的本地化资源信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @return
	 */
	public String getMessage(String key, Locale locale);
	
	/**
	 * 根据键获取对应的本地化资源信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(String key, Locale locale, String defaultMessage);
	
	/**
	 * 根据键获取具有参数的本地化资源信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @param param
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(String key, Locale locale, Object param, String defaultMessage);
	
	/**
	 * 根据键获取具有参数的本地化资源信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @param param
	 * @return
	 */
	public String getMessage(String key, Locale locale, Object param);
	
	/**
	 * 根据键获取具有参数的本地化资源信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @param params
	 * @return
	 */
	public String getMessage(String key, Locale locale, Object[] params);
	
	/**
	 * 根据键获取具有多个不同参数的本地化资源信息，未获取到时返回默认的信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locale
	 * @param params
	 * @param defaultMessage
	 * @return
	 */
	public String getMessage(String key, Locale locale, Object[] params, String defaultMessage);

}
