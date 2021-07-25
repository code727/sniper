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

package org.sniper.commons.enums.channel;

import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 客户端类型枚举
 * @author  Daniele
 * @version 1.0
 */
public enum PersonalDeviceEnum {
	
	/** PC */
	PC("personal.device.pc"),
	
	/** Android */
	ANDROID("personal.device.android"),
	
	/** IOS */
	IOS("personal.device.ios"),
	
	/** WINPHONE */
	WINPHONE("personal.device.winphone")
	;
	
	private static final Map<Integer, PersonalDeviceEnum> mappings = MapUtils.newHashMap(12);
	
	static {
		for (PersonalDeviceEnum personalDevice : values()) {
			mappings.put(personalDevice.ordinal(), personalDevice);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 消息 */
	private final String message;  

	private PersonalDeviceEnum(String value) {
		this.key = ordinal();
		this.message = MessageUtils.getClassMessage(getClass(), value);
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
	public static PersonalDeviceEnum resolve(int key) {
		return mappings.get(key);
	}
	
}
