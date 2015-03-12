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

package org.workin.support.multimedia.image;

import org.workin.commons.util.AssertUtils;

/**
 * @description 图像缩放处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractImageZoomHandler implements ImageZoomHandler {
	
	/** 最小的缩放宽度标准 */
	protected int minWidth;
	
	/** 最小的缩放高度标准 */
	protected int minHeight;

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		AssertUtils.assertTrue(minWidth > 0, "Image zoom minimum width must be more than 0");
		this.minWidth = minWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		AssertUtils.assertTrue(minHeight > 0, "Image zoom minimum height must be more than 0");
		this.minHeight = minHeight;
	}
	
}
