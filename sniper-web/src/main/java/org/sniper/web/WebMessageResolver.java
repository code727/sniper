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
 * Create Date : 2015-6-23
 */

package org.sniper.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.sniper.templet.message.resolver.MessageResolver;

/**
 * 基于Web应用的消息解析器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface WebMessageResolver extends MessageResolver {
	
	/**
	 * 获取当前HttpServletRequest对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest();
	
	/**
	 * 获取当前Locale对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Locale getLocale();

}
