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
 * Create Date : 2016-3-3
 */

package org.workin.captcha.handler.redis;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import org.workin.captcha.ImageCaptcha;
import org.workin.captcha.generator.CaptchaGenerator;
import org.workin.captcha.generator.ImageCaptchaGenerator;
import org.workin.captcha.handler.ImageCaptchaHandler;

/**
 * @description Redis库图片验证码处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisImageCaptchaHandler extends RedisCaptchaHandler implements
		ImageCaptchaHandler {
	
	/** 图片格式名称 */
	private String formatName;
	
	public RedisImageCaptchaHandler() {
		setPrefix("image_captcha_");
		setFormatName("PNG");
	}

	@Override
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	@Override
	public String getFormatName() {
		return this.formatName;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		CaptchaGenerator captchaGenerator = getCaptchaGenerator();
		if (!(captchaGenerator instanceof ImageCaptchaGenerator))
			throw new IllegalArgumentException("Property 'captchaGenerator' must be instance of " 
					+ ImageCaptchaGenerator.class);
	}

	@Override
	public RenderedImage create(Serializable id, OutputStream output) throws IOException {
		ImageCaptchaGenerator captchaGenerator = (ImageCaptchaGenerator) getCaptchaGenerator();
		ImageCaptcha captcha = captchaGenerator.create();
		RenderedImage image = captcha.getImage();
		
		ImageIO.write(image, getFormatName(), output);  
		doCreate(id, captcha.getText());
		return image;
	}

}
