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
 * Create Date : 2015-11-3
 */

package org.workin.fastdfs.factory.connection;

import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

/**
 * @description 连接工厂接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ConnectionFactory {
	
	/**
	 * @description 获取TrackerServer实例对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 * @throws Exception
	 */
	public TrackerServer getTrackerServer() throws Exception;
	
	/**
	 * @description 获取指定索引位的TrackerServer实例对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public TrackerServer getTrackerServer(int index) throws Exception;
	
	/**
	 * @description 根据TrackerServer获取StorageServer实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param trackerServer
	 * @return
	 * @throws Exception
	 */
	public StorageServer getStorageServer(TrackerServer trackerServer) throws Exception;
	
	/**
	 * @description 根据TrackerServer获取指定组的StorageServer实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param trackerServer
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public StorageServer getStorageServer(TrackerServer trackerServer, String groupName) throws Exception;
		
	/**
	 * @description 释放TrackerServer和StorageServer对象的连接
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws Exception
	 */
	public void release(TrackerServer trackerServer, StorageServer storageServer) throws Exception;

}
