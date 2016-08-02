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
 * Create Date : 2016-8-2
 */

package org.workin.templet.view.velocity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.workin.commons.util.PropertiesUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 以Properties配置为基准的Velocity视图渲染器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PropertiesVelocityViewRender extends AbstractVelocityViewRender {
	
	/** Properties资源路径，默认为当前workin-templet工程类路径内的velocity.properties  */
	private String resourcePath = StringUtils.CLASSPATH + "/velocity.properties";
	
	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	@Override
	protected VelocityEngine buildEngine() throws IOException {
		String realPath = resourcePath;
		
		Properties properties = null;
		if (StringUtils.indexOfIgnoreCase(resourcePath, StringUtils.CLASSPATH) > -1) {
			/* 从当前类路径中加载Properties文件 */
			realPath = StringUtils.afterLastIgnoreCase(resourcePath, StringUtils.CLASSPATH);

			InputStream inputStream = this.getClass().getResourceAsStream(realPath);
			if (inputStream != null)
				properties = PropertiesUtils.create(inputStream);
			else
				throw new FileNotFoundException("Can not be found properties file [" 
						+ realPath + "] in current class path.");
						
		} else 
			// 从当前系统的本地绝对路径中加载Properties文件 
			properties = PropertiesUtils.create(new File(realPath));
		
		return new VelocityEngine(properties);
	}

}
