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

/**
 * 数组工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0.0
 */
public class ArrayUtils {
	
	/**
	 * 判断byte数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(byte[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断byte数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(byte[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 判断boolean数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(boolean[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断boolean数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(boolean[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 判断char数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(char[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断char数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(char[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 判断double数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(double[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断double数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(double[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 判断float数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(float[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断float数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(float[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 判断int数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(int[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断int数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(int[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 判断long数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(long[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断long数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(long[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 判断short数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(short[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断short数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(short[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 判断数组是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param array
	 * @return 
	 */
	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断数组是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param array
	 * @return 
	 */
	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * 获取数组的长度
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param array
	 * @return 
	 */
	public static int length(Object[] array) {
		return array != null ? array.length : 0;
	}
	
	/**
	 * 获取数组的长度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return 数组长度，当参数为一个非数组对象时返回0
	 */
	public static int length(Object array) {
		return isArray(array) ? Array.getLength(array) : 0;
	}
	
	/**
	 * 判断指定的对象是否为一数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static boolean isArray(Object obj) {
		return obj != null && obj.getClass().isArray();
	}
	
	/**
	 * 将boolean[]数组转换为Boolean[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Boolean[] convert(boolean[] array) {
		if (array == null)
			return null;
		
		int length = array.length;
		Boolean[] wapperArray = new Boolean[length];
		for (int i = 0; i < length; i++)
			wapperArray[i] = array[i];
		return wapperArray;
	}
	
	/**
	 * 将Boolean[]数组转换为boolean[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean[] convert(Boolean[] array) {
		return convert(array, false);
	}
	
	/**
	 * 将Boolean[]数组转换为boolean[]。
	 * 				在转换的过程中，如果元素为空，则统一设置为指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param defaultValueIfNull
	 * @return
	 */
	public static boolean[] convert(Boolean[] array, boolean defaultValueIfNull) {
		if (array == null)
			return null;
		
		int length = array.length;
		boolean[] baseArray = new boolean[length];
		for (int i = 0; i < length; i++) 
			baseArray[i] = (array[i] != null ? array[i] : defaultValueIfNull);
		return baseArray;
	}
	
	/**
	 * 将byte[]数组转换为Byte[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Byte[] convert(byte[] array) {
		if (array == null)
			return null;
		
		int length = array.length;
		Byte[] wapperArray = new Byte[length];
		for (int i = 0; i < length; i++)
			wapperArray[i] = array[i];
		return wapperArray;
	}
	
	/**
	 * 将Byte[]数组转换为byte[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static byte[] convert(Byte[] array) {
		return convert(array, (byte) 0);
	}
	
	/**
	 * 将Byte[]数组转换为byte[]。
	 * 				在转换的过程中，如果元素为空，则统一设置为指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param defaultValueIfNull
	 * @return
	 */
	public static byte[] convert(Byte[] array, byte defaultValueIfNull) {
		if (array == null)
			return null;
		
		int length = array.length;
		byte[] baseArray = new byte[length];
		for (int i = 0; i < length; i++) 
			baseArray[i] = (array[i] != null ? array[i] : defaultValueIfNull);
		return baseArray;
	}
	
	/**
	 * 将char[]数组转换为Character[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Character[] convert(char[] array) {
		if (array == null)
			return null;
		
		int length = array.length;
		Character[] wapperArray = new Character[length];
		for (int i = 0; i < length; i++)
			wapperArray[i] = array[i];
		return wapperArray;
	}
	
	/**
	 * 将Character[]数组转换为char[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static char[] convert(Character[] array) {
		return convert(array, Character.MIN_VALUE);
	}
	
	/**
	 * 将Character[]数组转换为char[]。
	 * 				在转换的过程中，如果元素为空，则统一设置为指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param defaultValueIfNull
	 * @return
	 */
	public static char[] convert(Character[] array, char defaultValueIfNull) {
		if (array == null)
			return null;
		
		int length = array.length;
		char[] baseArray = new char[length];
		for (int i = 0; i < length; i++) 
			baseArray[i] = (array[i] != null ? array[i] : defaultValueIfNull);
		return baseArray;
	}
	
	/**
	 * 将double[]数组转换为Double[]。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Double[] convert(double[] array) {
		if (array == null)
			return null;
		
		int length = array.length;
		Double[] wapperArray = new Double[length];
		for (int i = 0; i < length; i++)
			wapperArray[i] = array[i];
		return wapperArray;
	}
	
	/**
	 * 将Double[]数组转换为double[]。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static double[] convert(Double[] array) {
		return convert(array, 0);
	}
	
	/**
	 * 将Double[]数组转换为double[]。
	 * 				在转换的过程中，如果元素为空，则统一设置为指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param defaultValueIfNull
	 * @return
	 */
	public static double[] convert(Double[] array, double defaultValueIfNull) {
		if (array == null)
			return null;
		
		int length = array.length;
		double[] baseArray = new double[length];
		for (int i = 0; i < length; i++) 
			baseArray[i] = (array[i] != null ? array[i] : defaultValueIfNull);
		return baseArray;
	}
	
