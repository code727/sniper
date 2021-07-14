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

package org.sniper.commons.enums.status;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 响应状态枚举
 * @author  Daniele
 * @version 1.0
 */
public enum ResponseStatusEnum implements Enumerable<Integer> {
	
	/** 未知错误，作为系统兜底的错误码 */
	UNKNOWN_ERROR(-1, "response.status.error.unknown"),
	
	/** 成功 */
	SUCCESS(0, "response.status.success"),
	
	/** 错误，包括业务逻辑错误和系统异常等 */
	ERROR(1, "response.status.error");
	
	private static final Map<Integer, ResponseStatusEnum> KEY_MAPPINGS = MapUtils.newHashMap(3);
	private static final Map<String, ResponseStatusEnum> NAME_MAPPINGS = MapUtils.newHashMap(3);
	
	static {
		for (ResponseStatusEnum status : values()) {
			KEY_MAPPINGS.put(status.key, status);
			NAME_MAPPINGS.put(status.name(), status);
		}
	}
	
	/** 键 */
	private final int key;
		
	/** 消息 */
	private final String message;

	private ResponseStatusEnum(int key, String message) {
		this.key = key;
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
	public static ResponseStatusEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static ResponseStatusEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
	
}
