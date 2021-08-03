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
 * Create Date : 2021-8-1
 */

package org.sniper.commons.enums.status;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * 活跃状态枚举类
 * @author  Daniele
 * @version 1.0
 */
public enum ActiveStatusEnum implements Enumerable<Integer> {
	
	/** 未激活 */
	UNACTIVATED("active.status.unactivated"),
	
	/** 已激活 */
	ACTIVATED("active.status.activated"),
	;
	
	private static final Map<Integer, ActiveStatusEnum> KEY_MAPPINGS = MapUtils.newHashMap(2);
	private static final Map<String, ActiveStatusEnum> NAME_MAPPINGS = MapUtils.newHashMap(2);
	
	static {
		for (ActiveStatusEnum status : values()) {
			KEY_MAPPINGS.put(status.key, status);
			NAME_MAPPINGS.put(status.name(), status);
		}
	}

	/** 键 */
	private final int key;
	
	/** 消息 */
	private final String message;

	private ActiveStatusEnum(String message) {
		this.key = ordinal();
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
	public static ActiveStatusEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static ActiveStatusEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}

}
