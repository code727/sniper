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

import org.sniper.commons.util.MapUtils;

/**
 * 日期模式枚举
 * @author  Daniele
 * @version 1.0
 */
public enum DatePatternEnum {

	/** 年 */
	YEAR("yyyy","yyyy"),
			
	/** 月 */
	MONTH("yyyy-MM", "yyyyMM"),
			
	/** 日 */
	DAY("yyyy-MM-dd", "yyyyMMdd"),
			
	/** 时 */
	HOUR("yyyy-MM-dd HH", "yyyyMMddHH"),
	
	/** 分 */
	MINUTE("yyyy-MM-dd HH:mm", "yyyyMMddHHmm"),
	
	/** 日期+精确到秒的时间 */
	DATETIME("yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"),
			
	/** 日期+精确到毫秒的时间 */
	DATETIME_PLUS("yyyy-MM-dd HH:mm:ss:SSS", "yyyyMMddHHmmssSSS");
	
	private static final Map<String, DatePatternEnum> mappings = MapUtils.newHashMap(14);
	
	static {
		for (DatePatternEnum patternEnum : values()) {
			mappings.put(patternEnum.pattern, patternEnum);
			mappings.put(patternEnum.sequencePattern, patternEnum);
		}
	}
	
	/** 模式 */
	private final String pattern;
	
	/** 序列模式 */
	private final String sequencePattern;
	
	private DatePatternEnum(String pattern, String sequencePattern) {
		this.pattern = pattern;
		this.sequencePattern = sequencePattern;
	}

	public String getPattern() {
		return pattern;
	}

	public String getSequencePattern() {
		return sequencePattern;
	}
	
	/**
	 * 判断指定的键是否匹配
	 * @author Daniele 
	 * @param pattern
	 * @return
	 */
	public boolean matches(String pattern) {
		return this.pattern.equals(pattern) || this.sequencePattern.equals(pattern);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param pattern
	 * @return
	 */
	public static DatePatternEnum resolve(String pattern) {
		return mappings.get(pattern);
	}
	
}
