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
 * Create Date : 2016-8-1
 */

package org.workin.templet.test.render;

import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.MapUtils;
import org.workin.templet.view.velocity.ClasspathVelocityRender;
import org.workin.templet.view.velocity.FileVelocityRender;
import org.workin.templet.view.velocity.PropertiesVelocityRender;
import org.workin.test.junit.BaseTestCase;

/**
 * @description Velocity模板渲染器单元测试类 
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class VelocityRenderTest extends BaseTestCase {
	
//	@Test
	public void testClasspathVelocityRender() {
		ClasspathVelocityRender velocityRender = new ClasspathVelocityRender();
		velocityRender.setSuffix(".html");
		
		Map<Object, Object> context = MapUtils.newHashMap();
		context.put("title", ClasspathVelocityRender.class.getName() + " - 测试页面");
		context.put("currentTime", DateUtils.dateToString(new Date()));
		
		StringWriter writer = new StringWriter();
		velocityRender.rende("test", context, writer);
		System.out.println(writer.toString());
	}
	
//	@Test
	public void testFileVelocityRender() {
		FileVelocityRender velocityRender = new FileVelocityRender();
		velocityRender.setPrefix("C:/Users/Administrator/Desktop/vm/");
		velocityRender.setSuffix(".html");
		
		Map<Object, Object> context = MapUtils.newHashMap();
		context.put("title", FileVelocityRender.class.getName() + " - 测试页面");
		context.put("currentTime", DateUtils.dateToString(new Date()));
		
		StringWriter writer = new StringWriter();
		velocityRender.rende("test", context, writer);
		System.out.println(writer.toString());
	}
	
	@Test
	public void testPropertiesVelocityRender() {
		PropertiesVelocityRender velocityRender = new PropertiesVelocityRender();
		
		velocityRender.setPrefix("C:/Users/Administrator/Desktop/vm/");
		velocityRender.setSuffix(".html");
		
		Map<Object, Object> context = MapUtils.newHashMap();
		context.put("title", PropertiesVelocityRender.class.getName() + " - 测试页面");
		context.put("currentTime", DateUtils.dateToString(new Date()));
		
		StringWriter writer = new StringWriter();
		velocityRender.rende("test", context, writer);
		System.out.println(writer.toString());
		
	}

}
