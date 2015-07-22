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

package org.workin.support.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description Bean映射器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultBeanReflector implements BeanReflector {
	
	/** 在表达式中各属性间的分隔符 */
	private String propertySeperator;
	
	public DefaultBeanReflector() {
		this.propertySeperator = ".";
	}
	
	public String getPropertySeperator() {
		return propertySeperator;
	}

	public void setPropertySeperator(String propertySeperator) {
		if (StringUtils.isNotBlank(propertySeperator))
			this.propertySeperator = propertySeperator;
	}

	@Override
	public String getterName(Object bean, String expression) {
		if (bean == null || StringUtils.isBlank(expression))
			return null;
		
		String memberName = StringUtils.beforeFrist(expression, this.propertySeperator);
		if (memberName.length() > 0) {
			memberName = memberName.trim();
			// 复合成员属性
			Class<?> memberType = ReflectionUtils.getFieldType(bean, memberName);
			if (memberType == null) {
				Method getter = BeanUtils.findUndefinedGetter(bean, memberName);
				if (getter != null)
					memberType = getter.getReturnType();
			}
			return getterName(memberType, StringUtils.afterFrist(expression, this.propertySeperator));
		}
		
		return memberGetterName(bean, expression);
	}

	@Override
	public String setterName(Object bean, String expression) {
		return setterName(bean, this.propertySeperator, null);
	}

	@Override
	public String setterName(Object bean, String expression, Class<?> parameterType) {
		if (bean == null || StringUtils.isBlank(expression))
			return null;
		
		String memberName = StringUtils.beforeFrist(expression, this.propertySeperator);
		if (memberName.length() > 0) {
			memberName = memberName.trim();
			// 复合成员属性
			Class<?> memberType = ReflectionUtils.getFieldType(bean, memberName);
			if (memberType == null) {
				// 若复合成员属性未找到，则查找对应的setter方法，并将setter方法的参数类型作为复合成员来处理
				Method setter = BeanUtils.findUndefinedSetter(bean, memberName, parameterType);
				if (setter != null)
					memberType = setter.getParameterTypes()[0];
			}
			return setterName(memberType, StringUtils.afterFrist(expression, this.propertySeperator), parameterType);
		}
		
		return memberSetterName(bean, expression, parameterType);
	}

	@Override
	public Object get(Object bean, String expression) throws Exception {
		
		String memberName = StringUtils.beforeFrist(expression, this.propertySeperator);
		if (StringUtils.isNotBlank(memberName)) {
			
			String memberGetterName = getterName(bean, memberName);
			if (StringUtils.isNotBlank(memberGetterName)) {
				Object memberValue = null;
				try {
					/* 先调用复合成员属性的getter方法或对其直接访问获取到值 */
					memberValue = ReflectionUtils.invokeMethod(bean, memberGetterName, null, null);
				} catch (NoSuchMethodException e) {
					memberValue = ReflectionUtils.getFieldValue(bean, memberName);
				}
				
				// 复合成员的下一级成员名
				String nextMemberName = StringUtils.afterFrist(expression, this.propertySeperator);
				if (StringUtils.isNotBlank(nextMemberName)) {
					if (memberValue != null)
						// 递归获取下一级成员的值
						return this.get(memberValue, nextMemberName);
					
					// 不能获取空复合成员的下一级成员值
					throw new NullPointerException("Unknow value of property [" + nextMemberName + "]." 
							+ "Because it's parent object [" + memberName + "] value is null.");
				}
				return memberValue;
			} else 
				// 获取成员值时，必须确保属性和对应的getter方法之一是存在的
				throw new NoSuchMethodException("Unknow member propertity [" + memberName 
						+ "] value,Please ensure that this property or corresponding getter method exists");
		} else {
			/*  调用当前成员属性的getter方法或对其直接访问获取值，否则抛出异常 */
			try {
				return ReflectionUtils.invokeMethod(bean, getterName(bean, expression), null, null);
			} catch (NoSuchMethodException e) {
				try {
					return ReflectionUtils.getFieldValue(bean, expression);
				} catch (NullPointerException npe) {
					throw new NoSuchMethodException("Unknow member propertity [" + (StringUtils.isNotBlank(memberName) ? memberName : expression)  
							+ "] value,Please ensure that this property or corresponding getter method exists");
				}
			}
		}
	}

	@Override
	public void set(Object bean, String expression, Object parameterValue) throws Exception {
		this.set(bean, expression, null, parameterValue);
	}

	@Override
	public void set(Object bean, String expression, Class<?> parameterType, 
			Object parameterValue) throws Exception {
			
		String memberName = StringUtils.beforeFrist(expression, this.propertySeperator);
		if (StringUtils.isNotBlank(memberName)) {
			
			// 成员setter方法参数类型。默认情况下，认为成员属性的类型即为对应setter方法的参数类型
			Class<?> memberSetterType = ReflectionUtils.getFieldType(bean, memberName);
			if (memberSetterType == null) {
				/* 未从属性中获取到参数类型时，则直接获取名称为"set+属性名"的方法的参数类型 */
				Method memberSetter = BeanUtils.findUndefinedSetter(bean, memberName, null);
				if (memberSetter != null) 
					memberSetterType = memberSetter.getParameterTypes()[0];
			}
			if (memberSetterType == null)
				// 设置成员属性值时，必须保证属性和对应的setter方法之一是存在的
				throw new NoSuchMethodException("Unknow member propertity [" + memberName 
						+ "] value,Please ensure that this property or corresponding setter method exists");
			
			Object memberValue = null;
			Field memberField = null;
			try {
				// 先调用对应的getter方法来获取成员值
				memberValue = get(bean, memberName);
			} catch (NoSuchMethodException e) {
				// 未存在有getter方法时，则直接访问成员值
				memberField = ReflectionUtils.getDeclaredField(bean, memberName);
				if (memberField != null)
					memberValue = ReflectionUtils.getFieldValue(bean, memberField);
				else
					// 获取成员值时，必须确保属性和对应的getter方法之一是存在的
					throw new NoSuchMethodException("Unknow member propertity [" + memberName + 
							"] value,Please ensure that this property or corresponding getter method exists when get member value.");
			}
			
			// 当前复合成员的下一级成员名
			String nextMemberName = StringUtils.afterFrist(expression, this.propertySeperator);
			if (StringUtils.isNotBlank(nextMemberName)) {
				if (memberValue == null) {
					/* 复合成员为空，则利用此成员的默认构造函数创建一个后，则调用此setter方法或直接对其赋值 */
					memberValue = BeanUtils.create(memberSetterType);
					try {
						set(bean, memberName, memberSetterType, memberValue);
					} catch (NoSuchMethodException e) {
						ReflectionUtils.setFieldValue(bean, memberName, memberValue);
					}
				}
			}  else 
				// 设置当前对象值
				set(bean, memberName, parameterType, parameterValue);
		} else {
			if (parameterType == null)
				// 传入的参数参数为空时，则自动识别出属性的类型后作为setter参数的类型
				parameterType = ReflectionUtils.getFieldType(bean, expression);
			if (parameterType == null) {
				/* 如果还为空，则找到它setter方法的参数类型 */
				Method setter = BeanUtils.findUndefinedSetter(bean, expression, null);
				if (setter != null)
					parameterType = setter.getParameterTypes()[0];
				else
					throw new NoSuchMethodException("Unknow member setter method [" + BeanUtils.setterName(expression) + 
							"],Please ensure that this method exists when member [" + expression + "] undefined.");
			}
			try {
				/*  调用当前成员属性的setter方法或对其直接赋值，否则抛出异常 */
				ReflectionUtils.invokeMethod(bean, BeanUtils.setterName(expression), 
						new Class<?>[] { parameterType }, new Object[] { parameterValue });
			} catch (NoSuchMethodException e) {
				try {
					ReflectionUtils.setFieldValue(bean, expression, parameterValue);
				} catch (NoSuchFieldException nsfe) {
					throw new NoSuchMethodException("Unknow member propertity [" + memberName 
							+ "] value,Please ensure that this property or corresponding setter method exists");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T create(String className, Map<String, Object> expressionValues) throws Exception {
		try {
			AssertUtils.assertTrue(StringUtils.isNotBlank(className), "Created bean class name can not be null or blank.");
			return (T) create(Class.forName(className.trim()), expressionValues);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public <T> T create(Class<T> clazz, Map<String, Object> expressionValues) throws Exception {
		T bean = ReflectionUtils.newInstance(clazz);
		if (MapUtils.isNotEmpty(expressionValues)) {
			Set<Entry<String, Object>> propertyValue = expressionValues.entrySet();
			String propertyName;
			for (Entry<String, Object> pv : propertyValue) {
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
	 * @description 获取当前Bean对象成员属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean bean对象或class类型
	 * @param propertyName 属性名称
	 * @return
	 */
	protected static <T> String memberGetterName(T bean, String propertyName) {
		propertyName = propertyName.trim();
		Class<?> propertyType = ReflectionUtils.getFieldType(bean, propertyName);
		if (propertyType != null) 
			return BeanUtils.getterName(propertyName, propertyType);
		else {
			Method getter = BeanUtils.findUndefinedGetter(bean, propertyName);
			return getter != null ? getter.getName() : null;
		}
	}
	
	/**
	 * @description 获取当前Bean对象成员属性对应的setter方法名称
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
		
		if (parameterType != null) {
			String methodName = "set" + StringUtils.capitalize(propertyName);
			Method setter = ReflectionUtils.getDeclaredMethod(bean, methodName, new Class[] { parameterType });
			return setter != null ? setter.getName() : null;
		} else {
			// 若方法参数类型仍为空，则遍历整个bean对象，找到第一个与名称一致的方法(不匹配参数类型)即可
			Method setter = BeanUtils.findUndefinedSetter(bean, propertyName, parameterType);
			return setter != null ? setter.getName() : null;
		}
	}
	
}
