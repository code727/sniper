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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.workin.beans.BeanUtils;
import org.workin.beans.parameter.MapParameter;
import org.workin.beans.parameter.Parameter;
import org.workin.commons.util.CollectionUtils;

/**
 * Java Bean对象与org.workin.beans.parameter.Parameter对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BeanToParameterMapper<V> extends AbstractMapper<Object, Parameter<String, V>> {
	
	@SuppressWarnings("unchecked")
	@Override
	public Parameter<String, V> mapping(Object source, Set<MapperRule> mapperRules) throws Exception {
		if (source == null)
			return null;
		
		Parameter<String, V> parameter = new MapParameter<String, V>();
		
		if (CollectionUtils.isNotEmpty(mapperRules)) {
			if (isAutoMapping()) {
				// 需要自动映射的属性名称集
				List<String> autoMappedNames = BeanUtils.findAllPropertyNameByGetter(source);
				
				for (MapperRule rule : mapperRules) {
					parameter.add(rule.getMappedName(), (V) BeanUtils.get(source, rule.getOriginalName()));
					// 删除已完成映射的属性名称
					autoMappedNames.remove(rule.getOriginalName());
				}
					
				/* 完成规则外的映射 */
				for (String mappedName : autoMappedNames) 
					parameter.add(mappedName, (V) BeanUtils.get(source, mappedName));
				
			} else {
				for (MapperRule rule : mapperRules) 
					parameter.add(rule.getMappedName(), (V) BeanUtils.get(source, rule.getOriginalName()));
			}
		} else
			parameter.setParameters((Map<String, V>) BeanUtils.create(source));
		
		return parameter;
	}

}
