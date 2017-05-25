/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-5-25
 */

package org.workin.beans;

import org.workin.commons.util.AssertUtils;

/**
 * 类型化Bean默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultTypedBean implements Typed {
	
	/** 类型路径限定名 */
	private String typeClass;
	
	/** 类型 */
	private Class<?> type;
	
	public DefaultTypedBean() {
		this(Object.class);
	}
	
	public DefaultTypedBean(String typeClass) throws Exception {
		setTypeClass(typeClass);
	}
	
	public DefaultTypedBean(Class<?> type) {
		setType(type);
	}

	@Override
	public String getTypeClass() {
		return typeClass;
	}

	@Override
	public void setTypeClass(String typeClass) throws Exception {
		AssertUtils.assertNotBlank(typeClass, "Type class must not be null or blank");
		
		// 先尝试加载类型
		this.type = Class.forName(typeClass);
		this.typeClass = typeClass;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public void setType(Class<?> type) {
		AssertUtils.assertNotNull(type, "Type must not be null or blank");
		
		this.type = type;
		this.typeClass = type.getName();
	}

}
