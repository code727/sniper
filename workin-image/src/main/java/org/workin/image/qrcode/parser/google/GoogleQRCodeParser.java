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
 * Create Date : 2016年7月6日
 */

package org.workin.image.qrcode.parser.google;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.workin.commons.util.CodecUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.image.qrcode.parser.QRCodeParser;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

/**
 * @description Google二维码解析器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GoogleQRCodeParser implements QRCodeParser {
	
	private Reader reader;
	
	public GoogleQRCodeParser() {
		this.reader = new MultiFormatReader(); 
	}
	
	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	@Override
	public String parse(BufferedImage qrCodeImage) throws Exception {
		return parse(qrCodeImage, null);
	}

	@Override
	public String parse(BufferedImage qrCodeImage, String encoding) throws Exception {
		LuminanceSource source = new BufferedImageLuminanceSource(qrCodeImage);  
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
		Map<DecodeHintType, Object> hints = MapUtils.newHashMap();
		hints.put(DecodeHintType.CHARACTER_SET, StringUtils
				.isNotBlank(encoding) ? encoding : CodecUtils.DEFAULT_ENCODING);
		return reader.decode(bitmap, hints).getText();
	}

}
