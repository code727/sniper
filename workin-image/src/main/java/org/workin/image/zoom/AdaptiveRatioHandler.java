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
 * @description 自适应图片等比缩放处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AdaptiveRatioHandler extends AbstractStandardZoomHandler {
	
	@Override
	protected Pixel createTragetPixel(BufferedImage sourceImage) {
		return createByImageProperty(sourceImage);
	}
	
	/**
	 * @description 按原图片自身的属性为基准创建目标像素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sourceImage
	 * @return
	 */
	protected Pixel createByImageProperty(BufferedImage sourceImage) {
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		
		Pixel pixel;
		if (ImageUtils.isLateraImage(width, height))
			pixel = createByWidthPriority(width, height);
		else
			pixel = createByHeightPriority(width, height);
		
		return pixel;
	}
	
	/**
	 * @description 已宽度为优先基准创建目标像素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param width
	 * @param height
	 * @return
	 */
	private Pixel createByWidthPriority(int width, int height) {
		// 原图高/宽比
		double hwScale = (double) height / width;
		width = targetWidth;
		height = (int) Math.ceil((width * hwScale)) ;
		if (overHeight(height)) {
			height = targetHeight;
			width = (int) Math.ceil((height / hwScale));
		}
		
		return new Pixel(NumberUtils.minLimit(width, 1), NumberUtils.minLimit(height, 1));
	}
	
	/**
	 * @description 已高度为优先基准创建目标像素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param width
	 * @param height
	 * @return
	 */
	private Pixel createByHeightPriority(int width, int height) {
		// 原图宽/高比
		double whScale = (double) width / height;
		
		height = targetHeight;
		width = (int) Math.ceil((height * whScale));
		if (overWidth(width)) {
			width = targetWidth;
			height = (int) Math.ceil((width / whScale)) ;
		}
		
		return new Pixel(NumberUtils.minLimit(width, 1), NumberUtils.minLimit(height, 1));
	}
	
	
}