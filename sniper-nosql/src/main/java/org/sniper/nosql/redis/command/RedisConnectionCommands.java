/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-2-20
 */

package org.sniper.nosql.redis.command;

/**
 * Redis连接命令行接口
 * @author  Daniele
 * @version 1.0
 */
public interface RedisConnectionCommands {
	
	/**
	 * 打印一个指定的信息
	 * @author Daniele 
	 * @param message
	 * @return
	 */
	public String echo(String message);
	
	/**
	 * 向 Redis服务器发送一段默认消息 ，如果服务器运作正常，则返回一个PONG
	 * @author Daniele 
	 * @return
	 */
	public String ping();
	
	/**
	 * 向 Redis服务器发送一段消息 ，如果服务器运作正常，则返回这段消息
	 * @author Daniele 
	 * @param message
	 * @return
	 */
	public String ping(String message);

}
