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
 * Create Date : 2018-10-26
 */

package org.sniper.commons.enums.date;

/**
 * 日期模式枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum DatePattern {
	
	/** 年 */
	YEAR("yyyy", "yyyy"),
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
	DATETIME_PLUS("yyyy-MM-dd HH:mm:ss:SSS", "yyyyMMddHHmmssSSS")
	;
	
	/** 默认格式 */
	private String defaultFormat;
	
	/** 序列格式 */
	private String sequenceFormat;
	
	private DatePattern(String defaultFormat, String sequenceFormat) {
		this.defaultFormat = defaultFormat;
		this.sequenceFormat = sequenceFormat;
	}

	public String getDefaultFormat() {
		return defaultFormat;
	}

	public String getSequenceFormat() {
		return sequenceFormat;
	}
	
}
