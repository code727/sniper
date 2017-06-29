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
 * Create Date : 2016年6月24日
 */

package org.sniper.captcha.manager.font;

import java.awt.Font;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 文本字体管理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TextFontManager implements FontManager, InitializingBean {
	
	private String fontName;
	
	private Map<String, Integer> fontStyles;
	
	/** 备选的字体名称组 */
	private String[] fontNames;
	
	/** 备选的字体大小名称组 */
	private String[] fontStyleNames;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isBlank(fontName))
			setFontName("Georgia");
		
		if (MapUtils.isEmpty(fontStyles))
			setFontStyles(buildDefaultFontStyles());
		
		setFontNames(getFontName());
		setFontStyleNames(getFontStyles());
	}

	@Override
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	@Override
	public String getFontName() {
		return this.fontName;
	}

	@Override
	public String selectFontName() {
		String name = ArrayUtils.get(fontNames, NumberUtils.randomIn(ArrayUtils.length(fontNames)));
		if (StringUtils.isBlank(name)) {
			String[] names = fontName.split(",");
			name = ArrayUtils.get(names, NumberUtils.randomIn(names.length));
		}
		
		return name;
	}

	@Override
	public void setFontStyles(Map<String, Integer> fontStyles) {
		this.fontStyles = fontStyles;
	}

	@Override
	public Map<String, Integer> getFontStyles() {
		return fontStyles;
	}

	@Override
	public String selectFontStyleName() {
		String name = ArrayUtils.get(fontStyleNames, NumberUtils.randomIn(ArrayUtils.length(fontStyleNames)));
		if (StringUtils.isBlank(name)) {
			String[] names = CollectionUtils.toArray(fontStyles.keySet(), String.class);
			name = ArrayUtils.get(names, NumberUtils.randomIn(names.length));
		}
		
		return name;
	}

	@Override
	public int selectFontStyle() {
		return getFontStyle(selectFontStyleName());
	}

	@Override
	public int getFontStyle(String name) {
		return NumberUtils.safeInteger(fontStyles.get(name));
	}
	
	/**
	 * 构建默认支持的字体样式映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected Map<String, Integer> buildDefaultFontStyles() {
		Map<String, Integer> fontStyles = MapUtils.newHashMap();
		fontStyles.put("bold", Font.BOLD);
		fontStyles.put("plain", Font.PLAIN);
		fontStyles.put("italic", Font.ITALIC);
		return fontStyles;
	}
	
	/**
	 * 设置字体名称组 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fontName
	 */
	protected void setFontNames(String fontName) {
		this.fontNames = fontName.split(",");
	}
	
	/**
	 * 获取字体名称组 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected String[] getFontNames() {
		return fontNames;
	}
	
	/**
	 * 设置字体样式名称组 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fontStyles
	 */
	protected void setFontStyleNames(Map<String, Integer> fontStyles) {
		this.fontStyleNames = CollectionUtils.toArray(fontStyles.keySet(), String.class);
	}
	
	/**
	 * 获取字体样式名称组 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected String[] getFontStyleNames() {
		return fontStyleNames;
	}

}
