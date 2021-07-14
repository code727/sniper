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

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * HTTP协议枚举
 * @author  Daniele
 * @version 1.0
 */
public enum HttpProtocolEnum implements Enumerable<Integer> {
	
	HTTP("http.protocol.http"), 
	
	HTTPS("http.protocol.https");
	
	private static final Map<Integer, HttpProtocolEnum> KEY_MAPPINGS = MapUtils.newHashMap(2);
	private static final Map<String, HttpProtocolEnum> NAME_MAPPINGS = MapUtils.newHashMap(2);
	
	static {
		for (HttpProtocolEnum httpProtocol : values()) {
			KEY_MAPPINGS.put(httpProtocol.key, httpProtocol);
			NAME_MAPPINGS.put(httpProtocol.name(), httpProtocol);
		}
	}
	
	/** 键 */
	private final int key;
		
	/** 消息 */
	private final String message;
	
	private HttpProtocolEnum(String message) {
		this.key = ordinal();
		this.message = MessageUtils.getClassMessage(getClass(), message);
	}
	
	@Override
	public Integer getKey() {
		return key;
	}
		
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean matches(Integer key) {
		return key != null && this.key == key.intValue();
	}
	
	@Override
	public boolean matches(String name) {
		return this.name().equalsIgnoreCase(name);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static HttpProtocolEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static HttpProtocolEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
	
}
