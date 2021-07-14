/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-5-3
 */

package org.sniper.commons.enums.date;

import java.util.Calendar;
import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 月份枚举类
 * @author  Daniele
 * @version 1.0
 */
public enum MonthEnum implements Enumerable<Integer> {
	
	/** 一月 */
	JANUARY(Calendar.JANUARY, "Jan", "month.january"),
	
	/** 二月 */
	FEBRUARY(Calendar.FEBRUARY, "Feb", "month.february"),
	
	/** 三月 */
	MARCH(Calendar.MARCH, "Mar", "month.march"),
	
	/** 四月 */
	APRIL(Calendar.APRIL, "Apr", "month.april"),
	
	/** 五月 */
	MAY(Calendar.MAY, "May", "month.may"),
	
	/** 六月 */
	JUNE(Calendar.JUNE, "Jun", "month.june"),
	
	/** 七月 */
	JULY(Calendar.JULY, "Jul", "month.july"),
	
	/** 八月 */
	AUGUST (Calendar.AUGUST, "Aug", "month.august"),
	
	/** 九月 */
	SEPTEMBER(Calendar.SEPTEMBER, "Sept", "month.september"),
	
	/** 十月 */
	OCTOBER(Calendar.OCTOBER, "Oct", "month.october"),
	
	/** 十一月 */
	NOVEMBER(Calendar.NOVEMBER, "Nov", "month.november"),
	
	/** 十二月 */
	DECEMBER(Calendar.DECEMBER, "Dec", "month.december");
	
	private static final Map<Integer, MonthEnum> KEY_MAPPINGS = MapUtils.newHashMap(12);
	private static final Map<String, MonthEnum> ABBREVIATION_AND_NAME_MAPPINGS = MapUtils.newHashMap(24);
	
	static {
		for (MonthEnum month : values()) {
			KEY_MAPPINGS.put(month.key, month);
			ABBREVIATION_AND_NAME_MAPPINGS.put(month.abbreviation.toUpperCase(), month);
			ABBREVIATION_AND_NAME_MAPPINGS.put(month.name(), month);
		}
	}
		
	/** 键 */
	private final int key;
	
	/** 缩写/简称 */
	private final String abbreviation;
	
	/** 消息 */
	private final String message;
	
	private MonthEnum(int key, String abbreviation, String message) {
		this.key = key;
		this.abbreviation = abbreviation;
		this.message = MessageUtils.getClassMessage(getClass(), message);
	}
	
	@Override
	public Integer getKey() {
		return key;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean matches(Integer key) {
		return key != null && this.key == key.intValue();
	}
	
	/**
	 * 判断指定的缩写或名称是否匹配当前枚举对象
	 * @author Daniele
	 * @param abbreviationOrName
	 * @return
	 */
	@Override
	public boolean matches(String abbreviationOrName) {
		return this.abbreviation.equalsIgnoreCase(abbreviationOrName)
				|| this.name().equalsIgnoreCase(abbreviationOrName);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static MonthEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的缩写或名称解析成枚举对象
	 * @author Daniele 
	 * @param abbreviationOrName
	 * @return
	 */
	public static MonthEnum resolve(String abbreviationOrName) {
		return abbreviationOrName != null ? ABBREVIATION_AND_NAME_MAPPINGS.get(
				abbreviationOrName.toUpperCase()) : null;
	}
		
}
