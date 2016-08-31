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

import org.workin.commons.util.MapUtils;

/**
 * 文本颜色管理实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TextColorManager extends AbstractColorManager {

	@Override
	protected Map<String, Color> buildDefaultColors() {
		Map<String, Color> colors = MapUtils.newHashMap();
		colors.put("0_0_0_black", Color.BLACK);
		colors.put("0_0_255_blue", Color.BLUE);
		colors.put("0_255_255_cyan", Color.CYAN);
//		colors.put("64_64_64_darkGray", Color.DARK_GRAY);
//		colors.put("128_128_128_gray", Color.GRAY);
		colors.put("0_255_0_green", Color.GREEN);
//		colors.put("192_192_192_lightGray", Color.LIGHT_GRAY);
		colors.put("255_0_255_magenta", Color.MAGENTA);
		colors.put("255_200_0_orange", Color.ORANGE);
		colors.put("255_175_175_pink", Color.PINK);
		colors.put("255_0_0_red", Color.RED);
//		colors.put("255_255_0_yellow", Color.YELLOW);
		
		return colors;
	}

}
