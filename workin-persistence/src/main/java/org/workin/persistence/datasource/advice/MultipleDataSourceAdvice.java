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
 * Create Date : 2015-5-12
 */

package org.workin.persistence.datasource.advice;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AopInvocationException;
import org.workin.support.context.DataSourceHolder;

/**
 * @description 多数据源切换实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MultipleDataSourceAdvice extends AbstractMultipleDataSourceAdvice {
		
	private static Logger logger = LoggerFactory.getLogger(MultipleDataSourceAdvice.class);
	
	@Override
	protected void doBeforeTask(Method method, Object[] args, Object target) {
		String methodName = method.getName();
		String sourceName = multipleDataSourceManager.getDataSourceName(methodName);
		if (sourceName == null)
			throw new AopInvocationException("Target method ["
					+ methodName + "] not found correlative data source name.");
		
		DataSourceHolder.setDataSourceName(sourceName);
		logger.info(new StringBuilder("Data source will be switch to [").append(sourceName)
				.append("] before invoke [").append(method.getDeclaringClass()).append("] method [")
				.append(methodName).append("].").toString());
	}
	
	@Override
	protected void doAfterReturningTask(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		
		DataSourceHolder.removeAttribute(DataSourceHolder.CURRENT_DATASOURCE_NAME);
	}
	
}
