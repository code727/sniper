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

package org.sniper.templet.view.velocity;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * 类路径Velocity视图渲染器实现类
 * @author  Daniele
 * @version 1.0
 */
public class ClasspathVelocityViewRender extends AbstractVelocityViewRender {
		
	/**
	 * 构建Velocity模板引擎
	 * @author Daniele 
	 * @return
	 */
	@Override
	protected VelocityEngine buildEngine() {
		/* 从类路径环境中加载模板文件 */
		Properties properties = new Properties();
		properties.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		return new VelocityEngine(properties);
	}

}
