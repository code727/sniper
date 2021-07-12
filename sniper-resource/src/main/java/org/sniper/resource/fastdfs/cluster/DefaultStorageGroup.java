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

package org.sniper.resource.fastdfs.cluster;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.NetUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.resource.fastdfs.node.Storage;
import org.sniper.spring.beans.CheckableInitializingBean;

/**
 * 默认的StorageGroup集群族实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefaultStorageGroup extends CheckableInitializingBean implements StorageGroup {
	
	/** StorageGroup内的所有Storage节点 */
	private List<Storage> storages;
	
	/** StorageGroup统一提供的外网服务地址 */
	private String internetHost = NetUtils.LOOPBACK_ADDRESS;
	
	/** StorageGroup统一提供的外网服务端口 */
	private int internetPort = Storage.DEFAULT_PORT;
	
	/** StorageGroup统一提供的内网服务地址 */
	private String host = NetUtils.LOOPBACK_ADDRESS;
	
	/** StorageGroup统一提供的内网服务端口 */
	private int port = Storage.DEFAULT_PORT;
	
	@Override
	public void setStorages(List<Storage> storages) {
		this.storages = storages;
	}

	@Override
	public List<Storage> getStorages() {
		return this.storages;
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
	protected void checkProperties() {
		if (CollectionUtils.isEmpty(this.storages))
			throw new IllegalArgumentException("Default storage group property 'storages' must not be null or empty.");
	
		/* 只检查端口的有效性，作为FastDFS的集群生态圈来说，
		 * 为每一个StorageGroup提供统一的服务入口不是必须要求的，因此这里不验证地址是否已声明  */
		if (!NetUtils.isValidPort(this.internetPort))
			throw new IllegalArgumentException("Default storage group property 'internetPort' is "
					+ this.internetPort + ",valid range [" + NetUtils.MIN_PORT + "-"
					+ NetUtils.MAX_PORT + "].");
		
		if (!NetUtils.isValidPort(this.port))
			throw new IllegalArgumentException("Default storage group property 'port' is "
					+ this.port + ",valid range [" + NetUtils.MIN_PORT + "-"
					+ NetUtils.MAX_PORT + "].");
	}
	
	@Override
	protected void init() throws Exception {
		if (StringUtils.isBlank(this.internetHost))
			this.internetHost = NetUtils.LOOPBACK_ADDRESS;
		
		if (StringUtils.isBlank(this.host))
			this.host = this.internetHost;
	}

}
