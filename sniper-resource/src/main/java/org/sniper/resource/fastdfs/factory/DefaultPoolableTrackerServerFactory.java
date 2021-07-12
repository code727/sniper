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

package org.sniper.resource.fastdfs.factory;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.pool.PoolableObjectFactory;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.sniper.commons.util.NumberUtils;

/**
 * 可池化的TrackerServer对象工厂默认实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefaultPoolableTrackerServerFactory implements PoolableObjectFactory<TrackerServer> {

	@Override
	public TrackerServer makeObject() throws Exception {
		/*
		 * 同org.sniper.resource.fastdfs.factory.connection.DefaultConnectionFactory类的getTrackerServer()方法的实现方式一样，
		 * 这里也不使用ClientGlobal.g_tracker_group.getConnection()的方法来返回，
		 * 目前只支持在tracker_servers内随机选择一个节点后返回，
		 * 未来扩展此实现，利用轮询、加权轮询和最小连接数优先等算法选择某个TrackerServer后再返回
		 */
		int serverIndex = NumberUtils.randomIn(ClientGlobal.g_tracker_group.tracker_servers.length);
		return ClientGlobal.g_tracker_group.getConnection(serverIndex);
	}

	@Override
	public void destroyObject(TrackerServer trackerServer) throws Exception {
		trackerServer.close();
	}

	@Override
	public boolean validateObject(TrackerServer trackerServer) {
		try {
			Socket socket = trackerServer.getSocket();
			return !socket.isClosed() && socket.isConnected();
		} catch (IOException e) {
			return false;
		}
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
