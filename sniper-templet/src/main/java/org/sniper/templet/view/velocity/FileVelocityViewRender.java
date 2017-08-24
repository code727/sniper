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
import org.sniper.commons.util.FileUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 文件路径Velocity视图渲染器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FileVelocityViewRender extends AbstractVelocityViewRender {
	
	/** 是否加载相对路径下的模板文件，默认加载绝对路径下的 */
	private boolean loadRelativePathTemplet;
	
	public boolean isLoadRelativePathTemplet() {
		return loadRelativePathTemplet;
	}

	public void setLoadRelativePathTemplet(boolean loadRelativePathTemplet) {
		this.loadRelativePathTemplet = loadRelativePathTemplet;
	}

	@Override
	protected VelocityEngine buildEngine() {
		Properties properties = new Properties();
		if (isLoadRelativePathTemplet()) 
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, FileUtils.EXTENSION_SEPERATOR);
		else
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, StringUtils.EMPTY);
			
		return new VelocityEngine(properties);
	}
	
}
