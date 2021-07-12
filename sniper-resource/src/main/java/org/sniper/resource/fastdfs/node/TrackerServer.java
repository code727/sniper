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

package org.sniper.resource.fastdfs.node;

import java.text.MessageFormat;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.NetUtils;
import org.sniper.spring.beans.CheckableInitializingBeanAdapter;

/**
 * TrackerServer实现类
 * @author  Daniele
 * @version 1.0
 */
public class TrackerServer extends CheckableInitializingBeanAdapter implements Tracker {
	
	/** TrackerServer节点的内网服务地址 */
	private String host;
	
	/** TrackerServer节点的内网服务端口 */
	private int port = DEFAULT_PORT;
	
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
		AssertUtils.assertNotBlank(this.host, "Tracker server property 'host' must not be null or blank");
		AssertUtils.assertTrue(NetUtils.isValidPort(this.port), MessageFormat.format(
				"Tracker server property 'port' is {0},valid range [{1}-{2}]", this.port, NetUtils.MIN_PORT, NetUtils.MAX_PORT));
	}

}
