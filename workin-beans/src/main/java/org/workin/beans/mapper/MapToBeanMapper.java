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

import java.beans.PropertyEditor;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.workin.beans.BeanUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.ObjectUtils;

/**
 * Map对象与Java Bean对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapToBeanMapper<V> extends AbstractBeanMapper<Map<String, V>> {
	
	@Override
	protected <T> T doMapping(Map<String, V> source, Set<MapperRule> mapperRules, Class<T> type) throws Exception {
		if (MapUtils.isEmpty(source))
			return null;
		
		T mappedBean = createMappedBean(type);
		
		if (CollectionUtils.isNotEmpty(mapperRules)) {
			if (isAutoMapping()) {
				// 需要自动映射的参数名称集
				Set<String> autoMappedNames = source.keySet();
				
				for (MapperRule rule : mapperRules) {
					ruleMapping(source, mappedBean, rule);
					// 删除已完成映射的参数名称
					autoMappedNames.remove(rule.getOriginalName());
				}
				
				/* 完成规则外的映射 */
				for (String mappedName : autoMappedNames) {
					BeanUtils.set(mappedBean, mappedName, source.get(mappedName));
				}
			} else {
				for (MapperRule rule : mapperRules) {
					ruleMapping(source, mappedBean, rule);
				}
			}
		} else {
			/* 当规则集为空时，则将源对象中所有的参数映射到目标对象中 */
			Iterator<Entry<String, V>> iterator = source.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, V> entry = iterator.next();
				BeanUtils.set(mappedBean, entry.getKey(), entry.getValue());
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
	private <T> void ruleMapping(Map<String, V> source, T mappedBean, MapperRule rule) throws Exception {
		PropertyEditor propertyEditor = rule.getPropertyEditor();
		if (propertyEditor != null) {
//			propertyEditor.setValue(source.get(rule.getOriginalName()));
			propertyEditor.setAsText(ObjectUtils.toString(source.get(rule.getOriginalName())));
			BeanUtils.set(mappedBean, rule.getMappedName(), propertyEditor.getValue());
		} else {
			BeanUtils.set(mappedBean, rule.getMappedName(), source.get(rule.getOriginalName()));
		}
	}
	
}
