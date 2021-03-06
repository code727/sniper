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

import org.sniper.beans.reflector.BeanReflector;
import org.sniper.beans.reflector.DefaultBeanReflector;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;

/**
 * JAVA Bean工具类
 * @author  Daniele
 * @version 1.0
 */
public class BeanUtils {
	
	private static final BeanReflector beanReflector = new DefaultBeanReflector();
	
	/**
	 * 检索当前对象所有的getter方法
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @return
	 */
	public static List<Method> findGetters(Object obj) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		if (currentType == null)
			return null;
		
		List<Method> getters = CollectionUtils.newArrayList();
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method declaredMethod : declaredMethods) {
					if (ReflectionUtils.isGetter(declaredMethod))
						getters.add(declaredMethod);
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
				
		return getters;
	}
	
	/**
	 * 检索当前对象所有的getter方法名称
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @return
	 */
	public static List<String> findGetterNames(Object obj) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		if (currentType == null)
			return null;
		
		List<String> names = CollectionUtils.newArrayList();
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method declaredMethod : declaredMethods) {
					if (ReflectionUtils.isGetter(declaredMethod))
						names.add(declaredMethod.getName());
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
				
		return names;
	}
	
	/**
	 * 检索成员属性对应的getter方法
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @param propertyName 属性名称
	 * @return
	 */
	public static Method findGetter(Object obj, String propertyName) {
		Class<?> currentType;
		if (StringUtils.isBlank(propertyName) || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		String getterName = buildGetterName(propertyName);
		String booleanGetterName = buildBooleanGetterName(propertyName);
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method declaredMethod : declaredMethods) {
					if (matchesGetter(declaredMethod, getterName, booleanGetterName))
						return declaredMethod;
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return null;
	}
	
	/**
	 * 检索成员属性对应的getter方法名称
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @param propertyName
	 * @return
	 */
	public static String findGetterName(Object obj, String propertyName) {
		Method getter = findGetter(obj, propertyName);
		return getter != null ? getter.getName() : null;
	}
	
	/**
	 * 检索当前对象所有的setter方法
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @return
	 */
	public static List<Method> findSetters(Object obj) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		if (currentType == null)
			return null;
		
		List<Method> setters = CollectionUtils.newArrayList();
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method declaredMethod : declaredMethods) {
					if (ReflectionUtils.isSetter(declaredMethod))
						setters.add(declaredMethod);
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
				
		return setters;
	}
	
	/**
	 * 检索当前对象所有的setter方法名称
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @return
	 */
	public static List<String> findSetterNames(Object obj) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		if (currentType == null)
			return null;
		
		List<String> names = CollectionUtils.newArrayList();
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method declaredMethod : declaredMethods) {
					if (ReflectionUtils.isSetter(declaredMethod))
						names.add(declaredMethod.getName());
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
				
		return names;
	}
	
	/**
	 * 检索成员属性对应的setter方法
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @param propertyName
	 * @return
	 */
	public static Method findSetter(Object obj, String propertyName) {
		return findSetter(obj, propertyName, null);
	}
	
	/**
	 * 检索成员属性对应的setter方法
	 * @author Daniele 
	 * @param bean bean对象或class类型
	 * @param propertyName 属性名称
	 * @param parameterType 方法参数类型
	 * @return
	 */
	public static Method findSetter(Object obj, String propertyName, Class<?> parameterType) {
		Class<?> currentType;
		if (StringUtils.isBlank(propertyName) || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		String setterName = buildSetterName(propertyName);
		Method[] declaredMethods;
		if (parameterType != null) {
			do {
				declaredMethods = currentType.getDeclaredMethods();
				if (ArrayUtils.isNotEmpty(declaredMethods)) {
					for (Method declaredMethod : declaredMethods) {
						if (matchesSetter(declaredMethod, setterName, parameterType))
							return declaredMethod;
					}
				}
				currentType = currentType.getSuperclass();
			} while (ClassUtils.isNotTopType(currentType));
		} else {
			do {
				declaredMethods = currentType.getDeclaredMethods();
				if (ArrayUtils.isNotEmpty(declaredMethods)) {
					for (Method declaredMethod : declaredMethods) {
						if (matchesSetter(declaredMethod, setterName))
							return declaredMethod;
					}
				}
				currentType = currentType.getSuperclass();
			} while (ClassUtils.isNotTopType(currentType));
		}
		return null;
	}
	
	/**
	 * 检索成员属性对应的setter方法名称
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @param propertyName
	 * @return
	 */
	public static String findSetterName(Object obj, String propertyName) {
		return findSetterName(obj, propertyName, null);
	}
	
	/**
	 * 检索成员属性对应的setter方法名称
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @param propertyName
	 * @param parameterType
	 * @return
	 */
	public static <T> String findSetterName(Object obj, String propertyName, Class<?> parameterType) {
		Method setter = findSetter(obj, propertyName, parameterType);
		return setter != null ? setter.getName() : null;
	}
	
	/**
	 * 根据属性名称或对应的Getter方法检索出属性类型
	 * @author Daniele 
	 * @param obj
	 * @param memberName
	 * @return
	 */
	public static Class<?> findPropertyTypeByNameOrGetter(Object obj, String propertyName) {
		Class<?> propertyType = ReflectionUtils.getFieldType(obj, propertyName);
		if (propertyType == null) {
			Method getter = BeanUtils.findGetter(obj, propertyName);
			return getter != null ? getter.getReturnType() : null;
		}
		
		return propertyType;
	}
	
	/**
	 * 根据属性名称或对应的Setter方法检索出属性类型
	 * @author Daniele 
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public static Class<?> findPropertyTypeByNameOrSetter(Object obj, String propertyName) {
		Class<?> memberType = ReflectionUtils.getFieldType(obj, propertyName);
		if (memberType == null) {
			Method setter = BeanUtils.findSetter(obj, propertyName);
			return setter != null ? setter.getParameterTypes()[0] : null;
		}
		
		return memberType;
	}
	
	/**
	 * 根据当前Bean的getter方法检索出对应的属性名
	 * @author Daniele 
	 * @param bean
	 * @return
	 */
	public static <T> List<String> findPropertyNamesByGetter(T bean) {
		return findPropertyNamesByGetter(bean, null);
	}
	
	/**
	 * 根据当前Bean的getter方法检索出对应的属性名，其中excludeNames数组内罗列的属性名称将排除在检索范围外
	 * @author Daniele 
	 * @param bean
	 * @param excludeNames
	 * @return
	 */
	public static <T> List<String> findPropertyNamesByGetter(T bean, String[] excludeNames) {
		List<Method> getters = findGetters(bean);
		if (CollectionUtils.isEmpty(getters))
			return null;
		
		List<String> list = CollectionUtils.newArrayList();
		for (Method getter : getters) {
			String propertyName = getterToPropertyName(getter, excludeNames);
			if (propertyName != null)
				list.add(propertyName);
		}
		return list;
	}
	
	/**
	 * 检索嵌套成员属性对应的getter方法名称
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @param propertyName 属性名
	 * @return
	 */
	public static <T> String findNestedGetterName(Object obj, String propertyName) {
		return beanReflector.findGetterName(obj, propertyName);
	}
	
	/**
	 * 检索嵌套成员属性对应的setter方法名称
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @param propertyName 属性名
	 * @return
	 */
	public static String findNestedSetterName(Object obj, String propertyName) {
		return findNestedSetterName(obj, propertyName, null);
	}
	
	/**
	 * 检索嵌套成员属性对应的setter方法名称
	 * @author Daniele 
	 * @param obj bean对象或class类型
	 * @param propertyName 属性名
	 * @param parameterType 方法的参数类型
	 * @return
	 */
	public static String findNestedSetterName(Object obj, String propertyName, Class<?> parameterType) {
		return beanReflector.findSetterName(obj, propertyName, parameterType);
	}
	
	/**
	 * 获取当前Bean对象属性的值
	 * @author Daniele 
	 * @param bean bean对象
	 * @param propertyName 属性名
	 * @return
	 * @throws Exception 
	 */
	public static <V> V getPropertyValue(Object bean, String propertyName) throws Exception {
		return beanReflector.getPropertyValue(bean, propertyName);
	}
		
	/**
	 * 设置当前Bean对象属性的值
	 * @author Daniele 
	 * @param bean bean对象
	 * @param propertyName 属性名
	 * @param parameterValue 参数值
	 * @throws Exception 
	 */
	public static void setPropertyValue(Object bean, String propertyName, Object parameterValue) throws Exception {
		setPropertyValue(bean, propertyName, null, parameterValue);
	}
	
	/**
	 * 调用当前Bean对象属性的setter方法
	 * @author Daniele 
	 * @param bean bean对象
	 * @param parameterType 方法的参数类型
	 * @param parameterValue 参数值
	 * @throws Exception 
	 */
	public static void setPropertyValue(Object bean, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception {
		beanReflector.setPropertyValue(bean, propertyName, parameterType, parameterValue);
	}
	
	/**
	 * 调用全限定名对应类的构造函数创建Bean实例
	 * @author Daniele 
	 * @param beanClassName Bean对象全限定名
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T create(String beanClassName) throws Exception { 
		return (T) create(beanClassName, (Map<String, Object>) null);
	}
	
	/**
	 * 调用全限定名对应类的构造函数创建Bean实例，并将映射集内的各属性值赋予Bean实例
	 * @author Daniele 
	 * @param beanClassName Bean对象全限定名
	 * @param properties 属性-值映射集
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> T create(String beanClassName, Map<String, V> properties) throws Exception {
		AssertUtils.assertNotBlank(beanClassName, "Bean class name must not be null or blank");
		
		return (T) create(Class.forName(beanClassName.trim()), properties);
	}
	
	/**
	 * 创建Bean实例
	 * @author Daniele 
	 * @param beanType Bean对象类型
	 * @return
	 * @throws Exception
	 */
	public static <T> T create(Class<T> beanType) throws Exception {
		return create(beanType, (Map<String, Object>) null);
	}
	
	/**
	 * 创建Bean实例，并将映射集内的各属性值赋予Bean实例
	 * @author Daniele 
	 * @param beanType Bean对象对象
	 * @param properties 属性-值映射集
	 * @return
	 * @throws Exception 
	 */
	public static <T, V> T create(Class<T> beanType, Map<String, V> properties) throws Exception {
		return beanReflector.create(beanType, properties);
	}
	
	/**
	 * 根据当前Bean对象创建一个Map，其键值对就是bean的每一个属性名和值
	 * @author Daniele 
	 * @param bean Bean对象
	 * @return
	 * @throws Exception
	 */
	public static <T, V> Map<String, V> create(T bean) throws Exception {
		return create(bean, null);
	}
	
	/**
	 * 根据当前Bean对象创建一个Map，在创建的过程中可以指定哪些属性不设置给Map对象
	 * @author Daniele 
	 * @param bean Bean对象
	 * @param excludeNames
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> Map<String, V> create(T bean, String[] excludeNames) throws Exception {
		List<Method> getters = findGetters(bean);
		if (CollectionUtils.isEmpty(getters))
			return null;
		
		Map<String, V> map = MapUtils.newHashMap();
		for (Method getter : getters) {
			String propertyName = getterToPropertyName(getter, excludeNames);
			/* 将getter方法转换为对应的属性名称不为空时，说明getter方法没有被排除在外，将getter方法的返回结果赋予Map即可*/
			if (propertyName != null)
				map.put(propertyName, (V) ReflectionUtils.invokeMethod(bean, getter));
		}
		return map;
	}
	
	/**
	 * 将Getter方法转换为对应的属性名称，其中excludeNames数组内罗列的属性名称将排除在转换范围外
	 * @author Daniele 
	 * @param getter
	 * @param excludeNames
	 * @return
	 */
	private static String getterToPropertyName(Method getter, String[] excludeNames) {
		String name = getter.getName();
		String propertyName = StringUtils.afterPrefix(name, ReflectionUtils.GETTER_PREFIX);
		if (StringUtils.isEmpty(propertyName))
			propertyName = StringUtils.afterPrefix(name, ReflectionUtils.BOOLEAN_GETTER_PREFIX);

		propertyName = StringUtils.uncapitalize(propertyName);
		return !ArrayUtils.contains(excludeNames, propertyName) ? propertyName : null;
	}
	
	/**
	 * 根据属性名称和指定的类型构建对应的Getter方法名称
	 * @author Daniele 
	 * @param propertyName
	 * @param type
	 * @return
	 */
	static String buildGetterName(String propertyName, Class<?> type) {
		return type == boolean.class ? buildBooleanGetterName(propertyName) : buildGetterName(propertyName);
	}
		
	/**
	 * 根据属性名称构建对应的Getter方法名称
	 * @author Daniele 
	 * @param propertyName
	 * @return
	 */
	static String buildGetterName(String propertyName) {
		return ReflectionUtils.GETTER_PREFIX + StringUtils.capitalize(propertyName);
	}
	
	/**
	 * 根据属性名称构建对应的布尔Getter方法名称
	 * @author Daniele 
	 * @param propertyName
	 * @return
	 */
	static String buildBooleanGetterName(String propertyName) {
		return ReflectionUtils.BOOLEAN_GETTER_PREFIX + StringUtils.capitalize(propertyName);
	}
	
	/**
	 * 根据属性名称构建对应的Setter方法名称
	 * @author Daniele 
	 * @param propertyName
	 * @return
	 */
	static String buildSetterName(String propertyName) {
		return ReflectionUtils.SETTER_PREFIX + StringUtils.capitalize(propertyName);
	}
		
	/**
	 * 判断方法对象是否与指定的Getter方法相匹配，匹配要求如下：</P>
	 * 1.方法自身不能有参数</P>
	 * 2.方法必须有返回</P>
	 * 3.方法名称必须与指定的Getter方法名称或布尔Getter方法名称匹配</P>
	 * @author Daniele 
	 * @param method
	 * @param getterName Getter方法名称
	 * @param booleanGetterName 布尔Getter方法名称
	 * @return
	 */
	private static boolean matchesGetter(Method method, String getterName, String booleanGetterName) {
		if (method == null || ArrayUtils.isNotEmpty(method.getParameterTypes()))
			return false;
		
		String name = method.getName();
		Class<?> returnType = method.getReturnType();
		return (returnType != void.class && name.equals(getterName))
				|| (returnType == boolean.class && name.equals(booleanGetterName));
	}
	
	/**
	 * 判断方法对象是否与指定的Setter方法相匹配，匹配要求如下：</P>
	 * 1.方法自身有且只有一个参数</P>
	 * 2.方法名称必须与指定的Setter方法名称匹配</P>
	 * @author Daniele 
	 * @param method
	 * @param getterName Getter方法名称
	 * @param booleanGetterName 布尔Getter方法名称
	 * @return
	 */
	private static boolean matchesSetter(Method method, String setterName) {
		if (method == null || ArrayUtils.length(method.getParameterTypes()) != 1)
			return false;
		
		return method.getName().equals(setterName);
	}
	
	/**
	 * 判断方法对象是否与指定的Setter方法相匹配，匹配要求如下：</P>
	 * 1.方法自身有且只有一个参数</P>
	 * 2.方法名称必须与指定的Setter方法名称匹配</P>
	 * 3.方法中唯一的参数类型必须与指定的类型匹配</P>
	 * @author Daniele 
	 * @param method
	 * @param setterName
	 * @param parameterType
	 * @return
	 */
	private static boolean matchesSetter(Method method, String setterName, Class<?> parameterType) {
		Class<?>[] parameterTypes;
		if (method == null || ArrayUtils.length(parameterTypes = method.getParameterTypes()) != 1)
			return false;
		
		return method.getName().equals(setterName) && parameterTypes[0] == parameterType;
	}
	
}
