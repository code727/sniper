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
 * Create Date : 2016-6-24
 */

package org.sniper.captcha.manager.font;

import java.util.Map;

/**
 * 字体管理器接口
 * @author  Daniele
 * @version 1.0
 */
public interface FontManager {
	
	/**
	 * 设置字体名称
	 * @author Daniele 
	 * @param fontName
	 */
	public void setFontName(String fontName);
	
	/**
	 * 获取字体名称
	 * @author Daniele 
	 * @return
	 */
	public String getFontName();
	
	/**
	 * 选择一个字体名称
	 * @author Daniele 
	 * @return
	 */
	public String selectFontName();
	
	/**
	 * 设置"名称 - 字体样式大小"映射集
	 * @author Daniele 
	 * @param fontStyles
	 */
	public void setFontStyles(Map<String, Integer> fontStyles);
	
	/**
	 * 获取"名称 - 字体样式大小"映射集
	 * @author Daniele 
	 * @return
	 */
	public Map<String, Integer> getFontStyles();
	
	/**
	 * 选择一个字体样式大小名称
	 * @author Daniele 
	 * @return
	 */
	public String selectFontStyleName();
	
	/**
	 * 选择一个字体样式大小
	 * @author Daniele 
	 * @return
	 */
	public int selectFontStyle();
	
	/**
	 * 获取指定名称对应的字体样式大小
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public int getFontStyle(String name);

}
