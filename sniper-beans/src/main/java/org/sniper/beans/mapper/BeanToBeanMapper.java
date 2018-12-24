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
import java.util.List;
import java.util.Set;

import org.sniper.beans.BeanUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ObjectUtils;

/**
 * Java Bean对象与Java Bean对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BeanToBeanMapper extends AbstractBeanMapper<Object>  {
		
	@Override
	protected <T> T doMapping(Object source, Set<MapperRule> mapperRules, Class<T> type) throws Exception {
		if (source == null)
			return null;
		
		T mappedBean = createMappedBean(type);
		
		if (CollectionUtils.isNotEmpty(mapperRules)) {
			if (isAutoMapping()) {
				// 需要自动映射的属性名称集
				List<String> autoMappedNames = BeanUtils.findPropertyNamesByGetter(source);
				
				for (MapperRule rule : mapperRules) {
					ruleMapping(source, mappedBean, rule);
					// 删除已完成映射的属性名称
					autoMappedNames.remove(rule.getOriginalName());
				}
				
				/* 完成规则外的映射 */
				for (String mappedName : autoMappedNames) {
					BeanUtils.setPropertyValue(mappedBean, mappedName, BeanUtils.getPropertyValue(source, mappedName));
				}
			} else {
				for (MapperRule rule : mapperRules) {
					ruleMapping(source, mappedBean, rule);
				}
			}
		} else {
			List<String> propertyNames = BeanUtils.findPropertyNamesByGetter(mappedBean);
			for (String propertyName : propertyNames) {
				BeanUtils.setPropertyValue(mappedBean, propertyName, BeanUtils.getPropertyValue(source, propertyName));
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
	private <T> void ruleMapping(Object source, T mappedBean, MapperRule rule) throws Exception {
		PropertyEditor propertyEditor = rule.getPropertyEditor();
		if (propertyEditor != null) {
//			propertyEditor.setValue(BeanUtils.get(source, rule.getOriginalName()));
			propertyEditor.setAsText(ObjectUtils.toString(BeanUtils.getPropertyValue(source, rule.getOriginalName())));
			BeanUtils.setPropertyValue(mappedBean, rule.getMappedName(), propertyEditor.getValue());
		} else {
			BeanUtils.setPropertyValue(mappedBean, rule.getMappedName(), BeanUtils.getPropertyValue(source, rule.getOriginalName()));
		}
	}

}
