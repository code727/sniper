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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 数字工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NumberUtils {
	
	/** 国际货币格式 */
	public static final String CURRENCY_FORMAT = "#,##0.00";
	
	/** 百分比格式 */
	public static final String PERCENT_FORMAT = "#%";
	
	/** 模式与格式对象关系映射集线程局部变量 */
//	private static final ThreadLocal<Map<String, DecimalFormat>> decimalFormats = new ThreadLocal<Map<String, DecimalFormat>>();
	
	/** 全局模模式与格式对象关系映射集 */
	private static final Map<String, DecimalFormat> decimalFormats = MapUtils.newConcurrentHashMap();
	
	/** 奇数映射组 */
	private static Map<Character, Integer> ODD_NUMBERS;
	
	/** 偶数映射组 */
	private static Map<Character, Integer> EVEN_NUMBERS;
	
	static {
		ODD_NUMBERS = MapUtils.newHashMap();
		EVEN_NUMBERS = MapUtils.newHashMap();
		
		ODD_NUMBERS.put('1', 1);
		ODD_NUMBERS.put('3', 3);
		ODD_NUMBERS.put('5', 5);
		ODD_NUMBERS.put('7', 7);
		ODD_NUMBERS.put('9', 9);
		
		EVEN_NUMBERS.put('0', 0);
		EVEN_NUMBERS.put('2', 2);
		EVEN_NUMBERS.put('4', 4);
		EVEN_NUMBERS.put('6', 6);
		EVEN_NUMBERS.put('8', 8);
	}
		
	/**
	 * 根据指定的模式获取已定义的数字格式对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
//	public static DecimalFormat getDecimalFormat(String pattern) {
//		Map<String, DecimalFormat> formateMap = decimalFormats.get();
//		
//		if (formateMap == null)
//			formateMap = MapUtils.newConcurrentHashMap();
//		
//		DecimalFormat decimalFormat = formateMap.get(pattern);
//		if (decimalFormat == null) {
//			decimalFormat = new DecimalFormat(pattern);
//			formateMap.put(pattern, decimalFormat);
//			decimalFormats.set(formateMap);
//		}
//		return decimalFormat;
//	}
	
	public static DecimalFormat getDecimalFormat(String pattern) {
		DecimalFormat decimalFormat = decimalFormats.get(pattern);
		if (decimalFormat == null) {
			decimalFormat = new DecimalFormat(pattern);
			decimalFormats.put(pattern, decimalFormat);
		}
		
		return decimalFormat;
	}
	
	/**
	 * 获取不为空的双精度数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static double safeDouble(Double value) {
		return safeDouble(value, 0);
	}
	
	/**
	 * 获取不为空的双精度数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static double safeDouble(Double value, double defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 获取不为空的单精度数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static float safeFloat(Float value) {
		return safeFloat(value, 0);
	}
	
	/**
	 * 获取不为空的双精度数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static float safeFloat(Float value, float defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 获取不为空的整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return 
	 */
	public static int safeInteger(Integer value) {
		return safeInteger(value, 0);
	}
	
	/**
	 * 获取不为空的整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return 
	 */
	public static int safeInteger(Integer value, int defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * 获取不为空的长整型数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static long safeLong(Long value) {
		return safeLong(value, 0);
	}
	
	/**
	 * 获取不为空的长整型数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static long safeLong(Long value, long defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * 获取不为空的短整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static short safeShort(Short value) {
		return safeShort(value, (short) 0);
	}
	
	/**
	 * 获取不为空的短整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static short safeShort(Short value, short defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * 获取不为空的比特数，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static byte safeByte(Byte value) {
		return safeByte(value, (byte) 0);
	}
	
	/**
	 * 获取不为空的比特数，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static byte safeByte(Byte value, byte defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 获取不为空的BigDecimal，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @return
	 */
	public static BigDecimal safeBigDecimal(BigDecimal decimal) {
		return safeBigDecimal(decimal, new BigDecimal(0));
	}
	
	/**
	 * 获取不为空的BigDecimal，否则返回指定不为空的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal safeBigDecimal(BigDecimal decimal, BigDecimal defaultValue) {
		if (decimal == null) {
			AssertUtils.assertNotNull(defaultValue, "Default big decimal must not be null.");
			return defaultValue;
		}
		
		return decimal;
	}
	
	/**
	 * 获取不为空的BigInteger，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param integer
	 * @return
	 */
	public static BigInteger safeBigInteger(BigInteger integer) {
		return safeBigInteger(integer, new BigInteger("0"));
	}
	
	/**
	 * 获取不为空的BigInteger，否则返回指定不为空的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param defaultValue
	 * @return
	 */
	public static BigInteger safeBigInteger(BigInteger integer, BigInteger defaultValue) {
		if (integer == null) {
			AssertUtils.assertNotNull(defaultValue, "Default big integer must not be null.");
			return defaultValue;
		}
		
		return integer;
	}
	
	/**
	 * 将数字对象转换为双精度数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static double toDouble(Object obj) {
		return toDouble(obj, 0);
	}
	
	/**
	 * 将数字对象转换为双精度数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static double toDouble(Object obj, double defaultValue) {
		return obj != null && obj instanceof Number ? ((Number)obj).doubleValue() : defaultValue;
	}
	
	/**
	 * 将数字对象转换为单精度数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static float toFloat(Object obj) {
		return toFloat(obj, 0);
	}
	
	/**
	 * 将数字对象转换为单精度数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static float toFloat(Object obj, float defaultValue) {
		return obj != null && obj instanceof Number ? ((Number)obj).floatValue() : defaultValue;
	}
	
	/** 
	 * 将数字对象转换为整数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static int toInteger(Object obj) {
		return toInteger(obj, 0);
	}
	
	/**
	 * 将数字对象转换为整数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int toInteger(Object obj, int defaultValue) {
		return obj != null && obj instanceof Number ? ((Number)obj).intValue() : defaultValue;
	}
	
	/** 
	 * 将数字对象转换为长整型数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static long toLong(Object obj) {
		return toLong(obj, 0);
	}
	
	/**
	 * 将数字对象转换为长整型数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static long toLong(Object obj, long defaultValue) {
		return obj != null && obj instanceof Number ? ((Number)obj).longValue() : defaultValue;
	}
	
	/**
	 * 将数字对象转换为短整型数值，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static short toShort(Object obj) {
		return toShort(obj, (short) 0);
	}
	
	/**
	 * 将数字对象转换为短整型数值，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static short toShort(Object obj, short defaultValue) {
		return obj != null && obj instanceof Number ? ((Number)obj).shortValue() : defaultValue;
	}
	
	/**
	 * 将数字对象转换为比特数，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static byte toByte(Object obj) {
		return toByte(obj, (byte) 0);
	}
	
	/**
	 * 将数字对象转换为比特数，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static byte toByte(Object obj, byte defaultValue) {
		return obj != null && obj instanceof Number ? ((Number)obj).byteValue() : defaultValue;
	}
	
	/**
	 * 将数字对象转换为BigDecimal，否则返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		return toBigDecimal(obj, new BigDecimal(0));
	}
	
	/**
	 * 将数字对象转换为BigDecimal，否则返回指定不为空的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj, BigDecimal defaultValue) {
		if (obj == null || !(obj instanceof Number) && !RegexUtils.isNumber(obj.toString())) {
			AssertUtils.assertNotNull(defaultValue, "Default big decimal must not be null.");
			return defaultValue;
		}
		
		return obj instanceof BigDecimal ? (BigDecimal)obj : new BigDecimal(obj.toString());
	}
		
	/**
	 * 将数字对象统一转换为BigDecimal
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static BigDecimal toBigDecimal(Number number) {
		return (number instanceof BigDecimal) ? (BigDecimal) number : new BigDecimal(number.toString());
	}
		
	/**
	 * 将数字对象转换为BigInteger，否则返回0
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static BigInteger toBigInteger(Object obj) {
		return toBigInteger(obj, new BigInteger("0"));
	}
	
	/**
	 * 将数字对象转换为BigInteger，否则返回指定不为空的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static BigInteger toBigInteger(Object obj, BigInteger defaultValue) {
		if (obj == null || !(obj instanceof Number) && !RegexUtils.isNumber(obj.toString())) {
			AssertUtils.assertNotNull(defaultValue, "Default big integer must not be null.");
			return defaultValue;
		}
		
		return obj instanceof BigInteger ? (BigInteger)obj : new BigInteger(obj.toString());
	}
		
	/**
	 * 生成指定值之内的随机双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static double randomIn(double in) {
		return (double) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static float randomIn(float in) {
		return (float) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static int randomIn(int in) {
		return (int) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机长整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static long randomIn(long in) {
		return (long) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机短整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static short randomIn(short in) {
		return (short) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @return
	 */
	public static byte randomIn(byte in) {
		return (byte) (Math.random() * in);
	}
	
	/**
	 * 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param d
	 * @param limit
	 * @return
	 */
	public static double minLimit(double d, double limit) {
		return d < limit ? limit : d;
	}
	
	/**
	 * 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param f
	 * @param limit
	 * @return
	 */
	public static float minLimit(float f, float limit) {
		return f < limit ? limit : f;
	}
	
	/**
	 * 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param i
	 * @param limit
	 * @return
	 */
	public static int minLimit(int i, int limit) {
		return i < limit ? limit : i;
	}
	
	/**
	 * 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param l
	 * @param limit
	 * @return
	 */
	public static long minLimit(long l, long limit) {
		return l < limit ? limit : l;
	}
	
	/**
	 * 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s
	 * @param limit
	 * @return
	 */
	public static short minLimit(short s, short limit) {
		return s < limit ? limit : s;
	}
	
	/**
	 * 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param b
	 * @param limit
	 * @return
	 */
	public static byte minLimit(byte b, byte limit) {
		return b < limit ? limit : b;
	}
	
	/**
	 * 求指定值的最小极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @param limit
	 * @return
	 */
	public static char minLimit(char c, char limit) {
		return c < limit ? limit : c;
	}
	
	/**
	 * 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param d
	 * @param limit
	 * @return
	 */
	public static double maxLimit(double d, double limit) {
		return d > limit ? limit : d;
	}
	
	/**
	 * 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param f
	 * @param limit
	 * @return
	 */
	public static float maxLimit(float f, float limit) {
		return f > limit ? limit : f;
	}
	
	/**
	 * 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param i
	 * @param limit
	 * @return
	 */
	public static int maxLimit(int i, int limit) {
		return i > limit ? limit : i;
	}
	
	/**
	 * 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param l
	 * @param limit
	 * @return
	 */
	public static long maxLimit(long l, long limit) {
		return l > limit ? limit : l;
	}
	
	/**
	 * 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s
	 * @param limit
	 * @return
	 */
	public static short maxLimit(short s, short limit) {
		return s > limit ? limit : s;
	}
	
	/**
	 * 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param b
	 * @param limit
	 * @return
	 */
	public static byte maxLimit(byte b, byte limit) {
		return b > limit ? limit : b;
	}
	
	/**
	 * 求指定值的最大极限值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c
	 * @param limit
	 * @return
	 */
	public static char maxLimit(char c, char limit) {
		return c > limit ? limit : c;
	}
	
	/**
	 * 判断decimal是否大于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, double number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, float number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于等于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, int number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, long number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, short number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, byte number) {
		return greaterThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean greaterThan(BigDecimal decimal, char c) {
		return greaterThan(decimal, new BigDecimal(c));
	}
	
	/**
	 * 判断decimal1是否大于decimal2
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
	 * 判断number1是否大于number2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean greaterThan(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.compareTo(decimal2) == 1;
	}
	
	/**
	 * 判断decimal是否大于等于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, double number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于等于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, float number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于等于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, int number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, long number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于等于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, short number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于等于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, byte number) {
		return greaterThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否大于等于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean greaterThanEquals(BigDecimal decimal, char c) {
		return greaterThanEquals(decimal, new BigDecimal(c));
	}
	
	/**
	 * 判断decimal1是否大于等于decimal2
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
	 * 判断number1是否大于等于number2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean greaterThanEquals(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.compareTo(decimal2) > -1;
	}
	
	
	/**
	 * 判断decimal是否小于等于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, double number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于等于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, float number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于等于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, int number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, long number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于等于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, short number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于等于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, byte number) {
		return lessThanEquals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于等于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean lessThanEquals(BigDecimal decimal, char c) {
		return lessThanEquals(decimal, new BigDecimal(c));
	}
	
	/**
	 * 判断decimal1是否小于等于decimal2
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
	 * 判断number1是否小于等于number2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean lessThanEquals(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.compareTo(decimal2) < 1;
	}
	
	/**
	 * 判断decimal是否小于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, double number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, float number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, int number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, long number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, short number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, byte number) {
		return lessThan(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否小于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean lessThan(BigDecimal decimal, char c) {
		return lessThan(decimal, new BigDecimal(c));
	}
	
	/**
	 * 判断decimal1是否小于decimal2
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
	 * 判断number1是否小于number2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean lessThan(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.compareTo(decimal2) == -1;
	}
	
	/**
	 * 判断decimal是否等于指定的双精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, double number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否等于指定的单精度浮点数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, float number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否等于指定的整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, int number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否等于指定的长整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, long number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否等于指定的短整型数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, short number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否等于指定的比特数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, byte number) {
		return equals(decimal, new BigDecimal(number));
	}
	
	/**
	 * 判断decimal是否等于指定的字符ASCII码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean equals(BigDecimal decimal, char c) {
		return equals(decimal, new BigDecimal(c));
	}
	
	/**
	 * 判断decimal1是否等于decimal2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static boolean equals(BigDecimal decimal1, BigDecimal decimal2) {
		if (decimal1 == null || decimal2 == null)
			return false;
		
		return decimal1.compareTo(decimal2) == 0;
	}
	
	/**
	 * 判断number1是否等于number2
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean equals(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.compareTo(decimal2) == 0;
	}
	
	/**
	 * 计算decimal + number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal, double number) {
		return add(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal + number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal, float number) {
		return add(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal + number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal, int number) {
		return add(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal + number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal, long number) {
		return add(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal + number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal, short number) {
		return add(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal + number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal, byte number) {
		return add(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal + number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal, char number) {
		return add(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal1 + decimal2的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal1, BigDecimal decimal2) {
		AssertUtils.assertNotNull(decimal1, "Added decimal must not be null");
		AssertUtils.assertNotNull(decimal2, "Adding decimal must not be null");
		
		return decimal1.add(decimal2);
	}
	
	/**
	 * 计算number1 + number2的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal add(Number number1, Number number2) {
		AssertUtils.assertNotNull(number1, "Added number must not be null");
		AssertUtils.assertNotNull(number2, "Adding number must not be null");
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.add(decimal2);
	}
	
	/**
	 * 计算decimal - number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal, double number) {
		return subtract(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal - number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal, float number) {
		return subtract(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal - number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal, int number) {
		return subtract(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal - number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal, long number) {
		return subtract(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal - number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal, short number) {
		return subtract(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal - number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal, byte number) {
		return subtract(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal - number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal, char number) {
		return subtract(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal1 - decimal2的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal1, BigDecimal decimal2) {
		AssertUtils.assertNotNull(decimal1, "Subtracted decimal must not be null");
		AssertUtils.assertNotNull(decimal2, "Subtracting decimal must not be null");
		
		return decimal1.subtract(decimal2);
	}
	
	/**
	 * 计算number1 - number2的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal subtract(Number number1, Number number2) {
		AssertUtils.assertNotNull(number1, "Subtracted number must not be null");
		AssertUtils.assertNotNull(number2, "Subtracting number must not be null");
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.subtract(decimal2);
	}
	
	/**
	 * 计算decimal * number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, double number) {
		return multiply(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal * number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, float number) {
		return multiply(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal * number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, long number) {
		return multiply(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal * number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, int number) {
		return multiply(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal * number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, short number) {
		return multiply(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal * number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, byte number) {
		return multiply(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal * number的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, char number) {
		return multiply(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal1 * decimal2的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal1, BigDecimal decimal2) {
		AssertUtils.assertNotNull(decimal1, "Multiplied decimal must not be null");
		AssertUtils.assertNotNull(decimal2, "Multiplting decimal must not be null");
		
		return decimal1.multiply(decimal2);
	}
	
	/**
	 * 计算number1 * number2的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal multiply(Number number1, Number number2) {
		AssertUtils.assertNotNull(number1, "Multiplied number must not be null");
		AssertUtils.assertNotNull(number2, "Multiplting number must not be null");
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.multiply(decimal2);
	}
	
	/**
	 * 计算decimal ÷ number的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, double number) {
		return divide(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal ÷ number的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, double number, int scale) {
		return divide(decimal, new BigDecimal(number), scale);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, double number, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, double number, int scale, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), scale, roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, float number) {
		return divide(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal ÷ number的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, float number, int scale) {
		return divide(decimal, new BigDecimal(number), scale);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, float number, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, float number, int scale, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), scale, roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, long number) {
		return divide(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal ÷ number的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, long number, int scale) {
		return divide(decimal, new BigDecimal(number), scale);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, long number, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, long number, int scale, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), scale, roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, int number) {
		return divide(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal ÷ number的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, int number, int scale) {
		return divide(decimal, new BigDecimal(number), scale);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, int number, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, int number, int scale, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), scale, roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, short number) {
		return divide(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal ÷ number的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, short number, int scale) {
		return divide(decimal, new BigDecimal(number), scale);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, short number, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, short number, int scale, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), scale, roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, byte number) {
		return divide(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal ÷ number的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, byte number, int scale) {
		return divide(decimal, new BigDecimal(number), scale);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, byte number, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, byte number, int scale, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), scale, roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, char number) {
		return divide(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal ÷ number的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, char number, int scale) {
		return divide(decimal, new BigDecimal(number), scale);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, char number, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), roundingMode);
	}
	
	/**
	 * 计算decimal ÷ number的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, char number, int scale, RoundingMode roundingMode) {
		return divide(decimal, new BigDecimal(number), scale, roundingMode);
	}
	
	/**
	 * 计算decimal1 ÷  decimal2的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal1, BigDecimal decimal2) {
		return divide(decimal1, decimal2, 0);
	}
	
	/**
	 * 计算decimal1 ÷  decimal2的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal1, BigDecimal decimal2, int scale) {
		return divide(decimal1, decimal2, scale, null);
	}
	
	/**
	 * 计算decimal1 ÷  decimal2的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal1, BigDecimal decimal2, RoundingMode roundingMode) {
		return divide(decimal1, decimal2, 0, roundingMode);
	}
	
	/**
	 * 计算decimal1 ÷  decimal2的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param decimal1
	 * @param decimal2
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal1, BigDecimal decimal2, int scale, RoundingMode roundingMode) {
		AssertUtils.assertNotNull(decimal1, "Divided decimal must not be null");
		AssertUtils.assertNotNull(decimal2, "Dividing decimal must not be null");
		
		return decimal1.divide(decimal2, scale, roundingMode != null ? roundingMode : RoundingMode.DOWN);
	}
	 
	/**
	 * 计算number1 ÷  number2的值，返回整数结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal divide(Number number1, Number number2) {
		return divide(number1, number2, 0);
	}
	
	/**
	 * 计算number1 ÷  number2的值，并保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(Number number1, Number number2, int scale) {
		return divide(number1, number2, scale, null);
	}
	
	/**
	 * 计算number1 ÷  number2的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(Number number1, Number number2, RoundingMode roundingMode) {
		return divide(number1, number2, 0, roundingMode);
	}
	
	/**
	 * 计算number1 ÷  number2的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number1
	 * @param number2
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(Number number1, Number number2, int scale, RoundingMode roundingMode) {
		AssertUtils.assertNotNull(number1, "Divided number must not be null");
		AssertUtils.assertNotNull(number2, "Dividing number must not be null");
		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.divide(decimal2, scale, roundingMode != null ? roundingMode : RoundingMode.DOWN);
	}
	
	/**
	 * 将字符ASCII码转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(char c, String pattern) {
		return format((long) c, pattern);
	}
	
	/**
	 * 将数字转化为指定格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(Number number, String pattern) {
		AssertUtils.assertNotNull(number, "Formated number must not be null.");
		AssertUtils.assertTrue(StringUtils.isNotBlank(pattern), "Number pattern must not be null or blank.");
		
		return getDecimalFormat(pattern).format(number);
	}
	
	/**
	 * 绝对值取反
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param a
	 * @return
	 */
	public static int unAbs(int a) {
		return (a > 0) ? -a : a;
	}
	
	/**
	 * 绝对值取反
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param a
	 * @return
	 */
	public static long unAbs(long a) {
		return (a > 0) ? -a : a;
	}
	
	/**
	 * 绝对值取反
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param a
	 * @return
	 */
	public static float unAbs(float a) {
        return (a > 0.0F) ? -a : a;
    }
	
	/**
	 * 绝对值取反
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param a
	 * @return
	 */
	public static double unAbs(double a) {
        return (a > 0.0D) ? -a : a;
    }
	
	/**
	 * 判断是否为一个奇数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static boolean isOddNumber(Number number) {
		return number != null && isOddNumber(number.toString(), false);
	}
	
	/**
	 * 判断字符串表示的数字是否为一个奇数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static boolean isOddNumber(String number) {
		return isOddNumber(number, true);
	}
	
	/**
	 * 判断是否为一个偶数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static boolean isEvenNumber(Number number) {
		return number != null && isEvenNumber(number.toString(), false);
	}
	
	/**
	 * 判断字符串表示的数字是否为一个偶数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public static boolean isEvenNumber(String number) {
		return isEvenNumber(number, true);
	}
	
	/**
	 * 判断字符串表示的数字是否为一个奇数，并且选择在判断前是否校验数字的合法性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param validation
	 * @return
	 */
	private static boolean isOddNumber(String number, boolean validation) {
		if (validation && !RegexUtils.isNumber(number))
			return false;
		
		char lastNumber = number.charAt(number.length() - 1);
		return ODD_NUMBERS.containsKey(lastNumber);
	}
	
	/**
	 * 判断字符串表示的数字是否为一个偶数，并且选择在判断前是否校验数字的合法性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param validation
	 * @return
	 */
	private static boolean isEvenNumber(String number, boolean validation) {
		if (validation && !RegexUtils.isNumber(number))
			return false;
		
		char lastNumber = number.charAt(number.length() - 1);
		return EVEN_NUMBERS.containsKey(lastNumber);
	}
		
}
