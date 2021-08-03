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
 * Create Date : 2016-6-19
 */

package org.sniper.commons.util;

import java.awt.image.BufferedImage;

/**
 * 图片工具类
 * @author  Daniele
 * @version 1.0
 */
public abstract class ImageUtils {
	
	/**
	 * 判断是否为横向图
	 * @author Daniele 
	 * @param image
	 * @return
	 */
	public static boolean isLateraImage(BufferedImage image) {
		return image != null && isLateraImage(image.getWidth(), image.getHeight());
	}
	
	/**
	 * 判断是否为纵向图
	 * @author Daniele 
	 * @param image
	 * @return
	 */
	public static boolean isVerticalImage(BufferedImage image) {
		return image != null && isVerticalImage(image.getWidth(), image.getHeight());
	}
	
	/**
	 * 判断是否为正方形图
	 * @author Daniele 
	 * @param image
	 * @return
	 */
	public static boolean isQuadrateImage(BufferedImage image) {
		return image != null && isQuadrateImage(image.getWidth(), image.getHeight());
	}
	
	/**
	 * 判断是否为横向图
	 * @author Daniele 
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean isLateraImage(int width, int height) {
		return width > height;
	}
	
	/**
	 * 判断是否为纵向图
	 * @author Daniele 
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean isVerticalImage(int width, int height) {
		return height > width;
	}
	
	/**
	 * 判断是否为正方形图
	 * @author Daniele 
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean isQuadrateImage(int width, int height) {
		return width == height;
	}

}
