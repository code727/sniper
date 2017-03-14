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
 * Create Date : 2017-3-14
 */

package org.workin.kafka.producer.listener;

import org.workin.commons.util.CodecUtils;
import org.workin.kafka.support.ProduceRecord;
import org.workin.kafka.support.ProduceResult;

/**
 * 可记录日志的生产者监听器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class LoggingProducerListener<K, V> extends AbstractProducerListener<K, V> {
	
	@Override
	protected void afterSuccess(ProduceResult<K, V> produceResult) {
		if (logger.isInfoEnabled())
			logger.info("Producer success send message:{}",
					CodecUtils.bytesToString(loggerSerializer.serialize(produceResult)));
	}

	@Override
	protected void afterFailure(ProduceRecord<K, V> produceRecord, Exception ex) {
		if (logger.isErrorEnabled())
			logger.error("Producer send message is failure:{},error cause:{}",CodecUtils.bytesToString(
					loggerSerializer.serialize(produceRecord)),ex.getMessage());
		
	}

}
