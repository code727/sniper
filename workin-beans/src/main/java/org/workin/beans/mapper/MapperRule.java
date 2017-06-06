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
 * Create Date : 2015-11-13
 */

package org.workin.beans.mapper;

import java.beans.PropertyEditor;

/**
 * 映射器规则
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapperRule {
	
	/** 原始参数名称 */
	private final String originalName;
	
	/** 被映射后的参数名称 */
	private final String mappedName;
	
	/** 参与原始参数值转换时所使用的PropertyEditor */
	private PropertyEditor propertyEditor;
	
	public MapperRule(String originalName, String mappedName) {
		this(originalName, mappedName, null);
	}
	
	public MapperRule(String originalName, String mappedName, PropertyEditor propertyEditor) {
		this.originalName = originalName;
		this.mappedName = mappedName;
		this.propertyEditor = propertyEditor;
	}

	public String getOriginalName() {
		return originalName;
	}

	public String getMappedName() {
		return mappedName;
	}

	public PropertyEditor getPropertyEditor() {
		return propertyEditor;
	}

	public void setPropertyEditor(PropertyEditor propertyEditor) {
		this.propertyEditor = propertyEditor;
	}

}