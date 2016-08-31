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
 * Create Date : 2016-1-27
 */

package org.workin.captcha;

import java.awt.image.RenderedImage;

/**
 * 图片验证码对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ImageCaptcha {

	/** 验证码文字内容 */
	private String text;

	/** 验证码图片对象 */
	private RenderedImage image;
	
	public ImageCaptcha() {}
		
	public ImageCaptcha(String text, RenderedImage image) {
		this.text = text;
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public RenderedImage getImage() {
		return image;
	}

	public void setImage(RenderedImage image) {
		this.image = image;
	}

}
