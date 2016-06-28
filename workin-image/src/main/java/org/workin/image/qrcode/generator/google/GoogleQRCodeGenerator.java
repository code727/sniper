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

package org.workin.image.qrcode.generator.google;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.Map;

import org.workin.commons.util.MapUtils;
import org.workin.image.layout.QRCodeLayout;
import org.workin.image.qrcode.QRCode;
import org.workin.image.qrcode.generator.AbstractQRCodeGenerator;

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
public class GoogleQRCodeGenerator extends AbstractQRCodeGenerator {
	
	private static final ThreadLocal<Map<String, Map<EncodeHintType, Object>>> hints = new ThreadLocal<Map<String, Map<EncodeHintType, Object>>>();

	@Override
	protected RenderedImage doCreate(QRCode qrCode) throws Exception {
		
		QRCodeLayout layout = qrCode.getLayout();
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix bitMatrix = writer.encode(qrCode.getText(),
				BarcodeFormat.QR_CODE, layout.getWidth(), layout.getHeight(),
				getHints(qrCode));
		
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, (bitMatrix.get(x, y) ? layout.getContentColor() : layout.getBlankColor()));
			}
		}
		return image;
	}
	
	/**
	 * @description 获取com.google.zxing.EncodeHintType映射配置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCode
	 * @return
	 */
	protected Map<EncodeHintType, Object> getHints(QRCode qrCode) {
		Map<String, Map<EncodeHintType, Object>> hintsMap = hints.get();
		if (hintsMap == null)
			hintsMap = MapUtils.newConcurrentHashMap();
		
		QRCodeLayout layout = qrCode.getLayout();
		StringBuffer key = new StringBuffer();
		key.append(ErrorCorrectionLevel.H).append("_")
				.append(qrCode.getEncoding()).append("_")
				.append(layout.getWidth()).append("_")
				.append(layout.getHeight()).append("_")
				.append(layout.getMargin());
		
		Map<EncodeHintType, Object> hs = hintsMap.get(key);
		if (hints == null) {
			hs = MapUtils.newHashMap();
			hs.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hs.put(EncodeHintType.CHARACTER_SET, qrCode.getEncoding());
			hs.put(EncodeHintType.MAX_SIZE, layout.getWidth());
			hs.put(EncodeHintType.MIN_SIZE, layout.getHeight());
			hs.put(EncodeHintType.MARGIN, layout.getMargin());
			
			hintsMap.put(key.toString(), hs);
			hints.set(hintsMap);
		}
		
		return hs;
	}
	
}
