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

import org.sniper.jms.core.strategy.ConsumeStrategy;

/**
 * JMS消费策略管理接口
 * @author  Daniele
 * @version 1.0
 */
public interface ConsumeStrategiesManager {
	
	/**
	 * 设置消费策略映射集
	 * @author Daniele 
	 * @param strategies
	 */
	public void setStrategies(Map<String, ConsumeStrategy> strategies);
	
	/**
	 * 设置消费策略映射集
	 * @author Daniele 
	 * @return
	 */
	public Map<String, ConsumeStrategy> getStrategies();
	
	/**
	 * 获取指定名称的消费策略
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public ConsumeStrategy getStrategy(String name);
	
}
