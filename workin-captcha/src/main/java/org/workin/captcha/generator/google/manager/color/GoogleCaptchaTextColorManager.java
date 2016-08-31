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

package org.workin.captcha.generator.google.manager.color;

import java.awt.Color;
import java.util.Map;

import org.workin.captcha.manager.color.TextColorManager;
import org.workin.commons.util.MapUtils;

/**
 * Google 验证码文本颜色管理实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GoogleCaptchaTextColorManager extends TextColorManager {
	
	/**
	 * 重写父类方法，重构建GoogleKaptcha默认支持的颜色映射组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 
	 */
	@Override
	protected Map<String, Color> buildDefaultColors() {
		Map<String, Color> colors = MapUtils.newHashMap();
		colors.put("black", Color.BLACK);
		colors.put("blue", Color.BLUE);
		colors.put("cyan", Color.CYAN);
		colors.put("darkGray", Color.DARK_GRAY);
		colors.put("gray", Color.GRAY);
		colors.put("green", Color.GREEN);
		colors.put("lightGray", Color.LIGHT_GRAY);
		colors.put("magenta", Color.MAGENTA);
		colors.put("orange", Color.ORANGE);
		colors.put("pink", Color.PINK);
		colors.put("red", Color.RED);
		colors.put("yellow", Color.YELLOW);
		
		return colors;
	}
	
}
