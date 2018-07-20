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
import org.sniper.commons.util.IOUtils;

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
		FileEntry entry = initialize(file, delayedReading);
		AssertUtils.assertNotNull(entry, "Initialize failed, file entry must not be null");
		
		this.file = file;
		this.delayedReading = delayedReading;
		this.name = entry.name;
		this.mainName = entry.mainName;
		this.extName = entry.extName;
		this.input = entry.input;
		this.bytes = entry.bytes;
	}
	
	/**
	 * 根据文件体初始化后返回创建完成的文件项
	 * @param file
	 * @param delayedReading
	 * @return
	 * @throws IOException
	 */
	protected FileEntry initialize(T file, boolean delayedReading) throws IOException {
		 throw new IOException("initialize not supported");
	}
	
	/**
	 * 根据输入流对象创建相关的内容字节数组：</P>
	 * 1.如果delayedReading参数为true，则创建时不及时读取输入流的内容</P>
	 * 2.如果delayedReading参数为false，则创建时及时读取输入流的内容。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param file
	 * @param input
	 * @param delayedReading
	 * @return
	 * @throws IOException
	 */
	protected byte[] createBytes(T file, InputStream input, boolean delayedReading) throws IOException {
		/* 如果需要延迟读取，则不读取任何内容，因此直接返回null */
		if (delayedReading) {
			return null;
		}
			
		byte[] bytes = new byte[input.available()];
		input.read(bytes, 0, bytes.length);
		
		return bytes;
	}
	
	/**
	 * 获取文件源的字节数组：</P>
	 * 1.如果delayedReading为true，则获取之前要先将文件内容读取到字节数组中；</P>
	 * 2.如果delayedReading为false，则说明在创建文件资源时已经将文件内容读取到字节数组中，直接返回即可。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 * @throws IOException
	 */
	@Override
	public byte[] getBytes() throws IOException {
		if (this.delayedReading && this.bytes == null) {
			synchronized (this) {
				/* 双重检测，如果文件源的字节数组为空，
				 * 表示从未读取过，因此需要在此读取一次后再返回结果 */
				if (this.bytes == null) {
					this.bytes = new byte[this.input.available()];
					this.input.read(this.bytes, 0, this.bytes.length);
				}
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
	
	@Override
	public void close() throws IOException {
		IOUtils.close(this.input);
	}
	
	public boolean isDelayedReading() {
		return delayedReading;
	}
	
	/**
	 * 文件条目实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	protected class FileEntry {
		
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
		
		public FileEntry(String name, String mainName, String extName, InputStream input, byte[] bytes) {
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
