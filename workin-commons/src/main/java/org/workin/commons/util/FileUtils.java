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
 * Create Date : 2014-12-11
 */

package org.workin.commons.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.FileChannel;

/**
 * @description 文件工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FileUtils {
	
	/** 文件名扩展标识 */
	public static final String EXTENSION_SEPERATOR = ".";
		
	/**
	 * @description 获取指定路径下的文件名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filePath
	 * @return
	 */
	public static String getName(String filePath) {
		if (StringUtils.isBlank(filePath))
			return StringUtils.EMPTY_STRING;
		
		String name = StringUtils.afterLast(filePath, "/");
		if (StringUtils.isEmpty(name))
			name = StringUtils.afterLast(filePath, "\\");
		
		return StringUtils.isNotEmpty(name) ? name : filePath;
	}
	
	/**
	 * @description 获取文件的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @return
	 */
	public static String getName(File file) {
		return file != null ? file.getName() : StringUtils.EMPTY_STRING;
	}
	
	/**
	 * @description 获取指定路径下的文件的主名（不包含扩展名的名称）
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filePath
	 * @return
	 */
	public static String getMainName(String filePath) {
		String name = getName(filePath);
		String mainName = StringUtils.beforeLast(name, EXTENSION_SEPERATOR);
		return StringUtils.isNotEmpty(mainName) ? mainName : name;
	}
	
	/**
	 * @description 获取文件的主名（不包含扩展名的名称）
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @return
	 */
	public static String getMainName(File file) {
		String name = getName(file);
		String mainName = StringUtils.beforeLast(name, EXTENSION_SEPERATOR);
		return StringUtils.isNotEmpty(mainName) ? mainName : name;
	}
	
	/**
	 * @description 获取指定路径下的文件的扩展名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filePath
	 * @return
	 */
	public static String getExtensionName(String filePath) {
		return StringUtils.afterLast(filePath, EXTENSION_SEPERATOR);
	}
	
	/**
	 * @description 获取文件的扩展名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @return
	 */
	public static String getExtensionName(File file) {
		return StringUtils.afterLast(getName(file), EXTENSION_SEPERATOR);
	}
	
	/**
	 * @description 判断文件有无扩展名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @return
	 */
	public static boolean hasExtensionName(File file) {
		return StringUtils.isNotEmpty(getExtensionName(file));
	}
	
	/**
	 * @description 按默认字符串编码格式和缓冲区大小读取文件的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static String read(File file) throws IOException {
		return read(file, CodecUtils.UTF8_ENCODING, IOUtils.DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按指定字符串编码格式和默认缓冲区大小读取文件的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String read(File file, String encoding) throws IOException {
		return read(file, encoding, IOUtils.DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按默认字符串编码格式和指定缓冲区大小读取文件的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String read(File file, int bufferSize) throws IOException {
		return read(file, CodecUtils.UTF8_ENCODING, bufferSize);
	}
	
	/**
	 * @description 按指定字符串编码格式和缓冲区大小读取文件的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String read(File file, String encoding, int bufferSize) throws IOException {
		if (file == null || !file.exists())
			return null;
		
		InputStream in = null;
		String content = null;
		try {
			in = new FileInputStream(file);
			content = IOUtils.read(in, encoding, bufferSize);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(in);
		}
		return content;
	}
	
	/**
	 * @description 按默认的字符集编码和缓冲区大小读取指定行号的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @return
	 * @throws IOException
	 */
	public static String readLine(File file, int lineNumber) throws IOException {
		return readLine(file, lineNumber, CodecUtils.UTF8_ENCODING, IOUtils.DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按指定的字符集编码和默认的缓冲区大小读取指定行号的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readLine(File file, int lineNumber, String encoding) throws IOException {
		return readLine(file, lineNumber, encoding, IOUtils.DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按默认的字符集编码和指定的缓冲区大小读取指定行号的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String readLine(File file, int lineNumber, int bufferSize) throws IOException {
		return readLine(file, lineNumber, CodecUtils.UTF8_ENCODING, bufferSize);
	}
	
	/**
	 * @description 按指定的字符集编码和缓冲区大小读取指定行号的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @return
	 * @throws IOException
	 */
	public static String readLine(File file, int lineNumber, String encoding, int bufferSize) throws IOException {
		if (lineNumber < 0 || file == null || !file.exists())
			return null;
		
		LineNumberReader reader = null;
		String lineContent = null;
		try {
			reader = IOUtils.newLineNumberReader(new FileInputStream(file), encoding, bufferSize);
			String content;
			while ((content = reader.readLine()) != null) {
				if (lineNumber == (reader.getLineNumber() - 1)) {
					lineContent = content;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(reader);
		}
		return lineContent;
	}
	
	/**
	 * @description 按默认的字符集编码和缓冲区大小读取指定行号之前的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @return
	 * @throws IOException
	 */
	public static String readBefore(File file, int lineNumber) throws IOException {
		return readBefore(file, lineNumber, CodecUtils.UTF8_ENCODING, IOUtils.DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按指定的字符集编码和默认的缓冲区大小读取指定行号之前的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readBefore(File file, int lineNumber, String encoding) throws IOException {
		return readBefore(file, lineNumber, encoding, IOUtils.DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按默认的字符集编码和指定的缓冲区大小读取指定行号之前的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String readBefore(File file, int lineNumber, int bufferSize) throws IOException {
		return readBefore(file, lineNumber, CodecUtils.UTF8_ENCODING, bufferSize);
	}
	
	/**
	 * @description 按指定的字符集编码和缓冲区大小读取指定行号之前的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String readBefore(File file, int lineNumber, String encoding, int bufferSize) throws IOException {
		if (lineNumber <= 0 || file == null || !file.exists())
			return null;
		
		LineNumberReader reader = null;
		try {
			reader = IOUtils.newLineNumberReader(new FileInputStream(file), encoding, bufferSize);
			StringBuilder lineContent = new StringBuilder();
			String textNewline = SystemUtils.getTextNewline();
			String content;
			while ((content = reader.readLine()) != null && lineNumber > (reader.getLineNumber() - 1)) {
				if (lineContent.length() > 0)
					lineContent.append(textNewline);
				lineContent.append(content);
			}
			return lineContent.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(reader);
		}
		return null;
	}
	
	/**
	 * @description 按默认的字符集编码和缓冲区大小读取指定行号之后的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @return
	 * @throws IOException
	 */
	public static String readAfter(File file, int lineNumber) throws IOException {
		return readAfter(file, lineNumber, CodecUtils.UTF8_ENCODING, IOUtils.DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按指定的字符集编码和默认的缓冲区大小读取指定行号之后的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readAfter(File file, int lineNumber, String encoding) throws IOException {
		return readAfter(file, lineNumber, encoding, IOUtils.DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * @description 按默认的字符集编码和指定的缓冲区大小读取指定行号之后的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String readAfter(File file, int lineNumber, int bufferSize) throws IOException {
		return readAfter(file, lineNumber, CodecUtils.UTF8_ENCODING, bufferSize);
	}
	
	/**
	 * @description 按指定的字符集编码和缓冲区大小读取指定行号之后的内容
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param lineNumber
	 * @param encoding
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static String readAfter(File file, int lineNumber, String encoding, int bufferSize) throws IOException {
		if (file == null || !file.exists())
			return null;
		
		LineNumberReader reader = null;
		try {
			reader = IOUtils.newLineNumberReader(new FileInputStream(file), encoding, bufferSize);
			StringBuilder lineContent = new StringBuilder();
			String textNewline = SystemUtils.getTextNewline();
			String content;
			while ((content = reader.readLine()) != null) {
				if (lineNumber < (reader.getLineNumber() - 1)) {
					if (lineContent.length() > 0)
						lineContent.append(textNewline);
					lineContent.append(content);
				}
			}
			return lineContent.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(reader);
		}
		return null;
	}
	
	/**
	 * @description 将输入流的数据拷贝到目标
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param file
	 * @throws IOException
	 */
	public static void copy(InputStream in, File file) throws IOException {
		copy(in, file, false);
	}
	
	/**
	 * @description 按是否追加的方式将源输入流的内容拷贝到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param file
	 * @param append
	 * @throws IOException
	 */
	public static void copy(InputStream in, File file, boolean append) throws IOException {
		AssertUtils.assertNotNull(in, "Copied source InputStream object can not be null.");
		AssertUtils.assertNotNull(file, "Copied destination file can not be null.");
		if (file.isDirectory())
			throw new IOException("Copied destination [" + file + "] can not be a directory.");
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file, append);
			IOUtils.write(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}
	
	/**
	 * @description 将读取器的内容按默认字符集编码格式写入到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param file
	 * @throws IOException
	 */
	public static void copy(Reader reader, File file) throws IOException {
		copy(reader, file, CodecUtils.UTF8_ENCODING, false);
	}
	
	/**
	 * @description 将读取器的内容按指定字符集编码格式写入到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param file
	 * @throws IOException
	 */
	public static void copy(Reader reader, File file, String encoding) throws IOException {
		copy(reader, file, encoding, false);
	}
	
	/**
	 * @description 按是否追加的方式将读取器的内容以指定字符集编码格式写入到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param file
	 * @param encoding
	 * @param append
	 * @throws IOException
	 */
	public static void copy(Reader reader, File file, String encoding, boolean append) throws IOException {
		AssertUtils.assertNotNull(reader, "Copied source Reader object can not be null.");
		AssertUtils.assertNotNull(file, "Copied destination file can not be null.");
		if (file.isDirectory())
			throw new IOException("Copied destination [" + file + "] can not be a directory.");
			
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file, append), IOUtils.DEFAULT_BUFFER_SIZE);
			IOUtils.write(reader, out, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}
	
	/**
	 * @description 将源文件/目录的内容拷贝到目标文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src 
	 * @param dest
	 */
	public static void copy(File src, File dest) throws IOException {
		copy(src, dest, null, false);
	}
	
	/**
	 * @description 将源文件/目录的内容拷贝到目标文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param dest
	 * @param filter 针对于源目录的文件过滤器
	 */
	public static void copy(File src, File dest, FileFilter filter) throws IOException {
		copy(src, dest, filter, false);
	}
	
	/**
	 * @description 按是否追加的方式将源文件内容拷贝到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param dest
	 * @param append
	 * @throws IOException
	 */
	public static void copy(File src, File dest, boolean append) throws IOException {
		copy(src, dest, null, append);
	}
	
	/**
	 * @description 按是否追加的方式将源文件内容拷贝到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param dest
	 * @param filter 针对于源目录的文件过滤器
	 * @param append
	 * @throws IOException
	 */
	public static void copy(File src, File dest, FileFilter filter, boolean append) throws IOException {
		AssertUtils.assertNotNull(src, "Copied source can not be null.");
		AssertUtils.assertNotNull(dest, "Copied destination can not be null.");
		
		if (!src.exists())
			throw new IOException("Copied source [" + src + "] not exists.");
		
		if (src.isDirectory()) {
			if (dest.isFile())
				throw new IOException("Can copy source directory [" + src
						+ "] to destination file [" + dest + "].");
			
			if (!dest.exists())
				dest.mkdirs();
			
			/* 源目录拷贝到目标目录下 */
			File[] files = src.listFiles(filter);
			for (File file : files) {
				if (file.isDirectory())
					// 维持源目录结构拷贝到目标的下一级目录
					copy(file, new File(dest + File.separator + file.getName()), append);
				else {
					File destFile = new File(dest.getAbsolutePath() + File.separator + file.getName());
					if (destFile.isDirectory())
						copy(file, destFile, append);
					else
						copyFile(file, destFile, append);
				}
			}
		} else {
			if (dest.isDirectory()) {
				/* 源文件拷贝到目标目录下  */
				File destFile = new File(dest.getAbsolutePath() + File.separator + src.getName());
				if (destFile.isDirectory())
					copy(src, destFile, append);
				else
					copyFile(src, destFile, append);
			} else
				/* 源文件拷贝到目标文件  */
				copyFile(src, dest, append);
		}
	}
	
	/**
	 * @description 将URL的内容拷贝到本地文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	public static void copy(String url, File file) throws IOException {
		AssertUtils.assertTrue(StringUtils.isNotBlank(url), "Copied url can not be null or blank.");
		copy(new URL(url), file);
	}
	
	/**
	 * @description 将URL的内容拷贝到本地文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	public static void copy(URL url, File file) throws IOException {
		if (file.isDirectory())
			throw new IOException("Copied destination [" + file + "] can not be a directory.");
		
		IOUtils.write(url.openStream(), new FileOutputStream(file));
	}
	
	/**
	 * @description 按是否追加的方式将源文件内容拷贝到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src 源文件
	 * @param dest 目标文件
	 * @param append
	 * @throws IOException
	 */
	private static void copyFile(File src, File dest, boolean append) throws IOException {
		if (append || !src.equals(dest)) {
			FileInputStream in = null;
			FileOutputStream out = null;
			FileChannel inChannel = null; 
			FileChannel outChannel = null;;
			try {
				in = new FileInputStream(src);
				out = new FileOutputStream(dest, append);
				inChannel = in.getChannel();
				outChannel = out.getChannel();
				outChannel.transferFrom(inChannel, outChannel.position(), inChannel.size());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(inChannel);
				IOUtils.close(outChannel);
				IOUtils.close(in);
				IOUtils.close(out);
			}
		}
	}
		
	/**
	 * @description 按追加的方式将源输入流的内容拷贝到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param in
	 * @param file
	 * @throws IOException
	 */
	public static void appendCopy(InputStream in, File file) throws IOException {
		copy(in, file, true);
	}
	
	/**
	 * @description 以默认UTF-8字符集编码格式并按追加的方式将读取器的内容写入到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param file
	 * @throws IOException
	 */
	public static void appendCopy(Reader reader, File file) throws IOException {
		copy(reader, file, CodecUtils.UTF8_ENCODING, true);
	}
	
	/**
	 * @description 以指定的字符集编码格式并按追加的方式将读取器的内容写入到目标文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param reader
	 * @param file
	 * @param encoding
	 * @throws IOException
	 */
	public static void appendCopy(Reader reader, File file, String encoding) throws IOException {
		copy(reader, file, encoding, true);
	}
	
	/**
	 * @description 将源文件/目录内容追加拷贝到目标文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void appendCopy(File src, File dest) throws IOException {
		copy(src, dest, null, true);
	}
	
	/**
	 * @description 将源文件/目录内容追加拷贝到目标文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param dest
	 * @param filter 针对于源目录的文件过滤器
	 * @throws IOException
	 */
	public static void appendCopy(File src, File dest, FileFilter filter) throws IOException {
		copy(src, dest, filter, true);
	}
	
	/**
	 * @description 删除文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param path
	 */
	public static void delete(String path) {
		if (StringUtils.isNotBlank(path))
			delete(new File(path));
	}
	
	/**
	 * @description 删除文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dest
	 */
	public static void delete(File dest) {
		delete(dest, null);
	}
	
	/**
	 * @description 删除文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dest
	 * @param filter 针对于源目录的文件过滤器
	 */
	public static void delete(File dest, FileFilter filter) {
		if (dest !=  null) {
			if (dest.isDirectory()) {
				File[] files = dest.listFiles(filter);
				if (files.length > 0) {
					for (File file : files) 
						delete(file);
				} 
			} 
			dest.delete();
		}
	}
	
	/**
	 * @description 获取文件/目录的大小(字节)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dest
	 * @return
	 */
	public static long size(File dest) {
		return size(dest, null);
	}
	
	/**
	 * @description 获取文件/目录的大小(字节)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dest
	 * @param filter 针对于目录的文件过滤器
	 * @return
	 */
	public static long size(File dest, FileFilter filter) {
		if (dest == null || !dest.exists())
			return 0;
		
		if (dest.isFile())
			return dest.length();
		
		if (dest.getParentFile() == null) 
			// 返回根目录已使用的大小
			return dest.getTotalSpace() - dest.getUsableSpace();
			
		
		return directorySize(dest, filter, 0L);
	}
	
	/**
	 * @description 累加目录的大小(字节)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dir
	 * @param filter
	 * @param size
	 * @return
	 */
	private static long directorySize(File dir, FileFilter filter, long size) {
		File[] files = dir.listFiles(filter);
		for (File file : files) {
			if (file.isFile())
				size += file.length();
			else 
				size = directorySize(file, filter, size);
		}
		return size;
	}
	
	/**
	 * @description 获取系统级的临时目录路径
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static String getSystemTempDirPath() {
	    return System.getProperty("java.io.tmpdir");
	}
	
	/**
	 * @description 获取系统级的临时目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static File getSystemTempDir() {
	    return new File(getSystemTempDirPath());
	}
			
}
