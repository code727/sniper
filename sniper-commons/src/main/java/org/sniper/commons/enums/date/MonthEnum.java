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

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 月份枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum MonthEnum {
	
	/** 一月 */
	JANUARY(Calendar.JANUARY, "date.month.january"),
	
	/** 二月 */
	FEBRUARY(Calendar.FEBRUARY, "date.month.february"),
	
	/** 三月 */
	MARCH(Calendar.MARCH, "date.month.march"),
	
	/** 四月 */
	APRIL(Calendar.APRIL, "date.month.april"),
	
	/** 五月 */
	MAY(Calendar.MAY, "date.month.may"),
	
	/** 六月 */
	JUNE(Calendar.JUNE, "date.month.june"),
	
	/** 七月 */
	JULY(Calendar.JULY, "date.month.july"),
	
	/** 八月 */
	AUGUST (Calendar.AUGUST, "date.month.august"),
	
	/** 九月 */
	SEPTEMBER(Calendar.SEPTEMBER, "date.month.september"),
	
	/** 十月 */
	OCTOBER(Calendar.OCTOBER, "date.month.october"),
	
	/** 十一月 */
	NOVEMBER(Calendar.NOVEMBER, "date.month.november"),
	
	/** 十二月 */
	DECEMBER(Calendar.DECEMBER, "date.month.december");
	
	public static final Map<Integer, MonthEnum> mappings = MapUtils.newHashMap(12);
	
	static {
		for (MonthEnum month : values()) {
			mappings.put(month.key, month);
		}
	}
		
	/** 键 */
	private final int key;
	
	/** 值 */
	private final String value;

	private MonthEnum(int key, String value) {
		this.key = key;
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
	 * 判断指定的键是否匹配一个Month对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 将指定的键解析成Month对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public static MonthEnum resolve(int key) {
		return mappings.get(key);
	}
	
}
