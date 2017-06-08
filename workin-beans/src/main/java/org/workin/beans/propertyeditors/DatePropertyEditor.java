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

package org.workin.beans.propertyeditors;

import java.util.Date;

import org.workin.commons.util.DateUtils;
import org.workin.commons.util.RegexUtils;
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
		this(true);
	}
	
	public DatePropertyEditor(boolean allowEmpty) {
		this(allowEmpty, null);
	}
	
	public DatePropertyEditor(String pattern) {
		this(true, pattern);
	}
		
	public DatePropertyEditor(boolean allowEmpty, String pattern) {
		super(allowEmpty);
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			setValue(handleText(text));
		} else {
			if (!isAllowEmpty())
				// 默认值可以为一个空字符串，当调用handleText时，则返回当前日期对象
				setValue(handleText(getDefaultValue()));
			else
				setValue(null);
		}
	}
			
	@Override
	protected Object handleText(String text) {
		if (RegexUtils.isInteger(text))
			return DateUtils.timeToDate(Long.valueOf(text));
		
		// parse的文本格式不匹配时，仍然会返回null
		return StringUtils.isNotBlank(text) ? DateUtils.stringToDate(text, pattern) : new Date();
	}
					
}
