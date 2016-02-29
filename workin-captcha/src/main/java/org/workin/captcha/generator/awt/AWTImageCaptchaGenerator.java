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
import java.util.Map;

import org.workin.captcha.ImageCaptcha;
import org.workin.captcha.generator.AbstractImageCaptchaGenerator;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description AWT图片验证码生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AWTImageCaptchaGenerator extends AbstractImageCaptchaGenerator {
	
	/** 图片类型 */
	private int imageType;
	
	/** 背景颜色 */
	private Color backgroundColor;
	
	/** 边框颜色 */
	private Color borderColor;
	
	/** 边框字体名称 */
	private String borderFontName;
	
	/** 边框字体样式 */
	private int borderStyle;
	
	/** 是否需要干扰项 */
	private boolean distracter;
	
	/** 干扰项颜色 */
	private Color distracterColor;
	
	/** 文本字体名称 */
	private String textFontName;
	
	/** 文本字体样式 */
	private int textFontStyle;
	
	/** 验证码文本颜色 */
	private Map<String, Color> textColorMap;
	
	/** 验证码文本颜色名称组 */
	private String[] textColorNames;
	
	/** 自定义的验证码文本颜色 */
	public static final Color CUSTOM_TEXT_COLOR = new Color(19, 148, 246);
	
	public AWTImageCaptchaGenerator() {
		this.setImageType(BufferedImage.TYPE_INT_RGB);
		this.setBackgroundColor(Color.WHITE);
		this.setBorderColor(Color.LIGHT_GRAY);
		this.setBorderFontName("Arial");
		this.setBorderStyle(Font.PLAIN);
		this.setDistracter(true);
		this.setDistracterColor(Color.LIGHT_GRAY);
		this.setTextFontName("Georgia");
		this.setTextFontStyle(Font.BOLD);
		
		this.initializeTextColors();
	}
	
	/**
	 * @description 初始化验证码文本的备选颜色
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void initializeTextColors() {
		Map<String, Color> textColorMap = MapUtils.newHashMap();
		textColorMap.put("custom", CUSTOM_TEXT_COLOR);
		textColorMap.put("black", Color.BLACK);
		textColorMap.put("blue", Color.BLUE);
		textColorMap.put("cyan", Color.CYAN);
		textColorMap.put("dark_gray", Color.DARK_GRAY);
		textColorMap.put("gray", Color.GRAY);
		textColorMap.put("green", Color.GREEN);
		textColorMap.put("light_gray", Color.LIGHT_GRAY);
		textColorMap.put("magenta", Color.MAGENTA);
		textColorMap.put("orange", Color.ORANGE);
		textColorMap.put("pink", Color.PINK);
		textColorMap.put("red", Color.RED);
		textColorMap.put("yellow", Color.YELLOW);
		
		this.setTextColorMap(textColorMap);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.getImageType() <= BufferedImage.TYPE_CUSTOM)
			this.setImageType(BufferedImage.TYPE_INT_RGB);
		
		if (this.getBackgroundColor() == null)
			this.setBackgroundColor(Color.WHITE);
		
		if (this.getBorderColor() == null)
			this.setBorderColor(Color.LIGHT_GRAY);
		
		if (StringUtils.isBlank(this.getBorderFontName()))
			this.setBorderFontName("Arial");
		
		if (this.getBorderStyle() < Font.PLAIN)
			this.setBorderStyle(Font.PLAIN);
		
		if (this.getDistracterColor() == null)
			this.setDistracterColor(Color.LIGHT_GRAY);
		
		if (StringUtils.isBlank(this.getTextFontName()))
			this.setTextFontName("Georgia");
		
		if (this.getTextFontStyle() < Font.PLAIN)
			this.setBorderStyle(Font.PLAIN);
		
		this.textColorNames = CollectionUtils.toArray(getTextColorMap().keySet(), String.class);
	}
	
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

	public Map<String, Color> getTextColorMap() {
		return textColorMap;
	}

	public void setTextColorMap(Map<String, Color> textColorMap) {
		this.textColorMap = textColorMap;
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
	
	public String[] getTextColorNames() {
		return textColorNames;
	}

	/**
	 * @description 选择一个验证码文本颜色
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected Color selectTextColor() {
		String colorName = ArrayUtils.get(this.textColorNames, NumberUtils.randomIn(this.textColorNames.length));
		return getTextColor(colorName);
	}
	
	/**
	 * @description 获取指定名称对应的验证码文本颜色
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param colorName
	 * @return
	 */
	protected Color getTextColor(String colorName) {
		Color color = getTextColorMap().get(colorName);
		return color != null ? color : CUSTOM_TEXT_COLOR;
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
