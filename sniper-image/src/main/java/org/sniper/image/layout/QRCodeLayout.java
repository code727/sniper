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

package org.sniper.image.layout;

import java.awt.Color;

import org.sniper.commons.util.NumberUtils;

/**
 * 二维码布局样式
 * @author  Daniele
 * @version 1.0
 */
public class QRCodeLayout extends BaseLayout implements Cloneable {
	
	private static final long serialVersionUID = -8237652728083670335L;
	
	/** 最小边长常量 */
	private static final int MIN_SIDELENGTH = 50;
	
	/** logo图片占总宽高的最小比例 */
	private static final double LOGO_MIN_SCALE = 0.05;
	
	/** logo图片占总宽高的最大比例 */
	private static final double LOGO_MAX_SCALE = 0.25;
		
	/** 默认的边框颜色(灰色) */
	private static final Color DEFAULT_BORDER_COLOR = new Color(215, 215, 215);
	
	/** 边长 */
	private int sideLength = 512;
	
	/** 开放颜色(黑色) */
    private Color onColor = Color.BLACK;
    
    /** 关闭颜色(白色) */
    private Color offColor = Color.WHITE;
    
    /** logo图片占总宽高的比例 */
    private double logoScale = 0.15;
    
    /** logo是否需要背景 */
    private boolean logoBackground = true;
    
    /** logo背景颜色(白色) */
    private Color logoBackgroundColor = Color.WHITE;
    
    /** logo背景是否需要边框 */
    private boolean logoBackgroundBorder = false;
    
    /** logo背景的边框颜色 */
    private Color logoBackgroundBorderColor = DEFAULT_BORDER_COLOR;
    
	/** logo图片是否需要边框 */
    private boolean logoBorder = true;
    
    /** logo图片的边框颜色 */
    private Color logoBorderColor = DEFAULT_BORDER_COLOR;
    
    @Override
    public void setMargin(int margin) {
    	super.setMargin(NumberUtils.minLimit(margin, 0));
    }
    
	public Color getOnColor() {
		return onColor;
	}

	public void setOnColor(Color onColor) {
		this.onColor = onColor;
	}

	public Color getOffColor() {
		return offColor;
	}

	public void setOffColor(Color offColor) {
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
		this.logoScale = NumberUtils.rangeLimit(logoScale, LOGO_MIN_SCALE, LOGO_MAX_SCALE);
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
	
	public boolean hasLogoBackgroundBorder() {
    	return logoBackgroundBorder;
    }
    
    public void setLogoBackgroundBorder(boolean logoBackgroundBorder) {
		this.logoBackgroundBorder = logoBackgroundBorder;
	}

	public void setLogoBackgroundBorderColor(Color logoBackgroundBorderColor) {
		this.logoBackgroundBorderColor = logoBackgroundBorderColor;
	}
	
	public Color getLogoBackgroundBorderColor() {
		return logoBackgroundBorderColor;
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
	
	@Override
	public QRCodeLayout clone() throws CloneNotSupportedException {
		return (QRCodeLayout) super.clone();
	}

}
