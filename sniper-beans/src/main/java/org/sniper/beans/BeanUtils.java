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

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * JAVA Bean工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2014-12-9
 */
public class BeanUtils {
	
	private static final BeanReflector beanReflector = new DefaultBeanReflector();
	
	/** Setter方法前缀 */
	public static final String SETTER_PREFIX = "set";
	
	/** Getter方法前缀 */
	public static final String GETTER_PREFIX = "get";
	
	/** 布尔Getter方法前缀 */
	public static final String BOOLEAN_GETTER_PREFIX = "is";
			
	/**
	 * 检索成员属性对应的getter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean bean对象或class类型
	 * @param propertyName 属性名称
	 * @return
	 */
	public static <T> Method findGetter(T bean, String propertyName) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		
		if (CollectionUtils.isNotEmpty(methods)) {
			String isMethodName = BOOLEAN_GETTER_PREFIX + StringUtils.capitalize(propertyName);
			String getMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
			
			String name;
			Class<?> returnType;
			for (Method method : methods) {
				name = method.getName();
				// getter方法不能有参数
				if (ArrayUtils.isEmpty(method.getParameterTypes())) {
					returnType = method.getReturnType();
					if (returnType != void.class && name.equals(getMethodName) || returnType == boolean.class && name.equals(isMethodName))
						return method;
				}
			}
		}
		return null;
	}
	
	/**
	 * 检索成员属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	public static <T> String findGetterName(T bean, String propertyName) {
		Method getter = findGetter(bean, propertyName);
		return getter != null ? getter.getName() : null;
	}
	
	/**
	 * 检索成员属性对应的setter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	public static <T> Method findSetter(T bean, String propertyName) {
		return findSetter(bean, propertyName, null);
	}
	
	/**
	 * 检索成员属性对应的setter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名称
	 * @param parameterType 方法参数类型
	 * @return
	 */
	public static <T> Method findSetter(T bean, String propertyName, Class<?> parameterType) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		
		if (CollectionUtils.isNotEmpty(methods)) {
			String methodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
			
			if (parameterType != null) {
				Class<?>[] types;
				for (Method method : methods) {
					/* 方法名称相等，并且方法的唯一一个参数的类型与指定类型相等时才是要找的Setter
					 * 这里没有判断返回类型为void，允许setter方法有返回类型 */
					if (method.getName().equals(methodName)
							&& ArrayUtils.length(types = method.getParameterTypes()) == 1 
							&& types[0] == parameterType)
						return method;
				}
			} else {
				for (Method method : methods) {
					/* 方法名称相等，并且方法只有唯一一个参数
					 * 这里没有判断返回类型为void，允许setter方法有返回类型 */
					if (method.getName().equals(methodName) && ArrayUtils.length(method.getParameterTypes()) == 1)
						return method;
				}
			}
		}
		return null;
	}
	
	/**
	 * 检索成员属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	public static <T> String findSetterName(T bean, String propertyName) {
		return findSetterName(bean, propertyName, null);
	}
	
	/**
	 * 检索成员属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @param parameterType
	 * @return
	 */
	public static <T> String findSetterName(T bean, String propertyName, Class<?> parameterType) {
		Method setter = findSetter(bean, propertyName, parameterType);
		return setter != null ? setter.getName() : null;
	}
	
	/**
	 * 检索当前对象所有的getter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<Method> findAllGetter(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isEmpty(methods))
			return null;
		
		List<Method> getters = CollectionUtils.newArrayList();
		for (Method method : methods) {
			if (ReflectionUtils.isGetter(method))
				getters.add(method);
		}
		
		return getters;
	}
	
	/**
	 * 检索当前对象所有的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<String> findAllGetterName(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isEmpty(methods))
			return null;
		
		List<String> getterNames = CollectionUtils.newArrayList();
		for (Method method : methods) {
			if (ReflectionUtils.isGetter(method))
				getterNames.add(method.getName());
		}
		
		return getterNames;
	}
		
	/**
	 * 检索当前对象所有的setter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<Method> findAllSetter(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isEmpty(methods))
			return null;
		
		List<Method> setters = CollectionUtils.newArrayList();
		for (Method method : methods) {
			if (ReflectionUtils.isSetter(method))
				setters.add(method);
		}
		
		return setters;
	}
	
	/**
	 * 检索当前对象所有的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<String> findAllSetterName(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isEmpty(methods))
			return null;
		
		List<String> setterNames = CollectionUtils.newArrayList();
		for (Method method : methods) {
			if (ReflectionUtils.isSetter(method))
				setterNames.add(method.getName());
		}
		
		return setterNames;
	}
			
	/**
	 * 获取非boolean类型属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @return
	 */
	static String getterName(String propertyName) {
		return getterName(propertyName, (Class<?>) null);
	}
	
	/**
	 * 获取属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param paramterType 成员属性类型
	 * @return
	 */
	static String getterName(String propertyName, Class<?> paramterType) {
		return methodName(propertyName, "get", paramterType);
	}
	
	/**
	 * 获取属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @return
	 */
	static String setterName(String propertyName) {
		return methodName(propertyName, "set", null);
	}
		
	/**
	 * 获取前缀+属性组合的方法名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param propertyName
	 * @param prefix
	 * @param paramterType
	 * @return
	 */
	private static String methodName(String propertyName, String prefix, Class<?> paramterType) {
		propertyName = StringUtils.safeString(StringUtils.trimAll(propertyName));
		if (propertyName.length() > 0) {
			if (GETTER_PREFIX.equals(prefix))
				return paramterType != boolean.class ? 
						GETTER_PREFIX + StringUtils.capitalize(propertyName) : BOOLEAN_GETTER_PREFIX + StringUtils.capitalize(propertyName);
			else
				return SETTER_PREFIX + StringUtils.capitalize(propertyName);	
		}
		return propertyName;
	}
	
	/**
	 * 获取当前Bean对象属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名
	 * @return
	 */
	public static <T> String getterName(T bean, String propertyName) {
		return beanReflector.getterName(bean, propertyName);
	}
	
	/**
	 * 获取当前Bean对象属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名
	 * @return
	 */
	public static <T> String setterName(T bean, String propertyName) {
		return setterName(bean, propertyName, null);
	}
	
	/**
	 * 获取当前Bean对象属性对应的具有指定类型的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName  属性名
	 * @param parameterType 方法的参数类型
	 * @return
	 */
	public static <T> String setterName(T bean, String propertyName, Class<?> parameterType) {
		return beanReflector.setterName(bean, propertyName, parameterType);
	}
	
	/**
	 * 获取当前Bean对象属性的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名
	 * @return
	 * @throws Exception 
	 */
	public static <T> Object get(T bean, String propertyName) throws Exception {
		return beanReflector.get(bean, propertyName);
	}
		
	/**
	 * 设置当前Bean对象属性的值
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
	 * 调用当前Bean对象属性的setter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param parameterType 方法的参数类型
	 * @param parameterValue 参数值
	 * @throws Exception 
	 */
	public static <T> void set(T bean, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception {
		beanReflector.set(bean, propertyName, parameterType, parameterValue);
	}
	
	/**
	 * 调用全限定名对应类的构造函数创建Bean实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(String className) throws Exception { 
		return (T) create(Class.forName(className), (Map<String, Object>) null);
	}
	
	/**
	 * 调用全限定名对应类的构造函数创建Bean实例，并调用各属性对应的setter方法赋值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @param propertyValues
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> T create(String className, Map<String, V> propertyValues) throws Exception {
		AssertUtils.assertNotBlank(className, "Created bean class name must not be null or blank.");
		
		return (T) create(Class.forName(className.trim()), propertyValues);
	}
	
	/**
	 * 创建Bean实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T create(Class<T> clazz) throws Exception {
		return create(clazz, ( Map<String, Object>) null);
	}
	
	/**
	 * 创建Bean实例，并调用各属性对应的setter方法赋值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @param propertyValues 属性-值映射集
	 * @return
	 * @throws Exception 
	 */
	public static <T, V> T create(Class<T> clazz, Map<String, V> propertyValues) throws Exception {
		return beanReflector.create(clazz, propertyValues);
	}
	
	/**
	 * 根据当前Bean对象创建一个Map，其键值对就是bean的每一个属性名和值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static <T, V> Map<String, V> create(T bean) throws Exception {
		return create(bean, null);
	}
	
	/**
	 * 根据当前Bean对象创建一个Map
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param excludesName
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> Map<String, V> create(T bean, String[] excludesName) throws Exception {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			Map<String, V> map = MapUtils.newHashMap();
			for (Method method : methods) {
				// 出于对性能的考虑，此方法只调用getter方法进行设值处理
				String propertyName = propertyNameByGetter(method, excludesName);
				if (propertyName != null)
					map.put(propertyName, (V) ReflectionUtils.invokeMethod(bean, method.getName()));
			}
			return map;
		}
		
		return null;
	}
	
	/**
	 * 根据当前Bean的getter方法检索出对应的属性名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<String> findAllPropertyNameByGetter(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			List<String> list = CollectionUtils.newArrayList();
			for (Method method : methods) {
				String propertyName = propertyNameByGetter(method, null);
				if (propertyName != null)
					list.add(propertyName);
			}
			
			return list;
		}
		
		return null;
	}
	
	/**
	 * 根据Getter方法获取对应的属性名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param method
	 * @param excludesName
	 * @return
	 */
	private static String propertyNameByGetter(Method method, String[] excludesName) {
		if (!ReflectionUtils.isGetter(method))
			return null;
		
		String name = method.getName();
		String propertyName = StringUtils.afterPrefix(name, GETTER_PREFIX);
		if (StringUtils.isEmpty(propertyName))
			propertyName = StringUtils.afterPrefix(name, BOOLEAN_GETTER_PREFIX);

		propertyName = StringUtils.uncapitalize(propertyName);
		return !ArrayUtils.contains(excludesName, propertyName) ? propertyName : null;
	}
	
}