	/**
	 * 将float[]数组转换为Float[]。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Float[] convert(float[] array) {
		if (array == null)
			return null;
		
		int length = array.length;
		Float[] wapperArray = new Float[length];
		for (int i = 0; i < length; i++)
			wapperArray[i] = array[i];
		return wapperArray;
	}
	
	/**
	 * 将Float[]数组转换为float[]。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static float[] convert(Float[] array) {
		return convert(array, 0);
	}
	
	/**
	 * 将Float[]数组转换为float[]。
	 * 				在转换的过程中，如果元素为空，则统一设置为指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param defaultValueIfNull
	 * @return
	 */
	public static float[] convert(Float[] array, float defaultValueIfNull) {
		if (array == null)
			return null;
		
		int length = array.length;
		float[] baseArray = new float[length];
		for (int i = 0; i < length; i++) 
			baseArray[i] = (array[i] != null ? array[i] : defaultValueIfNull);
		return baseArray;
	}
			
	/**
	 * 将int[]数组转换为Integer[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Integer[] convert(int[] array) {
		if (array == null)
			return null;
		
		int length = array.length;
		Integer[] wapperArray = new Integer[length];
		for (int i = 0; i < length; i++)
			wapperArray[i] = array[i];
		return wapperArray;
	}
		
	/**
	 * 将Integer[]数组转换为int[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static int[] convert(Integer[] array) {
		return convert(array, 0);
	}
	
	/**
	 * 将Integer[]数组转换为int[]。
	 * 				在转换的过程中，如果元素为空，则统一设置为指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param defaultValueIfNull
	 * @return
	 */
	public static int[] convert(Integer[] array, int defaultValueIfNull) {
		if (array == null)
			return null;
		
		int length = array.length;
		int[] baseArray = new int[length];
		for (int i = 0; i < length; i++) 
			baseArray[i] = (array[i] != null ? array[i] : defaultValueIfNull);
		return baseArray;
	}
	
	/**
	 * 将long[]数组转换为Long[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Long[] convert(long[] array) {
		if (array == null)
			return null;
		
		int length = array.length;
		Long[] wapperArray = new Long[length];
		for (int i = 0; i < length; i++)
			wapperArray[i] = array[i];
		return wapperArray;
	}
	
	/**
	 * 将Long[]数组转换为long[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static long[] convert(Long[] array) {
		return convert(array, 0L);
	}
	
	/**
	 * 将Long[]数组转换为long[]。
	 * 				在转换的过程中，如果元素为空，则统一设置为指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param defaultValueIfNull
	 * @return
	 */
	public static long[] convert(Long[] array, long defaultValueIfNull) {
		if (array == null)
			return null;
		
		int length = array.length;
		long[] baseArray = new long[length];
		for (int i = 0; i < length; i++) 
			baseArray[i] = (array[i] != null ? array[i] : defaultValueIfNull);
		return baseArray;
	}
	
	/**
	 * 将short[]数组转换为Short[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Short[] convert(short[] array) {
		if (array == null)
			return null;
		
		int length = array.length;
		Short[] wapperArray = new Short[length];
		for (int i = 0; i < length; i++)
			wapperArray[i] = array[i];
		return wapperArray;
	}
	
	/**
	 * 将Short[]数组转换为short[]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static short[] convert(Short[] array) {
		return convert(array, (short) 0);
	}
	
	/**
	 * 将Short[]数组转换为short[]。
	 * 				在转换的过程中，如果元素为空，则统一设置为指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param defaultValueIfNull
	 * @return
	 */
	public static short[] convert(Short[] array, short defaultValueIfNull) {
		if (array == null)
			return null;
		
		int length = array.length;
		short[] baseArray = new short[length];
		for (int i = 0; i < length; i++) 
			baseArray[i] = (array[i] != null ? array[i] : defaultValueIfNull);
		return baseArray;
	}
	
