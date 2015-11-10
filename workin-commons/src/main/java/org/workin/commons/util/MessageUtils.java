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
 * Create Date : 2014-10-30
 */

package org.workin.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @description 消息工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MessageUtils {
	
	/** UTF-8编码集 */
	public static final String UTF8_ENCODING = "UTF-8";
	
	/** GBK编码集 */
	public static final String GBK_ENCODING = "GBK";
	
	/** GB2312编码集 */
	public static final String GB2312_ENCODING = "GB2312";
	
	/** UNICODE编码集 */
	public static final String UNICODE_ENCODING = "UNICODE";
	
	public static final String ISO_8859_1 = "ISO-8859-1";
	
	/**
	 * @description 获取包级别资源文件内的信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, String key) {
		return getPackageMessage(clazz, null, key, null, key);
	}
	
	/**
	 * @description 获取包级别资源文件内的信息， 未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, String key, String defaultMessage) {
		return getPackageMessage(clazz, null, key, null, defaultMessage);
	}
	
	/**
	 * @description 获取包级别资源文件内的参数化信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, String key, Object param) {
		return getPackageMessage(clazz, null, key, new Object[] { param }, key);
	}
	
	/**
	 * @description 获取包级别资源文件内的参数化信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param params 参数组
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, String key, Object[] params) {
		return getPackageMessage(clazz, null, key, params, key);
	}
	
	/**
	 * @description 获取包级别资源文件内的参数化信息， 未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, String key, Object param, String defaultMessage) {
		return getPackageMessage(clazz, null, key, param, defaultMessage);
	}
	
	/**
	 * @description 获取包级别资源文件内的参数化信息， 未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param params 参数组
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, String key, Object[] params, String defaultMessage) {
		return getPackageMessage(clazz, null, key, params, defaultMessage);
	}
	
	/**
	 * @description 获取包级别资源文件内的本地信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, Locale locale, String key) {
		return getPackageMessage(clazz, locale, key, null, key);
	}
	
	/**
	 * @description 获取包级别资源文件内的本地信息， 未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, Locale locale, String key, String defaultMessage) {
		return getPackageMessage(clazz, locale, key, null, defaultMessage);
	}
	
	/**
	 * @description 获取包级别资源文件内的参数化本地信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, Locale locale, String key, Object param) {
		return getPackageMessage(clazz, locale, key, new Object[] { param }, key);
	}
	
	/**
	 * @description 获取包级别资源文件内的参数化本地信息， 未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, Locale locale,
			String key, Object param, String defaultMessage) {
		return getPackageMessage(clazz, locale, key, new Object[] { param }, defaultMessage);
	}
	
	/**
	 * @description 获取包级别资源文件内的参数化本地信息，  未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param params 参数组
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, Locale locale, 
			String key, Object[] params) {
		return getPackageMessage(clazz, locale, key, params, key);
	}
	
	/**
	 * @description 获取包级别资源文件内的参数化本地信息，  未获取到时返回指定的默认信息
	 * @author <a href="mailto:bin.du@daw.so">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param params 参数值组
	 * @param defaultMessage 默认信息
	 * @return 
	 */
	public static String getPackageMessage(Class<?> clazz, Locale locale,
			String key, Object[] params, String defaultMessage) {
		// 将包的完整限定名替换为相对路径
		String packageFullName = clazz.getPackage().getName();
		int index = packageFullName.lastIndexOf(".");
		String baseName = "";
		/* 信息文件的基名与包名相同 */
		if (index > -1) {
			String lastPackageName = packageFullName.substring(index);
			baseName = packageFullName.substring(0, index) + lastPackageName + lastPackageName;
		} else
			baseName = packageFullName + "/" + packageFullName;
		return getMessage(baseName, locale, key, params, defaultMessage);
	}
	
	/**
	 * @description 获取类级别资源文件内的信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, String key) {
		return getClassMessage(clazz, null, key, null, key);
	}
	
	/**
	 * @description 获取类级别资源文件内的信息，未获取到时返回的指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, String key, String defaultMessage) {
		return getClassMessage(clazz, null, key, null, key);
	}
	
	/**
	 * @description 获取类级别资源文件内的参数化信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, String key, Object param) {
		return getClassMessage(clazz, null, key, new Object[] { param }, key);
	}
	
	/**
	 * @description 获取类级别资源文件内的参数化信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param params 参数组
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, String key, Object[] params) {
		return getClassMessage(clazz, null, key, params, key);
	}
	
	/**
	 * @description 获取类级别资源文件内的参数化信息， 未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, String key, Object param, String defaultMessage) {
		return getClassMessage(clazz, null, key, new Object[] { param }, defaultMessage);
	}
	
	/**
	 * @description 获取类级别资源文件内的参数化信息， 未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @param params 参数
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, String key, Object[] params, String defaultMessage) {
		return getClassMessage(clazz, null, key, params, defaultMessage);
	}
	
	/** 
	 * @description 获取类级别资源文件内的本地信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, Locale locale, String key) {
		return getClassMessage(clazz, locale, key, null, key);
	}
	
	/**
	 * @description 获取类级别资源文件内的本地信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, Locale locale, String key, String defaultMessage) {
		return getClassMessage(clazz, locale, key, null, defaultMessage);
	}
	
	/**
	 * @description 获取类级别资源文件内的参数化本地信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, Locale locale, String key, Object param) {
		return getClassMessage(clazz, locale, key, new Object[] { param }, key);
	}
	
	/**
	 * @description 获取类级别资源文件内的参数化本地信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, Locale locale, String key, Object param, String defaultMessage) {
		return getClassMessage(clazz, locale, key, null, defaultMessage);
	}
	
	/**
	 * @description 获取类级别资源文件内的参数化本地信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param params 参数组
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, Locale locale, String key, Object[] params) {
		// 将类型所在的包的完整限定名替换为相对路径
		return getClassMessage(clazz, locale, key, params, key);
	}
	
	/**
	 * @description 获取类级别资源文件内的参数化本地信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param params 参数组
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, Locale locale,
			String key, Object[] params, String defaultMessage) {
		// 将类型所在的包的完整限定名替换为相对路径
		String pageckFullName = clazz.getPackage().getName().replaceAll("\\.", "/");
		String baseName = pageckFullName + "/" + clazz.getSimpleName();
		return getMessage(baseName, locale, key, params, defaultMessage);
	}
	
	/**
	 * @description 获取指定基名资源文件内的信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param key 资源项的键
	 * @return
	 */
	public static String getMessage(String baseName, String key) {
		return getMessage(baseName, null, key, null, key);
	}
	
	/**
	 * @description 获取指定基名资源文件内的信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param key 资源项的键
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getMessage(String baseName, String key, String defaultMessage) {
		return getMessage(baseName, null, key, null, defaultMessage);
	}
	
	/**
	 * @description 获取指定基名资源文件内的参数化信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param key 资源项的键
	 * @param param 参数
	 * @return
	 */
	public static String getMessage(String baseName, String key, Object param) {
		return getMessage(baseName, null, key, new Object[] { param }, key);
	}
	
	/**
	 * @description 获取指定基名资源文件内的参数化信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param key 资源项的键
	 * @param param 参数
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getMessage(String baseName, String key, Object param, String defaultMessage) {
		return getMessage(baseName, null, key, new Object[] { param }, defaultMessage);
	}
	
	/**
	 * @description 获取指定基名资源文件内的参数化信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param key 资源项的键
	 * @param params 参数组
	 * @return
	 */
	public static String getMessage(String baseName, String key, Object[] params) {
		return getMessage(baseName, null, key, params, key);
	}
	
	/**
	 * @description 获取指定基名资源文件内的参数化信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param key 资源项的键
	 * @param params 参数组
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getMessage(String baseName, String key, Object[] params, String defaultMessage) {
		return getMessage(baseName, null, key, params, defaultMessage);
	}
	
	/**
	 * @description 获取指定基名资源文件内的本地信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @return
	 */
	public static String getMessage(String baseName, Locale locale, String key) {
		return getMessage(baseName, locale, key, null, key);
	}
	
	/**
	 * @description 获取指定基名资源文件内的参数化本地信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @return
	 */
	public static String getMessage(String baseName, Locale locale, String key, Object param) {
		return getMessage(baseName, locale, key, new Object[] { param }, key);
	}
	
	/** 
	 * @description 获取指定基名资源文件内的本地信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getMessage(String baseName, Locale locale, String key, String defaultMessage) {
		return getMessage(baseName, locale, key, null, defaultMessage);
	}
	
	/**
	 * @description 获取指定基名资源文件内的参数化本地信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param params 参数组
	 * @return
	 */
	public static String getMessage(String baseName, Locale locale, String key, Object[] params) {
		return getMessage(baseName, locale, key, params, key);
	}
	
	/**
	 * @description 获取指定基名资源文件内的参数化本地信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param param 参数
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getMessage(String baseName, Locale locale, String key, 
			Object param, String defaultMessage) {
		return getMessage(baseName, locale, key, new Object[] { param }, defaultMessage);
	}
	
	/**
	 * @description 获取指定基名资源文件内的参数化本地信息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param locale 本地化对象
	 * @param key 资源项的键
	 * @param params 参数组
	 * @param defaultMessage 默认信息
	 * @return
	 */
	public static String getMessage(String baseName, Locale locale,
			String key, Object[] params, String defaultMessage) {
		if (StringUtils.isEmpty(baseName))
			return StringUtils.EMPTY_STRING;
		try {
			ResourceBundle bundle = (locale != null ? ResourceBundle.getBundle(
					baseName, locale) : ResourceBundle.getBundle(baseName));
			String message = bundle.getString(StringUtils.safeString(key));
			return ArrayUtils.isNotEmpty(params) ? MessageFormat.format(message, params) : message;
		} catch (MissingResourceException e) {
			return defaultMessage;
		}
	}
	
	/**
	 * @description 根据键值获取属性资源消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param properties
	 * @param key
	 * @return
	 */
	public static String getMessage(Properties properties, String key) {
		return getMessage(properties, key, null, key);
	}
	
	/**
	 * @description 根据键值获取属性资源消息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param properties
	 * @param key
	 * @param defaultMessage
	 * @return
	 */
	public static String getMessage(Properties properties, String key, String defaultMessage) {
		return getMessage(properties, key, null, defaultMessage);
	}
	
	/**
	 * @description 根据键值获取属性资源参数化消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param properties
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getMessage(Properties properties, String key, Object[] params) {
		return getMessage(properties, key, params, key);
	}
	
	/**
	 * @description 根据键值获取属性资源参数化消息，未获取到时返回指定的默认信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param properties
	 * @param key
	 * @param params
	 * @param defaultMessage
	 * @return
	 */
	public static String getMessage(Properties properties, String key, Object[] params, String defaultMessage) {
		String message = properties.getProperty(StringUtils.safeString(key));
		if (message != null) 
			return ArrayUtils.isNotEmpty(params) ? MessageFormat.format(message, params) : message;
			
		return defaultMessage;
	}
	
	/**
	 * @description 加密
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext 明文
	 * @param algorithm 加密算法名称
	 * @return
	 */
	public static String encrypt(String plaintext, String algorithm) {
		String ciphertext = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			ciphertext = new String(plaintext);
			byte[] bytes = messageDigest.digest(ciphertext.getBytes());
			StringBuffer buffer = new StringBuffer(bytes.length * 2);
			for (int i = 0; i < bytes.length; i++) {
				if ((bytes[i] & 0xff) < 0x10) 
					buffer.append("0");
				buffer.append(Long.toString(bytes[i] & 0xff, 16));
			}
			ciphertext = buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ciphertext;
	}
	
	/**
	 * @description MD5加密
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param plaintext 明文
	 * @return
	 */
	public static String md5Encrypt(String plaintext) {
		return encrypt(plaintext, "MD5");
	}
	
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
	 * @description 将字符串转换成十六进制编码表示
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param str
	 * @return
	 */
	public static String toHexString(String str) {
		if (str == null)
			return StringUtils.EMPTY_STRING;
		
		StringBuilder result = new StringBuilder();
		char c;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c > 127)
				// 只对ASCII以外的字符进行编码
				result.append("\\u").append(Integer.toHexString(c).toUpperCase());
			else
				result.append(c);
		}
		return result.toString();
	}
									
}
