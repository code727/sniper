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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.StringUtils;
import org.workin.support.bean.BeanUtils;

/**
 * @description Principal管理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractPrincipalManager implements PrincipalManager, InitializingBean {
	
	private final Logger logger;
	
	private PrincipalMeta principalMeta;
	
	public AbstractPrincipalManager() {
		this.logger = LoggerFactory.getLogger(getClass());
	}
	
	public PrincipalMeta getPrincipalMeta() {
		return principalMeta;
	}

	public void setPrincipalMeta(PrincipalMeta principalMeta) {
		this.principalMeta = principalMeta;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.principalMeta == null) 
			this.principalMeta = new PrincipalMeta();
		
		if (StringUtils.isBlank(this.principalMeta.getLoginNameField())) {
			this.principalMeta.setLoginNameField(PrincipalMeta.DEFAULT_LOGINNAMEFIELD);
			logger.info("PrincipalMeta loginNameField property is blank, will be set defalut value:"
					+ PrincipalMeta.DEFAULT_LOGINNAMEFIELD);
		}
		
		if (StringUtils.isBlank(this.principalMeta.getUserNameField())) {
			this.principalMeta.setUserNameField(PrincipalMeta.DEFAULT_USERNAMEFIELD);
			logger.info("PrincipalMeta userNameField property is blank, will be set defalut value:"
					+ PrincipalMeta.DEFAULT_USERNAMEFIELD);
		}
		
	}
	
	@Override
	public String getCurrentLoginName() {
		check();
		Object user = getCurrentUser();
		if (user instanceof String)
			return user.toString();

		try {
			return StringUtils.toString(BeanUtils.get(user, this.principalMeta.getLoginNameField()));
		} catch (Exception e) {
			throw new SecurityException("Can not found login name field [" 
					+ this.principalMeta.getLoginNameField() + "] in user class [" + user.getClass() + "].");
		}
	}

	@Override
	public String getCurrentUserName() {
		check();
		Object user = getCurrentUser();
		if (user instanceof String)
			return user.toString();
		
		try {
			return StringUtils.toString(BeanUtils.get(user, this.principalMeta.getUserNameField()));
		} catch (Exception e) {
			throw new SecurityException("Can not found user name field ["
					+ this.principalMeta.getUserNameField() + "] in user class [" + user.getClass() + "].");
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
		
}
