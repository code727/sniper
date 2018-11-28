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
 * 日期序列模式常量
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DateSequencePattern extends AbstractConstant<String, DateFormat> {
	
	private static final long serialVersionUID = 3224867467862743973L;

	protected DateSequencePattern(String key) {
		this(key, null);
	}
	
	protected DateSequencePattern(String key, DateFormat value) {
		super(key, value != null ? value : new SimpleDateFormat(key));
	}

	/** 年 */
	public static final DateSequencePattern YEAR = new DateSequencePattern("yyyy");
			
	/** 月 */
	public static final DateSequencePattern MONTH = new DateSequencePattern("yyyyMM");
			
	/** 日 */
	public static final DateSequencePattern DAY = new DateSequencePattern("yyyyMMdd");
			
	/** 时 */
	public static final DateSequencePattern HOUR = new DateSequencePattern("yyyyMMddHH");
	
	/** 分 */
	public static final DateSequencePattern MINUTE = new DateSequencePattern("yyyyMMddHHmm");
			
	/** 日期+精确到秒的时间 */
	public static final DateSequencePattern DATETIME = new DateSequencePattern("yyyyMMddHHmmss");
			
	/** 日期+精确到毫秒的时间 */
	public static final DateSequencePattern DATETIME_PLUS = new DateSequencePattern("yyyyMMddHHmmssSSS");
	
}
