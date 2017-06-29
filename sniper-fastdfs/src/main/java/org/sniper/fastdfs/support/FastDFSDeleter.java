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

package org.sniper.fastdfs.support;

import java.util.Set;


/**
 * FastDFS文件删除器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FastDFSDeleter {
	
	/**
	 * 删除指定路径的资源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public int delete(String path) throws Exception;
	
	/**
	 * 批量删除指定路径的资源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathSet
	 * @throws Exception
	 */
	public void bathDelete(Set<String> pathSet) throws Exception;
	
}
