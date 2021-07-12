/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-12-31
 */

package org.sniper.captcha.generator;

import java.awt.Font;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.InitializingBean;
import org.sniper.captcha.manager.color.BackgroundColorManager;
import org.sniper.captcha.manager.color.BorderColorManager;
import org.sniper.captcha.manager.color.ColorManager;
import org.sniper.captcha.manager.color.DistracterColorManager;
import org.sniper.captcha.manager.color.TextColorManager;
import org.sniper.captcha.manager.font.FontManager;
import org.sniper.captcha.manager.font.TextFontManager;
import org.sniper.commons.util.NumberUtils;
import org.sniper.image.layout.CaptchaLayout;

/**
 * 图片验证码生成器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractImageCaptchaGenerator extends TextCaptchaGenerator
		implements ImageCaptchaGenerator, InitializingBean {
	
	/** 图片类型 */
	private int imageType = BufferedImage.TYPE_INT_RGB;
	
	/** 布局 */
	private CaptchaLayout layout;
	
	/** 背景颜色管理器 */
	private ColorManager backgroundColorManager;
	
	/** 边框颜色管理器 */
	private ColorManager borderColorManager;
	
	/** 干扰项颜色管理器 */
	private ColorManager distracterColorManager;
	
	/** 文本颜色管理器 */
	private ColorManager textColorManager;
	
	/** 文本字体管理器 */
	private FontManager textFontManager;
	
	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = NumberUtils.minLimit(imageType, BufferedImage.TYPE_INT_RGB);
	}

	public CaptchaLayout getLayout() {
		return layout;
	}

	public void setLayout(CaptchaLayout layout) {
		this.layout = layout;
	}
	
	public ColorManager getBackgroundColorManager() {
		return backgroundColorManager;
	}

	public void setBackgroundColorManager(ColorManager backgroundColorManager) {
		this.backgroundColorManager = backgroundColorManager;
	}

	public ColorManager getBorderColorManager() {
		return borderColorManager;
	}

	public void setBorderColorManager(ColorManager borderColorManager) {
		this.borderColorManager = borderColorManager;
	}

	public ColorManager getDistracterColorManager() {
		return distracterColorManager;
	}

	public void setDistracterColorManager(ColorManager distracterColorManager) {
		this.distracterColorManager = distracterColorManager;
	}

	public ColorManager getTextColorManager() {
		return textColorManager;
	}

	public void setTextColorManager(ColorManager textColorManager) {
		this.textColorManager = textColorManager;
	}
	
	public FontManager getTextFontManager() {
		return textFontManager;
	}

	public void setTextFontManager(FontManager textFontManager) {
		this.textFontManager = textFontManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (this.layout == null)
			setLayout(buildDefaultLayout());
		
		initializeLayout();
		
		if (this.backgroundColorManager == null) 
			setBackgroundColorManager(buildDefaultBackgroundColorManager());
		
		if (this.borderColorManager == null) 
			setBorderColorManager(buildDefaultBorderColorManager());
		
		if (this.distracterColorManager == null)
			setDistracterColorManager(buildDefaultDistracterColorManager());
		
		if (this.textColorManager == null)
			setTextColorManager(buildDefaultTextColorManager());
		
		if (this.textFontManager == null)
			setTextFontManager(buildDefaultTextFontManager());
	}
	
	/**
	 * 构建默认样式
	 * @author Daniele 
	 * @return
	 */
	protected CaptchaLayout buildDefaultLayout() {
		CaptchaLayout layout = new CaptchaLayout();
		
		layout.setWidth(MIN_WIDTH);
		layout.setHeight(MIN_HEIGHT);
		layout.setMargin(MIN_MARGIN);
		
		layout.setBorderStyle(Font.PLAIN);
		layout.setBorder(true);
		
		layout.setDistracter(true);
		return layout;
	}
	
	/**
	 * 构建默认的背景颜色管理器
	 * @author Daniele 
	 * @return
	 * @throws Exception
	 */
	protected ColorManager buildDefaultBackgroundColorManager() throws Exception {
		BackgroundColorManager backgroundColorManager = new BackgroundColorManager();
		backgroundColorManager.afterPropertiesSet();
		return backgroundColorManager;
	}
	
	/**
	 * 构建默认的边框颜色管理器
	 * @author Daniele 
	 * @return
	 * @throws Exception
	 */
	protected ColorManager buildDefaultBorderColorManager() throws Exception {
		BorderColorManager borderColorManager = new BorderColorManager();
		borderColorManager.afterPropertiesSet();
		return borderColorManager;
	}
	
	/**
	 * 构建默认的干扰项颜色管理器
	 * @author Daniele 
	 * @return
	 * @throws Exception
	 */
	protected ColorManager buildDefaultDistracterColorManager() throws Exception {
		DistracterColorManager distracterColorManager = new DistracterColorManager();
		distracterColorManager.afterPropertiesSet();
		return distracterColorManager;
	}
	
	/** 
	 * 构建默认的文本颜色管理器
	 * @author Daniele 
	 * @return
	 * @throws Exception
	 */
	protected ColorManager buildDefaultTextColorManager() throws Exception {
		TextColorManager textColorManager = new TextColorManager();
		textColorManager.afterPropertiesSet();
		return textColorManager;
	}
	
	/** 
	 * 构建默认的文本字体管理器
	 * @author Daniele 
	 * @return 
	 * @throws Exception 
	 */
	protected FontManager buildDefaultTextFontManager() throws Exception {
		TextFontManager textFontManager = new TextFontManager();
		textFontManager.afterPropertiesSet();
		return textFontManager;
	}
		
	/**
	 * 初始化布局
	 * @author Daniele
	 */
	private void initializeLayout() {
		
		int fontSize = layout.getFontSize();
		if (fontSize < MIN_FONT_SIZE) 
			layout.setFontSize(MIN_FONT_SIZE);
			
		int width = layout.getWidth();
		if (width < MIN_WIDTH) 
			layout.setWidth(MIN_WIDTH);
		
		int height = layout.getHeight();
		if (height < MIN_HEIGHT) 
			layout.setHeight(MIN_HEIGHT);
			
		int margin = layout.getMargin();
		if (margin < MIN_MARGIN) 
			layout.setMargin(MIN_MARGIN);
				
		if (layout.getBorderStyle() < Font.PLAIN)
			layout.setBorderStyle(Font.PLAIN);
	}
	
}
