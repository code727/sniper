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
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.DateUtils;
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
	
	/** Tracker节点注册策略 */
	private int registerStrategy;
	
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public int getRegisterStrategy() {
		return registerStrategy;
	}

	public void setRegisterStrategy(int registerStrategy) {
		this.registerStrategy = registerStrategy;
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
		Date start = new Date();
		
		TrackerCluster trackerCluster = this.cluster.getTrackerCluster();
		
		boolean stealToken = trackerCluster.isHttpAntiStealToken();
		ClientGlobal.g_anti_steal_token = stealToken;
		if (stealToken) {
			String httpSecretKey = trackerCluster.getHttpSecretKey();
			AssertUtils.assertNotBlank(httpSecretKey, "Property 'httpSecretKey' can not be blank whern 'httpAntiStealToken' is true");
			ClientGlobal.g_secret_key = httpSecretKey;
		}
		
		ClientGlobal.g_connect_timeout = trackerCluster.getConnectTimeout();
		ClientGlobal.g_network_timeout = trackerCluster.getNetworkTimeout();
		ClientGlobal.g_charset = trackerCluster.getCharset();
		ClientGlobal.g_tracker_http_port = this.cluster.getAccessPort();
		
		createTrackerGroup(); 
		logger.info("Success init FastDFS client global configuration on {}ms", DateUtils.getIntervalMillis(start, new Date()));
	}
	
	/** 
	 * 创建TrackerGroup实例
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>  
	 */
	private void createTrackerGroup() {
		InetSocketAddress[] trackerServerAddresses = null;
		switch (this.registerStrategy) {
			case 1:
				trackerServerAddresses = registerByNode();
				break;
			default:
				trackerServerAddresses = registerByCluster();
				break;
		}
		this.trackerGroup = new TrackerGroup(trackerServerAddresses);
		ClientGlobal.g_tracker_group = trackerGroup;
	}
	
	/**
	 * 将TrackerCluster内的每个Tracker节点作为Group成员来注册
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private InetSocketAddress[] registerByNode() {
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
	
	/**
	 * 将TrackerCluster这个逻辑集群族作为Group成员来注册，通过使用在Tracker高可用的环境中。</P>
	 * 例如：为保证Tracker的高可用，常用的做法是在多个Tracker节点之上再搭载一层中间件(Nginx+Keepalived/LVS等)，
	 * 负责整个集群环境下Tracker的负载均衡、监控和故障转移等。</P>
	 * 这类中间件的host和port对应的就是API中TrackerCluster对象中的同名属性。
	 * 因此在高可用的集群环境中，如果配置了中间件的host和port，推荐选择方式来注册TrackerGroup。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private InetSocketAddress[] registerByCluster() {
		TrackerCluster trackerCluster = this.cluster.getTrackerCluster();
		InetSocketAddress[] trackerServerAddresses = new InetSocketAddress[1];
		trackerServerAddresses[0] = new InetSocketAddress(trackerCluster.getHost(), trackerCluster.getPort());
		return trackerServerAddresses;
	}

	@Override
	public TrackerGroup getTrackerGroup() {
		return this.trackerGroup;
	}
	
}
