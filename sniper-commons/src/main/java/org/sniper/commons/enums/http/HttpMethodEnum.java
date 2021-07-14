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

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * HTTP方法枚举
 * @author  Daniele
 * @version 1.0
 */
public enum HttpMethodEnum implements Enumerable<Integer> {
	
	/** GET方法 */
	GET("http.method.get"), 
	
	/** HEAD方法 */
	HEAD("http.method.head"), 
	
	/** POST方法 */
	POST("http.method.post"), 
	
	/** PUT方法 */
	PUT("http.method.put"), 
	
	/** PATCH方法 */
	PATCH("http.method.patch"),
	
	/** DELETE方法 */
	DELETE("http.method.delete"), 
	
	/** OPTIONS方法 */
	OPTIONS("http.method.options"), 
	
	/** TRACE方法 */
	TRACE("http.method.trace")
	;
	
	private static final Map<Integer, HttpMethodEnum> KEY_MAPPINGS = MapUtils.newHashMap(8);
	private static final Map<String, HttpMethodEnum> NAME_MAPPINGS = MapUtils.newHashMap(8);

	static {
		for (HttpMethodEnum httpMethod : values()) {
			KEY_MAPPINGS.put(httpMethod.ordinal(), httpMethod);
			NAME_MAPPINGS.put(httpMethod.name(), httpMethod);
		}
	}
	
	/** 键 */
	private final int key;
		
	/** 消息 */
	private final String message;
	
	static {
		for (HttpMethodEnum encoding : values()) {
			KEY_MAPPINGS.put(encoding.key, encoding);
			NAME_MAPPINGS.put(encoding.name(), encoding);
		}
	}
		
	private HttpMethodEnum(String message) {
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
	public static HttpMethodEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static HttpMethodEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
		
}
