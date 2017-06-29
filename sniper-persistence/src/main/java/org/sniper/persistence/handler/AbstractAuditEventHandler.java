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
 * Create Date : 2015-1-27
 */

package org.sniper.persistence.handler;

import org.sniper.commons.util.StringUtils;

/**
 * 审核事件处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractAuditEventHandler<T> implements AuditEventHandler<T>, AuditorHandler {
	
	/** 关联的审核者处理器 */
	private AuditorHandler auditorHandler;
	
	@Override
	public String getAuditorName() {
		String auditorName = auditorHandler.getAuditorName();
		if (StringUtils.isBlank(auditorName))
			throw new SecurityException("Current auditorName is empty!"
					+ "Please check whether user login or correlative field is empty.");
					
		return auditorName;
	}

	public AuditorHandler getAuditorHandler() {
		return auditorHandler;
	}

	public void setAuditorHandler(AuditorHandler auditorHandler) {
		this.auditorHandler = auditorHandler;
	}
	
}
