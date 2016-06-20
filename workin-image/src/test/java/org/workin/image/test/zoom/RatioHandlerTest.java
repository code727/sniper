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
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.workin.image.zoom.RatioHandler;
import org.workin.test.junit.BaseTestCase;

/**
 * @description 等比压缩处理器单元测试
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RatioHandlerTest extends BaseTestCase {
	
	@Test
	public void test() throws Exception {
		RatioHandler handler = new RatioHandler();
		handler.setMaxWidth(50);
		handler.setMaxHeight(200);
		
		URL url = new URL("http://a.dangdang.com/api/data/cpx/img/39320001/1");
		InputStream source = url.openStream();
		
		File dest = new File("C:/Users/Administrator/Desktop/test.png");
		handler.handle(source, dest);
		
		BufferedImage destImage = ImageIO.read(dest); 
		System.out.println("像素区间["+ handler.getMaxWidth() + "*" + handler.getMaxHeight() + "].");;
		System.out.println("目标图像素:[" + destImage.getWidth() + "*" + destImage.getHeight() + "].");
	}

}
