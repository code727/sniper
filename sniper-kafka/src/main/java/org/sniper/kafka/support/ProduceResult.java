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

package org.sniper.kafka.support;

import org.sniper.kafka.topic.Topic;

/**
 * 生产结果实例
 * @author  Daniele
 * @version 1.0
 */
public class ProduceResult<K, V> extends ProduceRecord<K, V> {
	
	private static final long serialVersionUID = 9160410391945854012L;
	
	/** 目标Topic */
	private Topic target;
	
	public ProduceResult(Topic source, Topic target,  Message<K, V> message) {
		super(source, message);
		this.target = target;
	}

	public Topic getTarget() {
		return target;
	}

}
