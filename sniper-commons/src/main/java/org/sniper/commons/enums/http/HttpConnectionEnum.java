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
	
	/** 关闭状态 */
	CLOSE("close"),
	
	/** 保持连接状态 */
	KEEP_ALIVE("keep-alive");
	
	private static final Map<String, HttpConnectionEnum> mappings = MapUtils.newHashMap(2);
	
	/** 状态 */
	private final String status;
	
	static {
		for (HttpConnectionEnum connection : values()) {
			mappings.put(connection.status, connection);
		}
	}
	
	private HttpConnectionEnum(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	/**
	 * 判断指定的状态是否匹配一个HttpConnection对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param status
	 * @return
	 */
	public boolean matches(String status) {
		return this.status.equalsIgnoreCase(status);
	}

	/**
	 * 将指定的状态解析成HttpConnection对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param status
	 * @return
	 */
	public static HttpConnectionEnum resolve(String status) {
		return (status != null ? mappings.get(status.toLowerCase()) : null);
	}
	
}
