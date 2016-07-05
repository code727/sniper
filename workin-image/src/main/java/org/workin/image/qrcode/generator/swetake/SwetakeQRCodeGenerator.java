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

package org.workin.image.qrcode.generator.swetake;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import org.workin.commons.util.CodecUtils;
import org.workin.commons.util.MapUtils;
import org.workin.image.layout.QRCodeLayout;
import org.workin.image.qrcode.QRCode;
import org.workin.image.qrcode.generator.AbstractQRCodeGenerator;

import com.swetake.util.Qrcode;

/**
 * @description Swetake二维码生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SwetakeQRCodeGenerator extends AbstractQRCodeGenerator {
	
	private static final ThreadLocal<Map<String, Qrcode>> codes = new ThreadLocal<Map<String, Qrcode>>();

	@Override
	protected BufferedImage createSourceImage(QRCode qrCode) throws Exception {
		Qrcode code = getCode(qrCode);
        byte [] contentBytes = CodecUtils.getBytes(qrCode.getText(), qrCode.getEncoding());  
		return drawSourceImage(code.calQrcode(contentBytes), qrCode);
	}
	
	/**
	 * @description 获取com.swetake.util.Qrcode对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCode
	 * @return
	 */
	protected Qrcode getCode(QRCode qrCode) {
		Map<String, Qrcode> codesMap = codes.get();
		if (codesMap == null)
			codesMap = MapUtils.newConcurrentHashMap();
		
		char ecl = qrCode.getErrorCorrectionLevel().toUpperCase().charAt(0);
		StringBuffer key = new StringBuffer(ecl);
		
		Qrcode code = codesMap.get(key);
		if (code == null) {
			code = new Qrcode();
			code.setQrcodeErrorCorrect(ecl);
			/* 编码模式，取第一个大写字母
	           Numeric：数字
	           Alphanumeric： 英文字母
	           Binary：二进制
	           Kanji：汉字
	        */
			code.setQrcodeEncodeMode('B');
			
			codesMap.put(key.toString(), code);
			codes.set(codesMap);
		}
		
		return code;
	}
	
	/**
	 * @description 绘制二维码原图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param calQrcode
	 * @param qrCode
	 * @return
	 */
	protected BufferedImage drawSourceImage(boolean [][] calQrcode, QRCode qrCode) {
		QRCodeLayout layout = qrCode.getLayout();
		int sideLength = calQrcode.length;
		
		BufferedImage image = new BufferedImage(sideLength, sideLength, qrCode.getImageType());  
		Graphics2D graphics = image.createGraphics();
		for (int x = 0; x < sideLength; x++) {
			for (int y = 0; y < sideLength; y++) {
				image.setRGB(x, y, calQrcode[x][y] ? layout.getOnColor() : layout.getOffColor());
			}
		}
		graphics.dispose(); 
		image.flush();  		
		return image;
	}

}
