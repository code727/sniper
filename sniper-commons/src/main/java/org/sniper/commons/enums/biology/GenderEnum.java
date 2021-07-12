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
 * Create Date : 2021-7-6
 */

package org.sniper.commons.enums.biology;

import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 性别枚举
 * @author  Daniele
 * @version 1.0
 */
public enum GenderEnum {
	
	/** 女性 */
	FEMALE("gender.female"),
	/** 男性 */
	MALE("gender.male");
	
	private static final Map<Integer, GenderEnum> mappings = MapUtils.newHashMap(2);
	
	static {
		for (GenderEnum gender: values()) {
			mappings.put(gender.ordinal(), gender);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 值 */
	private final String value;
	
	/** 消息 */
	private final String message;  
	
	private GenderEnum(String value) {
		this.key = ordinal();
		this.value = value;
		this.message = MessageUtils.getClassMessage(getClass(), value);
	}
	
	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
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
	public static GenderEnum resolve(int key) {
		return mappings.get(key);
	}

}
