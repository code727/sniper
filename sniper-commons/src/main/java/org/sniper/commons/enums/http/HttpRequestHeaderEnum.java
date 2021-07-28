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
 * HTTP请求头枚举
 * @author  Daniele
 * @version 1.0
 */
public enum HttpRequestHeaderEnum implements Enumerable<String> {

	ACCEPT("Accept","http.request.header.accept"),
	
	ACCEPT_CHARSET("Accept-Charset", "http.request.header.accept-charset"),
	
	ACCEPT_ENCODING("Accept-Encoding", "http.request.header.accept-encoding"),
	
	ACCEPT_LANGUAGE("Accept-Language", "http.request.header.accept-language"),
	
	ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers", "http.request.header.access-control-request-headers"),
	
	ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method", "http.request.header.access-control-request-method"),
	
	AUTHORIZATION("Authorization", "http.request.header.authorization"),
			
	COOKIE("Cookie", "http.request.header.cookie"),
	
	@Deprecated
	COOKIE2("Cookie2", "http.request.header.cookie2"),
			
	DNT("DNT", "http.request.header.dnt"),
	
	EXPECT("Expect", "http.request.header.expect"),
			
	FORWARDED("Forwarded", "http.request.header.forwarded"),
	
	FROM("From", "http.request.header.from"),
	
	HOST("Host", "http.request.header.host"),
	
	IF_MATCH("If-Match", "http.request.header.if-match"),
			
	IF_MODIFIED_SINCE("If-Modified-Since", "http.request.header.if-modified-since"),
	
	IF_NONE_MATCH("If-None-Match", "http.request.header.if-none-match"),
	
	IF_RANGE("If-Range", "http.request.header.if-range"),
			
	IF_UNMODIFIED_SINCE("If-Unmodified-Since", "http.request.header.if-unmodified-since"),
	
	ORIGIN("Origin", "http.request.header.origin"),
			
	PROXY_AUTHORIZATION("Proxy-Authorization", "http.request.header.proxy-authorization"),
	
	RANGE("Range", "http.request.header.range"),
			
	REFERER("Referer", "http.request.header.referer"),
	
	TE("TE", "http.request.header.te"),
	
	UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests", "http.request.header.upgrade-insecure-requests"),
	
	USER_AGENT("User-Agent", "http.request.header.user-agent"),
	
	X_FORWARDED_FOR("X-Forwarded-For", "http.request.header.x-forwarded-for"),
			
	X_FORWARDED_HOST("X-Forwarded-Host", "http.request.header.x-forwarded-host"),
			
	X_FORWARDED_PROTO("X-Forwarded-Proto", "http.request.header.x-forwarded-proto")
	;
	
	private static final Map<String, HttpRequestHeaderEnum> KEY_AND_NAME_MAPPINGS = MapUtils.newHashMap(44);
	
	static {
		for (HttpRequestHeaderEnum requestHeader: values()) {
			KEY_AND_NAME_MAPPINGS.put(requestHeader.key.toUpperCase(), requestHeader);
			KEY_AND_NAME_MAPPINGS.put(requestHeader.name(), requestHeader);
		}
	}
	
	/** 键 */
	private final String key;
	
	/** 消息 */
	private final String message;
	
	private HttpRequestHeaderEnum(String key, String message) {
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
	public static HttpRequestHeaderEnum resolve(String keyOrName) {
		return keyOrName != null ? KEY_AND_NAME_MAPPINGS.get(keyOrName.toUpperCase()) : null;
	}
			
}
