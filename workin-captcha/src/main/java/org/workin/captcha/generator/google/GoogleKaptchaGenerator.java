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
 * Create Date : 2016-1-27
 */

package org.workin.captcha.generator.google;

import java.util.Properties;

import org.workin.captcha.ImageCaptcha;
import org.workin.captcha.generator.AbstractImageCaptchaGenerator;
import org.workin.commons.util.StringUtils;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.Configurable;


/**
 * @description Google图片验证码处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GoogleKaptchaGenerator extends AbstractImageCaptchaGenerator {
	
	private Producer producer;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.producer == null) {
			DefaultKaptcha kaptcha = new DefaultKaptcha();
			this.producer = kaptcha;
		}
	}
	
	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	@Override
	public ImageCaptcha create() {
		if (this.producer instanceof Configurable) 
			((Configurable)this.producer).setConfig(new Config(buildConfig()));
		
		ImageCaptcha captcha = new ImageCaptcha();
		String text = super.generate();
		captcha.setText(text);
		captcha.setImage(this.producer.createImage(text));
		return captcha;
	}
	
	/**
	 * @description 构建配置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected Properties buildConfig() {
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "no");
		properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(super.getLength()));
		properties.setProperty("kaptcha.image.width", String.valueOf(super.getWidth()));
		properties.setProperty("kaptcha.image.height", String.valueOf(super.getHeight()));
		properties.setProperty("kaptcha.textproducer.font.size", String.valueOf(super.getFontSize()));
		properties.setProperty("kaptcha.textproducer.font.names", 
				StringUtils.isNotBlank(super.getFontNames()) ? super.getFontNames() : DEFAULT_FONTNAMES);
						
		String fontColors = super.getFontColors();
		if (StringUtils.isBlank(fontColors))
			properties.setProperty("kaptcha.textproducer.font.color", DEFAULT_FONTCOLORS);
		else {
			String []colors = fontColors.split(",");
			properties.setProperty("kaptcha.textproducer.font.color", colors[(int) (Math.random() * colors.length)]);
		}
		
		return properties;
	}

}
