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
 * Create Date : 2015-1-30
 */

package org.workin.persistence.jpa.dao;

import java.util.List;

/**
 * JPA标准化查询接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface JpaCriteriaQuery<T> {
		
	/**
	 * 查询出当前类型并满足参数条件的唯一实体对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter 条件参数对象
	 * @param callback 查询回调
	 * @return
	 */
	public <P> T findUniqueByCriteria(P parameter, JpaCriteriaQueryCallback<T> callback);
	
	/**
	 * 查询出当前类型并满足参数条件的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter 条件参数对象
	 * @param callback 查询回调
	 * @return
	 */
	public <P> List<T> findByCriteria(P parameter, JpaCriteriaQueryCallback<T> callback);
	
	/**
	 * 从起始位置开始查询出当前类型最大行数并满足参数条件的实体对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter 条件参数对象
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 * @param callback 查询回调
	 * @return
	 */
	public <P> List<T> findByCriteria(P parameter, int start, int maxRows, JpaCriteriaQueryCallback<T> callback);
	
	/**
	 * 查询出当前类型并满足参数条件的实体对象总数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter 条件参数对象
	 * @param callback 查询回调
	 * @return
	 */
	public <P> long countByCriteria(P parameter, JpaCriteriaQueryCallback<T> callback);
	
	/**
	 * 查询出当前类型并满足参数条件的实体对象总数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter 条件参数对象
	 * @param distinct 是否加上distinct关键字查询
	 * @param callback 查询回调
	 * @return
	 */
	public <P> long countByCriteria(P parameter, boolean distinct, JpaCriteriaQueryCallback<T> callback);
		
}
