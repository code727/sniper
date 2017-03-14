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
 * Create Date : 2017-3-13
 */

package org.workin.kafka.support;

import java.io.Serializable;

import org.workin.kafka.topic.Topic;

/**
 * 生产记录实例
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ProduceRecord <K, V> implements Serializable {
	
	private static final long serialVersionUID = -1444897214374302627L;

	/** 源Topic */
	private Topic sourceTopic;
	
	/** 预生产消息 */
	private Message<K, V> message;
	
	public ProduceRecord(Topic sourceTopic, Message<K, V> message) {
		this.sourceTopic = sourceTopic;
		this.message = message;
	}
	
	public Topic getSourceTopic() {
		return sourceTopic;
	}
	
	public Message<K, V> getMessage() {
		return message;
	}

}
