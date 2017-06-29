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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RegexUtils {
	
	public static final Map<String, String> regex;
	
	/** 表达式与模式关系映射集线程局部变量 */
	private static final ThreadLocal<Map<String, Pattern>> patterns = new ThreadLocal<Map<String, Pattern>>();
	
	static {
		regex = new HashMap<String, String>();
		regex.put("integer", "[+|-]?\\d+");
		regex.put("decimal", "[+|-]?(\\d+\\.\\d+)");
		regex.put("number",  "[+|-]?(\\d+((\\.\\d+)|d*))");
		regex.put("ipv4",  "(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}");
		regex.put("ipv6",  "((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)");
		regex.put("email", "[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
		regex.put("mobile", "[1][3578]\\d{9}");
		regex.put("ascii", "[\u0000-\u007E]+");
		regex.put("double_byte", "[^\u0000-\u007E]+");
		regex.put("chinese", "[\u4E00-\u9FA5]+");
		regex.put("urlQueryString", "(\\w*=[^&]*&)*\\w*=\\w*");
		regex.put("url", "((h|H)(t|T)(t|T)(p|P)(s|S)?(://)|(w|W){3}[.]|(f|F)(t|T)(p|P)(://)+){1}(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)");
		
		// java.text.MessageFormat所默认支持的占位符表达式
		regex.put(MessageFormat.class.getName(), "(\\{\\d+\\})");
	}
	
	/**
	 * 根据正则表达式获取对应的模式对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param regex
	 * @return
	 */
	public static Pattern getPattern(String regex) {
		Map<String, Pattern> patternMap = patterns.get();
		if (patternMap == null)
			patternMap = MapUtils.newConcurrentHashMap();
		
		Pattern pattern = patternMap.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
			// 编译通过后存储此模式
			patternMap.put(regex, pattern);
			patterns.set(patternMap);
		}
		
		return patternMap.get(regex);
	}
		
	/**
	 * 根据字符串和表达式创建Matcher对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static Matcher createMatcher(String str, String regex) {
		return getPattern(regex).matcher(str);
	}
	
	/**
	 * 判断字符串是否完全匹配表达式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		return is(str, regex.get("integer"));
	}
	
	/**
	 * 判断字符串中是否有整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasInteger(String str) {
		return has(str, regex.get("integer"));
	}
	
	/**
	 * 判断字符串是否为小数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {
		return is(str, regex.get("decimal"));
	}
	
	/**
	 * 判断字符串中是否有小数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasDecimal(String str) {
		return has(str, regex.get("decimal"));
	}
	
	/**
	 * 判断字符串是否为数字
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return is(str, regex.get("number"));
	}
	
	/**
	 * 判断字符串中是否有数字
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasNumber(String str) {
		return has(str, regex.get("number"));
	}
	
	/**
	 * 判断字符串是否为IPV4
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isIPV4(String str) {
		return is(str, regex.get("ipv4"));
	}
	
	/**
	 * 判断字符串中是否有IPV4
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasIPV4(String str) {
		return has(str, regex.get("ipv4"));
	}
	
	/**
	 * 判断字符串是否为IPV6
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isIPV6(String str) {
		return is(str, regex.get("ipv6"));
	}
	
	/**
	 * 判断字符串中是否有IPV6
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasIPV6(String str) {
		return has(str, regex.get("ipv6"));
	}
	
	/**
	 * 判断字符串是否为Email
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		return is(str, regex.get("email"));
	}
	
	/**
	 * 判断字符串中是否有Email
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasEmail(String str) {
		return has(str, regex.get("email"));
	}
	
	/**
	 * 判断字符串是否为手机号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		return is(str, regex.get("mobile"));
	}
	
	/**
	 * 判断字符串中是否有手机号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasMobile(String str) {
		return has(str, regex.get("mobile"));
	}
	
	/**
	 * 判断是否为URL查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isURLQueryString(String str) {
		return is(str, regex.get("urlQueryString"));
	}
	
	/**
	 * 判断字符串中是否有URL查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasURLQueryString(String str) {
		return has(str, regex.get("urlQueryString"));
	}
	
	/**
	 * 判断字符串是否全为ASCII字符组成
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isAscii(String str) {
		return is(str, regex.get("ascii"));
	}
	
	/**
	 * 判断字符串中有是否有ASCII字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasAscii(String str) {
		return has(str, regex.get("ascii"));
	}
		
	/**
	 * 判断字符串是否全为中文组成
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		return is(str, regex.get("chinese"));
	}
	
	/**
	 * 判断字符串是否有中文字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasChinese(String str) {
		return has(str, regex.get("chinese"));
	}
	
	/**
	 * 判断字符串是否为非ASCII的双字节字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isDoubleByte(String str) {
		return is(str, regex.get("double_byte"));
	}
	
	/**
	 * 判断字符串是否有非ASCII的双字节字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasDoubleByte(String str) {
		return has(str, regex.get("double_byte"));
	}
	
	/**
	 * 判断是否为URL字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isURL(String str) {
		return is(str, regex.get("url"));
	}
	
	/**
	 * 判断字符串中是否包含URL
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasURL(String str) {
		return has(str, regex.get("url"));
	}
	
	/**
	 * 从源字符串中提取匹配表达式的子串集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static int matchCount(String str, String regex) {
		Matcher matcher = createMatcher(str, regex);
		int count = 0;
		while (matcher.find()) 
			count++;
		
		return count;
	}
		
}
