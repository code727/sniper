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

package org.sniper.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 编解码工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CodecUtils {
	
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
	
	/** 默认编码集 */
	public static final String DEFAULT_ENCODING = UTF8_ENCODING;
	
	/** JVM默认编码集 */
	public static final String JVM_DEFAULT_ENCODING = Charset.defaultCharset().name();
	
	/** 16进制码 */
	public static char[] HEX_CODES = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
		
	/**
	 * 将URL字符串按默认编码集进行编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url URL字符
	 * @return
	 */
	public static String urlEncode(String url) {
		return urlEncode(url, DEFAULT_ENCODING);
	}
	
	/**
	 * 将URL字符串按指定的编码集进行编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url URL字符
	 * @param encoding 编码集
	 * @return
	 */
	public static String urlEncode(String url, String encoding) {
		AssertUtils.assertNotNull(url, "Encoding url can not be null.");
	
		try {
			return URLEncoder.encode(url, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return urlEncode(url, DEFAULT_ENCODING);
		}
	}
	
	/**
	 * 将URL字符串按默认编码集进行解码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param URL URL字符
	 * @return
	 */
	public static String urlDecode(String url) {
		return urlDecode(url, DEFAULT_ENCODING);
	}
	
	/**
	 * 将URL字符串按指定的编码集进行解码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url URL字符
	 * @param encoding 编码集
	 * @return
	 */
	public static String urlDecode(String url, String encoding) {
		AssertUtils.assertNotNull(url, "Decoding url can not be null.");
		try {
			return URLDecoder.decode(url, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return urlDecode(url, DEFAULT_ENCODING);
		}
	}
	
	/**
	 * UNICODE编码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @return
	 */
	public static String unicodeEncode(String text) {
		return unicodeEncode(text, false);
	}
	
	/**
	 * UNICODE编码，并选择是否以全大写的方式返回编码结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @param upperCase
	 * @return
	 */
	public static String unicodeEncode(String text, boolean upperCase) {
		if (StringUtils.isEmpty(text))
			return StringUtils.EMPTY;
		
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
	 * 将文本进行UNICODE解码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static String unicodeDecode(String text) { 
		if (StringUtils.isEmpty(text))
			return StringUtils.EMPTY;
		
//		Pattern pattern = RegexUtils.getPattern("[0-9A-Fa-f]{4}"); 
        StringBuilder result = new StringBuilder(); 
        int length = text.length();  
        
        for (int i = 0; i < length; i++) {  
            char c1 = text.charAt(i);  
            if (c1 == '\\' && i < length - 1) {  
                char c2 = text.charAt(++i);  
                if ((c2 == 'u' || c2 == 'U') && i <= length - 5) {  
                    String temp = text.substring(i + 1, i + 5);  
//                  Matcher matcher = pattern.matcher(temp);  
//                  if (matcher.find()) {  
                    result.append((char) Integer.parseInt(temp, 16));  
                    i = i + 4;  
//                  } else {  
//                  result.append(c1).append(c2);  
//                  }  
                } else 
                	result.append(c1).append(c2);  
            } else 
            	result.append(c1);  
        }  
        
        return result.toString();  
    }
	
	/**
	 * 获取字符串的字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		return getBytes(str, null);
	}
	
	/**
	 * 获取字符串按指定编码集编码后的字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static byte[] getBytes(String str, String encoding) {
		if (str == null)
			return null;
		
		try {
			return StringUtils.isNotBlank(encoding) ? str.getBytes(encoding) : str.getBytes();
		} catch (UnsupportedEncodingException e) {
			return getBytes(str, DEFAULT_ENCODING);
		}
	}
	
	/**
	 * 将字节数组还原成源字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param textBytes
	 * @return
	 */
	public static String bytesToString(byte[] textBytes) {
		return bytesToString(textBytes, null);
	}
	
	/**
	 * 按指定编码集将字节数组还原成源字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param textBytes
	 * @param encoding
	 * @return
	 */
	public static String bytesToString(byte[] textBytes, String encoding) {
		if (ArrayUtils.isEmpty(textBytes))
			return null;
		
		try {
			return StringUtils.isNotBlank(encoding) ? new String(textBytes,
					encoding) : new String(textBytes);
		} catch (UnsupportedEncodingException e) {
			return bytesToString(textBytes, DEFAULT_ENCODING);
		} 
	}
	
	/**
	 * 将字符串转换为16进制表现形式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static String stringToHex(String str) {
		return stringToHex(str, null);
	}
	
	/**
	 * 将字符串转换为按指定编码集编码后的16进制表现形式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String stringToHex(String str, String encoding) {
		return bytesToHex(getBytes(str, encoding));
	}
	
	/**
	 * 将字节数组转换为16进制全大写的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @return
	 */
	public static String bytesToUpperHex(byte[] bytes) {
		return bytesToHex(bytes).toUpperCase();
	}
	
	/**
	 * 将字节数组转换为16进制的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		String stmp = "";
		for (int n = 0; n < bytes.length; n++) {
			stmp = (Integer.toHexString(bytes[n] & 0XFF));
			if (stmp.length() == 1)
				hexString.append("0");
			
			hexString.append(stmp);
		}
		
		return hexString.toString();
	}
	
	/**
	 * 将16进制的字符串转换为字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexToBytes(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];
		char[] chs = hexString.toCharArray();
		for (int i = 0, c = 0; i < chs.length; i += 2, c++) 
			bytes[c] = (byte) (Integer.parseInt(new String(chs, i, 2), 16));

		return bytes;
	}
	
	/**
	 * 将16进制的字符串还原成原文
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param hexString
	 * @return
	 */
	public static String hexToString(String hexString) {
		return hexToString(hexString, null);
	}
	
	/**
	 * 将16进制的字符串按指定的字符集编码还原成原文
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param hexString
	 * @param encoding
	 * @return
	 */
	public static String hexToString(String hexString, String encoding) {
		byte[] bytes = hexToBytes(hexString);
		return bytesToString(bytes, encoding);
	}
	
}
