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
 * Create Date : 2015-1-5
 */

package org.sniper.commons.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 反射工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ReflectionUtils {
	
	private ReflectionUtils() {}
	
	/**
	 * 获取对象内所有公有的构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Constructor<?>> getConstructors(Object object) {
		return getConstructors(object, false);
	}
	
	/**
	 * 获取对象内所有已声明的构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Constructor<?>> getDeclaredConstructors(Object object) {
		return getConstructors(object, true);
	}
	
	/**
	 * 获取对象内公有的无参构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static Constructor<?> getConstructor(Object object) {
		return getConstructor(object, null);
	}
	
	/**
	 * 根据参数类型获取对象内公有的构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes
	 * @return
	 */
	public static Constructor<?> getConstructor(Object object, Class<?>[] pTypes) {
		return getConstructor(object, pTypes, false);
	}
	
	/**
	 * 获取对象内已声明的无参构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static Constructor<?> getDeclaredConstructor(Object object) {
		return getDeclaredConstructor(object, null);
	}
	
	/**
	 * 根据参数类型获取对象内已声明的构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static Constructor<?> getDeclaredConstructor(Object object, Class<?>[] pTypes) {
		return getConstructor(object, pTypes, true);
	}
	
	/**
	 * 判断对象中是否存在无参公有构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static boolean hasConstructor(Object object) {
		return hasConstructor(object, null);
	}
	
	/**
	 * 判断对象中是否存在指定参数类型的公有构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes
	 * @return
	 */
	public static boolean hasConstructor(Object object, Class<?>[] pTypes) {
		return getConstructor(object, pTypes) != null;
	}
	
	/**
	 * 判断对象中是否存在无参构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static boolean hasDeclaredConstructor(Object object) {
		return hasDeclaredConstructor(object, null);
	}
	
	/**
	 * 判断对象中是否存在指定参数类型的构造方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes
	 * @return
	 */
	public static boolean hasDeclaredConstructor(Object object, Class<?>[] pTypes) {
		return getDeclaredConstructor(object, pTypes) != null;
	}
	
	/**
	 * 获取对象内所有公有的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Method> getMethods(Object object) {
		return getMethods(object, false);
	}
	
	/**
	 * 获取对象内所有已声明的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Method> getDeclaredMethods(Object object) {
		return getMethods(object, true);
	}
	
	/**
	 * 获取对象内所有公有的方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<String> getMethodNames(Object object) {
		return getMethodNames(object, false);
	}
	
	/**
	 * 获取对象内所有已声明的方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<String> getDeclaredMethodNames(Object object){
		return getMethodNames(object, true);
	}
	
	/**
	 * 根据名称获取对象内公有的无参方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @return
	 */
	public static Method getMethod(Object object, String methodName) {
		return getMethod(object, methodName, null);
	}
	
	/**
	 * 根据名称和参数类型获取对象内公有的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static Method getMethod(Object object, String methodName, Class<?>[] pTypes) {
		return getMethod(object, methodName, pTypes, false);
	}
	
	/**
	 * 根据名称获取对象内已声明的无参方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodName) {
		return getDeclaredMethod(object, methodName, null);
	}
		
	/**
	 * 根据名称和参数类型获取对象内已声明的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] pTypes) {
		return getMethod(object, methodName, pTypes, true);
	}
	
	/**
	 * 判断对象中是否存在指定名称的无参公有方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName
	 * @return
	 */
	public static boolean hasMethod(Object object, String methodName) {
		return hasMethod(object, methodName, null);
	}
	
	/**
	 * 判断对象中是否存在指定名称和参数类型的公有方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName
	 * @param pTypes
	 * @return
	 */
	public static boolean hasMethod(Object object, String methodName, Class<?>[] pTypes) {
		return getMethod(object, methodName, pTypes) != null;
	}
	
	/**
	 * 判断对象中是否存在指定名称的无参方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName
	 * @return
	 */
	public static boolean hasDeclaredMethod(Object object, String methodName){
		return hasDeclaredMethod(object, methodName, null);
	}
	
	/**
	 * 判断对象中是否存在指定名称和参数类型的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static boolean hasDeclaredMethod(Object object, String methodName, Class<?>[] pTypes){
		return getDeclaredMethod(object, methodName, pTypes) != null;
	}
	
	/**
	 * 获取对象内所有公有的域
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Field> getFields(Object object) {
		return getFields(object, false);
	}
	
	/**
	 * 获取对象内所有已声明的域
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Field> getDeclaredFields(Object object) {
		return getFields(object, true);
	}
	
	/**
	 * 获取对象内所有公有的域名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<String> getFieldNames(Object object) {
		return getFieldNames(object, false);
	}
		
	/**
	 * 获取对象内所有已声明的域名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<String> getDeclaredFieldNames(Object object) {
		return getFieldNames(object, true);
	}
	
	/**
	 * 根据名称获取对象内公有的域
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Object object, String fieldName) {
		return getField(object, fieldName, false);
	}
	
	/**
	 * 根据名称获取对象内已声明的域
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(Object object, String fieldName) {
		return getField(object, fieldName, true);
	}
	
	/**
	 * 判断对象中是否存在指定名称的公有域
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static boolean hasField(Object object, String fieldName) {
		return getField(object, fieldName) != null;
	}
	
	/**
	 * 判断对象中是否存在指定名称的无参方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static boolean hasDeclaredField(Object object, String fieldName){
		return getDeclaredField(object, fieldName) != null;
	}
		
	/**
	 * 获取指定对象某个域的类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Class<?> getFieldType(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		return field != null ? field.getType() : null;
	}
	
	/**
	 * 获取指定对象某个域的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static <V> V getFieldValue(Object object, String fieldName) throws Exception {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) 
			throw new NoSuchFieldException(buildNoSuchFieldExceptionMessage(object, fieldName));
			
		return getAccessibleFieldValue(object, field);
	}
	
	/**
	 * 获取当前对象某个属性的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param field
	 * @return
	 * @throws Exception 
	 */
	public static <V> V getFieldValue(Object object, Field field) throws Exception {
		AssertUtils.assertNotNull(object, "Target object must not be null");
		AssertUtils.assertNotNull(field, "Target field must not be null");

		return getAccessibleFieldValue(object, field);
	}
	
	/**
	 * 设置当前对象的某个属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target
	 * @param fieldName
	 * @param value
	 * @throws Exception 
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) throws Exception {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) 
			throw new NoSuchFieldException(buildNoSuchFieldExceptionMessage(object, fieldName));
					
		setAccessibleFieldValue(object, field, value);
	}
	
	/**
	 * 设置当前对象的某个属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param field
	 * @param value
	 * @throws Exception 
	 */
	public static void setFieldValue(Object object, Field field, Object value) throws Exception {
		AssertUtils.assertNotNull(object, "Target object must not be null");
		AssertUtils.assertNotNull(field, "Target field must not be null");
		
		setAccessibleFieldValue(object, field, value);
	}
	
	/** 
	 * 批量设置指定对象的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldValues
	 * @throws Exception 
	 */
	public static void setFieldValue(Object object, Map<String, Object> fieldValues) throws Exception {
		AssertUtils.assertTrue(MapUtils.isNotEmpty(fieldValues), "Field values map must not be empty");
		
		Set<Entry<String, Object>> fieldValueSet = fieldValues.entrySet();
		for (Entry<String, Object> fieldValue : fieldValueSet) {
			setFieldValue(object, fieldValue.getKey(), fieldValue.getValue());
		}
	}
	
	/**
	 * 根据全限定名对应类的默认构造函数创建实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @return
	 * @throws Exception 
	 */
	public static <T> T newInstance(String className) throws Exception {
		return newInstance(className, null, null);
	}
	
	/**
	 * 根据全限定名对应类的构造函数创建实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @param pTypes 构造函数中各参数对应的类型
	 * @param pValues 构造函数中各参数对应的值
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className, Class<?>[] pTypes, Object[] pValues) throws Exception {
		return (T) newInstance(Class.forName(className), pTypes, pValues);
	}
	
	/**
	 * 调用指定类的默认构造函数生成一个实例对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 * @throws Exception 
	 */
	public static <T> T newInstance(Class<T> clazz) throws Exception {
		return newInstance(clazz, null, null);
	}
	
	/**
	 * 调用指定类的构造函数生成一个该类的实例对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @param pTypes 构造函数中各参数对应的类型
	 * @param pValues 构造函数中各参数对应的值
	 * @return
	 */
	public static <T> T newInstance(Class<T> clazz, Class<?>[] pTypes, Object[] pValues) throws Exception {
		AssertUtils.assertNotNull(clazz, "Can not create instance by null class");
		
		Constructor<T> constructor = clazz.getDeclaredConstructor(pTypes);
		if (!constructor.isAccessible())
			constructor.setAccessible(true);
		
		return constructor.newInstance(pValues);
	}
	
	/**
	 * 调用目标对象无参方法后返回执行结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target 目标对象
	 * @param methodName 方法名称
	 * @return
	 * @throws Exception 
	 */
	public static Object invokeMethod(Object target, String methodName) throws Exception {
		return invokeMethod(target, methodName, null, null);
	}
	
	/**
	 * 调用目标对象方法后返回执行结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target 目标对象
	 * @param methodName 方法名称
	 * @param pTypes 方法中各参数对应的类型
	 * @param pValues 方法中各参数对应的值
	 * @return
	 * @throws Exception 
	 */
	public static Object invokeMethod(Object target, String methodName,
			Class<?>[] pTypes, Object[] pValues) throws Exception {
		
		Method method = getDeclaredMethod(target, methodName, pTypes);
		if (method == null)
			throw new NoSuchMethodException(buildNoSuchMethodExceptionMessage(target, methodName, pTypes));
		
		return invokeAccessibleMethod(target, method, pTypes, pValues);
	}
	
	/**
	 * 调用目标对象某个被强制设置为可访问的方法后返回调用结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target
	 * @param method
	 * @param pTypes
	 * @param pValues
	 * @return
	 * @throws Exception
	 */
	static Object invokeAccessibleMethod(Object target, Method method, Class<?>[] pTypes, Object[] pValues) throws Exception {
		if (!method.isAccessible())
			method.setAccessible(true);
		
		// 参数类型或值列表为空时，则调用无参方法
		if (ArrayUtils.isEmpty(pTypes) || ArrayUtils.isEmpty(pValues))
			return method.invoke(target);

		int typeCount = pTypes.length;
		int valueCount = pValues.length;
		if (typeCount < valueCount) {
			/* 实际的类型个数小于值列表个数时，则忽略多余的参数值 */
			Object[] values = new Object[typeCount];
			System.arraycopy(pValues, 0, values, 0, typeCount);
			return method.invoke(target, values);
		}
		
		return method.invoke(target, pValues);
	}
	
	/**
	 * 选择性获取对象内的构造方法列表</P>
	 * 1.declared==true，获取所有已声明的构造方法</P>
	 * 2.declared==false，只获取所有公有的构造方法</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param declared
	 * @return
	 */
	private static List<Constructor<?>> getConstructors(Object object, boolean declared) {
		Class<?> currentType = ClassUtils.getCurrentType(object);
		if (currentType == null)
			return null;
		
		List<Constructor<?>> constructors = CollectionUtils.newArrayList();
		if (declared) {
			Constructor<?>[] declaredConstructors = currentType.getDeclaredConstructors();
			if (ArrayUtils.isNotEmpty(declaredConstructors)) 
				constructors.addAll(Arrays.asList(declaredConstructors));
		} else {
			Constructor<?>[] publicConstructors = currentType.getConstructors();
			if (ArrayUtils.isNotEmpty(publicConstructors)) 
				constructors.addAll(Arrays.asList(publicConstructors));
		}
		
		return constructors;
	}
	
	/**
	 * 根据参数类型选择性获取对象内的构造方法</P>
	 * 1.declared==true，获取已声明的构造方法</P>
	 * 2.declared==false，只获取公有的构造方法</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes
	 * @param declared
	 * @return
	 */
	private static Constructor<?> getConstructor(Object object, Class<?>[] pTypes, boolean declared) {
		Class<?> currentType = ClassUtils.getCurrentType(object);
		if (currentType == null)
			return null;
		
		try {
			return declared ? currentType.getDeclaredConstructor(pTypes) : currentType.getConstructor(pTypes);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * 选择性获取对象内的方法列表</P>
	 * 1.declared==true，获取所有已声明的方法</P>
	 * 2.declared==false，只获取所有公有的方法</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param declared
	 * @return
	 */
	private static List<Method> getMethods(Object object, boolean declared) {
		Class<?> currentType = ClassUtils.getCurrentType(object);
		if (currentType == null)
			return null;
		
		List<Method> methods = CollectionUtils.newArrayList();
		if (declared) {
			do {
				Method[] declaredMethods = currentType.getDeclaredMethods();
				if (ArrayUtils.isNotEmpty(declaredMethods))
					methods.addAll(Arrays.asList(declaredMethods));
				
				currentType = currentType.getSuperclass();
			} while (currentType != null && currentType != Object.class);
		} else {
			do {
				Method[] publicMethods = currentType.getMethods();
				if (ArrayUtils.isNotEmpty(publicMethods))
					methods.addAll(Arrays.asList(publicMethods));
				
				currentType = currentType.getSuperclass();
			} while (currentType != null && currentType != Object.class);
		}
		
		return methods;
	}
	
	/**
	 * 选择性获取对象内的方法名称列表</P>
	 * 1.declared==true，获取所有已声明的方法名称</P>
	 * 2.declared==false，只获取所有公有的方法名称</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param declared
	 * @return
	 */
	private static List<String> getMethodNames(Object object, boolean declared) {
		Class<?> currentType = ClassUtils.getCurrentType(object);
		if (currentType == null)
			return null;
		
		List<String> names = CollectionUtils.newArrayList();
		if (declared) {
			do {
				Method[] declaredMethods = currentType.getDeclaredMethods();
				if (ArrayUtils.isNotEmpty(declaredMethods)) {
					for (Method method : declaredMethods) {
						names.add(method.getName());
					}
				}
				
				currentType = currentType.getSuperclass();
			} while (currentType != null && currentType != Object.class);
		} else {
			do {
				Method[] publicMethods = currentType.getMethods();
				if (ArrayUtils.isNotEmpty(publicMethods)) {
					for (Method method : publicMethods) {
						names.add(method.getName());
					}
				}
				
				currentType = currentType.getSuperclass();
			} while (currentType != null && currentType != Object.class);
		}
		
		return names;
	}
	
	/**
	 * 根据名称和参数类型选择性获取对象内的方法</P>
	 * 1.declared==true，获取已声明的方法</P>
	 * 2.declared==false，只获取公有的方法</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName
	 * @param pTypes
	 * @param declared
	 * @return
	 */
	private static Method getMethod(Object object, String methodName, Class<?>[] pTypes, boolean declared) {
		Class<?> currentType;
		if (StringUtils.isBlank(methodName) || (currentType = ClassUtils.getCurrentType(object)) == null)
			return null;
		
		methodName = methodName.trim();
		if (declared) {
			do {
				try {
					return currentType.getDeclaredMethod(methodName, pTypes);
				} catch (NoSuchMethodException e) {
					currentType = currentType.getSuperclass();
				}
			} while (currentType != null && currentType != Object.class);
		} else {
			do {
				try {
					return currentType.getMethod(methodName, pTypes);
				} catch (NoSuchMethodException e) {
					currentType = currentType.getSuperclass();
				}
			} while (currentType != null && currentType != Object.class);
		}
		
		return null;
	}
	
	/**
	 * 选择性获取对象内的域列表</P>
	 * 1.declared==true，获取已声明的域</P>
	 * 2.declared==false，只获取公有的域</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param declared
	 * @return
	 */
	private static List<Field> getFields(Object object, boolean declared) {
		Class<?> currentType = ClassUtils.getCurrentType(object);
		if (currentType == null)
			return null;
		
		List<Field> fields = CollectionUtils.newArrayList();
		if (declared) {
			do {
				Field[] declaredFields = currentType.getDeclaredFields();
				if (ArrayUtils.isNotEmpty(declaredFields)) 
					fields.addAll(Arrays.asList(declaredFields));
				
				currentType = currentType.getSuperclass();
			} while(currentType != null && currentType != Object.class);
		} else {
			do {
				Field[] publicFields = currentType.getFields();
				if (ArrayUtils.isNotEmpty(publicFields)) 
					fields.addAll(Arrays.asList(publicFields));
				
				currentType = currentType.getSuperclass();
			} while(currentType != null && currentType != Object.class);
		}
		
		return fields;
	}
	
	/**
	 * 选择性获取对象内的域名称列表</P>
	 * 1.declared==true，获取已声明的域名称</P>
	 * 2.declared==false，只获取公有的域名称</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param declared
	 * @return
	 */
	private static List<String> getFieldNames(Object object, boolean declared) {
		Class<?> currentType = ClassUtils.getCurrentType(object);
		if (currentType == null)
			return null;
		
		List<String> names = CollectionUtils.newArrayList();
		if (declared) {
			do {
				Field[] declaredFields = currentType.getDeclaredFields();
				if (ArrayUtils.isNotEmpty(declaredFields)) {
					for (Field field : declaredFields) {
						names.add(field.getName());
					}
				}
				
				currentType = currentType.getSuperclass();
			} while(currentType != null && currentType != Object.class);
		} else {
			do {
				Field[] publicFields = currentType.getFields();
				if (ArrayUtils.isNotEmpty(publicFields)) {
					for (Field field : publicFields) {
						names.add(field.getName());
					}
				}
				
				currentType = currentType.getSuperclass();
			} while(currentType != null && currentType != Object.class);
		}
		
		return names;
	}
	
	/**
	 * 根据名称选择性获取对象内的域</P>
	 * 1.declared==true，获取已声明的域</P>
	 * 2.declared==false，只获取公有的域</P>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @param declared
	 * @return
	 */
	private static Field getField(Object object, String fieldName, boolean declared) {
		Class<?> currentType;
		if (StringUtils.isBlank(fieldName) || (currentType = ClassUtils.getCurrentType(object)) == null)
			return null;
		
		fieldName = fieldName.trim();
		if (declared) {
			do {
				try {
					return currentType.getDeclaredField(fieldName);
				} catch (NoSuchFieldException e) {
					currentType = currentType.getSuperclass();
				}
			} while (currentType != null && currentType != Object.class);
		} else {
			do {
				try {
					return currentType.getField(fieldName);
				} catch (NoSuchFieldException e) {
					currentType = currentType.getSuperclass();
				}
			} while (currentType != null && currentType != Object.class);
		}
		
		return null;
	}
	
	/**
	 * 获取指定对象某个被强制设置为可访问的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param field
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static <V> V getAccessibleFieldValue(Object object, Field field) throws Exception {
		if (!field.isAccessible())
			field.setAccessible(true);
		
		return (V) field.get(object);
	}
	
	/**
	 * 设置指定对象某个被强制设置为可访问的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param field
	 * @param value
	 * @throws Exception
	 */
	private static void setAccessibleFieldValue(Object object, Field field, Object value) throws Exception {
		if (!field.isAccessible())
			field.setAccessible(true);
		
		field.set(object, value);
	}
	
	/**
	 * 根据指定对象和域名称构建NoSuchFieldException消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static String buildNoSuchFieldExceptionMessage(Object object, String fieldName) {
		Class<?> currentType = ClassUtils.getCurrentType(object);
		return String.format("{\"currentType\":%s,\"fieldName\":%s}", 
				(currentType != null ? StringUtils.appendDoubleQuotes(currentType.getName()) : currentType), 
				(fieldName != null ? StringUtils.appendDoubleQuotes(fieldName) : fieldName));
	}
	
	/**
	 * 根据指定对象、方法名称和参数类型构建NoSuchMethodException消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 
	 * @param fieldName
	 * @return
	 */
	private static String buildNoSuchMethodExceptionMessage(Object object, String methodName, Class<?>[] pTypes) {
		Class<?> currentType = ClassUtils.getCurrentType(object);
		
		String pTypesMessage = "[]";
		if (ArrayUtils.isNotEmpty(pTypes)) {
			int max = pTypes.length - 1;
			Class<?> type; 
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < max; i++) {
				type = pTypes[i];
				builder.append(type != null ? StringUtils.appendDoubleQuotes(type.getName()) : type).append(StringUtils.COMMA);
			}
			
			type = pTypes[max];
			builder.append(type != null ? StringUtils.appendDoubleQuotes(type.getName()) : type);
			pTypesMessage = StringUtils.append(builder.toString(), "[", "]").toString();
		}
		
		return String.format("{\"currentType\":%s,\"methodName\":%s,\"parameterTypes\":%s}", 
				(currentType != null ? StringUtils.appendDoubleQuotes(currentType.getName()) : currentType), 
				(methodName != null ? StringUtils.appendDoubleQuotes(methodName) : methodName), pTypesMessage);
	}
		
}
