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

package org.workin.image.zoom;

import org.workin.image.AbstractImageHandler;

/**
 * @description 标准缩放处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractStandardZoomHandler extends AbstractImageHandler
		implements ImageZoomHandler {
	
	/** 最大宽度 */
	protected int maxWidth = 50;
	
	/** 最大高度 */
	protected int maxHeight = 50;
	
	@Override
	public int getMaxWidth() {
		return maxWidth;
	}

	@Override
	public void setMaxWidth(int maxWidth) {
		if (maxWidth > 0)
			this.maxWidth = maxWidth;
	}

	@Override
	public int getMaxHeight() {
		return maxHeight;
	}

	@Override
	public void setMaxHeight(int maxHeight) {
		if (maxHeight > 0)
			this.maxHeight = maxHeight;
	}
	
	/**
	 * @description 判断是否超宽
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param width
	 * @return
	 */
	protected boolean overWidth(int width) {
		return width > maxWidth;
	}
	
	/**
	 * @description 判断是否超高
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param height
	 * @return
	 */
	protected boolean overHeight(int height) {
		return height > maxHeight;
	}
	
	/**
	 * @description 判断指定的宽高是否在最大范围区间内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean inRange(int width, int height) {
		return !overWidth(width) && !overHeight(height);
	}

}
