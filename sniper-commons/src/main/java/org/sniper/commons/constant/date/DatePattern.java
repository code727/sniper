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

package org.sniper.commons.constant.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.sniper.commons.constant.AbstractConstant;

/**
 * 日期模式常量
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DatePattern extends AbstractConstant<String, DateFormat> {
	
	private static final long serialVersionUID = 3224867467862743973L;

	protected DatePattern(String key) {
		this(key, null);
	}
	
	protected DatePattern(String key, DateFormat value) {
		super(key, value != null ? value : new SimpleDateFormat(key));
	}

	/** 年 */
	public static final DatePattern YEAR = new DatePattern("yyyy");
			
	/** 月 */
	public static final DatePattern MONTH = new DatePattern("yyyy-MM");
			
	/** 日 */
	public static final DatePattern DAY = new DatePattern("yyyy-MM-dd");
			
	/** 时 */
	public static final DatePattern HOUR = new DatePattern("yyyy-MM-dd HH");
	
	/** 分 */
	public static final DatePattern MINUTE = new DatePattern("yyyy-MM-dd HH:mm");
			
	/** 日期+精确到秒的时间 */
	public static final DatePattern DATETIME = new DatePattern("yyyy-MM-dd HH:mm:ss");
			
	/** 日期+精确到毫秒的时间 */
	public static final DatePattern DATETIME_PLUS = new DatePattern("yyyy-MM-dd HH:mm:ss:SSS");
	
}
