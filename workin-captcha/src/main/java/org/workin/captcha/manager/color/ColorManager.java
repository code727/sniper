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

package org.workin.captcha.manager.color;

import java.awt.Color;
import java.util.Map;

/**
 * 颜色管理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ColorManager {
	
	/**
	 * 设置"名称-颜色"映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param colors
	 */
	public void setColors(Map<String, Color> colors);
	
	/**
	 * 获取"名称-颜色"映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, Color> getColors();
	
	/**
	 * 选择一个颜色名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String selectColorName();
	
	/**
	 * 选择一个颜色对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Color selectColor();
	
	/**
	 * 获取指定名称的颜色对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Color getColor(String name);
	
}
