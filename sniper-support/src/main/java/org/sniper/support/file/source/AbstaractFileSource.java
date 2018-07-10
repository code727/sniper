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

import java.io.IOException;
import java.io.InputStream;

import org.sniper.commons.util.AssertUtils;


/**
 * 文件资源抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstaractFileSource<T> implements FileSource<T> {
	
	/** 文件体*/
	private T file;
	
	/** 文件名(名.扩展) */
	private final String name;
	
	/** 文件名 */
	private final String mainName;
	
	/** 扩展名 */
	private final String extName;
	
	/** 输入流对象 */
	private final InputStream in;
	
	/** 字节数组 */
	private final byte[] bytes;
	
	public AbstaractFileSource(T file) throws IOException {
		AssertUtils.assertNotNull(file, "File source body must not be null");
		FileItem item = initialize(file);
		AssertUtils.assertNotNull(item, "Initialize failed, file item must not be null");
		
		this.file = file;
		this.name = item.name;
		this.mainName = item.mainName;
		this.extName = item.extName;
		this.in = item.in;
		this.bytes = item.bytes;
	}

	@Override
	public T getFile() {
		return this.file;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getMainName() {
		return this.mainName;
	}
	
	@Override
	public String getExtName() {
		return this.extName;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return this.bytes;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.in;
	} 
	
	/**
	 * 根据文件体初始化后返回创建完成的文件项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	protected abstract FileItem initialize(T file) throws IOException;
	
	/**
	 * 文件项内部实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	protected class FileItem {
		
		/** 文件名(名.扩展) */
		private final String name;
		
		/** 文件名 */
		private final String mainName;
		
		/** 扩展名 */
		private final String extName;
		
		/** 输入流对象 */
		private final InputStream in;
		
		/** 字节数组 */
		private final byte[] bytes;
		
		public FileItem(String name, String mainName, String extName, InputStream in, byte[] bytes) {
			this.name = name;
			this.mainName = mainName;
			this.extName = extName;
			this.in = in;
			this.bytes = bytes;
		}

		public String getName() {
			return name;
		}

		public String getMainName() {
			return mainName;
		}

		public String getExtName() {
			return extName;
		}

		public byte[] getBytes() {
			return bytes;
		}

		public InputStream getIn() {
			return in;
		}
	}

}