	/**
	 * 将boolean类型的元素组添加到同类型的原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static boolean[] add(boolean[] array, boolean[] element) {
		if (isEmpty(array))
			return element != null ? element : new boolean[]{};
		if (isEmpty(element))
			return array != null ? array : new boolean[]{};
		
		boolean joinedArray[] = new boolean[array.length + element.length];
		System.arraycopy(array, 0, joinedArray, 0, array.length);
        System.arraycopy(element, 0, joinedArray, array.length, element.length);
		return joinedArray;
	}
	
	/**
	 * 将byte类型的元素组添加到同类型的原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static byte[] add(byte[] array, byte[] element) {
		if (isEmpty(array))
			return element != null ? element : new byte[]{};
		if (isEmpty(element))
			return array != null ? array : new byte[]{};
		
		byte joinedArray[] = new byte[array.length + element.length];
		System.arraycopy(array, 0, joinedArray, 0, array.length);
        System.arraycopy(element, 0, joinedArray, array.length, element.length);
		return joinedArray;
	}
	
	/**
	 * 将char类型的元素组添加到同类型的原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static char[] add(char[] array, char[] element) {
		if (isEmpty(array))
			return element != null ? element : new char[]{};
		if (isEmpty(element))
			return array != null ? array : new char[]{};
		
		char joinedArray[] = new char[array.length + element.length];
		System.arraycopy(array, 0, joinedArray, 0, array.length);
        System.arraycopy(element, 0, joinedArray, array.length, element.length);
		return joinedArray;
	}
	
	/**
	 * 将double类型的元素组添加到同类型的原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static double[] add(double[] array, double[] element) {
		if (isEmpty(array))
			return element != null ? element : new double[]{};
		if (isEmpty(element))
			return array != null ? array : new double[]{};
		
		double joinedArray[] = new double[array.length + element.length];
		System.arraycopy(array, 0, joinedArray, 0, array.length);
        System.arraycopy(element, 0, joinedArray, array.length, element.length);
		return joinedArray;
	}
	
	/**
	 * 将float类型的元素组添加到同类型的原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static float[] add(float[] array, float[] element) {
		if (isEmpty(array))
			return element != null ? element : new float[]{};
		if (isEmpty(element))
			return array != null ? array : new float[]{};
		
		float joinedArray[] = new float[array.length + element.length];
		System.arraycopy(array, 0, joinedArray, 0, array.length);
        System.arraycopy(element, 0, joinedArray, array.length, element.length);
		return joinedArray;
	}
	
	/**
	 * 将int类型的元素组添加到同类型的原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int[] add(int[] array, int[] element) {
		if (isEmpty(array))
			return element != null ? element : new int[]{};
		if (isEmpty(element))
			return array != null ? array : new int[]{};
		
		int joinedArray[] = new int[array.length + element.length];
		System.arraycopy(array, 0, joinedArray, 0, array.length);
        System.arraycopy(element, 0, joinedArray, array.length, element.length);
		return joinedArray;
	}
	
	/**
	 * 将long类型的元素组添加到同类型的原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static long[] add(long[] array, long[] element) {
		if (isEmpty(array))
			return element != null ? element : new long[]{};
		if (isEmpty(element))
			return array != null ? array : new long[]{};
		
		long joinedArray[] = new long[array.length + element.length];
		System.arraycopy(array, 0, joinedArray, 0, array.length);
        System.arraycopy(element, 0, joinedArray, array.length, element.length);
		return joinedArray;
	}
	
	/**
	 * 将short类型的元素组添加到同类型的原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static short[] add(short[] array, short[] element) {
		if (isEmpty(array))
			return element != null ? element : new short[]{};
		if (isEmpty(element))
			return array != null ? array : new short[]{};
		
		short joinedArray[] = new short[array.length + element.length];
		System.arraycopy(array, 0, joinedArray, 0, array.length);
        System.arraycopy(element, 0, joinedArray, array.length, element.length);
		return joinedArray;
	}
	
				
	/**
	 * 将Object类型的元素组添加到原数组后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static Object[] add(Object[] array, Object[] element) {
		if (isEmpty(array))
			return element != null ? element : new Object[]{};
		if (isEmpty(element))
			return array != null ? array : new Object[]{};
		
		return copy(array, element, Object.class);
	}
	
	/**
	 * 将元素添加到原数组的后面
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static Object[] add(Object array, Object element) {
		return add(array, element, ClassUtils.getCommonType(array, element));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] add(Object array, Object element, Class<?> componentType) {
		Object[] array1 = convertWapperArray(array);
		Object[] array2 = convertWapperArray(element);
		return (T[]) copy(array1, array2, componentType != null ? componentType : Object.class);
	}
	
	/**
	 * 将基本类型的数组准换为包装类型的
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseTypeArray
	 * @return
	 */
	private static Object[] convertWapperArray(Object baseTypeArray) {
		Object[] array = null;
		if (isArray(baseTypeArray)) {
			Class<?> arrayComponentType = baseTypeArray.getClass().getComponentType();
			if (ClassUtils.isBaseType(arrayComponentType)) {
				if (arrayComponentType == Boolean.TYPE)
					array = convert((boolean[]) baseTypeArray);
				else if (arrayComponentType == Byte.TYPE)
					array = convert((byte[]) baseTypeArray);
				else if (arrayComponentType == Character.TYPE)
					array = convert((char[]) baseTypeArray);
				else if (arrayComponentType == Double.TYPE)
					array = convert((double[]) baseTypeArray);
				else if (arrayComponentType == Float.TYPE)
					array = convert((float[]) baseTypeArray);
				else if (arrayComponentType == Integer.TYPE)
					array = convert((int[]) baseTypeArray);
				else if (arrayComponentType == Long.TYPE)
					array = convert((long[]) baseTypeArray);
				else
					array = convert((short[]) baseTypeArray);
			} else
				array = (Object[]) baseTypeArray;
		} else
			array = new Object[] { baseTypeArray };
		
		return array;
	}
		
