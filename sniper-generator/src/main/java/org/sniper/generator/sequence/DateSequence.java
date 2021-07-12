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
 * Create Date : 2017-11-13
 */

package org.sniper.generator.sequence;

import java.text.DateFormat;
import java.util.Date;

import org.sniper.commons.enums.date.DatePatternEnum;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.DateUtils;

/**
 * 日期序列实现类
 * @author  Daniele
 * @version 1.0
 */
public class DateSequence implements DimensionSequence<String> {
	
	private final DateFormat format;
		
	public DateSequence() {
		// 默认以"日"的维度
		this(DatePatternEnum.DAY); 
	}
	
	public DateSequence(DatePatternEnum datePattern) {
		AssertUtils.assertNotNull(datePattern, "Date pattern must not be null");
		this.format = DateUtils.getDateFormat(datePattern.getSequencePattern());
	}

	@Override
	public String update() {
		return format.format(new Date());
	}
					
}
