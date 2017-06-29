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

import org.sniper.image.ImageHandler;

/**
 * 图像缩放处理器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ImageZoomHandler extends ImageHandler {
	
	/**
	 * 设置目标高度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param targetWidth
	 */
	public void setTargetWidth(int targetWidth);
	
	/**
	 * 获取目标高度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getTargetWidth();
	
	/**
	 * 设置目标高度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param targetHeight
	 */
	public void setTargetHeight(int targetHeight);
	
	/**
	 * 获取目标高度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getTargetHeight();
	
}
