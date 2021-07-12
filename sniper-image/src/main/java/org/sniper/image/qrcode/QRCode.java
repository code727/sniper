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

package org.sniper.image.qrcode;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import org.sniper.codec.CodecSupport;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.image.layout.QRCodeLayout;

/**
 * 二维码实体对象
 * @author  Daniele
 * @version 1.0
 */
public class QRCode extends CodecSupport implements Serializable {
	
	private static final long serialVersionUID = 6804121350317192944L;
	
	/** 文本内容 */
	private final String text;
	
	/** 图片布局 */
	private QRCodeLayout layout;
	
	/** 内嵌的logo图片 */
	private BufferedImage logo;
	
	/** 图片类型 */
	private int imageType = BufferedImage.TYPE_INT_RGB;
	
	/** 纠错等级 */
	private String errorCorrectionLevel = "H";
	
	public QRCode(String text) {
		AssertUtils.assertNotBlank("QRCode text must not be null or empty");
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public BufferedImage getLogo() {
		return logo;
	}

	public void setLogo(BufferedImage logo) {
		this.logo = logo;
	}

	public QRCodeLayout getLayout() {
		return layout;
	}

	public void setLayout(QRCodeLayout layout) {
		this.layout = layout;
	}
	
	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = NumberUtils.minLimit(imageType, BufferedImage.TYPE_INT_RGB);
	}

	public String getErrorCorrectionLevel() {
		return errorCorrectionLevel;
	}

	public void setErrorCorrectionLevel(String errorCorrectionLevel) {
		AssertUtils.assertNotBlank(errorCorrectionLevel, "QRCode error correction level must not null or blank");
		this.errorCorrectionLevel = errorCorrectionLevel;
	}
	
}
