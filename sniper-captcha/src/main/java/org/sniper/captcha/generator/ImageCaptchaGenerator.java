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

package org.sniper.captcha.generator;

import org.sniper.captcha.ImageCaptcha;
import org.sniper.image.layout.CaptchaLayout;

/**
 * 图片验证码生成器
 * @author  Daniele
 * @version 1.0
 */
public interface ImageCaptchaGenerator extends CaptchaGenerator {
	
	/** 最小宽度 */
	public static final int MIN_WIDTH = 70;
		
	/** 最小图片高度 */
	public static final int MIN_HEIGHT = 22;
	
	/** 文字与图片边缘的最小间距 */
	public static final int MIN_MARGIN = 3;
	
	/** 最小字体大小 */
	public static final int MIN_FONT_SIZE = 12;
	
	/**
	 * 获取图片验证码布局
	 * @author Daniele 
	 * @return
	 */
	public CaptchaLayout getLayout();
	
	/**
	 * 设置图片验证码布局
	 * @author Daniele 
	 * @param layout
	 */
	public void setLayout(CaptchaLayout layout);
		
	/**
	 * 创建图片验证码
	 * @author Daniele 
	 * @return
	 */
	public ImageCaptcha create();
	
}
