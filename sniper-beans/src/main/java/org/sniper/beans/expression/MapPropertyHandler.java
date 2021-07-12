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

import org.sniper.commons.util.StringUtils;

/**
 * Map属性处理器
 * @author  Daniele
 * @version 1.0
 */
public class MapPropertyHandler implements PropertyHandler<Map<String, Object>> {

	@Override
	public boolean support(Object obj, String propertyName) {
		return obj instanceof Map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getPropertyValue(Map<String, Object> map, String propertyName) throws Exception {
		String mappedPropertyName = StringUtils.leftSubstring(propertyName, MAPPED_START, MAPPED_END);
		return (V) (StringUtils.isNotEmpty(mappedPropertyName) ? map.get(mappedPropertyName) : map.get(propertyName));
	}

	@Override
	public <V> V getConstructedPropertyValue(Map<String, Object> map, String propertyName) throws Exception {
		return getPropertyValue(map, propertyName);
	}

	@Override
	public void setPropertyValue(Map<String, Object> map, String propertyName, Class<?> propertyType,
			Object propertyValue) throws Exception {
		
		String mappedPropertyName = StringUtils.leftSubstring(propertyName, MAPPED_START, MAPPED_END);
		if (StringUtils.isNotEmpty(mappedPropertyName))
			map.put(mappedPropertyName, propertyValue);
		else
			map.put(propertyName, propertyValue);
	}

}
