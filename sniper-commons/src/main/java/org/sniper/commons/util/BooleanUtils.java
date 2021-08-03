/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-12-28
 */

package org.sniper.commons.util;

/**
 * 布尔工具类
 * @author  Daniele
 * @version 1.0
 */
public abstract class BooleanUtils {
	
	private BooleanUtils() {}
	
	/**
	 * 判断包装类布尔值是否为true
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public static boolean isTrue(Boolean value) {
		return value != null && value;
	}
	
	/**
	 * 判断包装类布尔值是否为false
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public static boolean isFalse(Boolean value) {
		return !isTrue(value);
	}
	
	/**
	 * 将布尔值转换为byte类型的数值
	 * @author Daniele
	 * @param value
	 * @return
	 */
	public static byte toByteValue(Boolean value) {
		return toByteValue(value, (byte)1, (byte)0);
	}
	
	/**
	 * 将布尔值转换为byte类型的数值
	 * @author Daniele
	 * @param value
	 * @param valueOfTrue 布尔值为true时返回的值
	 * @param falseOrNullValue 布尔值为null和false时返回的值
	 * @return
	 */
	public static byte toByteValue(Boolean value, byte trueValue, byte falseOrNullValue) {
		return isTrue(value) ? trueValue : falseOrNullValue;
	}
	
	/**
	 * 将布尔值转换为byte类型的数值
	 * @author Daniele
	 * @param value
	 * @return
	 */
	public short toShortValue(Boolean value) {
		return toShortValue(value, (short) 1, (short) 0);
	}
	
	public short toShortValue(Boolean value, short trueValue, short falseOrNullValue) {
		return isTrue(value) ? trueValue : falseOrNullValue;
	}
	
}
