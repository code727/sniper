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

package org.sniper.commons.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 消息工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MessageUtils {
	
	private static final ThreadLocal<Map<String, ResourceBundle>> resourceBundles = new ThreadLocal<Map<String, ResourceBundle>>();
	
	private MessageUtils() {}
		
	/**
	 * 根据基础名称和Locale对象获取java.util.ResourceBundle对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName
	 * @param locale
	 * @return
	 */
	public static ResourceBundle getResourceBundle(String baseName, Locale locale)  {
		
		if (locale == null)
			locale = Locale.getDefault();
		
		Map<String, ResourceBundle> bundleMap = resourceBundles.get();
		if (bundleMap == null)
			bundleMap = MapUtils.newConcurrentHashMap();
		
		String key = baseName + "_" + locale;
		ResourceBundle resourceBundle = bundleMap.get(key);
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle(baseName, locale);
			bundleMap.put(key, resourceBundle);
			resourceBundles.set(bundleMap);
		}
		
		return resourceBundle;
	}
	
	/**
	 * 获取包级别资源文件内的信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @return
	 */
	public static String getPackageMessage(Class<?> clazz, String key) {
		return getPackageMessage(clazz, null, key, null, key);
	}
	
	/**
	 * 获取包级别资源文件内的信息， 未获取到时返回指定的默认信息
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
	 * 获取包级别资源文件内的参数化信息， 未获取到时返回资源项的键
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
	 * 获取包级别资源文件内的参数化信息， 未获取到时返回资源项的键
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
	 * 获取包级别资源文件内的参数化信息， 未获取到时返回指定的默认信息
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
	 * 获取包级别资源文件内的参数化信息， 未获取到时返回指定的默认信息
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
	 * 获取包级别资源文件内的本地信息， 未获取到时返回资源项的键
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
	 * 获取包级别资源文件内的本地信息， 未获取到时返回指定的默认信息
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
	 * 获取包级别资源文件内的参数化本地信息， 未获取到时返回资源项的键
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
	 * 获取包级别资源文件内的参数化本地信息， 未获取到时返回指定的默认信息
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
	 * 获取包级别资源文件内的参数化本地信息，  未获取到时返回资源项的键
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
	 * 获取包级别资源文件内的参数化本地信息，  未获取到时返回指定的默认信息
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
	 * 获取类级别资源文件内的信息， 未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz 类型的Class对象
	 * @param key 资源项的键
	 * @return
	 */
	public static String getClassMessage(Class<?> clazz, String key) {
		return getClassMessage(clazz, null, key, null, key);
	}
	
	/**
	 * 获取类级别资源文件内的信息，未获取到时返回的指定的默认信息
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
	 * 获取类级别资源文件内的参数化信息， 未获取到时返回资源项的键
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
	 * 获取类级别资源文件内的参数化信息， 未获取到时返回资源项的键
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
	 * 获取类级别资源文件内的参数化信息， 未获取到时返回指定的默认信息
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
	 * 获取类级别资源文件内的参数化信息， 未获取到时返回指定的默认信息
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
	 * 获取类级别资源文件内的本地信息，未获取到时返回资源项的键
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
	 * 获取类级别资源文件内的本地信息，未获取到时返回指定的默认信息
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
	 * 获取类级别资源文件内的参数化本地信息，未获取到时返回资源项的键
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
	 * 获取类级别资源文件内的参数化本地信息，未获取到时返回指定的默认信息
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
	 * 获取类级别资源文件内的参数化本地信息，未获取到时返回资源项的键
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
	 * 获取类级别资源文件内的参数化本地信息，未获取到时返回指定的默认信息
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
	 * 获取指定基名资源文件内的信息，未获取到时返回资源项的键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName 基名
	 * @param key 资源项的键
	 * @return
	 */
	public static String getMessage(String baseName, String key) {
		return getMessage(baseName, null, key, null, key);
	}
	
	/**
	 * 获取指定基名资源文件内的信息，未获取到时返回指定的默认信息
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
	 * 获取指定基名资源文件内的参数化信息，未获取到时返回资源项的键
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
	 * 获取指定基名资源文件内的参数化信息，未获取到时返回指定的默认信息
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
	 * 获取指定基名资源文件内的参数化信息，未获取到时返回资源项的键
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
	 * 获取指定基名资源文件内的参数化信息，未获取到时返回指定的默认信息
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
	 * 获取指定基名资源文件内的本地信息，未获取到时返回资源项的键
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
	 * 获取指定基名资源文件内的参数化本地信息，未获取到时返回资源项的键
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
	 * 获取指定基名资源文件内的本地信息，未获取到时返回指定的默认信息
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
	 * 获取指定基名资源文件内的参数化本地信息，未获取到时返回资源项的键
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
	 * 获取指定基名资源文件内的参数化本地信息，未获取到时返回指定的默认信息
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
	 * 获取指定基名资源文件内的参数化本地信息，未获取到时返回指定的默认信息
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
			return StringUtils.EMPTY;
		try {
			ResourceBundle bundle = getResourceBundle(baseName, locale);
			String message = bundle.getString(StringUtils.safeString(key));
			return ArrayUtils.isNotEmpty(params) ? MessageFormat.format(message, params) : message;
		} catch (MissingResourceException e) {
			return defaultMessage;
		}
	}
	
	/**
	 * 根据键值获取属性资源消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param properties
	 * @param key
	 * @return
	 */
	public static String getMessage(Properties properties, String key) {
		return getMessage(properties, key, null, key);
	}
	
	/**
	 * 根据键值获取属性资源消息，未获取到时返回指定的默认信息
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
	 * 根据键值获取属性资源参数化消息
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
	 * 根据键值获取属性资源参数化消息，未获取到时返回指定的默认信息
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
	
}
