/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-24
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * HTTP状态系列枚举
 * @author  Daniele
 * @version 1.0
 */
public enum HttpStatusSeriesEnum implements Enumerable<Integer> {
	
	/** 信息系列(1xx) */
	INFORMATIONAL(1, "http.status.series.1xx"),
	/** 成功系列(2xx) */
	SUCCESSFUL(2, "http.status.series.2xx"),
	/** 重定向系列(3xx) */
	REDIRECTION(3, "http.status.series.3xx"),
	/** 客户端错误系列(4xx) */
	CLIENT_ERROR(4, "http.status.series.4xx"),
	/** 服务器错误系列(5xx) */
	SERVER_ERROR(5, "http.status.series.5xx");
	
	private static final Map<Integer, HttpStatusSeriesEnum> KEY_MAPPINGS = MapUtils.newHashMap(5);
	private static final Map<String, HttpStatusSeriesEnum> NAME_MAPPINGS = MapUtils.newHashMap(5);
	
	static {
		for (HttpStatusSeriesEnum series : values()) {
			KEY_MAPPINGS.put(series.key, series);
			NAME_MAPPINGS.put(series.name(), series);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 消息 */
	private final String message;
	
	private HttpStatusSeriesEnum(int key, String message) {
		this.key = key;
		this.message = message;
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
	public boolean match(Integer key) {
		return key != null && this.key == key.intValue();
	}

	@Override
	public boolean match(String name) {
		return this.name().equalsIgnoreCase(name);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static HttpStatusSeriesEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static HttpStatusSeriesEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}

}
