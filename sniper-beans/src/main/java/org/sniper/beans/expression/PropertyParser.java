/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018年12月14日
 */

package org.sniper.beans.expression;

/**
 * 属性解析器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface PropertyParser {
	
	/**
	 * 从指定的表达式中判断出是否有嵌套属性
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param expression
	 * @return
	 */
	public boolean hasNested(String expression);
	
	/**
	 * 从指定的表达式中解析出属性名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param expression
	 * @return
	 */
	public String resolve(String expression);
	
	/**
	 * 从当前表达式中获取到下一条表达式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param expression
	 * @return
	 */
	public String next(String expression);

}
