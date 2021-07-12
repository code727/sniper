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
 * Create Date : 2016-6-17
 */

package org.sniper.image.qrcode.generator;

import java.awt.image.BufferedImage;

import org.sniper.image.qrcode.QRCode;

/**
 * 二维码生成器接口
 * @author  Daniele
 * @version 1.0
 */
public interface QRCodeGenerator {
		
	/**
	 * 生成二维码图片对象
	 * @author Daniele 
	 * @param qrCode
	 * @return
	 * @throws Exception
	 */
	public BufferedImage generate(QRCode qrCode) throws Exception;


}
