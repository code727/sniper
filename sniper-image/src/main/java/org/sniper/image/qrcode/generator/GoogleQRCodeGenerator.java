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
import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.image.layout.QRCodeLayout;
import org.sniper.image.qrcode.QRCode;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;

/**
 * Google二维码生成器实现类
 * @author <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GoogleQRCodeGenerator extends AbstractQRCodeGenerator {

	private static final Map<String, Map<EncodeHintType, Object>> hints = MapUtils.newConcurrentHashMap();

	private Writer writer;
	
	public GoogleQRCodeGenerator() {
		this.writer = new MultiFormatWriter();
	}
	
	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	protected BufferedImage createSourceImage(QRCode qrCode) throws Exception {
//		QRCodeLayout layout = qrCode.getLayout();
//		int targetWidth = layout.getSideLength();
//		int targetHeight = layout.getSideLength();
//		
//		BitMatrix bitMatrix = writer.encode(qrCode.getText(), BarcodeFormat.QR_CODE, targetWidth, targetHeight, getHints(qrCode));
//		return drawSourceImage(clearDefaultMargin(bitMatrix), qrCode);
		
		ErrorCorrectionLevel ecl = ErrorCorrectionLevel.valueOf(qrCode.getErrorCorrectionLevel().toUpperCase());
		com.google.zxing.qrcode.encoder.QRCode code = Encoder.encode(qrCode.getText(), ecl, getHints(qrCode));
		return drawSourceImage(code.getMatrix(), qrCode);
	}

	/**
	 * 获取com.google.zxing.EncodeHintType映射配置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param qrCode
	 * @return
	 */
	protected Map<EncodeHintType, Object> getHints(QRCode qrCode) {
		ErrorCorrectionLevel ecl = ErrorCorrectionLevel.valueOf(qrCode.getErrorCorrectionLevel().toUpperCase());
		QRCodeLayout layout = qrCode.getLayout();
		
		StringBuilder key = new StringBuilder();
		key.append(ecl).append("_")
				.append(qrCode.getEncoding()).append("_")
				.append(layout.getSideLength()).append("_")
				.append(layout.getSideLength()).append("_")
				.append(layout.getMargin());
		
		Map<EncodeHintType, Object> hs = hints.get(key);
		if (hs == null) {
			synchronized (this) {
				hs = MapUtils.newHashMap();
				hs.put(EncodeHintType.ERROR_CORRECTION, ecl);
				hs.put(EncodeHintType.CHARACTER_SET, qrCode.getEncoding());
//				hs.put(EncodeHintType.MAX_SIZE, layout.getSideLength());
//				hs.put(EncodeHintType.MIN_SIZE, QRCodeLayout.MIN_SIDELENGTH);
//				hs.put(EncodeHintType.MARGIN, layout.getMargin());
				hs.put(EncodeHintType.MARGIN, 0);

				hints.put(key.toString(), hs);
			}
		}

		return hs;
	}

	/**
	 * 根据字节矩阵绘制二维码原图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param byteMatrix
	 * @param qrCode
	 * @return
	 */
	protected BufferedImage drawSourceImage(ByteMatrix byteMatrix, QRCode qrCode) {
		int matrixWidth = byteMatrix.getWidth();
		int matrixHeight = byteMatrix.getHeight();
		
		BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, qrCode.getImageType());
		QRCodeLayout layout = qrCode.getLayout();
		for (int x = 0; x < matrixWidth; x++) {
			for (int y = 0; y < matrixHeight; y++) {
				image.setRGB(x, y, (byteMatrix.get(x, y) != 0 ? layout.getOnColor() : layout.getOffColor()));
			}
		}
			
		image.flush();
		return image;
	}
	
	/**
	 * 根据比特位矩阵绘制二维码原图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bitMatrix
	 * @param qrCode
	 * @return
	 */
	protected BufferedImage drawSourceImage(BitMatrix bitMatrix, QRCode qrCode) {
		int matrixWidth = bitMatrix.getWidth();
		int matrixHeight = bitMatrix.getHeight();
		
		BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, qrCode.getImageType());
		QRCodeLayout layout = qrCode.getLayout();
		for (int x = 0; x < matrixWidth; x++) {
			for (int y = 0; y < matrixHeight; y++) {
				image.setRGB(x, y, (bitMatrix.get(x, y) ? layout.getOnColor() : layout.getOffColor()));
			}
		}
			
		image.flush();
		return image;
	}
	
	/**
	 * 清空默认计算出的边距</P>
	 * @deprecated 不建议使用，调用此方法后再生成的图片可能会导致解析失败
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param matrix
	 * @return
	 */
	@Deprecated
	protected BitMatrix clearDefaultMargin(BitMatrix matrix) {
		int[] rectangle = matrix.getEnclosingRectangle(); 
		int left = rectangle[0];
		int top = rectangle[1];
		
		int targetWidth = rectangle[2];
		int targetHeight = rectangle[3];
		
		BitMatrix newMatrix = new BitMatrix(targetWidth, targetHeight);
		newMatrix.clear();
		for (int x = 0; x < newMatrix.getWidth(); x++) { 
			for (int y = 0; y < newMatrix.getHeight(); y++) {
				if (matrix.get(x + left, y + top)) {
					newMatrix.set(x, y);
				}
			}
		}
		return newMatrix;
	}
	
}
