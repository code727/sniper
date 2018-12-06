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
 * Create Date : 2015年7月21日
 */

package org.sniper.commons.test.domain;

import java.io.Serializable;

import org.sniper.commons.test.annotation.LoginName;
import org.sniper.commons.test.annotation.Principal;

/**
 * 
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Principal
public class User extends Person implements Serializable, Cloneable {

	private static final long serialVersionUID = -8069182640773640587L;
	
	public static final String DEFAULT_PASSWORD = "123456";
		
	@LoginName
	private String loginName;
	
	public String password = DEFAULT_PASSWORD;
	
	public User() {}
	
	protected User(String name, String loginName, String password) {
		super(name);
		this.loginName = loginName;
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected User clone() {
		try {
			return (User) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
			
}
