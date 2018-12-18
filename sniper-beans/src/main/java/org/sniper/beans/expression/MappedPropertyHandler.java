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
 * Create Date : 2018-12-18
 */

package org.sniper.beans.expression;

import java.util.Map;

import org.sniper.beans.parameter.Parameters;
import org.sniper.commons.exception.NestedNullPointerException;
import org.sniper.commons.util.StringUtils;

/**
 * 已映射的属性处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MappedPropertyHandler implements PropertyHandler<Object> {
	
	protected final BeanPropertyHandler beanPropertyHandler = new BeanPropertyHandler();

	@Override
	public boolean support(Object obj, String propertyName) {
		int startIndex = propertyName.indexOf(MAPPED_START);
		int endIndex = propertyName.indexOf(MAPPED_END);
		return startIndex > -1 && endIndex > -1 && startIndex < endIndex;
	}

	@Override
	public <V> V getPropertyValue(Object obj, String propertyName) throws Exception {
		String mappedPropertyName = StringUtils.beforeFrist(propertyName, MAPPED_START);
		Object mappedPropertyValue = beanPropertyHandler.getPropertyValue(obj, mappedPropertyName);
		return getMappedValue(mappedPropertyValue, mappedPropertyName);
	}

	@Override
	public <V> V getConstructedPropertyValue(Object obj, String propertyName) throws Exception {
		String mappedPropertyName = StringUtils.beforeFrist(propertyName, MAPPED_START);
		Object mappedPropertyValue = beanPropertyHandler.getConstructedPropertyValue(obj, mappedPropertyName);
		return getMappedValue(mappedPropertyValue, mappedPropertyName);
	}
	
	@SuppressWarnings("unchecked")
	protected <V> V getMappedValue(Object mappedPropertyValue, String propertyName) {
		if (mappedPropertyValue == null)
			return null;
		
		String key = StringUtils.leftSubstring(propertyName, MAPPED_START, MAPPED_END);
		if (mappedPropertyValue instanceof Map) 
			return ((Map<String, V>) mappedPropertyValue).get(key);
		
		if (mappedPropertyValue instanceof Parameters)
			return ((Parameters<String, V>) mappedPropertyValue).getValue(key);
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPropertyValue(Object obj, String propertyName, Class<?> propertyType, Object propertyValue) throws Exception {
		String mappedPropertyName = StringUtils.beforeFrist(propertyName, MAPPED_START);
		Object mappedPropertyValue = beanPropertyHandler.getConstructedPropertyValue(obj, mappedPropertyName);
		
		if (mappedPropertyValue == null)
			throw new NestedNullPointerException();
		
		String key = StringUtils.leftSubstring(propertyName, MAPPED_START, MAPPED_END);
		if (mappedPropertyValue instanceof Map) 
			((Map<String, Object>) mappedPropertyValue).put(key, propertyValue);
		else if (mappedPropertyValue instanceof Parameters)
			((Parameters<String, Object>) mappedPropertyValue).add(key, propertyValue);
	}

}
