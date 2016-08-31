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
 * Create Date : 2015年11月5日
 */

package org.workin.web.spring.file;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.workin.commons.util.FileUtils;
import org.workin.support.file.meta.AbstaractFileMeta;

/**
 * MultipartFile源实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MultipartFileMeta extends AbstaractFileMeta<MultipartFile> {

	public MultipartFileMeta(MultipartFile source) throws IOException {
		super(source);
	}

	@Override
	protected void handle() throws IOException {
		MultipartFile source = getSource();
		this.name = source.getOriginalFilename();
		this.mainName = FileUtils.getMainName(this.name);
		this.extName = FileUtils.getExtensionName(this.name);
		this.in = source.getInputStream();
		this.bytes = new byte[this.in.available()];
		this.in.read(bytes, 0, bytes.length);
	}

}
