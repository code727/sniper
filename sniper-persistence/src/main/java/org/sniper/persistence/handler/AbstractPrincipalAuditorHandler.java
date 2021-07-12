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
 * Create Date : 2015年6月10日
 */

package org.sniper.persistence.handler;

import org.springframework.beans.factory.InitializingBean;
import org.sniper.security.manager.PrincipalManager;

/**
 * 关联Principal对象的审核者处理器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractPrincipalAuditorHandler implements AuditorHandler, InitializingBean {
	
	protected PrincipalManager pincipalManager;
	
	public PrincipalManager getPincipalManager() {
		return pincipalManager;
	}

	public void setPincipalManager(PrincipalManager pincipalManager) {
		this.pincipalManager = pincipalManager;
	}

	public void afterPropertiesSet() throws Exception {
		if (pincipalManager == null)
			throw new IllegalArgumentException("Property 'pincipalManager' is required");
	}

}
