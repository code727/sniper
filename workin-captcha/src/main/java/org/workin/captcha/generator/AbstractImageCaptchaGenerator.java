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

package org.workin.captcha.generator;

import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.AssertUtils;

/**
 * @description 图片验证码生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractImageCaptchaGenerator extends TextCaptchaGenerator
		implements ImageCaptchaGenerator, InitializingBean {
	
	/** 高度 */
	private int width;
	
	/** 宽度 */
	private int height;
	
	/** 字体大小 */
	private int textFontSize;
	
	/** 字与图片边缘的间距  */
	private int textSpacing;
	
	/** 是否需要边框 */
	private boolean border = true;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.formatWidth();
		this.formatHeight();
		this.formatTextFontSize();
	}
	
	/**
	 * @description 初始化
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void initialization() {
		if (getWidth() <= 0)
			setWidth(MIN_WIDTH);
		
		if (getHeight() <= 0)
			setHeight(MIN_HEIGHT);
		
		if (getTextFontSize() <= 0)
			setTextFontSize(MIN_FONTSIZE);
		
		if (getTextSpacing() <= 0)
			setTextSpacing(MIN_TEXT_SPACING);
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public void setWidth(int width) {
		AssertUtils.assertTrue(width >= MIN_WIDTH, "Captcha image width ["
				+ width + "] must greater than equals " + MIN_WIDTH);
		
		this.width = width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public void setHeight(int height) {
		AssertUtils.assertTrue(height >= MIN_HEIGHT, "Captcha image height ["
				+ height + "] must greater than equals " + MIN_HEIGHT);
		
		this.height = height;
	}

	@Override
	public int getTextFontSize() {
		return this.textFontSize;
	}

	@Override
	public void setTextFontSize(int textFontSize) {
		AssertUtils.assertTrue(textFontSize >= MIN_FONTSIZE,
				"Captcha text font size [" + textFontSize + "] must greater than equals " + MIN_FONTSIZE);
		
		this.textFontSize = textFontSize;
	}

	@Override
	public void setTextSpacing(int textSpacing) {
		AssertUtils.assertTrue(textSpacing >= MIN_TEXT_SPACING,
				"Captcha text spacing [" + textSpacing + "] must greater than equals " + MIN_TEXT_SPACING);
		
		this.textSpacing = textSpacing;
	}

	@Override
	public int getTextSpacing() {
		return this.textSpacing;
	}
	
	@Override
	public void setBorder(boolean border) {
		this.border = border;
	}
	
	@Override
	public boolean hasBorder() {
		return this.border;
	}
	
	/**
	 * @description 格式化图片宽度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void formatWidth() {
		if (this.width < MIN_WIDTH)
			this.setWidth(MIN_WIDTH);
	}
	
	/**
	 * @description 格式化图片高度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void formatHeight() {
		
		if (this.width < this.height)
			// 如果长度比高度小，则设置高度只为宽度的1/3
			this.setHeight(this.width / 3);
			
		if (this.height < MIN_HEIGHT)
			this.setHeight(MIN_HEIGHT);
	}
	
	/**
	 * @description 格式化验证码字体大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void formatTextFontSize() {
		// 字体为"最小宽/高值 - 边距"
		this.setTextFontSize(Math.min(this.width, this.height) - this.getTextSpacing());
	}
	
}
