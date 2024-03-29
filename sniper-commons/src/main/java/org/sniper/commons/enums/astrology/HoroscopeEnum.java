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
 * 星座枚举类
 * @author  Daniele
 * @version 1.0
 */
public enum HoroscopeEnum implements Enumerable<Integer> {
	
	/** 白羊座 */
	ARIES("horoscope.aries"),
	
	/** 金牛座 */
	TAURUS("horoscope.taurus"),
	
	/** 双子座 */
	GEMINI("horoscope.gemini"),
	
	/** 巨蟹座 */
	CANCER("horoscope.cancer"),
	
	/** 狮子座 */
	LEO("horoscope.leo"),
	
	/** 处女座 */
	VIRGO("horoscope.virgo"),
	
	/** 天秤座 */
	LIBRA("horoscope.libra"),
	
	/** 天蝎座 */
	ACRAB("horoscope.acrab"),
	
	/** 射手座 */
	SAGITTARIUS("horoscope.sagittarius"),
	
	/** 摩羯座 */
	CAPRICORN("horoscope.capricorn"),
	
	/** 水平座 */
	AQUARIUS("horoscope.aquarius"),
	
	/** 双鱼座 */
	PISCES("horoscope.pisces");
	
	private static final Map<Integer, HoroscopeEnum> KEY_MAPPINGS = MapUtils.newHashMap(12);
	
	private static final Map<String, HoroscopeEnum> NAME_MAPPINGS = MapUtils.newHashMap(12);
	
	static {
		for (HoroscopeEnum horoscope : values()) {
			KEY_MAPPINGS.put(horoscope.key, horoscope);
			NAME_MAPPINGS.put(horoscope.name(), horoscope);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 消息 */
	private final String message;  

	private HoroscopeEnum(String message) {
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
	public static HoroscopeEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static HoroscopeEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
		
}
