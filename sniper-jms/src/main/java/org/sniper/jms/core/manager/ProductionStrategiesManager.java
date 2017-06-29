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

package org.sniper.jms.core.manager;

import java.util.Map;

import org.sniper.jms.core.strategy.ProductionStrategy;

/**
 * Jms生产策略管理接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ProductionStrategiesManager {
	
	/**
	 * 设置生产策略映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param productionStrategies
	 */
	public void setStrategies(Map<String, ProductionStrategy> productionStrategies);
	
	/**
	 * 获取生产策略映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<String, ProductionStrategy> getStrategies();
	
	/**
	 * 获取指定名称的生产策略
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public ProductionStrategy getStrategy(String name);
		
}
