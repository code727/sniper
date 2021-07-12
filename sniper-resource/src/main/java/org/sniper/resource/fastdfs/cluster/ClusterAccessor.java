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
 * Create Date : 2015-10-30
 */

package org.sniper.resource.fastdfs.cluster;

import org.sniper.resource.fastdfs.node.Storage;
import org.sniper.resource.fastdfs.node.Tracker;

/**
 * 集群访问接口
 * @author  Daniele
 * @version 1.0
 */
public interface ClusterAccessor {
	
	/**
	 * 根据名称获取集群族内的Tracker
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public Tracker getTracker(String name);
	
	/**
	 * 根据路径前缀获取StorageGroup
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public StorageGroup getStorageGroup(String pathPrefix);
	
	/**
	 * 根据路径前缀获取StorageGroup内指定索引位的Storage
	 * @author Daniele 
	 * @param pathPrefix
	 * @param index
	 * @return
	 */
	public Storage getStorage(String pathPrefix, int index);
	
	/**
	 * 根据路径前缀获取StorageGroup内第一个Storage
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public Storage getFristStorage(String pathPrefix);
	
	/**
	 * 根据路径前缀获取StorageGroup内最后一个Storage
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public Storage getLastStorage(String pathPrefix);
	
	/**
	 * 根据路径前缀获取StorageGroup内随机的一个Storage
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public Storage randomStorage(String pathPrefix);
	
	/**
	 * 获取整个Tracker族提供的内网访问URL地址
	 * @author Daniele 
	 * @return
	 */
	public String getTrackerClusterIntranetAccessURL();
	
	/**
	 * 获取整个Tracker族提供的外网访问URL地址
	 * @author Daniele 
	 * @return
	 */
	public String getTrackerClusterInternetAccessURL();
	
	/**
	 * 根据路径前缀获取StorageGroup提供的内网访问URL地址
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public String getStorageGroupIntranetAccessURL(String pathPrefix);
	
	/**
	 * 根据路径前缀获取StorageGroup提供的外网访问URL地址
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public String getStorageGroupInternetAccessURL(String pathPrefix);
	
	/**
	 * 根据名称获取Tracker节点提供的内网访问URL地址
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public String getTrackerIntranetAccessURL(String name);
	
	/**
	 * 根据路径前缀获取StorageGroup内指定索引位Storage节点提供的内网访问URL地址
	 * @author Daniele 
	 * @param pathPrefix
	 * @param index
	 * @return
	 */
	public String getStorageIntranetAccessURL(String pathPrefix, int index);
	
	/**
	 * 根据路径前缀获取StorageGroup内第一个Storage节点提供的内网访问URL地址
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public String getFristStorageIntranetAccessURL(String pathPrefix);
	
	/**
	 * 根据路径前缀获取StorageGroup内最后一个Storage节点提供的内网访问URL地址
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public String getLastStorageIntranetAccessURL(String pathPrefix);
	
	/**
	 * 根据路径前缀获取StorageGroup内随机的一个Storage节点提供的内网访问URL地址
	 * @author Daniele 
	 * @param pathPrefix
	 * @return
	 */
	public String randomStorageIntranetAccessURL(String pathPrefix);
	
}
