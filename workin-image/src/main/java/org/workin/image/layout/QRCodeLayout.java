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
 * @description 二维码布局
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QRCodeLayout extends BaseLayout {
	
	private static final long serialVersionUID = -8237652728083670335L;
	
	/** 最小边长常量 */
	public static final int MIN_SIDE_LENGTH = 50;
	
	/** 边长 */
	private int sideLength = MIN_SIDE_LENGTH;
	
	/** 开放颜色 */
    private int onColor = 0xFF000000;
    
    /** 关闭颜色 */
    private int offColor = 0xFFFFFFFF;
    
    @Override
    public void setMargin(int margin) {
    	if (margin > 0)
    		super.setMargin(margin);
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
		if (sideLength > MIN_SIDE_LENGTH)
			this.sideLength = sideLength;
	}
		
}
