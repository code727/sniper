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
 * Create Date : 2015-1-19
 */

package org.workin.support.file.filter;

/**
 * Workin框架本地文件数字属性值过滤器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface WorkinFileNumberFilter extends WorkinFileFilter {
	
	/**
	 * 获取字符串过滤值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Number getFilterValue();
	
	/**
	 * 设置字符串过滤值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param filterValue
	 */
	public void setFilterValue(Number filterValue);
	
	/**
	 * 设置逻辑运算规则
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param operation
	 */
	public void setLogicOperation(String operation);
	
}
