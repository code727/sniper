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

import org.sniper.commons.enums.AbstractLocaleEnum;

/**
 * 星期枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class Week extends AbstractLocaleEnum<Integer> {

	private static final long serialVersionUID = 2842891904770104643L;

	private Week(Integer key, String value) {
		super(key, value);
	}
	
	/** 星期天 */
	public static final Week SUNDAY = new Week(Calendar.SUNDAY, "date.week.sunday");
	
	/** 星期一 */
	public static final Week MONDAY = new Week(Calendar.MONDAY, "date.week.monday");
	
	/** 星期二 */
	public static final Week TUESDAY = new Week(Calendar.TUESDAY, "date.week.tuesday");
	
	/** 星期三 */
	public static final Week WEDNESDAY = new Week(Calendar.WEDNESDAY, "date.week.wednesday"); 
	
	/** 星期四 */
	public static final Week THURSDAY = new Week(Calendar.THURSDAY, "date.week.thursday"); 
	
	/** 星期五 */
	public static final Week FRIDAY = new Week(Calendar.FRIDAY, "date.week.friday"); 
	
	/** 星期六 */
	public static final Week SATURDAY = new Week(Calendar.SATURDAY, "date.week.saturday");  
	
}
