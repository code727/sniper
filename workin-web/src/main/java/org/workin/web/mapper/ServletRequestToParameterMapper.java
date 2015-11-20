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
 * Create Date : 2015-11-16
 */

package org.workin.web.mapper;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.workin.support.mapper.AbstractMapper;
import org.workin.support.mapper.Mapper;
import org.workin.support.mapper.ParameterRule;
import org.workin.support.mapper.impl.MapToParameterMapper;
import org.workin.support.parameter.Parameter;

/**
 * @description javax.servlet.ServletRequest对象与org.workin.support.parameter.Parameter对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ServletRequestToParameterMapper<V> extends AbstractMapper<ServletRequest, Parameter<String, V>>{

	private Mapper<Map<String, V>, Parameter<String, V>> mapper;
	
	public ServletRequestToParameterMapper() {
		this.mapper = new MapToParameterMapper<V>();
	}
	
	@Override
	public void setParameterRules(Set<ParameterRule> parameterRules) {
		this.mapper.setParameterRules(parameterRules);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Parameter<String, V> mapping(ServletRequest source) throws Exception {
		if (source == null)
			return null;
		
		return mapper.mapping(source.getParameterMap());
	}

}
