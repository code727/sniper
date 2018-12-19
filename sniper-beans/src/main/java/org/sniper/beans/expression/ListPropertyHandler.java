/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-12-19
 */

package org.sniper.beans.expression;

import java.util.List;

import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 列表属性处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ListPropertyHandler implements PropertyHandler<List<Object>> {

	@Override
	public boolean support(Object obj, String propertyName) {
		return ClassUtils.isList(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getPropertyValue(List<Object> list, String propertyName) throws Exception {
		String mappedPropertyName = StringUtils.leftSubstring(propertyName, INDEXED_START, INDEXED_END);
		int index = (StringUtils.isNotEmpty(mappedPropertyName) ? 
				Integer.parseInt(mappedPropertyName) : Integer.parseInt(propertyName));
		
		return (V) list.get(index);
	}

	@Override
	public <V> V getConstructedPropertyValue(List<Object> list, String propertyName) throws Exception {
		return getPropertyValue(list, propertyName);
	}

	@Override
	public void setPropertyValue(List<Object> list, String propertyName, Class<?> propertyType, Object propertyValue) throws Exception {
		String mappedPropertyName = StringUtils.leftSubstring(propertyName, INDEXED_START, INDEXED_END);
		int index = (StringUtils.isNotEmpty(mappedPropertyName) ? 
				Integer.parseInt(mappedPropertyName) : Integer.parseInt(propertyName));

		CollectionUtils.merge(list, index, propertyValue);
	}
	
}
