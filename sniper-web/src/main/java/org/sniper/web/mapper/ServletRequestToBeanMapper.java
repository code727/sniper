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

package org.sniper.web.mapper;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.sniper.beans.mapper.AbstractBeanMapper;
import org.sniper.beans.mapper.BeanMapper;
import org.sniper.beans.mapper.MapToBeanMapper;
import org.sniper.beans.mapper.MapperRule;
import org.sniper.web.WebUtils;

/**
 * javax.servlet.ServletRequest对象与Java Bean对象之间的映射转换
 * @author  Daniele
 * @version 1.0
 */
public class ServletRequestToBeanMapper extends AbstractBeanMapper<ServletRequest> {
	
	private BeanMapper<Map<String, String>> mapper;
	
	public ServletRequestToBeanMapper() {
		this.mapper = new MapToBeanMapper<String>();
	}
	
	@Override
	public void setMapperRules(Set<MapperRule> mapperRules) {
		this.mapper.setMapperRules(mapperRules);
	}
	
	@Override
	public Set<MapperRule> getMapperRules() {
		return this.mapper.getMapperRules();
	}
	
	@Override
	public void setAutoMapping(boolean autoMapping) {
		this.mapper.setAutoMapping(autoMapping);
	}
	
	@Override
	public boolean isAutoMapping() {
		return this.mapper.isAutoMapping();
	}
	
	@Override
	public <T> T doMapping(ServletRequest source, Set<MapperRule> mapperRules, Class<T> type) throws Exception {
		return mapper.mapping(WebUtils.getParameters(source), mapperRules, type);
	}
		
}
