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
 * Create Date : 2015-7-27
 */

package org.sniper.security.handler;

import org.sniper.commons.util.StringUtils;
import org.sniper.security.service.AuthorizationService;

/**
 * 授权处理适配器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AuthorizationHandlerAdapter implements AuthorizationHandler {
	
	/** 固定授权规则字符串 */
	private String fixRule = StringUtils.EMPTY;
	
	private AuthorizationService authorizationService;
	
	@Override
	public void setFixRule(String fixRule) {
		this.fixRule = fixRule;
	}

	@Override
	public String getFixRule() {
		return this.fixRule;
	}
	
	public AuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Override
	public String createRoleRule() {
		return StringUtils.EMPTY;
	}

	@Override
	public String createPermissionRule() {
		return StringUtils.EMPTY;
	}

}
