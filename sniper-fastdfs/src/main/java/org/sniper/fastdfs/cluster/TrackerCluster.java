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
 * Create Date : 2015-10-29
 */

package org.sniper.fastdfs.cluster;

import java.util.Map;

import org.sniper.fastdfs.node.Tracker;
import org.sniper.support.server.InternetServer;

/**
 * Tracker集群族接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface TrackerCluster extends InternetServer {
		
	/**
	 * 设置集群族内的TrackerServer映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param trackers
	 */
	public void setTrackers(Map<String, Tracker> trackers);
	
	/**
	 * 获取集群族内的TrackerServer映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, Tracker> getTrackers();
	
	/**
	 * 设置Tracker集群族连接超时时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connectTimeout
	 */
	public void setConnectTimeout(int connectTimeout);
	
	/**
	 * 获取Tracker集群族连接超时时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getConnectTimeout();
	
	/**
	 * 设置Tracker集群族网络超时时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param networkTimeout
	 */
	public void setNetworkTimeout(int networkTimeout);
	
	/**
	 * 设置Tracker集群族网络超时时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getNetworkTimeout();
	
	/**
	 * 设置字符集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param charset
	 */
	public void setCharset(String charset);
	
	/**
	 * 获取字符集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getCharset();
		
	/**
	 * 是否设置HTTP安全秘钥
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param httpAntiStealToken
	 */
	public void setHttpAntiStealToken(boolean httpAntiStealToken);

	/**
	 * 判断是否设置HTTP安全秘钥
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isHttpAntiStealToken();
	
	/**
	 * 设置HTTP安全秘钥 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param httpSecretKey
	 */
	public void setHttpSecretKey(String httpSecretKey);

	/**
	 * 获取HTTP安全秘钥 
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getHttpSecretKey();

}
