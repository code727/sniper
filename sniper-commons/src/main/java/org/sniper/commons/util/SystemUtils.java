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
 * Create Date : 2014-12-18
 */

package org.sniper.commons.util;

import java.util.Properties;
import java.util.Set;

/**
 * 系统工具类
 * @author  Daniele
 * @version 1.0
 */
public class SystemUtils {
	
	/** 全局固定的系统变量 */
	public static final Properties GLOBAL_PROPERTIES;
	
	/** Windows系统文本换行符 */
	public static final String WINDOWS_TEXT_NEWLINE = "\r\n";
	
	/** Unix/Linux系统文本换行符 */
	public static final String UNIX_TEXT_NEWLINE = "\n";
	
	/** Mac系统文本换行符 */
	public static final String MAC_TEXT_NEWLINE = "\r";
	
	static {
		GLOBAL_PROPERTIES = new Properties();
		String osName = ObjectUtils.toString(getSystemProperties().get("os.name"));
		if (StringUtils.indexOfIgnoreCase(osName, "Unix") > -1 || StringUtils.indexOfIgnoreCase(osName, "Linux") > -1)
			GLOBAL_PROPERTIES.put("sys.text.newline", UNIX_TEXT_NEWLINE);
		else if (StringUtils.indexOfIgnoreCase(osName, "Mac") > -1)
			GLOBAL_PROPERTIES.put("sys.text.newline", MAC_TEXT_NEWLINE);
		else
			GLOBAL_PROPERTIES.put("sys.text.newline", WINDOWS_TEXT_NEWLINE);
	}
	
	private SystemUtils() {}
	
	/**
	 * 获取所用的系统属性
	 * @author Daniele 
	 * @return
	 */
	public static Properties getSystemProperties() {
		return System.getProperties();
	}
	
	/**
	 * 获取所用的系统属性名称
	 * @author Daniele 
	 * @return
	 */
	public static Set<Object> getSystemPropertyNames() {
		return getSystemProperties().keySet();
	}
	
	/**
	 * 根据名称获取Worin Jar包定义的全局属性对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static String getGlobalPropertie(String name) {
		return StringUtils.isNotBlank(name) ? GLOBAL_PROPERTIES.getProperty(name.trim()) : null;
	}
	
	/**
	 * 判断是否为系统属性
	 * @author Daniele 
	 * @param name
	 */
	public static boolean isSystemProperty(String name) {
		if (StringUtils.isBlank(name))
			return false;
		
		return getSystemProperties().containsKey(name.toLowerCase().trim());
	}
	
	/**
	 * 获取当前操作系统的文本换行符
	 * @author Daniele 
	 * @return
	 */
	public static String getTextNewline() {
		return GLOBAL_PROPERTIES.getProperty("sys.text.newline");
	}
	
	/**
	 * 获取文件系统编码集
	 * @author Daniele 
	 * @return
	 */
	public static String getFileEncoding() {
		return System.getProperty("file.encoding");
	}
	
	/**
	 * 获取当前操作系统的临时目录
	 * @author Daniele 
	 * @return
	 */
	public static String getTempDir() {
		return System.getProperty(System.getProperty("java.io.tmpdir"));
	}
							
}
