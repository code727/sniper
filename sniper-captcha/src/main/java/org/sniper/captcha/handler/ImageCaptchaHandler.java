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
 * Create Date : 2016-3-1
 */

package org.sniper.captcha.handler;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 图片验证码处理器接口
 * @author  Daniele
 * @version 1.0
 */
public interface ImageCaptchaHandler extends CaptchaHandler {
	
	/**
	 * 设置图片格式名称
	 * @author Daniele 
	 * @param formatName
	 */
	public void setFormatName(String formatName);
	
	/**
	 * 获取图片格式名称
	 * @author Daniele 
	 * @return
	 */
	public String getFormatName();
	
	/**
	 * 创建图片验证码，并写入指定的输出对象
	 * @author Daniele 
	 * @param id
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public RenderedImage create(Serializable id, OutputStream output) throws IOException;

}
