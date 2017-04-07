/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-6-26
 */

package org.workin.spring.beans.propertyeditors;

import java.util.Date;

import org.workin.commons.util.DateUtils;
import org.workin.commons.util.StringUtils;

/**
 * 日期属性编辑器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DatePropertyEditor extends StringPropertyEditor {
	
	/** 日期格式 */
	private String pattern;
	
	public DatePropertyEditor() {
		
	}
	
	public DatePropertyEditor(String pattern) {
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	protected void check() throws Exception {
		if (!this.isAllowEmpty() && StringUtils.isBlank(this.getDefaultValue()))
			throw new IllegalArgumentException("Date default value must not be null or blank when 'allowEmpty' is false.");
	}
		
	@Override
	protected Object handleText(String text) {
		// 格式不匹配时，仍然会返回null
		return DateUtils.stringToDate(text, this.pattern);
	}
	
	/**
	 * 覆盖父类getDefaultValue()方法，当文本值为空时，默认值取当前时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public String getDefaultValue() {
		return DateUtils.dateToString(new Date(), this.pattern);
	}
		
}
