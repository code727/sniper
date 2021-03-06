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

package org.sniper.kafka.consumer.service;

import org.sniper.kafka.exception.ConsumerException;
import org.sniper.kafka.support.ConsumeResult;

/**
 * 消费者服务接口
 * @author  Daniele
 * @version 1.0
 */
public interface ConsumerService<K, V> {
	
	/**
	 * 接收到消费结果后的业务处理
	 * @author Daniele 
	 * @param consumeResult
	 * @throws ConsumerException
	 */
	public void receive(ConsumeResult<K, V> consumeResult) throws ConsumerException;

}
