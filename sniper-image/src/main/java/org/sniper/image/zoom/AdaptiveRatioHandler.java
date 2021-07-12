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

package org.sniper.image.zoom;

import java.awt.image.BufferedImage;

import org.sniper.commons.util.ImageUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.image.Pixel;

/**
 * 自适应图片等比缩放处理器
 * @author  Daniele
 * @version 1.0
 */
public class AdaptiveRatioHandler extends AbstractStandardZoomHandler {
	
	/** 是否只用于压缩处理 */
	private boolean compressOnly;
	
	/**
	 * 判断是否只用于压缩处理
	 * @author Daniele 
	 * @return
	 */
	public boolean isCompressOnly() {
		return compressOnly;
	}

	/**
	 * 设置是否只用于压缩处理
	 * @author Daniele 
	 * @param compressOnly
	 */
	public void setCompressOnly(boolean compressOnly) {
		this.compressOnly = compressOnly;
	}

	@Override
	protected Pixel createTragetPixel(BufferedImage sourceImage) {
		Pixel pixel;
		if (isCompressOnly() && inRange(sourceImage.getWidth(), sourceImage.getHeight()))
			pixel = new Pixel(sourceImage.getWidth(), sourceImage.getHeight());
		else
			pixel = createByImageProperty(sourceImage);
		
		return pixel;
	}
	
	/**
	 * 按原图片自身的属性为基准创建目标像素
	 * @author Daniele 
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
	 * 以宽度为优先基准创建目标像素
	 * @author Daniele 
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
	 * 以高度为优先基准创建目标像素
	 * @author Daniele 
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