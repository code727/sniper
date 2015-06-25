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
 * Create Date : 2015-6-16
 */

package org.workin.spring.beans;

import org.workin.commons.util.StringUtils;

/**
 * @description 数字属性编辑器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class NumberPropertyEditor extends AbstractPropertyEditor {
	
	public NumberPropertyEditor() {
		this.defaultValue = "0";
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// 当不允许为空而默认数字字符串为空白字符串时，则抛出异常
		if (!this.isAllowEmpty() && StringUtils.isBlank(this.getDefaultValue()))
			throw new IllegalArgumentException(
					"Default value can not be null or blank when 'allowEmpty' is false.");
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text))
			setValue(handleText(text));
		else {
			if (!this.isAllowEmpty())
				setValue(this.getDefaultValue());
			else
				setValue(text);
		}
	}
		
	@Override
	public String getAsText() {
		return StringUtils.toString(this.getValue()).trim();
	}
	
}
