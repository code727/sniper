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

package org.workin.beans.propertyeditors;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.StringUtils;

/**
 * 字符串属性编辑器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class StringPropertyEditor extends PropertyEditorSupport implements PropertyEditor {
	
	/** 是否允许为空 */
	private boolean allowEmpty;
	
	/** 不允许为空时指定的默认值 */
	private String defaultValue;
	
	public StringPropertyEditor() {
		this(true);
	}
	
	public StringPropertyEditor(boolean allowEmpty) {
		this(allowEmpty, StringUtils.EMPTY_STRING);
	}
	
	public StringPropertyEditor(String defaultValue) {
		this(true, defaultValue);
	}
	
	public StringPropertyEditor(boolean allowEmpty, String defaultValue) {
		setAllowEmpty(allowEmpty);
		setDefaultValue(defaultValue);
	}
	
	public boolean isAllowEmpty() {
		return allowEmpty;
	}

	public void setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		check(defaultValue);
		this.defaultValue = defaultValue;
	}
	
	/**
	 * 检查默认值的合法性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param defaultValue
	 * @throws IllegalArgumentException
	 */
	protected void check(String defaultValue) throws IllegalArgumentException {
		AssertUtils.assertNotNull(defaultValue, "String default value must not be null");
	}
		
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null) {
			setValue(handleText(text));
		} else {
			if (!isAllowEmpty())
				setValue(handleText(defaultValue));
			else
				setValue(text);
		}
	}

	/**
	 * 非空文本的处理方法，返回处理后的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @return
	 */
	protected Object handleText(String text) {
		return text;
	}
					
}
