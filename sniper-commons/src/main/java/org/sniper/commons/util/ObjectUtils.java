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
 * Create Date : 2014-9-29
 */

package org.sniper.commons.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 对象工具类
 * @author  Daniele
 * @version 1.0
 */
public class ObjectUtils {
	
	private ObjectUtils() {}
	
	/**
	 * 判断指定的对象是否为空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		
		if (obj instanceof CharSequence)
			return ((CharSequence)obj).length() == 0;
		
		if (obj.getClass().isArray())
			return Array.getLength(obj) == 0;
		
		if (obj instanceof Collection)
			return ((Collection<?>)obj).size() == 0;
		
		if (obj instanceof Map<?, ?>)
			return ((Map<?,?>)obj).size() == 0;
		
		return false;
	}
	
	/**
	 * 判断指定的对象是否不为空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	/**
	 * 判断对象是否为空白
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static boolean isBlank(Object obj) {
		if (obj == null)
			return true;
		
		if (obj instanceof CharSequence)
			return StringUtils.isBlank(obj.toString());
		
		if (obj.getClass().isArray())
			return Array.getLength(obj) == 0;
		
		if (obj instanceof Collection)
			return ((Collection<?>)obj).size() == 0;
		
		if (obj instanceof Map<?, ?>)
			return ((Map<?,?>)obj).size() == 0;
		
		return false;
	}
	
	/**
	 * 判断对象是否不为空白
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static boolean isNotBlank(Object obj) {
		return !isBlank(obj);
	}
	
	/**
	 * 判断两个对象是否相等
	 * @author Daniele 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null)
			return obj1 == obj2;
		
		return obj1.equals(obj2);
	}
	
	/**
	 * 判断两个对象的字符串是否相等
	 * @author Daniele 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean stringEquals(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null)
			return obj1 == obj2;
		
		return StringUtils.equals(obj1.toString(), obj2.toString());
	}
	
	/**
	 * 调用对象的toString()方法
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return obj != null ? obj.toString() : null;
	}
	
	/**
	 * 调用对象的toString()方法，若对象为Null，则返回空字符串
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static String toSafeString(Object obj) {
		return obj != null ? obj.toString() : StringUtils.EMPTY;
	}
	
	/**
	 * 调用对象的toString()方法，若对象为Null，则返回字符串"null"
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static String toSafeNullString(Object obj) {
		return obj != null ? obj.toString() : StringUtils.NULL;
	}
	
	/**
	 * 获取对象元素的个数
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static int count(Object obj) {
		if (obj == null)
			return 0;
		
		if (obj instanceof Collection<?>)
			return ((Collection<?>)obj).size();
		if (ClassUtils.isArray(obj))
			return Array.getLength(obj);
		if (obj instanceof Map<?, ?>)
			return ((Map<?, ?>)obj).size();
		
		return 1;
	}
	
	/**
	 * 获取对象的容量/长度
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static int size(Object obj) {
		if (obj == null)
			return 0;
		
		if (obj instanceof CharSequence)
			return ((CharSequence)obj).length();
		
		if (ClassUtils.isArray(obj))
			return Array.getLength(obj);
		
		if (obj instanceof Collection<?>)
			return ((Collection<?>)obj).size();
		
		if (obj instanceof Map<?, ?>)
			return ((Map<?, ?>)obj).size();
		
		return 1;
	}
	
	/**
	 * 获取对象的哈希值，如果对象为null则结果等于0，等价于空字符串的值
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static int hashCode(Object obj) {
		return obj != null ? obj.hashCode() : 0;
	}
			
}
