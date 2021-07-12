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
 * Create Date : 2015年10月30日
 */

package org.sniper.resource.server;

/**
 * 内网服务器接口
 * @author  Daniele
 * @version 1.0
 */
public interface IntranetServer {
	
	/**
	 * 设置内网主机服务地址
	 * @author Daniele 
	 * @param host
	 */
	public void setHost(String host);
	
	/**
	 * 获取内网主机服务地址
	 * @author Daniele 
	 * @return
	 */
	public String getHost();
	
	/**
	 * 设置内网主机服务端口
	 * @author Daniele 
	 * @param port
	 */
	public void setPort(int port);
	
	/**
	 * 获取内网主机服务端口
	 * @author Daniele 
	 * @return
	 */
	public int getPort();

}
