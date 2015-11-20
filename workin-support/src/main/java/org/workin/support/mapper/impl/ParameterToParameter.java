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

package org.workin.support.mapper.impl;

import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.support.mapper.AbstractMapper;
import org.workin.support.mapper.ParameterRule;
import org.workin.support.parameter.ConcurrentParameter;
import org.workin.support.parameter.Parameter;
import org.workin.support.parameter.ParameterUtils;

/**
 * @description org.workin.support.parameter.Parameter对象与org.workin.support.parameter.Parameter对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParameterToParameter<V> extends AbstractMapper<Parameter<String,V>, Parameter<String,V>> {

	@Override
	public Parameter<String, V> mapping(Parameter<String, V> source) {
		if (ParameterUtils.isEmpty(source) || CollectionUtils.isEmpty((parameterRules)))
			return source;
		
		Parameter<String, V> parameter = new ConcurrentParameter<String, V>();
		for (ParameterRule rule : parameterRules) 
			parameter.add(rule.getMappedName(), source.getValue(rule.getOriginalName()));
		
		return parameter;
	}

}
