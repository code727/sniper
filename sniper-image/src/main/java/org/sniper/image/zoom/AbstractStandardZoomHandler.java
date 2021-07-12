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

import org.sniper.image.AbstractImageHandler;

/**
 * 标准缩放处理器
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractStandardZoomHandler extends AbstractImageHandler
		implements ImageZoomHandler {
	
	/** 目标宽度 */
	protected int targetWidth = 50;
	
	/** 目标高度 */
	protected int targetHeight = 50;
	
	public int getTargetWidth() {
		return targetWidth;
	}

	public void setTargetWidth(int targetWidth) {
		if (targetWidth > 0)
			this.targetWidth = targetWidth;
	}

	public int getTargetHeight() {
		return targetHeight;
	}

	public void setTargetHeight(int targetHeight) {
		if (targetHeight > 0)
			this.targetHeight = targetHeight;
	}

	/**
	 * 判断是否超宽
	 * @author Daniele 
	 * @param width
	 * @return
	 */
	protected boolean overWidth(int width) {
		return width > targetWidth;
	}
	
	/**
	 * 判断是否超高
	 * @author Daniele 
	 * @param height
	 * @return
	 */
	protected boolean overHeight(int height) {
		return height > targetHeight;
	}
	
	/**
	 * 判断指定的宽高是否在目标范围区间内
	 * @author Daniele 
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean inRange(int width, int height) {
		return !overWidth(width) && !overHeight(height);
	}

}
