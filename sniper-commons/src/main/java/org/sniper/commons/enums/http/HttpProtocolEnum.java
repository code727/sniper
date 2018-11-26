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
 * Create Date : 2017-9-7
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * HTTP协议枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum HttpProtocolEnum {
	
	HTTP, HTTPS;
	
	private static final Map<String, HttpProtocolEnum> mappings = MapUtils.newHashMap(2);
	
	static {
		for (HttpProtocolEnum httpProtocol : values()) {
			mappings.put(httpProtocol.name(), httpProtocol);
		}
	}
	
	/**
	 * 将指定的协议解析成HttpProtocol对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param protocol
	 * @return
	 */
	public static HttpProtocolEnum resolve(String protocol) {
		return (protocol != null ? mappings.get(protocol.toUpperCase()) : null);
	}

	/**
	 * 判断指定的协议是否匹配一个HttpProtocol对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param protocol
	 * @return
	 */
	public boolean matches(String protocol) {
		return this.name().equalsIgnoreCase(protocol);
	}

}
