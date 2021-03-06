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

package org.sniper.orm.jpa.dao;

import org.sniper.commons.model.PageResult;
import org.sniper.commons.request.PageQuery;

/**
 * Jpa分页查询接口
 * @author  Daniele
 * @version 1.0
 */
public interface JpaPagingQuery<T> {
	
	/**
	 * 针对于当前实体类型的分页查询
	 * @author Daniele 
	 * @param query 分页查询条件对象
	 * @param callback 查询回调
	 * @return
	 */
	public PageResult<T> pagingQuery(PageQuery query, JpaCriteriaQueryCallback<T> callback);

}
