/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-3-6
 */

package org.sniper.beans;

import java.beans.PropertyEditor;
import java.text.MessageFormat;

import org.sniper.commons.util.AssertUtils;

/**
 * 属性工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PropertyUtils {
	
	/**
	 * 使用指定的属性编辑器将一个值转化成目标类型的结果，当属性编辑器为空时，则直接抛出空指针异常
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyEditor
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converte(PropertyEditor propertyEditor, Object value) {
		if (value instanceof CharSequence) 
			propertyEditor.setAsText(value.toString());
		else
			propertyEditor.setValue(value);
		
		return (T) propertyEditor.getValue();
	}
	
	/**
	 * 使用指定的属性编辑器将一个值转化成目标类型的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyEditor
	 * @param value
	 * @param targetType
	 * @return
	 */
	public static <T> T converte(PropertyEditor propertyEditor, Object value, Class<T> targetType) {
		AssertUtils.assertNotNull(propertyEditor, MessageFormat.format(
				"Property editor must not be null for target type [{0}]", targetType));
		return converte(propertyEditor, value);
	}

}
