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

import javax.imageio.ImageIO;




import org.workin.commons.util.AssertUtils;
import org.workin.support.multimedia.image.AbstractAWTImageZoomHandler;
import org.workin.support.multimedia.image.ImageScaleZoomHandler;

/**
 * @description AWT图像比例缩放处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AWTImageScaleZoomHandler extends AbstractAWTImageZoomHandler
		implements ImageScaleZoomHandler {
		
	/** 宽度缩放比例 */
	protected double widthScale = 1.0;
	
	/** 高度缩放比例 */
	protected double heightScale = 1.0;
	
	@Override
	public void setWidthScale(double widthScale) {
		AssertUtils.assertTrue(widthScale > 0, "Image zoom width scale must be more than 0");
		this.widthScale = widthScale;
	}

	@Override
	public double getWidthScale() {
		return this.widthScale;
	}

	@Override
	public void setHeightScale(double heightScale) {
		AssertUtils.assertTrue(heightScale > 0, "Image zoom height scale must be more than 0");
		this.heightScale = heightScale;
	}

	@Override
	public double getHeightScale() {
		return this.heightScale;
	}

	@Override
	protected BufferedImage drawHandle(InputStream source) throws IOException {
		BufferedImage imageSource = ImageIO.read(source); 
		
		int zoomWidth = (int) Math.round(imageSource.getWidth() * widthScale);
		int zoomHeight = (int) Math.round(imageSource.getHeight() * heightScale);
				
		// 目标图片对象
		BufferedImage destImage = new BufferedImage(zoomWidth, zoomHeight, imageSource.getType());
		Graphics g = destImage.getGraphics();
		g.drawImage(imageSource, 0, 0, zoomWidth, zoomHeight, null);
		return destImage;
	}

}
