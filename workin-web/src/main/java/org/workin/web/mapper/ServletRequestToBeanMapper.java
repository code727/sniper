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

import org.workin.beans.mapper.AbstractBeanMapper;
import org.workin.beans.mapper.MapToBeanMapper;
import org.workin.beans.mapper.Mapper;
import org.workin.beans.mapper.MapperRule;
import org.workin.web.WebUtils;

/**
 * javax.servlet.ServletRequest对象与Java Bean对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ServletRequestToBeanMapper<T> extends AbstractBeanMapper<ServletRequest, T> {
	
	private Mapper<Map<String, String>, T> mapper;
	
	public ServletRequestToBeanMapper(String beanType) throws ClassNotFoundException {
		this(beanType, null);
	}
	
	public ServletRequestToBeanMapper(Class<T> beanClass) {
		this(beanClass, null);
	}
	
	@SuppressWarnings("unchecked")
	public ServletRequestToBeanMapper(String beanType, Set<MapperRule> mapperRules) throws ClassNotFoundException {
		this((Class<T>) Class.forName(beanType), mapperRules);
	}
	
	public ServletRequestToBeanMapper(Class<T> beanClass, Set<MapperRule> mapperRules) {
		super(beanClass, mapperRules);
		this.mapper = new MapToBeanMapper<String, T>(beanClass, mapperRules);
	}
	
	@Override
	public void setMapperRules(Set<MapperRule> mapperRules) {
		this.mapper.setMapperRules(mapperRules);
	}
	
	@Override
	public T mapping(ServletRequest source) throws Exception {
		if (source == null)
			return null;
		
		return mapper.mapping(WebUtils.getParameters(source));
	}

}
