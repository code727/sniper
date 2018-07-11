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
	private final T file;
	
	/** 是否延迟读取文件内容 */
	private final boolean delayedReading;
	
	/** 文件名(名.扩展) */
	private final String name;
	
	/** 文件名 */
	private final String mainName;
	
	/** 扩展名 */
	private final String extName;
	
	/** 输入流对象 */
	protected final InputStream input;
	
	/** 字节数组 */
	protected byte[] bytes;
	
	protected AbstaractFileSource(T file) throws IOException {
		this(file, false);
	}
	
	protected AbstaractFileSource(T file, boolean delayedReading) throws IOException {
		AssertUtils.assertNotNull(file, "File source body must not be null");
		FileItem item = initialize(file, delayedReading);
		AssertUtils.assertNotNull(item, "Initialize failed, file item must not be null");
		
		this.file = file;
		this.delayedReading = delayedReading;
		this.name = item.name;
		this.mainName = item.mainName;
		this.extName = item.extName;
		this.input = item.input;
		this.bytes = item.bytes;
	}
	
	/**
	 * 根据输入流对象创建相关的内容字节数组：</P>
	 * 1.如果delayedReading参数为true，则创建时不及时读取输入流的内容</P>
	 * 2.如果delayedReading参数为false，则创建时及时读取输入流的内容。
	 * @param input
	 * @param delayedReading
	 * @return 
	 * @throws IOException
	 */
	protected byte[] createBytes(T file, InputStream input, boolean delayedReading) throws IOException {
		byte[] bytes = new byte[input.available()];
		if (!delayedReading) {
			input.read(bytes, 0, bytes.length);
		}
		
		return bytes;
	}
	
	/**
	 * 重写父类方法，获取文件源的字节数组：</P>
	 * 1.如果delayedReading为true，则获取之前要先将文件内容读取到字节数组中；</P>
	 * 2.如果delayedReading为false，则说明在创建文件资源时已经将文件内容读取到字节数组中，直接返回即可。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 * @throws IOException
	 */
	@Override
	public byte[] getBytes() throws IOException {
		if (this.delayedReading) {
			synchronized (this) {
				this.bytes = new byte[this.input.available()];
				this.input.read(this.bytes, 0, this.bytes.length);
			}
		}
		return this.bytes;
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
	public InputStream getInputStream() throws IOException {
		return this.input;
	} 
	
	public boolean isDelayedReading() {
		return delayedReading;
	}
	
	/**
	 * 根据文件体初始化后返回创建完成的文件项
	 * @param file
	 * @param delayedReading
	 * @return
	 * @throws IOException
	 */
	protected abstract FileItem initialize(T file, boolean delayedReading) throws IOException;
	
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
		private final InputStream input;
		
		/** 字节数组 */
		private final byte[] bytes;
		
		public FileItem(String name, String mainName, String extName, InputStream input, byte[] bytes) {
			this.name = name;
			this.mainName = mainName;
			this.extName = extName;
			this.input = input;
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

		public InputStream getInputStream() {
			return input;
		}
	}

}
