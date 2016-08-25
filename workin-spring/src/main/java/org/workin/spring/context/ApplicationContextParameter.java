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

package org.workin.spring.context;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.DateUtils;
import org.workin.support.parameter.ConcurrentParameter;
import org.workin.support.parameter.handler.CoverageDulicateKeyHandler;
import org.workin.support.parameter.handler.KeyHandler;

/**
 * @description 应用上下文参数实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ApplicationContextParameter<K, V> extends ConcurrentParameter<K, V> implements InitializingBean {
		
	private static Logger logger = LoggerFactory.getLogger(ApplicationContextParameter.class);
	
	private KeyHandler keyHandler;
	
	private ParameterService parameterService;
	
	public ApplicationContextParameter() {
		this.keyHandler = new CoverageDulicateKeyHandler();
	}
	
	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public void setKeyHandler(KeyHandler keyHandler) {
		this.keyHandler = keyHandler;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (this.parameterService != null) {
			Date start = new Date();
			logger.info("Starting preloading application context parameters");
			this.setParameters((Map<K, V>) this.parameterService.preloading());
			logger.info("End preloading application context parameters in "
					+ DateUtils.getIntervalMillis(start, new Date()) + "ms.");
		}
	}
	
	@Override
	public void add(K name, V value) {
		keyHandler.put(this.parameters, name, value);
	}
	
	@Override
	public void setParameters(Map<K, V> parameters) {
		keyHandler.putAll(this.parameters, parameters);
	}
	
}
