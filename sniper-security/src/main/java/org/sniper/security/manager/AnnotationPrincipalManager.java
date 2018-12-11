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
 * Create Date : 2015-7-24
 */

package org.sniper.security.manager;

import java.lang.reflect.Field;

import org.sniper.commons.util.AnnotationUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.security.PrincipalMeta;
import org.sniper.security.annotation.LoginName;
import org.sniper.security.annotation.UserName;


/**
 * 注解方式的Principal管理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AnnotationPrincipalManager extends AbstractPrincipalManager {
	
	/** Principal对象类型 */
	private String principalBeanClass;
	
	private Class<?> principalClass;

	public void setPrincipalBeanClass(String principalBeanClass) {
		this.principalBeanClass = principalBeanClass;
	}
	
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isNotBlank(this.principalBeanClass)) {
			/* 只有设置了principalBeanClass值，注解工作才会进行 */
			this.principalClass = Class.forName(this.principalBeanClass.trim());
			PrincipalMeta principalMeta = new PrincipalMeta();
			this.setLoginNameField(principalMeta);
			this.setUserNameField(principalMeta);
			super.setPrincipalMeta(principalMeta);
		}
		super.afterPropertiesSet();
	}
	
	/**
	 * 根据注解描述设置PrincipalMeta对象的loginNameField值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param principalMeta
	 */
	private void setLoginNameField(PrincipalMeta principalMeta) {
		Field loginNameField = AnnotationUtils.getAnnotatedField(this.principalClass, LoginName.class);
		if (loginNameField != null)
			principalMeta.setLoginNameField(loginNameField.getName());
	}
	
	/**
	 * 根据注解描述设置PrincipalMeta对象的userNameField值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param principalMeta
	 */
	private void setUserNameField(PrincipalMeta principalMeta) {
		Field userNameField = AnnotationUtils.getAnnotatedField(this.principalClass, UserName.class);
		if (userNameField != null)
			principalMeta.setLoginNameField(userNameField.getName());
	}

}
