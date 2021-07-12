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
 * Create Date : 2016-6-22
 */

package org.sniper.captcha.generator.awt;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.sniper.captcha.ImageCaptcha;
import org.sniper.captcha.generator.AbstractImageCaptchaGenerator;
import org.sniper.image.layout.CaptchaLayout;

/**
 * AWT图片验证码生成器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AWTImageCaptchaGenerator extends AbstractImageCaptchaGenerator {
	
	@Override
	public ImageCaptcha create() {
		CaptchaLayout layout = getLayout();
		BufferedImage image = new BufferedImage(layout.getWidth(), layout.getHeight(), getImageType());
		Graphics graphics = image.createGraphics();
		
		String text = super.generate();
		draw(graphics, text);
		graphics.dispose();
		return new ImageCaptcha(text, image);
	}
	
	/**
	 * 绘制验证码文本
	 * @author Daniele 
	 * @param graphics
	 * @param text
	 */
	protected abstract void draw(Graphics graphics, String text);

}
