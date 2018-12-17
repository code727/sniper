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
 * Create Date : 2015-2-26
 */

package org.sniper.beans.reflector;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sniper.beans.BeanUtils;
import org.sniper.beans.expression.DefaultPropertyParser;
import org.sniper.beans.expression.PropertyParser;
import org.sniper.commons.exception.NestedNullPointerException;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * Bean映射器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultBeanReflector implements BeanReflector {
	
	private PropertyParser propertyParser = new DefaultPropertyParser();
	
	@Override
	public String findGetterName(Object bean, String propertyName) {
		Class<?> currentType = ClassUtils.getCurrentType(bean);
		if (currentType == null)
			return null;
		
		while (propertyParser.hasNested(propertyName)) {
			String memberName = propertyParser.resolve(propertyName);
			Class<?> memberType = findMemberTypeByNameOrGetter(currentType, memberName);
			
			/* 如果未获取到成员类型，说明此成员属性以及对应的Getter方法都没有在当前类中定义。
			 * 因此，无法根据当前成员推导出下一级成员，直接返回空结果  */
			if (memberType == null)
				return null;
			
			currentType = memberType;
			propertyName = propertyParser.next(propertyName);
		}
		
		return BeanUtils.findGetterName(currentType, propertyName);
	}
	
	@Override
	public String findSetterName(Object bean, String propertyName) {
		return findSetterName(bean, propertyName, null);
	}

	@Override
	public String findSetterName(Object bean, String propertyName, Class<?> parameterType) {
		Class<?> currentType = ClassUtils.getCurrentType(bean);
		if (currentType == null)
			return null;
		
		while (propertyParser.hasNested(propertyName)) {
			String memberName = propertyParser.resolve(propertyName);
			Class<?> memberType = findMemberTypeByNameOrSetter(currentType, memberName);
			
			/* 如果未获取到成员类型，说明此成员属性以及对应的Setter方法都没有在当前类中定义。
			 * 因此，无法根据当前成员推导出下一级成员，直接返回空结果  */
			if (memberType == null)
				return null;
			
			currentType = memberType;
			propertyName = propertyParser.next(propertyName);
		}
		
		return BeanUtils.findSetterName(currentType, propertyName, parameterType);
	}
	
	@Override
	public <V> V getPropertyValue(Object bean, String propertyName) throws Exception {
		String currentPropertyName = propertyName;
		while (propertyParser.hasNested(currentPropertyName)) {
			String memberName = propertyParser.resolve(currentPropertyName);
			Object memberValue = getMemberValue(bean, memberName);
			
			/* 嵌套成员值为空时，无法根据当前成员推导出下一级成员，直接抛出异常 */
			if (memberValue == null)
				throw new NestedNullPointerException(String.format(
						"Null nested property value for '%s' on bean class '%s'", propertyName, bean.getClass()));
			
			bean = memberValue;
			currentPropertyName = propertyParser.next(currentPropertyName);
		}
		
		return getMemberValue(bean, currentPropertyName);
	}
	
	@Override
	public void setPropertyValue(Object bean, String propertyName, Object parameterValue) throws Exception {
		setPropertyValue(bean, propertyName, null, parameterValue);
	}
	
	@Override
	public void setPropertyValue(Object bean, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception {
		String currentPropertyName = propertyName;
		while (propertyParser.hasNested(currentPropertyName)) {
			String memberName = propertyParser.resolve(currentPropertyName);
			Object memberValue = getMemberValue(bean, memberName);
			
			/* 嵌套成员值为空时，则先获取到嵌套成员的类型，再根据类型调用默认构造函数创建一个嵌套成员值 */
			if (memberValue == null) {
				Class<?> memberType = findMemberTypeByNameOrSetter(bean, memberName);
				if (memberType == null)
					throw new NestedNullPointerException(String.format(
							"Null nested property value for '%s' on bean class '%s'", propertyName, bean.getClass()));
				
				memberValue = BeanUtils.create(memberType);
			}
				
			bean = memberValue;
			currentPropertyName = propertyParser.next(currentPropertyName);
		}
		
		setMemberValue(bean, currentPropertyName, parameterType, parameterValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T, V> T create(String className, Map<String, V> properties) throws Exception {
		AssertUtils.assertNotBlank(className, "Bean class name must not be null or blank");
		
		try {
			return (T) create(Class.forName(className.trim()), properties);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public <T, V> T create(Class<T> clazz, Map<String, V> properties) throws Exception {
		T bean = ReflectionUtils.newInstance(clazz);
		
		if (MapUtils.isNotEmpty(properties)) {
			Set<Entry<String, V>> propertyValue = properties.entrySet();
			for (Entry<String, V> pv : propertyValue) {
				String propertyName = pv.getKey();
				if (StringUtils.isNotBlank(propertyName))
					try {
						setPropertyValue(bean, propertyName, pv.getValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		return bean;
	}
	
	/**
	 * 在当前Bean对象中获取指定属性成员的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <V> V getMemberValue(Object bean, String propertyName) throws Exception {
		/* 优先从属性成员对应的Getter方法中获取值，如果Getter方法未定义，则直接从属性域获取值   */
		Method getter = BeanUtils.findGetter(bean, propertyName);
		return (V) (getter != null ? ReflectionUtils.invokeMethod(bean, getter)
				: ReflectionUtils.getFieldValue(bean, propertyName));
	}
	
	/**
	 * 在当前Bean对象中设置指定属性成员的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @param parameterType
	 * @param parameterValue
	 * @throws Exception 
	 */
	protected void setMemberValue(Object bean, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception {
		Method setter = BeanUtils.findSetter(bean, propertyName, parameterType);
		if (setter != null)
			ReflectionUtils.invokeMethod(bean, setter, new Class<?>[]{parameterType}, new Object[]{parameterValue});
		else
			ReflectionUtils.setFieldValue(bean, propertyName, parameterValue);
	}
	
	/**
	 * 根据属性名称或对应的Getter方法检索出成员类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param memberName
	 * @return
	 */
	private Class<?> findMemberTypeByNameOrGetter(Object obj, String memberName) {
		Class<?> memberType = ReflectionUtils.getFieldType(obj, memberName);
		if (memberType == null) {
			Method getter = BeanUtils.findGetter(obj, memberName);
			return getter != null ? getter.getReturnType() : null;
		}
		
		return memberType;
	}
	
	/**
	 * 根据属性名称或对应的Setter方法检索出成员类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param memberName
	 * @return
	 */
	private Class<?> findMemberTypeByNameOrSetter(Object obj, String memberName) {
		Class<?> memberType = ReflectionUtils.getFieldType(obj, memberName);
		if (memberType == null) {
			Method setter = BeanUtils.findSetter(obj, memberName);
			return setter != null ? setter.getParameterTypes()[0] : null;
		}
		
		return memberType;
	}
	
}
