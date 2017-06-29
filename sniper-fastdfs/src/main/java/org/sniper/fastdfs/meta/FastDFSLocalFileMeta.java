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

package org.sniper.fastdfs.meta;

import java.io.File;
import java.io.IOException;

import org.sniper.support.file.meta.LocalFileMeta;

/**
 * FastDFS本地文件源实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastDFSLocalFileMeta extends AbstractFastDFSMeta<File> {
	
	private LocalFileMeta localFileSource;

	public FastDFSLocalFileMeta(File source) throws IOException {
		super(source);
	}

	@Override
	protected void handle() throws IOException {
		if (this.localFileSource == null)
			this.localFileSource = new LocalFileMeta(getSource());
		
		this.name = this.localFileSource.getName();
		this.mainName = this.localFileSource.getMainName();
		this.extName = this.localFileSource.getExtName();
		this.in = this.localFileSource.getInputStream();
		this.bytes = this.localFileSource.getBytes();
	}
	
}
