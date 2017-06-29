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

package org.sniper.persistence.datasource.advice;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.exception.SniperException;
import org.sniper.context.DataSourceHolder;

/**
 * 多数据源切换实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MultipleDataSourceAdvice extends AbstractMultipleDataSourceAdvice {
	
	private static Logger logger = LoggerFactory.getLogger(MultipleDataSourceAdvice.class);

	@Override
	protected void doBeforeTask(Method method, Object[] args, Object target) {
		String methodName = method.getName();
		// 根据方法名称来获取对应的数据源
		Object dataSource = multipleDataSourceManager.getDataSource(methodName);
		if (dataSource == null)
			throw new SniperException("Target method [" + methodName + "] not found correlative data source"); 
		
		DataSourceHolder.setDataSource(dataSource);
		logger.debug("Data source will be switch to [{}] before invoke [{}] method [{}]", dataSource,
				method.getDeclaringClass(), methodName);
	}
		
	@Override
	protected void doAfterReturningTask(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		
		DataSourceHolder.removeDataSource();
	}
	
}
