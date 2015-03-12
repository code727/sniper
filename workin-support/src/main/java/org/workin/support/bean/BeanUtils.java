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

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description JAVA Bean工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2014-12-9
 */
public class BeanUtils {
			
	/**
	 * @description 检索未定义的成员属性对应的getter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean bean对象或class类型
	 * @param propertyName 属性名称
	 * @return
	 */
	public static <T> Method findUndefinedGetter(T bean, String propertyName) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			String isMethodName = "is" + StringUtils.capitalize(propertyName);
			String getMethodName = "get" + StringUtils.capitalize(propertyName);
			for (Method method : methods) {
				if ((method.getName().equals(getMethodName) || method.getName().equals(isMethodName))
						&& ArrayUtils.isEmpty(method.getParameterTypes()))
					return method;
			}
		}
		return null;
	}
	
	/**
	 * @description 检索未定义的成员属性对应的setter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名称
	 * @param parameterType 方法参数类型
	 * @return
	 */
	public static <T> Method findUndefinedSetter(T bean, String propertyName, Class<?> parameterType) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			String methodName = "set" + StringUtils.capitalize(propertyName);
			if (parameterType != null) {
				Class<?>[] types;
				for (Method method : methods) {
					if (method.getName().equals(methodName)
							&& ArrayUtils.length(types = method.getParameterTypes()) == 1 
							&& types[0] == parameterType)
						return method;
				}
			} else {
				for (Method method : methods) {
					if (method.getName().equals(methodName)
							&& ArrayUtils.length(method.getParameterTypes()) == 1)
						return method;
				}
			}
		}
		return null;
	}
	
		
	/**
	 * @description 获取非boolean类型属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @return
	 */
	public static String getterName(String propertyName) {
		return getterName(propertyName, (Class<?>) null);
	}
	
	/**
	 * @description 获取属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param paramterType 成员属性类型
	 * @return
	 */
	public static String getterName(String propertyName, Class<?> paramterType) {
		return methodName(propertyName, "get", paramterType);
	}
	
	
	/**
	 * @description 获取属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @return
	 */
	public static String setterName(String propertyName) {
		return methodName(propertyName, "set", null);
	}
		
	/**
	 * @description 获取前缀+属性组合的方法名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param prefix
	 * @param paramterType
	 * @return
	 */
	private static String methodName(String propertyName, String prefix, Class<?> paramterType) {
		propertyName = StringUtils.safeString(StringUtils.trimAll(propertyName));
		if (propertyName.length() > 0) {
			if ("get".equals(prefix))
				return (paramterType != boolean.class && paramterType != Boolean.class) ? 
						"get" + StringUtils.capitalize(propertyName) : "is" + StringUtils.capitalize(propertyName);
			else
				return "set" + StringUtils.capitalize(propertyName);	
		}
		return propertyName;
	}
	
	/**
	 * @description 获取当前Bean对象属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名
	 * @return
	 */
	public static <T> String getterName(T bean, String propertyName) {
		return new DefaultBeanReflector().getterName(bean, propertyName);
	}
	
	/**
	 * @description 获取当前Bean对象属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名
	 * @return
	 */
	public static <T> String setterName(T bean, String propertyName) {
		return setterName(bean, propertyName, null);
	}
	
	/**
	 * @description 获取当前Bean对象属性对应的具有指定类型的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName  属性名
	 * @param parameterType 方法的参数类型
	 * @return
	 */
	public static <T> String setterName(T bean, String propertyName, Class<?> parameterType) {
		return new DefaultBeanReflector().setterName(bean, propertyName);
	}
	
	/**
	 * @description 获取当前Bean对象属性的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名
	 * @return
	 * @throws Exception 
	 */
	public static <T> Object get(T bean, String propertyName) throws Exception {
		return new DefaultBeanReflector().get(bean, propertyName);
		
	}
		
	/**
	 * @description 设置当前Bean对象属性的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名
	 * @param parameterValue 参数值
	 * @throws NoSuchMethodException 
	 */
	public static <T> void set(T bean, String propertyName, Object parameterValue) throws Exception {
		set(bean, propertyName, null, parameterValue);
	}
	
	/**
	 * @description 调用当前Bean对象属性的setter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param parameterType 方法的参数类型
	 * @param parameterValue 参数值
	 * @throws NoSuchMethodException 
	 */
	public static <T> void set(T bean, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception {
		new DefaultBeanReflector().set(bean, propertyName, parameterType, parameterValue);
	}
	
	/**
	 * @description 调用全限定名对应类的构造函数创建Bean实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @return
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(String className) throws NoSuchMethodException { 
		try {
			return (T) create(Class.forName(className), null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description 调用全限定名对应类的构造函数创建Bean实例，并调用各属性对应的setter方法赋值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @param propertyValues
	 * @return
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(String className, Map<String, Object> propertyValues) throws NoSuchMethodException {
		try {
			AssertUtils.assertTrue(StringUtils.isNotBlank(className), "Created bean class name can not be null or blank.");
			return (T) create(Class.forName(className.trim()), propertyValues);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description 创建Bean实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static <T> T create(Class<T> clazz) {
		return create(clazz, null);
	}
	
	/**
	 * @description 创建Bean实例，并调用各属性对应的setter方法赋值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @param propertyValues 属性-值映射集
	 * @return
	 * @throws NoSuchMethodException 
	 */
	public static <T> T create(Class<T> clazz, Map<String, Object> propertyValues) {
		try {
			return new DefaultBeanReflector().create(clazz, propertyValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
			
}
