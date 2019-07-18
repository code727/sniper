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

package org.sniper.image.qrcode.generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.sniper.commons.util.AssertUtils;
import org.sniper.image.layout.QRCodeLayout;
import org.sniper.image.qrcode.QRCode;

/**
 * 二维码生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractQRCodeGenerator implements QRCodeGenerator {

	/** 全局样式 */
	private QRCodeLayout globalLayout = new QRCodeLayout();
	
	public QRCodeLayout getGlobalLayout() {
		return globalLayout;
	}

	public void setGlobalLayout(QRCodeLayout globalLayout) {
		AssertUtils.assertNotNull(globalLayout, "Global qrcode layout must not be null");
		this.globalLayout = globalLayout;
	}

	@Override
	public BufferedImage generate(QRCode qrCode) throws Exception {
		AssertUtils.assertNotEmpty(qrCode.getText(), "QRCode text must not be null or empty");
		QRCodeLayout layout = qrCode.getLayout();
		if (layout == null) {
			// 将全局样式拷贝一份给当前二维码对象
			layout = getGlobalLayout().clone();
			qrCode.setLayout(layout);
		}
		
		BufferedImage qrcodeImage = createSourceImage(qrCode);
		qrcodeImage = ratioSourceImage(qrcodeImage, qrCode);
		
		BufferedImage logo = qrCode.getLogo();
		if (logo != null) 
			drawLogo(qrcodeImage, ratioLogo(qrcodeImage, logo, layout), layout);

		return qrcodeImage;
	}

	/**
	 * 创建二维码原图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCode
	 * @return
	 * @throws Exception
	 */
	protected abstract BufferedImage createSourceImage(QRCode qrCode) throws Exception;

	/**
	 * 等比缩放二维码原图
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrcodeImage
	 */
	protected BufferedImage ratioSourceImage(BufferedImage qrcodeImage, QRCode qrCode) {
		QRCodeLayout layout = qrCode.getLayout();

		/* 当前二维码图片的实际宽高 */
		int imageWidth = qrcodeImage.getWidth();
		int imageHeight = qrcodeImage.getHeight();
		
		/* 目标宽高 */
		int targetSideLength = layout.getSideLength();
		
		int margin = layout.getMargin();
		/* 如果二维码图片的实际宽高与目标不一致或设置有边距时，则重新生成一张与目标一致的二维码图片 
		 * 注意：
		 * 1.通常情况下，各实现类创建的二维码原图其宽高已经就与目标是一致的了;
		 * 2.更多时候，调用方生成二维码时，如果指定了边距值(margin)，此时将进入如下代码块进行处理;
		 * 3.建议不要设置margin值，因为各实现类为尽可能保证消息的完整性，在生成二维码时都会根据消息的大小预留一定大小的边距。
		 *   因此，如果再设置margin值，只会让实际的边距值比设置的值更大，另外，如果margin值设置的不合理，将可能导致解析失败。
		 * */
		if (imageWidth != targetSideLength || imageHeight != targetSideLength || margin > 0) {
			BufferedImage newImage = new BufferedImage(targetSideLength, targetSideLength, qrcodeImage.getType());
			Graphics2D graphics = newImage.createGraphics();
			if (margin > 0) {
				int doubleMargin = margin * 2;
				/* 如果设置有边距，先将整张图片的背景渲染成指定关闭颜色 */ 
				graphics.setBackground(layout.getOffColor());
				graphics.clearRect(0, 0, targetSideLength, targetSideLength);
				
				/* 再将原二维码的内容绘制在边距区域内 */
				graphics.fillRect(0, 0, targetSideLength, targetSideLength);
				graphics.drawImage(qrcodeImage.getScaledInstance(targetSideLength,
						targetSideLength, Image.SCALE_SMOOTH), margin, margin,
						targetSideLength - doubleMargin, targetSideLength - doubleMargin, null);
			} else {
				graphics.drawImage(qrcodeImage.getScaledInstance(targetSideLength,
						targetSideLength, Image.SCALE_SMOOTH), 0, 0,
						targetSideLength, targetSideLength, null);
			}
			graphics.dispose();
			newImage.flush();
			return newImage;
		}

		return qrcodeImage;
	}

	/**
	 * 等比缩放二维码logo图
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
		
		double logoScale = layout.getLogoScale();
		int ratioSize = Math.max((int) (qrcodeWidth * logoScale),
				(int) (qrcodeHeight * logoScale * (double) logoHeight / logoWidth));
			
		/* 将Logo图片缩放成正方形 */
		if (logoWidth != ratioSize || logoHeight != ratioSize) {
			BufferedImage newLogo = new BufferedImage(ratioSize, ratioSize, logo.getType());
			Graphics2D graphics = newLogo.createGraphics();
			graphics.drawImage(logo.getScaledInstance(ratioSize, ratioSize, Image.SCALE_SMOOTH), 0, 0, ratioSize, ratioSize, null);
			graphics.dispose();
			newLogo.flush();
			return newLogo;
		}

		return logo;
	}

	/**
	 * 在二维码原图内绘制logo
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
		int arc = (int) Math.ceil((double) (Math.max(logoWidth, logoHeight) * arcScale));
//		int halfArc = (int) Math.ceil((double) arc / 2);
		int halfArc = (int) arc / 2;
		
		Graphics2D graphics = qrcodeImage.createGraphics(); 
		Color backgroundColor = layout.getLogoBackgroundColor();
		if (layout.hasLogoBackground() && backgroundColor != null) {
			/* 绘制Logo的背景(颜色) */
			graphics.setColor(backgroundColor);
			graphics.fillRoundRect(x - halfArc, y - halfArc, logoWidth + arc, logoHeight + arc, arc, arc);
			
			/* 绘制Logo背景的边框(颜色) */
			Color backgroundBorderColor = layout.getLogoBackgroundBorderColor();
			if (layout.hasLogoBackgroundBorder() && backgroundBorderColor != null) {
				graphics.setColor(backgroundBorderColor);
				graphics.drawRoundRect(x - halfArc, y - halfArc, logoWidth + arc, logoHeight + arc, arc, arc);
			}
		} 
		
		int quarterArc = (int) Math.ceil((double) arc / 4);
		Color borderColor = layout.getLogoBorderColor();
		if (layout.hasLogoBorder() && borderColor != null) {
			/* 绘制Logo的边框(颜色) */
			graphics.setColor(borderColor);
			graphics.drawRoundRect(x - quarterArc, y - quarterArc, logoWidth + halfArc, logoHeight + halfArc, halfArc, halfArc);
		}
		
		graphics.drawImage(logo, x, y, logoWidth, logoHeight, null);
		graphics.dispose();  
	}

}
