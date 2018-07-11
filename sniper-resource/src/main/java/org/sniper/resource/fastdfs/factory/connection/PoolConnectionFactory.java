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
 * Create Date : 2015-11-4
 */

package org.sniper.resource.fastdfs.factory.connection;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.context.ThreadLocalHolder;
import org.sniper.resource.fastdfs.factory.DefaultPoolableStorageServerFactory;
import org.sniper.resource.fastdfs.factory.DefaultPoolableTrackerServerFactory;
import org.sniper.resource.fastdfs.factory.PoolableStorageServerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 池化的连接工厂实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PoolConnectionFactory extends AbstractConnectionFactory implements InitializingBean {
	
	/** TrackerServer连接池 */
	private GenericObjectPool<TrackerServer> trackerServerPool;
	
	/** StorageServer数组连接池 */
	private GenericObjectPool<StorageServer> storageServerPool;
	
	public GenericObjectPool<TrackerServer> getTrackerServerPool() {
		return this.trackerServerPool;
	}

	public void setTrackerServerPool(GenericObjectPool<TrackerServer> trackerServerPool) {
		this.trackerServerPool = trackerServerPool;
	}

	public GenericObjectPool<StorageServer> getStorageServerPool() {
		return storageServerPool;
	}

	public void setStorageServerPool(GenericObjectPool<StorageServer> storageServerPool) {
		this.storageServerPool = storageServerPool;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.trackerServerPool == null)
			this.trackerServerPool = new GenericObjectPool<TrackerServer>(new DefaultPoolableTrackerServerFactory());
		
		if (this.storageServerPool == null) {
			PoolableStorageServerFactory factory = new DefaultPoolableStorageServerFactory();
			factory.setTrackerClient(super.getTrackerClient());
			this.storageServerPool = new GenericObjectPool<StorageServer>(factory);
		} else {
			Object fieldValue = ReflectionUtils.getFieldValue(this.storageServerPool, "_factory");
			if (fieldValue instanceof PoolableStorageServerFactory) {
				PoolableStorageServerFactory factory = (PoolableStorageServerFactory) fieldValue;
				if (factory.getTrackerClient() == null) 
					factory.setTrackerClient(super.getTrackerClient());
			}
		}
	}

	@Override
	public TrackerServer getTrackerServer() throws Exception {
		return getTrackerServer(ClientGlobal.g_tracker_group.tracker_server_index);
	}

	@Override
	public TrackerServer getTrackerServer(int index) throws Exception {
		ThreadLocalHolder.setAttribute("CURRENT_TRACKERSERVER_INDEX", index);
		return this.trackerServerPool.borrowObject();
	}

	@Override
	public StorageServer getStorageServer(TrackerServer trackerServer) throws Exception {
		return getStorageServer(trackerServer, null);
	}

	@Override
	public StorageServer getStorageServer(TrackerServer trackerServer, String groupName) throws Exception {
		ThreadLocalHolder.setAttribute("CURRENT_TRACKERSERVER", trackerServer);
		ThreadLocalHolder.setAttribute("CURRENT_STORAGE_GROUPNAME", StringUtils.safeString(groupName));
		return this.storageServerPool.borrowObject();
	}

	@Override
	public void release(TrackerServer trackerServer, StorageServer storageServer) throws Exception {
		ThreadLocalHolder.removeAttribute("CURRENT_TRACKERSERVER_INDEX");
		ThreadLocalHolder.removeAttribute("CURRENT_TRACKERSERVER");
		ThreadLocalHolder.removeAttribute("CURRENT_STORAGE_GROUPNAME");
		try {
			if (storageServer != null)
				this.storageServerPool.returnObject(storageServer);
		} finally {
			if (trackerServer != null)
				this.trackerServerPool.returnObject(trackerServer);
		}
	}
	
}
