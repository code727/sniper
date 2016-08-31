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
 * 背景颜色管理实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BackgroundColorManager extends AbstractColorManager {

	@Override
	protected Map<String, Color> buildDefaultColors() {
		Map<String, Color> colors = MapUtils.newHashMap();
		colors.put("255_255_255_white", Color.WHITE);
		colors.put("239_239_239_gray", new Color(239, 239, 239));
		colors.put("223_223_223_gray", new Color(223, 223, 223));
		colors.put("207_207_207_gray", new Color(207, 207, 207));
		colors.put("191_191_191_gray", new Color(191, 191, 191));
		return colors;
	}

}
