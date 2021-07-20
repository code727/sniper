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
 * Create Date : 2015-7-22
 */

package org.sniper.commons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.sniper.commons.kv.KeyValuePair;

/**
 * 注解工具类
 * @author  Daniele
 * @version 1.0
 */
public class AnnotationUtils {
	
	private AnnotationUtils() {}
	
	/**
	 * 判断当前对象是否已被注解。</P>
	 * 1.此方法的实现原理为：在当前对象类型的层次结构中获取注解组，直到在某一层次中获取到的注解组不为空时，表明当前对象已被注解。</P>
	 * 2.即使当前对象的父类所使用的注解没有用@Inherited标注为"可继承"时，调用此方法仍然可得出"当前对象已被父类注解所标识"的结论</P>
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static boolean annotated(Object obj) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		if (currentType == null)
			return false;
		
		do {
			if (ArrayUtils.isNotEmpty(currentType.getAnnotations()))
				return true;
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return false;
	}
	
	/**
	 * 判断当前对象是否已被指定的注解所标识。</P>
	 * 1.此方法的实现原理为：在当前对象类型的层次结构中获取指定的注解，直到在某一层次中获取到的注解不为空时，表明当前对象已被指定的注解所标识。</P>
	 * 2.即使当前对象的父类所使用的注解没有用@Inherited标注为"可继承"时，调用此方法仍然可得出"当前对象已被父类注解所标识"的结论</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> boolean annotated(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null) 
			return false;
			
		do {
			if (currentType.getAnnotation(annotationClass) != null)
				return true;
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return false;
	}
	
	/**
	 * 判断域对象是否已被注解
	 * @author Daniele 
	 * @param field
	 * @return
	 */
	public static boolean annotated(Field field) {
		return field != null && ArrayUtils.isNotEmpty(field.getAnnotations());
	}
	
	/**
	 * 判断域对象是否已被指定的注解所标识
	 * @author Daniele 
	 * @param field
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> boolean annotated(Field field, Class<A> annotationClass) {
		if (field == null || annotationClass == null) 
			return false;
			
		return field.getAnnotation(annotationClass) != null;
	}
	
	/**
	 * 判断方法对象是否已被注解
	 * @author Daniele 
	 * @param method
	 * @return
	 */
	public static boolean annotated(Method method) {
		return method != null && ArrayUtils.isNotEmpty(method.getAnnotations());
	}
	
