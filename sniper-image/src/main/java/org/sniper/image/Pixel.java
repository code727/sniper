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

package org.sniper.image;

import java.io.Serializable;

/**
 * 像素对象
 * @author  Daniele
 * @version 1.0
 */
public class Pixel implements Serializable {

	private static final long serialVersionUID = -6308548621758866410L;
	
	private int width;
	
	private int height;
	
	/** 宽的比例 */
	private double widthScale = 1.0;
	
	/** 高的比例 */
	private double heightScale = 1.0;
	
	public Pixel() {}
	
	public Pixel(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Pixel(int width, int height, double widthScale, double heightScale) {
		this.width = width;
		this.height = height;
		this.widthScale = widthScale;
		this.heightScale = heightScale;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getWidthScale() {
		return widthScale;
	}

	public void setWidthScale(double widthScale) {
		this.widthScale = widthScale;
	}

	public double getHeightScale() {
		return heightScale;
	}

	public void setHeightScale(double heightScale) {
		this.heightScale = heightScale;
	}
	
}
