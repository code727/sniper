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
 * Create Date : 2015-6-28
 */

package org.sniper.spring.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * 后置型方法拦截切面抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractMethodAfterAdvice extends AbstractThrowableMethodAdvice implements AfterReturningAdvice {
	
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		
		doAfterReturningTask(returnValue, method, args, target);
	}

	/**
	 * 执行后置拦截任务
	 * @author Daniele 
	 * @param returnValue
	 * @param method
	 * @param args
	 * @param target
	 * @throws Throwable
	 */
	protected abstract void doAfterReturningTask(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable;
	
}
