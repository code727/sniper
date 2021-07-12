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

package org.sniper.kafka.consumer.service;

import java.util.Map;

/**
 * 消费者服务管理接口
 * @author  Daniele
 * @version 1.0
 */
public interface ConsumerServiceManager {
	
	/**
	 * 设置消费者服务映射集
	 * @author Daniele 
	 * @param consumerSevices
	 */
	public void setConsumerServices(Map<String, ConsumerService<?, ?>> consumerSevices);
	
	/**
	 * 获取消费者服务映射集
	 * @author Daniele 
	 * @return
	 */
	public Map<String, ConsumerService<?, ?>> getConsumerServices();
	
	/**
	 * 根据键获取映射集里的某个消费者服务
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public ConsumerService<?, ?> getService(String key);


}
