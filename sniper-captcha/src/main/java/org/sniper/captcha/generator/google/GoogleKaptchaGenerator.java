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

package org.sniper.captcha.generator.google;

import java.util.Map;
import java.util.Properties;

import org.sniper.captcha.ImageCaptcha;
import org.sniper.captcha.generator.AbstractImageCaptchaGenerator;
import org.sniper.captcha.generator.google.manager.color.GoogleCaptchaTextColorManager;
import org.sniper.captcha.manager.color.ColorManager;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.image.layout.CaptchaLayout;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;


/**
 * Google图片验证码处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class GoogleKaptchaGenerator extends AbstractImageCaptchaGenerator {
	
	/** 默认文字颜色集 */
	public static final String DEFAULT_FONTCOLOR = "red";
		
	/** 文字颜色与验证码对象关系映射集线程局部变量 */
	private static final ThreadLocal<Map<String, Producer>> kaptchas = new ThreadLocal<Map<String,Producer>>();
	
	@Override
	protected ColorManager buildDefaultTextColorManager() throws Exception {
		GoogleCaptchaTextColorManager textColorManager = new GoogleCaptchaTextColorManager();
		textColorManager.afterPropertiesSet();
		return textColorManager;
	}
	
	@Override
	public ImageCaptcha create() {
		String text = super.generate();
		return new ImageCaptcha(text, getProducer().createImage(text));
	}
	
	/**
	 * 获取具有指定文字颜色的验证码对象
	 * @author Daniele 
	 * @param fontColor
	 * @return
	 */
	protected Producer getProducer() {
		String fontColor = getTextColorManager().selectColorName();
		if (StringUtils.isBlank(fontColor))
			fontColor = DEFAULT_FONTCOLOR;
		
		Map<String, Producer> kaptchaMap = kaptchas.get();
		if (kaptchaMap == null)
			kaptchaMap = MapUtils.newConcurrentHashMap();
		
		Producer producer = kaptchaMap.get(fontColor);
		if (producer == null) {
			DefaultKaptcha kaptcha = new DefaultKaptcha();
			kaptcha.setConfig(new Config(build(fontColor)));
			producer = kaptcha;
			// 建立文字颜色与当前验证码对象的映射关系后存入线程局部变量
			kaptchaMap.put(kaptcha.getConfig().getProperties().getProperty("kaptcha.textproducer.font.color"), producer);
			kaptchas.set(kaptchaMap);
		}
		
		return producer;
	}
	
	/**
	 * 构建配置
	 * @author Daniele 
	 * @return
	 */
	protected Properties build(String fontColor) {
		CaptchaLayout layout = getLayout();
		String fontName = getTextFontManager().selectFontName();
		
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", layout.hasBorder() ? "yes" : "no");
		properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(getLength()));
		properties.setProperty("kaptcha.image.width", String.valueOf(layout.getWidth()));
		properties.setProperty("kaptcha.image.height", String.valueOf(layout.getHeight()));
		properties.setProperty("kaptcha.textproducer.font.size", String.valueOf(layout.getFontSize()));
		properties.setProperty("kaptcha.textproducer.font.names", fontName);
		properties.setProperty("kaptcha.textproducer.font.color", fontColor);
		return properties;
	}
	
}
