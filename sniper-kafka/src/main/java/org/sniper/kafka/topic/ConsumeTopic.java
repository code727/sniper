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
 * Create Date : 2017-3-10
 */

package org.sniper.kafka.topic;

import org.apache.kafka.common.record.TimestampType;

/**
 * 产生实际消费的Topic实例
 * @author  Daniele
 * @version 1.0
 */
public class ConsumeTopic extends Topic {
	
	private static final long serialVersionUID = -2057391162831745875L;

	/** 消费某条消息后当前Topic的偏移量 */
	private long offset;
	
	/** 时间戳类型 */
	private TimestampType timestampType;
	
	/** 序列化键大小 */
	private int serializedKeySize;
	
	/** 序列化值大小 */
	private int serializedValueSize;
	
	public ConsumeTopic(String name, Integer partition, Long timestamp,
			long offset, TimestampType timestampType, int serializedKeySize,
			int serializedValueSize) {
		
		super(name, partition, timestamp);
		this.offset = offset;
		this.timestampType = timestampType;
		this.serializedKeySize = serializedKeySize;
		this.serializedValueSize = serializedValueSize;
	}

	public long getOffset() {
		return offset;
	}

	public TimestampType getTimestampType() {
		return timestampType;
	}

	public int getSerializedKeySize() {
		return serializedKeySize;
	}

	public int getSerializedValueSize() {
		return serializedValueSize;
	}
	
}
