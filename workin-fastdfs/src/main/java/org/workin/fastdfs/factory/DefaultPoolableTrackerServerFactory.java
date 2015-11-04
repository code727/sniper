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
 * Create Date : 2015年11月4日
 */

package org.workin.fastdfs.factory;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.pool.PoolableObjectFactory;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.workin.support.context.ApplicationContextHolder;

/**
 * @description 可池化的TrackerServer对象工厂默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultPoolableTrackerServerFactory implements PoolableObjectFactory<TrackerServer> {

	@Override
	public TrackerServer makeObject() throws Exception {
		return ClientGlobal.g_tracker_group.getConnection(
				(int) ApplicationContextHolder.getAttribute("CURRENT_TRACKERSERVER_INDEX"));
	}

	@Override
	public void destroyObject(TrackerServer trackerServer) throws Exception {
		trackerServer.close();
	}

	@Override
	public boolean validateObject(TrackerServer trackerServer) {
		try {
			Socket socket = trackerServer.getSocket();
			if (!socket.isConnected() || socket.isClosed())
				return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void activateObject(TrackerServer trackerServer) throws Exception {
		Socket socket = trackerServer.getSocket();
		if (!socket.isConnected() || socket.isClosed()) {
			/* 重连TrackerServer节点 */
			socket.setSoTimeout(ClientGlobal.g_network_timeout);
			socket.connect(trackerServer.getInetSocketAddress(), ClientGlobal.g_connect_timeout);
		}
	}

	@Override
	public void passivateObject(TrackerServer trackerServer) throws Exception {
		
	}

}
