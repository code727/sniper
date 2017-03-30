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

package org.workin.kafka.producer.service;

import java.util.Map;

/**
 * 生产者服务管理接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ProducerServiceManager {
	
	/**
	 * 设置生产者服务映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param producerSevice
	 */
	public void setProducerServices(Map<String, ProducerService<?, ?>> producerServices);
	
	/**
	 * 获取生产者服务映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, ProducerService<?, ?>> getProducerServices();
	
	/** 
	 * 根据键获取映射集里的某个生产者服务
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return 
	 */
	public ProducerService<?, ?> getService(String key);

}
