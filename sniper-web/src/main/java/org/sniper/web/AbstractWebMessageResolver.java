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
 * Create Date : 2018-7-25
 */

package org.sniper.web;

import org.sniper.templet.message.resolver.AbstractMessageResolver;

/**
 * Web消息解析器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractWebMessageResolver extends AbstractMessageResolver implements WebMessageResolver {
	
	/**
	 * 获取指定类对应的参数化消息
	 * @author Daniele 
	 * @param clazz
	 * @param key
	 * @param param
	 * @param defaultMessage
	 * @return
	 */
	public abstract String getMessage(Class<?> clazz, String key, Object param, String defaultMessage);
	
	/**
	 * 获取指定类对应的参数化消息
	 * @author Daniele 
	 * @param clazz
	 * @param key
	 * @param params
	 * @param defaultMessage
	 * @return
	 */
	public abstract String getMessage(Class<?> clazz, String key, Object[] params, String defaultMessage);

}
