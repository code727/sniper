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
 * Create Date : 2016-2-25
 */

package org.workin.captcha.generator.awt;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JLabel;

import org.workin.captcha.manager.font.FontManager;
import org.workin.image.layout.CaptchaLayout;

/**
 * 默认的图片验证码生成器实现类
 * @author <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultCaptchaGenerator extends AWTImageCaptchaGenerator {

	@Override
	protected void draw(Graphics graphics, String text) {
		
		drawBackground(graphics);
		drawText(graphics, text);

		CaptchaLayout layout = getLayout();
		if (layout.hasBorder())
			drawBorder(graphics);

		if (layout.hasDistracter())
			drawDistracter(graphics, text);
		
	}

	/**
	 * 绘制背景
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 */
	protected void drawBackground(Graphics graphics) {
		CaptchaLayout layout = getLayout();
		/* 设置背景颜色后填充 */
		graphics.setColor(getBackgroundColorManager().selectColor());
		graphics.fillRect(0, 0, layout.getWidth(), layout.getHeight());
	}

	/**
	 * 绘制边框
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 */
	protected void drawBorder(Graphics graphics) {
		CaptchaLayout layout = getLayout();
		int height = layout.getHeight();
		// 设置边框颜色
		graphics.setColor(getBorderColorManager().selectColor());
		// 绘制边框
		graphics.drawRect(0, 0, layout.getWidth() - 1, height - 1);
	}

	/**
	 * 绘制干扰项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 * @param text
	 */
	protected void drawDistracter(Graphics graphics, String text) {
		CaptchaLayout layout = getLayout();
		// 设置干扰项颜色
		int width = layout.getWidth();
		int height = layout.getHeight();
		
		int x;
		int y;
		int multiple = Math.max(width, height) * 2;
		Random rand = new Random();
		for (int i = 0; i < multiple; i++) {
			x = rand.nextInt(width);
			y = rand.nextInt(height);
			graphics.setColor(getDistracterColorManager().selectColor());
			// 绘制1*1大小的矩形
			graphics.drawRect(x, y, 1, 1);
		}
	}

	/** 
	 * 绘制验证码文本
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 * @param text 
	 */
	protected void drawText(Graphics graphics, String text) {
		CaptchaLayout layout = getLayout();
		FontManager textFontManager = getTextFontManager();
		
		Font textFont = new Font(textFontManager.selectFontName(),
				textFontManager.selectFontStyle(), layout.getFontSize());
		graphics.setFont(textFont);
		
		JLabel label = new JLabel();
		FontMetrics fontMetrics = label.getFontMetrics(textFont);
		
		char currentChar;
		int charWidth = 0;
		int margin = layout.getMargin();
		int textWidth = fontMetrics.stringWidth(text);
		
		int x;
		if (textWidth > layout.getWidth())
			// 文本总宽度大于图片宽度，起始坐标为0
			x = 0;
		else if (textWidth + margin > layout.getWidth())
			// 文本总宽 + 设置的边距大于图片宽度，则起始坐标为"图片宽度 - 文本总宽"
		    x = layout.getWidth() - textWidth;
		else
			x = margin;
		
		// y坐标取文本高度与图片高度之间的最小值
		int y =  Math.min(fontMetrics.getHeight(), layout.getHeight());
		for (int i = 0; i < text.length(); i++) {
			currentChar = text.charAt(i);
			// 当前字符的起始横坐标为"上一个字符的起始坐标 + 上一个字符所占宽度"
			x = x + charWidth;
			
			/* 绘制颜色以及对应的字符 */
			graphics.setColor(getTextColorManager().selectColor());
			graphics.drawString(String.valueOf(currentChar), x, y);
			charWidth = fontMetrics.charWidth(currentChar);
		}
	}

}
