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

package org.workin.fastdfs.cluster.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NetUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.fastdfs.cluster.Cluster;
import org.workin.fastdfs.cluster.StorageGroup;
import org.workin.fastdfs.cluster.TrackerCluster;
import org.workin.fastdfs.node.Storage;
import org.workin.fastdfs.node.Tracker;
import org.workin.spring.beans.CheckableInitializingBean;

/**
 * @description 默认的FastDFS集群族实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultCluster extends CheckableInitializingBean implements Cluster {
	
	/** 整个FastDFS生态圈提供的访问服务协议 */
	private String protocol = "http";
	
	/** 整个FastDFS生态圈提供的访问服务端口 */
	private int accessPort = NetUtils.DEFAULT_HTTP_PORT;
	
	/** Tracker集群族 */
	private TrackerCluster trackerCluster;
	
	/** Storage组映射集 */
	private Map<String, StorageGroup> storageGroups;
	
	/** Tracker集群族可访问的内网URL */
	private String trackerClusterIntranetAccessURL;
	
	/** Tracker集群族提供的外网访问URL */
	private String trackerClusterInternetAccessURL;
	
	/** Tracker节点提供的内网访问URL映射集 */
	private Map<String, String> trackerIntranetAccessURLMap;
	
	/** StorageGroup集群族提供的内网访问URL映射集 */
	private Map<String, String> storageGroupIntranetAccessURLMap;
	
	/** StorageGroup集群族提供的外网访问URL映射集 */
	private Map<String, String> storageGroupInternetAccessURLMap;
	
	/** Storage节点提供的内网访问URL映射集 */
	private Map<String, Map<Integer, String>> storageInternetAccessURLMap;
	
	@Override
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public String getProtocol() {
		return this.protocol;
	}
	
	@Override
	public void setAccessPort(int accessPort) {
		this.accessPort = accessPort;
	}

	@Override
	public int getAccessPort() {
		return this.accessPort;
	}
	
	@Override
	public void setTrackerCluster(TrackerCluster trackerCluster) {
		this.trackerCluster = trackerCluster;
	}

	@Override
	public TrackerCluster getTrackerCluster() {
		return this.trackerCluster;
	}
	
	@Override
	public void setStorageGroups(Map<String, StorageGroup> storageGroups) {
		this.storageGroups = storageGroups;
	}

	@Override
	public Map<String, StorageGroup> getStorageGroups() {
		return this.storageGroups;
	}

	@Override
	public Tracker getTracker(String name) {
		return this.trackerCluster.getTrackers().get(name);
	}

	@Override
	public StorageGroup getStorageGroup(String pathPrefix) {
		return this.storageGroups.get(pathPrefix);
	}

	@Override
	public Storage getStorage(String pathPrefix, int index) {
		StorageGroup storageGroup = this.getStorageGroup(pathPrefix);
		return storageGroup != null ? CollectionUtils.get(storageGroup.getStorages(), index) : null;
	}

	@Override
	public Storage getFristStorage(String pathPrefix) {
		return this.getStorage(pathPrefix, 0);
	}

	@Override
	public Storage getLastStorage(String pathPrefix) {
		StorageGroup storageGroup = this.getStorageGroup(pathPrefix);
		Storage storage = null;
		if (storageGroup != null) {
			List<Storage> storages = storageGroup.getStorages();
			storage = CollectionUtils.get(storages, CollectionUtils.size(storages) - 1);
		}
		return storage;
	}

	@Override
	public Storage randomStorage(String pathPrefix) {
		StorageGroup storageGroup = this.getStorageGroup(pathPrefix);
		Storage storage = null;
		if (storageGroup != null) {
			List<Storage> storages = storageGroup.getStorages();
			storage = CollectionUtils.get(storages, NumberUtils.randomIn(CollectionUtils.size(storages)));
		}
		return storage;
	}

	@Override
	public String getTrackerClusterIntranetAccessURL() {
		return this.trackerClusterIntranetAccessURL;
	}

	@Override
	public String getTrackerClusterInternetAccessURL() {
		return this.trackerClusterInternetAccessURL;
	}

	@Override
	public String getStorageGroupIntranetAccessURL(String pathPrefix) {
		return MapUtils.isNotEmpty(this.storageGroupIntranetAccessURLMap) ? 
				this.storageGroupIntranetAccessURLMap.get(pathPrefix) : null;
	}

	@Override
	public String getStorageGroupInternetAccessURL(String pathPrefix) {
		return MapUtils.isNotEmpty(this.storageGroupInternetAccessURLMap) ? 
				this.storageGroupInternetAccessURLMap.get(pathPrefix) : null;
	}

	@Override
	public String getTrackerIntranetAccessURL(String name) {
		return this.trackerIntranetAccessURLMap.get(name);
	}

	@Override
	public String getStorageIntranetAccessURL(String pathPrefix, int index) {
		Map<Integer, String> storageGroupMap = this.storageInternetAccessURLMap.get(pathPrefix);
		return MapUtils.isNotEmpty(storageGroupMap) ? storageGroupMap.get(index) : null;
	}

	@Override
	public String getFristStorageIntranetAccessURL(String pathPrefix) {
		return getStorageIntranetAccessURL(pathPrefix, 0);
	}

	@Override
	public String getLastStorageIntranetAccessURL(String pathPrefix) {
		Map<Integer, String> storageGroupMap = this.storageInternetAccessURLMap.get(pathPrefix);
		return MapUtils.isNotEmpty(storageGroupMap) ? storageGroupMap.get(storageGroupMap.size() - 1) : null;
	}

	@Override
	public String randomStorageIntranetAccessURL(String pathPrefix) {
		Map<Integer, String> storageGroupMap = this.storageInternetAccessURLMap.get(pathPrefix);
		return MapUtils.isNotEmpty(storageGroupMap) ? 
				storageGroupMap.get(NumberUtils.randomIn(storageGroupMap.size())) : null;
	}
	
	@Override
	protected void checkProperties() {
		if (StringUtils.isBlank(this.protocol))
			new IllegalArgumentException("Default cluster property 'protocol' must not be null or blank.");
		
		if (!NetUtils.isValidPort(this.accessPort))
			throw new IllegalArgumentException("Default cluster property 'accessPort' is "
					+ this.accessPort + ",valid range [" + NetUtils.MIN_PORT + "-"
					+ NetUtils.MAX_PORT + "].");
		
		if (this.trackerCluster == null)
			new IllegalArgumentException("Default cluster property 'trackerCluster' must not be null.");
		
		if (MapUtils.isEmpty(this.storageGroups))
			new IllegalArgumentException("Default cluster property 'storageGroups' must not be null or empty.");
	}
	
	@Override
	protected void init() throws Exception {
		buildTrackerClusterIntranetAccessURL();
		buildTrackerClusterInternetAccessURL();
		buildTrackerIntranetAccessURLMap();
		
		buildStorageGroupAndNodeAccessURLMap();
	}
	
	/**
	 * @description 构建Tracker集群族可访问的内网URL 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private void buildTrackerClusterIntranetAccessURL() {
		this.trackerClusterIntranetAccessURL = NetUtils.toURL(this.protocol,
				this.trackerCluster.getHost(), this.accessPort);
	}
	
	/**
	 * @description 构建Tracker集群族可访问的外网URL 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private void buildTrackerClusterInternetAccessURL() {
		this.trackerClusterInternetAccessURL = NetUtils.toURL(this.protocol,
				this.trackerCluster.getInternetHost(), this.accessPort);
	}
	
	/**
	 * @description 构建Tracker节点可访问的内网URL映射集 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private void buildTrackerIntranetAccessURLMap() {
		this.trackerIntranetAccessURLMap = MapUtils.newHashMap();
		Map<String, Tracker> trackers = this.trackerCluster.getTrackers();
		Iterator<Entry<String, Tracker>> iterator = trackers.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Tracker> trackerEntry = iterator.next();
			Tracker tracker = trackerEntry.getValue();
			this.trackerIntranetAccessURLMap.put(trackerEntry.getKey(), 
					NetUtils.toURL(this.protocol, tracker.getHost(), this.accessPort));
		}
	}
	
	/**
	 * @description 构建StorageGroup集群组以及组内各节点可访问的URL映射集 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private void buildStorageGroupAndNodeAccessURLMap() {
		this.storageGroupIntranetAccessURLMap = MapUtils.newHashMap();
		this.storageGroupInternetAccessURLMap = MapUtils.newHashMap();
		this.storageInternetAccessURLMap = MapUtils.newHashMap();
		
		Iterator<Entry<String, StorageGroup>> iterator = getStorageGroups().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, StorageGroup> storageGroupEntry = iterator.next();
			StorageGroup storageGroup = storageGroupEntry.getValue();
			String pathPrefix = storageGroupEntry.getKey();
			
			/* 构建StorageGroup路径前缀与可访问的内外网URL的映射关系 */
			this.storageGroupIntranetAccessURLMap.put(pathPrefix, NetUtils.toURL(
					this.protocol, storageGroup.getHost(), this.accessPort));
			this.storageGroupInternetAccessURLMap.put(pathPrefix, NetUtils.toURL(
					this.protocol, storageGroup.getInternetHost(), this.accessPort));
				
			/* 构建StorageGroup路径前缀与组内可访问的内网URL的映射关系 */
			Map<Integer, String> storageIntranetURLMap= this.storageInternetAccessURLMap.get(storageGroupEntry.getKey());
			if (storageIntranetURLMap == null) 
				storageIntranetURLMap = MapUtils.newHashMap();
			
			List<Storage> storages = storageGroup.getStorages();
			for (int i = 0; i < storages.size(); i++) {
				Storage storage = storages.get(i);
				storageIntranetURLMap.put(i, NetUtils.toURL(this.protocol, storage.getHost(), this.accessPort));
			}
			this.storageInternetAccessURLMap.put(pathPrefix, storageIntranetURLMap);
		}
	}

}
