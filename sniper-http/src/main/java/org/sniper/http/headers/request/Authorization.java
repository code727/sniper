/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-8-17
 */

package org.sniper.http.headers.request;

import java.io.Serializable;

import org.sniper.commons.enums.http.AuthenticationEnum;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.StringUtils;

/**
 * HTTP授权凭证请求头
 * @author  Daniele
 * @version 1.0
 */
public class Authorization implements Serializable {
	
	private static final long serialVersionUID = -892657955289586836L;

	/** 认证类型 */
	private final AuthenticationEnum authEnum;
	
	/** 凭证 */
	private final String credentials;
	
	public Authorization(AuthenticationEnum authEnum, String credentials) {
		AssertUtils.assertNotNull(authEnum, "HTTP authentication type must not be null");
		AssertUtils.assertNotBlank(credentials, "HTTP authentication credentials must not be null or blank");
		
		this.authEnum = authEnum;
		this.credentials = credentials;
	}

	public AuthenticationEnum getAuthEnum() {
		return authEnum;
	}

	public String getCredentials() {
		return credentials;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(authEnum.getType())
				.append(StringUtils.SPACE).append(credentials).toString();
	}

}
