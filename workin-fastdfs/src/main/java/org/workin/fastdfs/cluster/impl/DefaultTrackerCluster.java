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
 * Create Date : 2015-11-2
 */

package org.workin.fastdfs.cluster.impl;

import java.util.Map;

import org.workin.commons.util.MessageUtils;
import org.workin.commons.util.NetUtils;
import org.workin.commons.util.StringUtils;
import org.workin.fastdfs.cluster.TrackerCluster;
import org.workin.fastdfs.node.Tracker;
import org.workin.spring.beans.CheckableInitializingBean;

/**
 * @description 默认的Tracker集群族实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultTrackerCluster extends CheckableInitializingBean implements TrackerCluster {
	
	/** Tracker节点映射集 */
	private Map<String, Tracker> trackers;
	
	/** 连接超时时间 */
	private int connectTimeout = NetUtils.DEFAULT_TIMEOUT_SEC;
	
	/** 网络超时时间 */
	private int networkTimeout = NetUtils.DEFAULT_TIMEOUT_SEC;
	
	/** 字符集编码 */
	private String charset = MessageUtils.UTF8_ENCODING;
	
	/** 是否设置HTTP安全秘钥 */
	private boolean httpAntiStealToken;
	
	/** HTTP安全秘钥  */
	private String httpSecretKey;
	
	/** Tracker集群族统一提供的外网服务地址 */
	private String internetHost;
	
	/** Tracker集群族统一提供的外网服务端口 */
	private int internetPort = Tracker.DEFAULT_PORT;
	
	/** Tracker集群族统一提供的内网服务地址 */
	private String host;
	
	/** Tracker集群族统一提供的内网服务端口 */
	private int port = Tracker.DEFAULT_PORT;
	
	@Override
	public void setTrackers(Map<String, Tracker> trackers) {
		this.trackers = trackers;
	}

	@Override
	public Map<String, Tracker> getTrackers() {
		return this.trackers;
	}
	
	@Override
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	@Override
	public int getConnectTimeout() {
		return this.connectTimeout;
	}

	@Override
	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	@Override
	public int getNetworkTimeout() {
		return this.networkTimeout;
	}
	
	@Override
	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public String getCharset() {
		return this.charset;
	}
	
	@Override
	public void setHttpAntiStealToken(boolean httpAntiStealToken) {
		this.httpAntiStealToken = httpAntiStealToken;
	}

	@Override
	public boolean isHttpAntiStealToken() {
		return this.httpAntiStealToken;
	}
	
	@Override
	public void setHttpSecretKey(String httpSecretKey) {
		this.httpSecretKey = httpSecretKey;
	}

	@Override
	public String getHttpSecretKey() {
		return this.httpSecretKey;
	}

	@Override
	public void setInternetHost(String internetHost) {
		this.internetHost = internetHost;
	}

	@Override
	public String getInternetHost() {
		return this.internetHost;
	}

	@Override
	public void setInternetPort(int internetPort) {
		this.internetPort = internetPort;
	}

	@Override
	public int getInternetPort() {
		return this.internetPort;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public int getPort() {
		return this.port;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		/* 当内网地址未注册时，其值与外网地址一致 */
		if (StringUtils.isBlank(this.host))
			this.host = this.internetHost;
	}

	@Override
	protected void checkProperties() throws IllegalArgumentException {
		if (this.connectTimeout <= 0)
			throw new IllegalArgumentException("Default tracker cluster property 'connectTimeout' is "
					+ this.connectTimeout + " second,but must greater than 0.");
		
		if (this.networkTimeout <= 0)
			throw new IllegalArgumentException("Default tracker cluster property 'networkTimeout' is "
					+ this.networkTimeout + " second,but must greater than 0.");
		
		if (this.httpAntiStealToken && StringUtils.isEmpty(this.httpSecretKey))
			throw new IllegalArgumentException("Default tracker cluster property 'httpSecretKey' must not be null or empty "
					+ "when property 'httpAntiStealToken' is true.");
		
		/* 要求Tracker集群族必须要有可访问的外网地址 */
		if (StringUtils.isBlank(this.internetHost))
			throw new IllegalArgumentException("Default tracker cluster property 'internetHost' must not be null or blank.");
		
		if (!NetUtils.isValidPort(this.internetPort))
			throw new IllegalArgumentException("Default tracker cluster property 'internetPort' is "
					+ this.internetPort + ",valid range [" + NetUtils.MIN_PORT + "-"
					+ NetUtils.MAX_PORT + "].");
		
		if (!NetUtils.isValidPort(this.port))
			throw new IllegalArgumentException("Tracker cluster property 'port' is "
					+ this.port + ",valid range [" + NetUtils.MIN_PORT + "-"
					+ NetUtils.MAX_PORT + "].");
	}

}
