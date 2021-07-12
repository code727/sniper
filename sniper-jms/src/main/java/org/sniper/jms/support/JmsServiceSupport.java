/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-8-17
 */

package org.sniper.jms.support;

import org.springframework.beans.factory.InitializingBean;
import org.sniper.jms.core.template.ConsumerTemplate;
import org.sniper.jms.core.template.ProducerTemplate;

/**
 * JMS服务支持抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class JmsServiceSupport implements InitializingBean {
	
	private ProducerTemplate producerTemplate;
	
	private ConsumerTemplate consumerTemplate;
	
	public ProducerTemplate getProducerTemplate() {
		return producerTemplate;
	}

	public void setProducerTemplate(ProducerTemplate producerTemplate) {
		this.producerTemplate = producerTemplate;
	}

	public ConsumerTemplate getConsumerTemplate() {
		return consumerTemplate;
	}

	public void setConsumerTemplate(ConsumerTemplate consumerTemplate) {
		this.consumerTemplate = consumerTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (getProducerTemplate() == null)
			throw new IllegalArgumentException("Property 'producerTemplate' is required.");
		
		if (getConsumerTemplate() == null)
			throw new IllegalArgumentException("Property 'consumerTemplate' is required.");
	}

}
