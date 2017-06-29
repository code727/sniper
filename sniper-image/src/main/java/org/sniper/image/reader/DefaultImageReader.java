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
 * Create Date : 2016-7-6
 */

package org.sniper.image.reader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 * 图片读取器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultImageReader implements ImageReader {
	
	/** 是否检查原文件 */
	private boolean checkSourceFile;
	
	public boolean isCheckSourceFile() {
		return checkSourceFile;
	}

	public void setCheckSourceFile(boolean checkSourceFile) {
		this.checkSourceFile = checkSourceFile;
	}

	@Override
	public BufferedImage read(File source) throws IOException {
		if (isCheckSourceFile())
			checkSource(source);
		
		return ImageIO.read(source);
	}

	@Override
	public BufferedImage read(InputStream source) throws IOException {
		return ImageIO.read(source);
	}

	@Override
	public BufferedImage read(ImageInputStream source) throws IOException {
		return ImageIO.read(source);
	}

	@Override
	public BufferedImage read(URL url) throws IOException {
		return ImageIO.read(url);
	}

	@Override
	public BufferedImage read(URI uri) throws IOException {
		return read(uri.toURL());
	}
	
	/**
	 * 检查原图片
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param src
	 * @throws IOException
	 */
	protected void checkSource(File source) throws IOException {
		if (!source.exists())
			throw new IOException("Source [" + source.getAbsolutePath() + "] does not exist.");
		
		if (source.isDirectory())
			throw new IOException("Source [" + source.getCanonicalPath() + "] is a file directory.");
		
		if (!source.canRead())
			throw new IOException("Source [" + source.getCanonicalPath() + "] can not read." );
	}

}
