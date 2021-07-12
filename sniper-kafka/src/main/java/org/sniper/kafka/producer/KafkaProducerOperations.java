/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-3-9
 */

package org.sniper.kafka.producer;

import java.util.List;

import org.apache.kafka.common.PartitionInfo;

/**
 * 生产者操作接口
 * @author  Daniele
 * @version 1.0
 */
public interface KafkaProducerOperations {
	
	/**
	 * 获取默认topic分区信息列表
	 * @author Daniele 
	 * @return
	 */
	public List<PartitionInfo> partitionsFor();
	
	/**
	 * 获取指定的topic分区信息列表
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public List<PartitionInfo> partitionsFor(String name);
	
	/**
	 * 发送生产者缓存的消息
	 * @author Daniele
	 */
	public void flush();

}
