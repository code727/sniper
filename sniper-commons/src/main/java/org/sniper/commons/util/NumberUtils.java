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

package org.sniper.commons.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 数字工具类
 * @author  Daniele
 * @version 1.0
 */
public abstract class NumberUtils {
		
	/** 百分比格式 */
	public static final String PERCENT_FORMAT = "#%";
				
	/** 全局模式与格式对象关系映射集 */
	private static final Map<String, DecimalFormat> PATTERN_DECIMAL_FORMATS = MapUtils.newConcurrentHashMap();
	
	/** 全局长度与格式对象关系映射集 */
	private static final Map<Integer, DecimalFormat> LENGTH_DECIMAL_FORMATS = MapUtils.newConcurrentHashMap();
		
	/**
	 * 根据指定的模式获取已定义的数字格式对象
	 * @author Daniele 
	 * @param pattern
	 * @return
	 */
	private static DecimalFormat getDecimalFormat(String pattern) {
		DecimalFormat decimalFormat = PATTERN_DECIMAL_FORMATS.get(pattern);
		
		if (decimalFormat == null) {
			synchronized (PATTERN_DECIMAL_FORMATS) {
				if ((decimalFormat = PATTERN_DECIMAL_FORMATS.get(pattern)) == null) {
					decimalFormat = new DecimalFormat(pattern);
					PATTERN_DECIMAL_FORMATS.put(pattern, decimalFormat);
				}
			}
		}
		return decimalFormat;
	}
	
	/**
	 * 获取指定长度的数字格式化对象
	 * @author Daniele 
	 * @param pattern
	 * @return
	 */
	private static DecimalFormat getDecimalFormat(int length) {
		if (length < 1)
			return null;
		
		DecimalFormat decimalFormat = LENGTH_DECIMAL_FORMATS.get(length);
		if (decimalFormat == null) {
			synchronized (LENGTH_DECIMAL_FORMATS) {
				if ((decimalFormat = LENGTH_DECIMAL_FORMATS.get(length)) == null) {
					decimalFormat = new DecimalFormat(StringUtils.leftSupplement(StringUtils.EMPTY, '0', length));
					LENGTH_DECIMAL_FORMATS.put(length, decimalFormat);
				}
			}
		}
		
		return decimalFormat;
	}
	
	
	/**
	 * 获取不为空的双精度数值，否则返回0
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public static double safeDouble(Double value) {
		return safeDouble(value, 0d);
	}
	
	/**
	 * 获取不为空的双精度数值，否则返回指定的默认值
	 * @author Daniele 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static double safeDouble(Double value, double defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 获取不为空的单精度数值，否则返回0
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public static float safeFloat(Float value) {
		return safeFloat(value, 0f);
	}
	
	/**
	 * 获取不为空的双精度数值，否则返回指定的默认值
	 * @author Daniele 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static float safeFloat(Float value, float defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 获取不为空的整数值，否则返回0
	 * @author Daniele 
	 * @param value
	 * @return 
	 */
	public static int safeInteger(Integer value) {
		return safeInteger(value, 0);
	}
	
