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
 * Create Date : 2016年6月20日
 */

package org.workin.image.test.zoom;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.workin.image.zoom.FixedZoomHandler;
import org.workin.image.zoom.RatioHandler;
import org.workin.test.junit.BaseTestCase;

/**
 * @description 缩放处理器单元测试
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ZoomHandlerTest extends BaseTestCase {
	
	/** 横向图 */
	private InputStream lateraImage;
	
	/** 纵向图 */
	private InputStream verticalImage;
	
	private File lateraImageFile = new File("C:/Users/Administrator/Desktop/lateraImage.png");
	private File verticalImageFile = new File("C:/Users/Administrator/Desktop/verticalImage.png");
	
	@Before
	public void init() throws IOException {
		lateraImage = new URL("http://thumbs.dreamstime.com/z/%D3%EB%D4%C2%C1%C1%B5%C4%CD%ED%C9%CF%BA%E1%CF%F2%A3%AC%BD%E1%B9%B9%CA%F7%BC%F4%D3%B0%A3%AC%D0%C7%D0%CE-28196962.jpg").openStream();
		verticalImage = new URL("http://s9.sinaimg.cn/middle/4fe42793ta02d76d21578&690").openStream();
	}
	
//	@Test
	public void testFixedZoomHandler() throws Exception {
		FixedZoomHandler handler = new FixedZoomHandler();
		handler.setTargetWidth(1024);
		handler.setTargetHeight(768);
		
		handler.handle(lateraImage, lateraImageFile);
		BufferedImage destImage = ImageIO.read(lateraImageFile); 
		
		System.out.println("像素区间["+ handler.getTargetWidth() + "*" + handler.getTargetHeight() + "].");;
		System.out.println("目标横向图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "].");
		
		handler.handle(verticalImage, verticalImageFile);
		destImage = ImageIO.read(verticalImageFile); 
		
		System.out.println("像素区间["+ handler.getTargetWidth() + "*" + handler.getTargetHeight() + "].");;
		System.out.println("目标纵向图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "].");
	}
	
	@Test
	public void testRatioHandler() throws Exception {
		RatioHandler handler = new RatioHandler();
		handler.setTargetWidth(800);
		handler.setTargetHeight(2000);
		
//		handler.handle(lateraImage, lateraImageFile);
//		BufferedImage destImage = ImageIO.read(lateraImageFile); 
//		
//		System.out.println("像素区间["+ handler.getTargetWidth() + "*" + handler.getTargetHeight() + "].");;
//		System.out.println("目标横向图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "].");
		
		handler.handle(verticalImage, verticalImageFile);
		BufferedImage destImage = ImageIO.read(verticalImageFile); 
		
		System.out.println("像素区间["+ handler.getTargetWidth() + "*" + handler.getTargetHeight() + "].");;
		System.out.println("目标纵向图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "].");
	}
	

}
