/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-12-10
 */

package org.sniper.commons.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 安全工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SecurityUtils {
	
	/** MD5算法名称 */
	public static final String MD5_ALGORITHM_NAME = "MD5";
	
	/** DES算法名称 */
	public static final String DES_ALGORITHM_NAME = "DES";
	
	/** DSA算法名称 */
	public static final String DSA_ALGORITHM_NAME = "DSA";
	
	/** RSA算法名称 */
	public static final String RSA_ALGORITHM_NAME = "RSA";
	
	private SecurityUtils() {}
	
	/**
	 * 按指定算法生成明文的摘要
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext 明文
	 * @param algorithm 摘要算法名称
	 * @return
	 */
	public static String digest(String plaintext, String algorithm) {
		return digest(plaintext, algorithm, null);
	}
	
	/**
	 * 按指定的字符集编码和算法生成明文的摘要
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @param algorithm
	 * @param charsetName
	 * @return
	 */
	public static String digest(String plaintext, String algorithm, String charsetName) {
		AssertUtils.assertNotNull(plaintext, "Plaintext must not be null.");
		AssertUtils.assertTrue(StringUtils.isNotBlank(algorithm), "Algorithm must not be null or blank.");

		String digestMessage = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			byte[] bytes = messageDigest.digest(plaintext.getBytes(
					StringUtils.isNotBlank(charsetName) ? charsetName : CodecUtils.DEFAULT_ENCODING));
			StringBuffer buffer = new StringBuffer(bytes.length * 2);
			for (int i = 0; i < bytes.length; i++) {
				if ((bytes[i] & 0xff) < 0x10) {
					buffer.append("0");
				}
				buffer.append(Long.toString(bytes[i] & 0xff, 16));
			}
			digestMessage = buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			return digest(plaintext, algorithm, CodecUtils.DEFAULT_ENCODING);
		}
		
		return digestMessage;
	}
	
	/**
	 * 生成32位全小写MD5摘要信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext 明文
	 * @return
	 */
	public static String md5(String plaintext) {
		return md5(plaintext, null);
	}
	
	/**
	 * 生成32位全小写并按指定字符集编码后的MD5摘要信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @param charsetName
	 * @return
	 */
	public static String md5(String plaintext, String charsetName) {
		return md5(plaintext, charsetName, false);
	}
	
	/**
	 * 生成32位全大写MD5摘要信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @return
	 */
	public static String md5UpperCase(String plaintext) {
		return md5UpperCase(plaintext, null);
	}
	
	/**
	 * 生成32位全大写并按指定字符集编码后的MD5摘要信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @param charsetName
	 * @return
	 */
	public static String md5UpperCase(String plaintext, String charsetName) {
		return md5(plaintext, charsetName, true);
	}
	
	/**
	 * 选择是否按全大写的方式生成32位MD5摘要信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @param upperCase
	 * @return
	 */
	public static String md5(String plaintext, boolean upperCase) {
		return md5(plaintext, null, upperCase);
	}
	
	/**
	 * 选择是否按全大写的方式生成指定字符集编码后的32位MD5摘要信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext
	 * @param charsetName
	 * @param upperCase
	 * @return
	 */
	public static String md5(String plaintext, String charsetName, boolean upperCase) {
		String ciphertext = digest(plaintext, MD5_ALGORITHM_NAME, charsetName);
		return upperCase ? ciphertext.toUpperCase() : ciphertext;
	}
					
}
