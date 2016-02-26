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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * @description 默认的图片验证码生成器实现类
 * @author <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultCaptchaGenerator extends AWTImageCaptchaGenerator {

	@Override
	protected void draw(Graphics graphics, String text) {
		drawBackground(graphics);
		
		if (hasBorder())
			drawBorder(graphics);
		
		if (hasDistracter())
			drawDistracter(graphics, text);
		
		drawText(graphics, text);
	}
	
	/**
	 * @description 绘制背景
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 */
	protected void drawBackground(Graphics graphics) {
		/* 填充并设置背景颜色 */
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(getBackgroundColor());
	}
	
	/**
	 * @description 绘制边框
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 */
	protected void drawBorder(Graphics graphics) {
		int height = getHeight();
		// 设置边框颜色
		graphics.setColor(Color.LIGHT_GRAY);
		// 边框字体样式
		graphics.setFont(new Font(getBorderFontName(), getBorderStyle(), height - 2));
		// 绘制边框
		graphics.drawRect(0, 0, getWidth() - 1, height - 1);
	}
	
	/**
	 * @description 绘制干扰项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 * @param text
	 */
	protected void drawDistracter(Graphics graphics, String text) {
		Random rand = new Random();
		graphics.setColor(getDistracterColor());
		
		int x;
		int y;
		for (int i = 0; i < text.length() * 6; i++) {
			x = rand.nextInt(getWidth());
			y = rand.nextInt(getHeight());
			// 绘制1*1大小的矩形
			graphics.drawRect(x, y, 1, 1);
		}
	}
	
	/** 
	 * @description 绘制验证码文本
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 * @param text 
	 */
	private void drawText(Graphics graphics, String text) {
		int codeY = getHeight() - 5;
		graphics.setColor(getTextColor());
		graphics.setFont(new Font(getTextFontName(), getTextFontStyle(), getTextFontSize()));
		for (int i = 0; i < text.length(); i++) 
			graphics.drawString(String.valueOf(text.charAt(i)), i * 16 + 5, codeY);
	}

}
