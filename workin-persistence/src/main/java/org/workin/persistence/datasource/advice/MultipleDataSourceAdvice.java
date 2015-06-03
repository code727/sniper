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

/**
 * @description 多数据源切换实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MultipleDataSourceAdvice extends AbstractMultipleDataSourceAdvice {
		
	private static Logger logger = LoggerFactory.getLogger(MultipleDataSourceAdvice.class);
	
	/**
	 * @description 目标方法执行之前调用
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param method
	 * @param args
	 * @param target
	 * @throws Throwable 
	 */
	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		String methodName = method.getName();
		String sourceName = multipleDataSourceManager.getDataSourceName(methodName);
		if (sourceName == null)
			throw new AopInvocationException("Target method ["
					+ methodName + "] not found correlative data source name.");
		
		MultipleDataSourceHolder.setDataSourceName(sourceName);
		logger.info("Invoke method [" + methodName + "] of data source [" + sourceName + "].");
	}

	/**
	 * @description 目标方法执行之后调用
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param returnValue
	 * @param method
	 * @param args
	 * @param target
	 * @throws Throwable
	 */
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		MultipleDataSourceHolder.clear();
	}
	
	/**
	 * @description 目标方法出现异常后调用
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param e
	 * @throws Throwable
	 */
	public void afterThrowing(Exception e) throws Throwable {  
		throw new Exception(e);
	}

}
