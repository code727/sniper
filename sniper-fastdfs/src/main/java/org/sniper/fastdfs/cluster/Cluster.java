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
 * Create Date : 2015-10-29
 */

package org.sniper.fastdfs.cluster;

import java.util.Map;

/**
 * FastDFS集群族接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Cluster extends ClusterAccessor {
	
	/**
	 * 设置Tracker和Storage集群族统一提供的服务协议
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param protocol
	 */
	public void setProtocol(String protocol);
	
	/**
	 * 获取Tracker和Storage集群族统一提供的服务协议
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getProtocol();
	
	/**
	 * 设置Tracker和Storage集群族统一提供的访问服务端口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param accessPort
	 */
	public void setAccessPort(int accessPort);
	
	/**
	 * 获取Tracker和Storage集群族统一提供的访问服务端口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getAccessPort();
	
	/**
	 * 设置Tracker集群族
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param trackerCluster
	 */
	public void setTrackerCluster(TrackerCluster trackerCluster);
	
	/**
	 * 获取Tracker集群族
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public TrackerCluster getTrackerCluster();
	
	/**
	 * 设置StorageGroup映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param storageGroups：StorageGroup映射集，其中K表示请求路径前缀，V表示StorageGroup实例
	 */
	public void setStorageGroups(Map<String, StorageGroup> storageGroups);
	
	/**
	 * 获取StorageGroup映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, StorageGroup> getStorageGroups();
	
}
