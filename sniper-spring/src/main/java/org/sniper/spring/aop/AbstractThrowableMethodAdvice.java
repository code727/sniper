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

import org.sniper.spring.beans.CheckableInitializingBeanAdapter;

/**
 * 可抛出异常的方法可拦截切面抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractThrowableMethodAdvice extends CheckableInitializingBeanAdapter
		implements ThrowableMethodAdvice {
	
	@Override
	public void afterThrowing(Exception e) throws Throwable {
		throw new Exception(e);
	}
	
}
