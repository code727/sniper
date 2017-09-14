/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-8-15
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * HTTP方法枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum HttpMethod {
	
	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

	private static final Map<String, HttpMethod> mappings = MapUtils.newHashMap(8);

	static {
		for (HttpMethod httpMethod : values()) {
			mappings.put(httpMethod.name(), httpMethod);
		}
	}

	/**
	 * 将指定的名称解析成HTTP方法对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param method
	 * @return
	 */
	public static HttpMethod resolve(String method) {
		return (method != null ? mappings.get(method.toUpperCase()) : null);
	}

	/**
	 * 判断指定的名称是否匹配一个HTTP方法对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param method
	 * @return
	 */
	public boolean matches(String method) {
		return this.name().equalsIgnoreCase(method);
	}
	
}
