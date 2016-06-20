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

package org.workin.image.qrcode.google;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Map;

import javax.imageio.ImageIO;

import org.workin.commons.util.MapUtils;
import org.workin.image.qrcode.QRCodeGenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @description Google二维码生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GoogleQRCodeGenerator implements QRCodeGenerator {

	@Override
	public RenderedImage generator(String text) throws Exception {
		Map<EncodeHintType, Object> hints = MapUtils.newHashMap();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);
		
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 100, 100, hints);
		
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, (bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF));
			}
		}
		return image;
	}
	
	public static void main(String[] args) throws Exception {
		GoogleQRCodeGenerator generator = new GoogleQRCodeGenerator();
		RenderedImage image = generator.generator("http://www.163.com");
		
		ImageIO.write(image, "PNG", new File("C:/Users/Administrator/Desktop/test.png"));
		
		
		
	}

}
