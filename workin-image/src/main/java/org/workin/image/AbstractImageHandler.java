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

package org.workin.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.workin.commons.util.FileUtils;
import org.workin.commons.util.IOUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 图片处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractImageHandler implements ImageHandler {
	
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
		handle(source, null, dest);
	}
	
	@Override
	public void handle(File source, String formatName, File dest) throws IOException {
		checkSource(source);
		
		if (StringUtils.isBlank(formatName))
			// 格式未指定时，则按目标文件的格式写入
			formatName = FileUtils.getExtensionName(dest);

		if (StringUtils.isBlank(formatName))
			// 格式仍然为空时，再按原文件的格式写入
			formatName = FileUtils.getExtensionName(source);

		FileInputStream sourceIn = null;
		try {
			sourceIn = new FileInputStream(source);
			handle(sourceIn, formatName, dest);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(sourceIn);
		}
	}
	
	@Override
	public void handle(File source, OutputStream dest) throws IOException {
		handle(source, null, dest);
	}

	@Override
	public void handle(File source, String formatName, OutputStream dest) throws IOException {
		checkSource(source);
		
		if (StringUtils.isBlank(formatName)) 
			// 格式未指定时，则按源文件的格式写入
			formatName = FileUtils.getExtensionName(source);
		
		FileInputStream sourceIn = null;
		try {
			sourceIn = new FileInputStream(source);
			handle(sourceIn, formatName, dest);
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			IOUtils.close(sourceIn);
		}
	}

	@Override
	public void handle(InputStream source, File dest) throws IOException {
		handle(source, null, dest);
	}
	
	@Override
	public void handle(InputStream source, String formatName, File dest) throws IOException {
		checkDestination(dest);
		
		if (StringUtils.isBlank(formatName)) 
			// 格式未指定时，则按目标文件的格式写入
			formatName = FileUtils.getExtensionName(dest);
		
		FileOutputStream destOut = null;
		try {
			destOut = new FileOutputStream(dest);
			handle(source, formatName, destOut);
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			IOUtils.close(destOut);
		}
	}

	@Override
	public void handle(InputStream source, OutputStream dest) throws IOException {
		handle(source, null, dest);
	}

	@Override
	public void handle(InputStream source, String formatName, OutputStream dest) throws IOException {
		BufferedImage destImage = drawHandle(source);
		write(destImage, formatName, dest);
	}
	
	/**
	 * @description 将图像资源按照指定的格式写入到目标输出流
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param image
	 * @param formatName
	 * @param dest
	 * @throws IOException
	 */
	protected void write(BufferedImage image, String formatName, OutputStream dest) throws IOException {
		if (StringUtils.isBlank(formatName))
			formatName = "PNG";
		
		ImageIO.write(image, formatName.trim(), dest);
		dest.flush();
	}
	
	/**
	 * @description 检查原图片
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param source
	 * @throws IOException
	 */
	protected void checkSource(File source) throws IOException {
		if (!source.exists())
			throw new IOException("Image zoom failed, source [" + source.getAbsolutePath() + "] does not exist.");
		
		if (source.isDirectory())
			throw new IOException("Image zoom failed, source [" + source.getCanonicalPath() + "] is a file directory.");
		
		if (!source.canRead())
			throw new IOException("Image zoom failed, source [" + source.getCanonicalPath() + "] can not read." );
	}
	
	/**
	 * @description 检查目标图片
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dest
	 * @throws IOException
	 */
	protected void checkDestination(File dest) throws IOException {
		if (dest.isDirectory()) 
			throw new IOException("Image zoom failed, destination [" + dest.getCanonicalPath() + "] is a file directory.");
		
		if (dest.exists() && !dest.canWrite())
			throw new IOException("Image zoom failed, destination [" + dest.getCanonicalPath() + "] can not write." );
	}
	
	
	/**
	 * @description 图像资源绘制处理
	 * @author <a href="mailto:bin.du@daw.so">杜斌</a> 
	 * @param source 图像资源
	 * @return
	 * @throws IOException 
	 */
	protected BufferedImage drawHandle(InputStream source) throws IOException {
		BufferedImage sourceImage = ImageIO.read(source); 
		Pixel pixel = createTragetPixel(sourceImage);
		BufferedImage destImage = new BufferedImage(pixel.getWidth(), pixel.getHeight(), sourceImage.getType());
		
		Graphics2D graphics = destImage.createGraphics();
		graphics.drawImage(handSourceImage(sourceImage, pixel), 0, 0, null);
		graphics.dispose();
		return destImage;
	}
	
	/**
	 * @description 根据原图片创建目标图片的像素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sourceImage
	 * @return
	 */
	protected abstract Pixel createTragetPixel(BufferedImage sourceImage);
	
	/**
	 * @description 处理原图资源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sourceImage
	 * @param pixel
	 * @return
	 */
	protected Image handSourceImage(BufferedImage sourceImage, Pixel pixel) {
		// 保证原图在缩放时不失真
		return sourceImage.getScaledInstance(pixel.getWidth(), pixel.getHeight(), Image.SCALE_SMOOTH);
	}
	
}
