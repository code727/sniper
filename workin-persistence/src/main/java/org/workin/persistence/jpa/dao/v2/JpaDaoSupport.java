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
 * Create Date : 2015-1-29
 */

package org.workin.persistence.jpa.dao.v2;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.spring.beans.AbstractGenricBean;

/**
 * @description JPA2标准的DAO支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class JpaDaoSupport<T> extends AbstractGenricBean<T> {
	
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	protected void checkProperties() {
		if (entityManager == null)
			throw new IllegalArgumentException("Property 'entityManager' is required");
	}
	
	/**
	 * @description 设置分段查询参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	protected void setOffsetQuery(Query query, int start, int maxRows) {
		if (start >= 0)
			query.setFirstResult(start);
		if (maxRows > 0)
			query.setMaxResults(maxRows);
	}
		
	/**
	 * @description 设置占位符形式的查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param values 查询参数值顺序组
	 */
	protected void setQueryParameters(Query query, Object[] values) {
		if (ArrayUtils.isNotEmpty(values)) {
			int length = values.length;
			for (int i = 0; i < length; i++) 
				query.setParameter(i + 1, values[i]);
		}
	}
	
	/**
	 * @description 设置占位符形式的分段查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param values 查询参数值顺序组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	protected void setQueryParameters(Query query, Object[] values, int start, int maxRows) {
		setQueryParameters(query, values);
		setOffsetQuery(query, start, maxRows);
	}
	
	/**
	 * @description 设置命名形式的查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @param paramName 查询参数名
	 * @param paramValue 查询参数值
	 */
	protected void setQueryNamedParameter(Query query, String paramName, Object paramValue) {
		if (StringUtils.isNotBlank(paramName)) 
			query.setParameter(paramName, paramValue);
	}
	
	/**
	 * @description 设置命名形式的分段查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param paramName 查询参数名
	 * @param paramValue 查询参数值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	protected void setQueryNamedParameter(Query query, String paramName, Object paramValue, int start, int maxRows) {
		setQueryNamedParameter(query, paramName, paramValue);
		setOffsetQuery(query, start, maxRows);
	}
	
	/**
	 * @description 设置命名形式的多个查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param paramMap 查询参数名-值映射集
	 */
	protected void setQueryNamedParameters(Query query, Map<String, ?> paramMap) {
		if (MapUtils.isNotEmpty(paramMap)) {
			Iterator<?> iterator = paramMap.entrySet().iterator();
			String name;
			while (iterator.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
				name = (String)entry.getKey();
				if (StringUtils.isNotBlank(name))
					query.setParameter(name, entry.getValue());
			}
		}
	}
	
	/**
	 * @description 设置命名形式的多个分段查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param paramMap 查询参数名-值映射集
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	protected void setQueryNamedParameters(Query query, Map<String, ?> paramMap, int start, int maxRows) {
		setQueryNamedParameters(query, paramMap);
		setOffsetQuery(query, start, maxRows);
	}

}
