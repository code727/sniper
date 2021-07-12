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
 * Create Date : 2016-6-18
 */

package org.sniper.image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.sniper.image.reader.ImageReader;
import org.sniper.image.writer.ImageWriter;

/**
 * 图片处理器
 * @author  Daniele
 * @version 1.0
 */
public interface ImageHandler {
	
	/**
	 * 设置图片读取器
	 * @author Daniele 
	 * @param imageReader
	 */
	public void setImageReader(ImageReader imageReader);
	
	/**
	 * 获取图片读取器
	 * @author Daniele 
	 * @return
	 */
	public ImageReader getImageReader();
	
	/**
	 * 设置图片写入器
	 * @author Daniele 
	 * @param imageWriter
	 */
	public void setImageWriter(ImageWriter imageWriter);
	
	/**
	 * 获取图片写入器
	 * @author Daniele 
	 * @return
	 */
	public ImageWriter getImageWriter();
	
	/**
	 * 将本地的图片资源文件进行处理
	 * @author Daniele 
	 * @param source 本地资源文件
	 * @throws Exception 
	 */
	public void handle(File source) throws IOException;
	
	/**
	 * 将本地的资源文件按照指定的格式进行处理
	 * @author Daniele 
	 * @param source 本地资源文件
	 * @throws Exception 
	 */
	public void handle(File source, String formatName) throws IOException;
	
	/**
	 * 将本地的资源文件进行处理后写入目标本地文件
	 * @author Daniele 
	 * @param source
	 * @param dest
	 * @throws Exception 
	 */
	public void handle(File source, File dest) throws IOException;
	
	/**
	 * 将本地的资源文件按照指定的格式进行处理后写入目标本地文件
	 * @author Daniele 
	 * @param source 本地资源文件
	 * @param formatName 格式
	 * @param dest 目标本地文件
	 * @throws IOException 
	 */
	public void handle(File source, String formatName, File dest) throws IOException;
	
	/**
	 * 将本地的资源文件进行处理后写入目标输出流
	 * @author Daniele 
	 * @param source 本地资源文件
	 * @param dest 目标输出流
	 * @throws IOException 
	 */
	public void handle(File source, OutputStream dest) throws IOException;
	
	/**
	 * 将本地的资源文件按照指定的格式进行处理后写入目标输出流
	 * @author Daniele 
	 * @param source 本地资源文件
	 * @param formatName 格式
	 * @param dest 目标输出流
	 * @throws IOException 
	 */
	public void handle(File source, String formatName, OutputStream dest) throws IOException;
	
	/**
	 * 将资源流进行处理后写入目标本地文件
	 * @author Daniele 
	 * @param source 资源流
	 * @param dest 目标本地文件
	 * @throws IOException 
	 */
	public void handle(InputStream source, File dest) throws IOException;
	
	/**
	 * 将资源流进行处理后写入目标输出流
	 * @author Daniele 
	 * @param source 资源流
	 * @param dest 目标输出流
	 * @throws IOException 
	 */
	public void handle(InputStream source, OutputStream dest) throws IOException;
	
	/**
	 * 将资源流按照指定格式进行处理后写入目标本地文件
	 * @author Daniele 
	 * @param source
	 * @param formatName
	 * @param dest
	 * @throws IOException 
	 */
	public void handle(InputStream source, String formatName, File dest) throws IOException;
	
	/**
	 * 将资源流按照指定格式进行处理后写入到目标输出流
	 * @author Daniele 
	 * @param source 图片资源流
	 * @param formatName 格式
	 * @param dest 目标输出流
	 * @throws IOException 
	 */
	public void handle(InputStream source, String formatName, OutputStream dest) throws IOException;

}