	/**
	 * 获取不为空的整数值，否则返回指定的默认值
	 * @author Daniele 
	 * @param value
	 * @param defaultValue
	 * @return 
	 */
	public static int safeInteger(Integer value, int defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * 获取不为空的长整型数值，否则返回0
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public static long safeLong(Long value) {
		return safeLong(value, 0L);
	}
	
	/**
	 * 获取不为空的长整型数值，否则返回指定的默认值
	 * @author Daniele 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static long safeLong(Long value, long defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * 获取不为空的短整数值，否则返回0
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public static short safeShort(Short value) {
		return safeShort(value, (short) 0);
	}
	
	/**
	 * 获取不为空的短整数值，否则返回指定的默认值
	 * @author Daniele 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static short safeShort(Short value, short defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/** 
	 * 获取不为空的比特数，否则返回0
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public static byte safeByte(Byte value) {
		return safeByte(value, (byte) 0);
	}
	
	/**
	 * 获取不为空的比特数，否则返回指定的默认值
	 * @author Daniele 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static byte safeByte(Byte value, byte defaultValue) {
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 获取不为空的BigDecimal，否则返回0
	 * @author Daniele 
	 * @param decimal
	 * @return
	 */
	public static BigDecimal safeBigDecimal(BigDecimal decimal) {
		return safeBigDecimal(decimal, new BigDecimal(0));
	}
	
	/**
	 * 返回不为空BigDecimal
	 * @author Daniele 
	 * @param decimal
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal safeBigDecimal(BigDecimal decimal, BigDecimal defaultValue) {
		AssertUtils.assertNotNull(defaultValue, "Default big decimal must not be null");
		
		return decimal != null ? decimal : defaultValue;
	}
	
	/**
	 * 获取不为空的BigInteger，否则返回0
	 * @author Daniele 
	 * @param integer
	 * @return
	 */
	public static BigInteger safeBigInteger(BigInteger integer) {
		return safeBigInteger(integer, new BigInteger("0"));
	}
	
	/**
	 * 获取不为空的BigInteger，否则返回指定不为空的默认值
	 * @author Daniele 
	 * @param decimal
	 * @param defaultValue
	 * @return
	 */
	public static BigInteger safeBigInteger(BigInteger integer, BigInteger defaultValue) {
		AssertUtils.assertNotNull(defaultValue, "Default big integer must not be null");
		
		return integer != null ? integer : defaultValue;
	}
	
	/**
	 * 将对象转换为双精度数值，当对象为空或转换失败时返回空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static Double toDouble(Object obj) {
		return toDouble(obj, null);
	}
	
	/**
	 * 将对象转换为双精度数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static Double toDouble(Object obj, Double defaultValue) {
		if (obj == null)
			return defaultValue;
		
		if (obj instanceof Number)
			return ((Number) obj).doubleValue();
			
		try {
			return Double.parseDouble(obj.toString());
		} catch (NumberFormatException e) {
			return defaultValue;
	    }
	}
	
	/**
	 * 将对象转换为双精度数值，当对象为空或转换失败时返回0
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static double toDoubleValue(Object obj) {
		return toDoubleValue(obj, 0);
	}
	
	/**
	 * 将对象转换为双精度数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static double toDoubleValue(Object obj, double defaultValue) {
		return toDouble(obj, defaultValue);
	}
	
	/**
	 * 将对象转换为单精度数值，当对象为空或转换失败时返回空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static Float toFloat(Object obj) {
		return toFloat(obj, null);
	}
	
	/**
	 * 将对象转换为单精度数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static Float toFloat(Object obj, Float defaultValue) {
		if (obj == null)
			return defaultValue;
		
		if (obj instanceof Number)
			return ((Number) obj).floatValue();
		
		try {
			return Float.parseFloat(obj.toString());
		} catch (NumberFormatException e) {
			return defaultValue;
	    }
	}
	
	/**
	 * 将对象转换为单精度数值，当对象为空或转换失败时返回0
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static float toFloatValue(Object obj) {
		return toFloatValue(obj, 0);
	}
	
	/**
	 * 将对象转换为单精度数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static float toFloatValue(Object obj, float defaultValue) {
		return toFloat(obj, defaultValue);
	}
	
	/**
	 * 将对象转换为整数值，当对象为空或转换失败时返回空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static Integer toInteger(Object obj) {
		return toInteger(obj, null);
	}
	
	/**
	 * 将对象转换为整数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static Integer toInteger(Object obj, Integer defaultValue) {
		if (obj == null)
			return defaultValue;
		
		if (obj instanceof Number)
			return ((Number) obj).intValue();
		
		try {
			/* 这里不用Integer.parseInt(obj.toString())的原因在于当字符串为小数时，最终的结果为默认值。
			 * 因此这里为了与Number类型的小数(如Double、Float和BigDecimal)保持一致的转换结果，
			 * 统一的先将字符串转换成BigDecimal后再获取最终结果。
			 * 如下toLong、toShort、toByte、toBigInteger方法的处理方式类似。*/
			return new BigDecimal(obj.toString()).intValue();
		} catch (NumberFormatException e) {
			return defaultValue;
	    }
	}
	
	/** 
	 * 将对象转换为整数值，当对象为空或转换失败时返回0
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static int toIntegerValue(Object obj) {
		return toIntegerValue(obj, 0);
	}
	
	/**
	 * 将对象转换为整数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int toIntegerValue(Object obj, int defaultValue) {
		return toInteger(obj, defaultValue);
	}
	
	/**
	 * 将对象转换为长整型数值，当对象为空或转换失败时返回空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static Long toLong(Object obj) {
		return toLong(obj, null);
	}
	
	/**
	 * 将对象转换为长整型数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static Long toLong(Object obj, Long defaultValue) {
		if (obj == null)
			return defaultValue;
		
		if (obj instanceof Number)
			return ((Number) obj).longValue();
		
		try {
			return new BigDecimal(obj.toString()).longValue();
		} catch (NumberFormatException e) {
			return defaultValue;
	    }
	}
	
	/** 
	 * 将对象转换为长整型数值，当对象为空或转换失败时返回0
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static long toLongValue(Object obj) {
		return toLongValue(obj, 0);
	}
	
	/**
	 * 将对象转换为长整型数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static long toLongValue(Object obj, long defaultValue) {
		return toLong(obj, defaultValue);
	}
	
	/**
	 * 将对象转换为短整型数值，当对象为空或转换失败时返回空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static Short toShort(Object obj) {
		return toShort(obj, null);
	}
	
	/**
	 * 将对象转换为短整型数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static Short toShort(Object obj, Short defaultValue) {
		if (obj == null)
			return defaultValue;
		
		if (obj instanceof Number)
			return ((Number) obj).shortValue();
		
		try {
			return new BigDecimal(obj.toString()).shortValue();
		} catch (NumberFormatException e) {
			return defaultValue;
	    }
	}
	
	/**
	 * 将对象转换为短整型数值，当对象为空或转换失败时返回0
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static short toShortValue(Object obj) {
		return toShortValue(obj, (short) 0);
	}
	
	/**
	 * 将对象转换为短整型数值，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static short toShortValue(Object obj, short defaultValue) {
		return toShort(obj, defaultValue);
	}
	
	/**
	 * 将对象转换为比特数，当对象为空或转换失败时返回空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static Byte toByte(Object obj) {
		return toByte(obj, null);
	}
	
	/**
	 * 将对象转换为比特数，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static Byte toByte(Object obj, Byte defaultValue) {
		if (obj == null)
			return defaultValue;
		
		if (obj instanceof Number)
			return ((Number) obj).byteValue();
		
		try {
			return new BigDecimal(obj.toString()).byteValue();
		} catch (NumberFormatException e) {
			return defaultValue;
	    }
	}
	
	/**
	 * 将对象转换为比特数，当对象为空或转换失败时返回0
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static byte toByteValue(Object obj) {
		return toByteValue(obj, (byte) 0);
	}
	
	/**
	 * 将对象转换为比特数，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static byte toByteValue(Object obj, byte defaultValue) {
		return toByte(obj, defaultValue);
	}
	
	/**
	 * 将对象转换为BigInteger，当对象为空或转换失败时返回空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static BigInteger toBigInteger(Object obj) {
		return toBigInteger(obj, null);
	}
	
	/**
	 * 将对象转换为BigInteger，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static BigInteger toBigInteger(Object obj, BigInteger defaultValue) {
		if (obj == null)
			return defaultValue;
		
		if (obj instanceof BigInteger)
			return (BigInteger) obj;
		
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).toBigInteger();
		
		try {
			return new BigDecimal(obj.toString()).toBigInteger();
		} catch (NumberFormatException e) {
			return defaultValue;
	    }
	}
	
	/**
	 * 将对象转换为BigInteger，当对象为空或转换失败时返回0
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static BigInteger toBigIntegerValue(Object obj) {
		return toBigIntegerValue(obj, new BigInteger("0"));
	}
	
	/**
	 * 将对象转换为BigInteger，当对象为空或转换失败时返回指定不为空的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static BigInteger toBigIntegerValue(Object obj, BigInteger defaultValue) {
		AssertUtils.assertNotNull(defaultValue, "Default big integer must not be null");
		return toBigInteger(obj, defaultValue);
	}
	
	/**
	 * 将对象转换为BigDecimal，当对象为空或转换失败时返回空
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		return toBigDecimal(obj, null);
	}
	
	/**
	 * 将对象转换为BigDecimal，当对象为空或转换失败时返回指定的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj, BigDecimal defaultValue) {
		if (obj == null)
			return defaultValue;
				
		if (obj instanceof BigDecimal)
			return (BigDecimal) obj;
		
		if (obj instanceof BigInteger)
			return new BigDecimal((BigInteger) obj);
		
		try {
			return new BigDecimal(obj.toString());
		} catch (NumberFormatException e) {
			return defaultValue;
	    }
	}
	
	/**
	 * 将对象转换为BigDecimal，当对象为空或转换失败时返回0
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimalValue(Object obj) {
		return toBigDecimalValue(obj, new BigDecimal(0));
	}
	
	/**
	 * 将对象转换为BigDecimal，当对象为空或转换失败时返回指定不为空的默认值
	 * @author Daniele 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimalValue(Object obj, BigDecimal defaultValue) {
		AssertUtils.assertNotNull(defaultValue, "Default big decimal must not be null");
		return toBigDecimal(obj, defaultValue);
	}
	
	/**
	 * 将数字对象统一转换为BigDecimal
	 * @author Daniele 
	 * @param number
	 * @return
	 */
	private static BigDecimal toBigDecimal(Number number) {
		AssertUtils.assertNotNull(number, "Number must not be null");
		
		if (number instanceof BigDecimal)
			return (BigDecimal) number;
		
		if (number instanceof BigInteger)
			return new BigDecimal((BigInteger) number);
		
		return new BigDecimal(number.toString());
	}
		
	/**
	 * 生成指定值之内的随机双精度浮点数
	 * @author Daniele 
	 * @param in
	 * @return
	 */
	public static double randomIn(double in) {
		return (double) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机浮点数
	 * @author Daniele 
	 * @param in
	 * @return
	 */
	public static float randomIn(float in) {
		return (float) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机整数
	 * @author Daniele 
	 * @param in
	 * @return
	 */
	public static int randomIn(int in) {
		return (int) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机长整数
	 * @author Daniele 
	 * @param in
	 * @return
	 */
	public static long randomIn(long in) {
		return (long) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机短整数
	 * @author Daniele 
	 * @param in
	 * @return
	 */
	public static short randomIn(short in) {
		return (short) (Math.random() * in);
	}
	
	/**
	 * 生成指定值之内的随机比特数
	 * @author Daniele 
	 * @param in
	 * @return
	 */
	public static byte randomIn(byte in) {
		return (byte) (Math.random() * in);
	}
		
	/**
	 * 产生[min,max]区间内的随机整数
	 * @author Daniele 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int rangeRandom(int min, int max) {
		return min < max ? randomIn(max - min + 1) + min : randomIn(min - max + 1) + max;
	}
	
	/**
	 * 产生[min,max]区间内的随机长整数
	 * @author Daniele 
	 * @param min
	 * @param max
	 * @return
	 */
	public static long rangeRandom(long min, long max) {
		return min < max ? randomIn(max - min + 1) + min : randomIn(min - max + 1) + max;
	}
		
	/**
	 * 将指定值限定在[limit,+∞)区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static double minLimit(double value, double limit) {
		return Math.max(value, limit);
	}
	
	/**
	 * 将指定值限定在[limit,+∞)区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static float minLimit(float value, float limit) {
		return Math.max(value, limit);
	}
	
	/**
	 * 将指定值限定在最小范围外
	 * @author Daniele 
	 * @param i
	 * @param value
	 * @return
	 */
	public static int minLimit(int value, int limit) {
		return Math.max(value, limit);
	}
	
	/**
	 * 将指定值限定在[limit,+∞)区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static long minLimit(long value, long limit) {
		return Math.max(value, limit);
	}
	
	/**
	 * 将指定值限定在[limit,+∞)区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static short minLimit(short value, short limit) {
		return value < limit ? limit : value;
	}
	
	/**
	 * 将指定值限定在[limit,+∞)区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static byte minLimit(byte value, byte limit) {
		return value < limit ? limit : value;
	}
	
	/**
	 * 将指定值限定在[limit,+∞)区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static char minLimit(char value, char limit) {
		return value < limit ? limit : value;
	}
	
	/**
	 * 将指定值限定在(-∞,limit]区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static double maxLimit(double value, double limit) {
		return Math.min(value, limit);
	}
	
	/**
	 * 将指定值限定在(-∞,limit]区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static float maxLimit(float value, float limit) {
		return Math.min(value, limit);
	}
	
	/**
	 * 将指定值限定在(-∞,limit]区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static int maxLimit(int value, int limit) {
		return Math.min(value, limit);
	}
	
	/**
	 * 将指定值限定在(-∞,limit]区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static long maxLimit(long value, long limit) {
		return Math.min(value, limit);
	}
	
	/**
	 * 将指定值限定在(-∞,limit]区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static short maxLimit(short value, short limit) {
		return value > limit ? limit : value;
	}
	
	/**
	 * 将指定值限定在(-∞,limit]区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static byte maxLimit(byte value, byte limit) {
		return value > limit ? limit : value;
	}
	
	/**
	 * 将指定值限定在(-∞,limit]区间
	 * @author Daniele 
	 * @param value
	 * @param limit
	 * @return
	 */
	public static char maxLimit(char value, char limit) {
		return value > limit ? limit : value;
	}
	
	/**
	 * 将指定值限定在[minLimit,maxLimit]区间内
	 * @author Daniele 
	 * @param value
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public static double rangeLimit(double value, double minLimit, double maxLimit) {
		return minLimit > maxLimit ? minLimit(maxLimit(value, minLimit), maxLimit)
				:minLimit(maxLimit(value, maxLimit), minLimit);
	}
	
	/**
	 * 将指定值限定在[minLimit,maxLimit]区间内
	 * @author Daniele 
	 * @param value
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public static float rangeLimit(float value, float minLimit, float maxLimit) {
		return minLimit > maxLimit ? minLimit(maxLimit(value, minLimit), maxLimit)
				:minLimit(maxLimit(value, maxLimit), minLimit);
	}
	
	/**
	 * 将指定值限定在[minLimit,maxLimit]区间内
	 * @author Daniele 
	 * @param value
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public static int rangeLimit(int value, int minLimit, int maxLimit) {
		return minLimit > maxLimit ? minLimit(maxLimit(value, minLimit), maxLimit)
				:minLimit(maxLimit(value, maxLimit), minLimit);
	}
	
	/**
	 * 将指定值限定在[minLimit,maxLimit]区间内
	 * @author Daniele 
	 * @param value
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public static long rangeLimit(long value, long minLimit, long maxLimit) {
		return minLimit > maxLimit ? minLimit(maxLimit(value, minLimit), maxLimit)
				:minLimit(maxLimit(value, maxLimit), minLimit);
	}
	
	/**
	 * 将指定值限定在[minLimit,maxLimit]区间内
	 * @author Daniele 
	 * @param value
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public static short rangeLimit(short value, short minLimit, short maxLimit) {
		return minLimit > maxLimit ? minLimit(maxLimit(value, minLimit), maxLimit)
				:minLimit(maxLimit(value, maxLimit), minLimit);
	}
	
	/**
	 * 将指定值限定在[minLimit,maxLimit]区间内
	 * @author Daniele 
	 * @param value
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public static byte rangeLimit(byte value, byte minLimit, byte maxLimit) {
		return minLimit > maxLimit ? minLimit(maxLimit(value, minLimit), maxLimit)
				:minLimit(maxLimit(value, maxLimit), minLimit);
	}
	
	/**
	 * 将指定值限定在[minLimit,maxLimit]区间内
	 * @author Daniele 
	 * @param value
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public static char rangeLimit(char value, char minLimit, char maxLimit) {
		return minLimit > maxLimit ? minLimit(maxLimit(value, minLimit), maxLimit)
				:minLimit(maxLimit(value, maxLimit), minLimit);
	}
		
	/**
	 * 判断数字是否大于指定的字符ASCII码
	 * @author Daniele 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean greaterThan(Number number, char c) {
		return greaterThan(number, new BigDecimal(c));
	}
	
	/**
	 * 判断number1是否大于number2
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean greaterThan(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		return toBigDecimal(number1).compareTo(toBigDecimal(number2)) == 1;
	}
	
	/**
	 * 判断obj1代表的数字是否大于obj2代表的数字
	 * @author Daniele 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean greaterThan(Object obj1, Object obj2) {
		return greaterThan(toBigDecimal(obj1, null), toBigDecimal(obj2, null));
	}
	
	/**
	 * 判断数字是否大于等于指定的字符ASCII码值
	 * @author Daniele 
	 * @param number
	 * @param c
	 * @return
	 */
	public static boolean greaterThanEquals(Number number, char c) {
		return greaterThanEquals(number, new BigDecimal(c));
	}
		
	/**
	 * 判断number1是否大于等于number2
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean greaterThanEquals(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		return toBigDecimal(number1).compareTo(toBigDecimal(number2)) > -1;
	}
	
	/**
	 * 判断obj1代表的数字是否大于等于obj2代表的数字
	 * @author Daniele 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean greaterThanEquals(Object obj1, Object obj2) {
		return greaterThanEquals(toBigDecimal(obj1, null), toBigDecimal(obj2, null));
	}
		
	/**
	 * 判断数字是否小于等于指定的字符ASCII码值
	 * @author Daniele 
	 * @param decimal
	 * @param c
	 * @return
	 */
	public static boolean lessThanEquals(Number number, char c) {
		return lessThanEquals(number, new BigDecimal(c));
	}
		
	/**
	 * 判断number1是否小于等于number2
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean lessThanEquals(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		return toBigDecimal(number1).compareTo(toBigDecimal(number2)) < 1;
	}
	
	/**
	 * 判断obj1代表的数字是否小于等于obj2代表的数字
	 * @author Daniele 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean lessThanEquals(Object obj1, Object obj2) {
		return lessThanEquals(toBigDecimal(obj1, null), toBigDecimal(obj2, null));
	}
	
	/**
	 * 判断数字是否小于指定的字符ASCII码值
	 * @author Daniele 
	 * @param number
	 * @param c
	 * @return
	 */
	public static boolean lessThan(Number number, char c) {
		return lessThan(number, new BigDecimal(c));
	}
		
	/**
	 * 判断number1是否小于number2
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean lessThan(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		return toBigDecimal(number1).compareTo(toBigDecimal(number2)) == -1;
	}
	
	/**
	 * 判断obj1代表的数字是否小于obj2代表的数字
	 * @author Daniele 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean lessThan(Object obj1, Object obj2) {
		return lessThan(toBigDecimal(obj1, null), toBigDecimal(obj2, null));
	}
			
	/**
	 * 判断数字是否等于指定的字符ASCII码值
	 * @author Daniele 
	 * @param number
	 * @param c
	 * @return
	 */
	public static boolean equals(Number number, char c) {
		return equals(number, new BigDecimal(c));
	}
		
	/**
	 * 判断number1是否等于number2
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean equals(Number number1, Number number2) {
		if (number1 == null || number2 == null)
			return false;
		
		return toBigDecimal(number1).compareTo(toBigDecimal(number2)) == 0;
	}
	
	/**
	 * 判断两个对象代表的数字是否相等
	 * @author Daniele 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {
		return equals(toBigDecimal(obj1, null), toBigDecimal(obj2, null));
	}
	
	/**
	 * 计算decimal + number的值
	 * @author Daniele 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal add(BigDecimal decimal, char number) {
		return add(decimal, new BigDecimal(number));
	}
			
	/**
	 * 计算number1 + number2的值
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal add(Number number1, Number number2) {
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.add(decimal2);
	}
		
	/**
	 * 计算decimal - number的值
	 * @author Daniele 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal decimal, char number) {
		return subtract(decimal, new BigDecimal(number));
	}
		
	/**
	 * 计算number1 - number2的值
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal subtract(Number number1, Number number2) {		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.subtract(decimal2);
	}
		
	/**
	 * 计算decimal * number的值
	 * @author Daniele 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal decimal, char number) {
		return multiply(decimal, new BigDecimal(number));
	}
		
	/**
	 * 计算number1 * number2的值
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal multiply(Number number1, Number number2) {		
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.multiply(decimal2);
	}
		
	/**
	 * 计算decimal ÷ number的值，返回整数结果
	 * @author Daniele 
	 * @param decimal
	 * @param number
	 * @return
	 */
	public static BigDecimal divide(BigDecimal decimal, char number) {
		return divide(decimal, new BigDecimal(number));
	}
	
	/**
	 * 计算decimal ÷ number的值，并保留scale位小数后返回最终结果
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * @author Daniele 
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
	 * 计算number1 ÷ number2的值，返回整数结果
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static BigDecimal divide(Number number1, Number number2) {
		return divide(number1, number2, 0);
	}
	
	/**
	 * 计算number1 ÷ number2的值，并保留scale位小数后返回最终结果
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(Number number1, Number number2, int scale) {
		return divide(number1, number2, scale, null);
	}
	
	/**
	 * 计算number1 ÷ number2的值，并以roundingMode指定的四舍五入除不尽的小数后返回最终结果
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(Number number1, Number number2, RoundingMode roundingMode) {
		return divide(number1, number2, 0, roundingMode);
	}
	
	/**
	 * 计算number1 ÷ number2的值，并以roundingMode指定的四舍五入的形式保留scale位小数后返回最终结果
	 * @author Daniele 
	 * @param number1
	 * @param number2
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(Number number1, Number number2, int scale, RoundingMode roundingMode) {
		BigDecimal decimal1 = toBigDecimal(number1);
		BigDecimal decimal2 = toBigDecimal(number2);
		return decimal1.divide(decimal2, scale, roundingMode != null ? roundingMode : RoundingMode.HALF_EVEN);
	}
	
	/**
	 * 将字符ASCII码转化为指定格式的字符串
	 * @author Daniele 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(char c, String pattern) {
		return format((long) c, pattern);
	}
	
	/**
	 * 将字符ASCII码转化为固定长度的字符串
	 * @author Daniele 
	 * @param number
	 * @param length
	 * @return
	 */
	public static String format(char c, int length) {
		return format((long) c, length);
	}
	
	/**
	 * 将数字转化为指定格式的字符串
	 * @author Daniele 
	 * @param number
	 * @param pattern
	 * @return
	 */
	public static String format(Number number, String pattern) {
		if (number == null)
			return null;
		
		if (pattern == null)
			return number.toString();
		
		return getDecimalFormat(pattern).format(number);
	}
	
	/**
	 * 将数字转化为固定长度的字符串
	 * @author Daniele 
	 * @param number
	 * @param length
	 * @return
	 */
	public static String format(Number number, int length) {
		if (number == null)
			return null;
		
		DecimalFormat decimalFormat = getDecimalFormat(length);
		return decimalFormat != null ? decimalFormat.format(number) : number.toString();
	}
			
	/**
	 * 绝对值取反
	 * @author Daniele 
	 * @param a
	 * @return
	 */
	public static int unAbs(int a) {
		return (a > 0) ? -a : a;
	}
	
	/**
	 * 绝对值取反
	 * @author Daniele 
	 * @param a
	 * @return
	 */
	public static long unAbs(long a) {
		return (a > 0) ? -a : a;
	}
	
	/**
	 * 绝对值取反
	 * @author Daniele 
	 * @param a
	 * @return
	 */
	public static float unAbs(float a) {
        return (a > 0.0F) ? -a : a;
    }
	
	/**
	 * 绝对值取反
	 * @author Daniele 
	 * @param a
	 * @return
	 */
	public static double unAbs(double a) {
        return (a > 0.0D) ? -a : a;
    }
				
}
