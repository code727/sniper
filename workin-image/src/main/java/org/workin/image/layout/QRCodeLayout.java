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
 * Create Date : 2016-6-21
 */

package org.workin.image.layout;

import java.awt.Color;

import org.workin.commons.util.NumberUtils;

/**
 * @description 二维码布局
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QRCodeLayout extends BaseLayout {
	
	private static final long serialVersionUID = -8237652728083670335L;
	
	/** 最小边长常量 */
	public static final int MIN_SIDELENGTH = 50;
	
	/** logo图片占总宽高的最小比例 */
	public static final double MIN_LOGOSCALE = 0.2;
	
	/** logo图片占总宽高的最大比例 */
	public static final double MAX_LOGOSCALE = 0.45;
	
	/** 边长 */
	private int sideLength = MIN_SIDELENGTH;
	
	/** 开放颜色 */
    private int onColor = 0xFF000000;
    
    /** 关闭颜色 */
    private int offColor = 0xFFFFFFFF;
    
    /** logo图片占总宽高的比例 */
    private double logoScale = MIN_LOGOSCALE;
    
    /** 是否需要背景 */
    private boolean logoBackground = true;
    
    /** logo背景颜色 */
    private Color logoBackgroundColor = Color.WHITE;
    
    /** 是否需要背景 */
    private boolean logoBorder = true;
    
    /** logo边框颜色 */
    private Color logoBorderColor = new Color(239, 239, 239);
    
    @Override
    public void setMargin(int margin) {
    	super.setMargin(NumberUtils.minLimit(margin, 0));
    }
    
	public int getOnColor() {
		return onColor;
	}

	public void setOnColor(int onColor) {
		this.onColor = onColor;
	}

	public int getOffColor() {
		return offColor;
	}

	public void setOffColor(int offColor) {
		this.offColor = offColor;
	}

	public int getSideLength() {
		return sideLength;
	}
	
	public void setSideLength(int sideLength) {
		this.sideLength = NumberUtils.minLimit(sideLength, MIN_SIDELENGTH);
	}

	public double getLogoScale() {
		return logoScale;
	}

	public void setLogoScale(double logoScale) {
		this.logoScale = NumberUtils.maxLimit(NumberUtils.minLimit(logoScale, MIN_LOGOSCALE), MAX_LOGOSCALE);
	}

	public boolean hasLogoBackground() {
		return logoBackground;
	}

	public void setLogoBackground(boolean logoBackground) {
		this.logoBackground = logoBackground;
	}

	public Color getLogoBackgroundColor() {
		return logoBackgroundColor;
	}

	public void setLogoBackgroundColor(Color logoBackgroundColor) {
		if (!hasLogoBackground() || logoBackgroundColor != null)
			this.logoBackgroundColor = logoBackgroundColor;
	}

	public boolean hasLogoBorder() {
		return logoBorder;
	}

	public void setLogoBorder(boolean logoBorder) {
		this.logoBorder = logoBorder;
	}

	public Color getLogoBorderColor() {
		return logoBorderColor;
	}

	public void setLogoBorderColor(Color logoBorderColor) {
		if (!hasLogoBorder() || logoBorderColor != null)
			this.logoBorderColor = logoBorderColor;
	}

}
