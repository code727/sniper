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

package org.sniper.resource.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.sniper.commons.util.FileUtils;

/**
 * 本地文件源抽象类
 * @author  Daniele
 * @version 1.0
 */
public class LocalFileSource extends AbstaractFileSource<File> {

	public LocalFileSource(File file) throws IOException {
		super(file);
	}
	
	public LocalFileSource(File file, boolean delayedReading) throws IOException {
		super(file, delayedReading);
	}
	
	@Override
	protected FileEntry initialize(File file, boolean delayedReading) throws IOException {
		String name = file.getName();
		String mainName = FileUtils.getMainName(file);
		String extName = FileUtils.getExtensionName(file);
		FileInputStream input = new FileInputStream(file);
		byte[] bytes = createBytes(file, input, delayedReading);
		
		return new FileEntry(name, mainName, extName, input, bytes);
	}	

}
