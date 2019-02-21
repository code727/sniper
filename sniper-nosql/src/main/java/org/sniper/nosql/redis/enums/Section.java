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
 * Create Date : 2019年2月15日
 */

package org.sniper.nosql.redis.enums;

/**
 * Redis服务信息部分枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum Section {

	/** 服务器的信息 */
	SERVER,
	/** 已连接客户端的信息 */
	CLIENTS,
	/** 服务器的内存信息 */
	MEMORY,
	/** RDB持久化和 AOF持久化有关的信息 */
	PERSISTENCE,
	/** 一般统计信息 */
	STATS,
	/** 主/从复制信息 */
	REPLICATION,
	/** CPU的计算量统计信息 */
	CPU,
	/** 各种不同类型的命令的执行统计信息 */
	COMMANDSTATS,
	/** 集群相关的信息 */
	CLUSTER,
	/** 数据库相关的统计信息 */
	KEYSPACE,
	/** 所有信息 */
	ALL,
	/** 默认选择的信息 */
	DEFAULT
}
