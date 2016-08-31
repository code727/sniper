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


/**
 * 文本框布局
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TextBoxLayout extends BaseLayout {

	private static final long serialVersionUID = -872351049933813382L;
	
	/** 宽 */
    private int width;
    
    /** 高 */
    private int height;
	
	/** 文字大小 */
	private int fontSize;
			
	/** 是否需要边框 */
	private boolean border = true;
	
	/** 边框样式 */
	private int borderStyle;
	
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

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public boolean hasBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public int getBorderStyle() {
		return borderStyle;
	}

	public void setBorderStyle(int borderStyle) {
		this.borderStyle = borderStyle;
	}
	
}
