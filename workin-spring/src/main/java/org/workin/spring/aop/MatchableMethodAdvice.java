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

package org.workin.spring.aop;

import java.util.Set;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.InitializingBean;

/**
 * @description 可设置匹配模式的方法拦截切面接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MatchableMethodAdvice extends ThrowsAdvice, InitializingBean {
	
	/**
	 * @description 设置需要被拦截处理的方法名称模式集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param names
	 */
	public void setNamePatterns(Set<String> namePatterns);
	
	/**
	 * @description 获取需要被拦截处理的方法模式集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Set<String> getNamePatterns();
	
	/**
	 * @description 目标方法出现异常后被拦截处理的方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param e
	 * @throws Throwable
	 */
	public void afterThrowing(Exception e) throws Throwable; 

}
