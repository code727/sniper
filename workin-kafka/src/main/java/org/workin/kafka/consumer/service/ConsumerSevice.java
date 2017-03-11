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
 * Create Date : 2017年3月10日
 */

package org.workin.kafka.consumer.service;

import org.springframework.kafka.support.Acknowledgment;
import org.workin.kafka.exception.ConsumerException;
import org.workin.kafka.support.ConsumeResult;

/**
 * 消费者服务接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ConsumerSevice {
	
	/**
	 * 接收到消费结果后的业务处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param consumeResult
	 * @throws ConsumerException
	 */
	public <K, V> void afterReceive(ConsumeResult<K, V> consumeResult)
			throws ConsumerException;
	
	/**
	 * 接收到消费结果后的业务处理，完成之前进行Ack回复确认
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param tconsumeResult
	 * @param acknowledgment
	 * @throws ConsumerException
	 */
	public <K, V> void afterReceive(ConsumeResult<K, V> tconsumeResult,
			Acknowledgment acknowledgment) throws ConsumerException;

}
