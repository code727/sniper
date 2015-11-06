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

package org.workin.support.file.meta;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description 文件源接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FileMeta<T> {
	
	/**
	 * @description 获取文件源对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public T getSource();
	
	/**
	 * @description 获取文件源原始名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getOriginalName();
	
	/**
	 * @description 获取文件源扩展名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getExtName();
	
	/**
	 * @description 获取文件源的字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public byte[] getBytes() throws IOException;
	
	/**
	 * @description 获取文件源的输入流对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public InputStream getInputStream() throws IOException;

}
