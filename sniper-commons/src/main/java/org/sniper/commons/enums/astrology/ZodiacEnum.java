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
 * 十二生肖枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum ZodiacEnum {
	
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
	
	private static final Map<Integer, ZodiacEnum> mappings = MapUtils.newHashMap(12);
	
	static {
		for (ZodiacEnum zodiac : values()) {
			mappings.put(zodiac.ordinal(), zodiac);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 值 */
	private final String value;

	private ZodiacEnum(String value) {
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
	 * 判断指定的键是否匹配一个Zodiac对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 将指定的键解析成Zodiac对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public static ZodiacEnum resolve(int key) {
		return mappings.get(key);
	}
	
}
