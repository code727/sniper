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

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 星座枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum HoroscopeEnum {
	
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
	
	private static final Map<Integer, HoroscopeEnum> mappings = MapUtils.newHashMap(12);
	
	static {
		for (HoroscopeEnum horoscope : values()) {
			mappings.put(horoscope.ordinal(), horoscope);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 值 */
	private final String value;

	private HoroscopeEnum(String value) {
		this.key = ordinal();
		this.value = value;
	}
	
	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getMessage() {
		return MessageUtils.getClassMessage(this.getClass(), this.value);
	}
	
	/**
	 * 判断指定的键是否匹配一个Horoscope对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 将指定的键解析成Horoscope对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @return
	 */
	public static HoroscopeEnum resolve(int key) {
		return mappings.get(key);
	}
		
}