	/**
	 * 判断方法对象是否已被指定的注解所标识
	 * @author Daniele 
	 * @param method
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> boolean annotated(Method method, Class<A> annotationClass) {
		if (method == null || annotationClass == null) 
			return false;
		
		return method.getAnnotation(annotationClass) != null;
	}
	
	/**
	 * 获取当前对象类型上被标识的注解。</P>
	 * 1.此方法的实现原理为：在当前对象类型的层次结构中获取注解，直到父类不为空或不为顶级类型(Object)为止。</P>
	 * 2.即使当前对象的父类所使用的注解没有用@Inherited标注为"可继承"时，调用此方法仍然可得到这个注解，是对原生getDeclaredAnnotations方法的扩展实现</P>
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static List<Annotation> getAnnotations(Object obj) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		if (currentType == null)
			return null;
		
		List<Annotation> result = CollectionUtils.newArrayList();
		Annotation[] annotations;
		do {
			annotations = (Annotation[]) currentType.getDeclaredAnnotations();
			if (ArrayUtils.isNotEmpty(annotations))
				result.addAll(Arrays.asList(annotations));
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return result;
	}
	
	/**
	 * 获取当前对象类型上被指定标识的注解。</P>
	 * 1.此方法的实现原理为：在当前对象类型的层次结构中获取注解，直到获取到指定标识、父类不为空或不为顶级类型(Object)为止。</P>
	 * 2.即使当前对象的父类所使用的注解没有用@Inherited标注为"可继承"时，调用此方法仍然可得到这个注解，是对原生getAnnotation方法的扩展实现</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> Annotation getAnnotation(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		do {
			A annotation = currentType.getAnnotation(annotationClass);
			if (annotation != null)
				return annotation;
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return null;
	}
	
	/**
	 * 获取对象内被注解的所有域
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static List<Field> getAnnotatedFields(Object obj) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		if (currentType == null)
			return null;
		
		List<Field> fields = CollectionUtils.newArrayList();
		Field[] declaredFields;
		do {
			declaredFields = currentType.getDeclaredFields();
			if (ArrayUtils.isNotEmpty(declaredFields)) {
				for (Field field : declaredFields) {
					if (ArrayUtils.isNotEmpty(field.getAnnotations())) 
						fields.add(field);
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return fields;
	}
	
	/**
	 * 获取对象内被指定注解标识的所有域
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> List<Field> getAnnotatedFields(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		List<Field> fields = CollectionUtils.newArrayList();
		Field[] declaredFields;
		do {
			declaredFields = currentType.getDeclaredFields();
			if (ArrayUtils.isNotEmpty(declaredFields)) {
				for (Field field : declaredFields) {
					if (field.getAnnotation(annotationClass) != null) 
						fields.add(field);
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return fields;
	}
			
	/**
	 * 获取对象内被指定注解标识的域</P>
	 * 注意：由于内部实现使用的getDeclaredFields方法并不一定是按照声明的顺序返回的。
	 * 当同一个注解作用在当前对象的多个域上时，此方法多次执行后并不能保证每次返回的都是同一个域对象</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 */
	public static <A extends Annotation> Field getAnnotatedField(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		Field[] declaredFields;
		do {
			declaredFields = currentType.getDeclaredFields();
			if (ArrayUtils.isNotEmpty(declaredFields)) {
				for (Field field : declaredFields) {
					/* 获取到第一个被指定注解标识的域对象后立即返回结果 */
					if (field.getAnnotation(annotationClass) != null) 
						return field;
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return null;
	}
	
	/**
	 * 获取对象内被指定注解标识的域名称和相应的值</P>
	 * 注意：由于内部实现使用的getAnnotatedField方法结果的不确定性，
	 * 当同一个annotation作用在当前对象的多个域上时，此方法并不能保证每次获取到的值都来自于同一个目标域</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 * @throws Exception 
	 */
	public static <A extends Annotation, V> KeyValuePair<String, V> getAnnotatedFieldValue(Object obj, Class<A> annotationClass) throws Exception {
		Field annotatedField = getAnnotatedField(obj, annotationClass);
		if (annotatedField == null)
			throw new NoSuchFieldException(buildNoSuchAnnotatedFieldExceptionMessage(obj, annotationClass));
		
		V value = ReflectionUtils.getAccessibleFieldValue(obj, annotatedField);
		return new KeyValuePair<String, V>(annotatedField.getName(), value);
	}
	
	/**
	 * 设置对象内被指定注解标识的域值后返回被设置的域对象</P>
	 * 注意：由于内部实现使用的getAnnotatedField方法结果的不确定性，
	 * 当同一个annotation作用在当前对象的多个域上时，此方法并不能保证每次被设置的域都是同一个</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static <A extends Annotation, V> Field setAnnotatedFieldValue(Object obj, Class<A> annotationClass, V value) throws Exception {
		Field annotatedField = getAnnotatedField(obj, annotationClass);
		if (annotatedField == null)
			throw new NoSuchFieldException(buildNoSuchAnnotatedFieldExceptionMessage(obj, annotationClass));
		
		ReflectionUtils.setAccessibleFieldValue(obj, annotatedField, value);
		return annotatedField;
	}
	
	/**
	 * 获取对象内被注解标识的所有方法
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static List<Method> getAnnotatedMethods(Object obj) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		if (currentType == null)
			return null;
		
		List<Method> methods = CollectionUtils.newArrayList();
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method method : declaredMethods) {
					if (ArrayUtils.isNotEmpty(method.getAnnotations()))
						methods.add(method);
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return methods;
	}
			
	/**
	 * 获取对象内被指定注解标识的所有方法
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> List<Method> getAnnotatedMethods(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		List<Method> methods = CollectionUtils.newArrayList();
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method method : declaredMethods) {
					if (method.getAnnotation(annotationClass) != null)
						methods.add(method);
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return methods;
	}
	
	/**
	 * 获取对象内被指定注解标识的方法</P>
	 * 注意：由于内部实现使用的getDeclaredMethods方法并不一定是按照声明的顺序返回的。
	 * 当同一个注解作用在当前对象的多个方法上时，此方法多次执行后并不能保证每次返回的都是同一个方法对象</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> Method getAnnotatedMethod(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method method : declaredMethods) {
					/* 获取到第一个被指定注解标识的方法对象后立即返回结果 */
					if (method.getAnnotation(annotationClass) != null)
						return method;
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return null;
	}
	
	/**
	 * 查找对象内被指定注解标识的所有getter方法
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> List<Method> findAnnotatedGetters(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		List<Method> methods = CollectionUtils.newArrayList();
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method method : declaredMethods) {
					if (annotatedGetter(method, annotationClass))
						methods.add(method);
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return methods;
	}
	
	/**
	 * 查找对象内被指定注解标识的getter方法</P>
	 * 注意：由于内部实现使用的getDeclaredMethods方法并不一定是按照声明的顺序返回的。
	 * 当同一个注解作用在当前对象的多个方法上时，此方法多次执行后并不能保证每次返回的都是同一个方法对象</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> Method findAnnotatedGetter(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method method : declaredMethods) {
					if (annotatedGetter(method, annotationClass))
						return method;
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return null;
	}
		
	/**
	 * 查找对象内被指定注解标识的所有setter方法
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> List<Method> findAnnotatedSetters(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		List<Method> methods = CollectionUtils.newArrayList();
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method method : declaredMethods) {
					if (annotatedSetter(method, annotationClass))
						methods.add(method);
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return methods;
	}
	
	/**
	 * 查找对象内被指定注解标识的setter方法</P>
	 * 注意：由于内部实现使用的getDeclaredMethods方法并不一定是按照声明的顺序返回的。
	 * 当同一个注解作用在当前对象的多个方法上时，此方法多次执行后并不能保证每次返回的都是同一个方法对象</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	public static <A extends Annotation> Method findAnnotatedSetter(Object obj, Class<A> annotationClass) {
		Class<?> currentType;
		if (annotationClass == null || (currentType = ClassUtils.getCurrentType(obj)) == null)
			return null;
		
		Method[] declaredMethods;
		do {
			declaredMethods = currentType.getDeclaredMethods();
			if (ArrayUtils.isNotEmpty(declaredMethods)) {
				for (Method method : declaredMethods) {
					if (annotatedSetter(method, annotationClass))
						return method;
				}
			}
			
			currentType = currentType.getSuperclass();
		} while (ClassUtils.isNotTopType(currentType));
		
		return null;
	}
		
	/**
	 * 调用对象内被指定注解标识的无参方法后返回被调方法名称和执行结果</P>
	 * 注意：由于内部实现使用的getAnnotatedMethod方法结果的不确定性，
	 * 当同一个annotation作用在当前对象的多个方法上时，此方法并不能保证每次都调用的是同一个目标方法。</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 * @throws Exception 
	 */
	public static <A extends Annotation, V> KeyValuePair<String, V> invokeAnnotatedMethod(Object obj, Class<A> annotationClass) throws Exception {
		return invokeAnnotatedMethod(obj, annotationClass, null);
	}
	
	/**
	 * 调用对象内被指定注解标识的方法后返回被调方法名称和执行结果</P>
	 * 注意：由于内部实现使用的getAnnotatedMethod方法结果的不确定性，
	 * 当同一个annotation作用在当前对象的多个方法上时，此方法并不能保证每次都调用的是同一个目标方法</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @param pValues
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <A extends Annotation, V> KeyValuePair<String, V> invokeAnnotatedMethod(Object obj, Class<A> annotationClass, Object[] pValues) throws Exception {
		Method annotatedMethod = getAnnotatedMethod(obj, annotationClass);
		if (annotatedMethod == null)
			throw new NoSuchMethodException(buildNoSuchAnnotatedMethodExceptionMessage(obj, annotationClass));
		
		V value = (V) ReflectionUtils.invokeAccessibleMethod(obj, 
				annotatedMethod, annotatedMethod.getParameterTypes(), pValues);
		return new KeyValuePair<String, V>(annotatedMethod.getName(), value);
	}
	
	/**
	 * 调用对象内被指定注解标识的getter方法后返回被调方法名称和执行结果</P>
	 * 注意：由于内部实现使用的findAnnotatedGetter方法结果的不确定性，
	 * 当同一个annotation作用在当前对象的多个Getter方法上时，此方法并不能保证每次都调用的是同一个目标方法。</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <A extends Annotation, V> KeyValuePair<String, V> invokeAnnotatedGetter(Object obj, Class<A> annotationClass) throws Exception {
		Method annotatedGetter = findAnnotatedGetter(obj, annotationClass);
		if (annotatedGetter == null)
			throw new NoSuchMethodException(buildNoSuchAnnotatedMethodExceptionMessage(obj, annotationClass));
		
		V value = (V) ReflectionUtils.invokeAccessibleMethod(obj, annotatedGetter, null, null);
		return new KeyValuePair<String, V>(annotatedGetter.getName(), value);
	}
	
	/**
	 * 调用对象内被指定注解标识的setter方法返回被调方法对象</P>
	 * 注意：由于内部实现使用的findAnnotatedSetter方法结果的不确定性，
	 * 当同一个annotation作用在当前对象的多个Setter方法上时，此方法并不能保证每次都调用的是同一个目标方法。</P>
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @param pValue
	 * @throws Exception 
	 */
	public static <A extends Annotation, V> Method invokeAnnotatedSetter(Object obj, Class<A> annotationClass, V pValue) throws Exception {
		Method annotatedSetter = findAnnotatedSetter(obj, annotationClass);
		if (annotatedSetter == null)
			throw new NoSuchMethodException(buildNoSuchAnnotatedMethodExceptionMessage(obj, annotationClass));
		
		ReflectionUtils.invokeAccessibleMethod(obj, annotatedSetter, annotatedSetter.getParameterTypes(), new Object[]{pValue});
		return annotatedSetter;
	}
	
	/**
	 * 判断是否为一个被指定注解标识的getter方法对象
	 * @author Daniele 
	 * @param method
	 * @param annotationClass
	 * @return
	 */
	private static <A extends Annotation> boolean annotatedGetter(Method method, Class<A> annotationClass) {
		return ReflectionUtils.isGetter(method) && method.getAnnotation(annotationClass) != null;
	}
	
	/**
	 * 判断是否为一个被指定注解标识的setter方法对象
	 * @author Daniele 
	 * @param method
	 * @param annotationClass
	 * @return
	 */
	private static <A extends Annotation> boolean annotatedSetter(Method method, Class<A> annotationClass) {
		return ReflectionUtils.isSetter(method) && method.getAnnotation(annotationClass) != null;
	}
	
	/**
	 * 构建目标方法没有被注解的异常消息
	 * @author Daniele
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	private static <A extends Annotation> String buildNoSuchAnnotatedMethodExceptionMessage(Object obj, Class<A> annotationClass) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		return String.format("{\"currentType\":%s,\"annotationClass\":%s}", 
				(currentType != null ? StringUtils.appendDoubleQuotes(currentType.getName()) : currentType),
				(annotationClass != null ? StringUtils.appendDoubleQuotes(annotationClass.getName()) : annotationClass));
	}
	
	/**
	 * 构建目标属性域没有被注解的异常消息
	 * @author Daniele 
	 * @param obj
	 * @param annotationClass
	 * @return
	 */
	private static <A extends Annotation> String buildNoSuchAnnotatedFieldExceptionMessage(Object obj, Class<A> annotationClass) {
		Class<?> currentType = ClassUtils.getCurrentType(obj);
		return String.format("{\"currentType\":%s,\"annotationClass\":%s}", 
				(currentType != null ? StringUtils.appendDoubleQuotes(currentType.getName()) : currentType),
				(annotationClass != null ? StringUtils.appendDoubleQuotes(annotationClass.getName()) : annotationClass));
	}
	
}
