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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 资源属性工具类
 * @author  Daniele
 * @version 1.0
 */
public class PropertiesUtils {
	
	private PropertiesUtils() {}
	
	/**
	 * 判断属性对象是否为空
	 * @author Daniele 
	 * @param properties
	 * @return
	 */
	public static boolean isEmpty(Properties properties) {
		return properties == null || properties.size() == 0;
	}
	
	/**
	 * 判断属性对象是否不为空
	 * @author Daniele 
	 * @param properties
	 * @return
	 */
	public static boolean isNotEmpty(Properties properties) {
		return !isEmpty(properties);
	}
	
	/**
	 * 将资源属性对象里的所有键值对元素连接成URL参数字符串
	 * @author Daniele 
	 * @param properties
	 * @return
	 */
	public static String joinURLParameters(Properties properties) {
		return join(properties, "=", "&");
	}
	
	/**
	 * 将资源属性对象里的所有键值对元素按各部分的连接符连接成字符串
	 * @author Daniele 
	 * @param properties 资源属性对象
	 * @param kvSeperator 键值之间的连接符
	 * @param itemSeperator 键值对之间的连接符
	 * @return
	 */
	public static String join(Properties properties, String kvSeperator, String itemSeperator) {
		return MapUtils.join(properties.entrySet(), kvSeperator, itemSeperator);
	}
	
	/**
	 * 根据指定的URL参数字符串创建Properties对象
	 * @author Daniele 
	 * @param url
	 * @return
	 */
	public static Properties createByURLParameters(String urlParameters) {
		return createBySeperator(urlParameters, "=", "&");
	}
	
	/**
	 * 按照指定的各部分分隔符将字符串分割后创建Properties对象
	 * @author Daniele 
	 * @param str 
	 * @param kvSeperator
	 * @param itemSeperator
	 * @return
	 */
	public static Properties createBySeperator(String str, String kvSeperator, String itemSeperator) {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(kvSeperator) || StringUtils.isEmpty(itemSeperator))
			return new Properties();
		
		Properties properties = new Properties();
		String[] items = str.split(itemSeperator);
		for (String item : items) 
			properties.put(StringUtils.beforeFrist(item, kvSeperator), StringUtils.afterFrist(item, kvSeperator));
			
