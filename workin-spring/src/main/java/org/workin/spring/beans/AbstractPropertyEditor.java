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

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.InitializingBean;

/**
 * @description 属性编辑器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractPropertyEditor extends PropertyEditorSupport
		implements PropertyEditor, InitializingBean {
	
	/** 是否允许为空 */
	private boolean allowEmpty = true;
	
	/** 不允许为空时指定的默认值 */
	protected String defaultValue;

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
		// 当不允许为空而默认值为空时，则抛出异常
		if (!this.allowEmpty && this.defaultValue == null)
			throw new IllegalArgumentException(
					"Default value can not be null when 'allowEmpty' is false.");
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null)
			setValue(handleText(text));
		else {
			if (!this.allowEmpty)
				setValue(this.defaultValue);
			else
				setValue(text);
		}
	}

	/**
	 * @description 处理文本，并返回处理后的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @return
	 */
	protected abstract Object handleText(String text);

}
