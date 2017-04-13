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

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.StringUtils;

/**
 * 字符串属性编辑器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class StringPropertyEditor extends PropertyEditorSupport
		implements PropertyEditor, InitializingBean {
	
	/** 是否允许为空，默认允许为空 */
	private boolean allowEmpty = true;
	
	/** 不允许为空时指定的默认值 */
	private String defaultValue = StringUtils.EMPTY_STRING;

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
		this.defaultValue = defaultValue;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		check();
	}
	
	/**
	 * 检查设置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws IllegalArgumentException
	 */
	protected void check() throws IllegalArgumentException {
		// 当不允许为空而默认值为null时，则抛出异常
		if (!this.isAllowEmpty() && this.getDefaultValue() == null)
			throw new IllegalArgumentException("String default value must not be null when 'allowEmpty' is false.");
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null)
			setValue(handleText(text));
		else {
			if (!this.isAllowEmpty())
				setValue(handleText(this.getDefaultValue()));
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
