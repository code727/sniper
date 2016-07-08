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
 * Create Date : 2016-6-18
 */

package org.workin.image.zoom;

import java.awt.image.BufferedImage;

import org.workin.commons.util.ImageUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.image.Pixel;

/**
 * @description 图片等比压缩处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RatioHandler extends AbstractStandardZoomHandler {
	
	@Override
	protected Pixel createTragetPixel(BufferedImage sourceImage) {
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		
		/* 当原图的宽和高不在指定的范围内时，则计算出所要缩小或放大的目标像素 */
		if (!inRange(width, height)) {
			
			/* 限定被缩放的宽和高在指定的最大范围内 */
			int ratioWidth = NumberUtils.maxLimit(width, targetWidth);
			int ratiotHeight =  NumberUtils.maxLimit(height, targetHeight);
			
			/* 如果原图为横向的，则以宽度为准，高度为"宽度 * 高/宽比 
			 * 否则以高度为准，宽度为"高度 * 高/宽比"*/
			if (ImageUtils.isLateraImage(width, height)) {
				// 原图高/宽比
				double hwScale = (double) height / width; 
				width = ratioWidth;
				height = (int) (width * hwScale);
			} else {
				// 原图宽/高比
				double whScale = (double) width / height; 
				height = ratiotHeight;
				width = (int) (height * whScale);
			}
		}
		
		return new Pixel(NumberUtils.minLimit(width, 1), NumberUtils.minLimit(height, 1));
	}

}