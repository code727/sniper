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

package org.sniper.support.file.meta;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.sniper.commons.util.FileUtils;

/**
 * 本地文件源抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class LocalFileMeta extends AbstaractFileMeta<File> {

	public LocalFileMeta(File source) throws IOException {
		super(source);
	}

	@Override
	protected void handle() throws IOException {
		File source = getSource();
		this.name = source.getName();
		this.mainName = FileUtils.getMainName(source);
		this.extName = FileUtils.getExtensionName(source);
		this.in = new FileInputStream(source);
		this.bytes = new byte[this.in.available()];
		this.in.read(bytes, 0, bytes.length);
	}
	
}
