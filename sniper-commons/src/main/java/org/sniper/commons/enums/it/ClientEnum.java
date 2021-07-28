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
 * Create Date : 2021-7-12
 */

package org.sniper.commons.enums.it;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * 客户端类型枚举
 * @author  Daniele
 * @version 1.0
 */
public enum ClientEnum {
	
	/** PC */
	PC("client.type.pc"),
	
	/** Android */
	ANDROID("client.type.android"),
	
	/** IOS */
	IOS("client.type.ios"),
	
	/** WINPHONE */
	WINPHONE("client.type.winphone")
	;
	
	private static final Map<Integer, ClientEnum> mappings = MapUtils.newHashMap(4);
	
	static {
		for (ClientEnum client : values()) {
			mappings.put(client.ordinal(), client);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 消息 */
	private final String message;  

	private ClientEnum(String message) {
		this.key = ordinal();
		this.message = message;
	}
	
	public int getKey() {
		return key;
	}

	public String getMessage() {
		return message;
	}
	
	/**
	 * 判断指定的键是否匹配
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param type
	 * @return
	 */
	public static ClientEnum resolve(int key) {
		return mappings.get(key);
	}
	
}
