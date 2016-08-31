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
 * Create Date : 2015-6-26
 */

package org.workin.spring.beans;

import org.springframework.beans.factory.InitializingBean;

/**
 * 可检测的初始化Bean抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class CheckableInitializingBean implements InitializingBean {
	
	public final void afterPropertiesSet() throws Exception {
		checkProperties();
		init();
	}
	
	/**
	 * 检测当前Bean对象属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 */
	protected abstract void checkProperties();
	
	/**
	 * 初始化操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws Exception
	 */
	protected abstract void init() throws Exception;
	
}
