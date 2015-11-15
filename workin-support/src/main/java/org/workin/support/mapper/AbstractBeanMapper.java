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
 * Create Date : 2015-11-15
 */

package org.workin.support.mapper;

import java.util.Set;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;


/**
 * @description Java Bean映射器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractBeanMapper<T, R> extends AbstractMapper<T, R> {
	
	/** 被映射的目标Bean类型 */
	private String type;
	
	public AbstractBeanMapper(String type) {
		this(type, null);
	}
	
	public AbstractBeanMapper(String type, Set<ParameterRule> parameterRules) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(type), "Mapped bean type must not be null or blank.");
		this.type = type;
		this.parameterRules = parameterRules;
	}

	public String getType() {
		return type;
	}

	/**
	 * @description 根据被映射的目标Bean对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected R createMappedBean() throws Exception {
		return ReflectionUtils.newInstance(this.type);
	}
	
}
