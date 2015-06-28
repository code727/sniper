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

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import org.springframework.util.PatternMatchUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.support.context.ApplicationContextHolder;

/**
 * @description 可设置匹配模式的方法拦截切面抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractMatchableMethodAdvice implements MatchableMethodAdvice {
	
	/** 方法名称模式集 */
	private Set<String> namePatterns;
	
	protected static String ADVICE_MATCH = "advice_match";
			
	public Set<String> getNamePatterns() {
		return namePatterns;
	}

	public void setNamePatterns(Set<String> namePatterns) {
		this.namePatterns = namePatterns;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	/**
	 * @description 检测当前方法是否匹配于模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param method
	 * @return
	 */
	protected boolean checkMatch(Method method) {
		/* 同一个方法第一次被拦截时的检测 */
		if (ApplicationContextHolder.getAttribute(ADVICE_MATCH) == null) {
			if (CollectionUtils.isNotEmpty(this.namePatterns)) {
				// 配置有"*"号时，则所有方法都需要拦截处理
				if (this.namePatterns.contains("*")) {
					ApplicationContextHolder.setAttribute(ADVICE_MATCH, true);
					return true;
				} else {
					Iterator<String> iterator = this.namePatterns.iterator();
					while (iterator.hasNext()) {
						String pattern = iterator.next();
						if (PatternMatchUtils.simpleMatch(pattern, method.getName())) {
							ApplicationContextHolder.setAttribute(ADVICE_MATCH, true);
							return true;
						}
					}
					ApplicationContextHolder.setAttribute(ADVICE_MATCH, false);
					return false;
				}
			} else {
				ApplicationContextHolder.setAttribute(ADVICE_MATCH, true);
				return true;
			}
		} 
		else
			/* 同一个方法第n+1次被拦截时的检测，例如：
			 * 环绕型拦截切面执行afterReturning()方法时则直接返回第一次执行before()方式时的检测结果 */
			return (boolean) ApplicationContextHolder.getAttribute(ADVICE_MATCH);
	}

	@Override
	public void afterThrowing(Exception e) throws Throwable {
		throw new Exception(e);
	}
	
}
