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
 * 背景颜色管理实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BorderColorManager extends AbstractColorManager {

	@Override
	protected Map<String, Color> buildDefaultColors() {
		Map<String, Color> colors = MapUtils.newHashMap();
		colors.put("224_224_224_gray", new Color(224, 224, 224));
		return colors;
	}

}
