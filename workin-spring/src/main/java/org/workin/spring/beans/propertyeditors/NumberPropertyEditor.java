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

package org.workin.spring.beans.propertyeditors;

import org.workin.commons.util.StringUtils;

/**
 * 数字属性编辑器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class NumberPropertyEditor extends StringPropertyEditor {
	
	public NumberPropertyEditor() {
		// 重新设置数字属性编辑器的默认值为0
		setDefaultValue("0");
	}
	
	@Override
	protected void check() throws IllegalArgumentException {
		// 当不允许为空而默认值为空白字符串时，则抛出异常
		if (!this.isAllowEmpty() && StringUtils.isBlank(this.getDefaultValue()))
			throw new IllegalArgumentException("Number default value must not be null or blank when 'allowEmpty' is false.");
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text))
			setValue(handleText(text));
		else {
			if (!this.isAllowEmpty())
				setValue(handleText(this.getDefaultValue()));
		}
	}
	
	@Override
	public String getAsText() {
		// 让左右两侧有空白的字符串也能进行数字转换
		return StringUtils.toString(this.getValue()).trim();
	}
	
}
