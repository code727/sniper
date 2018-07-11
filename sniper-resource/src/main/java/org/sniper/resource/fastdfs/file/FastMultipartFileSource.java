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
 * Create Date : 2015年11月6日
 */

package org.sniper.resource.fastdfs.file;

import java.io.IOException;
import java.io.InputStream;

import org.sniper.resource.file.SpringMultipartFileSource;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastDFS MultipartFile源抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastMultipartFileSource extends AbstractFastFileSource<MultipartFile> {
	
	private SpringMultipartFileSource multipartFileSource;

	public FastMultipartFileSource(MultipartFile source) throws IOException {
		super(source);
	}

	@Override
	protected FileItem initialize(MultipartFile file) throws IOException {
		if (this.multipartFileSource == null)
			this.multipartFileSource = new SpringMultipartFileSource(file);
		
		String name = this.multipartFileSource.getName();
		String mainName = this.multipartFileSource.getMainName();
		String extName = this.multipartFileSource.getExtName();
		InputStream in = this.multipartFileSource.getInputStream();
		byte[] bytes = this.multipartFileSource.getBytes();
		
		return new FileItem(name, mainName, extName, in, bytes);
	}

}
