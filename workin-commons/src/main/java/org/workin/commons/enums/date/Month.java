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

package org.workin.commons.enums.date;

import java.util.Calendar;

import org.workin.commons.enums.AbstractLocaleEnums;

/**
 * 月份枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class Month extends AbstractLocaleEnums<Integer> {

	protected Month(Integer key, String messageKey) {
		super(key, messageKey);
	}
	
	/** 一月 */
	public static final Month JANUARY = new Month(Calendar.JANUARY, "date.month.january");
	
	/** 二月 */
	public static final Month FEBRUARY = new Month(Calendar.FEBRUARY, "date.month.february");
	
	/** 三月 */
	public static final Month MARCH = new Month(Calendar.MARCH, "date.month.march");
	
	/** 四月 */
	public static final Month APRIL = new Month(Calendar.APRIL, "date.month.april");
	
	/** 五月 */
	public static final Month MAY = new Month(Calendar.MAY, "date.month.may");
	
	/** 六月 */
	public static final Month JUNE = new Month(Calendar.JUNE, "date.month.june");
	
	/** 七月 */
	public static final Month JULY = new Month(Calendar.JULY, "date.month.july");
	
	/** 八月 */
	public static final Month AUGUST = new Month(Calendar.AUGUST, "date.month.august");
	
	/** 九月 */
	public static final Month SEPTEMBER = new Month(Calendar.SEPTEMBER, "date.month.september");
	
	/** 十月 */
	public static final Month OCTOBER = new Month(Calendar.OCTOBER, "date.month.october");
	
	/** 十一月 */
	public static final Month NOVEMBER = new Month(Calendar.NOVEMBER, "date.month.november");
	
	/** 十二月 */
	public static final Month DECEMBER = new Month(Calendar.DECEMBER, "date.month.december");

}
