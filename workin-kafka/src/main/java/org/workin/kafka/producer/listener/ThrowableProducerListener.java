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

package org.workin.kafka.producer.listener;

import org.springframework.kafka.support.LoggingProducerListener;
import org.workin.kafka.producer.exception.ProducerException;

/**
 * 可抛出异常的生产监听器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 * @param <V>
 * @param <K>
 */
public class ThrowableProducerListener<K, V> extends LoggingProducerListener<K, V> {
	
	@Override
	public void onError(String topic, Integer partition, K key, V value, Exception exception) {
		super.onError(topic, partition, key, value, exception);
		throw new ProducerException(exception);
	}

}
