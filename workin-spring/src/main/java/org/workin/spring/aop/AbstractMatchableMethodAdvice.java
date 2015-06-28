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

/**
 * @description 可设置匹配模式的方法拦截切面抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractMatchableMethodAdvice implements MatchableMethodAdvice {
	
	/** 方法名称模式集 */
	private Set<String> namePatterns;
	
	/** 全部匹配的标识符 */
	private String matchAll;
	
	private boolean match;
	
	/** 标识是否已做过模式匹配检测 */
	private boolean doMatch;

	public Set<String> getNamePatterns() {
		return namePatterns;
	}

	public void setNamePatterns(Set<String> namePatterns) {
		this.namePatterns = namePatterns;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (CollectionUtils.isNotEmpty(this.namePatterns)) {
			if (this.namePatterns.contains("*")) {
				this.matchAll = "*";
				this.namePatterns.remove("*");
			}
		} else
			this.matchAll = "*";
	}
	
	/**
	 * @description 检测当前方法是否匹配于模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param method
	 * @return
	 */
	protected boolean checkMatch(Method method) {
		if (!this.doMatch) {
			if (this.matchAll != null) {
				this.match = true;
			} else {
				Iterator<String> iterator = this.namePatterns.iterator();
				while (iterator.hasNext()) {
					String pattern = iterator.next();
					if (PatternMatchUtils.simpleMatch(pattern, method.getName())) {
						this.match = true;
						break;
					}
				}
			} 
			this.doMatch = true;
		}
		
		return this.match;
	}

}
