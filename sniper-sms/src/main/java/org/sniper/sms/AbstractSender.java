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
 * Create Date : 2015年12月28日
 */

package org.sniper.sms;

import java.util.Map;

import org.sniper.spring.context.ApplicationContextParameters;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 短信发送抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractSender implements Sender, InitializingBean {
	
	/** 短信应用上下文配置参数项 */
	@Autowired
	private ApplicationContextParameters<String, Object> smsContextParameters;
	
	protected Map<String, String> parameters;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.smsContextParameters == null)
			throw new IllegalArgumentException("Property 'smsContextParameters' must not be null.");
		
		parameters = initParameters(smsContextParameters);
	}

	public void setSmsContextParameters(ApplicationContextParameters<String, Object> smsContextParameters) {
		this.smsContextParameters = smsContextParameters;
	}

	/**
	 * 根据应用上下文配置参数项初始化必要的参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param smsContextParameters
	 * @return
	 */
	protected abstract Map<String, String> initParameters(
			ApplicationContextParameters<String, Object> smsContextParameters);

}
