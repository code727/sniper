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

package org.sniper.beans.mapper;

import java.beans.PropertyEditor;
import java.util.Set;

import org.sniper.beans.BeanUtils;
import org.sniper.beans.parameter.Parameters;
import org.sniper.beans.parameter.ParametersUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ObjectUtils;

/**
 * org.sniper.beans.parameter.Parameters对象与Java Bean对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParametersToBeanMapper<V> extends AbstractBeanMapper<Parameters<String, V>> {
	
	@Override
	protected <T> T doMapping(Parameters<String, V> parameters, Set<MapperRule> mapperRules, Class<T> type) throws Exception {
		if (ParametersUtils.isEmpty(parameters))
			return null;
		
		T mappedBean = createMappedBean(type);
		
		if (CollectionUtils.isNotEmpty(mapperRules)) {
			if (isAutoMapping()) {
				// 需要自动映射的参数名称集
				Set<String> autoMappedNames = parameters.getNames();
				
				for (MapperRule rule : mapperRules) {
					ruleMapping(parameters, mappedBean, rule);
					// 删除已完成映射的参数名称
					autoMappedNames.remove(rule.getOriginalName());
				}
				
				/* 完成规则外的映射 */
				for (String mappedName : autoMappedNames) {
					BeanUtils.set(mappedBean, mappedName, parameters.getValue(mappedName));
				}
			} else {
				for (MapperRule rule : mapperRules) {
					ruleMapping(parameters, mappedBean, rule);
				}
			}
		} else {
			/* 当规则集为空时，则将源对象中所有的参数映射到目标对象中 */
			Set<String> names = parameters.getNames();
			for (String name : names) {
				BeanUtils.set(mappedBean, name, parameters.getValue(name));
			}
		}
		
		return mappedBean;
	}
	
	/**
	 * 规则内映射处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param source
	 * @param mappedBean
	 * @param rule
	 * @throws Exception
	 */
	private <T> void ruleMapping(Parameters<String, V> source, T mappedBean, MapperRule rule) throws Exception {
		PropertyEditor propertyEditor = rule.getPropertyEditor();
		if (propertyEditor != null) {
//			propertyEditor.setValue(source.getValue(rule.getOriginalName()));
			propertyEditor.setAsText(ObjectUtils.toString(source.getValue(rule.getOriginalName())));
			BeanUtils.set(mappedBean, rule.getMappedName(), propertyEditor.getValue());
		} else {
			BeanUtils.set(mappedBean, rule.getMappedName(), source.getValue(rule.getOriginalName()));
		}
	}

}
