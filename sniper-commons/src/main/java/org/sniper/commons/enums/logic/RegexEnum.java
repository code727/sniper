/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-4
 */

package org.sniper.commons.enums.logic;

import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 正则表达式枚举
 * @author  Daniele
 * @version 1.0
 */
public enum RegexEnum {
	
	/** ASCII码 */
	ASCII("[\u0000-\u007E]+"),
	
	/** 中文字符 */
	CHINESE("[\u4E00-\u9FA5]+"),
	
	/** 双字节 */
	DOUBLE_BYTE("[^\u0000-\u007E]+"),
	
	/** 整数 */
	INTEGER("[+|-]?\\d+"),
	
	/** 小数 */
	DECIMAL("[+|-]?(\\d+\\.\\d+)"),
	
	/** 数字(包含整数和小数) */
	NUMBER("[+|-]?(\\d+((\\.\\d+)|d*))"),
	
	/** IPV4 */
	IPV4("(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}"),
	
	/** IPV6 */
	IPV6("((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)"),
	
	/** EMAIL */
	EMAIL("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+"),
	
	/** 移动电话号码，更新时间2020-10-26 */
//	MOBILE("[1][3456789]\\d{9}"),
	MOBILE("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$"),
	
	/** URL字符串 */
	URL("((h|H)(t|T)(t|T)(p|P)(s|S)?(://)|(w|W){3}[.]|(f|F)(t|T)(p|P)(://)+){1}(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)"),
	
	/** URL查询字符串 */
	URL_QUERY_STRING("(\\w*=[^&]*&)*\\w*=\\w*"),
	
	/** java.text.MessageFormat默认支持的占位符 */
	MESSAGE_FORMAT_PLACEHOLDER("(\\{\\d+\\})"),
	
	/** String#format方法默认支持的占位符 */
	STRING_FORMAT_PLACEHOLDER("(%s|%c|%b|%d|%x|%o|%f|%a|%e|%g|%h|%%|%n|%tx)")
	;
	
	private static final Map<String, RegexEnum> NAME_MAPPINGS = MapUtils.newHashMap(14);
	
	static {
		for (RegexEnum regex : values()) {
			NAME_MAPPINGS.put(regex.name(), regex);
		}
	}
		
	/** 表达式值 */
	private final String value;
	
	/** 消息 */
	private final String message;

	private RegexEnum(String value) {
		this.value = value;
		this.message = MessageUtils.getClassMessage(getClass(), name());
	}
	
	public String getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}
	
	/**
	 * 判断指定的名称是否匹配
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public boolean matches(String name) {
		return this.name().equalsIgnoreCase(name);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static RegexEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
		
}
