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
 * Create Date : 2014-9-27
 */

package org.workin.commons.util;

/**
 * @description 数字工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NumberUtils {
	
	/**
	 * @description 获取不为空的双精度数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static double safeDouble(Double value) {
		return safeDouble(value, 0);
	}
	
	/**
	 * @description 获取不为空的双精度数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static double safeDouble(Double value, double defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 获取不为空的单精度数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static float safeFloat(Float value) {
		return safeFloat(value, 0);
	}
	
	/**
	 * @description 获取不为空的双精度数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static float safeFloat(Float value, float defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 获取不为空的整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param value
	 * @param defaultValue
	 * @return 
	 */
	public static int safeInteger(Integer value) {
		return safeInteger(value, 0);
	}
	
	/**
	 * @description 获取不为空的整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param value
	 * @param defaultValue
	 * @return 
	 */
	public static int safeInteger(Integer value, int defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * @description 获取不为空的长整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static long safeLong(Long value) {
		return safeLong(value, 0);
	}
	
	/**
	 * @description 获取不为空的长整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static long safeLong(Long value, long defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * @description 获取不为空的短整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static short safeShort(Short value) {
		return safeShort(value, (short) 0);
	}
	
	/**
	 * @description 获取不为空的短整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static short safeShort(Short value, short defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * @description 获取不为空的比特数，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static byte safeByte(Byte value) {
		return safeByte(value, (byte) 0);
	}
	
	/**
	 * @description 获取不为空的比特数，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static byte safeByte(Byte value, byte defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * @description 生成指定值之内的随机双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static double randomIn(double in) {
		return (double) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static float randomIn(float in) {
		return (float) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static int randomIn(int in) {
		return (int) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机长整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static long randomIn(long in) {
		return (long) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机短整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static short randomIn(short in) {
		return (short) (Math.random() * in);
	}
	
	/**
	 * @description 生成指定值之内的随机比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static byte randomIn(byte in) {
		return (byte) (Math.random() * in);
	}
	
}
