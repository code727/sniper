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
 * Create Date : 2015-8-14
 */

package org.workin.jms.service.support;

import java.util.Map;

import javax.jms.Destination;

import org.workin.jms.strategy.ProductionStrategy;
import org.workin.jms.strategy.manager.ProductionStrategiesManager;

/**
 * @description JMS生产者服务支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class ProducerServiceSupport extends JmsServiceSupport
		implements ProductionStrategiesManager {
		
	private Map<String, ProductionStrategy> productionStrategies;
	
	@Override
	public void setProductionStrategies(Map<String, ProductionStrategy> productionStrategies) {
		this.productionStrategies = productionStrategies;
	}

	@Override
	public Map<String, ProductionStrategy> getProductionStrategies() {
		return this.productionStrategies;
	}

	@Override
	public ProductionStrategy getProductionStrategy(String name) {
		return this.productionStrategies.get(name);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.productionStrategies == null)
			throw new IllegalAccessException("Property 'productionStrategies' map can not be empty.");
	}
		
	@Override
	protected Destination getDestination(String name) {
		 ProductionStrategy productionStrategy = getProductionStrategy(name);
		 return productionStrategy != null ? productionStrategy.getDestination() : null;
	}
		
}
