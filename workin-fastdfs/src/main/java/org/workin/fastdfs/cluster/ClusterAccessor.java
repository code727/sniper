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

package org.workin.fastdfs.cluster;

import org.workin.fastdfs.node.Storage;
import org.workin.fastdfs.node.Tracker;

/**
 * @description 集群访问接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ClusterAccessor {
	
	/**
	 * @description 根据名称获取集群族内的Tracker
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Tracker getTracker(String name);
	
	/**
	 * @description 根据路径前缀获取StorageGroup
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public StorageGroup getStorageGroup(String pathPrefix);
	
	/**
	 * @description 根据路径前缀获取StorageGroup内指定索引位的Storage
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @param index
	 * @return
	 */
	public Storage getStorage(String pathPrefix, int index);
	
	/**
	 * @description 根据路径前缀获取StorageGroup内第一个Storage
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public Storage getFristStorage(String pathPrefix);
	
	/**
	 * @description 根据路径前缀获取StorageGroup内最后一个Storage
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public Storage getLastStorage(String pathPrefix);
	
	/**
	 * @description 根据路径前缀获取StorageGroup内随机的一个Storage
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public Storage randomStorage(String pathPrefix);
	
	/**
	 * @description 获取整个Tracker族提供的内网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getTrackerClusterIntranetUrl();
	
	/**
	 * @description 获取整个Tracker族提供的外网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getTrackerClusterInternetUrl();
	
	/**
	 * @description 根据路径前缀获取StorageGroup提供的内网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public String getStorageGroupIntranetUrl(String pathPrefix);
	
	/**
	 * @description 根据路径前缀获取StorageGroup提供的外网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public String getStorageGroupInternetUrl(String pathPrefix);
	
	/**
	 * @description 根据名称获取Tracker节点提供的内网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public String getTrackerIntranetUrl(String name);
	
	/**
	 * @description 根据路径前缀获取StorageGroup内指定索引位Storage节点提供的内网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @param index
	 * @return
	 */
	public String getStorageIntranetUrl(String pathPrefix, int index);
	
	/**
	 * @description 根据路径前缀获取StorageGroup内第一个Storage节点提供的内网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public String getFristStorageIntranetUrl(String pathPrefix);
	
	/**
	 * @description 根据路径前缀获取StorageGroup内最后一个Storage节点提供的内网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public String getLastStorageIntranetUrl(String pathPrefix);
	
	/**
	 * @description 根据路径前缀获取StorageGroup内随机的一个Storage节点提供的内网URL地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pathPrefix
	 * @return
	 */
	public String randomStorageIntranetUrl(String pathPrefix);
	
}
