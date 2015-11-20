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

import org.workin.support.mapper.AbstractBeanMapper;
import org.workin.support.mapper.Mapper;
import org.workin.support.mapper.ParameterRule;
import org.workin.support.mapper.impl.MapToBeanMapper;

/**
 * @description javax.servlet.ServletRequest对象与Java Bean对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ServletRequestToBeanMapper<V, R> extends AbstractBeanMapper<ServletRequest, R> {
	
	private Mapper<Map<String, V>, R> mapper;

	public ServletRequestToBeanMapper(String type) {
		super(type);
		this.mapper = new MapToBeanMapper<V, R>(type);
	}
	
	@Override
	public void setParameterRules(Set<ParameterRule> parameterRules) {
		this.mapper.setParameterRules(parameterRules);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public R mapping(ServletRequest source) throws Exception {
		if (source == null)
			return null;
		
		return mapper.mapping(source.getParameterMap());
	}

}