	@SuppressWarnings("unchecked")
	private static <T1, T2, T3> T3[] copy(T1[] array, T2[] element, Class<T3> componentType) {
		int arrayLength = array.length;
		int elementLength = element.length;
		
		if (ClassUtils.isBaseType(componentType))
			// 将基本类型转换为对应的包装类型后再进行拷贝，防止出现java.lang.ArrayStoreException
			componentType = (Class<T3>) ClassUtils.getWrapperType(componentType);
		
		Object dest = Array.newInstance(componentType != null ? componentType
				: Object.class, arrayLength + elementLength);
		System.arraycopy(array, 0, dest, 0, arrayLength);
		System.arraycopy(element, 0, dest, arrayLength, elementLength);
		return (T3[]) dest;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(boolean[] array, boolean element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(boolean[] array, boolean element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = start; i < array.length; i++)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(boolean[] array, boolean[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int indexOf(boolean[] array, boolean[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = 0;
		int i;
		do {
			for (i = start++; i < length; i++) {
				if (array[i] == subArray[subIndex++]) {
					if (index == -1)
						index = i;
					
					if (subIndex == subLength) {
						i = length;
						break;
					}
				} else {
					subIndex = 0;
					break;
				}
			}
			
			if (subIndex < subLength)
				index = -1;
		} while (i < length);
		
		return index;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(byte[] array, byte element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(byte[] array, byte element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = start; i < array.length; i++)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(byte[] array, byte[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int indexOf(byte[] array, byte[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = 0;
		int i;
		do {
			for (i = start++; i < length; i++) {
				if (array[i] == subArray[subIndex++]) {
					if (index == -1)
						index = i;
					
					if (subIndex == subLength) {
						i = length;
						break;
					}
				} else {
					subIndex = 0;
					break;
				}
			}
			
			if (subIndex < subLength)
				index = -1;
		} while (i < length);
		
		return index;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(char[] array, char element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(char[] array, char element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = start; i < array.length; i++)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(char[] array, char[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int indexOf(char[] array, char[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = 0;
		int i;
		do {
			for (i = start++; i < length; i++) {
				if (array[i] == subArray[subIndex++]) {
					if (index == -1)
						index = i;
					
					if (subIndex == subLength) {
						i = length;
						break;
					}
				} else {
					subIndex = 0;
					break;
				}
			}
			
			if (subIndex < subLength)
				index = -1;
		} while (i < length);
		
		return index;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(double[] array, double element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(double[] array, double element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = start; i < array.length; i++)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(double[] array, double[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int indexOf(double[] array, double[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = 0;
		int i;
		do {
			for (i = start++; i < length; i++) {
				if (array[i] == subArray[subIndex++]) {
					if (index == -1)
						index = i;
					
					if (subIndex == subLength) {
						i = length;
						break;
					}
				} else {
					subIndex = 0;
					break;
				}
			}
			
			if (subIndex < subLength)
				index = -1;
		} while (i < length);
		
		return index;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(float[] array, float element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(float[] array, float element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = start; i < array.length; i++)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(float[] array, float[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int indexOf(float[] array, float[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = 0;
		int i;
		do {
			for (i = start++; i < length; i++) {
				if (array[i] == subArray[subIndex++]) {
					if (index == -1)
						index = i;
					
					if (subIndex == subLength) {
						i = length;
						break;
					}
				} else {
					subIndex = 0;
					break;
				}
			}
			
			if (subIndex < subLength)
				index = -1;
		} while (i < length);
		
		return index;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(int[] array, int element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(int[] array, int element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = start; i < array.length; i++)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(int[] array, int[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int indexOf(int[] array, int[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = 0;
		int i;
		do {
			for (i = start++; i < length; i++) {
				if (array[i] == subArray[subIndex++]) {
					if (index == -1)
						index = i;
					
					if (subIndex == subLength) {
						i = length;
						break;
					}
				} else {
					subIndex = 0;
					break;
				}
			}
			
			if (subIndex < subLength)
				index = -1;
		} while (i < length);
		
		return index;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(long[] array, long element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(long[] array, long element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = start; i < array.length; i++)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(long[] array, long[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int indexOf(long[] array, long[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = 0;
		int i;
		do {
			for (i = start++; i < length; i++) {
				if (array[i] == subArray[subIndex++]) {
					if (index == -1)
						index = i;
					
					if (subIndex == subLength) {
						i = length;
						break;
					}
				} else {
					subIndex = 0;
					break;
				}
			}
			
			if (subIndex < subLength)
				index = -1;
		} while (i < length);
		
		return index;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(short[] array, short element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(short[] array, short element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = start; i < array.length; i++)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(short[] array, short[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取第一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int indexOf(short[] array, short[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = 0;
		int i;
		do {
			for (i = start++; i < length; i++) {
				if (array[i] == subArray[subIndex++]) {
					if (index == -1)
						index = i;
					
					if (subIndex == subLength) {
						i = length;
						break;
					}
				} else {
					subIndex = 0;
					break;
				}
			}
			
			if (subIndex < subLength)
				index = -1;
		} while (i < length);
		
		return index;
	}

	/**
	 * 获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int indexOf(Object[] array, Object[] subArray) {
		return indexOf(array, subArray, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return
	 */
	public static int indexOf(Object[] array, Object[] subArray, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		if (subArray == null) {
			for (int i = start; i < array.length; i++)
				if (ObjectUtils.equals(array[i], subArray))
					return i;
		} else {
			
			int subLength = subArray.length;
			int length;
			if (subLength == 0 || (length = array.length) < subLength)
				return -1;
			
			int index = -1;
			int subIndex = 0;
			int i;
			do {
				for (i = start++; i < length; i++) {
					if (ObjectUtils.equals(array[i], subArray[subIndex++])) {
						if (index == -1)
							index = i;
						
						/* 下标为字数组长度后强制退出本次和后续的while循环 */
						if (subIndex == subLength) {
							i = length;
							break;
						}
					} else {
						/* 退出本次循环，在下一次循环中重新从子数组的第一个元素开始比较 */
						subIndex = 0;
						break;
					}
				}
				
				// 如果未遍历完整个子数组，说明在这次for循环中这个子数组不是原数组的子集
				if (subIndex < subLength)
					index = -1;
				
			} while (i < length);
				
			return index;
		}
		return -1;
	}
	
	/**
	 * 获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(Object[] array, Object element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(Object[] array, Object element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		if (!isArray(element)) {
			for (int i = start; i < array.length; i++)
				if (ObjectUtils.equals(array[i], element))
					return i;
		} else
			return indexOf(array, convertWapperArray(element), start);
		
		return -1;
	}
	
	/**
	 * 获取第一个匹配值字符序列元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int indexOf(CharSequence[] array, CharSequence element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取第一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int indexOf(CharSequence[] array, CharSequence element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		
		for (int i = start; i < array.length; i++) {
			if (StringUtils.equals(array[i], element))
				return i;
		}
			
		return -1;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(boolean[] array, boolean element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(boolean[] array, boolean element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = (array.length - 1); i >= start; i--)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(boolean[] array, boolean[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(boolean[] array, boolean[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = subLength - 1;
		int i;
		
		do {
			for (i = --length; i >= start; i--) {
				if (array[i] == subArray[subIndex--]) {
					index = i;
					if (subIndex < 0) 
						break;
					
				} else {
					subIndex = subLength - 1;
					break;
				}
			}
			
			if (index > -1 && subIndex < 0)
				//完全匹配时停止检测
				break;
			else
				index = -1;
			
		} while (i >= start);
		
		return index;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(byte[] array, byte element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(byte[] array, byte element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = (array.length - 1); i >= start; i--)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(byte[] array, byte[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(byte[] array, byte[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = subLength - 1;
		int i;
		
		do {
			for (i = --length; i >= start; i--) {
				if (array[i] == subArray[subIndex--]) {
					index = i;
					if (subIndex < 0) 
						break;
					
				} else {
					subIndex = subLength - 1;
					break;
				}
			}
			
			if (index > -1 && subIndex < 0)
				//完全匹配时停止检测
				break;
			else
				index = -1;
			
		} while (i >= start);
		
		return index;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(char[] array, char element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(char[] array, char element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = (array.length - 1); i >= start; i--)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(char[] array, char[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(char[] array, char[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = subLength - 1;
		int i;
		
		do {
			for (i = --length; i >= start; i--) {
				if (array[i] == subArray[subIndex--]) {
					index = i;
					if (subIndex < 0) 
						break;
					
				} else {
					subIndex = subLength - 1;
					break;
				}
			}
			
			if (index > -1 && subIndex < 0)
				//完全匹配时停止检测
				break;
			else
				index = -1;
			
		} while (i >= start);
		
		return index;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(double[] array, double element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(double[] array, double element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = (array.length - 1); i >= start; i--)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(double[] array, double[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(double[] array, double[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = subLength - 1;
		int i;
		
		do {
			for (i = --length; i >= start; i--) {
				if (array[i] == subArray[subIndex--]) {
					index = i;
					if (subIndex < 0) 
						break;
					
				} else {
					subIndex = subLength - 1;
					break;
				}
			}
			
			if (index > -1 && subIndex < 0)
				//完全匹配时停止检测
				break;
			else
				index = -1;
			
		} while (i >= start);
		
		return index;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(float[] array, float element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(float[] array, float element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = (array.length - 1); i >= start; i--)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(float[] array, float[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(float[] array, float[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = subLength - 1;
		int i;
		
		do {
			for (i = --length; i >= start; i--) {
				if (array[i] == subArray[subIndex--]) {
					index = i;
					if (subIndex < 0) 
						break;
					
				} else {
					subIndex = subLength - 1;
					break;
				}
			}
			
			if (index > -1 && subIndex < 0)
				//完全匹配时停止检测
				break;
			else
				index = -1;
			
		} while (i >= start);
		
		return index;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(int[] array, int element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(int[] array, int element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = (array.length - 1); i >= start; i--)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(int[] array, int[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(int[] array, int[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = subLength - 1;
		int i;
		
		do {
			for (i = --length; i >= start; i--) {
				if (array[i] == subArray[subIndex--]) {
					index = i;
					if (subIndex < 0) 
						break;
					
				} else {
					subIndex = subLength - 1;
					break;
				}
			}
			
			if (index > -1 && subIndex < 0)
				//完全匹配时停止检测
				break;
			else
				index = -1;
			
		} while (i >= start);
		
		return index;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(long[] array, long element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(long[] array, long element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = (array.length - 1); i >= start; i--)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(long[] array, long[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(long[] array, long[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = subLength - 1;
		int i;
		
		do {
			for (i = --length; i >= start; i--) {
				if (array[i] == subArray[subIndex--]) {
					index = i;
					if (subIndex < 0) 
						break;
					
				} else {
					subIndex = subLength - 1;
					break;
				}
			}
			
			if (index > -1 && subIndex < 0)
				//完全匹配时停止检测
				break;
			else
				index = -1;
			
		} while (i >= start);
		
		return index;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(short[] array, short element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(short[] array, short element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		for (int i = (array.length - 1); i >= start; i--)
			if (array[i] == element)
				return i;
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(short[] array, short[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(short[] array, short[] subArray, int start) {
		if (isEmpty(array) || isEmpty(subArray))
			return -1;
		
		int length = array.length;
		int subLength = subArray.length;
		if (length < subLength)
			return -1;
		
		if (start < 0)
			start = 0;
		
		int index = -1;
		int subIndex = subLength - 1;
		int i;
		
		do {
			for (i = --length; i >= start; i--) {
				if (array[i] == subArray[subIndex--]) {
					index = i;
					if (subIndex < 0) 
						break;
					
				} else {
					subIndex = subLength - 1;
					break;
				}
			}
			
			if (index > -1 && subIndex < 0)
				//完全匹配时停止检测
				break;
			else
				index = -1;
			
		} while (i >= start);
		
		return index;
	}
	
	/**
	 * 获取最后一个匹配子数组在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int lastIndexOf(Object[] array, Object[] subArray) {
		return lastIndexOf(array, subArray, 0);
	}
	
	/** 
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @param start
	 * @return 
	 */
	public static int lastIndexOf(Object[] array, Object[] subArray, int start) {
		if (isEmpty(array))
			return -1;
		
		int length = array.length;
		if (subArray == null) {
			for (int i = --length; i >= start; i--) {
				if (ObjectUtils.equals(array[i], subArray))
					return i;
			}
		} else {
			int subLength = subArray.length;
			if (subLength == 0 || length < subLength)
				return -1;
			
			if (start < 0)
				start = 0;
			
			int index = -1;
			int subIndex = subLength - 1;
			int i;
			
			do {
				for (i = --length; i >= start; i--) {
					if (ObjectUtils.equals(array[i], subArray[subIndex--])) {
						index = i;
						if (subIndex < 0) 
							break;
						
					} else {
						subIndex = subLength - 1;
						break;
					}
				}
				
				if (index > -1 && subIndex < 0)
					//完全匹配时停止检测
					break;
				else
					index = -1;
				
			} while (i >= start);
			
			return index;
		}
		return -1;
	}
	
	/**
	 * 获取最后一个匹配元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOf(Object[] array, Object element) {
		return lastIndexOf(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配元素在原数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOf(Object[] array, Object element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		
		if (!isArray(element)) {
			for (int i = (array.length - 1); i >= start; i--)
				if (ObjectUtils.equals(array[i], element))
					return i;
		} else 
			return lastIndexOf(array, convertWapperArray(element), start);
		
		return -1;
	}
	
	/**
	 * 获取最后一个匹配值字符序列元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @return
	 */
	public static int lastIndexOfValue(CharSequence[] array, CharSequence element) {
		return lastIndexOfValue(array, element, 0);
	}
	
	/**
	 * 从指定的索引位置开始获取最后一个匹配值字符序列元素在数组中的下标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int lastIndexOfValue(CharSequence[] array, CharSequence element, int start) {
		if (isEmpty(array))
			return -1;
		
		if (start < 0)
			start = 0;
		
		for (int i = (array.length - 1); i >= start; i--) {
			if (StringUtils.equals(array[i], element))
				return i;
		}
			
		return -1;
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static boolean[] remove(boolean[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		boolean[] result = new boolean[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static boolean[] removeElement(boolean[] array, boolean element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		
		return removeElement(remove(array, index), element);
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean[] removeSubArray(boolean[] array, boolean[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		boolean[] result = new boolean[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static byte[] remove(byte[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		byte[] result = new byte[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static byte[] removeElement(byte[] array, byte element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		
		return removeElement(remove(array, index), element);
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static byte[] removeSubArray(byte[] array, byte[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		byte[] result = new byte[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static char[] remove(char[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		char[] result = new char[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static char[] removeElement(char[] array, char element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		
		return removeElement(remove(array, index), element);
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static char[] removeSubArray(char[] array, char[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		char[] result = new char[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static double[] remove(double[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		double[] result = new double[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static double[] removeElement(double[] array, double element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		
		return removeElement(remove(array, index), element);
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static double[] removeSubArray(double[] array, double[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		double[] result = new double[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static float[] remove(float[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		float[] result = new float[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static float[] removeElement(float[] array, float element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		
		return removeElement(remove(array, index), element);
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static float[] removeSubArray(float[] array, float[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		float[] result = new float[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static int[] remove(int[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		int[] result = new int[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static int[] removeElement(int[] array, int element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		
		return removeElement(remove(array, index), element);
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static int[] removeSubArray(int[] array, int[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		int[] result = new int[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static long[] remove(long[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		long[] result = new long[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static long[] removeElement(long[] array, long element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		
		return removeElement(remove(array, index), element);
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static long[] removeSubArray(long[] array, long[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		long[] result = new long[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static short[] remove(short[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		short[] result = new short[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static short[] removeElement(short[] array, short element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		
		return removeElement(remove(array, index), element);
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static short[] removeSubArray(short[] array, short[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		short[] result = new short[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 删除数组中指定索引位的元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static Object[] remove(Object[] array, int index) {
		int length;
		if (isEmpty(array) || index < 0 || index > (length = array.length - 1))
			return array;
		
		Object[] result = new Object[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, index + 1, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index + 1);
				System.arraycopy(array, index + 1, result, index, result.length - index);
			}
		}
		return result;
	}
	
	/**
	 * 删除数组中所有指定的子元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param element
	 * @param start
	 * @return
	 */
	public static Object[] removeElement(Object[] array, Object element) {
		int index = indexOf(array, element);
		if (index < 0)
			return array;
		if (!isArray(element))
			return removeElement(remove(array, index), element);
		else 
			return removeSubArray(array, convertWapperArray(element));
	}
	
	/**
	 * 删除数组中所有指定的子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static Object[] removeSubArray(Object[] array, Object[] subArray) {
		int index = indexOf(array, subArray);
		if (index < 0)
			return array;
		
		int length = array.length - subArray.length;
		Object[] result = new Object[length];
		if (length != 0) {
			if (index == 0) 
				System.arraycopy(array, subArray.length, result, 0, result.length);
			else if (index == length)
				System.arraycopy(array, 0, result, 0, length);
			else {
				System.arraycopy(array, 0, result, 0, index);
				System.arraycopy(array, index + subArray.length, result, index, result.length - index);
			}
		}
		return removeSubArray(result, subArray);
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(boolean array[]) {
		int length = length(array);
		if (length > 1) {
			boolean temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(byte array[]) {
		int length = length(array);
		if (length > 1) {
			byte temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(char array[]) {
		int length = length(array);
		if (length > 1) {
			char temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(double array[]) {
		int length = length(array);
		if (length > 1) {
			double temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(float array[]) {
		int length = length(array);
		if (length > 1) {
			float temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(int array[]) {
		int length = length(array);
		if (length > 1) {
			int temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(long array[]) {
		int length = length(array);
		if (length > 1) {
			long temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(short array[]) {
		int length = length(array);
		if (length > 1) {
			short temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 倒置数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 */
	public static void reverse(Object array[]) {
		int length = length(array);
		if (length > 1) {
			Object temp;
			int reverseIndex;
			for (int i = 0; i < length / 2; i++) {
				reverseIndex = length - (i + 1);
				temp = array[i];
				array[i] = array[reverseIndex];
				array[reverseIndex] = temp;
			}
		}
	}
	
	/**
	 * 判断指定的值是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(boolean array[], boolean value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 判断指定的子数组是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean contains(boolean array[], boolean[] subArray) {
		return indexOf(array, subArray) != -1;
	}
	
	/**
	 * 判断指定的值是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(byte array[], byte value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 判断指定的值是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(char array[], char value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 判断指定的子数组是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean contains(char array[], char[] subArray) {
		return indexOf(array, subArray) != -1;
	}
	
	/**
	 * 判断指定的值是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(double array[], double value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 判断指定的子数组是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean contains(double array[], double[] subArray) {
		return indexOf(array, subArray) != -1;
	}
	
	/**
	 * 判断指定的值是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(float array[], float value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 判断指定的子数组是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean contains(float array[], float[] subArray) {
		return indexOf(array, subArray) != -1;
	}
	
	/**
	 * 判断指定的值是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(int array[], int value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 判断指定的子数组是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean contains(int array[], int[] subArray) {
		return indexOf(array, subArray) != -1;
	}
	
	/**
	 * 判断指定的值是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(long array[], long value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 判断指定的子数组是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean contains(long array[], long[] subArray) {
		return indexOf(array, subArray) != -1;
	}
	
	/**
	 * 判断指定的值是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(short array[], short value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 判断指定的子数组是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean contains(short array[], short[] subArray) {
		return indexOf(array, subArray) != -1;
	}
	
	/**
	 * 判断指定的对象是否存在于数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(Object array[], Object obj) {
		return indexOf(array, obj) != -1;
	}
	
	/**
	 * 判断指定的子数组是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param subArray
	 * @return
	 */
	public static boolean contains(Object array[], Object[] subArray) {
		return indexOf(array, subArray) != -1;
	}
	
	/**
	 * 判断指定的字符序列元素值是否存在于原数组中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param obj
	 * @return
	 */
	public static boolean contains(CharSequence array[], CharSequence value) {
		return indexOf(array, value) != -1;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static boolean[] nullToEmpty(boolean array[]) {
		return array == null ? new boolean[0] : array;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static byte[] nullToEmpty(byte array[]) {
		return array == null ? new byte[0] : array;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static char[] nullToEmpty(char array[]) {
		return array == null ? new char[0] : array;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static double[] nullToEmpty(double array[]) {
		return array == null ? new double[0] : array;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static float[] nullToEmpty(float array[]) {
		return array == null ? new float[0] : array;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static int[] nullToEmpty(int array[]) {
		return array == null ? new int[0] : array;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static long[] nullToEmpty(long array[]) {
		return array == null ? new long[0] : array;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static short[] nullToEmpty(short array[]) {
		return array == null ? new short[0] : array;
	}
	
	/**
	 * 将null转换为容量为0的空数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Object[] nullToEmpty(Object array[]) {
		return array == null ? new Object[0] : array;
	}
	
	/**
	 * 整理数组里所有的空元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static Object[] trimNull(Object array[]) {
		return removeElement(array, null);
	}
	
	/**
	 * 删除字符串数组里所有的空字符串元素，并且整理各元素左右两侧的空白字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static String[] rete(String array[]) {
		if (isEmpty(array))
			return array;
		
		for (int i = 0; i < array.length; i++) {
			if (StringUtils.isEmpty(array[i])) {
				Object[] temp = remove(array, i);
				array = new String[temp.length];
				System.arraycopy(temp, 0, array, 0, array.length);
				return rete(array);
			} else 
				array[i] = array[i].trim();
		}
		return array;
	}
	
	/**
	 * 删除字符串数组里所有的空白字符串元素，并且整理各元素左右两侧的空白字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static String[] rbte(String array[]) {
		if (isEmpty(array))
			return array;
		
		for (int i = 0; i < array.length; i++) {
			if (StringUtils.isBlank(array[i])) {
				Object[] temp = remove(array, i);
				array = new String[temp.length];
				System.arraycopy(temp, 0, array, 0, array.length);
				return rbte(array);
			} else 
				array[i] = array[i].trim();
		}
		return array;
	}
	
	/**
	 * 将数组转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @return
	 */
	public static String toString(Object[] array) {
		return join(array, ",");
	}
	
	/**
	 * 按指定的分隔符将数组中各元素连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(Object[] array, String separator) {
		if (isEmpty(array))
			return StringUtils.EMPTY_STRING;
		
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isNotEmpty(separator)) {
			
			int max = array.length - 1;
			for (int i = 0; i < max; i++)
				builder.append(array[i]).append(separator);
			
			builder.append(array[max]);
		} else {
			for (Object e : array) 
				builder.append(e);
		}
		return builder.toString();
	}
	
	/**
	 * 获取索引位对应的元素值，未获取到时默认返回null
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @return
	 */
	public static <T> T get(T[] array, int index) {
		return get(array, index, null);
	}
	
	/**
	 * 获取索引位对应的元素值，未获取到时返回指定的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param array
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public static <T> T get(T[] array, int index, T defaultValue) {
		int max = length(array) - 1;
		if (max == -1 || index < 0 || index > max)
			return defaultValue;
		
		return array[index];
	}
	
}
