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
 * Create Date : 2015-12-30
 */

package org.workin.captcha.generator;

import org.workin.captcha.ImageCaptcha;

/**
 * @description 图片验证码生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ImageCaptchaGenerator extends CaptchaGenerator {
	
	/** 最小图片宽度 */
	public static final int MIN_WIDTH = 74;
	
	/** 最小图片高度 */
	public static final int MIN_HEIGHT = 21;
	
	/** 最小字体大小 */
	public static final int MIN_FONTSIZE = 1;
	
	/** 文字与图片边缘的最小间距 */
	public static final int MIN_TEXT_SPACING = 3;
	
	/**
	 * @description 获取宽度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getWidth();

	/**
	 * @description 设置宽度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param width
	 */
	public void setWidth(int width);

	/**
	 * @description 获取高度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getHeight();

	/**
	 * @description 设置高度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param height
	 */
	public void setHeight(int height);

	/**
	 * @description 获取验证码文字大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getTextFontSize();

	/**
	 * @description 设置验证码文字大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param textFontSize
	 */
	public void setTextFontSize(int textFontSize);
	
	/**
	 * @description 设置验证码文本与图片的边距
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param textSpacing
	 */
	public void setTextSpacing(int textSpacing);
	
	/**
	 * @description 设置文字与图片的边距
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @returnWW
	 */
	public int getTextSpacing();
	
	/**
	 * @description 设置是否需要边框样式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param border
	 */
	public void setBorder(boolean border);
	
	/**
	 * @description 判断是否有边框样式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean hasBorder();
	
	/**
	 * @description 创建图片验证码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public ImageCaptcha create();
	
}
