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
 * Create Date : 2014-7-11
 */

package org.workin.commons.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * 字符串工具类
 * @author <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0.0
 */
public class StringUtils {
	
	/** null字符串 */
	public static final String NULL_STRING = "null";

	/** 空字符串 */
	public static final String EMPTY_STRING = "";

	/** 空格字符串 */
	public static final String SPACE_STRING = " ";
	
	public static final String CLASSPATH = "classpath:";
	
	/**
	 * 判断是否为空字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 判断是否不为空字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断是否全为空白字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int length;
		if (str == null || (length = str.length()) == 0)
            return true;
		
		for (int i = 0; i < length; i++)
			if (!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
	}

	/**
	 * 判断是否不全为空白字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
				
	/**
	 * 检索第一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static int indexOf(String str, String mark) {
		return indexOf(str, mark, 0, false);
	}
	
	/**
	 * 从指定的起始位开始检索第一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static int indexOf(String str, String mark, int start) {
		return indexOf(str, mark, start, false);
	}
	
	/**
	 * 按照忽略大小写的方式检索第一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static int indexOfIgnoreCase(String str, String mark) {
		return indexOf(str, mark, 0, true);
	}
	
	/**
	 * 按照忽略大小写的方式从指定的起始位开始检索第一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static int indexOfIgnoreCase(String str, String mark, int start) {
		return indexOf(str, mark, start, true);
	}
	
	/**
	 * 选择是否按照忽略大小写的方式检索第一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return
	 */
	public static int indexOf(String str, String mark, boolean ignoreCase) {
		return indexOf(str, mark, 0, ignoreCase);
	}
	
	/**
	 * 选择是否按照忽略大小写的方式从指定的起始位开始检索第一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @param ignoreCase
	 * @return 
	 */
	public static int indexOf(String str, String mark, int start, boolean ignoreCase) {
		if (str == null || mark == null)
			return -1;
		
		/* 以判断是否忽略大小写进行不同的实现，因为两种方式存在性能差异 */
		if (ignoreCase) {
			if (start < 0)
				start = 0;
				
			int markLength = mark.length();
			// 最大检索次数
			int maxSearchCount = str.length() - markLength + 1;
			if (start > maxSearchCount)
				return -1;

			if (markLength == 0)
				return start;

			for (int i = start; i < maxSearchCount; i++)
				if (str.regionMatches(ignoreCase, i, mark, 0, markLength))
					return i;
			
			return -1;
		} else 
			return str.indexOf(mark, start);
	}
	
	/**
	 * 检索最后一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static int lastIndexOf(String str, String mark) {
		return lastIndexOf(str, mark, length(str), false);
	}
	
	/**
	 * 从指定的起始位开始检索最后一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static int lastIndexOf(String str, String mark, int start) {
		return lastIndexOf(str, mark, start, false);
	}
	
	/**
	 * 按照忽略大小写的方式检索最后一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static int lastIndexOfIgnoreCase(String str, String mark) {
		return lastIndexOf(str, mark, length(str), true);
	}
	
	/**
	 * 按照忽略大小写的方式从指定的起始位开始检索最后一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @return 
	 */
	public static int lastIndexOfIgnoreCase(String str, String mark, int start) {
		return lastIndexOf(str, mark, start, true);
	}
	
	/**
	 * 选择是否按照忽略大小写的方式检索最后一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return
	 */
	public static int lastIndexOf(String str, String mark, boolean ignoreCase) {
		return lastIndexOf(str, mark, length(str), ignoreCase);
	}
	
	/**
	 * 选择是否按照忽略大小写的方式从指定的起始位开始检索最后一个标记在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @param ignoreCase
	 * @return 
	 */
	public static int lastIndexOf(String str, String mark, int start, boolean ignoreCase) {
		if (str == null || mark == null)
			return -1;
		
		/* 以判断是否忽略大小写进行不同的实现，因为两种方式存在性能差异 */
		if (ignoreCase) {
			int length = str.length();
			int markLength = mark.length();
			// 开始检索的最大索引值
			if (start > length - markLength)
				start = length - markLength;

			if (start < 0)
				return -1;

			if (markLength == 0) 
				return start;

			// 从最大索引处开始反向检索
			for (int i = start; i > -1; i--)
				if (str.regionMatches(ignoreCase, i, mark, 0, markLength))
					return i;

			return -1;
		} else
			return str.lastIndexOf(mark, start);
	}
	
	/**
	 * 判断字符串是否以指定的前缀开头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param prefix
	 * @return 
	 */
	public static boolean startsWith(String str, String prefix) {
		return startsWith(str, prefix, false);
	}
	
