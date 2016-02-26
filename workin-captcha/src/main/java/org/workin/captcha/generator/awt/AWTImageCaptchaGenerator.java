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
 * Create Date : 2016-2-26
 */

package org.workin.captcha.generator.awt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.workin.captcha.ImageCaptcha;
import org.workin.captcha.generator.AbstractImageCaptchaGenerator;

/**
 * @description AWT图片验证码生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AWTImageCaptchaGenerator extends AbstractImageCaptchaGenerator {
	
	/** 图片类型 */
	private int imageType = BufferedImage.TYPE_INT_RGB;
	
	/** 背景颜色 */
	private Color backgroundColor = Color.WHITE;
	
	/** 边框颜色 */
	private Color borderColor = Color.LIGHT_GRAY;
	
	/** 边框字体名称 */
	private String borderFontName = "Arial";
	
	/** 边框字体样式 */
	private int borderStyle = Font.BOLD;
	
	/** 是否需要干扰项 */
	private boolean distracter = true;
	
	/** 干扰项颜色 */
	private Color distracterColor = Color.LIGHT_GRAY;
	
	/** 验证码文本颜色 */
	private Color textColor = new Color(19, 148, 246);
	
	/** 文本字体名称 */
	private String textFontName = "Georgia";
	
	/** 文本字体样式 */
	private int textFontStyle = Font.BOLD;
	
	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public String getBorderFontName() {
		return borderFontName;
	}

	public void setBorderFontName(String borderFontName) {
		this.borderFontName = borderFontName;
	}

	public int getBorderStyle() {
		return borderStyle;
	}

	public void setBorderStyle(int borderStyle) {
		this.borderStyle = borderStyle;
	}

	public boolean hasDistracter() {
		return distracter;
	}

	public void setDistracter(boolean distracter) {
		this.distracter = distracter;
	}

	public Color getDistracterColor() {
		return distracterColor;
	}

	public void setDistracterColor(Color distracterColor) {
		this.distracterColor = distracterColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public String getTextFontName() {
		return textFontName;
	}

	public void setTextFontName(String textFontName) {
		this.textFontName = textFontName;
	}

	public int getTextFontStyle() {
		return textFontStyle;
	}

	public void setTextFontStyle(int textFontStyle) {
		this.textFontStyle = textFontStyle;
	}

	@Override
	public ImageCaptcha create() {
		String text = super.generate();
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), getImageType());
		
		Graphics graphics = image.createGraphics();
		draw(graphics, text);
		graphics.dispose();
		
		return new ImageCaptcha(text, image);
	}
	
	/**
	 * @description 绘制验证码文本
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param graphics
	 * @param text
	 */
	protected abstract void draw(Graphics graphics, String text);

}
