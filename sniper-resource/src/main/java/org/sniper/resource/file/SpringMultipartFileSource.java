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

package org.sniper.resource.file;

import java.io.IOException;
import java.io.InputStream;

import org.sniper.commons.util.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Spring MultipartFile资源实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SpringMultipartFileSource extends AbstaractFileSource<MultipartFile> {

	public SpringMultipartFileSource(MultipartFile source) throws IOException {
		super(source);
	}
	
	@Override
	protected FileItem initialize(MultipartFile file) throws IOException {
		String name = file.getOriginalFilename();
		String mainName = FileUtils.getMainName(name);
		String extName = FileUtils.getExtensionName(name);
		InputStream in = file.getInputStream();
		byte[] bytes = new byte[in.available()];
//		in.read(bytes, 0, bytes.length);
		
		return new FileItem(name, mainName, extName, in, bytes);
	}

}
