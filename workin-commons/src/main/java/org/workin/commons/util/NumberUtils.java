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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @description 数字工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NumberUtils {
	
	/** 圆角分货币格式 */
	public static final String YUANJIAOFEN_FORMAT = "0.00";
	
	/** 国际货币格式 */
	public static final String CURRENCY_FORMAT = "#,##0.00";
	
	/** 百分比格式 */
	public static final String PERCENT_FORMAT = "%";
	
	private static final ThreadLocal<Map<String, DecimalFormat>> decimalFormats = new ThreadLocal<Map<String, DecimalFormat>>();
	
	static {
		Map<String, DecimalFormat> formateMap = decimalFormats.get();
		if (formateMap == null)
			formateMap = MapUtils.newConcurrentHashMap();
		
		// 圆角分货币表示法
		formateMap.put(YUANJIAOFEN_FORMAT, new DecimalFormat(YUANJIAOFEN_FORMAT));
		// 国际货币表示法
		formateMap.put(CURRENCY_FORMAT, new DecimalFormat(CURRENCY_FORMAT));
		// 百分比表示法
		formateMap.put(PERCENT_FORMAT, new DecimalFormat(PERCENT_FORMAT));
		
		decimalFormats.set(formateMap);
	}
	
	/**
	 * @description 根据指定的模式在当前线程的局部变量中获取已定义的数字格式对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
	private static DecimalFormat getDecimalFormat(String pattern) {
		Map<String, DecimalFormat> formateMap = decimalFormats.get();
		
		DecimalFormat decimalFormat = formateMap.get(pattern);
		if (decimalFormat == null) {
			decimalFormat = new DecimalFormat(pattern);
			formateMap.put(pattern, decimalFormat);
			decimalFormats.set(formateMap);
		}
		return decimalFormat;
	}
	
	
	
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
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param d
	 * @param limit
	 * @return
	 */
	public static double minLimit(double d, double limit) {
		return d < limit ? limit : d;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param f
	 * @param limit
	 * @return
	 */
	public static float minLimit(float f, float limit) {
		return f < limit ? limit : f;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param i
	 * @param limit
	 * @return
	 */
	public static int minLimit(int i, int limit) {
		return i < limit ? limit : i;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param l
	 * @param limit
	 * @return
	 */
	public static long minLimit(long l, long limit) {
		return l < limit ? limit : l;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s
	 * @param limit
	 * @return
	 */
	public static short minLimit(short s, short limit) {
		return s < limit ? limit : s;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param b
	 * @param limit
	 * @return
	 */
	public static byte minLimit(byte b, byte limit) {
		return b < limit ? limit : b;
	}
	
	/**
	 * @description 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @param limit
	 * @return
	 */
	public static char minLimit(char c, char limit) {
		return c < limit ? limit : c;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param d
	 * @param limit
	 * @return
	 */
	public static double maxLimit(double d, double limit) {
		return d > limit ? limit : d;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param f
	 * @param limit
	 * @return
	 */
	public static float maxLimit(float f, float limit) {
		return f > limit ? limit : f;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param i
	 * @param limit
	 * @return
	 */
	public static int maxLimit(int i, int limit) {
		return i > limit ? limit : i;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param l
	 * @param limit
	 * @return
	 */
	public static long maxLimit(long l, long limit) {
		return l > limit ? limit : l;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s
	 * @param limit
	 * @return
	 */
	public static short maxLimit(short s, short limit) {
		return s > limit ? limit : s;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param b
	 * @param limit
	 * @return
	 */
	public static byte maxLimit(byte b, byte limit) {
		return b > limit ? limit : b;
	}
	
	/**
	 * @description 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @param limit
	 * @return
	 */
	public static char maxLimit(char c, char limit) {
		return c > limit ? limit : c;
	}
	
	/**
	 * @description 判断decimal是否大于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, double number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, float number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于等于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, int number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, long number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, short number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, byte number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, char c) {
		return greaterThan(decimal, new BigDecimal(c));
	}
	
	/**
	 * @description 判断decimal1是否大于decimal2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal1, BigDecimal decimal2) {
		if (decimal1 == null || decimal2 == null)
			return false;
		
		return decimal1.compareTo(decimal2) == 1;
	}
	
	/**
	 * @description 判断decimal是否大于等于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, double number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于等于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, float number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于等于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, int number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, long number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于等于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, short number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于等于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, byte number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否大于等于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, char c) {
		return greaterThanEquals(decimal, new BigDecimal(c));
	}
	
	/**
	 * @description 判断decimal1是否大于等于decimal2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal1, BigDecimal decimal2) {
		if (decimal1 == null || decimal2 == null)
			return false;
		
		return decimal1.compareTo(decimal2) > -1;
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, double number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, float number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, int number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, long number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, short number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, byte number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, char c) {
		return lessThanEquals(decimal, new BigDecimal(c));
	}
	
	/**
	 * @description 判断decimal1是否小于等于decimal2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal1, BigDecimal decimal2) {
		if (decimal1 == null || decimal2 == null)
			return false;
		
		return decimal1.compareTo(decimal2) < 1;
	}
	
	/**
	 * @description 判断decimal是否小于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, double number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, float number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, int number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, long number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, short number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, byte number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否小于等于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, char c) {
		return lessThan(decimal, new BigDecimal(c));
	}
	
	/**
	 * @description 判断decimal1是否小于等于decimal2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal1, BigDecimal decimal2) {
		if (decimal1 == null || decimal2 == null)
			return false;
		
		return decimal1.compareTo(decimal2) == -1;
	}
	
	/**
	 * @description 判断decimal是否等于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, double number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否等于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, float number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否等于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, int number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, long number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否等于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, short number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否等于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, byte number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * @description 判断decimal是否等于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, char c) {
		return equals(decimal, new BigDecimal(c));
	}
	
	/**
	 * @description 判断decimal1是否等于decimal2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static boolean equals(BigDecimal decimal1, BigDecimal decimal2) {
		if (decimal1 == null || decimal2 == null)
			return false;
		
		return decimal1.equals(decimal2);
	}
	
	/**
	 * @description 将字符ASCII码转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(char c, String pattern) {
		return format((long) c, pattern);
	}
	
	/**
	 * @description 将比特数字转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(byte number, String pattern) {
		return format((long) number, pattern);
	}
	
	/**
	 * @description 将短整型数字转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(short number, String pattern) {
		return format((long) number, pattern);
	}
	
	/**
	 * @description 将整型数字转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(int number, String pattern) {
		return format((long) number, pattern);
	}
	
	/**
	 * @description 将长整型数字转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(long number, String pattern) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(pattern), "Number pattern must not be null or blank.");
		return getDecimalFormat(pattern).format(number);
	}
	
	/**
	 * @description 将单精度浮点数字转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(float number, String pattern) {
		return format((double) number, pattern);
	}
	
	/**
	 * @description 将双精度浮点数字转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(double number, String pattern) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(pattern), "Number pattern must not be null or blank.");
		return getDecimalFormat(pattern).format(number);
	}
	
	/**
	 * @description 将BigDecimal转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(BigDecimal number, String pattern) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(pattern), "Decimal pattern must not be null or blank.");
		return getDecimalFormat(pattern).format(number);
	}
	
	/**
	 * @description 将BigInteger转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(BigInteger number, String pattern) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(pattern), "Integer pattern must not be null or blank.");
		return getDecimalFormat(pattern).format(number);
	}
	
	/**
	 * @description 将比特数字转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(byte number) {
		return format(number, CURRENCY_FORMAT);
	}
	
	/**
	 * @description 将字符ASCII码转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(char c) {
		return format(c, CURRENCY_FORMAT);
	}
	
	/**
	 * @description 将短整型数字转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(short number) {
		return format(number, CURRENCY_FORMAT);
	}
	
	/**
	 * @description 将整型数字转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(int number) {
		return format(number, CURRENCY_FORMAT);
	}
	
	/**
	 * @description 将长整型数字转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(long number) {
		return format(number, CURRENCY_FORMAT);
	}

	/**
	 * @description 将单精度浮点数字转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(float number) {
		return format(number, CURRENCY_FORMAT);
	}

	/**
	 * @description 将双精度浮点数字转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(double number) {
		return format(number, CURRENCY_FORMAT);
	}

	/**
	 * @description 将BigDecimal转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(BigDecimal number) {
		return format(number, CURRENCY_FORMAT);
	}
	
	/**
	 * @description 将BigInteger转化为国际货币格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String currency(BigInteger number) {
		return format(number, CURRENCY_FORMAT);
	}
	
	/**
	 * @description 将比特数字转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(byte number) {
		return format(number, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将字符ASCII码转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(char c) {
		return format(c, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将短整型数字转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(short number) {
		return format(number, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将整型数字转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(int number) {
		return format(number, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将长整型数字转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(long number) {
		return format(number, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将单精度浮点数字转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(float number) {
		return format(number, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将双精度浮点数字转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(double number) {
		return format(number, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将BigDecimal转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(BigDecimal number) {
		return format(number, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将BigInteger转化为"圆角分"格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static String yuanJiaoFen(BigInteger number) {
		return format(number, YUANJIAOFEN_FORMAT);
	}
	
	/**
	 * @description 将比特型的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(byte cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * @description 将字符型的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(char cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * @description 将短整型的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(short cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * @description 将整型的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(int cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * @description 将长整型的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(long cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * @description 将单精度浮点数的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(float cash) {
		return yuanToFen(yuanJiaoFen(cash), true);
	}
	
	/**
	 * @description 将双精度浮点数的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(double cash) {
		return yuanToFen(yuanJiaoFen(cash), true);
	}
	
	/**
	 * @description 将BigDecimal类型的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(BigDecimal cash) {
		cash = cash.multiply(new BigDecimal(100));
		return yuanToFen(cash.toString(), false);
	}
	
	/**
	 * @description 将BigInteger类型的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(BigInteger cash) {
		return cash.multiply(new BigInteger("100"));
	}
	
	/**
	 * @description 将字符串类型的元转换为分
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(String cash) {
		return yuanToFen(cash, new BigInteger("0"));
	}
	
	/**
	 * @description 将字符串类型的元转换为分，当字符串为空或转换出现异常时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @param defaultValue
	 * @return
	 */
	public static BigInteger yuanToFen(String cash, BigInteger defaultValue) {
		if (StringUtils.isBlank(cash))
			return defaultValue;
		
		try {
			return yuanToFen(cash, true);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	/**
	 * @description 将字符串表示的元转换为分，并且选择在转换过程中是否进行换算
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cash
	 * @param conversion 是否进行换算
	 * @return
	 */
	private static BigInteger yuanToFen(String cash, boolean conversion) {
		// 获取小数点以前的元
		String yuan = StringUtils.beforeFrist(cash, ".");
		if (conversion) {
			if (StringUtils.isEmpty(yuan))
				// 在整数元后面加两个0
				cash = cash + "00";
			else {
				// 获取数字的分
				String fen = StringUtils.afterFrist(cash, ".");
				int length = fen.length();
				if (length < 2) {
					for (int i = length; i < 2; i++)
						fen += "0";
				} else 
					// 如果小数位数大于等于2，则直接截取前两位即可
					fen = fen.substring(0, 2);
				
				cash = yuan + fen;
			}
		} else {
			// 如果不进行换算，则只需要小数点前面的元
			if (StringUtils.isNotEmpty(yuan))
				cash = yuan;
		}
		
		return new BigInteger(cash);
	}
				
}
