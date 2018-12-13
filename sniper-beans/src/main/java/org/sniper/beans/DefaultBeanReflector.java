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

package org.sniper.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * Bean映射器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultBeanReflector implements BeanReflector {
	
	/** 各属性间的分隔符 */
	private String propertySeperator = StringUtils.POINT;
		
	public String getPropertySeperator() {
		return propertySeperator;
	}

	public void setPropertySeperator(String propertySeperator) {
		this.propertySeperator = propertySeperator;
	}

	@Override
	public String getterName(Object bean, String propertyName) {
		if (bean == null || StringUtils.isBlank(propertyName))
			return null;
		
		String memberName = StringUtils.beforeFrist(propertyName, this.propertySeperator);
		if (memberName.length() > 0) {
			// 复合成员属性
			memberName = memberName.trim();
			Class<?> memberType = ReflectionUtils.getFieldType(bean, memberName);
			if (memberType == null) {
				Method getter = BeanUtils.findGetter(bean, memberName);
				if (getter != null)
					memberType = getter.getReturnType();
			}
			
			return getterName(memberType, StringUtils.afterFrist(propertyName, this.propertySeperator));
		}
		return memberGetterName(bean, propertyName);
	}
	
	@Override
	public String setterName(Object bean, String propertyName) {
		return setterName(bean, propertyName, null);
	}

	@Override
	public String setterName(Object bean, String propertyName, Class<?> parameterType) {
		if (bean == null || StringUtils.isBlank(propertyName))
			return null;
		
		String memberName = StringUtils.beforeFrist(propertyName, this.propertySeperator);
		if (memberName.length() > 0) {
			memberName = memberName.trim();
			// 复合成员属性
			Class<?> memberType = ReflectionUtils.getFieldType(bean, memberName);
			if (memberType == null) {
				// 若复合成员属性未找到，则查找对应的setter方法，并将setter方法的参数类型作为复合成员来处理
				Method setter = BeanUtils.findSetter(bean, memberName, parameterType);
				if (setter != null)
					memberType = setter.getParameterTypes()[0];
			}
			return setterName(memberType, StringUtils.afterFrist(propertyName, this.propertySeperator), parameterType);
		}
		
		return memberSetterName(bean, propertyName, parameterType);
	}

	@Override
	public Object get(Object bean, String propertyName) throws Exception {
		
		String memberName = StringUtils.beforeFrist(propertyName, this.propertySeperator);
		if (StringUtils.isNotBlank(memberName)) {
			
			String memberGetterName = getterName(bean, memberName);
			if (StringUtils.isNotBlank(memberGetterName)) {
				Object memberValue = null;
				try {
					/* 先调用复合成员属性的getter方法或对其直接访问获取到值 */
					memberValue = ReflectionUtils.invokeMethod(bean, memberGetterName);
				} catch (NoSuchMethodException e) {
					memberValue = ReflectionUtils.getFieldValue(bean, memberName);
				}
				
				// 复合成员的下一级成员名
				String nextMemberName = StringUtils.afterFrist(propertyName, this.propertySeperator);
				if (StringUtils.isNotBlank(nextMemberName)) {
					if (memberValue != null)
						// 递归获取下一级成员的值
						return this.get(memberValue, nextMemberName);
					
					// 不能获取空复合成员的下一级成员值
					throw new NullPointerException(String.format(
							"Unknow value of property '%s',because it's parent '%s' value is null", nextMemberName, memberName));
				}
				return memberValue;
			} else 
				// 获取成员值时，必须确保属性和对应的getter方法之一是已存在的
				throw new NoSuchMethodException(String.format(
						"Unknow member property '%s' value,please ensure that this property or corresponding getter method existed", memberName));
		} else {
			/*  调用当前成员属性的getter方法或对其直接访问获取值，否则抛出异常 */
			try {
				return ReflectionUtils.invokeMethod(bean, getterName(bean, propertyName));
			} catch (NoSuchMethodException e) {
				try {
					return ReflectionUtils.getFieldValue(bean, propertyName);
				} catch (NullPointerException npe) {
					throw new NoSuchMethodException(String.format(
							"Unknow member property '&s' value,please ensure that this property or corresponding getter method existed", 
							(StringUtils.isNotBlank(memberName) ? memberName : propertyName)));
				}
			}
		}
	}

	@Override
	public void set(Object bean, String propertyName, Object parameterValue) throws Exception {
		this.set(bean, propertyName, null, parameterValue);
	}

	@Override
	public void set(Object bean, String propertyName, Class<?> parameterType, 
			Object parameterValue) throws Exception {
			
		String memberName = StringUtils.beforeFrist(propertyName, this.propertySeperator);
		if (StringUtils.isNotBlank(memberName)) {
			
			// 成员setter方法参数类型。默认情况下，认为成员属性的类型即为对应setter方法的参数类型
			Class<?> memberSetterType = ReflectionUtils.getFieldType(bean, memberName);
			if (memberSetterType == null) {
				/* 未从属性中获取到参数类型时，则直接获取名称为"set+属性名"的方法的参数类型 */
				Method memberSetter = BeanUtils.findSetter(bean, memberName, null);
				if (memberSetter != null) 
					memberSetterType = memberSetter.getParameterTypes()[0];
			}
			if (memberSetterType == null)
				// 设置成员属性值时，必须保证属性和对应的setter方法之一是存在的
				throw new NoSuchMethodException(String.format(
						"Unknow member property '&s' value,please ensure that this property or corresponding setter method existed", memberName)); 
						
			Object memberValue = null;
			Field memberField = null;
			try {
				// 先调用对应的getter方法来获取成员值
				memberValue = get(bean, memberName);
			} catch (NoSuchMethodException e) {
				// 未存在有getter方法时，则直接访问成员值
				memberField = ReflectionUtils.getDeclaredField(bean, memberName);
				if (memberField == null)
					// 获取成员值时，必须确保属性和对应的getter方法之一是存在的
					throw new NoSuchMethodException(String.format(
							"Unknow member propertity '%s' value,Please ensure that this property or corresponding getter method existed when get member value ", memberName));
							
				memberValue = ReflectionUtils.getFieldValue(bean, memberField);
			}
			
			// 当前复合成员的下一级成员名
			String nextMemberName = StringUtils.afterFrist(propertyName, this.propertySeperator);
			if (StringUtils.isNotBlank(nextMemberName)) {
				if (memberValue == null) {
					/* 复合成员为空，则利用此成员的默认构造函数创建一个后，再调用此setter方法或直接对其赋值 */
					memberValue = BeanUtils.create(memberSetterType);
					try {
						// 设置复合对象的值
						set(bean, memberName, memberSetterType, memberValue);
					} catch (NoSuchMethodException e) {
						ReflectionUtils.setFieldValue(bean, memberName, memberValue);
					}
				}
				// 设置复合对象的属性值
				set(memberValue, nextMemberName, null, parameterValue);
			}  else 
				// 设置当前对象值
				set(bean, memberName, parameterType, parameterValue);
		} else {
			if (parameterType == null) {
				// 传入的参数类型为空时，则将属性的类型作为setter的参数类型
				parameterType = ReflectionUtils.getFieldType(bean, propertyName);
				
				/* 从属性中也未获取到参数类型，则从当前属性对应的setter方法列表中获取 */
				if (parameterType == null) {
					Method setter = BeanUtils.findSetter(bean, propertyName, null);
					if (setter == null)
						throw new NoSuchMethodException(String.format(
								"Unknow member setter method '%s',please ensure that this method existed when member '%s' undefined",
								BeanUtils.buildSetterName(propertyName), propertyName));
					
					parameterType = setter.getParameterTypes()[0];
				}
			}
			
			try {
				/*  调用当前成员属性的setter方法或对其直接赋值，否则抛出异常 */
				ReflectionUtils.invokeMethod(bean, BeanUtils.buildSetterName(propertyName),
						new Class<?>[] {parameterType}, new Object[] {parameterValue});
			} catch (NoSuchMethodException e) {
				try {
					ReflectionUtils.setFieldValue(bean, propertyName, parameterValue);
				} catch (NoSuchFieldException nsfe) {
					throw new NoSuchMethodException(String.format(
							"Unknow member propertity '%s' value,please ensure that this property or corresponding setter method existed", memberName));
				}
			}
		}
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
			
			
			String propertyName;
			for (Entry<String, V> pv : propertyValue) {
				
				propertyName = pv.getKey();
				if (StringUtils.isNotBlank(propertyName))
					try {
						set(bean, propertyName.trim(), pv.getValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		return bean;
	}
	
	/**
	 * 获取当前Bean对象成员属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean bean对象或class类型
	 * @param propertyName 属性名称
	 * @return
	 */
	protected static <T> String memberGetterName(T bean, String propertyName) {
		
		// TODO 重构
		propertyName = propertyName.trim();
		Class<?> propertyType = ReflectionUtils.getFieldType(bean, propertyName);
		if (propertyType != null) 
			return BeanUtils.buildGetterName(propertyName, propertyType);
		
		Method getter = BeanUtils.findGetter(bean, propertyName);
		return getter != null ? getter.getName() : null;
	}
	
	/**
	 * 获取当前Bean对象成员属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean bean对象或class类型
	 * @param propertyName 属性名称
	 * @param parameterType 方法参数类型
	 * @return
	 */
	protected String memberSetterName(Object bean, String propertyName, Class<?> parameterType) {
		propertyName = propertyName.trim();
		if (parameterType == null) 
			// 如果传入的方法参数类型为空，则认为方法参数类型和属性的类型一致
			parameterType = ReflectionUtils.getFieldType(bean, propertyName);
		
		Method setter;
		if (parameterType != null) {
			String methodName = BeanUtils.buildSetterName(propertyName);
			setter = ReflectionUtils.getDeclaredMethod(bean, methodName, new Class[] { parameterType });
		} else 
			// 若方法参数类型仍为空，则遍历整个bean对象，找到第一个与名称一致的方法(不匹配参数类型)即可
			setter = BeanUtils.findSetter(bean, propertyName, parameterType);
		
		return setter != null ? setter.getName() : null;
	}
	
}
