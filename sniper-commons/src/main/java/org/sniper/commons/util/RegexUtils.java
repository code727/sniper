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
 * Create Date : 2014-12-22
 */

package org.sniper.commons.util;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sniper.commons.enums.logic.RegexEnum;

/**
 * 正则工具类
 * @author  Daniele
 * @version 1.0
 */
public abstract class RegexUtils {
	
	/** 表达式与模式关系映射集 */
	private static final Map<String, Pattern> PATTERNS = MapUtils.newConcurrentHashMap();
		
	/**
	 * 根据正则表达式获取对应的模式对象
	 * @author Daniele 
	 * @param regex
	 * @return
	 */
	public static Pattern getPattern(String regex) {
		Pattern pattern = PATTERNS.get(regex);
		
		if (pattern == null) {
			synchronized (PATTERNS) {
				if ((pattern = PATTERNS.get(regex)) == null) {
					pattern = Pattern.compile(regex);
					PATTERNS.put(regex, pattern);
				}
			}
		}
		
		return pattern;
	}
			
	/**
	 * 根据字符串和表达式创建Matcher对象
	 * @author Daniele 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static Matcher createMatcher(String str, String regex) {
		return getPattern(regex).matcher(str);
	}
	
	/**
	 * 判断字符串是否完全匹配表达式
	 * @author Daniele 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean is(String str, String regex) {
		if (str == null || regex == null)
			return str == null && regex == null;
		
		return createMatcher(str, regex).matches();
	}
	
	/**
	 * 判断字符串是否有匹配表达式的部分
	 * @author Daniele 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean has(String str, String regex) {
		if (str == null || regex == null)
			return str == null && regex == null;
		
		return createMatcher(str, regex).find();
	}
	
	/**
	 * 判断字符串是否为整数
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		return is(str, RegexEnum.INTEGER.getExpression());
	}
	
	/**
	 * 判断字符串中是否有整数
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasInteger(String str) {
		return has(str, RegexEnum.INTEGER.getExpression());
	}
		
	/**
	 * 判断字符串是否为小数
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {
		return is(str, RegexEnum.DECIMAL.getExpression());
	}
	
	/**
	 * 判断字符串中是否有小数
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasDecimal(String str) {
		return has(str, RegexEnum.DECIMAL.getExpression());
	}
	
	/**
	 * 判断字符串是否为数字
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return is(str, RegexEnum.NUMBER.getExpression());
	}
	
	/**
	 * 判断字符串中是否有数字
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasNumber(String str) {
		return has(str, RegexEnum.NUMBER.getExpression());
	}
	
	/**
	 * 判断字符串是否为IP
	 * @author Daniele
	 * @param str
	 * @return
	 */
	public static boolean isIP(String str) {
		return isIPV4(str) || isIPV6(str);
	}
	
	/**
	 * 判断字符串中是否有IP
	 * @author Daniele
	 * @param str
	 * @return
	 */
	public static boolean hasIP(String str) {
		return hasIPV4(str) || hasIPV6(str);
	}
	
