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

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import org.sniper.commons.util.CodecUtils;

/**
 * jp.sourceforge二维码解析器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JpQRCodeParser implements QRCodeParser {
	
	private QRCodeDecoder decoder;
	
	public JpQRCodeParser() {
		this.decoder = new QRCodeDecoder();
	}
	
	public QRCodeDecoder getDecoder() {
		return decoder;
	}

	public void setDecoder(QRCodeDecoder decoder) {
		this.decoder = decoder;
	}

	@Override
	public String parse(BufferedImage qrCodeImage) throws Exception {
		return parse(qrCodeImage, null);
	}

	@Override
	public String parse(final BufferedImage qrCodeImage, String encoding) throws Exception {
		byte[] bytes = decoder.decode(new DecodeQRCodeImage(qrCodeImage));
		return CodecUtils.bytesToString(bytes, encoding);
	}
	
	protected class DecodeQRCodeImage implements QRCodeImage {
		
		private BufferedImage qrCodeImage;
		
		public DecodeQRCodeImage(BufferedImage qrCodeImage) {
			this.qrCodeImage = qrCodeImage;
		}

		@Override
		public int getWidth() {
			return qrCodeImage.getWidth();
		}

		@Override
		public int getHeight() {
			return qrCodeImage.getHeight();
		}

		@Override
		public int getPixel(int x, int y) {
			return qrCodeImage.getRGB(x, y);
		}
		
	}

}
