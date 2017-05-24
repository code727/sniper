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

package org.workin.beans.mapper;

import java.util.Map;

import org.workin.beans.BeanUtils;
import org.workin.beans.parameter.ConcurrentParameter;
import org.workin.beans.parameter.Parameter;
import org.workin.commons.util.CollectionUtils;

/**
 * Java Bean对象与org.workin.support.parameter.Parameter对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BeanToParameterMapper<T, V> extends AbstractMapper<T, Parameter<String, V>> {

	@SuppressWarnings("unchecked")
	@Override
	public Parameter<String, V> mapping(T source) throws Exception {
		if (source == null)
			return null;
		
		Parameter<String, V> parameter = new ConcurrentParameter<String, V>();
		if (CollectionUtils.isNotEmpty(parameterRules)) {
			for (ParameterRule rule : parameterRules) 
				parameter.add(rule.getMappedName(), (V) BeanUtils.get(source, rule.getOriginalName()));
		} else
			parameter.setParameters((Map<String, V>) BeanUtils.create(source));
		
		return parameter;
	}

}
