/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-11-5
 */

package org.sniper.support.file.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.sniper.commons.util.FileUtils;

/**
 * 本地文件源抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class LocalFileSource extends AbstaractFileSource<File> {

	public LocalFileSource(File source) throws IOException {
		super(source);
	}

	@Override
	protected FileItem initialize(File file) throws IOException {
		String name = file.getName();
		String mainName = FileUtils.getMainName(file);
		String extName = FileUtils.getExtensionName(file);
		FileInputStream input = new FileInputStream(file);
		byte[] bytes = new byte[input.available()];
		
//		input.read(bytes, 0, bytes.length);
		return new FileItem(name, mainName, extName, input, bytes);
				
	}
	
}
