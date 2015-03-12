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
 * Create Date : 2015-1-15
 */

package org.workin.support.multimedia.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.workin.commons.util.StringUtils;

/**
 * @description AWT图像缩放处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractAWTImageZoomHandler extends AbstractImageZoomHandler {
	
	@Override
	public void handle(File source) throws IOException {
		handle(source, source);
	}
	
	@Override
	public void handle(File source, String formatName) throws IOException {
		handle(source, formatName, source);
	}
	
	@Override
	public void handle(File source, File dest) throws IOException {
		handle(source, StringUtils.afterLast(source.getName(), "."), dest);
	}
	
	@Override
	public void handle(File source, String formatName, File dest) throws IOException {
		if (!source.exists())
			throw new IOException("Image zoom failed, source ["
					+ source.getAbsolutePath() + "] does not exist.");
		else if (source.isDirectory())
			throw new IOException("Image zoom failed, source ["
					+ source.getCanonicalPath() + "] is a file directory.");
		else 
			// 按指定的格式写入目标文件，格式未指定时，则按源文件的格式写入
			handle(new FileInputStream(source), StringUtils.isNotBlank(formatName) ? 
					formatName.trim() : StringUtils.afterLast(source.getName(), "."), dest);
	}
	
	@Override
	public void handle(File source, OutputStream dest) throws IOException {
		handle(source, null, dest);
	}

	@Override
	public void handle(File source, String formatName, OutputStream dest) throws IOException {
		if (!source.exists())
			throw new IOException("Image zoom failed, source ["
					+ source.getAbsolutePath() + "] does not exist.");
		else if (source.isDirectory())
			throw new IOException("Image zoom failed, source ["
					+ source.getCanonicalPath() + "] is a file directory.");
		else 
			// 按指定的格式写入目标文件，格式未指定时，则按源文件的格式写入
			handle(new FileInputStream(source), StringUtils.isNotBlank(formatName) ? 
					formatName.trim() : StringUtils.afterLast(source.getName(), "."), dest);
	}

	@Override
	public void handle(InputStream source, File dest) throws IOException {
		handle(source, null, dest);
	}
	
	@Override
	public void handle(InputStream source, String formatName, File dest) throws IOException {
		if (dest.isDirectory()) 
			throw new IOException("Image zoom failed, destination ["
					+ dest.getCanonicalPath() + "] is a file directory.");
		else if (dest.exists() && !dest.canWrite())
			throw new IOException("Image zoom failed, no write access to the destination ["
					+ dest.getCanonicalPath() + "]");
		else 
			// 按指定的格式写入目标文件，格式未指定时，则按目标文件的格式写入
			handle(source, StringUtils.isNotBlank(formatName) ? 
					formatName.trim() : StringUtils.afterLast(dest.getName(), "."), 
					new FileOutputStream(dest));
	}

	@Override
	public void handle(InputStream source, OutputStream dest) throws IOException {
		handle(source, null , dest);
	}

	@Override
	public void handle(InputStream source, String formatName, OutputStream dest) throws IOException {
		BufferedImage destImage = drawHandle(source);
		write(destImage, formatName, dest);
	}
	
	/**
	 * @description 将图像资源按照指定的格式写入到指定的本地目标文件
	 * @author <a href="mailto:bin.du@daw.so">杜斌</a> 
	 * @param image 图像资源
	 * @param formatName 格式
	 * @param dest 本地目标文件
	 * @throws IOException 
	 */
	protected void write(BufferedImage image, String formatName, File dest) throws IOException {
		write(image, formatName, new FileOutputStream(dest));
	}
	
	/**
	 * @description 将图像资源按照指定的格式写入到目标输出流
	 * @author <a href="mailto:bin.du@daw.so">杜斌</a> 
	 * @param image 图像资源
	 * @param formatName 格式
	 * @param dest 本地输出流
	 * @throws IOException 
	 */
	protected void write(BufferedImage image, String formatName, OutputStream dest) throws IOException {
		ImageIO.write(image, StringUtils.safeString(formatName), dest);
	}
	
	/**
	 * @description 图像资源绘制处理
	 * @author <a href="mailto:bin.du@daw.so">杜斌</a> 
	 * @param source 图像资源
	 * @return
	 * @throws IOException 
	 */
	protected abstract BufferedImage drawHandle(InputStream source) throws IOException;

}
