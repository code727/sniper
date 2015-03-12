/*
 * Copyright (c) 2015 org.workin-support 
 * Create Date : 2015-1-15
 */

package org.worin.support.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.workin.support.multimedia.image.ImageScaleZoomHandler;
import org.workin.support.multimedia.image.ImageZoomHandler;
import org.workin.support.multimedia.image.impl.AWTImageAdaptiveZoomHandler;
import org.workin.support.multimedia.image.impl.AWTImageFixedZoomHandler;
import org.workin.support.multimedia.image.impl.AWTImageScaleZoomHandler;
import org.workin.test.junit.BaseTestCase;

/**
 * @description 图像压缩处理单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-15
 */
public class ImageZoomTest extends BaseTestCase {
	
	@Test
	public void testFixedZoomHandler() throws IOException {
		int minHeight = 100;
		int minWidth = 200;
		
		ImageZoomHandler handler = new AWTImageFixedZoomHandler();
		handler.setMinHeight(minHeight);
		handler.setMinWidth(minWidth);
		File source = new File("D:/test.jpg");
		File dest = new File("D:/test_1.jpg");
		handler.handle(source, dest);
		
		BufferedImage imageSource = ImageIO.read(new FileInputStream(dest));
		assertEquals(minWidth, imageSource.getWidth());
		assertEquals(minHeight, imageSource.getHeight());
	}
	
	@Test
	public void testScaleZoomHandler() throws IOException {
		double heightScale = 0.75;
		double widthScale = 0.5;
		
		ImageScaleZoomHandler handler = new AWTImageScaleZoomHandler();
		handler.setHeightScale(heightScale);
		handler.setWidthScale(widthScale);
		File source = new File("D:/test.jpg");
		File dest = new File("D:/test_2.jpg");
		handler.handle(source, dest);
		
		BufferedImage srcImage = ImageIO.read(new FileInputStream(source));
		BufferedImage destImage = ImageIO.read(new FileInputStream(dest));
		assertEquals(heightScale, (double)destImage.getHeight()/srcImage.getHeight());
		assertEquals(widthScale, (double)destImage.getWidth()/srcImage.getWidth());
	}
	
	@Test
	public void testAdaptiveZoomHandler() throws IOException {
		ImageScaleZoomHandler handler = new AWTImageAdaptiveZoomHandler();
		File source = new File("D:/test.jpg");
		File dest = new File("D:/test_3.jpg");
		handler.handle(source, dest);
		BufferedImage destImage = ImageIO.read(new FileInputStream(dest));
		assertTrue(((double) destImage.getWidth() / destImage.getHeight()) == 0.75
				|| ((double) destImage.getHeight() / destImage.getWidth()) == 0.75);
	}
	
		
}
