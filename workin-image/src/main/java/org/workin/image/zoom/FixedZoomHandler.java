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
 * Create Date : 2016-7-8
 */

package org.workin.image.zoom;

import java.awt.image.BufferedImage;

import org.workin.image.Pixel;

/**
 * @description 固定像素缩放处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FixedZoomHandler extends AbstractStandardZoomHandler {

	@Override
	protected Pixel createTragetPixel(BufferedImage sourceImage) {
		return new Pixel(getTargetWidth(), getTargetHeight());
	}

}
