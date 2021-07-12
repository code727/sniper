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

package org.sniper.captcha.manager.color;

import java.awt.Color;
import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * 干扰项颜色管理实现类
 * @author  Daniele
 * @version 1.0
 */
public class DistracterColorManager extends AbstractColorManager {

	@Override
	protected Map<String, Color> buildDefaultColors() {
		Map<String, Color> colors = MapUtils.newHashMap();
		colors.put("255_172_172_red", new Color(255, 172, 172));
		colors.put("255_220_185_orange", new Color(255, 220, 185));
		colors.put("172_255_172_green", new Color(172, 255, 172));
		colors.put("196_196_255_blue", new Color(196, 196, 255));
		colors.put("185_255_255_cyan", new Color(185, 255, 255));
		
		return colors;
	}

}
