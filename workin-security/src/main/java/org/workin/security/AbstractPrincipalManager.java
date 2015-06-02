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

package org.workin.security;

import org.workin.commons.util.StringUtils;
import org.workin.support.bean.BeanUtils;

/**
 * @description Principal管理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractPrincipalManager implements PrincipalManager {
	
	protected PrincipalField principalField;
	
	public PrincipalField getPrincipalField() {
		return principalField;
	}

	public void setPrincipalField(PrincipalField principalField) {
		this.principalField = principalField;
	}
	
	@Override
	public String getCurrentLoginName() {
		check();
		Object user = getCurrentUser();
		if (user instanceof String)
			return user.toString();
		
		if (hasLoginNameField()) {
			try {
				return BeanUtils.get(user, principalField.getLoginNameField()).toString();
			} catch (Exception e) {
				throw new SecurityException("Login name field ["
						+ principalField.getLoginNameField() + "] can not found in user class [" + user.getClass() + "].");
			}
		} else {
			try {
				return BeanUtils.get(user, PrincipalField.DEFAULT_LOGINNAMEFIELD).toString();
			} catch (Exception e) {
				throw new SecurityException("Login name field [" 
						+ PrincipalField.DEFAULT_LOGINNAMEFIELD + "] can not found in user class [" + user.getClass() + "].");
			}
		}
	}

	@Override
	public String getCurrentUserName() {
		check();
		Object user = getCurrentUser();
		if (user instanceof String)
			return user.toString();
		
		if (hasUserNameField()) {
			try {
				return BeanUtils.get(user, principalField.getUserNameField()).toString();
			} catch (Exception e) {
				throw new SecurityException("User name field ["
						+ principalField.getUserNameField() + "] can not found in user class [" + user.getClass() + "].");
			}
		} else {
			try {
				return BeanUtils.get(user, PrincipalField.DEFAULT_USERNAMEFIELD).toString();
			} catch (Exception e) {
				throw new SecurityException("User name field [" 
						+ PrincipalField.DEFAULT_USERNAMEFIELD + "] can not found in user class [" + user.getClass() + "].");
			}
		}
	}
	
	/**
	 * @description 检测当前用户是否登录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws SecurityException
	 */
	protected void check() throws SecurityException {
		Object user = getCurrentUser();
		if (user == null)
			throw new SecurityException("Current user is null! Please login again.");
	}
	
	/**
	 * @description 判断是否指定有登录名字段
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected boolean hasLoginNameField() {
		return principalField != null && StringUtils.isNotBlank(principalField.getLoginNameField());
	}
	
	/**
	 * @description 判断是否指定有用户名字段
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected boolean hasUserNameField() {
		return principalField != null && StringUtils.isNotBlank(principalField.getUserNameField());
	}
	
}
