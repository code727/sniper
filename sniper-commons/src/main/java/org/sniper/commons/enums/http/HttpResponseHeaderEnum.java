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
 * HTTP响应头常量类
 * @author  Daniele
 * @version 1.0
 */
public enum HttpResponseHeaderEnum implements Enumerable<String> {

	ACCEPT_RANGES("Accept-Ranges", "http.response.header.accept-ranges"),
			
	ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials", "http.response.header.access-control-allow-credentials"),
	
	ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers", "http.response.header.access-control-allow-headers"),
			
	ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods", "http.response.header.access-control-allow-methods"),
			
	ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin", "http.response.header.access-control-allow-origin"),
			
	ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers", "http.response.header.access-control-expose-headers"),
	
	ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age", "http.response.header.access-control-max-age"),
	
	AGE("Age", "http.response.header.age"),
	
	CONTENT_SECURITY_POLICY("Content-Security-Policy", "http.response.header.content-security-policy"),
	
	CONTENT_SECURITY_POLICY_REPORT_ONLY("Content-Security-Policy-Report-Only", "http.response.header.content-security-policy-report-only"),
	
	CONTENT_RANGE("Content-Range", "http.response.header.content-range"),
	
	ETAG("ETag", "http.response.header.etag"),
			
	EXPIRES("Expires", "http.response.header.expires"),
			
	LAST_MODIFIED("Last-Modified", "http.response.header.last-modified"),
	
	LOCATION("Location", "http.response.header.location"),
			
	PROXY_AUTHENTICATE("Proxy-Authenticate", "http.response.header.proxy-authenticate"),
			
	RETRY_AFTER("Retry-After", "http.response.header.retry-after"),
	
	SERVER("Server", "http.response.header.server"),
	
	SET_COOKIE("Set-Cookie", "http.response.header.set-cookie"),
	
	@Deprecated
	SET_COOKIE2("Set-Cookie2", "http.response.header.set-cookie2"),
			
	TRAILER("Trailer", "http.response.header.trailer"),
			
	TRANSFER_ENCODING("Transfer-Encoding", "http.response.header.transfer-encoding"),
	
	VARY("Vary", "http.response.header.vary"),
			
	WWW_AUTHENTICATE("WWW-Authenticate", "http.response.header.www-authenticate")
	;
	
	private static final Map<String, HttpResponseHeaderEnum> KEY_AND_NAME_MAPPINGS = MapUtils.newHashMap(44);
	
	static {
		for (HttpResponseHeaderEnum responseHeader: values()) {
			KEY_AND_NAME_MAPPINGS.put(responseHeader.key.toUpperCase(), responseHeader);
			KEY_AND_NAME_MAPPINGS.put(responseHeader.name(), responseHeader);
		}
	}
	
	/** 键 */
	private final String key;
	
	/** 消息 */
	private final String message;
	
	private HttpResponseHeaderEnum(String key, String message) {
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
	public static HttpResponseHeaderEnum resolve(String keyOrName) {
		return keyOrName != null ? KEY_AND_NAME_MAPPINGS.get(keyOrName.toUpperCase()) : null;
	}
			
}
