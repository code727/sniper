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

package org.sniper.kafka.support;

import java.io.Serializable;

import org.sniper.kafka.topic.ConsumeTopic;

/**
 * 消费结果
 * @author  Daniele
 * @version 1.0
 */
public class ConsumeResult<K, V> implements Serializable {
	
	private static final long serialVersionUID = 9005358330863757717L;

	/** 产生实际消费的目标topic */
	private ConsumeTopic target;
	
	/** 消费的消息 */
	private Message<K, V> message;
	
	public ConsumeResult(ConsumeTopic target, Message<K, V> message) {
		this.target = target;
		this.message = message;
	}

	public ConsumeTopic getTarget() {
		return target;
	}

	public Message<K, V> getMessage() {
		return message;
	}
	
}
