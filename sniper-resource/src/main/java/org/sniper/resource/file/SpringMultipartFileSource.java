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

	public SpringMultipartFileSource(MultipartFile file) throws IOException {
		super(file);
	}
	
	public SpringMultipartFileSource(MultipartFile file, boolean delayedReading) throws IOException {
		super(file, delayedReading);
	}
	
	@Override
	protected FileItem initialize(MultipartFile file, boolean delayedReading) throws IOException {
		String name = file.getOriginalFilename();
		String mainName = FileUtils.getMainName(name);
		String extName = FileUtils.getExtensionName(name);
		InputStream input = file.getInputStream();
		byte[] bytes = createBytes(file, input, delayedReading);
				
		return new FileItem(name, mainName, extName, input, bytes);
	}
	
	/**
	 * 重写父类方法，当创建内容字节数组时，如果需要及时读取内容(delayedReading=false)，
	 * 则不会像父类方法那样直接从input对象中读取，而是根据MultipartFile间接的从input对象中读取。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param input
	 * @param delayedReading
	 * @return
	 * @throws IOException
	 */
	@Override
	protected byte[] createBytes(MultipartFile file, InputStream input, boolean delayedReading) throws IOException {
		return delayedReading ? new byte[input.available()] : file.getBytes();
	}
	
	@Override
	public int read() throws IOException {
		return 0;
	}

}
