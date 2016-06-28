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

import org.workin.commons.util.NumberUtils;

/**
 * @description 二维码布局
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QRCodeLayout extends BaseLayout {
	
	private static final long serialVersionUID = -8237652728083670335L;
	
	/** 最小宽高尺寸大小常量 */
	public static final int MIN_SIZE = 50;
	
    private int contentColor = 0xFF000000;
    
    private int blankColor = 0xFFFFFFFF;
    
	public QRCodeLayout() {
		setWidth(MIN_SIZE);
		setHeight(MIN_SIZE);
	}
    
    @Override
    public void setWidth(int width) {
    	super.setWidth(NumberUtils.minLimit(width, MIN_SIZE));
    }
    
    @Override
    public void setHeight(int height) {
    	super.setHeight(NumberUtils.minLimit(height, MIN_SIZE));
    }
 
	@Override
	public void setMargin(int margin) {
		if (margin > 0)
			super.setMargin(margin);
	}
    
	public int getContentColor() {
		return contentColor;
	}

	public void setContentColor(int contentColor) {
		this.contentColor = contentColor;
	}

	public int getBlankColor() {
		return blankColor;
	}

	public void setBlankColor(int blankColor) {
		this.blankColor = blankColor;
	}
		
}
