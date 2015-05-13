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
 * Create Date : 2015-5-13
 */

package org.workin.persistence.datasource.advice;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.workin.persistence.datasource.manager.DataSourceManager;

/**
 * @description 多数据源切换选择器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractMultipleDataSourceAdvice implements MethodBeforeAdvice,
	AfterReturningAdvice, ThrowsAdvice, InitializingBean {
	
	/** 多数据源管理器 */
	protected DataSourceManager multipleDataSourceManager;
	
	public void setMultipleDataSourceManager(
			DataSourceManager multipleDataSourceManager) {
		this.multipleDataSourceManager = multipleDataSourceManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (multipleDataSourceManager == null)
			throw new IllegalArgumentException("Property 'multipleDataSourceManager' is required");
	}

}
