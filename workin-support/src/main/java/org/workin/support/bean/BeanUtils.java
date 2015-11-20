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
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.ReflectionUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description JAVA Bean工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2014-12-9
 */
public class BeanUtils {
	
	private static final BeanReflector beanReflector = new DefaultBeanReflector();
			
	/**
	 * @description 检索成员属性对应的getter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean bean对象或class类型
	 * @param propertyName 属性名称
	 * @return
	 */
	public static <T> Method findGetter(T bean, String propertyName) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			String isMethodName = "is" + StringUtils.capitalize(propertyName);
			String getMethodName = "get" + StringUtils.capitalize(propertyName);
			Class<?> returnType;
			String name;
			for (Method method : methods) {
				name = method.getName();
				if (ArrayUtils.isEmpty(method.getParameterTypes()) && (returnType = method.getReturnType()) != void.class
						&& (name.equals(getMethodName) || (returnType == boolean.class && name.equals(isMethodName)))) {
					return method;
				}					
			}
		}
		return null;
	}
	
	/**
	 * @description 检索成员属性对应的setter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName 属性名称
	 * @param parameterType 方法参数类型
	 * @return
	 */
	public static <T> Method findSetter(T bean, String propertyName, Class<?> parameterType) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			String methodName = "set" + StringUtils.capitalize(propertyName);
			if (parameterType != null) {
				Class<?>[] types;
				for (Method method : methods) {
					// 没有判断返回类型为void，允许setter方法有返回类型
					if (method.getName().equals(methodName)
							&& ArrayUtils.length(types = method.getParameterTypes()) == 1 
							&& types[0] == parameterType)
						return method;
				}
			} else {
				for (Method method : methods) {
					// 没有判断返回类型为void，允许setter方法有返回类型
					if (method.getName().equals(methodName)
							&& ArrayUtils.length(method.getParameterTypes()) == 1)
						return method;
				}
			}
		}
		return null;
	}
	
	/**
	 * @description 检索当前对象所有的getter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<Method> findAllGetter(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			List<Method> getters = CollectionUtils.newArrayList();
			Class<?> returnType;
			String name;
			for (Method method : methods) {
				name = method.getName();
				if (ArrayUtils.isEmpty(method.getParameterTypes()) 
						&& (returnType = method.getReturnType()) != void.class
						&& ((name.startsWith("get") && name.length() > 3) 
								|| (name.startsWith("is") && name.length() > 2  
										&& returnType == boolean.class)))
					getters.add(method);
			}
			return getters;
		}
		return null;
	}
	
	/**
	 * @description 检索当前对象所有的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<String> findAllGetterName(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			List<String> getterNames = CollectionUtils.newArrayList();
			Class<?> returnType;
			String name;
			for (Method method : methods) {
				name = method.getName();
				if (ArrayUtils.isEmpty(method.getParameterTypes()) 
						&& (returnType = method.getReturnType()) != void.class
						&& ((name.startsWith("get") && name.length() > 3) 
								|| (name.startsWith("is") && name.length() > 2  
										&& returnType == boolean.class)))
					getterNames.add(name);
			}
			return getterNames;
		}
		return null;
	}
	
	/**
	 * @description 检索当前对象所有的setter方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<Method> findAllSetter(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			List<Method> setters = CollectionUtils.newArrayList();
			String name;
			for (Method method : methods) {
				// 没有判断返回类型是否为void，允许setter方法有返回类型
				if (ArrayUtils.length(method.getParameterTypes()) == 1 && 
						(name = method.getName()).startsWith("set") && name.length() > 3) 
					setters.add(method);
			}
			return setters;
		}
		return null;
	}
	
	/**
	 * @description 检索当前对象所有的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 */
	public static <T> List<String> findAllSetterName(T bean) {
		List<Method> methods = ReflectionUtils.getDeclaredMethods(bean);
		if (CollectionUtils.isNotEmpty(methods)) {
			List<String> setterNames = CollectionUtils.newArrayList();
			String name;
			for (Method method : methods) {
				// 没有判断返回类型是否为void，允许setter方法有返回类型
				if (ArrayUtils.length(method.getParameterTypes()) == 1 && 
						(name = method.getName()).startsWith("set") && name.length() > 3) 
					setterNames.add(name);
			}
			return setterNames;
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
				return paramterType != boolean.class ? 
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
		return beanReflector.getterName(bean, propertyName);
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
		return beanReflector.setterName(bean, propertyName);
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
		return beanReflector.get(bean, propertyName);
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
	 * @throws Exception 
	 */
	public static <T> void set(T bean, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception {
		beanReflector.set(bean, propertyName, parameterType, parameterValue);
	}
	
	/**
	 * @description 调用全限定名对应类的构造函数创建Bean实例
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
	 * @description 调用全限定名对应类的构造函数创建Bean实例，并调用各属性对应的setter方法赋值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @param propertyValues
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> T create(String className, Map<String, V> propertyValues) throws Exception {
		AssertUtils.assertTrue(StringUtils.isNotBlank(className), "Created bean class name must not be null or blank.");
		return (T) create(Class.forName(className.trim()), propertyValues);
	}
	
	/**
	 * @description 创建Bean实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T create(Class<T> clazz) throws Exception {
		return create(clazz, ( Map<String, Object>) null);
	}
	
	/**
	 * @description 创建Bean实例，并调用各属性对应的setter方法赋值
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
	 * @description 根据当前Bean对象创建一个Map，其键值对就是bean的每一个属性名和值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static <T, V> Map<String, V> create(T bean) throws Exception {
		return create(bean, null);
	}
	
	/**
	 * @description 根据当前Bean对象创建一个Map
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param excludesName
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> Map<String, V> create(T bean, String[] excludesName) throws Exception {
		if (bean == null)
			return null;
		
		List<String> getterNames = findAllGetterName(bean);
		// 出于对性能的考虑，此方法只调用getter方法进行设值处理
		if (CollectionUtils.isNotEmpty(getterNames)) {
			Map<String, V> map = MapUtils.newHashMap();
			String key;
			for (String getterName : getterNames) {
				key = StringUtils.afterPrefix(getterName, "get");
				if (StringUtils.isEmpty(key))
					key = StringUtils.afterPrefix(getterName, "is");
				
				key = StringUtils.uncapitalize(key);
				if (!ArrayUtils.contains(excludesName, key))
					map.put(key, (V) ReflectionUtils.invokeMethod(bean, getterName));
			}
			return map;
		}
		
		return null;
	}
			
}
