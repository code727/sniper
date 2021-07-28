/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-11-27
 */

package org.sniper.commons.enums.date;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * 日期模式枚举
 * @author  Daniele
 * @version 1.0
 */
public enum DatePatternEnum implements Enumerable<Integer> {

	/** 年 */
	YEAR("yyyy", "yyyy", "date.pattern.year"),
			
	/** 月 */
	MONTH("yyyy-MM", "yyyyMM", "date.pattern.month"),
			
	/** 日 */
	DAY("yyyy-MM-dd", "yyyyMMdd", "date.pattern.day"),
			
	/** 时 */
	HOUR("yyyy-MM-dd HH", "yyyyMMddHH", "date.pattern.hour"),
	
	/** 分 */
	MINUTE("yyyy-MM-dd HH:mm", "yyyyMMddHHmm", "date.pattern.minute"),
	
	/** 日期+精确到秒的时间 */
	DATETIME("yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "date.pattern.datetime"),
			
	/** 日期+精确到毫秒的时间 */
	DATETIME_PLUS("yyyy-MM-dd HH:mm:ss:SSS", "yyyyMMddHHmmssSSS", "date.pattern.datetime.plus");
	
	private static final Map<Integer, DatePatternEnum> KEY_MAPPINGS = MapUtils.newHashMap(7);
	
	private static final Map<String, DatePatternEnum> NAME_MAPPINGS = MapUtils.newHashMap(7);
	
	private static final Map<String, DatePatternEnum> PATTERN_MAPPINGS = MapUtils.newHashMap(14);
	
	static {
		for (DatePatternEnum patternEnum : values()) {
			KEY_MAPPINGS.put(patternEnum.key, patternEnum);
			NAME_MAPPINGS.put(patternEnum.name(), patternEnum);
			
			PATTERN_MAPPINGS.put(patternEnum.pattern, patternEnum);
			PATTERN_MAPPINGS.put(patternEnum.sequencePattern, patternEnum);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 模式 */
	private final String pattern;
	
	/** 序列模式 */
	private final String sequencePattern;
	
	/** 消息 */
	private final String message;
	
	private DatePatternEnum(String pattern, String sequencePattern, String message) {
		this.key = ordinal();
		this.pattern = pattern;
		this.sequencePattern = sequencePattern;
		this.message = message;
	}
	
	@Override
	public Integer getKey() {
		return key;
	}

	public String getPattern() {
		return pattern;
	}

	public String getSequencePattern() {
		return sequencePattern;
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
	 * 判断指定的模式或名称是否匹配当前枚举对象
	 * @author Daniele 
	 * @param patternOrName
	 * @return
	 */
	@Override
	public boolean match(String patternOrName) {
		return this.pattern.equals(patternOrName) || this.sequencePattern.equals(patternOrName)
				|| this.name().equalsIgnoreCase(patternOrName);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static DatePatternEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的模式或名称解析成枚举对象
	 * @author Daniele 
	 * @param patternOrName
	 * @return
	 */
	public static DatePatternEnum resolve(String patternOrName) {
		if (patternOrName == null)
			return null;
		
		DatePatternEnum patternEnum = PATTERN_MAPPINGS.get(patternOrName);
		if (patternEnum == null)
			patternEnum = NAME_MAPPINGS.get(patternOrName.toUpperCase());
		
		return patternEnum;
	}
	
}
