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

import java.util.Map;
import java.util.Set;

import org.workin.beans.parameter.MapParameter;
import org.workin.beans.parameter.Parameter;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;

/**
 * Map对象与org.workin.beans.parameter.Parameter对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapToParameterMapper<V> extends AbstractMapper<Map<String, V>, Parameter<String, V>> {
	
	public MapToParameterMapper() {
		super();
	}
	
	public MapToParameterMapper(Set<MapperRule> mapperRules) {
		super(mapperRules);
	}

	@Override
	public Parameter<String, V> mapping(Map<String, V> source) {
		if (MapUtils.isEmpty(source))
			return null;
		
		Parameter<String, V> parameter = new MapParameter<String, V>();
		if (CollectionUtils.isNotEmpty((mapperRules))) {
			for (MapperRule rule : mapperRules) {
				parameter.add(rule.getMappedName(), source.get(rule.getOriginalName()));
			}
		} else
			parameter.setParameters(source);
		
		return parameter;
	}
	
}
