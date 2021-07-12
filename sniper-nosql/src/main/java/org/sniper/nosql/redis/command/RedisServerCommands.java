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
 * Create Date : 2015-5-11
 */

package org.sniper.nosql.redis.command;

import java.util.Properties;

import org.sniper.nosql.redis.enums.Section;

/**
 * Redis服务终端命令接口
 * @author  Daniele
 * @version 1.0
 */
public interface RedisServerCommands {
	
	/**
	 * 执行info命令，获取Redis服务器的各种信息和统计数值。
	 * @author Daniele 
	 * @return
	 */
	public Properties info();
	
	/**
	 * 执行info命令，获取Redis服务器指定部分的信息
	 * @author Daniele 
	 * @param section
	 * @return
	 */
	public Properties info(Section section);
	
	/**
	 * 执行info命令，获取Redis服务器指定的信息
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <T> T info(String key);
	
	/**
	 * 执行info命令， 获取Redis服务器指定类型的信息
	 * @author Daniele 
	 * @param key
	 * @param messageType
	 * @return
	 */
	public <T> T info(String key, Class<T> messageType);
	
	/**
	 * 执行config get命令，取得服务器的所有配置参数信息
	 * @author Daniele 
	 * @return
	 */
	public Properties configGet(); 
	
	/**
	 * 执行config get命令，取得服务器的配置参数信息
	 * @author Daniele 
	 * @param pattern
	 * @return
	 */
	public Properties configGet(String pattern); 
	
	/**
	 * 执行config get命令，单一取得服务器的配置参数值
	 * @author Daniele 
	 * @param parameter
	 * @return
	 */
	public <V> V config(String parameter);
	
	/**
	 * 执行config get命令，单一取得服务器指定类型的配置参数值
	 * @author Daniele 
	 * @param parameter
	 * @param valueType
	 * @return
	 */
	public <V> V config(String parameter, Class<V> valueType);
	
	/**
	 * 重置某些统计数据
	 * @author Daniele
	 */
	public void configResetStat();
	
	/**
	 * 动态设置服务器的配置(热更新)
	 * @author Daniele 
	 * @param parameter
	 * @param value
	 */
	public void configSet(String parameter, Object value);
	
	/**
	 * 在当前库中执行dbSize命令，获取当前库中键的个数
	 * @author Daniele 
	 * @return
	 */
	public Long dbSize();
	
	/**
	 * 在指定库中执行dbSize命令，获取当前库中键的个数
	 * @author Daniele 
	 * @param dbName
	 * @return
	 */
	public Long dbSize(String dbName);
	
	/**
	 * 返回最近一次成功将数据保存到磁盘上的UNIX时间戳
	 * @author Daniele 
	 * @return
	 */
	public Long lastSave();
	
	/**
	 * 执行一个同步保存操作，将当前 Redis实例的所有数据快照(snapshot)以RDB文件的形式保存到硬盘</P>
	 * 注意：在生产环境很少执行SAVE操作，因为它会阻塞所有客户端，保存数据快照的任务通常由BGSAVE命令异步执行。
	 * 如果负责保存数据的后台子进程不幸出现问题时， SAVE可以作为保存数据的最后手段来使用。
	 * @author Daniele
	 */
	public void save();
	
	/**
	 * 在后台异步保存当前数据库的数据到磁盘。
	 * @author Daniele
	 */
	public void bgSave();
	
	/**
	 * 执行flushAll命令，清空所有库中的数据
	 * @author Daniele
	 */
	public void flushAll();
	
	/**
	 * 在当前库中执行flushDb命令，清空当前库中的所有数据
	 * @author Daniele
	 */
	public void flushDb();
	
	/**
	 * 在指定库中执行flushDb命令，清空当前库中的所有数据
	 * @author Daniele 
	 * @param dbName
	 */
	public void flushDb(String dbName);
	
	/**
	 * 将当前服务器转变为指定服务器(host:port)的从服务器(slave server)
	 * @author Daniele 
	 * @param host
	 * @param port
	 */
	public void slaveOf(String host, int port);
	
	/**
	 * 获取服务器当前时间
	 * @author Daniele 
	 * @return
	 */
	public Long time();
	
	/**
	 * 执行shutdown命令，关闭服务器
	 * @author Daniele
	 */
	public void shutdown();
	
}
