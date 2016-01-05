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

/**
 * @description 图片验证码生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ImageCaptchaGenerator extends CaptchaGenerator {
	
	/** 最小图片宽度 */
	public static final int MIN_WIDTH = 150;
	
	/** 最小图片高度 */
	public static final int MIN_HEIGHT = 50;
	
	/** 最小字体大小 */
	public static final int MIN_FONTSIZE = 40;
	
	/** 文字与图片边缘的间距 */
	public static final int TEXT_SPACING = 10;
	
	/** 默认文字颜色集 */
	public static final String DEFAULT_FONTCOLORS = "red";
	
	/** 默认字体集 */
	public static final String DEFAULT_FONTNAMES = "宋体,楷体,微软雅黑";
	
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
	 * @description 获取文字大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getFontSize();

	/**
	 * @description 设置文字大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fontSize
	 */
	public void setFontSize(int fontSize);

	/**
	 * @description 获取字体颜色
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getFontColors();

	/**
	 * @description 设置字体颜色
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fontColors
	 */
	public void setFontColors(String fontColors);

	/**
	 * @description 获取字体颜色
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getFontNames();

	/**
	 * @description 设置字体颜色
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fontNames
	 */
	public void setFontNames(String fontNames);
	
	
	

}
