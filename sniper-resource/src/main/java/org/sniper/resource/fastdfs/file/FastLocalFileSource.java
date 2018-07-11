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
 * Create Date : 2015-11-6
 */

package org.sniper.resource.fastdfs.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.sniper.resource.file.AbstaractFileSource.FileItem;
import org.sniper.resource.file.LocalFileSource;

/**
 * FastDFS本地文件源实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastLocalFileSource extends AbstractFastFileSource<File> {
	
	private LocalFileSource localFileSource;

	public FastLocalFileSource(File source) throws IOException {
		super(source);
	}
	
	protected FileItem initialize(File file) throws IOException {
		if (this.localFileSource == null)
			this.localFileSource = new LocalFileSource(file);
		
		String name = this.localFileSource.getName();
		String mainName = this.localFileSource.getMainName();
		String extName = this.localFileSource.getExtName();
		InputStream in = this.localFileSource.getInputStream();
		byte[] bytes = this.localFileSource.getBytes();
		
		return new FileItem(name, mainName, extName, in, bytes);
	}
	
}
