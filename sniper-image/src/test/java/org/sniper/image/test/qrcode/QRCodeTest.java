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

package org.sniper.image.test.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.image.qrcode.QRCode;
import org.sniper.image.qrcode.generator.GoogleQRCodeGenerator;
import org.sniper.image.qrcode.generator.SwetakeQRCodeGenerator;
import org.sniper.image.qrcode.parser.GoogleQRCodeParser;
import org.sniper.image.qrcode.parser.JpQRCodeParser;
import org.sniper.image.reader.DefaultImageReader;
import org.sniper.image.reader.ImageReader;
import org.sniper.image.writer.DefaultImageWriter;
import org.sniper.image.writer.ImageWriter;
import org.sniper.test.junit.BaseTestCase;

/**
 * 二维码单元测试
 * @author  Daniele
 * @version 1.0
 */
public class QRCodeTest extends BaseTestCase {
	
	private final String[] texts = new String[]{
		"a", "qrcode_test", StringUtils.unsignedUUID(),
		"http://f.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=86ac36ad10ce36d3a204843602c85dba/0824ab18972bd407e8e3198a7e899e510fb309a5.jpg",	
		"二维码又称二维条码，常见的二维码为QR Code，QR全称Quick Response，是一个近几年来移动设备上超流行的一种编码方式，它比传统的Bar Code条形码能存更多的信息，也能表示更多的数据类型。"	
	};
	
	private final String logoURL = "http://pic.58pic.com/58pic/15/02/65/67d58PICkMZ_1024.jpg";
	
	private final String google_qrcode = "/test/QRCode/test_google.png";
	private final String swetake_qrcode = "/test/QRCode/test_swetake.png";
	
	private final File googleQRCodeFile = new File(google_qrcode);
	private final File swetakeQRCodeFile = new File(swetake_qrcode);
	
	@Test
	public void testGoogleGenerateAndParse() throws Exception {
		generateGoogleQRCode();
		
		ImageReader reader = new DefaultImageReader();
		GoogleQRCodeParser googleParser = new GoogleQRCodeParser();
		JpQRCodeParser swetakeParser = new JpQRCodeParser();
			
		String result1 = googleParser.parse(reader.read(googleQRCodeFile));
		System.out.println("Google parse google_qrcode:" + result1);
		
		String result2 = swetakeParser.parse(reader.read(googleQRCodeFile));
		System.out.println("Swetake parse google_qrcode:" + result2);
	}
	
//	@Test
	public void testSwetakeGenerateAndParse() throws Exception {
		generateSwetakeQRCode();
		
		ImageReader reader = new DefaultImageReader();
		JpQRCodeParser swetakeParser = new JpQRCodeParser();
		GoogleQRCodeParser googleParser = new GoogleQRCodeParser();
		
		String result1 = googleParser.parse(reader.read(swetakeQRCodeFile));
		System.out.println("Google parse swetake_qrcode:" + result1);
		
		String result2 = swetakeParser.parse(reader.read(swetakeQRCodeFile));
		System.out.println("Swetake parse swetake_qrcode:" + result2);
		
	}
	
	protected void generateGoogleQRCode() throws Exception {
		String text = texts[NumberUtils.randomIn(texts.length)];
		System.out.println("Encode text:" + text);
		
		QRCode qrCode = new QRCode(text);
		qrCode.setLogo(ImageIO.read(new URL(logoURL).openStream()));
		
		GoogleQRCodeGenerator generator = new GoogleQRCodeGenerator();
		BufferedImage image = generator.generate(qrCode);
		
		File googleQRCodeFile = new File(google_qrcode);
		
		File parent = googleQRCodeFile.getParentFile();
		if (!parent.exists()) 
			parent.mkdirs();
				
		ImageWriter imageWriter = new DefaultImageWriter();
		imageWriter.write(image, googleQRCodeFile);
	}
	
	protected void generateSwetakeQRCode() throws Exception {
		String text = texts[NumberUtils.randomIn(texts.length)];
		System.out.println("Encode text:" + text);
		
		QRCode qrCode = new QRCode(text);
		qrCode.setLogo(ImageIO.read(new URL(logoURL).openStream()));
				
		SwetakeQRCodeGenerator generator = new SwetakeQRCodeGenerator();
		BufferedImage image = generator.generate(qrCode);
		
		File parent = swetakeQRCodeFile.getParentFile();
		if (!parent.exists()) 
			parent.mkdirs();
		
		ImageWriter imageWriter = new DefaultImageWriter();
		imageWriter.write(image, swetakeQRCodeFile);
	}
	
}
