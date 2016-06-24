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
 * Create Date : 2016-6-20
 */

package org.workin.image.qrcode;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import org.workin.image.layout.QRCodeImageLayout;
import org.workin.support.codec.CodecSupport;

/**
 * @description 二维码对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QRCode extends CodecSupport implements Serializable {
	
	private static final long serialVersionUID = 6804121350317192944L;
	
	/** 文本内容 */
	private String text;
	
	/** logo图片 */
	private BufferedImage logo;
	
	/** 图片布局 */
	private QRCodeImageLayout layout;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BufferedImage getLogo() {
		return logo;
	}

	public void setLogo(BufferedImage logo) {
		this.logo = logo;
	}

	public QRCodeImageLayout getLayout() {
		return layout;
	}

	public void setLayout(QRCodeImageLayout layout) {
		this.layout = layout;
	}
	
}