	/**
	 * 按忽略大小写的方式判断字符串是否以指定的前缀开头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param prefix
	 * @return 
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return startsWith(str, prefix, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式判断字符串是否以指定的前缀开头
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param prefix
	 * @param ignoreCase
	 * @return 
	 */
	public static boolean startsWith(String str, String prefix, boolean ignoreCase) {
		if (str == null || prefix == null)
			return str == null && prefix == null;
		
		int prefixLength;
		if ((prefixLength = prefix.length()) > str.length())
			return false;
		
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefixLength);
	}
	
	/**
	 * 判断字符串是否以指定的后缀结束
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param suffix
	 * @return 
	 */
	public static boolean endsWith(String str, String suffix) {
		return endsWith(str, suffix, false);
	}
	
	/**
	 * 按忽略大小写的方式判断字符串是否以指定的后缀结束
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param prefix
	 * @param ignoreCase
	 * @return 
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		return endsWith(str, suffix, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式判断字符串是否以指定的后缀结束
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param prefix
	 * @param ignoreCase
	 * @return 
	 */
	public static boolean endsWith(String str, String suffix, boolean ignoreCase) {
		if (str == null || suffix == null)
			return str == null && suffix == null;
		
		int suffixLength;
		int length;
		if ((suffixLength = suffix.length()) > (length = str.length()))
			return false;
		
		return str.regionMatches(ignoreCase, length - suffixLength, suffix, 0, suffixLength);
	}
		
	/**
	 * 获取后缀在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param suffix
	 * @return 
	 */
	public static int indexOfSuffix(String str, String suffix) {
		return indexOfSuffix(str, suffix, false);
	}
	
	/**
	 * 按忽略大小写的方式获取后缀在字符串中的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param suffix
	 * @return 
	 */
	public static int indexOfSuffixIgnoreCase(String str, String suffix) {
		return indexOfSuffix(str, suffix, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式获取指定后缀的索引位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param prefix
	 * @param ignoreCase
	 * @return 
	 */
	public static int indexOfSuffix(String str, String suffix, boolean ignoreCase) {
		if (str == null || suffix == null)
			return -1;
		
		int suffixLength;
		int offset = str.length() - (suffixLength = suffix.length());
		if (offset < 0)	
			return -1;
		
		return str.regionMatches(ignoreCase, offset, suffix, 0, suffixLength) ? offset : -1;
	}
	
	/**
	 * 字符串首字符大写
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
		return firstCharacterCase(str, true);
	}

	/**
	 * 字符串首字符小写
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @return
	 */
	public static String uncapitalize(String str) {
		return firstCharacterCase(str, false);
	}

	/**
	 * 字符串首字符大/小写
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param capitalize
	 * @return
	 */
	private static String firstCharacterCase(String str, boolean capitalize) {
		if (isBlank(str))
			return str;

		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize)
			buf.append(Character.toUpperCase(str.charAt(0)));
		else
			buf.append(Character.toLowerCase(str.charAt(0)));

		buf.append(str.substring(1));
		return buf.toString();
	}

	/**
	 * 返回不为null的字符，null返回空字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static String safeString(String str) {
		return str != null ? str : EMPTY_STRING ;
	}
	
	/**
	 * 去掉字符串左右两端的空白字符，null返回空字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return 
	 */
	public static String trimToEmpty(String str) {
		return safeString(trim(str));
	}
	
	/**
	 * 去掉字符串左右两端的空白字符，null不作处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return 
	 */
	public static String trim(String str) {
		return str != null ? str.trim() : str ;
	}
	
	/**
	 * 清空字符串中所有的空格、制表符和换页符等空白字符，null返回空字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @return
	 */
	public static String trimAllToEmpty(String str) {
		return safeString(trimAll(str));
	}
		
	/**
	 * 清空字符串中所有的空格、制表符和换页符等空白字符，null不作处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return 
	 */
	public static String trimAll(String str) {
		if (str == null)
			return str;
		
		StringTokenizer tokenizer = new StringTokenizer(str);
		StringBuilder result = new StringBuilder();
		while (tokenizer.hasMoreTokens())
			result.append(tokenizer.nextElement());
		return result.toString();
	}
	
	/**
	 * 获取前缀之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static String afterPrefix(String str, String prefix) {
		return afterPrefix(str, prefix, false);
	}
	
	/**
	 * 按忽略大小写的方式获取前缀之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static String afterPrefixIgnoreCase(String str, String prefix) {
		return afterPrefix(str, prefix, true);
	}
	
	/**
	 * 选择是否按忽略大小写方式获取前缀之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param prefix
	 * @param ignoreCase
	 * @return 
	 */
	public static String afterPrefix(String str, String prefix, boolean ignoreCase) {		
		if (isEmpty(str) || isEmpty(prefix))
			return str;
		
		return indexOf(str, prefix, 0, ignoreCase) == 0 ? str.substring(prefix.length()) : EMPTY_STRING;
	}
	
	/**
	 * 获取第一个标记之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String afterFrist(String str, String mark) {
		return afterFrist(str, mark, false);
	}
	
	/**
	 * 按忽略大小写的方式获取第一个标记之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static String afterFristIgnoreCase(String str, String mark) {
		return afterFrist(str, mark, true);
	}
	
	/**
	 * 选择是否按按忽略大小写的方式获取第一个标记之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return 
	 */
	public static String afterFrist(String str, String mark, boolean ignoreCase) {
		if (isEmpty(str) || isEmpty(mark))
			return str;
		
		int index = indexOf(str, mark, 0, ignoreCase);
		return index > -1 ? str.substring(index + mark.length()) : EMPTY_STRING;
	}
	
	/**
	 * 获取最后一个标记之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String afterLast(String str, String mark) {
		return afterLast(str, mark, false);
	}
	
	/**
	 * 按忽略大小写的方式获取最后一个标记之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String afterLastIgnoreCase(String str, String mark) {
		return afterLast(str, mark, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式获取最后一个标记之后的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str 开始标记
	 * @param mark 结束标记
	 * @param ignoreCase
	 * @return 
	 */
	public static String afterLast(String str, String mark, boolean ignoreCase) {
		if (str == null)
			return str;
		
		if (str.length() == 0 || isEmpty(mark))
			return EMPTY_STRING;
		
		int index = lastIndexOf(str, mark, ignoreCase);
		return index > -1 ? str.substring(index + mark.length()) : EMPTY_STRING;
	}
	
	/**
	 * 获取后缀之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param suffix
	 * @return
	 */
	public static String beforeSuffix(String str, String suffix) {
		return beforeSuffix(str, suffix, false);
	}
	
	/**
	 * 按忽略大小写方式获取后缀之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param suffix
	 * @return
	 
	 */
	public static String beforeSuffixIgnoreCase(String str, String suffix) {
		return beforeSuffix(str, suffix, true);
	}
	
	/**
	 * 选择是否按忽略大小写方式获取后缀之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param suffix
	 * @param ignoreCase
	 * @return 
	 */
	public static String beforeSuffix(String str, String suffix, boolean ignoreCase) {
		if (isEmpty(str) || isEmpty(suffix))
			return str;
		
		int suffixIndex = indexOfSuffix(str, suffix, ignoreCase);
		return suffixIndex > -1 ? str.substring(0, suffixIndex) : EMPTY_STRING;
	}

	/**
	 * 获取第一个标记之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param mark
	 * @return
	 
	 */
	public static String beforeFrist(String str, String mark) {
		return beforeFrist(str, mark, false);
	}
	
	/**
	 * 按忽略大小写的方式获取第一个标记之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param part
	 * @return
	 */
	public static String beforeFristIgnoreCase(String str, String mark) {
		return beforeFrist(str, mark, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式获取第一个标记之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return 
	 */
	public static String beforeFrist(String str, String mark, boolean ignoreCase) {
		if (str == null)
			return str;
		
		if (str.length() == 0 || isEmpty(mark))
			return EMPTY_STRING;
				
		int index = indexOf(str, mark, 0, ignoreCase);
		return index > 0 ? str.substring(0, index) : EMPTY_STRING;
	}

	/**
	 * 获取最后一个标记之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String beforeLast(String str, String mark) {
		return beforeLast(str, mark, false);
	}
	
	/**
	 * 按忽略大小写的方式获取最后一个标记之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String beforeLastIgnoreCase(String str, String mark) {
		return beforeLast(str, mark, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式获取最后一个标记之前的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return 
	 */
	public static String beforeLast(String str, String mark, boolean ignoreCase) {
		if (isEmpty(str) || isEmpty(mark))
			return str;
		
		int index = lastIndexOf(str, mark, ignoreCase);
		return index > 0 ? str.substring(0, index) : EMPTY_STRING;
	}

	/**
	 * 从左至右截取起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @return
	 */
	public static String leftSubstring(String str, String start, String end) {		
		return leftSubstring(str, start, end, false);
	}
	
	/**
	 * 按忽略大小写的方式，从左至右截取起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @return
	 */
	public static String leftSubstringIgnoreCase(String str, String start, String end) {
		return leftSubstring(str, start, end, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式从左至右截取起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @param ignoreCase
	 * @return
	 */
	public static String leftSubstring(String str, String start, String end, boolean ignoreCase) {
		if (isEmpty(start) || isEmpty(end))
			return str == null ? str : EMPTY_STRING;
		
		int startIndex = indexOf(str, start, 0, ignoreCase);
		if (startIndex > -1) {
			/* 从start标记处开始查找end标记 */
			startIndex = startIndex + start.length();
			int endIndex = indexOfIgnoreCase(str, end, startIndex);
			
			if (endIndex > -1)
				return str.substring(startIndex, endIndex);
		} 
		return null;
	}
	
	/**
	 * 从左至右截取所有起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @return
	 */
	public static List<String> leftSubstringAll(String str, String start, String end) {
		return leftSubstringAll(str, start, end, false);
	}
	
	/**
	 * 按忽略大小写的方式，从左至右截取所有起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @return
	 */
	public static List<String> leftSubstringAllIgnoreCase(String str, String start, String end) {
		return leftSubstringAll(str, start, end, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式从左至右截取所有起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @param ignoreCase
	 * @return
	 */
	public static List<String> leftSubstringAll(String str, String start, String end, boolean ignoreCase) {
		List<String> list = CollectionUtils.newLinkedList();
		if (isBlank(start) || isBlank(end))
			return list;
		
		int startIndex = 0;
		int endIndex;
		int startLength = start.length();
		do {
			startIndex = indexOf(str, start, startIndex, ignoreCase);
			if (startIndex > -1) {
				/* 从start标记处开始查找end标记 */
				startIndex = startIndex + startLength;
				endIndex = indexOf(str, end, startIndex, ignoreCase);
				
				if (endIndex > -1) 
					list.add(str.substring(startIndex, endIndex));
				else
					break;
			} else
				break;
		} while (true);
		
		return list;
	}
	
	/**
	 * 从右至左截取起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @return
	 */
	public static String rightSubstring(String str, String start, String end) {
		return rightSubstring(str, start, end, false);
	}
	
	/**
	 * 按忽略大小写的方式从右至左截取起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @return
	 */
	public static String rightSubstringIgnoreCase(String str, String start, String end) {
		return rightSubstring(str, start, end, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式从右至左截取起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @param ignoreCase
	 * @return
	 */
	public static String rightSubstring(String str, String start, String end, boolean ignoreCase) {
		if (isEmpty(start) || isEmpty(end))
			return str == null ? str : EMPTY_STRING;
		
		int endIndex = lastIndexOf(str, end, ignoreCase);
		if (endIndex > -1) {
			// 从end标记处开始查询start标记
			int startIndex = lastIndexOf(str, start, endIndex, ignoreCase);
			if (startIndex > -1) 
				return str.substring(startIndex + start.length(), endIndex);
		}
		return null;
	}
	
	/**
	 * 从右至左截取所有起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @return
	 */
	public static List<String> rightSubstringAll(String str, String start, String end) {
		return rightSubstringAll(str, start, end, false);
	}
	
	/**
	 * 按忽略大小写的方式从右至左截取所有起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @return
	 */
	public static List<String> rightSubstringAllIgnoreCase(String str, String start, String end) {
		return rightSubstringAll(str, start, end, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式从右至左截取所有起始字符串之间的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param start 开始标记
	 * @param end 结束标记
	 * @param ignoreCase
	 * @return
	 */
	public static List<String> rightSubstringAll(String str, String start, String end, boolean ignoreCase) {
		List<String> list = CollectionUtils.newLinkedList();
		if (isBlank(start) || isBlank(end))
			return list;
		
		int startIndex = length(str);
		int endIndex;
		int startLength = start.length();
		do {
			endIndex = lastIndexOf(str, end, startIndex, ignoreCase);
			if (endIndex > -1) {
				startIndex = lastIndexOf(str, start, endIndex, ignoreCase);
				if (startIndex > -1) 
					list.add(str.substring(startIndex + startLength, endIndex));
				else
					break;
			} else
				break;
		} while (true);
		
		return list;
	}
	
	
	/**
	 * 将字符串中的第一个标记替换成指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param subStr
	 * @return
	 */
	public static String replaceFirst(String str, String mark, String subStr) {
		return replaceFirst(str, mark, subStr, false);
	}
	
	/**
	 * 按照忽略大小写的方式将字符串中的第一个标记替换成指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param subStr
	 * @return
	 */
	public static String replaceFirstIgnoreCase(String str, String mark, String subStr) {
		return replaceFirst(str, mark, subStr, true);
	}
	
	/**
	 * 选择是否按照忽略大小写的方式将字符串中的第一个标记替换成指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param subStr
	 * @param ignoreCase
	 * @return
	 */
	public static String replaceFirst(String str, String mark, String subStr, boolean ignoreCase) {
		if (str == null)
			return subStr;
		
		if (mark == null)
			return str;
		
		int start = 0;
		int markPos;
		int markLength = mark.length();
		StringBuilder result = new StringBuilder();
		if (markLength > 0) {
			if ((markPos = indexOf(str, mark, start, ignoreCase)) > -1) {
				result.append(str.substring(start, markPos)).append(subStr); 
				start = markPos + markLength;
			}
			result.append(str.substring(start));
		} else {
			/* 将第一个字符替换成子串后再拼接其后所有的原内容 */
			result.append(subStr);
			result.append(str.substring(1));
		}
		return result.toString();
	}
	
	/**
	 * 将字符串中的所有标记替换成指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param mark
	 * @param subStr
	 * @return
	 */
	public static String replaceAll(String str, String mark, String subStr) {
		return replaceAll(str, mark, subStr, false);
	}
	
	/**
	 * 按照忽略大小写的方式将字符串中的所有标记替换成指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param subStr
	 * @return 
	 */
	public static String replaceAllIgnoreCase(String str, String mark, String subStr) {
		return replaceAll(str, mark, subStr, true);
	}
	
	/**
	 * 选择是否按照忽略大小写的方式将字符串中的所有标记替换成指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param subStr
	 * @param ignoreCase
	 * @return 
	 */
	public static String replaceAll(String str, String mark, String subStr, boolean ignoreCase) {
		if (str == null)
			return subStr;
		
		if (mark == null)
			return str;
		
		int start = 0;
		int markPos;
		int markLength = mark.length();
		StringBuilder result = new StringBuilder();
		if (markLength > 0) {
			while ((markPos = indexOf(str, mark, start, ignoreCase)) > -1) {
				result.append(str.substring(start, markPos)).append(subStr); 
				start = markPos + markLength;
			}
			result.append(str.substring(start));
		} else {
			/* 替换成length个子串 */
			int length = str.length();
			for (int i = 0; i < length; i++)
				result.append(subStr);
		}
		return result.toString();
	}
	
	/**
	 * 将开始索引位之后的所有字符串替换成子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param subStr
	 * @param beginIndex
	 * @return
	 */
	public static String replace(String str, String subStr, int beginIndex) {
		return replace(str, subStr, beginIndex, length(str));
	}
	
	/**
	 * 将开始索引和结束索引位之间的所有字符串替换成子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param subStr
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static String replace(String str, String subStr, int beginIndex, int endIndex) {
		if (str == null)
			return subStr;
		
		beginIndex = NumberUtils.minLimit(beginIndex, 0);
		endIndex = NumberUtils.maxLimit(endIndex, str.length());
		if (beginIndex > endIndex) {
			int index = beginIndex;
			beginIndex = endIndex;
			endIndex = index;
		}
		
		StringBuilder result = new StringBuilder();
		if (beginIndex > 0) 
			// 未从头开始，则将头到beginIndex之间的字符串拼接上
			result.append(str.substring(0, beginIndex));
		
		result.append(subStr);
		
		int length = str.length();
		if (endIndex < length) 
			// 未在末尾结束，则将endIndex到末尾之间的字符串拼接上
			result.append(str.substring(endIndex, length));
		
		return result.toString();
	}
	
	/**
	 * 将字符串中连续多个空格、制表符和换页符等空白字符整理替换成一个空格。
	 * 				而字符串左右两侧的空白字符则直接清除。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @return
	 */
	public static String tirmSingleSpace(String str) {
		if (isEmpty(str))
			return str;
		
		StringTokenizer tokenizer = new StringTokenizer(str);
		StringBuilder builder = new StringBuilder();
		if (tokenizer.hasMoreElements()) {
			builder.append(tokenizer.nextToken());
			while (tokenizer.hasMoreElements()) {
				builder.append(SPACE_STRING);
				builder.append(tokenizer.nextToken());
			}
		}
		return builder.toString();
	}

	/**
	 * 生成32位UUID
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @return
	 */
	public static String UUID() {
		return UUID(false);
	}
	
	/**
	 * 生成32位UUID，并指定是否转换为全大写
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param upperCase
	 * @return
	 */
	public static String UUID(boolean upperCase) {
		String uuid = UUID.randomUUID().toString();
		return upperCase ? uuid.toUpperCase() : uuid;
	}
	
	/**
	 * 生成32位无符号UUID
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static String unsignedUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 生成32位无符号UUID，并指定是否转换为全大写
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param upperCase
	 * @return
	 */
	public static String unsignedUUID(boolean upperCase) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return upperCase ? uuid.toUpperCase() : uuid;
	}
		
	/**
	 * 判断指定的标记是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static boolean contains(String str, String mark) {
		return contains(str, mark, false);
	}
	
	/**
	 * 以忽略大小写的方式来判断指定的标记是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static boolean containsIgnoreCase(String str, String mark) {
		return contains(str, mark, true);
	}
	
	/**
	 * 选择是否按照忽略大小写的方式来判断指定的标记是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return
	 */
	public static boolean contains(String str, String mark, boolean ignoreCase) {
		return indexOf(str, mark, ignoreCase) > -1;
	}

	/**
	 * 统计出字符串中指定标记的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param str
	 * @param mark
	 * @return
	 */
	public static int count(String str, String mark) {
		return count(str, mark, false);
	}
	
	/**
	 * 按忽略大小写的方式统计出字符串中指定标记的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static int countIgnoreCase(String str, String mark) {
		return count(str, mark, true);
	}
	
	/**
	 * 选择是否忽略大小写的方式统计出字符串中指定标记的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return 
	 */	
	public static int count(String str, String mark, boolean ignoreCase) {
		if (str == null || mark == null || mark.length() > str.length())
			return 0;
		
		// 标记为空字符串时，直接返回被检索字符串长度
		if (mark.length() == 0) {
			int length;
			if ((length = str.length()) == 0)
				return 1;
			return length;
		}
		
		int start = 0;
		int count = 0;
		while((start = indexOf(str, mark, start, ignoreCase)) > -1) {
			count++;
			start++;
		}
		return count;
	}
	
	/**
	 * 删除字符串中指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static String delete(String str, String mark) {
		return delete(str, mark, false);
	}
	
	/**
	 * 按忽略大小写的方式删除字符串中指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static String deleteIgnoreCase(String str, String mark) {
		return delete(str, mark, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式除字符串中指定的子串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return 
	 */
	public static String delete(String str, String mark, boolean ignoreCase) {
		return replaceAll(str, mark, EMPTY_STRING, ignoreCase);
	}
	
	/**
	 * 整理字符串最左侧的空白字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static String leftTrim(String str) {
		if (isEmpty(str))
			return str;
		
		char[] charArray = str.toCharArray();
		int length = charArray.length;
		int start = 0;
		while ((start < length) && (charArray[start] <= ' '))
			start++;
		
		return str.substring(start);
	}
	
	/**
	 * 整理字符串最右侧的空白字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static String rightTrim(String str) {
		if (isEmpty(str))
			return str;
		
		char[] charArray = str.toCharArray();
		int length = charArray.length;
		int start = 0;
		while ((start < length) && (charArray[length - 1] <= ' '))
			length--;
		
		return str.substring(0, length);
	}
	
	/**
	 * 将集合中的所有元素以指定的分隔符连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param collection
	 * @param seperator
	 * @return 
	 */
	public static String join(Collection<?> collection, String seperator) {
		if (CollectionUtils.isEmpty(collection))
			return EMPTY_STRING;
		
		StringBuilder result = new StringBuilder();
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext()) {
			result.append(iterator.next());
			if (iterator.hasNext())
				result.append(seperator);
		}
		return result.toString();
	}
	
	/**
	 * 将数组中的所有元素以指定的分隔符连接成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param arry
	 * @param seperator
	 * @return 
	 */
	public static String join(Object[] array, String seperator) {
		return join(array, seperator, 0, ArrayUtils.length(array));
	}
	
	/**
	 * 从指定的起始下标位开始，将数组中的元素以指定的分隔符连接成字符串
	 * @param array
	 * @param seperator
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static String join(Object[] array, String seperator, int startIndex, int endIndex) {
		if (ArrayUtils.isEmpty(array))
			return EMPTY_STRING;
		
		if (startIndex < 0)
			startIndex = 0;
		
		if (endIndex > array.length)
			endIndex = array.length;
		
		StringBuilder result = new StringBuilder();
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex)
				result.append(seperator);
			result.append(array[i]);
		}
		return result.toString();
	}
	
	/**
	 * 从原字符串中随机产生出指定长度的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String random(String str, int length) {
		if (isEmpty(str))
			return str;
		
		StringBuilder result = new StringBuilder();
		int index;
		for (int i = 0; i < length; i++) {
			index =  NumberUtils.randomIn(str.length());
			result.append(str.charAt(index));
		}
		return result.toString();
	}

	/** 
	 * 调用Object对象的toString()方法，为空时返回空字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return 
	 */
	public static String toString(Object value) {
		return toString(value, EMPTY_STRING);
	}
	
	/**
	 * 调用Object对象的toString()方法，为空时返回指定的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @param defaultStr
	 * @return
	 */
	public static String toString(Object value, String defaultStr) {
		return value != null ? value.toString() : defaultStr;
	}
	
	/**
	 * 获取字符串的长度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static int length(String str) {
		return str != null ? str.length() : 0;
	}
	
	/**
	 * 倒置字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static String reverse(String str) {
		return length(str) > 1 ? new StringBuffer(str).reverse().toString() : str;
	}
		
	/**
	 * 比较两个字符序列对象的值是否相等
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equals(CharSequence s1, CharSequence s2) {
		if (s1 == null || s2 == null)
			return s1 == s2;
		
		return s1.toString().equals(s2.toString());
	}
	
	/**
	 * 按忽略大小写的方式比较两个字符序列对象的值是否相等
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equalsIgnoreCase(CharSequence s1, CharSequence s2) {
		if (s1 == null || s2 == null)
			return s1 == s2;
		
		return s1.toString().equalsIgnoreCase(s2.toString());
	}
	
	/**
	 * 按指定标记将字符串分割成若干数组
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String[] split(String str, String mark) {
		return split(str, mark, 0);
	}
	
	/**
	 * 从指定位置开始用指定标记将字符串分割成若干数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @return
	 */
	public static String[] split(String str, String mark, int start) {
		return split(str, mark, start, false);
	}
	
	/**
	 * 以忽略大小写的方式将指定标记将字符串分割成若干数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String[] splitIgnoreCase(String str, String mark) {
		return splitIgnoreCase(str, mark, 0);
	}
	
	/**
	 * 从指定位置开始以忽略大小写的方式将指定标记将字符串分割成若干数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @return
	 */
	public static String[] splitIgnoreCase(String str, String mark, int start) {
		return split(str, mark, start, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式用指定标记将字符串分割成若干数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return
	 */
	public static String[] split(String str, String mark, boolean ignoreCase) {
		return split(str, mark, 0, ignoreCase);
	}
	
	/**
	 * 选择是否按忽略大小写的方式从指定位置开始用指定标记将字符串分割成若干数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @param ignoreCase
	 * @return
	 */
	public static String[] split(String str, String mark, int start, boolean ignoreCase) {
		if (str == null)
			return new String[0];
		
		if (mark == null)
			return new String[] { str };
		
		int index = 0;
		int length = str.length();
		int markLength = mark.length();
		
//		if (start >= length)
//			start = 0;
		
		List<String> list = CollectionUtils.newArrayList();
		if (markLength > 0) {
			while (true) {
				index = indexOf(str, mark, start, ignoreCase);
				if (index > -1) {
					list.add(str.substring(0, index));
					str = str.substring(index + markLength);
				} else {
					list.add(str);
					break;
				}
			}
		} else {
			for (int i = 0; i < length; i++)
				list.add(String.valueOf(str.charAt(i)));
		}
		
		return CollectionUtils.toArray(list, String.class);
	}
	
	/**
	 * 用指定标记将字符串分割成若干去空后的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String[] splitTrim(String str, String mark) {
		return splitTrim(str, mark, 0);
	}
	
	/**
	 * 从指定位置开始用指定标记将字符串分割成若干去空后的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @return
	 */
	public static String[] splitTrim(String str, String mark, int start) {
		return splitTrim(str, mark, 0, false);
	}
	
	/**
	 * 以忽略大小写的方式用指定标记将字符串分割成若干去空后的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static String[] splitTrimIgnoreCase(String str, String mark) {
		return splitTrimIgnoreCase(str, mark, 0);
	}
	
	/**
	 * 从指定位置开始以忽略大小写的方式用指定标记将字符串分割成若干去空后的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @return
	 */
	public static String[] splitTrimIgnoreCase(String str, String mark, int start) {
		return splitTrim(str, mark, start, true);
	}
	
	/**
	 * 选择是否按忽略大小写的方式用指定标记将字符串分割成若干去空后的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return 
	 */
	public static String[] splitTrim(String str, String mark, boolean ignoreCase) {
		return splitTrim(str, mark, 0, ignoreCase);
	}
	
	/**
	 * 选择是否按忽略大小写的方式从指定位置开始用指定标记将字符串分割成若干去空后的数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @param ignoreCase
	 * @return
	 */
	public static String[] splitTrim(String str, String mark, int start, boolean ignoreCase) {
		if (isBlank(str))
			return new String[0];
		
		if (mark == null)
			return new String[] { str };
		
		int index = 0;
		int length = str.length();
		int markLength = mark.length();
		
//		if (start >= length)
//			start = 0;
		
		List<String> list = CollectionUtils.newArrayList();
		if (markLength > 0) {
			String element = null;
			while (true) {
				index = indexOf(str, mark, start, ignoreCase);
				if (index > -1) {
					element = str.substring(0, index);
					if (isNotBlank(element))
						list.add(element);
					
					str = str.substring(index + markLength);
				} else {
					if (isNotBlank(str))
						list.add(str);
					
					break;
				}
			}
		} else {
			char c;
			for (int i = 0; i < length; i++) {
				c = str.charAt(i);
				if (c > ' ')
					list.add(String.valueOf(c));
			}
		}
		
		return CollectionUtils.toArray(list, String.class);
	}
	
	/**
	 * 判断字符串是否有指定的文本标记
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static boolean hasText(String str, String mark) {
		return hasText(str, mark, 0);
	}
	
	/**
	 * 从指定的索引位置开始判断字符串是否有指定的文本标记
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @return
	 */
	public static boolean hasText(String str, String mark, int start) {
		return hasText(str, mark, start, false);
	}
	
	/**
	 * 以忽略大小写的方式,判断字符串是否有指定的文本标记
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @return
	 */
	public static boolean hasTextIgnoreCase(String str, String mark) {
		return hasTextIgnoreCase(str, mark, 0);
	}
	
	/**
	 * 以忽略大小写的方式,从指定的索引位置开始判断字符串是否有指定的文本标记
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @return
	 */
	public static boolean hasTextIgnoreCase(String str, String mark, int start) {
		return hasText(str, mark, start, true);
	}
	
	/**
	 * 选择是否以忽略大小写的方式,判断字符串是否有指定的文本标记
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param ignoreCase
	 * @return
	 */
	public static boolean hasText(String str, String mark, boolean ignoreCase) {
		return hasText(str, mark, 0, ignoreCase);
	}
	
	/**
	 * 选择是否以忽略大小写的方式,从指定的索引位置开始判断字符串是否有指定的文本标记
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param mark
	 * @param start
	 * @param ignoreCase
	 * @return
	 */
	public static boolean hasText(String str, String mark, int start, boolean ignoreCase) {
		return indexOf(str, mark, start, ignoreCase) > -1;
	}
	
	/**
	 * 从原数字的左侧（开头）增补若干个字符0，生成具有指定最小长度的新字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param minLength
	 * @return
	 */
	public static String leftSupplement(Number number, int minLength) {
		return leftSupplement(number, '0', minLength);
	}
	
	/**
	 * 从原数字的左侧（开头）增补若干个指定的字符，生成具有指定最小长度的新字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param c
	 * @param minLength
	 * @return
	 */
	public static String leftSupplement(Number number, char c, int minLength) {
		return leftSupplement(number != null ? number.toString() : EMPTY_STRING, c, minLength);
	}
	
	/**
	 * 从原数字的右侧（尾部）增补若干个字符0，生成具有指定最小长度的新字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param minLength
	 * @return
	 */
	public static String rightSupplement(Number number, int minLength) {
		return rightSupplement(number, '0', minLength);
	}
	
	/**
	 * 从原数字的右侧（尾部）增补若干个指定的字符，生成具有指定最小长度的新字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @param c
	 * @param minLength
	 * @return
	 */
	public static String rightSupplement(Number number, char c, int minLength) {
		return rightSupplement(number != null ? number.toString() : EMPTY_STRING, c, minLength);
	}
	
	/**
	 * 从原字符串的左侧（开头）增补若干个指定的字符，生成具有指定最小长度的新字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param c
	 * @param minLength
	 * @return
	 */
	public static String leftSupplement(String str, char c, int minLength) {
		if (str == null)
			str = EMPTY_STRING;
		
		int length = str.length();
		if (length < minLength) {
			StringBuilder result = new StringBuilder();
			int count = minLength - length;
			for (int i = 0; i < count; i++)
				result.append(c);
			
			return result.append(str).toString();
		}
		
		return str;
	}
	
	/**
	 * 从原字符串的右侧（尾部）增补若干个指定的字符，生成具有指定最小长度的新字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param c
	 * @param minLength
	 * @return
	 */
	public static String rightSupplement(String str, char c, int minLength) {
		if (str == null)
			str = EMPTY_STRING;
		
		int length = str.length();
		if (length < minLength) {
			StringBuilder result = new StringBuilder(str);
			int count = minLength - length;
			for (int i = 0; i < count; i++)
				result.append(c);
			
			return result.toString();
		}
		
		return str;
	}
	
}
