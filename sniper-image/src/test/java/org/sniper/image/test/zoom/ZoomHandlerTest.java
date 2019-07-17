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

package org.sniper.image.test.zoom;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.sniper.image.zoom.FixedZoomHandler;
import org.sniper.image.zoom.AdaptiveRatioHandler;
import org.sniper.test.junit.BaseTestCase;

/**
 * 缩放处理器单元测试
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ZoomHandlerTest extends BaseTestCase {
	
	/** 横向图 */
	private InputStream lateraImage;
	
	/** 纵向图 */
	private InputStream verticalImage;
	
	private File lateraImageFile = new File("/test/zoom/lateraImage.png");
	private File verticalImageFile = new File("/test/zoom/verticalImage.png");
	
	@Before
	public void init() throws IOException {
		// 1024 * 643  scale: 1.35 - 1.36
		lateraImage = new URL("http://pic.58pic.com/58pic/13/80/78/35V58PICrWD_1024.jpg").openStream();
		// 821 * 1024 scale: 0.65 - 0.66
		verticalImage = new URL("http://pic25.nipic.com/20121117/9252150_165726249000_2.jpg").openStream();
	}
	
//	@Test
	public void testFixedZoomHandler() throws Exception {
		FixedZoomHandler handler = new FixedZoomHandler();
		handler.setTargetWidth(800);
		handler.setTargetHeight(600);
		
		handler.handle(lateraImage, lateraImageFile);
		BufferedImage destImage = ImageIO.read(lateraImageFile); 
		
		System.out.println("像素区间["+ handler.getTargetWidth() + "*" + handler.getTargetHeight() + "]");
		System.out.println("目标横向图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "]");
		
		handler.handle(verticalImage, verticalImageFile);
		destImage = ImageIO.read(verticalImageFile); 
		
		System.out.println("像素区间["+ handler.getTargetWidth() + "*" + handler.getTargetHeight() + "]");
		System.out.println("目标纵向图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "]");
	}
	
	@Test
	public void testRatioHandler() throws Exception {
		AdaptiveRatioHandler handler = new AdaptiveRatioHandler();
		handler.setTargetWidth(800);
		handler.setTargetHeight(600);
		handler.setCompressOnly(false);
		
		handler.handle(lateraImage, lateraImageFile);
		BufferedImage destImage = ImageIO.read(lateraImageFile); 
		
		System.out.println("像素区间["+ handler.getTargetWidth() + "*" + handler.getTargetHeight() + "]");
		System.out.println("目标横向图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "]");
		
		handler.handle(verticalImage, verticalImageFile);
		destImage = ImageIO.read(verticalImageFile); 
		
		System.out.println("像素区间["+ handler.getTargetWidth() + "*" + handler.getTargetHeight() + "]");
		System.out.println("目标纵向图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "]");
	}
	

}
