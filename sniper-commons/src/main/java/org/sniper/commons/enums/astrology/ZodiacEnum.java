/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-12-17
 */

package org.sniper.commons.enums.astrology;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * 属相枚举类
 * @author  Daniele
 * @version 1.0
 */
public enum ZodiacEnum implements Enumerable<Integer> {
	
	/** 鼠 */
	MOUSE("zodiac.mouse"),
	/** 牛 */
	CATTLE("zodiac.cattle"),
	/** 虎 */
	TIGER("zodiac.tiger"),
	/** 兔 */
	RABBIT("zodiac.rabbit"),
	/** 龙  */
	DRAGON("zodiac.dragon"),
	/** 蛇 */
	SNAKE("zodiac.snake"),
	/** 马 */
	HORSE("zodiac.horse"),
	/** 羊 */
	SHEEP("zodiac.sheep"),
	/** 猴 */
	MONKEY("zodiac.monkey"),
	/** 鸡 */
	CHOOK("zodiac.chook"),
	/** 狗*/
	DOG("zodiac.dog"),
	/** 猪 */
	PIG("zodiac.pig");
	
	private static final Map<Integer, ZodiacEnum> KEY_MAPPINGS = MapUtils.newHashMap(12);
	
	private static final Map<String, ZodiacEnum> NAME_MAPPINGS = MapUtils.newHashMap(12);
	
	static {
		for (ZodiacEnum zodiac : values()) {
			KEY_MAPPINGS.put(zodiac.key, zodiac);
			NAME_MAPPINGS.put(zodiac.name(), zodiac);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 消息 */
	private final String message;  

	private ZodiacEnum(String message) {
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
	 * @param type
	 * @return
	 */
	public static ZodiacEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static ZodiacEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
	
}
