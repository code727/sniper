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

import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.image.layout.QRCodeImageLayout;
import org.workin.image.qrcode.QRCode;

/**
 * @description 二维码生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractQRCodeGenerator implements QRCodeGenerator, InitializingBean {
	
	/** 全局样式 */
	private QRCodeImageLayout layout;
	
	public void setLayout(QRCodeImageLayout layout) {
		this.layout = layout;
	}
	
	public QRCodeImageLayout getLayout() {
		return layout;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.layout == null)
			setLayout(buildDefaultLayout());
		
		formatLayout();
	}

	/**
	 * @description 构建全局默认的验证码样式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected QRCodeImageLayout buildDefaultLayout() {
		QRCodeImageLayout layout = new QRCodeImageLayout();
		layout.setWidth(100);
		layout.setHeight(100);
		layout.setMargin(0);
		return layout;
	}
	
	/**
	 * @description 格式化
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private void formatLayout() {
		int width = NumberUtils.minLimit(layout.getWidth(), 100);
		int height = NumberUtils.minLimit(layout.getHeight(), 100);
		
		int size = Math.max(width, height);
		/* 控制验证码为一个以宽高最大尺寸为基准的正方形 */
		layout.setWidth(size);
		layout.setHeight(size);
		
		int margin = layout.getMargin();
		if (margin < 0)
			layout.setMargin(0);
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
		QRCodeImageLayout layout = qrCode.getLayout();
		if (layout == null) {
			layout = new QRCodeImageLayout();
			qrCode.setLayout(layout);
		}
		
		/* 长度不能小于全局样式的 */
		int width = NumberUtils.minLimit(layout.getWidth(), this.getLayout().getWidth());
		int height = NumberUtils.minLimit(layout.getHeight(), this.getLayout().getHeight());
		
		int size = Math.max(width, height);
		/* 控制验证码为一个以宽高最大尺寸为基准的正方形 */
		layout.setWidth(size);
		layout.setHeight(size);
		
		// 边距也不能小于全局样式的
		int margin = NumberUtils.minLimit(layout.getMargin(), this.getLayout().getMargin());
		layout.setMargin(margin);
	}

}
