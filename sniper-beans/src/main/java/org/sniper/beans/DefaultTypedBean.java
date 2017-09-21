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

package org.sniper.beans;

import org.sniper.commons.util.AssertUtils;

/**
 * 类型化Bean默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultTypedBean implements Typed {
		
	/** 类型 */
	private Class<?> type;
	
	public DefaultTypedBean() {
		this(Object.class);
	}
		
	public DefaultTypedBean(Class<?> type) {
		setType(type);
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public void setType(Class<?> type) {
		AssertUtils.assertNotNull(type, "Type must not be null or blank");
		this.type = type;
	}

}
