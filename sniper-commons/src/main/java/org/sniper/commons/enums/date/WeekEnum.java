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
 * 星期枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum WeekEnum {
	
	/** 星期天 */
	SUNDAY(Calendar.SUNDAY, "week.sunday"),
	
	/** 星期一 */
	MONDAY(Calendar.MONDAY, "week.monday"),
	
	/** 星期二 */
	TUESDAY(Calendar.TUESDAY, "week.tuesday"),
	
	/** 星期三 */
	WEDNESDAY(Calendar.WEDNESDAY, "week.wednesday"),
	
	/** 星期四 */
	THURSDAY(Calendar.THURSDAY, "week.thursday"),
	
	/** 星期五 */
	FRIDAY(Calendar.FRIDAY, "week.friday"),
	
	/** 星期六 */
	SATURDAY(Calendar.SATURDAY, "week.saturday");
	
	private static final Map<Integer, WeekEnum> mappings = MapUtils.newHashMap(7);
	
	static {
		for (WeekEnum week : values()) {
			mappings.put(week.key, week);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 值 */
	private final String value;
	
	/** 消息 */
	private final String message;
	
	private WeekEnum(int key, String value) {
		this.key = key;
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 将指定的键解析成Week对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public static WeekEnum resolve(int key) {
		return mappings.get(key);
	}
		
}
