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

package org.sniper.captcha.manager.color;

import java.awt.Color;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 颜色管理器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractColorManager implements ColorManager, InitializingBean {
	
	private Map<String, Color> colors;
	
	/** 备选颜色名称组 */
	private String[] colorNames;
	
	@Override
	public void setColors(Map<String, Color> colors) {
		this.colors = colors;
	}

	@Override
	public Map<String, Color> getColors() {
		return colors;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (MapUtils.isEmpty(colors))
			setColors(buildDefaultColors());
		
		setTextColorNames(getColors());
	}
	
	@Override
	public String selectColorName() {
		String name = ArrayUtils.get(colorNames, NumberUtils.randomIn(ArrayUtils.length(colorNames)));
		if (StringUtils.isBlank(name)) {
			String[] names = CollectionUtils.toArray(colors.keySet(), String.class);
			name = ArrayUtils.get(names, NumberUtils.randomIn(names.length));
		}
		
		return name;
	}

	@Override
	public Color selectColor() {
		return getColor(selectColorName());
	}

	@Override
	public Color getColor(String name) {
		return colors.get(name);
	}

	/**
	 * 获取颜色名称组
	 * @author Daniele 
	 * @return
	 */
	protected String[] getColorNames() {
		return colorNames;
	}
	
	/**
	 * 根据备选颜色对象映射设置颜色名称组
	 * @author Daniele 
	 * @param colors
	 */
	protected void setTextColorNames(Map<String, Color> colors) {
		this.colorNames = CollectionUtils.toArray(colors.keySet(), String.class);
	}
	
	/**
	 * 构建默认支持的颜色映射集
	 * @author Daniele 
	 * @return
	 */
	protected abstract Map<String, Color> buildDefaultColors();
	
}
