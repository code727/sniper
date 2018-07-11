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

package org.sniper.resource.fastdfs.support;

/**
 * FastDFS操作接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface FastOperations extends FastUploader, FastDownloader, FastDeleter {
	
	/**
	 * 执行回调操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public <T> T execute(FastCallback<T> action) throws Exception;
	
	/**
	 * 在指定组上执行回调操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public <T> T execute(String groupName, FastCallback<T> action) throws Exception;
	
	/**
	 * 在指定组上执行回调操作，并指定完成操作后是否自动释放连接
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param groupName
	 * @param action
	 * @param autoRelease
	 * @return
	 * @throws Exception
	 */
	public <T> T execute(String groupName, FastCallback<T> action, boolean autoRelease) throws Exception;
	
}
