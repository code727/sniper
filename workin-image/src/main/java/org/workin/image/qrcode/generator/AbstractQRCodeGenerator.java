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
 * Create Date : 2016-6-27
 */

package org.workin.image.qrcode.generator;

import java.awt.image.RenderedImage;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.StringUtils;
import org.workin.image.layout.QRCodeLayout;
import org.workin.image.qrcode.QRCode;

/**
 * @description 二维码生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractQRCodeGenerator implements QRCodeGenerator {
	
	/** 全局样式 */
	private QRCodeLayout layout;
	
	public AbstractQRCodeGenerator() {
		this.layout = new QRCodeLayout();
	}
	
	public void setLayout(QRCodeLayout layout) {
		this.layout = layout;
	}
	
	public QRCodeLayout getLayout() {
		return layout;
	}
		
	public RenderedImage generator(QRCode qrCode) throws Exception {
		AssertUtils.assertTrue(StringUtils.isNotEmpty(qrCode.getText()), "QRCode text must not be null or empty.");
		prepare(qrCode);
		return doCreate(qrCode);
	}
	
	/**
	 * @description 创建二维码图片
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCode
	 * @return
	 * @throws Exception
	 */
	protected abstract RenderedImage doCreate(QRCode qrCode) throws Exception;

	/**
	 * @description 准备操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param qrCode
	 */
	protected void prepare(QRCode qrCode) {
		QRCodeLayout layout = qrCode.getLayout();
		if (layout == null) 
			// 将全局样式赋予当前二维码对象
			qrCode.setLayout(getLayout());
	}

}
