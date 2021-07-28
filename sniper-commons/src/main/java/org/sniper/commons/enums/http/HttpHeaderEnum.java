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
 * Create Date : 2017-8-10
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * HTTP头枚举
 * @author  Daniele
 * @version 1.0
 */
public enum HttpHeaderEnum implements Enumerable<String> {

	ALLOW("Allow", "http.entity.header.allow"),
	
	CACHE_CONTROL("Cache-Control", "http.general.header.cache-control"),
	
	CONNECTION("Connection", "http.general.header.connection"),
		
	CONTENT_ENCODING("Content-Encoding", "http.entity.header.content-encoding"),
			
	CONTENT_DISPOSITION("Content-Disposition", "http.general.header.content-disposition"),
	
	CONTENT_LANGUAGE("Content-Language", "http.entity.header.content-language"),
	
	CONTENT_LENGTH("Content-Length", "http.entity.header.content-length"),
	
	CONTENT_LOCATION("Content-Location", "http.entity.header.content-location"),
			
	CONTENT_TYPE("Content-Type", "http.entity.header.content-type"),
	
	DATE("Date", "http.general.header.date"),
	
	KEEP_ALIVE("Keep-Alive", "http.general.header.keep-alive"),
	
	PRAGMA("Pragma", "http.general.header.pragma"),
	
	VIA("Via", "http.general.header.via"),
	
	WARNING("Warning", "http.general.header.warning");
	
	private static final Map<String, HttpHeaderEnum> KEY_AND_NAME_MAPPINGS = MapUtils.newHashMap(22);
	
	static {
		for (HttpHeaderEnum httpHeader: values()) {
			KEY_AND_NAME_MAPPINGS.put(httpHeader.key.toUpperCase(), httpHeader);
			KEY_AND_NAME_MAPPINGS.put(httpHeader.name(), httpHeader);
		}
	}
	
	/** 键 */
	private final String key;
	
	/** 消息 */
	private final String message;
	
	private HttpHeaderEnum(String key, String message) {
		this.key = key;
		this.message = message;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * 判断指定的键或名称是否匹配当前枚举对象
	 * @author Daniele 
	 * @param patternOrName
	 * @return
	 */
	@Override
	public boolean match(String keyOrName) {
		return this.key.equalsIgnoreCase(key) || this.name().equalsIgnoreCase(key);
	}
	
	/**
	 * 将指定的键或名称解析成枚举对象
	 * @author Daniele
	 * @param keyOrName
	 * @return
	 */
	public static HttpHeaderEnum resolve(String keyOrName) {
		return keyOrName != null ? KEY_AND_NAME_MAPPINGS.get(keyOrName.toUpperCase()) : null;
	}
			
}
