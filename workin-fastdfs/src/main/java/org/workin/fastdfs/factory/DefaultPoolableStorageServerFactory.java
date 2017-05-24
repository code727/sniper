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

package org.workin.fastdfs.factory;

import java.io.IOException;
import java.net.Socket;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.workin.context.ThreadLocalHolder;

/**
 * 可池化的StorageServer对象工厂默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultPoolableStorageServerFactory implements PoolableStorageServerFactory {
	
	private TrackerClient trackerClient;
	
	@Override
	public TrackerClient getTrackerClient() {
		return this.trackerClient;
	}

	@Override
	public void setTrackerClient(TrackerClient trackerClient) {
		this.trackerClient = trackerClient;
	}

	@Override
	public StorageServer makeObject() throws Exception {
		TrackerServer trackerServer = (TrackerServer) ThreadLocalHolder.getAttribute("CURRENT_TRACKERSERVER");
		String groupName = (String) ThreadLocalHolder.getAttribute("CURRENT_STORAGE_GROUPNAME");
		return trackerClient.getStoreStorage(trackerServer, groupName);
	}

	@Override
	public void destroyObject(StorageServer storageServer) throws Exception {
		storageServer.close();
	}

	@Override
	public boolean validateObject(StorageServer storageServer) {
		try {
			Socket socket = storageServer.getSocket();
			if (!socket.isConnected() || socket.isClosed())
				return false;
		}catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void activateObject(StorageServer storageServer) throws Exception {
		Socket socket = storageServer.getSocket();
		if (!socket.isConnected() || socket.isClosed()) {
			/* 重连StorageServer节点 */
			socket.setSoTimeout(ClientGlobal.g_network_timeout);
			socket.connect(storageServer.getInetSocketAddress(), ClientGlobal.g_connect_timeout);
		}
	}

	@Override
	public void passivateObject(StorageServer storageServer) throws Exception {
		
	}

}
