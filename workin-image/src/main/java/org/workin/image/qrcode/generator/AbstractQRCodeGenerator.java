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
 * Create Date : 2016-6-27
 */

package org.workin.image.qrcode.generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.StringUtils;
import org.workin.image.layout.QRCodeLayout;
import org.workin.image.qrcode.QRCode;

/**
 * @description 二维码生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractQRCodeGenerator implements QRCodeGenerator {

	/** 全局样式 */
	private QRCodeLayout layout;
	
	public AbstractQRCodeGenerator() {
		this.layout = new QRCodeLayout();
	}

	public void setLayout(QRCodeLayout layout) {
		this.layout = layout;
	}

	public QRCodeLayout getLayout() {
		return layout;
	}

	public BufferedImage generator(QRCode qrCode) throws Exception {
		AssertUtils.assertTrue(StringUtils.isNotEmpty(qrCode.getText()), "QRCode text must not be null or empty.");
		QRCodeLayout layout = qrCode.getLayout();
		if (layout == null) 
			// 将全局样式赋予当前二维码对象
			qrCode.setLayout(getLayout());
		
		BufferedImage qrcodeImage = createSourceImage(qrCode);
		qrcodeImage = ratioSourceImage(qrcodeImage, qrCode);
		
		BufferedImage logo = qrCode.getLogo();
		if (logo != null) 
			drawLogo(qrcodeImage, ratioLogo(qrcodeImage, logo, layout), layout);

		return qrcodeImage;
	}

	/**
	 * @description 创建二维码原图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCode
	 * @return
	 * @throws Exception
	 */
	protected abstract BufferedImage createSourceImage(QRCode qrCode) throws Exception;

	/**
	 * @description 等比缩放二维码原图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrcodeImage
	 */
	protected BufferedImage ratioSourceImage(BufferedImage qrcodeImage, QRCode qrCode) {
		QRCodeLayout layout = qrCode.getLayout();

		/* 目标宽高 */
		int targetWidth = layout.getSideLength();
		int targetHeight = layout.getSideLength();
		
		/* 二维码图片的实际宽高 */
		int imageWidth = qrcodeImage.getWidth();
		int imageHeight = qrcodeImage.getHeight();
		
		int margin = layout.getMargin();
		/* 如果二维码图片宽高与目标不一致或设置有边距时，则按原宽高等比缩放重新生成一张与目标一致的二维码图片 */
		if (imageWidth != targetWidth || imageHeight != targetHeight || margin > 0) {
			BufferedImage newImage = new BufferedImage(targetWidth, targetHeight, qrcodeImage.getType());
			Graphics2D graphics = newImage.createGraphics();
			if (margin > 0) {
				/* 如果设置有边距，则先将整张图片的背景渲染成白色，
				 * 而下面的graphics.drawImage方法则将二维码绘制在边距区域内的部分 */
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, targetWidth, targetHeight);
			}
			
			int doubleMargin = margin * 2;
			graphics.drawImage(qrcodeImage.getScaledInstance(imageWidth,
					imageHeight, Image.SCALE_SMOOTH), margin, margin,
					targetWidth - doubleMargin, targetWidth - doubleMargin, null);
			
			graphics.dispose();
			newImage.flush();
			qrcodeImage = newImage;
		}

		return qrcodeImage;
	}

	/**
	 * @description 等比缩放二维码logo图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrcodeImage
	 * @param logo
	 * @param layout
	 * @return
	 */
	protected BufferedImage ratioLogo(BufferedImage qrcodeImage, BufferedImage logo, QRCodeLayout layout) {
		int qrcodeWidth = qrcodeImage.getWidth();
		int qrcodeHeight = qrcodeImage.getHeight();
		
		int logoWidth = logo.getWidth();
		int logoHeight = logo.getHeight();

		int targetWidth = (int) (qrcodeWidth * layout.getLogoScale());
		int targetHeight = (int) (qrcodeHeight * layout.getLogoScale() * (double) logoHeight / logoWidth);
		
		if (logoWidth != targetWidth || logoHeight != targetHeight) {
			/* 等比缩放logo图片到实际的宽高 */
			BufferedImage newLogo = new BufferedImage(targetWidth, targetHeight, logo.getType());
			Graphics2D graphics = newLogo.createGraphics();
			graphics.drawImage(logo.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, targetWidth, targetHeight, null);
			graphics.dispose();
			newLogo.flush();
			logo = newLogo;
		}

		return logo;
	}

	/**
	 * @description 在二维码原图内绘制logo
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrcodeImage
	 * @param logo
	 * @param layout
	 */
	protected void drawLogo(BufferedImage qrcodeImage, BufferedImage logo, QRCodeLayout layout) {
		int logoWidth = logo.getWidth();
		int logoHeight = logo.getHeight();

		int x = (qrcodeImage.getWidth() - logoWidth) / 2;
		int y = (qrcodeImage.getHeight() - logoHeight) / 2;
		
		// 圆角比例 
		double arcScale = 0.35;
		int arc = (int) Math.ceil((double) Math.max(logoWidth, logoHeight) * arcScale);
		int halfArc = (int) Math.ceil((double) arc / 2);
		Graphics2D graphics = qrcodeImage.createGraphics(); 
		
		/* 绘制背景(颜色)和背景的边框(颜色) */
		Color backgroundColor = layout.getLogoBackgroundColor();
		if (layout.hasLogoBackground() && backgroundColor != null) {
			graphics.setColor(backgroundColor);
			graphics.fillRoundRect(x - halfArc, y - halfArc, logoWidth + arc, logoHeight + arc, arc, arc);
			
			Color backgroundBorderColor = layout.getLogoBackgroundBorderColor();
			if (layout.hasLogoBackgroundBorder() && backgroundBorderColor != null) {
				graphics.setColor(backgroundBorderColor);
				graphics.drawRoundRect(x - halfArc, y - halfArc, logoWidth + arc, logoHeight + arc, arc, arc);
			}
		} 
		
		int quarterArc = (int) Math.ceil((double) arc / 4);
		Color borderColor = layout.getLogoBorderColor();
		if (layout.hasLogoBorder() && borderColor != null) {
			graphics.setColor(borderColor);
			graphics.drawRoundRect(x - quarterArc, y - quarterArc, logoWidth + halfArc, logoHeight + halfArc, halfArc, halfArc);
		}
		
		graphics.drawImage(logo, x, y, logoWidth, logoHeight, null);
		graphics.dispose();  
	}

}
