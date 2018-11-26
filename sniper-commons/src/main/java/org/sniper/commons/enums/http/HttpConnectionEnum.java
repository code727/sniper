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
 * Create Date : 2017-8-21
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * HTTP网络连接枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum HttpConnectionEnum {
	
	CLOSE("close"),
	
	KEEP_ALIVE("keep-alive");
	
	private static final Map<String, HttpConnectionEnum> mappings = MapUtils.newHashMap(2);
	
	static {
		for (HttpConnectionEnum connection : values()) {
			mappings.put(connection.value, connection);
		}
	}
	
	private String value;
	
	private HttpConnectionEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	/**
	 * 将指定的值解析成HttpConnection对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static HttpConnectionEnum resolve(String value) {
		return (value != null ? mappings.get(value.toLowerCase()) : null);
	}

	/**
	 * 判断指定的值是否匹配一个HttpConnection对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public boolean matches(String value) {
		return this.value.equalsIgnoreCase(value);
	}
	
}