		return properties;
	}
	
	/**
	 * 根据本地文件创建Properties文件
	 * @author Daniele 
	 * @param fileName 本地文件名
	 * @return
	 * @throws IOException
	 */
	public static Properties create(String fileName) throws IOException {
		return create(new File(fileName));
	}
	
	/** 
	 * 根据本地文件创建Properties文件
	 * @author Daniele 
	 * @param file 本地文件
	 * @return
	 * @throws IOException
	 */
	public static Properties create(File file) throws IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			return create(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(in);
		}
		return null;
	}
	
	/**
	 * 根据输入流创建Properties对象
	 * @author Daniele 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static Properties create(InputStream in) throws IOException {
		Properties properties = new Properties();
		properties.load(in);
		return properties;
	}
	
	/**
	 * 根据IO读取器创建Properties对象
	 * @author Daniele 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static Properties create(Reader reader) throws IOException {
		Properties properties = new Properties();
		properties.load(reader);
		return properties;
	}
	
	/**
	 * 根据本地XML文件创建Properties对象
	 * @author Daniele 
	 * @param fileName 本地XML文件名
	 * @return
	 */
	public static Properties createByXML(String fileName) throws IOException {
		return createByXML(new File(fileName));
	}
	
	/**
	 * 根据本地XML文件创建Properties对象
	 * @author Daniele 
	 * @param file 本地XML文件
	 * @return
	 */
	public static Properties createByXML(File file) throws IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			return createByXML(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(in);
		}
		return null;
	}
	
	/**
	 * 根据XML文件输入流创建Properties对象
	 * @author Daniele 
	 * @param in XML文件输入流
	 * @return
	 * @throws IOException
	 */
	public static Properties createByXML(InputStream in) throws IOException {
		Properties properties = new Properties();
		properties.loadFromXML(in);
		return properties;
	}
	
	/**
	 * 将源文件属性追加到目标属性对象中，重复项将被更新
	 * @author Daniele 
	 * @param dest
	 * @param fileName
	 * @throws IOException
	 */
	public static void append(Properties dest, String fileName) throws IOException {
		append(dest, create(fileName));
	}
	
	/**
	 * 按忽略重复项的方式将源文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param fileName
	 * @throws IOException
	 */
	public static void appendIgnoreDuplicate(Properties dest, String fileName) throws IOException {
		appendIgnoreDuplicate(dest, create(fileName));
	}
		
	/**
	 * 选择是否按忽略重复项的方式将源文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param fileName
	 * @param ignoreDuplicate
	 * @throws IOException
	 */
	public static void append(Properties dest, String fileName, boolean ignoreDuplicate) throws IOException {
		append(dest, create(fileName), ignoreDuplicate);
	}
	
	/**
	 * 按忽略重复项的方式将源文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param src
	 * @throws IOException
	 */
	public static void appendIgnoreDuplicate(Properties dest, File src) throws IOException {
		append(dest, create(src), true);
	}
	
	/**
	 * 选择是否按忽略重复项的方式将源文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param src
	 * @param ignoreDuplicate
	 * @throws IOException
	 */
	public static void append(Properties dest, File src, boolean ignoreDuplicate) throws IOException {
		append(dest, create(src), ignoreDuplicate);
	}
	
	/**
	 * 将源属性追加到目标属性对象中，重复项将被更新
	 * @author Daniele 
	 * @param dest
	 * @param src
	 */
	public static void append(Properties dest, Properties src) {
		append(dest, src, false);
	}
	
	/**
	 * 按忽略重复项的方式将源属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param src
	 */
	public static void appendIgnoreDuplicate(Properties dest, Properties src) {
		append(dest, src, true);
	}
	
	/**
	 * 选择是否按忽略重复项的方式将源属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param src
	 * @param ignoreDuplicate
	 */
	public static void append(Properties dest, Properties src, boolean ignoreDuplicate) {
		if (isNotEmpty(src)) {
			Set<Entry<Object, Object>> srcSet = src.entrySet();
			if (ignoreDuplicate) {
				for (Entry<Object, Object> srcEntry : srcSet) {
					if (!dest.containsKey(srcEntry.getKey()))
						dest.put(srcEntry.getKey(), srcEntry.getValue());
				}
			} else {
				for (Entry<Object, Object> srcEntry : srcSet) 
					dest.put(srcEntry.getKey(), srcEntry.getValue());
			}
		}
	}
	
	/**
	 * 将输入流的属性追加到目标属性对象中，重复项将被更新
	 * @author Daniele 
	 * @param dest
	 * @param in
	 * @throws IOException
	 */
	public static void append(Properties dest, InputStream in) throws IOException {
		append(dest, create(in));
	}
	
	/**
	 * 按忽略重复项的方式将输入流的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param src
	 * @throws IOException
	 */
	public static void appendIgnoreDuplicate(Properties dest, InputStream in) throws IOException {
		appendIgnoreDuplicate(dest, create(in));
	}
	
	/**
	 * 选择是否按忽略重复项的方式将输入流的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param in
	 * @param ignoreDuplicate
	 * @throws IOException 
	 */
	public static void append(Properties dest, InputStream in, boolean ignoreDuplicate) throws IOException {
		append(dest, create(in), ignoreDuplicate);
	}
	
	/**
	 * 将源XML文件的属性追加到目标属性对象中，重复项将被更新
	 * @author Daniele 
	 * @param dest
	 * @param fileName
	 * @throws IOException
	 */
	public static void appendByXML(Properties dest, String fileName) throws IOException {
		append(dest, createByXML(fileName));
	}
	
	/**
	 * 按忽略重复项的方式将源XML文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param fileName
	 * @throws IOException
	 */
	public static void appendByXMLIgnoreDuplicate(Properties dest, String fileName) throws IOException {
		appendIgnoreDuplicate(dest, create(fileName));
	}
		
	/**
	 * 选择是否按忽略重复项的方式将源XML文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param fileName
	 * @param ignoreDuplicate
	 * @throws IOException
	 */
	public static void appendByXML(Properties dest, String fileName, boolean ignoreDuplicate) throws IOException {
		append(dest, create(fileName), ignoreDuplicate);
	}
	
	/**
	 * 将源XML文件的属性追加到目标属性对象中，重复项将被更新
	 * @author Daniele 
	 * @param dest
	 * @param file
	 * @throws IOException
	 */
	public static void appendByXML(Properties dest, File file) throws IOException {
		append(dest, createByXML(file));
	}
	
	/**
	 * 按忽略重复项的方式将源XML文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param file
	 * @throws IOException
	 */
	public static void appendByXMLIgnoreDuplicate(Properties dest, File file) throws IOException {
		appendIgnoreDuplicate(dest, create(file));
	}
		
	/**
	 * 选择是否按忽略重复项的方式将源XML文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param file
	 * @param ignoreDuplicate
	 * @throws IOException
	 */
	public static void appendByXML(Properties dest, File file, boolean ignoreDuplicate) throws IOException {
		append(dest, create(file), ignoreDuplicate);
	}
	
	/**
	 * 将源XML文件的属性追加到目标属性对象中，重复项将被更新
	 * @author Daniele 
	 * @param dest
	 * @param in XML输入流
	 * @throws IOException
	 */
	public static void appendByXML(Properties dest, InputStream in) throws IOException {
		append(dest, createByXML(in));
	}
	
	/**
	 * 按忽略重复项的方式将源XML文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param in XML输入流
	 * @throws IOException
	 */
	public static void appendByXMLIgnoreDuplicate(Properties dest, InputStream in) throws IOException {
		appendIgnoreDuplicate(dest, create(in));
	}
		
	/**
	 * 选择是否按忽略重复项的方式将源XML文件的属性追加到目标属性对象中
	 * @author Daniele 
	 * @param dest
	 * @param in XML输入流
	 * @param ignoreDuplicate
	 * @throws IOException
	 */
	public static void appendByXML(Properties dest, InputStream in, boolean ignoreDuplicate) throws IOException {
		append(dest, create(in), ignoreDuplicate);
	}
	
	/**
	 * 将各属性追加写入到本地文件
	 * @author Daniele 
	 * @param fileName 本地文件名
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void write(String fileName, Properties properties) throws IOException {
		write(new File(fileName), properties, "");
	}
	
	/**
	 * 将各属性和备注追加写入到本地文件
	 * @author Daniele 
	 * @param fileName 本地文件名
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void write(String fileName, Properties properties, String comments) throws IOException {
		write(new File(fileName), properties, comments);
	}
	
	/**
	 * 将各属性追加写入到本地文件
	 * @author Daniele 
	 * @param file 本地文件
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void write(File file, Properties properties) throws IOException {
		write(file, properties, "");
	}
	
	/**
	 * 将各属性和备注追加写入到本地文件
	 * @author Daniele 
	 * @param file 本地文件
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void write(File file, Properties properties, String comments) throws IOException {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file, true));
			write(out, properties, comments);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}
	
	/**
	 * 将各属性追加写入到输出流
	 * @author Daniele 
	 * @param out
	 * @param properties
	 * @throws IOException
	 */
	public static void write(OutputStream out, Properties properties) throws IOException {
		write(out, properties, "");
	}
	
	/**
	 * 将各属性和备注追加写入到输出流
	 * @author Daniele 
	 * @param out 输出流
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void write(OutputStream out, Properties properties, String comments) throws IOException {
		properties.store(out, StringUtils.safeString(comments));
	}
	
	/**
	 * 将各属性追加写入到写入器中
	 * @author Daniele 
	 * @param writer 写入器
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void write(Writer writer, Properties properties) throws IOException {
		write(writer, properties, "");
	}
	
	/**
	 * 将各属性和备注追加写入到写入器中
	 * @author Daniele 
	 * @param writer 写入器
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void write(Writer writer, Properties properties, String comments) throws IOException {
		properties.store(writer, comments);
	}
	
	/**
	 * 将各属性追加写入到本地XML文件中
	 * @author Daniele 
	 * @param fileName 本地文件名
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void writeToXML(String fileName, Properties properties) throws IOException {
		writeToXML(fileName, properties, "");
	}
	
	/**
	 * 将各属性和备注追加写入到本地XML文件中
	 * @author Daniele 
	 * @param fileName 本地文件名
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void writeToXML(String fileName, Properties properties, String comments) throws IOException {
		writeToXML(new File(fileName), properties, comments);
	}
	
	/**
	 * 将各属性追加写入到本地XML文件中
	 * @author Daniele 
	 * @param file 本地文件
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void writeToXML(File file, Properties properties) throws IOException {
		writeToXML(file, properties, "");
	}
	
	/**
	 * 将各属性和备注追加写入到本地XML文件中
	 * @author Daniele 
	 * @param file 本地文件
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void writeToXML(File file, Properties properties, String comments) throws IOException {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file, true));
			writeToXML(out, properties, comments);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}
	
	/**
	 * 将各属性写入到输出流中
	 * @author Daniele 
	 * @param out 输出流
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void writeToXML(OutputStream out, Properties properties) throws IOException {
		writeToXML(out, properties, "", "");
	}
	
	/**
	 * 将各属性和备注写入到输出流中
	 * @author Daniele 
	 * @param out 输出流
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void writeToXML(OutputStream out, Properties properties, String comments) throws IOException {
		writeToXML(out, properties, comments, "");
	}
	
	/**
	 * 按指定的编码格式将各属性和备注写入到输出流中
	 * @author Daniele 
	 * @param out 输出流
	 * @param properties 属性对象
	 * @param comments 备注
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void writeToXML(OutputStream out, Properties properties, String comments, String encoding) throws IOException {
		if (StringUtils.isBlank(encoding)) 
			properties.storeToXML(out, StringUtils.safeString(comments));
		else
			properties.storeToXML(out, StringUtils.safeString(comments), encoding);
	}
	
	/**
	 * 按指定的编码格式将各属性写入到输出流中
	 * @author Daniele 
	 * @param out 输出流
	 * @param properties 属性对象
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void writeToXMLByEncoding(OutputStream out, Properties properties, String encoding) throws IOException {
		writeToXML(out, properties, "", encoding);
	}
	
	/**
	 * 按指定的编码格式将各属性追加写入到本地XML文件中
	 * @author Daniele 
	 * @param fileName 本地XML文件名
	 * @param properties 属性对象
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void writeToXMLByEncoding(String fileName, Properties properties, String encoding) throws IOException {
		writeToXMLByEncoding(fileName, properties, "", encoding);
	}
	
	/**
	 * 按指定的编码格式将各属性和备注追加写入到本地XML文件中
	 * @author Daniele 
	 * @param fileName 本地XML文件名
	 * @param properties 属性对象
	 * @param comments 备注
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void writeToXMLByEncoding(String fileName, Properties properties, String comments, String encoding) throws IOException {
		writeToXMLByEncoding(new File(fileName), properties, comments, encoding);
	}
	
	/**
	 * 按指定的编码格式将各属性追加写入到本地XML文件中
	 * @author Daniele 
	 * @param file
	 * @param properties
	 * @param encoding
	 * @throws IOException
	 */
	public static void writeToXMLByEncoding(File file, Properties properties, String encoding) throws IOException {
		writeToXMLByEncoding(file, properties, "", encoding);
	}
	
	/**
	 * 按指定的编码格式将各属性和备注追加写入到本地XML文件中
	 * @author Daniele 
	 * @param file 本地XML文件
	 * @param properties 属性对象
	 * @param comments 备注
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void writeToXMLByEncoding(File file, Properties properties, String comments, String encoding) throws IOException {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file, true));
			if (StringUtils.isNotBlank(encoding))
				writeToXML(out, properties, StringUtils.safeString(comments), encoding);
			else
				writeToXML(out, properties, StringUtils.safeString(comments));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}
		
	/**
	 * 将各属性重写入到本地文件
	 * @author Daniele 
	 * @param fileName 本地文件名
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void rewrite(String fileName, Properties properties) throws IOException {
		rewrite(new File(fileName), properties, "");
	}
	
	/**
	 * 将各属性和备注重写入到本地文件
	 * @author Daniele 
	 * @param fileName 本地文件名
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void rewrite(String fileName, Properties properties, String comments) throws IOException {
		rewrite(new File(fileName), properties, comments);
	}
	
	/**
	 * 将各属性重写入到本地文件
	 * @author Daniele 
	 * @param file 本地文件
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void rewrite(File file, Properties properties) throws IOException {
		rewrite(file, properties, "");
	}
	
	/**
	 * 将各属性和备注重写入到本地文件
	 * @author Daniele 
	 * @param file 本地文件
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void rewrite(File file, Properties properties, String comments) throws IOException {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			write(out, properties, comments);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}
	
	/**
	 * 将各属性重写入到本地XML文件
	 * @author Daniele 
	 * @param fileName 本地XML文件名
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void rewriteToXML(String fileName, Properties properties) throws IOException {
		rewriteToXML(new File(fileName), properties);
	}
	
	/**
	 * 将各属性和备注重写入到本地XML文件
	 * @author Daniele 
	 * @param fileName 本地XML文件名
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void rewriteToXML(String fileName, Properties properties, String comments) throws IOException {
		rewriteToXML(new File(fileName), properties, comments);
	}
	
	/**
	 * 将各属性重写入到本地XML文件
	 * @author Daniele 
	 * @param file 本地XML文件
	 * @param properties 属性对象
	 * @throws IOException
	 */
	public static void rewriteToXML(File file, Properties properties) throws IOException {
		rewriteToXML(file, properties, "");
	}
	
	/**
	 * 将各属性和备注重写入到本地XML文件
	 * @author Daniele 
	 * @param file 本地XML文件
	 * @param properties 属性对象
	 * @param comments 备注
	 * @throws IOException
	 */
	public static void rewriteToXML(File file, Properties properties, String comments) throws IOException {
		rewriteToXMLByEncoding(file, properties, comments, "");
	}
	
	/**
	 * 按指定的编码格式将各属性重写入到本地XML文件
	 * @author Daniele 
	 * @param fileName 本地XML文件名
	 * @param properties 属性对象
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void rewriteToXMLByEncoding(String fileName, Properties properties, String encoding) throws IOException {
		rewriteToXMLByEncoding(new File(fileName), properties, "", encoding);
	}
	
	/**
	 * 按指定的编码格式将各属性和备注重写入到本地XML文件
	 * @author Daniele 
	 * @param fileName 本地XML文件名
	 * @param properties 属性对象
	 * @param comments 备注
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void rewriteToXMLByEncoding(String fileName, Properties properties, String comments, String encoding) throws IOException {
		rewriteToXMLByEncoding(new File(fileName), properties, comments, encoding);
	}
	
	/**
	 * 按指定的编码格式将各属性重写入到本地XML文件
	 * @author Daniele 
	 * @param file 本地XML文件
	 * @param properties 属性对象
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void rewriteToXMLByEncoding(File file, Properties properties, String encoding) throws IOException {
		rewriteToXMLByEncoding(file, properties, "", encoding);
	}
	
	/**
	 * 按指定的编码格式将各属性和备注重写入到本地XML文件
	 * @author Daniele 
	 * @param file 本地XML文件
	 * @param properties 属性对象
	 * @param comments 备注
	 * @param encoding 字符集编码
	 * @throws IOException
	 */
	public static void rewriteToXMLByEncoding(File file, Properties properties, String comments, String encoding) throws IOException {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			if (StringUtils.isNotBlank(encoding))
				writeToXML(out, properties, StringUtils.safeString(comments), encoding);
			else
				writeToXML(out, properties, StringUtils.safeString(comments));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}
		
}
