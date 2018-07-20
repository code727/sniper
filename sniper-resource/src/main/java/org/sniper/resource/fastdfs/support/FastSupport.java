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
 * Create Date : 2015-11-9
 */

package org.sniper.resource.fastdfs.support;

import org.sniper.resource.fastdfs.accessor.Accessor;
import org.sniper.resource.fastdfs.accessor.DefaultAccessor;
import org.sniper.resource.fastdfs.cluster.Cluster;
import org.sniper.resource.fastdfs.factory.connection.ConnectionFactory;
import org.sniper.spring.beans.CheckableInitializingBean;

/**
 * FastDFS支持服务类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class FastSupport extends CheckableInitializingBean {
		
	/** FastDFS集群族对象 */
	protected Cluster cluster;
	
	/** 连接工厂 */
	protected ConnectionFactory connectionFactory;
		
	/** FastDFS访问器 */
	protected Accessor accessor;
		
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
		
	public Accessor getAccessor() {
		return accessor;
	}

	public void setAccessor(Accessor accessor) {
		this.accessor = accessor;
	}
	
	@Override
	protected void checkProperties() {
		if (this.cluster == null)
			throw new IllegalArgumentException("Property 'cluster' must not be null");
		
		if (this.connectionFactory == null)
			throw new IllegalArgumentException("Property 'connectionFactory' must not be null");
	}
	
	@Override
	protected void init() throws Exception {
		if (this.accessor == null)
			this.accessor = new DefaultAccessor();
	}
	
}
