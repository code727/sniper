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
import org.workin.commons.util.NumberUtils;
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
	
	/** 默认文字颜色集 */
	public static final String DEFAULT_FONTCOLORS = "red";
	
	/** 默认字体集 */
	public static final String DEFAULT_FONTNAMES = "宋体,楷体,微软雅黑";
	
	/** 字体颜色 */
	private String fontColors = DEFAULT_FONTCOLORS;
	
	/** 字体名称 */
	private String fontNames = DEFAULT_FONTNAMES;
	
	/** 文字颜色与验证码对象关系映射集线程局部变量 */
	private static final ThreadLocal<Map<String, Producer>> kaptchas = new ThreadLocal<Map<String,Producer>>();
	
	public String getFontColors() {
		return this.fontColors;
	}

	public void setFontColors(String fontColors) {
		if (StringUtils.isNotBlank(fontColors))
			this.fontColors = fontColors;
	}

	public String getFontNames() {
		return this.fontNames;
	}

	public void setFontNames(String fontNames) {
		if (StringUtils.isNotBlank(fontNames))
			this.fontNames = fontNames;
	}
	
	@Override
	public ImageCaptcha create() {
		String text = super.generate();
		return new ImageCaptcha(text, this.getProducer(selectFontColor()).createImage(text));
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
		properties.setProperty("kaptcha.border", hasBorder() ? "yes" : "no");
		properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(super.getLength()));
		properties.setProperty("kaptcha.image.width", String.valueOf(super.getWidth()));
		properties.setProperty("kaptcha.image.height", String.valueOf(super.getHeight()));
		properties.setProperty("kaptcha.textproducer.font.size", String.valueOf(super.getTextFontSize()));
		properties.setProperty("kaptcha.textproducer.font.names", 
				StringUtils.isNotBlank(getFontNames()) ? getFontNames() : DEFAULT_FONTNAMES);
		
		properties.setProperty("kaptcha.textproducer.font.color", selectFontColor());
		return properties;
	}
	
	/**
	 * @description 选择文字颜色
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected String selectFontColor() {
		String fontColors = this.getFontColors();
		if (StringUtils.isNotBlank(fontColors)) {
			String[] colors = fontColors.split(",");
			// 随机选择一个文字颜色
			return colors[NumberUtils.randomIn(colors.length)];
		} else 
			return DEFAULT_FONTCOLORS;
	}
		
}
