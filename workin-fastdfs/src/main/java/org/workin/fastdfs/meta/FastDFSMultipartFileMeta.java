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

package org.workin.fastdfs.meta;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.workin.web.spring.file.MultipartFileMeta;


/**
 * @description FastDFS MultipartFile源抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastDFSMultipartFileMeta extends AbstractFastDFSMeta<MultipartFile> {
	
	private MultipartFileMeta multipartFileSource;

	public FastDFSMultipartFileMeta(MultipartFile source) throws IOException {
		super(source);
	}

	@Override
	protected void handle() throws IOException {
		if (this.multipartFileSource == null)
			this.multipartFileSource = new MultipartFileMeta(getSource());
		
		this.originalName = this.multipartFileSource.getOriginalName();
		this.extName = this.multipartFileSource.getExtName();
		this.in = this.multipartFileSource.getInputStream();
		this.bytes = this.multipartFileSource.getBytes();
	}

}
