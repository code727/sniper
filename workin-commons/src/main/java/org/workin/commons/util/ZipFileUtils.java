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
 * Create Date : 2015-1-21
 */

package org.workin.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @description ZIP文件工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ZipFileUtils {
	
	/** 实体分割标识符 */
	public static final String ENTRY_SEPERATOR = "/";
	
	/** ZIP扩展名 */
	public static final String EXTENSION_NAME = ".zip";
		
	/**
	 * @description 创建一个ZIP文件。若先前存在则直接返回
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathname
	 * @return
	 */
	public static ZipFile createNewZip(String pathname) throws IOException {
		AssertUtils.assertTrue(StringUtils.isNotBlank(pathname), "Zip file path can not be null or blank.");
		return createNewZip(new File(pathname));
	}
	
	/**
	 * @description 创建一个ZIP文件。若先前存在则直接返回
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @return
	 */
	public static ZipFile createNewZip(File file) throws IOException {
		return createNewZip(file, null);
	}
	
	/**
	 * @description 创建一个指定字符集的ZIP文件。若先前存在则直接返回。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param chatset
	 * @return
	 * @throws IOException
	 */
	public static ZipFile createNewZip(File file, String chatset) throws IOException {
		AssertUtils.assertNotNull(file, "Zip file can not be null.");
		if (file.isDirectory())
			throw new IOException("Zip file [" + file + "] can not be is a directory");
		
		if (file.exists()) 
			// 直接返回一个已存在的文件
			return new ZipFile(file);
		
		/* 新创建一个空实体的ZIP文件 */
		ZipOutputStream out = null;
		try {
			File parent = file.getParentFile();
			if (parent != null && !parent.exists())
				parent.mkdirs();

			if (StringUtils.isNotBlank(chatset))
				out = new ZipOutputStream(new FileOutputStream(file), Charset.forName(chatset));
			else
				out = new ZipOutputStream(new FileOutputStream(file));
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			IOUtils.close(out);
		}
		return new ZipFile(file);
	}
	
	/**
	 * @description 创建ZIP文件内的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @param name
	 * @throws IOException
	 */
	public static void createEntries(ZipFile zipFile, String name) throws IOException {
		if (StringUtils.isBlank(name))
			return;
		
		name = name.trim();
		if (zipFile.getEntry(name) != null) {
			ZipOutputStream out = null;
			try {
				out = new ZipOutputStream(new FileOutputStream(new File(zipFile.getName())));
				out.putNextEntry(new ZipEntry(name));
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(out);
			}
		}
	}
	
	/**
	 * @description 创建ZIP输出流的实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param out
	 * @param name
	 */
	public static void createEntries(ZipOutputStream out, String name) throws IOException {
		if (StringUtils.isBlank(name))
			return;
		
		out.putNextEntry(new ZipEntry(name.trim()));
		out.flush();
	}
			
	/**
	 * @description 获取ZIP文件的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @return
	 */
	public static String getName(ZipFile zipFile) {
		if (zipFile == null)
			return StringUtils.EMPTY_STRING;
		
		return StringUtils.afterLast(zipFile.getName(), File.separator);
	}
	
	/**
	 * @description 获取ZIP压缩文件的主名（不包含扩展名的名称）
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @return
	 */
	public static String getMainName(ZipFile zipFile) {
		String name = getName(zipFile);
		String mainName = StringUtils.beforeLast(name, FileUtils.EXTENSION_SEPERATOR);
		return StringUtils.isNotEmpty(mainName) ? mainName : name;
	}
	
	/**
	 * @description 获取ZIP文件的扩展名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @return
	 */
	public static String getExtensionsName(ZipFile zipFile) {
		return StringUtils.afterLast(getName(zipFile), FileUtils.EXTENSION_SEPERATOR);
	}
	
	/**
	 * @description 获取ZIP文件所在的上一级文件/目录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @return
	 */
	public static File getParentFile(ZipFile zipFile) {
		if (zipFile == null)
			return null;
		
		return new File(zipFile.getName()).getParentFile();
	}
	
	/**
	 * @description 将源文件压缩到同目录同名的ZIP文件中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public static ZipFile compress(File src) throws IOException {
		return compress(src, (File)null);
	}
	
	/**
	 * @description 将源文件压缩到指定ZIP文件/目录中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param destPath
	 * @throws IOException
	 */
	public static ZipFile compress(File src, String destPath) throws IOException {
		if (StringUtils.isBlank(destPath)) 
			// 目标路径为空时，则压缩到与源文件同目录同名的ZIP文件中
			destPath = src.getParent() + File.separator + FileUtils.getMainName(src) + EXTENSION_NAME;
		
		return compress(src, new File(destPath));
	}
		
	/**
	 * @description 将源文件压缩到指定的ZIP文件/目录中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param dest
	 * @return
	 * @throws IOException
	 */
	public static ZipFile compress(File src, File dest) throws IOException {
		if (!src.exists()) 
			throw new FileNotFoundException("Compression source file [" + src + "] not found.");
		
		if (src.isFile() && src.equals(dest))
			throw new IOException("Compression source file [" + src + "] same with destination.");
		
		ZipFile zipFile;
		if (dest == null) {
			// 目标路径为空时，则压缩到与源文件同目录同名的ZIP文件中
			String destZipName = src.getParent() + File.separator + FileUtils.getMainName(src) + EXTENSION_NAME;
			dest = new File(destZipName);
			zipFile = createNewZip(dest);
		}
		
		if (!dest.exists())	{
			if (FileUtils.hasExtensionName(dest))
				zipFile = createNewZip(dest);
			else
				dest.mkdirs();
		}
				
		if (dest.isDirectory()) {	
			// 压缩到目标目录下同名的ZIP文件中
			zipFile = createNewZip(dest + File.separator + FileUtils.getMainName(src) + EXTENSION_NAME);
		} else 
			zipFile = createNewZip(dest);
		
		compress(src, zipFile);
		return zipFile;
	}
	
	/**
	 * @description 将源文件压缩到指定的ZIP文件中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param dest
	 * @param append
	 * @throws IOException
	 */
	public static void compress(File src, ZipFile dest) throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(dest.getName()));
		try {
			String destZipName = dest.getName().replace("\\", ENTRY_SEPERATOR);
			compress(src, destZipName, out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}
	
	/**
	 * @description 将源文件/目录压缩写入到目标ZIP的输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param out
	 * @throws IOException
	 */
	public static void compress(File src, ZipOutputStream out) throws IOException {
		
		if (!src.exists()) 
			throw new FileNotFoundException("Compression source file [" + src + "] not found.");
		
		if (src.isFile()) 
			compressFile(src, src.getName(), out);
		else
			compressDir(src, null, src.getName() + ENTRY_SEPERATOR, out);
	}
	
	/**
	 * @description 将源文件/目录压缩写入到目标ZIP的输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param destZipName 目标ZIP的名称。
	 * @param out
	 * @throws IOException
	 */
	private static void compress(File src, String destZipName, ZipOutputStream out) throws IOException {
		AssertUtils.assertNotNull(src, "Compression source file can not be null.");
		
		if (!src.exists()) 
			throw new FileNotFoundException("Compression source file [" + src + "] not found.");
		
		if (src.isFile()) 
			compressFile(src, src.getName(), out);
		else
			compressDir(src, destZipName, src.getName() + ENTRY_SEPERATOR,  out);
	}
	
	/** 
	 * @description 将源文件按照指定的名称写入ZipOutputStream对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @param name 实体名称
	 * @param out
	 * @throws IOException
	 */
	private static void compressFile(File src, String name, ZipOutputStream out) throws IOException {
		BufferedInputStream in = null; 
		try {
			in = new BufferedInputStream(new FileInputStream(src));
			createEntries(out, name);
			IOUtils.write(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(in);
		}
	}
	
	/**
	 * @description 将源文件目录按照指定的名称写入ZipOutputStream对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dir
	 * @param out
	 * @param destZipName 目标ZIP文件的名称
	 * @param prevEntryName 上一级实体名称
	 * @throws IOException
	 */
	private static void compressDir(File dir, String destZipName, String prevEntryName, ZipOutputStream out) throws IOException {		
		File[] files = dir.listFiles();
		if (ArrayUtils.isNotEmpty(files)) {
			String entryName;
			for (File file : files) {
				if (file.isFile()) {
					entryName = prevEntryName + file.getName();
					/*
					 * 如果目标ZIP文件是源目录的子集，
					 * 则会出现生成的ZIP文件压缩自身的问题，
					 * 因此需要作判断排除这种情况
					 */
					if (!StringUtils.endsWith(destZipName, entryName))
						compressFile(file, entryName, out);
				} else {
					entryName = prevEntryName + file.getName() + ENTRY_SEPERATOR;
					createEntries(out, entryName);
					compressDir(file, destZipName, entryName, out);
				}
			}
		} else 
			// 创建一个空文件夹实体
			createEntries(out, prevEntryName);
	}
	
	/**
	 * @description 将ZIP文件解压到当前所在的父目录内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @return 目标目录的第一级子目录/文件
	 * @throws IOException
	 */
	public static File[] uncompress(ZipFile zipFile) throws IOException {
		return uncompress(zipFile, false);
	}
	
	/**
	 * @description 选择是否按追加的方式将ZIP文件解压到当前所在的父目录内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @param append
	 * @return 解压后的资源根目录/文件
	 * @throws IOException
	 */
	public static File[] uncompress(ZipFile zipFile, boolean append) throws IOException {
		return uncompress(zipFile, getParentFile(zipFile), append);
	}
	
	/**
	 * @description 将ZIP文件解压到指定的目录内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @param dir
	 * @return 目标目录的第一级子目录/文件
	 * @throws IOException
	 */
	public static File[] uncompress(ZipFile zipFile, String dir) throws IOException {
		return uncompress(zipFile, dir, false);
	}
	
	/**
	 * @description 选择是否按追加的方式将ZIP文件解压到指定的目录内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @param dir
	 * @param append
	 * @return 目标目录的第一级子目录/文件
	 * @throws IOException
	 */
	public static File[] uncompress(ZipFile zipFile, String dir, boolean append) throws IOException {
		return StringUtils.isNotBlank(dir) ? uncompress(zipFile, new File(dir),
				append) : uncompress(zipFile, append);
	}
	
	/**
	 * @description 将ZIP文件解压到指定的目录内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @param dest
	 * @return 目标目录的第一级子目录/文件
	 * @throws IOException
	 */
	public static File[] uncompress(ZipFile zipFile, File dest) throws IOException {
		 return uncompress(zipFile, dest, false);
	}
	
	/**
	 * @description 选择是否按追加的方式将ZIP文件解压到指定的目录内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param zipFile
	 * @param dest
	 * @param append
	 * @return 目标目录的第一级子目录/文件
	 * @throws IOException
	 */
	public static File[] uncompress(ZipFile zipFile, File dest, boolean append) throws IOException {
		AssertUtils.assertNotNull(zipFile, "Uncompress zip source can not be null.");
		
		if (dest == null)
			dest = getParentFile(zipFile);
		
		if (dest.isFile())
			throw new IOException("Can not uncompress to destination [" + 
					dest + "], because it isn't a directory.");
		
		if (!dest.exists())
			dest.mkdirs();
		
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		List<File> files = CollectionUtils.newArrayList();
		ZipEntry entry;
		File subDest;
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();
			if (entry.isDirectory()) {
				subDest = new File(dest + File.separator + entry.getName());
				if (!subDest.exists())
					subDest.mkdir();
			} else {
				subDest = new File(dest + File.separator + entry.getName());
				FileUtils.copy(zipFile.getInputStream(entry), subDest, append);
			}
			
			// 只添加目标目录的第一级子目录/文件到结果集
			if (dest.equals(subDest.getParentFile()))
				files.add(subDest);
		}
		return CollectionUtils.toArray(files,File.class);
	}
	
}
