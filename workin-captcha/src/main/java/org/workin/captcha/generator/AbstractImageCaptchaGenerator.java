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
import org.workin.commons.util.StringUtils;

/**
 * @description 图片验证码生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractImageCaptchaGenerator extends TextCaptchaGenerator
		implements ImageCaptchaGenerator, InitializingBean {
	
	/** 高度 */
	private int width = MIN_WIDTH;
	
	/** 宽度 */
	private int height = MIN_HEIGHT;
	
	/** 最小字体大小 */
	private int fontSize = MIN_FONTSIZE;
	
	/** 字体颜色 */
	private String fontColors = DEFAULT_FONTCOLORS;
	
	/** 字体名称 */
	private String fontNames = DEFAULT_FONTNAMES;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.formatWidth();
		this.formatHeight();
		this.formatFontSize();
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public void setWidth(int width) {
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
	public int getFontSize() {
		return this.fontSize;
	}

	@Override
	public void setFontSize(int fontSize) {
		AssertUtils.assertTrue(fontSize >= MIN_FONTSIZE,
				"Captcha image font size [" + fontSize + "] must greater than equals " + MIN_FONTSIZE);
		this.fontSize = fontSize;
	}

	@Override
	public String getFontColors() {
		return this.fontColors;
	}

	@Override
	public void setFontColors(String fontColors) {
		if (StringUtils.isNotBlank(fontColors))
			this.fontColors = fontColors;
	}

	@Override
	public String getFontNames() {
		return this.fontNames;
	}

	@Override
	public void setFontNames(String fontNames) {
		if (StringUtils.isNotBlank(fontNames))
			this.fontNames = fontNames;
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
	 * @description 格式化字体大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void formatFontSize() {
		// 字体为"最小宽/高值 - 边距"
		this.setFontSize(Math.min(this.width, this.height) - TEXT_SPACING);
	}

}
