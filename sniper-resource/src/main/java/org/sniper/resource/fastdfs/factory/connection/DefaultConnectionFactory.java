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

package org.sniper.resource.fastdfs.factory.connection;

import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.sniper.commons.util.NumberUtils;

/**
 * 默认原生API实现的连接工厂
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultConnectionFactory extends AbstractConnectionFactory {
	
	protected DefaultConnectionFactory() {
		super();
	}
	
	protected DefaultConnectionFactory(TrackerGroup trackerGroup) {
		super(trackerGroup);
	}
	
	@Override
	public TrackerServer getTrackerServer() throws IOException {
		/*
		 * 不使用ClientGlobal.g_tracker_group.getConnection()的方法来返回，
		 * 因为此方法在源代码中的实现方式为加锁轮询tracker_servers，效率不高。
		 * 目前只支持在tracker_servers内随机选择一个节点后返回，
		 * 未来扩展此实现，利用轮询、加权轮询和最小连接数优先等算法选择某个TrackerServer后再返回
		 */
		int serverIndex = NumberUtils.randomIn(ClientGlobal.g_tracker_group.tracker_servers.length);
		return ClientGlobal.g_tracker_group.getConnection(serverIndex);
	}

	public TrackerServer getTrackerServer(int index) throws IOException {
		return ClientGlobal.g_tracker_group.getConnection(index);
	}

	@Override
	public StorageServer getStorageServer(TrackerServer trackerServer) throws IOException {
		return this.trackerClient.getStoreStorage(trackerServer);
	}

	@Override
	public StorageServer getStorageServer(TrackerServer trackerServer, String groupName) throws IOException {
		return this.trackerClient.getStoreStorage(trackerServer, groupName);
	}

	@Override
	public void release(TrackerServer trackerServer, StorageServer storageServer) throws IOException {
		try {
			if (storageServer != null)
				storageServer.close();
		} finally {
			if (trackerServer != null)
				trackerServer.close();
		}
	}

}
