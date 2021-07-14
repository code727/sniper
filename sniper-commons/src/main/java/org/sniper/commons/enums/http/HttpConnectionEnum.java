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

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * HTTP网络连接状态枚举
 * @author  Daniele
 * @version 1.0
 */
public enum HttpConnectionEnum implements Enumerable<Integer> {
	
	/** 关闭状态 */
	CLOSE("close", "http.connection.close"),
	
	/** 保持连接状态 */
	KEEP_ALIVE("keep-alive", "http.connection.keep-alive");
	
	private static final Map<Integer, HttpConnectionEnum> KEY_MAPPINGS = MapUtils.newHashMap(2);
	private static final Map<String, HttpConnectionEnum> STATUS_AND_NAME_MAPPINGS = MapUtils.newHashMap(3);
	
	/** 键 */
	private final int key;
	
	/** 状态 */
	private final String status;
	
	/** 消息 */
	private final String message;
	
	static {
		for (HttpConnectionEnum connection : values()) {
			KEY_MAPPINGS.put(connection.key, connection);
			STATUS_AND_NAME_MAPPINGS.put(connection.status.toUpperCase(), connection);
			STATUS_AND_NAME_MAPPINGS.put(connection.name(), connection);
		}
	}
	
	private HttpConnectionEnum(String status, String message) {
		this.key = ordinal();
		this.status = status;
		this.message = MessageUtils.getClassMessage(getClass(), message);
	}
	
	@Override
	public Integer getKey() {
		return key;
	}
	
	public String getStatus() {
		return status;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean matches(Integer key) {
		return key != null && this.key == key.intValue();
	}
	
	/**
	 * 判断指定的状态或名称是否匹配当前枚举
	 * @author Daniele 
	 * @param statusOrName
	 * @return
	 */
	public boolean matches(String statusOrName) {
		return this.status.equalsIgnoreCase(statusOrName) || this.name().equalsIgnoreCase(statusOrName);
	}

	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static HttpConnectionEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的状态或名称解析成枚举对象
	 * @author Daniele 
	 * @param statusOrName
	 * @return
	 */
	public static HttpConnectionEnum resolve(String statusOrName) {
		return statusOrName != null ? STATUS_AND_NAME_MAPPINGS.get(statusOrName.toUpperCase()) : null;
	}
	
}
