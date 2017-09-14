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
 * Create Date : 2015-11-16
 */

package org.sniper.spring.context;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.sniper.beans.parameter.ConcurrentParameters;
import org.sniper.beans.parameter.handler.CoverageDulicateKeyHandler;
import org.sniper.beans.parameter.handler.KeyHandler;
import org.sniper.commons.util.DateUtils;

/**
 * 应用上下文参数实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ApplicationContextParameters<K, V> extends ConcurrentParameters<K, V> implements InitializingBean {
		
	private static Logger logger = LoggerFactory.getLogger(ApplicationContextParameters.class);
	
	private KeyHandler keyHandler;
	
	@Autowired(required = false)
	private ParametersService parametersService;
	
	public ApplicationContextParameters() {
		this.keyHandler = new CoverageDulicateKeyHandler();
	}
	
	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public void setKeyHandler(KeyHandler keyHandler) {
		this.keyHandler = keyHandler;
	}

	public ParametersService getParameterService() {
		return parametersService;
	}

	public void setParameterService(ParametersService parametersService) {
		this.parametersService = parametersService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (this.parametersService != null) {
			Date start = new Date();
			logger.info("Starting preloading application context parameters");
			this.setParameterItems((Map<K, V>) this.parametersService.preloading());
			logger.info("End preloading application context parameters in {} ms", DateUtils.getIntervalMillis(start, new Date()));
		}
	}
	
	/**
	 * 重写父类方法，将新增参数的行为交由KeyHandler对象来处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param value
	 */
	@Override
	public void add(K name, V value) {
		keyHandler.put(this.parameterItems, name, value);
	}
	
	/**
	 * 重写父类方法，将新增多个参数的行为交由KeyHandler对象来处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameterItems 
	 */
	@Override
	public void addAll(Map<K, V> parameterItems) {
		keyHandler.putAll(this.parameterItems, parameterItems);
	}
			
}
