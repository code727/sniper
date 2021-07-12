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

/**
 * 文件资源接口
 * @author  Daniele
 * @version 1.0
 */
public interface FileSource<T> {
	
	/**
	 * 获取文件体
	 * @author Daniele 
	 * @return
	 */
	public T getFile();
	
	/**
	 * 获取文件源名称
	 * @author Daniele 
	 * @return
	 */
	public String getName();
	
	/**
	 * 获取文件源主名
	 * @author Daniele 
	 * @return
	 */
	public String getMainName();
	
	/**
	 * 获取文件源扩展名
	 * @author Daniele 
	 * @return
	 */
	public String getExtName();
	
	/**
	 * 获取文件源的输入流对象
	 * @author Daniele 
	 * @return
	 */
	public InputStream getInputStream() throws IOException;
	
	/**
	 * 获取文件源的字节数组
	 * @author Daniele 
	 * @return
	 */
	public byte[] getBytes() throws IOException;
	
	/**
	 * 关闭
	 * @author Daniele 
	 * @throws IOException
	 */
	public void close() throws IOException;
	
}
