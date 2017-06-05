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
import java.util.Set;

import org.workin.beans.BeanUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;

/**
 * Java Bean对象与Java Bean对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BeanToBeanMapper extends AbstractBeanMapper<Object>  {
		
	@Override
	public <T> T mapping(Object source, Set<MapperRule> mapperRules, Class<T> type) throws Exception {
		AssertUtils.assertNotNull(type, "Mapped bean type must not be null");
		
		if (source == null)
			return null;
		
		T mappedBean = createMappedBean(type);
		
		if (CollectionUtils.isNotEmpty(mapperRules)) {
			if (isAutoMapping()) {
				// 需要自动映射的属性名称集
				List<String> autoMappedNames = BeanUtils.findAllPropertyNameByGetter(source);
				
				for (MapperRule rule : mapperRules) {
					BeanUtils.set(mappedBean, rule.getMappedName(), BeanUtils.get(source, rule.getOriginalName()));
					// 删除已完成映射的属性名称
					autoMappedNames.remove(rule.getOriginalName());
				}
				
				/* 完成规则外的映射 */
				for (String mappedName : autoMappedNames) 
					BeanUtils.set(mappedBean, mappedName, BeanUtils.get(source, mappedName));
					
			} else {
				for (MapperRule rule : mapperRules) 
					BeanUtils.set(mappedBean, rule.getMappedName(), BeanUtils.get(source, rule.getOriginalName()));
			}
		} else {
			List<String> propertyNames = BeanUtils.findAllPropertyNameByGetter(mappedBean);
			for (String propertyName : propertyNames) {
				BeanUtils.set(mappedBean, propertyName, BeanUtils.get(source, propertyName));
			}
		}
		return mappedBean;
	}

}
