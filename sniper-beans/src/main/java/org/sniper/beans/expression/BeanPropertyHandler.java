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

import java.lang.reflect.Method;

import org.sniper.beans.BeanUtils;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;

/**
 * JavaBean属性处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BeanPropertyHandler implements PropertyHandler<Object> {

	@Override
	public boolean support(Object obj, String propertyName) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getPropertyValue(Object bean, String propertyName) throws Exception {
		Method getter = BeanUtils.findGetter(bean, propertyName);
		return (V) (getter != null ? ReflectionUtils.invokeMethod(bean, getter)
				: ReflectionUtils.getFieldValue(bean, propertyName));
	}
	
	@Override
	public <V> V getConstructedPropertyValue(Object bean, String propertyName) throws Exception {
		// 先尝试直接获取属性值
		V propertyValue = getPropertyValue(bean, propertyName);
		if (propertyValue == null) {
			/* 属性值为空时， 尝试从属性域或对应的Setter方法中获取到属性类型，再根据此类型构造出一个成员值并设置给此属性 */
			Class<?> propertyType = BeanUtils.findPropertyTypeByNameOrSetter(bean, propertyName);
			propertyValue = constructPropertyValue(propertyType);
			if (propertyValue != null) 
				BeanUtils.setPropertyValue(bean, propertyName, propertyValue);
		}
			
		return propertyValue;
	}

	@Override
	public void setPropertyValue(Object bean, String propertyName, Class<?> propertyType, Object propertyValue) throws Exception {
		Method setter = BeanUtils.findSetter(bean, propertyName, propertyType);
		if (setter != null)
			ReflectionUtils.invokeMethod(bean, setter, new Class<?>[] {propertyType}, new Object[] {propertyValue});
		else
			ReflectionUtils.setFieldValue(bean, propertyName, propertyValue);	
	}
	
	@SuppressWarnings("unchecked")
	protected <V> V constructPropertyValue(Class<?> propertyType) throws Exception {
		if (ReflectionUtils.hasDeclaredConstructor(propertyType))
			return (V) BeanUtils.create(propertyType);
		
		if (ClassUtils.isMap(propertyType))
			return (V) MapUtils.newHashMap();
		
		if (ClassUtils.isCollection(propertyType) || ClassUtils.isList(propertyType))
			return (V) CollectionUtils.newArrayList();
		
		return null;
	}

}
