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

import java.util.Map;

import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.support.mapper.AbstractMapper;
import org.workin.support.mapper.ParameterRule;
import org.workin.support.parameter.Parameter;
import org.workin.support.parameter.ParameterUtils;

/**
 *  org.workin.support.parameter.Parameter对象与Map对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParameterToMapMapper<V> extends
		AbstractMapper<Parameter<String, V>, Map<String, V>> {

	@Override
	public Map<String, V> mapping(Parameter<String, V> source) {
		if (ParameterUtils.isEmpty(source))
			return null;
		
		 Map<String, V> map = null;
		 if (CollectionUtils.isNotEmpty(parameterRules)) {
			 map = MapUtils.newHashMap();
			 for (ParameterRule rule : parameterRules) 
				 map.put(rule.getMappedName(), source.getValue(rule.getOriginalName()));
		 } else
			 map = source.getParameters();
		 
		 return map;
	}

}
