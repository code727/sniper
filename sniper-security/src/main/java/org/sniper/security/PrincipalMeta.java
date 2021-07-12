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
 * Create Date : 2015-6-2
 */

package org.sniper.security;

/**
 * Principal元数据对象
 * @author  Daniele
 * @version 1.0
 */
public class PrincipalMeta {
	
	/** 默认的用户登录名字段名称 */
	public static final String DEFAULT_LOGINNAMEFIELD = "loginName";
	
	/** 默认的用户姓名字段名称 */
	public static final String DEFAULT_USERNAMEFIELD = "userName";
	
	/** 用户登录名字段名称 */
	private String loginNameField;
	
	/** 用户姓名字段名称 */
	private String userNameField;

	public String getLoginNameField() {
		return loginNameField;
	}

	public void setLoginNameField(String loginNameField) {
		this.loginNameField = loginNameField;
	}

	public String getUserNameField() {
		return userNameField;
	}

	public void setUserNameField(String userNameField) {
		this.userNameField = userNameField;
	}
	
}
