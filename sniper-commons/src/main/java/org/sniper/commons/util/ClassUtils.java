/*
 * Copyright 2014 the original author or authors.
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
 * Create Date : 2014-11-25
 */

package org.sniper.commons.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 类型工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ClassUtils {
	
	/** 基本类型集(K:基本类型对象,V:包装类型对象)*/
	private static final Map<Class<?>, Class<?>> BASE_TYPES = MapUtils.newHashMap(8);
	
	/** 包装类型集(K:包装类型对象,V:基本类型对象) */
	private static final Map<Class<?>, Class<?>> WRAPPER_TYPES = MapUtils.newHashMap(8);
	
	static {
		
		BASE_TYPES.put(Boolean.TYPE, Boolean.class);
		BASE_TYPES.put(Byte.TYPE, Byte.class);
		BASE_TYPES.put(Character.TYPE, Character.class);
		BASE_TYPES.put(Double.TYPE, Double.class);
		BASE_TYPES.put(Float.TYPE, Float.class);
		BASE_TYPES.put(Integer.TYPE, Integer.class);
		BASE_TYPES.put(Long.TYPE, Long.class);
		BASE_TYPES.put(Short.TYPE, Short.class);
				
		WRAPPER_TYPES.put(Boolean.class, Boolean.TYPE);
		WRAPPER_TYPES.put(Byte.class, Byte.TYPE);
		WRAPPER_TYPES.put(Character.class, Character.TYPE);
		WRAPPER_TYPES.put(Double.class, Double.TYPE);
	    WRAPPER_TYPES.put(Float.class, Float.TYPE);
	    WRAPPER_TYPES.put(Integer.class, Integer.TYPE);
	    WRAPPER_TYPES.put(Long.class, Long.TYPE);
	    WRAPPER_TYPES.put(Short.class, Short.TYPE);
	    
	}
	
	private ClassUtils() {}
	
	/**
	 * 判断指定的类型对象是否为包装类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.get(clazz) != null;
    } 
	
	/**
	 * 判断指定的类型对象是否为基本类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseType(Class<?> clazz) {
		return BASE_TYPES.get(clazz) != null;
	}
	
	/**
	 * 获取指定类型的包装类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getWrapperType(Class<?> clazz) {
		if (clazz == null || isWrapperType(clazz))
			return clazz;
		
		return BASE_TYPES.get(clazz);
	}
	
	/**
	 * 获取指定类型的基本类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getBaseType(Class<?> clazz) {
		if (clazz == null || isBaseType(clazz))
			return clazz;
		
		return WRAPPER_TYPES.get(clazz);
	}
	
	/**
	 * 获取两个类型对象共同的类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz1
	 * @param clazz2
	 * @return
	 */
	public static <C1,C2> Class<?> getCommonType(Class<C1> clazz1, Class<C2> clazz2) {
		
		if (clazz1 == null || clazz2 == null)
			return Object.class;
		
		if (clazz1 == clazz2)
			return clazz1;
		
		if (clazz1.isAssignableFrom(clazz2))
			return clazz1;
		
		if (clazz2.isAssignableFrom(clazz1))
			return clazz2;
		
		Class<?> wrapperType1 = getWrapperType(clazz1);
		Class<?> wrapperType2 = getWrapperType(clazz2);
		if (wrapperType1 != null && wrapperType1 == wrapperType2)
			return wrapperType1;
		
		return Object.class;
	}
	
	/**
	 * 获取两个对象共同的类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static Class<?> getCommonType(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null)
			return Object.class;
		
		Class<?> clazz1 = obj1.getClass();
		if (clazz1.isArray())
			clazz1 = clazz1.getComponentType();
		
		Class<?> clazz2 = obj2.getClass();
		if (clazz2.isArray())
			clazz2 = clazz2.getComponentType();
		
		return getCommonType(clazz1, clazz2);
	}
	
	/**
	 * 获取集合元素共同的类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @return
	 */
	public static Class<?> getCommonType(Collection<?> collection) {
		if (CollectionUtils.isEmpty(collection))
			return null;
		
		Class<?> type = null;
		Object element;
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext()) {
			element = iterator.next();
			Class<?> elementType = (element != null ? element.getClass() : null);
			if (type != null) {
				// 元素之间类型不一致时，则返回Object类型
				if (elementType != null && type != elementType )
					return Object.class;
			} else
				type = elementType;
		}
		 
		return type;
	}
	
	/**
	 * 获取指定类型的超类签名中第1个泛型类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz) {
		return getSuperClassGenricType(clazz, 0);
	}
	
	/**
	 * 获取指定类型的超类签名中第index+1个泛型类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @param index
	 * @return
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {
		if (clazz == null)
			return Object.class;
		
        boolean isGenericType = true;
        Type type = clazz.getGenericSuperclass();
        
        Type[] params = null;
        
        if (!(type instanceof ParameterizedType))
        	isGenericType = false;
        else {
            params = ((ParameterizedType) type).getActualTypeArguments();
            if (index < 0)
            	index = 0;
            
            if (index >= params.length || !(params[index] instanceof Class))
            	isGenericType = false;
        }
        if (!isGenericType) {
            clazz = clazz.getSuperclass();
            if (clazz != Object.class)
            	/* 非Object泛型类型时，则再根据此类型的超类获取一次 */
                return getSuperClassGenricType(clazz, index);
            else
            	return clazz;
        }
        return (Class<?>) params[index];
    }
	
	/**
	 * 判断指定的类型是否为一个接口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static boolean isInterface(Class<?> clazz) {
		return clazz != null && clazz.isInterface();
	}
	
	/**
	 * 判断对象的类型是否为一个接口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static boolean isInterface(Object obj) {
		return obj != null && isInterface(obj.getClass());
	}
	
	/**
	 * 判断是否为JAVA自带类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static boolean isJavaType(Class<?> clazz) {  
	    return clazz != null && clazz.getClassLoader() == null;  
	}  
	
	/**
	 * 判断是否为JAVA自带类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static boolean isJavaTypeObject(Object obj) {  
	    return obj != null && isJavaType(obj.getClass());
	} 
	
	/**
	 * 判断是否为数组类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static boolean isArray(Class<?> clazz) {  
	    return clazz != null && clazz.isArray();
	}  
	
	/**
	 * 判断是否为数组类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static boolean isArray(Object obj) {
		return obj != null && isArray(obj.getClass());
	}
	
	/**
	 * 判断是否为集合类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static boolean isCollection(Class<?> clazz) {
		return clazz != null && Collection.class.isAssignableFrom(clazz);
	}
	
	/**
	 * 判断是否为集合类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static boolean isCollection(Object obj) {
		return obj != null && isCollection(obj.getClass());
	}
	
	/**
	 * 判断是否为列表类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static boolean isList(Class<?> clazz) {
		return clazz != null && List.class.isAssignableFrom(clazz);
	}
	
	/**
	 * 判断是否为列表类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static boolean isList(Object obj) {
		return obj != null && isList(obj.getClass());
	}
	
	/**
	 * 返回当前非Class对象的类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static Class<?> getDeclaredClass(Object object) {
		if (object == null)
			return null;
		
		return !(object instanceof Class) ? object.getClass() : (Class<?>) object;
	}
		
	/**
	 * 判断clazz1是否为clazz2的子类
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz1
	 * @param clazz2
	 * @return
	 */
	public static boolean isSubClass(Class<?> clazz1, Class<?> clazz2) {
		if (clazz1 == null || clazz2 == null || clazz1 == clazz2)
			return false;
		
		return clazz2.isAssignableFrom(clazz1);
	}
	
	/**
	 * 判断clazz1是否包含在clazz2中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz1
	 * @param clazz2
	 * @return
	 */
	public static boolean contains(Class<?> clazz1, Class<?> clazz2) {
		if (clazz1 == null || clazz2 == null)
			return false;
		
		return clazz1 == clazz2 || clazz2.isAssignableFrom(clazz1);
	}
	
	/**
	 * 获取指定类所属包的基础名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static String getPackageBaseName(Class<?> clazz) {
		Package pkg;
		if (clazz == null || (pkg = clazz.getPackage()) == null)
			return null;
		
		String packageName = pkg.getName();
		int index = packageName.lastIndexOf(".");
		
		if (index > -1) {
			/* 如果当前类所属包的路径大于一级，则最终结果为"packageName.lastPackageName" */
			String lastPackageName = packageName.substring(index);
			return new StringBuilder(packageName.substring(0, index))
					.append(lastPackageName).append(lastPackageName).toString();
		} 
		
		// 如果当前类所属包的路径只有一级，则最终结果为"packageName/packageName"
		return new StringBuilder(packageName).append(StringUtils.FORWARD_SLASH).append(packageName).toString();
	}
	
	/**
	 * 获取指定类的基础名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static String getClassBaseName(Class<?> clazz) {
		if (clazz == null)
			return null;
		
		Package pkg = clazz.getPackage();
		if (pkg == null)
			return clazz.getSimpleName();
		
		/* 将类型所属包的完整限定名替换为相对路径后，得到最终结果为"packageName/classSimpleName" */
		String pageckName = pkg.getName().replaceAll("\\.", StringUtils.FORWARD_SLASH);
		return new StringBuilder(pageckName).append(StringUtils.FORWARD_SLASH).append(clazz.getSimpleName()).toString();
	}
	
	/**
	 * 获取指定类的包名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	public static String getPackageName(Class<?> clazz) {
		Package pkg;
		if (clazz == null || (pkg = clazz.getPackage()) == null)
			return null;
		
		return pkg.getName();
	}
							
}
