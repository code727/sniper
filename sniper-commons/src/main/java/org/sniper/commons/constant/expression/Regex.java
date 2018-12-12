/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-12-12
 */

package org.sniper.commons.constant.expression;

import org.sniper.commons.constant.AbstractConstant;

/**
 * 正则表达式常量
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class Regex extends AbstractConstant<String, String> {

	private static final long serialVersionUID = -6340578024795397271L;
	
	protected Regex(String key, String value) {
		super(key, value);
	}
	
	/** 整数表达式 */
	public static final Regex INTEGER = new Regex("INTEGER", "[+|-]?\\d+");
	
	/** 小数表达式 */
	public static final Regex DECIMAL = new Regex("DECIMAL", "[+|-]?(\\d+\\.\\d+)");
	
	/** 数字(整数/小数)表达式 */
	public static final Regex NUMBER = new Regex("NUMBER", "[+|-]?(\\d+((\\.\\d+)|d*))");
	
	/** IPV4表达式 */
	public static final Regex IPV4 = new Regex("IPV4", "(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}");
	
	/** IPV6表达式 */
	public static final Regex IPV6 = new Regex("IPV6", "((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)");
	
	/** EMAIL表达式 */
	public static final Regex EMAIL = new Regex("EMAIL", "[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
	
	/** 移动电话号码表达式 */
	public static final Regex MOBILE = new Regex("MOBILE", "[1][3578]\\d{9}");
		
	/** ASCII码表达式 */
	public static final Regex ASCII = new Regex("ASCII", "[\u0000-\u007E]+");
	
	/** 双字节表达式 */
	public static final Regex DOUBLE_BYTE = new Regex("DOUBLE_BYTE", "[^\u0000-\u007E]+");
	
	/** 中文字符表达式 */
	public static final Regex CHINESE = new Regex("CHINESE", "[\u4E00-\u9FA5]+");
		
	/** URL查询字符串表达式 */
	public static final Regex URL_QUERY_STRING = new Regex("URL_QUERY_STRING", "(\\w*=[^&]*&)*\\w*=\\w*");
	
	/** URL字符串表达式 */
	public static final Regex URL = new Regex("URL", "((h|H)(t|T)(t|T)(p|P)(s|S)?(://)|(w|W){3}[.]|(f|F)(t|T)(p|P)(://)+){1}(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)");

	/** java.text.MessageFormat默认支持的占位符表达式 */
	public static final Regex MESSAGE_FORMAT_PLACEHOLDER = new Regex("MESSAGE_FORMAT_PLACEHOLDER", "(\\{\\d+\\})");
	
	/** StringFormat默认支持的占位符表达式 */
	public static final Regex STRING_FORMAT_PLACEHOLDER = new Regex("STRING_FORMAT_PLACEHOLDER", "(%s|%c|%b|%d|%x|%o|%f|%a|%e|%g|%h|%%|%n|%tx)");
}
