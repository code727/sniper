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
 * Create Date : 2016-7-5
 */

package org.sniper.image.qrcode.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.image.layout.QRCodeLayout;
import org.sniper.image.qrcode.QRCode;

import com.swetake.util.Qrcode;

/**
 * Swetake二维码生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SwetakeQRCodeGenerator extends AbstractQRCodeGenerator {
	
	private static final Map<String, Qrcode> codes = MapUtils.newConcurrentHashMap();

	@Override
	protected BufferedImage createSourceImage(QRCode qrCode) throws Exception {
		Qrcode code = getCode(qrCode);
        byte [] contentBytes = CodecUtils.getBytes(qrCode.getText(), qrCode.getEncoding()); 
		return drawSourceImage(code.calQrcode(contentBytes), qrCode);
	}
	
	/**
	 * 获取com.swetake.util.Qrcode对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCode
	 * @return
	 */
	protected Qrcode getCode(QRCode qrCode) {
		
		char ecl = qrCode.getErrorCorrectionLevel().toUpperCase().charAt(0);
		String key = String.valueOf(ecl);
		
		Qrcode code = codes.get(key);
		if (code == null) {
			synchronized (this) {
				code = new Qrcode();
				code.setQrcodeErrorCorrect(ecl);
				/* 编码模式，取第一个大写字母。
				   Numeric:数字，
				   Alphanumeric:英文字母，
				   Binary:二进制，
				   Kanji:汉字 
				*/
				code.setQrcodeEncodeMode('B');
				
				codes.put(key, code);
			}
		}
		
		return code;
	}
	
	/**
	 * 绘制二维码原图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param matrix
	 * @param qrCode
	 * @return
	 */
	protected BufferedImage drawSourceImage(boolean [][] matrix, QRCode qrCode) {
		QRCodeLayout layout = qrCode.getLayout();
		int sideLength = layout.getSideLength();
		
		BufferedImage image = new BufferedImage(sideLength, sideLength, qrCode.getImageType());  
		Graphics2D graphics = image.createGraphics();
		
		graphics.setBackground(layout.getOffColor());
		graphics.clearRect(0, 0, sideLength, sideLength);
		graphics.setColor(layout.getOnColor());

		int matrixLength = matrix.length;
		// 绘制二维码图片内容时的缩放倍数
		int multiple = Math.max(sideLength, matrixLength) / matrixLength;

		int leftPadding = (sideLength - (matrixLength * multiple)) / 2;
		int topPadding = (sideLength - (matrixLength * multiple)) / 2;
		for (int inputY = 0, outputY = topPadding; inputY < matrixLength; inputY++, outputY += multiple) {
			for (int inputX = 0, outputX = leftPadding; inputX < matrixLength; inputX++, outputX += multiple) {
				if (matrix[inputX][inputY]) {
					graphics.fillRect(outputX, outputY, multiple, multiple);
				}
			}
		}

		graphics.dispose(); 
		image.flush();  		
		return image;
	}

}
