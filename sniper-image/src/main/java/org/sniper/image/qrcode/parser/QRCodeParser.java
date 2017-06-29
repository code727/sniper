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

package org.sniper.image.qrcode.parser;

import java.awt.image.BufferedImage;

/**
 * 二维码解析器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface QRCodeParser {
	
	/**
	 * 解析出二维码图片
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCodeImage
	 * @return
	 * @throws Exception
	 */
	public String parse(BufferedImage qrCodeImage) throws Exception;
	
	/**
	 * 按指定的字符集编码解析出二维码图片
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCodeImage
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public String parse(BufferedImage qrCodeImage, String encoding) throws Exception;

}
