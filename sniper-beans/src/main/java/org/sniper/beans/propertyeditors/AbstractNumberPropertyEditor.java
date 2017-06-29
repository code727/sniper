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

package org.sniper.beans.propertyeditors;

import org.sniper.commons.util.StringUtils;

/**
 * 数字属性编辑器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractNumberPropertyEditor extends AbstractPropertyEditor {
	
	protected static final String NUMBER_DEFAULT_VALUE = "0";
	
	protected AbstractNumberPropertyEditor() {
		this(true);
	}
	
	protected AbstractNumberPropertyEditor(boolean allowEmpty) {
		this(allowEmpty, NUMBER_DEFAULT_VALUE);
	}
	
	protected AbstractNumberPropertyEditor(String defaultValue) {
		this(true, defaultValue);
	}
	
	protected AbstractNumberPropertyEditor(boolean allowEmpty, String defaultValue) {
		super(allowEmpty, defaultValue);
	}
	
	/**
	 * 由于字符串的数字要求不能为空白，因此重写父类方法，覆盖掉父类中的检查方式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param defaultValue
	 * @throws IllegalArgumentException
	 */
	@Override
	protected void check(String defaultValue) throws IllegalArgumentException {
		if (StringUtils.isBlank(defaultValue))
			throw new IllegalArgumentException("Number default value must not be null or blank");
	}
	
	/**
	 * 由于字符串的数字要求不能为空白，因此重写父类方法，覆盖掉父类中的判空方式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text))
			setValue(handleText(text.trim()));
		else {
			if (!isAllowEmpty())
				setValue(handleText(getDefaultValue().trim()));
			else
				setValue(null);
		}
	}
			
}
