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

package org.sniper.resource.fastdfs.factory;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.StringUtils;
import org.sniper.resource.fastdfs.cluster.Cluster;
import org.sniper.resource.fastdfs.cluster.TrackerCluster;
import org.sniper.resource.fastdfs.node.Tracker;
import org.sniper.spring.beans.CheckableInitializingBean;

/**
 * 默认的TrackerGroup实例工厂实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultTrackerGroupFactory extends CheckableInitializingBean implements TrackerGroupFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultTrackerGroupFactory.class);
	
	/** FastDFS集群生态圈对象 */
	private Cluster cluster;
	
	/** TrackerGroup实例 */
	private TrackerGroup trackerGroup;
	
	/** TrackerGroup成员注册策略 */
	private int memberStrategy;
	
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public int getMemberStrategy() {
		return memberStrategy;
	}

	public void setMemberStrategy(int memberStrategy) {
		this.memberStrategy = memberStrategy;
	}
	
	@Override
	protected void checkProperties() {
		if (this.cluster == null)
			new IllegalArgumentException("DefaultTrackerGroupFactory property 'cluster' must not be null.");
	}

	/**
	 * 初始化全局配置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void init() throws Exception {
		logger.info("Starting init FastDFS client global configuration");
		TrackerCluster trackerCluster = this.cluster.getTrackerCluster();
		ClientGlobal.g_connect_timeout = trackerCluster.getConnectTimeout();
		ClientGlobal.g_network_timeout = trackerCluster.getNetworkTimeout();
		ClientGlobal.g_charset = trackerCluster.getCharset();
		ClientGlobal.g_tracker_http_port = this.cluster.getAccessPort();
		
		boolean stealToken = trackerCluster.isHttpAntiStealToken();
		ClientGlobal.g_anti_steal_token = stealToken;
		if (stealToken) 
			ClientGlobal.g_secret_key = StringUtils.safeString(trackerCluster.getHttpSecretKey());
		
		createTrackerGroup(); 
	}
	
	/** 
	 * 创建TrackerGroup实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>  
	 */
	private void createTrackerGroup() {
		InetSocketAddress[] trackerServerAddresses = null;
		switch (this.memberStrategy) {
			case 1:
				trackerServerAddresses = registerMembersByNode();
				break;
			default:
				trackerServerAddresses = registerMembersByCluster();
				break;
		}
		this.trackerGroup = new TrackerGroup(trackerServerAddresses);
		ClientGlobal.g_tracker_group = trackerGroup;
		logger.info("End init FastDFS client global configuration, and success create TrackerGroup instance.");
	}
	
	/**
	 * 将TrackerCluster作为Group成员来注册
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private InetSocketAddress[] registerMembersByCluster() {
		TrackerCluster trackerCluster = this.cluster.getTrackerCluster();
		InetSocketAddress[] trackerServerAddresses = new InetSocketAddress[1];
		trackerServerAddresses[0] = new InetSocketAddress(trackerCluster.getHost(), trackerCluster.getPort());
		return trackerServerAddresses;
	}

	/**
	 * 将TrackerCluster内的每个Tracker节点作为Group成员来注册
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private InetSocketAddress[] registerMembersByNode() {
		Map<String, Tracker> trackers = this.cluster.getTrackerCluster().getTrackers();
		Iterator<Entry<String, Tracker>> iterator = trackers.entrySet().iterator();
		
		InetSocketAddress[] trackerServerAddresses = new InetSocketAddress[trackers.size()];
		int i = 0;
		while (iterator.hasNext()) {
			Entry<String, Tracker> trackerEntry = iterator.next();
			Tracker tracker = trackerEntry.getValue();
			trackerServerAddresses[i++] = new InetSocketAddress(tracker.getHost(), tracker.getPort());
		}
		return trackerServerAddresses;
	}

	@Override
	public TrackerGroup getTrackerGroup() {
		return this.trackerGroup;
	}
	
}
