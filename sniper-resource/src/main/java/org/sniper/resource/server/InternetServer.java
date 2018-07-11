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

package org.sniper.resource.server;

/**
 * 互联网服务器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface InternetServer extends IntranetServer {
		
	/**
	 * 设置互联网服务地址
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param internetHost
	 */
    public void setInternetHost(String internetHost);
	
    /**
     * 获取互联网服务地址
     * @author <a href="mailto:code727@gmail.com">杜斌</a> 
     * @return
     */
	public String getInternetHost();

	/**
	 * 设置互联网服务端口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param internetPort
	 */
	public void setInternetPort(int internetPort);
	
	/**
	 * 获取互联网服务端口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param extPort
	 * @return
	 */
	public int getInternetPort();
	
}
