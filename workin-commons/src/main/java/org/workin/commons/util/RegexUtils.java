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

package org.workin.commons.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 正则工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RegexUtils {
	
	private static final Map<String, String> regex;
	
	static {
		regex = new HashMap<String, String>();
		regex.put("integer", "[+|-]?\\d+");
		regex.put("decimal", "[+|-]?(\\d+\\.\\d+)");
		regex.put("number",  "[+|-]?(\\d+((\\.\\d+)|d*))");
		regex.put("ipv4",  "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
		regex.put("ipv6",  "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
		regex.put("email", "[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
		regex.put("ascii", "[\u0000-\u007E]+");
		regex.put("double_byte", "[^\u0000-\u007E]+");
		regex.put("chinese", "[\u4E00-\u9FA5]+");
	}
	
	/**
	 * @description 根据字符串和表达式创建Matcher对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static Matcher createMatcher(String str, String regex) {
		return Pattern.compile(regex).matcher(str);
	}
	
	/**
	 * @description 判断字符串是否完全匹配表达式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean is(String str, String regex) {
		if (str == null || regex == null)
			return str == null && regex == null;
		
		return str.matches(regex);
	}
	
	/**
	 * @description 判断字符串是否有匹配表达式的部分
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
	 * @description 判断字符串是否为整数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		return is(str, regex.get("integer"));
	}
	
	/**
	 * @description 判断字符串是否为小数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {
		return is(str, regex.get("decimal"));
	}
	
	/**
	 * @description 判断字符串是否为数字
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return is(str, regex.get("number"));
	}
	
	/**
	 * @description 判断字符串是否为IPV4
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isIPV4(String str) {
		return is(str, regex.get("ipv4"));
	}
	
	/**
	 * @description 判断字符串是否为IPV6
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isIPV6(String str) {
		return is(str, regex.get("ipv6"));
	}
	
	/**
	 * @description 判断字符串是否满足Email格式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		return is(str, regex.get("email"));
	}
		
	/**
	 * @description 判断字符串是否有Ascii字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasAscii(String str) {
		return has(str, regex.get("ascii"));
	}
	
	/**
	 * @description 判断字符串是否有中文字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasChinese(String str) {
		return has(str, regex.get("chinese"));
	}
	
	/**
	 * @description 判断字符串是否有非Ascii的双字节字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static boolean hasDoubleByte(String str) {
		return has(str, regex.get("double_byte"));
	}
	
	/**
	 * @description 从源字符串中提取匹配表达式的子串集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static List<String> matches(String str, String regex) {
		Matcher matcher = createMatcher(str, regex);
		List<String> list = CollectionUtils.newArrayList();
		while (matcher.find()) 
			list.add(matcher.group());
		return list;
	}
	
	/**
	 * @description 统计源字符串中匹配表达式的子串个数
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
