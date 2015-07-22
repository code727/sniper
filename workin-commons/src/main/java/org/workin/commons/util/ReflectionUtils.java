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

package org.workin.commons.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @description 反射工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ReflectionUtils {
	
	/**
	 * @description 获取当前对象所在类以及非Object基类中定义的所有方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Method> getDeclaredMethods(Object object) {
		Class<?> declaredClass = ClassUtils.getDeclaredClass(object);
		if (declaredClass != null) {
			List<Method> method = CollectionUtils.newArrayList();
			while (declaredClass != Object.class) {
				// 如果当前对象所属的类不是顶层基类(Object),则获取此类以及基类中的所有方法
				method.addAll(Arrays.asList(declaredClass.getDeclaredMethods()));
				declaredClass = declaredClass.getSuperclass();
			}
			return method;
		}
		return null;
	}
	
	/**
	 * @description 获取当前对象所在类以及非Object基类中定义的所有方法的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<String> getDeclaredMethodNames(Object object){
		List<Method> methods = getDeclaredMethods(object);
		if (CollectionUtils.isEmpty(methods))
			return null;
		
		List<String> names = CollectionUtils.newArrayList();
		for (Method method : methods) 
			names.add(method.getName());
		return names;
	}
	
	/**
	 * @description 获取当前对象所在类或非Object基类中所指定的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName 方法名称
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] pTypes) {
		if (StringUtils.isBlank(methodName))
			return null;
		
		Class<?> declaredClass = ClassUtils.getDeclaredClass(object);
		if (declaredClass != null) {
			methodName = methodName.trim();
			while (declaredClass != Object.class) {
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
	 * @description 判断指定的方法是否存在于当前对象所在类或非Object基类中
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
	 * @description 获取当前对象所在类中所指定的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param methodName
	 * @param pTypes 方法中各参数对应的类型
	 * @return
	 */
	public static Method getMethod(Object object, String methodName, Class<?>[] pTypes) {
		if (StringUtils.isBlank(methodName))
			return null;
		
		Class<?> declaredClass = ClassUtils.getDeclaredClass(object);
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
	 * @description 判断指定的方法是否存在于当前对象中
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
	 * @description 获取当前对象所在类以及非Object基类中定义的所有构造函数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Constructor<?>> getDeclaredConstructors(Object object) {
		Class<?> declaredClass = ClassUtils.getDeclaredClass(object);
		if (declaredClass != null) {
			List<Constructor<?>> constructors = CollectionUtils.newArrayList();
			while (declaredClass != Object.class) {
				// 如果当前对象所属的类不是顶级基类(Object),则获取此类以及基类中的所有方法
				constructors.addAll(Arrays.asList(declaredClass.getDeclaredConstructors()));
				declaredClass = declaredClass.getSuperclass();
			}
			return constructors;
		}
		return null;
	}
	
	/**
	 * @description 获取当前对象所在类以及非Object基类中定义的所有构造函数名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static Set<String> getDeclaredConstructorNames(Object object) {
		List<Constructor<?>> constructors = getDeclaredConstructors(object);
		if (CollectionUtils.isEmpty(constructors))
			return null;
		
		Set<String> names = CollectionUtils.newHashSet();
		for (Constructor<?> constructor : constructors) 
			names.add(constructor.getName());
		return names;
	}
	
	/**
	 * @description 获取当前对象所在类或非Object基类中所指定的构造函数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static Constructor<?> getDeclaredConstructor(Object object, Class<?>[] pTypes) {
		Class<?> declaredClass = ClassUtils.getDeclaredClass(object);
		if (declaredClass != null) {
			while (declaredClass != Object.class) {
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
	 * @description 判断指定的构造函数是否存在于当前对象所在类或非Object基类中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static boolean isDeclaredConstructor(Object object, Class<?>[] pTypes) {
		return getDeclaredConstructor(object, pTypes) != null;
	}
	
	/**
	 * @description 获取当前对象所在类中所指定的构造函数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static Constructor<?> getConstructor(Object object, Class<?>[] pTypes) {
		Class<?> declaredClass = ClassUtils.getDeclaredClass(object);
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
	 * @description 判断指定的构造函数是否存在于当前对象中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pTypes 构造函数中各参数对应的类型
	 * @return
	 */
	public static boolean isConstructor(Object object, Class<?>[] pTypes) {
		return getConstructor(object, pTypes) != null;
	}
	
	/**
	 * @description 获取当前对象所在类以及非Object基类中定义的所有属性对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<Field> getDeclaredFields(Object object) {
		Class<?> declaredClass = ClassUtils.getDeclaredClass(object);
		if (declaredClass != null) {
			List<Field> fields = CollectionUtils.newArrayList();
			while (declaredClass != Object.class) {
				fields.addAll(Arrays.asList(declaredClass.getDeclaredFields()));
				declaredClass = declaredClass.getSuperclass();
			}
			return fields;
		}
		return null;
	}
	
	/**
	 * @description 获取当前对象所在类以及非Object基类中定义的所有属性名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static List<String> getDeclaredFieldNames(Object object) {
		List<Field> fields = getDeclaredFields(object);
		if (CollectionUtils.isEmpty(fields))
			return null;
		
		List<String> fieldName = CollectionUtils.newArrayList();
		for (Field field : fields)
			fieldName.add(field.getName());
				
		return fieldName;
	}
	
	/**
	 * @description 获取当前对象所在类或非Object基类中所指定的属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(Object object, String fieldName) {
		if (StringUtils.isBlank(fieldName))
			return null;
		
		Class<?> declaredClass = ClassUtils.getDeclaredClass(object);
		if (declaredClass != null) {
			fieldName = fieldName.trim();
			while (declaredClass != Object.class) {
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
	 * @description 判断指定的属性是否存在于当前对象所在类或非Object基类中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static boolean isDeclaredField(Object object, String fieldName){
		return getDeclaredField(object, fieldName) != null;
	}
	
	/**
	 * @description 获取当前对象所在类中所指定的属性
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
	 * @description 判断指定的属性是否存在于当前对象中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static boolean isField(Object object, String fieldName) {
		return getField(object, fieldName) != null;
	}
	
	/**
	 * @description 获取当前对象某个属性的类型
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
	 * @description 获取当前对象某个属性的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static <V> V getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
		Field field = getField(object, fieldName);
		if (field == null)
			throw new NoSuchFieldException("No such target field [" + StringUtils.safeString(fieldName) + "]," + 
				"Please check whether invoked target and field name not be null,and ensure field are present in the current target class.");
		
		return getAccessibleFieldValue(object, field);
	}
	
	/**
	 * @description 获取当前对象某个属性的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param field
	 * @return
	 */
	public static <V> V getFieldValue(Object object, Field field) {
		AssertUtils.assertNotNull(object, "Target object can not be null.");
		return getAccessibleFieldValue(object, field);
	}
	
	/**
	 * @description 获取当前对象某个被强制设置为可访问的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <V> V getAccessibleFieldValue(Object object, Field field) {
		AssertUtils.assertNotNull(field, "Target field can not be null.");
		
		field.setAccessible(true);
		try {
			return (V) field.get(object);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	/**
	 * @description 设置当前对象的某个属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target
	 * @param fieldName
	 * @param value
	 * @throws NoSuchFieldException 
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException {
		Field field = getField(object, fieldName);
		if (field == null)
			throw new NoSuchFieldException("No such target field [" + StringUtils.safeString(fieldName) + "]," + 
				"Please check whether invoked target and field name not be null,and ensure field are present in the current target class.");
		
		setAccessibleFieldValue(object, field, value);
	}
	
	/** 
	 * @description 批量设置当前对象的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param fieldValues
	 * @throws NoSuchFieldException 
	 */
	public static void setFieldValue(Object object, Map<String, Object> fieldValues) throws NoSuchFieldException {
		AssertUtils.assertTrue(MapUtils.isNotEmpty(fieldValues), "Field values map can not be empty");
		
		if (object != null && MapUtils.isNotEmpty(fieldValues)) {
			Set<Entry<String, Object>> fieldValueSet = fieldValues.entrySet();
			for (Entry<String, Object> fieldValue : fieldValueSet) 
				setFieldValue(object, fieldValue.getKey(), fieldValue.getValue());
		}
	}
	
	/**
	 * @description 设置当前对象的某个属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param field
	 * @param value
	 */
	public static void setFieldValue(Object object, Field field, Object value) {
		AssertUtils.assertNotNull(object, "Target object can not be null.");
		setAccessibleFieldValue(object, field, value);
	}
	
	/**
	 * @description 设置当前对象某个被强制设置为可访问的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param field
	 * @param value
	 */
	private static void setAccessibleFieldValue(Object object, Field field, Object value) {
		AssertUtils.assertNotNull(field, "Target field can not be null.");
		
		field.setAccessible(true);
		try {
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @description 根据调用全限定名对应类的默认构造函数创建实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @return
	 */
	public static <T> T newInstance(String className) {
		return newInstance(className, null, null);
	}
	
	/**
	 * @description 调用全限定名对应类的构造函数创建实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @param pTypes
	 * @param pValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String className, Class<?>[] pTypes, Object[] pValue) {
		try {
			return (T) newInstance(Class.forName(className), pTypes, pValue);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description 调用指定类的默认构造函数生成一个实例对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @return
	 */
	public static <T> T newInstance(Class<T> c) {
		return newInstance(c, null, null);
	}
	
	/**
	 * @description 调用指定类的构造函数生成一个该类的实例对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @param pTypes 构造函数中各参数对应的类型
	 * @param pValue 构造函数中对应的参数值
	 * @return
	 */
	public static <T> T newInstance(Class<T> c, Class<?>[] pTypes, Object[] pValue) {
		AssertUtils.assertNotNull(c, "Can not be create instance by null class.");
		
		try {
			if (ArrayUtils.isNotEmpty(pTypes)) {
				Constructor<T> constructor = c.getDeclaredConstructor(pTypes);
				constructor.setAccessible(true);
				return constructor.newInstance(pValue);
			} else
				// 参数类型为空时，则调用目标无参数的构造函数，因此忽略掉传入的参数值
				return c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * @description 调用目标对象无参数的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException 
	 */
	public static Object invokeMethod(Object target, String methodName) throws NoSuchMethodException {
		return invokeMethod(target, methodName, null, null);
	}
	
	/**
	 * @description 调用目标对象的方法后返回调用结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param target
	 * @param methodName
	 * @param pTypes 方法中各参数对应的类型
	 * @param pValue
	 * @return
	 * @throws NoSuchMethodException 
	 */
	public static Object invokeMethod(Object target, String methodName,
			Class<?>[] pTypes, Object[] pValue) throws NoSuchMethodException {
		Method method = getMethod(target, methodName, pTypes);
		if (method == null)
			throw new NoSuchMethodException("No such invoked target method [" + StringUtils.safeString(methodName) + "]," + 
					"Please check whether invoked target and method name not be null,and ensure method are present in the current target class.");
		try {
			method.setAccessible(true);
			// 参数类型为空时，则调用目标无参数的方法，因此忽略掉传入的参数值
			if (ArrayUtils.isEmpty(pTypes)) 
				return method.invoke(target, new Object[] {});
			
			return method.invoke(target, pValue);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
			
}
