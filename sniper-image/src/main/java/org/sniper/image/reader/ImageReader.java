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

import javax.imageio.stream.ImageInputStream;

/**
 * 图片读取器接口
 * @author  Daniele
 * @version 1.0
 */
public interface ImageReader {
	
	/**
	 * 读取本地图片
	 * @author Daniele 
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public BufferedImage read(File source) throws IOException;
	
	/**
	 * 读取输入流
	 * @author Daniele 
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public BufferedImage read(InputStream source) throws IOException;
	
	/**
	 * 读取图片输入流
	 * @author Daniele 
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public BufferedImage read(ImageInputStream source) throws IOException;
	
	/**
	 * 读取URL图片
	 * @author Daniele 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public BufferedImage read(URL url) throws IOException;
	
	/**
	 * 读取URI图片
	 * @author Daniele 
	 * @param uri
	 * @return
	 */
	public BufferedImage read(URI uri) throws IOException;
	
}
