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
 * Create Date : 2017-9-11
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * HTTP身份认证类型枚举
 * @author  Daniele
 * @version 1.0
 */
public enum AuthenticationEnum implements Enumerable<Integer> {

	BASIC("Basic", "http.authentication.basic"),
	BEARER("Bearer", "http.authentication.bearer"),
	DIGEST("Digest", "http.authentication.digest"),
	HOBA("HOBA", "http.authentication.hoba"),
	MUTUAL("Mutual", "http.authentication.mutual"),
	NEGOTIATE("Negotiate", "http.authentication.negotiate"),
	OAUTH("OAuth", "http.authentication.oauth"),
	SCRAM_SHA_1("SCRAM-SHA-1", "http.authentication.scram-sha-1"),
	SCRAM_SHA_256("SCRAM-SHA-256", "http.authentication.scram-sha-256")
	;
	
	private static final Map<Integer, AuthenticationEnum> KEY_MAPPINGS = MapUtils.newHashMap(9);
	private static final Map<String, AuthenticationEnum> TYPE_AND_NAME_MAPPINGS = MapUtils.newHashMap(11);
	
	/** 键 */
	private final int key;
	
	/** 编码类型 */
	private final String type;
	
	/** 消息 */
	private final String message;
	
	static {
		for (AuthenticationEnum auth : values()) {
			KEY_MAPPINGS.put(auth.key, auth);
			TYPE_AND_NAME_MAPPINGS.put(auth.type.toUpperCase(), auth);
			TYPE_AND_NAME_MAPPINGS.put(auth.name(), auth);
		}
	}
		
	private AuthenticationEnum(String type, String message) {
		this.key = ordinal();
		this.type = type;
		this.message = message;
	}
	
	@Override
	public Integer getKey() {
		return key;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean match(Integer key) {
		return key != null && this.key == key.intValue();
	}

	/**
	 * 判断指定的类型或名称是否匹配当前枚举
	 * @author Daniele 
	 * @param typeOrName
	 * @return
	 */
	@Override
	public boolean match(String typeOrName) {
		return this.type.equalsIgnoreCase(typeOrName) || this.name().equalsIgnoreCase(typeOrName);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static AuthenticationEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的类型或名称解析成枚举对象
	 * @author Daniele 
	 * @param typeOrName
	 * @return
	 */
	public static AuthenticationEnum resolve(String typeOrName) {
		return typeOrName != null ? TYPE_AND_NAME_MAPPINGS.get(typeOrName.toUpperCase()) : null;
	}
		
}
