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
 * Create Date : 2015-1-14
 */

package org.workin.persistence.pagination;

/**
 * @description 分页附加结果类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface PagingAdditionalResult {
	
	/**
	 * @description 获取当前分页结果的总页数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public long totalPages();
	
	/**
	 * @description 获取当前分页结果从第几条记录开始
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public long getFrom();
	
	/**
	 * @description 获取当前分页结果从第几条记录结束
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public long getTo();

}
