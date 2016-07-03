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
 * Create Date : 2016-7-3
 */

package org.workin.image.writer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @description 图片写入器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ImageWriter {
	
	/**
	 * @description 将图片写入输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param image
	 * @param dest
	 * @throws IOException
	 */
	public void write(BufferedImage image, OutputStream dest) throws IOException;
	
	/**
	 * @description 将图片按指定格式写入输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param image
	 * @param formatName
	 * @param dest
	 * @throws IOException
	 */
	public void write(BufferedImage image, String formatName, OutputStream dest) throws IOException;
	
	/**
	 * @description 将图片写入指定文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param image
	 * @param dest
	 * @throws IOException
	 */
	public void write(BufferedImage image, File dest) throws IOException;
	
	/**
	 * @description 将图片按指定格式写入文件
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param image
	 * @param formatName
	 * @param dest
	 * @throws IOException
	 */
	public void write(BufferedImage image, String formatName, File dest) throws IOException;
	

}
