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

package org.workin.commons.util;

/**
 * @description 断言工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0.0
 */
public class AssertUtils {
	
	/**
	 * @description 断言对象为空
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param obj 
	 */
	public static void assertNull(Object obj) {
		assertNull(obj, "[Assertion failed] - This object argument must be null.");
	}
	
	/**
	 * @description 断言对象为空，否则抛出自定义异常信息
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param obj
	 * @param message 
	 */
	public static void assertNull(Object obj, String message) {
		if (obj != null)
			throw new IllegalArgumentException(message);
	}
	
	/**
	 * @description 断言对象不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param obj 
	 */
	public static void assertNotNull(Object obj) {
		assertNotNull(obj, "[Assertion failed] - This object argument object must not be null.");
	}

	/**
	 * @description 断言对象不为空，否则抛出自定义异常信息
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param obj
	 * @param message 
	 */
	public static void assertNotNull(Object obj, String message) {
		if (obj == null)
			throw new IllegalArgumentException(message);
	}
	
	/**
	 * @description 断言表达式为true
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param express 
	 */
	public static void assertTrue(boolean express) {
		assertTrue(express, "[Assertion failed] - This expression must be true.");
	}
	
	/**
	 * @description 断言表达式为true,否则抛出自定义异常信息
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param express
	 * @param message 
	 */
	public static void assertTrue(boolean express, String message) {
		if (!express)
			throw new IllegalArgumentException(message);
	}
	
	/**
	 * @description 断言表达式为false
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param express 
	 */
	public static void assertFalse(boolean express) {
		assertFalse(express, "[Assertion failed] - This expression must be false.");
	}
	
	/**
	 * @description 断言表达式为false，否则抛出自定义异常信息
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param express
	 * @param message 
	 */
	public static void assertFalse(boolean express, String message) {
		if (express)
			throw new IllegalArgumentException(message);
	}
	
	/**
	 * @description 断言不为空字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param str 
	 */
	public static void assertNotEmpty(String str) {
		assertNotEmpty(str, "[Assertion failed] - This string argument must not be null or empty.");
	}
	
	/**
	 * @description 断言不为空字符串，否则抛出自定义的异常信息
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param str
	 * @param message 
	 */
	public static void assertNotEmpty(String str, String message) {
		assertTrue(StringUtils.isNotEmpty(str), message);
	}
	
	/**
	 * @description 断言不全为空白字符串，否则抛出自定义的异常信息
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param str
	 * @param message 
	 */
	public static void assertNotBlank(String str, String message) {
		assertTrue(StringUtils.isNotBlank(str), message);
	}

}
