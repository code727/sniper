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

package org.workin.image.test.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.workin.image.layout.QRCodeLayout;
import org.workin.image.qrcode.QRCode;
import org.workin.image.qrcode.generator.google.GoogleQRCodeGenerator;
import org.workin.image.qrcode.generator.swetake.SwetakeQRCodeGenerator;
import org.workin.image.qrcode.parser.google.GoogleQRCodeParser;
import org.workin.image.qrcode.parser.jp.JpQRCodeParser;
import org.workin.image.reader.DefaultImageReader;
import org.workin.image.reader.ImageReader;
import org.workin.image.writer.DefaultImageWriter;
import org.workin.image.writer.ImageWriter;
import org.workin.test.junit.BaseTestCase;

/**
 * @description 二维码单元测试
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class QRCodeTest extends BaseTestCase {
	
	private String text = "http://www.cmge.com/";
	private String logoURL = "http://f.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=86ac36ad10ce36d3a204843602c85dba/0824ab18972bd407e8e3198a7e899e510fb309a5.jpg";
	private String google_qrcode = "C:/Users/Administrator/Desktop/test_google.png";
	private String swetake_qrcode = "C:/Users/Administrator/Desktop/test_swetake.png";
	
	@Test
	public void testGoogleQRCodeGenerator() throws Exception {
		QRCodeLayout layout = new QRCodeLayout();
		layout.setSideLength(300);
		layout.setMargin(5);
		QRCode qrCode = new QRCode();
		layout.setLogoScale(0.25);
		qrCode.setLayout(layout);
		qrCode.setText(text);
		qrCode.setLogo(ImageIO.read(new URL(logoURL).openStream()));
		
		GoogleQRCodeGenerator generator = new GoogleQRCodeGenerator();
		BufferedImage image = generator.generator(qrCode);
		
		ImageWriter imageWriter = new DefaultImageWriter();
		imageWriter.write(image, new File(google_qrcode));
	}
	
	@Test
	public void testSwetakeQRCodeGenerator() throws Exception {
		QRCodeLayout layout = new QRCodeLayout();
		layout.setSideLength(300);
		layout.setMargin(5);
		QRCode qrCode = new QRCode();
		layout.setLogoScale(0.25);
		qrCode.setLayout(layout);
		qrCode.setText(text);
		qrCode.setLogo(ImageIO.read(new URL(logoURL).openStream()));
		
		SwetakeQRCodeGenerator generator = new SwetakeQRCodeGenerator();
		BufferedImage image = generator.generator(qrCode);
		
		ImageWriter imageWriter = new DefaultImageWriter();
		imageWriter.write(image, new File(swetake_qrcode));
	}
	
	@Test
	public void testGoogleQRCodeParser() throws Exception {
		ImageReader reader = new DefaultImageReader();
		GoogleQRCodeParser parser = new GoogleQRCodeParser();
		
		String result = parser.parse(reader.read(new File(google_qrcode)));
		System.out.println(result);
		
		result = parser.parse(reader.read(new File(swetake_qrcode)));
		System.out.println(result);
	}
	
	/**
	 * @description 如果二维码图片太小，小日本的二维码解析器要报错，
	 * 				另外还不能解析出zxing生成的二维码图片
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws Exception
	 */
	@Test
	public void testJpQRCodeParser() throws Exception {
		ImageReader reader = new DefaultImageReader();
		JpQRCodeParser parser = new JpQRCodeParser();
		
		String result = parser.parse(reader.read(new File(swetake_qrcode)));
		System.out.println(result);
		
		result = parser.parse(reader.read(new File(google_qrcode)));
		System.out.println(result);
	}

}
