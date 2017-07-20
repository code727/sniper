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
 * Create Date : 2017-3-31
 */

package org.sniper.kafka.producer;

import org.sniper.commons.DataPair;
import org.sniper.commons.KeyValuePair;

/**
 * 生产者消息数据包
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MessagePacket<K, V> extends KeyValuePair<K, V> {
	
	private static final long serialVersionUID = -3243425298344495025L;

	/** 分区索引 */
	private Integer partition;
	
	/** 时间戳 */
	private Long timestamp;
	
	public MessagePacket() {
		super();
	}
	
	public MessagePacket(K key) {
		super(key);
	}
	
	public MessagePacket(DataPair<K, V> pair) {
		super(pair);
	}
	
	public MessagePacket(K key, V value) {
		super(key, value);
	}

	public Integer getPartition() {
		return partition;
	}

	public void setPartition(Integer partition) {
		this.partition = partition;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
}
