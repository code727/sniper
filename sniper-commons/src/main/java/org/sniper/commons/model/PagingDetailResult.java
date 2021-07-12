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

package org.sniper.commons.model;

import org.sniper.commons.request.Pageable;

/**
 * 分页明细结果接口
 * @author  Daniele
 * @version 1.0
 */
public interface PagingDetailResult<T> extends Pageable, PagingResult<T> {
	
	/**
	 * 获取当前分页结果的总页数
	 * @author Daniele 
	 * @return
	 */
	public long getPages();
	
	/**
	 * 获取当前分页结果从第几条记录开始
	 * @author Daniele 
	 * @return
	 */
	public long getFrom();
	
	/**
	 * 获取当前分页结果从第几条记录结束
	 * @author Daniele 
	 * @return
	 */
	public long getTo();

}
