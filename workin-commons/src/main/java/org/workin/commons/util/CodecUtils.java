/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-1-6
 */

package org.workin.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 编解码工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CodecUtils {
	
	/** 系统默认的编码集 */
	public static final String DEFAULT_ENCODING = SystemUtils.getSystemEncoding();
	
	/** UTF-8编码集 */
	public static final String UTF8_ENCODING = "UTF-8";
	
	/** GBK编码集 */
	public static final String GBK_ENCODING = "GBK";
	
	/** GB2312编码集 */
	public static final String GB2312_ENCODING = "GB2312";
	
	/** UNICODE编码集 */
	public static final String UNICODE_ENCODING = "UNICODE";
	
	/** ISO-8859-1编码集 */
	public static final String ISO_8859_1 = "ISO-8859-1";
	
	/** 16进制码 */
	public static char[] HEX_CODE = {
		'0', '1', '2', '3', 
		'4', '5', '6', '7',  
		'8', '9', 'a', 'b', 
		'c', 'd', 'e', 'f'
	};  
       
	/**
	 * @description 将URL字符串按UTF-8编码集进行编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url URL字符
	 * @return
	 */
	public static String urlEncode(String url) {
		return urlEncode(url, UTF8_ENCODING);
	}
	
	/**
	 * @description 将URL字符串按指定的编码集进行编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url URL字符
	 * @param encoding 编码集
	 * @return
	 */
	public static String urlEncode(String url, String encoding) {
		AssertUtils.assertNotNull(url, "Encoding url can not be null.");
		
		
		try {
			return URLEncoder.encode(url, StringUtils.isNotBlank(encoding) ? encoding : UTF8_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return urlEncode(url, UTF8_ENCODING);
		}
	}
	
	/**
	 * @description 将URL字符串按UTF-8编码集进行解码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param URL URL字符
	 * @return
	 */
	public static String urlDecode(String url) {
		return urlDecode(url, UTF8_ENCODING);
	}
	
	/**
	 * @description 将URL字符串按指定的编码集进行解码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url URL字符
	 * @param encoding 编码集
	 * @return
	 */
	public static String urlDecode(String url, String encoding) {
		AssertUtils.assertNotNull(url, "Decoding url can not be null.");
		try {
			return URLDecoder.decode(url, StringUtils.isNotBlank(encoding) ? encoding : UTF8_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return urlDecode(url, UTF8_ENCODING);
		}
	}
	
	/**
	 * @description UNICODE编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @return
	 */
	public static String unicodeEncode(String text) {
		return unicodeEncode(text, false);
	}
	
	/**
	 * @description UNICODE编码，并选择是否以全大写的方式返回编码结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @param upperCase
	 * @return
	 */
	public static String unicodeEncode(String text, boolean upperCase) {
		if (StringUtils.isEmpty(text))
			return StringUtils.EMPTY_STRING;
		
		StringBuilder result = new StringBuilder();
		char c;
		if (upperCase) {
			for (int i = 0; i < text.length(); i++) {
				c = text.charAt(i);
				if (c > 127)
					// 只对ASCII以外的字符进行编码
					result.append("\\u").append(Integer.toHexString(c).toUpperCase());
				else
					result.append(c);
			}
		} else {
			for (int i = 0; i < text.length(); i++) {
				c = text.charAt(i);
				if (c > 127)
					// 只对ASCII以外的字符进行编码
					result.append("\\u").append(Integer.toHexString(c));
				else
					result.append(c);
			}
		}
		return result.toString();
	}
	
	/**
	 * @description 将文本进行UNICODE编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static String unicodeDecode(String text) { 
		if (StringUtils.isEmpty(text))
			return StringUtils.EMPTY_STRING;
		
		Pattern pattern = RegexUtils.getPattern("[0-9A-Fa-f]{4}"); 
        StringBuilder result = new StringBuilder(); 
        int length = text.length();  
        
        for (int i = 0; i < length; i++) {  
            char c1 = text.charAt(i);  
            if (c1 == '\\' && i < length - 1) {  
                char c2 = text.charAt(++i);  
                if ((c2 == 'u' || c2 == 'U') && i <= length - 5) {  
                    String temp = text.substring(i + 1, i + 5);  
                    Matcher matcher = pattern.matcher(temp);  
                    if (matcher.find()) {  
                    	result.append((char) Integer.parseInt(temp, 16));  
                        i = i + 4;  
                    } else {  
                    	result.append(c1).append(c2);  
                    }  
                } else 
                	result.append(c1).append(c2);  
            } else 
            	result.append(c1);  
        }  
        
        return result.toString();  
    }
		    
}