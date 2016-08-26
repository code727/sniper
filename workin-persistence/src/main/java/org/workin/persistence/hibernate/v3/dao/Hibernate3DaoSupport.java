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

package org.workin.persistence.hibernate.v3.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.persistence.hibernate.dao.HibernateDao;

/**
 * @description Hibernate3 DAO支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class Hibernate3DaoSupport<T, PK extends Serializable> extends
		HibernateDaoSupport implements HibernateDao<T, PK> {
	
	/** 当前DAO所关联的实体类型 */
	private Class<T> entityClass;
	
	@Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getEntityClass() {
		if (this.entityClass == null)
			this.entityClass = (Class<T>) ClassUtils.getSuperClassGenricType(getClass());
		
		return this.entityClass;
	}
	
	/**
	 * @description 打开一个新会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public Session openSession() {
		return getSessionFactory().openSession();
	}
	
	/**
	 * @description 获取当前会话
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public Session getCurrentSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}
	
	/**
	 * @description 获取实体类型对应的元数据对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected ClassMetadata getClassMetadata(Class<?> entityClass) {
		return getSessionFactory().getClassMetadata(entityClass);
	}
	
	/**
	 * @description 获取实体类型对应的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected String entityName(Class<?> entityClass) {
		return getClassMetadata(entityClass).getEntityName();
	}
	
	/**
	 * @description 获取实体类型对应的表名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param entityClass
	 * @return
	 */
	protected String getTableName(Class<?> entityClass) {
		return ((AbstractEntityPersister) getClassMetadata(entityClass)).getTableName();
	}
			
	/**
	 * @description 获取实体类型对应的表的主键名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	protected String getPrimaryKeyName(Class<?> entityClass) {
		return getClassMetadata(entityClass).getIdentifierPropertyName();
	}
	
	/**
	 * @description 设置分段标准化查询参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param criteria 标准化查询接口
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	protected void setOffsetCriteria(Criteria criteria, int start, int maxRows) {
		if (start >= 0)
			criteria.setFirstResult(start);
		if (maxRows > 0)
			criteria.setMaxResults(maxRows);
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
			for (int i = 0; i < values.length; i++) 
				query.setParameter(i, values[i]);
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