	/**
	 * 判断字符串是否为IPV4
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isIPV4(String str) {
		return is(str, RegexEnum.IPV4.getExpression());
	}
	
	/**
	 * 判断字符串中是否有IPV4
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasIPV4(String str) {
		return has(str, RegexEnum.IPV4.getExpression());
	}
	
	/**
	 * 判断字符串是否为IPV6
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isIPV6(String str) {
		return is(str, RegexEnum.IPV6.getExpression());
	}
	
	/**
	 * 判断字符串中是否有IPV6
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasIPV6(String str) {
		return has(str, RegexEnum.IPV6.getExpression());
	}
	
	/**
	 * 判断字符串是否为Email
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		return is(str, RegexEnum.EMAIL.getExpression());
	}
	
	/**
	 * 判断字符串中是否有Email
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasEmail(String str) {
		return has(str, RegexEnum.EMAIL.getExpression());
	}
	
	/**
	 * 判断字符串是否为移动电话号码
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		return is(str, RegexEnum.MOBILE.getExpression());
	}
	
	/**
	 * 判断字符串中是否有移动电话号码
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasMobile(String str) {
		return has(str, RegexEnum.MOBILE.getExpression());
	}
	
	/**
	 * 判断字符串是否全由ASCII字符组成
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isAscii(String str) {
		return is(str, RegexEnum.ASCII.getExpression());
	}
	
	/**
	 * 判断字符串中有是否有ASCII字符
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasAscii(String str) {
		return has(str, RegexEnum.ASCII.getExpression());
	}
	
	/**
	 * 判断字符串是否全由非ASCII的双字节字符组成
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isDoubleByte(String str) {
		return is(str, RegexEnum.DOUBLE_BYTE.getExpression());
	}
	
	/**
	 * 判断字符串是否有非ASCII的双字节字符
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasDoubleByte(String str) {
		return has(str, RegexEnum.DOUBLE_BYTE.getExpression());
	}
		
	/**
	 * 判断字符串是否全由中文字符组成
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		return is(str, RegexEnum.CHINESE.getExpression());
	}
	
	/**
	 * 判断字符串是否有中文字符
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasChinese(String str) {
		return has(str, RegexEnum.CHINESE.getExpression());
	}
	
	/**
	 * 判断是否为URL查询字符串
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isURLQueryString(String str) {
		return is(str, RegexEnum.URL_QUERY_STRING.getExpression());
	}
	
	/**
	 * 判断字符串中是否有URL查询字符串
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasURLQueryString(String str) {
		return has(str, RegexEnum.URL_QUERY_STRING.getExpression());
	}
	
	/**
	 * 判断是否为URL字符串
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isURL(String str) {
		return is(str, RegexEnum.URL.getExpression());
	}
	
	/**
	 * 判断字符串中是否包含URL
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasURL(String str) {
		return has(str, RegexEnum.URL.getExpression());
	}
	
	/**
	 * 判断字符串是否为java.text.MessageFormat默认支持的占位符
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isMessageFormatPlaceholder(String str) {
		return is(str, RegexEnum.MESSAGE_FORMAT_PLACEHOLDER.getExpression());
	}
	
	/**
	 * 判断字符串是否包含java.text.MessageFormat默认支持的占位符
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasMessageFormatPlaceholder(String str) {
		return has(str, RegexEnum.MESSAGE_FORMAT_PLACEHOLDER.getExpression());
	}
	
	/**
	 * 判断字符串是否为默认支持的格式化占位符
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean isStringFormatPlaceholder(String str) {
		return is(str, RegexEnum.STRING_FORMAT_PLACEHOLDER.getExpression());
	}
	
	/**
	 * 判断字符串是否包含默认支持的格式化占位符
	 * @author Daniele 
	 * @param str
	 * @return
	 */
	public static boolean hasStringFormatPlaceholder(String str) {
		return has(str, RegexEnum.STRING_FORMAT_PLACEHOLDER.getExpression());
	}
		
	/**
	 * 从源字符串中提取匹配表达式的子串集
	 * @author Daniele 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static Set<String> matches(String str, String regex) {
		Matcher matcher = createMatcher(str, regex);
		Set<String> set = CollectionUtils.newHashSet();
		
		while (matcher.find()) {
			set.add(matcher.group());
		}
			
		return set;
	}
	
	/**
	 * 统一为匹配模式的子串添加前缀和后缀
	 * @author Daniele 
	 * @param str
	 * @param regex
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String matchesAppend(String str, String regex, String prefix, String suffix) {
		if (str == null || regex == null)
			return prefix + str + suffix;
		
		Matcher matcher = createMatcher(str, regex);
		
		int beginIndex;
		int endIndex;
		int offset;
		int length = (prefix != null ? prefix.length() : 4) + (suffix != null ? suffix.length() : 4);
		for (int count = 0; matcher.find();) {
			/* 替换文本的起始和结束索引偏移量为前后缀文本长度的第count次循环倍数 */
			offset = length * (count++);
			beginIndex = matcher.start() + offset;
			endIndex = matcher.end() + offset;
			
			str = StringUtils.replace(str, prefix + matcher.group() + suffix, beginIndex, endIndex);
		}
		
		return str;
	}
	
	/**
	 * 从源字符串中提取匹配表达式的起始位置和子串映射集
	 * @author Daniele 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static Map<Integer, String> matchedMap(String str, String regex) {
		Matcher matcher = createMatcher(str, regex);
		Map<Integer, String> matchedMap = MapUtils.newLinkedHashMap();
		
		while (matcher.find()) {
			matchedMap.put(matcher.start(), matcher.group());
		}
			
		return matchedMap;
	}
	
	/**
	 * 统计源字符串中匹配表达式的子串个数
	 * @author Daniele 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static int matchCount(String str, String regex) {
		Matcher matcher = createMatcher(str, regex);
		int count = 0;
		
		while (matcher.find()) {
			count++;
		}
		
		return count;
	}
		
}
