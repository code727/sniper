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

import java.util.Set;

import org.workin.beans.BeanUtils;
import org.workin.beans.parameter.Parameter;
import org.workin.beans.parameter.ParameterUtils;
import org.workin.commons.util.CollectionUtils;

/**
 * org.workin.beans.parameter.Parameter对象与Java Bean对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParameterToBeanMapper<V, T> extends AbstractBeanMapper<Parameter<String, V>, T> {
	
	public ParameterToBeanMapper(String beanType) throws ClassNotFoundException {
		super(beanType);
	}
	
	public ParameterToBeanMapper(Class<T> beanClass) {
		super(beanClass);
	}
	
	public ParameterToBeanMapper(String beanType, Set<MapperRule> mapperRules) throws ClassNotFoundException {
		super(beanType, mapperRules);
	}
	
	public ParameterToBeanMapper(Class<T> beanClass, Set<MapperRule> mapperRules) {
		super(beanClass, mapperRules);
	}

	@Override
	public T mapping(Parameter<String, V> source) throws Exception {
		if (ParameterUtils.isEmpty(source))
			return null;
		
		T mappedBean = createMappedBean();
		if (CollectionUtils.isNotEmpty(mapperRules)) {
			for (MapperRule rule : mapperRules) {
				BeanUtils.set(mappedBean, rule.getMappedName(), source.getValue(rule.getOriginalName()));
			}
		} else {
			/* 当规则集为空时，则将源对象中所有的参数映射到目标对象中 */
			Set<String> names = source.getNames();
			for (String name : names) {
				BeanUtils.set(mappedBean, name, source.getValue(name));
			}
		}
		
		return mappedBean;
	}

}
