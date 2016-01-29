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

import java.util.Map;
import java.util.Properties;

import org.workin.captcha.ImageCaptcha;
import org.workin.captcha.generator.AbstractImageCaptchaGenerator;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;


/**
 * @description Google图片验证码处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GoogleKaptchaGenerator extends AbstractImageCaptchaGenerator {
	
	/** 文字颜色与验证码对象关系映射集线程局部变量 */
	private static final ThreadLocal<Map<String, Producer>> kaptchas = new ThreadLocal<Map<String,Producer>>();
	
	@Override
	public ImageCaptcha create() {
		String text = super.generate();
		ImageCaptcha captcha = new ImageCaptcha();
		captcha.setText(text);
		captcha.setImage(this.getProducer(selectFontColor()).createImage(text));
		return captcha;
	}
	
	/**
	 * @description 获取具有指定文字颜色的验证码对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fontColor
	 * @return
	 */
	protected Producer getProducer(String fontColor) {
		if (StringUtils.isBlank(fontColor))
			fontColor = DEFAULT_FONTCOLORS;
		
		Map<String, Producer> kaptchaMap = kaptchas.get();
		if (kaptchaMap == null)
			kaptchaMap = MapUtils.newConcurrentHashMap();
		
		Producer producer = kaptchaMap.get(fontColor);
		if (producer == null) {
			DefaultKaptcha kaptcha = new DefaultKaptcha();
			kaptcha.setConfig(new Config(build()));
			producer = kaptcha;
			// 建立文字颜色与当前验证码对象的映射关系后存入线程局部变量
			kaptchaMap.put(kaptcha.getConfig().getProperties().getProperty("kaptcha.textproducer.font.color"), producer);
			kaptchas.set(kaptchaMap);
		}
		
		return producer;
	}
	
	/**
	 * @description 构建配置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected Properties build() {
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "no");
		properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(super.getLength()));
		properties.setProperty("kaptcha.image.width", String.valueOf(super.getWidth()));
		properties.setProperty("kaptcha.image.height", String.valueOf(super.getHeight()));
		properties.setProperty("kaptcha.textproducer.font.size", String.valueOf(super.getFontSize()));
		properties.setProperty("kaptcha.textproducer.font.names", 
				StringUtils.isNotBlank(super.getFontNames()) ? super.getFontNames() : DEFAULT_FONTNAMES);
		
		properties.setProperty("kaptcha.textproducer.font.color", super.selectFontColor());
		return properties;
	}
		
}
