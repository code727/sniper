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

package org.workin.captcha.manager.font;

import java.util.Map;

/**
 * @description 字体管理器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FontManager {
	
	/**
	 * @description 设置字体名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fontName
	 */
	public void setFontName(String fontName);
	
	/**
	 * @description 获取字体名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getFontName();
	
	/**
	 * @description 选择一个字体名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String selectFontName();
	
	/**
	 * @description 设置"名称 - 字体样式大小"映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param fontStyles
	 */
	public void setFontStyles(Map<String, Integer> fontStyles);
	
	/**
	 * @description 获取"名称 - 字体样式大小"映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, Integer> getFontStyles();
	
	/**
	 * @description 选择一个字体样式大小名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String selectFontStyleName();
	
	/**
	 * @description 选择一个字体样式大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int selectFontStyle();
	
	/**
	 * @description 获取指定名称对应的字体样式大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public int getFontStyle(String name);

}
