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
	 * 获取当前对象所在类以及非Object基类中定义的所有方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Method> getDeclaredMethods(Object object) {
		Class<?> declaredClass = ClassUtils.getDeclaredType(object);
		if (declaredClass != null) {
			List<Method> method = CollectionUtils.newArrayList();
			while (declaredClass != null && declaredClass != Object.class) {
				// 如果当前对象所属的类不是顶层基类(Object),则获取此类以及基类中的所有方法
				Method[] declaredMethods = declaredClass.getDeclaredMethods();
				if (ArrayUtils.isNotEmpty(declaredMethods)) {
					method.addAll(Arrays.asList(declaredMethods));
				}
				
				declaredClass = declaredClass.getSuperclass();
			}
			return method;
		}
		return null;
	}
	
	/**
	 * 获取当前对象所在类以及非Object基类中定义的所有方法的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<String> getDeclaredMethodNames(Object object){
		List<Method> methods = getDeclaredMethods(object);
		if (CollectionUtils.isEmpty(methods)) {
			return null;
		}
		
		List<String> names = CollectionUtils.newArrayList();
		for (Method method : methods) {
			names.add(method.getName());
		}
			
		return names;
	}
	
	/**
	 * 获取当前对象所在类或非Object基类中所指定的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] pTypes) {
		if (StringUtils.isBlank(methodName)) {
			return null;
		}
		
		Class<?> declaredClass = ClassUtils.getDeclaredType(object);
		if (declaredClass != null) {
			methodName = methodName.trim();
			while (declaredClass != null && declaredClass != Object.class) {
				try {
					return declaredClass.getDeclaredMethod(methodName, pTypes);
				} catch (NoSuchMethodException e) {
					declaredClass = declaredClass.getSuperclass();
				}
			}
		}
		return null;
	}
	
	/**
	 * 判断指定的方法是否存在于当前对象所在类或非Object基类中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static boolean isDeclaredMethod(Object object, String methodName, Class<?>[] pTypes){
		return getDeclaredMethod(object, methodName, pTypes) != null;
	}
	
	/**
	 * 获取当前对象所在类中所指定的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static Method getMethod(Object object, String methodName, Class<?>[] pTypes) {
		if (StringUtils.isBlank(methodName))
			return null;
		
		Class<?> declaredClass = ClassUtils.getDeclaredType(object);
		while (declaredClass != null) {
			methodName = methodName.trim();
			try {
				return declaredClass.getDeclaredMethod(methodName, pTypes);
			} catch (NoSuchMethodException e) {
				declaredClass = declaredClass.getSuperclass();
			}
		}
		return null;
	}
	
	/**
	 * 判断指定的方法是否存在于当前对象中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static boolean isMethod(Object object, String methodName, Class<?>[] pTypes) {
		return getMethod(object, methodName, pTypes) != null;
	}
	
	/**
	 * 获取当前对象所在类以及非Object基类中定义的所有构造函数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Constructor<?>> getDeclaredConstructors(Object object) {
		Class<?> declaredClass = ClassUtils.getDeclaredType(object);
		if (declaredClass != null) {
			List<Constructor<?>> constructors = CollectionUtils.newArrayList();
			while (declaredClass != null && declaredClass != Object.class) {
				// 如果当前对象所属的类不是顶级基类(Object),则获取此类以及基类中的所有方法
				Constructor<?>[] declaredConstructors = declaredClass.getDeclaredConstructors();
				if (ArrayUtils.isNotEmpty(declaredConstructors)) {
					constructors.addAll(Arrays.asList(declaredConstructors));
				}
				
				declaredClass = declaredClass.getSuperclass();
			}
			return constructors;
		}
		return null;
	}
	
	/**
	 * 获取当前对象所在类以及非Object基类中定义的所有构造函数名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static Set<String> getDeclaredConstructorNames(Object object) {
		List<Constructor<?>> constructors = getDeclaredConstructors(object);
		if (CollectionUtils.isEmpty(constructors)) {
			return null;
		}
		
		Set<String> names = CollectionUtils.newHashSet();
		for (Constructor<?> constructor : constructors) {
			names.add(constructor.getName());
		}
			
		return names;
	}
	
	/**
	 * 获取当前对象所在类或非Object基类中所指定的构造函数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static Constructor<?> getDeclaredConstructor(Object object, Class<?>[] pTypes) {
		Class<?> declaredClass = ClassUtils.getDeclaredType(object);
		if (declaredClass != null) {
			while (declaredClass != null && declaredClass != Object.class) {
				try {
					return declaredClass.getDeclaredConstructor(pTypes);
				} catch (NoSuchMethodException e) {
					declaredClass = declaredClass.getSuperclass();
				}
			}
		}
		return null;
	}
		
	/**
	 * 判断指定的构造函数是否存在于当前对象所在类或非Object基类中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static boolean isDeclaredConstructor(Object object, Class<?>[] pTypes) {
		return getDeclaredConstructor(object, pTypes) != null;
	}
	
	/**
	 * 获取当前对象所在类中所指定的构造函数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static Constructor<?> getConstructor(Object object, Class<?>[] pTypes) {
		Class<?> declaredClass = ClassUtils.getDeclaredType(object);
		if (declaredClass != null) {
			while (declaredClass != null) {
				try {
					return declaredClass.getDeclaredConstructor(pTypes);
				} catch (NoSuchMethodException e) {
					declaredClass = declaredClass.getSuperclass();
				}
			}
		}
		return null;
	}
	
	/**
	 * 判断指定的构造函数是否存在于当前对象中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static boolean isConstructor(Object object, Class<?>[] pTypes) {
		return getConstructor(object, pTypes) != null;
	}
	
	/**
	 * 获取当前对象所在类以及非Object基类中定义的所有属性对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Field> getDeclaredFields(Object object) {
		Class<?> declaredClass = ClassUtils.getDeclaredType(object);
		if (declaredClass != null) {
			List<Field> fields = CollectionUtils.newArrayList();
			while (declaredClass != null && declaredClass != Object.class) {
				Field[] declaredFields = declaredClass.getDeclaredFields();
				if (ArrayUtils.isNotEmpty(declaredFields)) {
					fields.addAll(Arrays.asList(declaredFields));
				}
				
				declaredClass = declaredClass.getSuperclass();
			}
			return fields;
		}
		return null;
	}
		
	/**
	 * 获取当前对象所在类以及非Object基类中定义的所有属性名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<String> getDeclaredFieldNames(Object object) {
		List<Field> fields = getDeclaredFields(object);
		if (CollectionUtils.isEmpty(fields)) {
			return null;
		}
		
		List<String> fieldName = CollectionUtils.newArrayList();
		for (Field field : fields) {
			fieldName.add(field.getName());
		}
			
		return fieldName;
	}
	
	/**
	 * 获取当前对象所在类或非Object基类中所指定的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(Object object, String fieldName) {
		if (StringUtils.isBlank(fieldName)) {
			return null;
		}
		
		Class<?> declaredClass = ClassUtils.getDeclaredType(object);
		if (declaredClass != null) {
			fieldName = fieldName.trim();
			while (declaredClass != null && declaredClass != Object.class) {
				try {
					return declaredClass.getDeclaredField(fieldName);
				} catch (NoSuchFieldException e) {
					declaredClass = declaredClass.getSuperclass();
				}
			}
		}
		return null;
	}
	
	/**
	 * 判断指定的属性是否存在于当前对象所在类或非Object基类中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static boolean isDeclaredField(Object object, String fieldName){
		return getDeclaredField(object, fieldName) != null;
	}
	
	/**
	 * 获取当前对象所在类中所指定的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Object object, String fieldName) {
		// 由于Object类没有属性，因此结果与getDeclaredField方法一样
		return getDeclaredField(object, fieldName);
	}
	
	/**
	 * 判断指定的属性是否存在于当前对象中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static boolean isField(Object object, String fieldName) {
		return getField(object, fieldName) != null;
	}
	
	/**
	 * 获取当前对象某个属性的类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Class<?> getFieldType(Object object,String fieldName){
		Field field = getField(object, fieldName);
		return field != null ? field.getType() : null;
	}
	
	/**
	 * 获取当前对象某个属性的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static <V> V getFieldValue(Object object, String fieldName) throws Exception {
		Field field = getField(object, fieldName);
		if (field == null)
			throw new NoSuchFieldException("No such target field [" + StringUtils.safeString(fieldName) + "]," + 
				"Please check whether invoked target and field name not be null,and ensure field are present in the current target class.");
		
		return Supports.getAccessibleFieldValue(object, field);
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
		AssertUtils.assertNotNull(object, "Target object can not be null");
		return Supports.getAccessibleFieldValue(object, field);
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
		Field field = getField(object, fieldName);
		if (field == null)
			throw new NoSuchFieldException("No such target field [" + StringUtils.safeString(fieldName) + "]," + 
				"Please check whether invoked target and field name not be null,and ensure field are present in the current target class.");
		
		Supports.setAccessibleFieldValue(object, field, value);
	}
	
	/** 
	 * 批量设置当前对象的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldValues
	 * @throws Exception 
	 */
	public static void setFieldValue(Object object, Map<String, Object> fieldValues) throws Exception {
		AssertUtils.assertTrue(MapUtils.isNotEmpty(fieldValues), "Field values map can not be empty");
		
		if (object != null && MapUtils.isNotEmpty(fieldValues)) {
			Set<Entry<String, Object>> fieldValueSet = fieldValues.entrySet();
			for (Entry<String, Object> fieldValue : fieldValueSet) 
				setFieldValue(object, fieldValue.getKey(), fieldValue.getValue());
		}
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
		AssertUtils.assertNotNull(object, "Target object can not be null");
		Supports.setAccessibleFieldValue(object, field, value);
	}
	
	/**
	 * 根据调用全限定名对应类的默认构造函数创建实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @return
	 * @throws Exception 
	 */
	public static <T> T newInstance(String className) throws Exception {
		return newInstance(className, null, null);
	}
	
	/**
	 * 调用全限定名对应类的构造函数创建实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @param pTypes
	 * @param pValues
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
	 * @param c
	 * @return
	 * @throws Exception 
	 */
	public static <T> T newInstance(Class<T> c) throws Exception {
		return newInstance(c, null, null);
	}
	
	/**
	 * 调用指定类的构造函数生成一个该类的实例对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @param pTypes 构造函数中各参数对应的类型
	 * @param pValues 构造函数中对应的参数值
	 * @return
	 */
	public static <T> T newInstance(Class<T> c, Class<?>[] pTypes, Object[] pValues) throws Exception {
		AssertUtils.assertNotNull(c, "Can not be create instance by null class");
		
		if (ArrayUtils.isNotEmpty(pTypes)) {
			Constructor<T> constructor = c.getDeclaredConstructor(pTypes);
			constructor.setAccessible(true);
			return constructor.newInstance(pValues);
		} else
			// 参数类型为空时，则调用目标无参数的构造函数，因此忽略掉传入的参数值
			return c.newInstance();
	}
	
	/**
	 * 调用目标对象无参数的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target
	 * @param methodName
	 * @return
	 * @throws Exception 
	 */
	public static Object invokeMethod(Object target, String methodName) throws Exception {
		return invokeMethod(target, methodName, null, null);
	}
	
	/**
	 * 调用目标对象的方法后返回调用结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target
	 * @param methodName
	 * @param pTypes 方法中各参数对应的类型
	 * @param pValues
	 * @return
	 * @throws Exception 
	 */
	public static Object invokeMethod(Object target, String methodName,
			Class<?>[] pTypes, Object[] pValues) throws Exception {
		Method method = getMethod(target, methodName, pTypes);
		if (method == null)
			throw new NoSuchMethodException("No such invoked target method [" + StringUtils.safeString(methodName) + "]," + 
					"Please check whether invoked target and method name not be null,and ensure method are present in the current target class.");
		
		return Supports.invokeAccessibleMethod(target, method, pTypes, pValues);
	}
	
	/**
	 * 当前包下共享的静态反射工具支持类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	static class Supports {
		
		/**
		 * 设置当前对象某个被强制设置为可访问的属性值
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param object
		 * @param field
		 * @param value
		 * @throws Exception
		 */
		public static void setAccessibleFieldValue(Object object, Field field, Object value) throws Exception {
			AssertUtils.assertNotNull(field, "Target field can not be null");
			
			field.setAccessible(true);
			field.set(object, value);
		}
		
		/**
		 * 获取当前对象某个被强制设置为可访问的属性值
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param object
		 * @param field
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public static <V> V getAccessibleFieldValue(Object object, Field field) throws Exception {
			AssertUtils.assertNotNull(field, "Target field can not be null");
			
			field.setAccessible(true);
			return (V) field.get(object);
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
		public static Object invokeAccessibleMethod(Object target, Method method, Class<?>[] pTypes, Object[] pValues) throws Exception {
			method.setAccessible(true);
				// 参数类型或值列表为空时，则调用目标无参数的方法
			if (ArrayUtils.isEmpty(pTypes) || ArrayUtils.isEmpty(pValues))
				return method.invoke(target, new Object[] {});

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
	}
	
}
