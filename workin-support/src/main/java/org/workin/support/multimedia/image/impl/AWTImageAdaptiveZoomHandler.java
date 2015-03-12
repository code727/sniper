/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-1-15
 */

package org.workin.support.multimedia.image.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.workin.commons.util.StringUtils;

/**
 * @description AWT图像自适应缩放处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AWTImageAdaptiveZoomHandler extends AWTImageScaleZoomHandler {
	
	/** 最佳缩放比例集 */
	private static final Map<String, Double> scales;
	
	/** 当前所选最佳比例 */
	private double bestScale = scales.get("4:3");
	
	static {
		scales = new HashMap<String, Double>();
		scales.put("4:3", 0.75);
		scales.put("16:9", 0.375);
	}
	
	public double select(String name) {
		Double scale = scales.get(StringUtils.trim(name));
		bestScale = (scale != null ? scale : scales.get("4:3"));
		return bestScale;
	}
	
	@Override
	protected BufferedImage drawHandle(InputStream source) throws IOException {
		BufferedImage imageSource = ImageIO.read(source); 
		
		Map<String, Integer> adaptive = adaptive(imageSource);
		BufferedImage destImage = new BufferedImage(adaptive.get("width"),
				adaptive.get("height"), imageSource.getType());
		Graphics g = destImage.getGraphics();
		g.drawImage(imageSource, 0, 0, adaptive.get("width"), adaptive.get("height"), null);
		return destImage;
	}
	
	/**
	 * @description 自适应计算出最佳的长宽
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param image
	 * @return
	 */
	private Map<String, Integer> adaptive(BufferedImage image) {
		
		int zoomWidth = (int) Math.round(image.getWidth() * widthScale);
		int zoomHeight = (int) Math.round(image.getHeight() * heightScale);
		
		if (minWidth > 0 && zoomWidth < minWidth)
			// 如果设置了最小宽度，则缩放宽度不能低于最小值
			zoomWidth = minWidth;
		
		if (minHeight > 0 && zoomHeight < minHeight)
			// 如果设置了最小高度，则缩放高度不能低于最小值
			zoomHeight = minHeight;
		
		// 高按宽的比例缩放
//		zoomHeight = (int) Math.round(zoomWidth * bestScale);
		
		if (zoomWidth >= zoomHeight) 
			// 宽大于等于高，则高按宽的比例压缩
			zoomHeight = (int) Math.round(zoomWidth * bestScale);
		else 
			// 高大于宽，则宽按高的比例压缩
			zoomWidth = (int) Math.round(zoomHeight * bestScale);
			
		Map<String, Integer> adaptiveScale = new HashMap<String, Integer>();
		adaptiveScale.put("width", zoomWidth);
		adaptiveScale.put("height", zoomHeight);
		return adaptiveScale;
	}

}
