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
 * Create Date : 2015-2-2
 */

package org.workin.persistence.jpa.dao;

import org.workin.commons.pagination.PagingQuery;
import org.workin.commons.pagination.PagingResult;

/**
 * Jpa分页查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JpaPagingQuery<T> {
	
	/**
	 * 针对于当前实体类型的分页查询
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 分页查询条件对象
	 * @param callback 查询回调
	 * @return
	 */
	public PagingResult<T> pagingQuery(PagingQuery query, JpaCriteriaQueryCallback<T> callback);

}
