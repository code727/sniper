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

import org.sniper.beans.parameter.Parameters;
import org.sniper.commons.util.StringUtils;

/**
 * 泛型参数属性处理器
 * @author  Daniele
 * @version 1.0
 */
public class ParametersPropertyHandler implements PropertyHandler<Parameters<String, Object>> {

	@Override
	public boolean support(Object obj, String propertyName) {
		return obj instanceof Parameters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getPropertyValue(Parameters<String, Object> parameters, String propertyName) throws Exception {
		String mappedPropertyName = StringUtils.leftSubstring(propertyName, MAPPED_START, MAPPED_END);
		return (V) (StringUtils.isNotEmpty(mappedPropertyName) ? parameters.getValue(mappedPropertyName) : parameters.getValue(propertyName));
	}

	@Override
	public <V> V getConstructedPropertyValue(Parameters<String, Object> parameters, String propertyName) throws Exception {
		return getPropertyValue(parameters, propertyName);
	}

	@Override
	public void setPropertyValue(Parameters<String, Object> parameters, String propertyName, Class<?> propertyType,
			Object propertyValue) throws Exception {
		
		String mappedPropertyName = StringUtils.leftSubstring(propertyName, MAPPED_START, MAPPED_END);
		if (StringUtils.isNotEmpty(mappedPropertyName))
			parameters.add(mappedPropertyName, propertyValue);
		else
			parameters.add(propertyName, propertyValue);
	}

}
