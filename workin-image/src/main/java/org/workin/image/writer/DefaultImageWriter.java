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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.workin.commons.util.FileUtils;
import org.workin.commons.util.IOUtils;
import org.workin.commons.util.StringUtils;

/**
 * 图片写入器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultImageWriter implements ImageWriter {
	
	/** 是否检查目标文件 */
	private boolean checkDestinationFile;
	
	public boolean isCheckDestinationFile() {
		return checkDestinationFile;
	}

	public void setCheckDestinationFile(boolean checkDestinationFile) {
		this.checkDestinationFile = checkDestinationFile;
	}

	@Override
	public void write(BufferedImage image, File dest) throws IOException {
		write(image, null, dest);
	}

	@Override
	public void write(BufferedImage image, String formatName, File dest) throws IOException {
		if (isCheckDestinationFile())
			checkDestination(dest);
		
		if (StringUtils.isBlank(formatName))
			// 格式未指定时，则按目标文件的格式写入
			formatName = FileUtils.getExtensionName(dest);
	
		OutputStream out = null;
		try {
			out = new FileOutputStream(dest);
			write(image, formatName, out);
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			IOUtils.close(out);
		}
	}
	
	@Override
	public void write(BufferedImage image, OutputStream dest) throws IOException {
		write(image, null, dest);
	}

	@Override
	public void write(BufferedImage image, String formatName, OutputStream dest) throws IOException {
		if (StringUtils.isBlank(formatName))
			formatName = "PNG";
		
		ImageIO.write(image, formatName.trim(), dest);
		dest.flush();
	}
	
	/**
	 * 检查目标图片
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dest
	 * @throws IOException
	 */
	protected void checkDestination(File dest) throws IOException {
		if (dest.isDirectory()) 
			throw new IOException("Destination [" + dest.getCanonicalPath() + "] is a file directory.");
		
		if (dest.exists() && !dest.canWrite())
			throw new IOException("Destination [" + dest.getCanonicalPath() + "] can not write." );
	}
	
}
