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
 * Create Date : 2017-3-30
 */

package org.sniper.kafka.producer.behavior;

import org.sniper.kafka.support.ProduceRecord;
import org.sniper.kafka.support.ProduceResult;

/**
 * 生产者日志接口
 * @author  Daniele
 * @version 1.0
 */
public interface ProducerLogger {
	
	/**
	 * 打印成功事件发生时的生产日志
	 * @author Daniele 
	 * @param produceResult
	 */
	public <K, V> void successLog(ProduceResult<K, V> produceResult);
	
	/**
	 * 打印错误事件发生时的日志
	 * @author Daniele 
	 * @param produceRecord
	 * @param ex
	 */
	public <K, V> void errorLog(ProduceRecord<K, V> produceRecord, Throwable ex);

}
