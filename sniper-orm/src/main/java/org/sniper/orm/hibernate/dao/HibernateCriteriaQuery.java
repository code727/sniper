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
 * Create Date : 2015-3-10
 */

package org.sniper.orm.hibernate.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Hibernate标准化查询接口
 * @author  Daniele
 * @version 1.0
 */
public interface HibernateCriteriaQuery<T> {
	
	/**
	 * 执行标准化查询后返回当前类型的唯一实体对象
	 * @author Daniele 
	 * @param parameter 条件参数对象
	 * @param callback 查询回调
	 * @return
	 */
	public <P> T findUniqueByCriteria(P parameter, HibernateCriteriaQueryCallback callback);
	
	/**
	 * 执行标准化查询后返回当前类型的唯一实体对象
	 * @author Daniele 
	 * @param criteria
	 * @return
	 */
	public T findUniqueByCriteria(DetachedCriteria criteria);
	
	/**
	 * 执行标准化查询后返回当前类型的实体对象列表
	 * @author Daniele 
	 * @param parameter 条件参数对象
	 * @param callback 查询回调
	 * @return
	 */
	public <P> List<T> findByCriteria(P parameter, HibernateCriteriaQueryCallback callback);
	
	/**
	 * 从起始位置开始 执行标准化查询后返回最大行数当前类型的实体对象列表
	 * @author Daniele 
	 * @param parameter 条件参数对象
	 * @param start
	 * @param maxRows
	 * @param callback 查询回调
	 * @return
	 */
	public <P> List<T> findByCriteria(P parameter, int start, int maxRows, HibernateCriteriaQueryCallback callback);
	
	/**
	 * 执行标准化查询后返回当前类型的实体对象列表
	 * @author Daniele 
	 * @param criteria
	 * @return
	 */
	public List<T> findByCriteria(DetachedCriteria criteria);
	
	/**
	 * 从起始位置开始 执行标准化查询后返回最大行数当前类型的实体对象列表
	 * @author Daniele 
	 * @param criteria
	 * @param start
	 * @param maxRows
	 * @return
	 */
	public List<T> findByCriteria(DetachedCriteria criteria, int start, int maxRows);
	
	/**
	 * 查询出当前类型并满足参数条件的实体对象总数
	 * @author Daniele 
	 * @param parameter 条件参数对象
	 * @param callback 查询回调
	 * @return
	 */
	public <P> long countByCriteria(P parameter, HibernateCriteriaQueryCallback callback);
	
	/**
	 * 查询出当前类型并满足参数条件的实体对象总数
	 * @author Daniele 
	 * @param parameter 条件参数对象
	 * @param distinct 是否加上distinct关键字查询
	 * @param callback 查询回调
	 * @return
	 */
	public <P> long countByCriteria(P parameter, boolean distinct, HibernateCriteriaQueryCallback callback);
	
	/**
	 * 查询出当前类型并满足参数条件的实体对象总数
	 * @author Daniele 
	 * @param criteria
	 * @return
	 */
	public long countByCriteria(DetachedCriteria criteria);
	
	/**
	 * 查询出当前类型并满足参数条件的实体对象总数
	 * @author Daniele 
	 * @param criteria
	 * @param distinct 是否加上distinct关键字查询
	 * @return
	 */
	public long countByCriteria(DetachedCriteria criteria, boolean distinct);

}
