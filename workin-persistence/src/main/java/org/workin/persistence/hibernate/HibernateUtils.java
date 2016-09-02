/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-8-26
 */

package org.workin.persistence.hibernate;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;

/**
 * Hibernate工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HibernateUtils {
	
	/**
	 * 设置分段标准化查询参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param criteria 标准化查询接口
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	public static void setOffsetCriteria(Criteria criteria, int start, int maxRows) {
		if (start >= 0)
			criteria.setFirstResult(start);
		
		if (maxRows > 0)
			criteria.setMaxResults(maxRows);
	}
	
	/**
	 * 设置分段查询参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	public static void setOffsetQuery(Query query, int start, int maxRows) {
		if (start >= 0)
			query.setFirstResult(start);
		
		if (maxRows > 0)
			query.setMaxResults(maxRows);
	}
		
	/**
	 * 设置占位符形式的查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param values 查询参数值顺序组
	 */
	public static void setQueryParameters(Query query, Object[] values) {
		if (ArrayUtils.isNotEmpty(values)) {
			int length = values.length;
			for (int i = 0; i < length; i++) 
				query.setParameter(i, values[i]);
		}
	}
	
	/**
	 * 设置占位符形式的分段查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param values 查询参数值顺序组
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	public static void setQueryParameters(Query query, Object[] values, int start, int maxRows) {
		setQueryParameters(query, values);
		setOffsetQuery(query, start, maxRows);
	}
	
	/**
	 * 设置命名形式的查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query
	 * @param paramName 查询参数名
	 * @param paramValue 查询参数值
	 */
	public static void setQueryNamedParameter(Query query, String paramName, Object paramValue) {
		if (StringUtils.isNotBlank(paramName)) 
			query.setParameter(paramName, paramValue);
	}
	
	/**
	 * 设置命名形式的分段查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param paramName 查询参数名
	 * @param paramValue 查询参数值
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	public static void setQueryNamedParameter(Query query, String paramName, Object paramValue, int start, int maxRows) {
		setQueryNamedParameter(query, paramName, paramValue);
		setOffsetQuery(query, start, maxRows);
	}
	
	/**
	 * 设置命名形式的多个查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param paramMap 查询参数名-值映射集
	 */
	public static void setQueryNamedParameters(Query query, Map<String, ?> paramMap) {
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
	 * 设置命名形式的多个分段查询参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param query 查询接口
	 * @param paramMap 查询参数名-值映射集
	 * @param start 起始位置
	 * @param maxRows 最大行数
	 */
	public static void setQueryNamedParameters(Query query, Map<String, ?> paramMap, int start, int maxRows) {
		setQueryNamedParameters(query, paramMap);
		setOffsetQuery(query, start, maxRows);
	}
	
	/**
	 * 获取实体类型对应的元数据对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sessionFactory
	 * @param entityClass
	 * @return
	 */
	public static ClassMetadata getClassMetadata(SessionFactory sessionFactory, Class<?> entityClass) {
		return sessionFactory.getClassMetadata(entityClass);
	}
	
	/**
	 * 获取实体类型对应的名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sessionFactory
	 * @param entityClass
	 * @return
	 */
	public static String entityName(SessionFactory sessionFactory, Class<?> entityClass) {
		return getClassMetadata(sessionFactory, entityClass).getEntityName();
	}
	
	/**
	 * 获取实体类型对应的表名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sessionFactory
	 * @param entityClass
	 * @return
	 */
	public static String getTableName(SessionFactory sessionFactory, Class<?> entityClass) {
		return ((AbstractEntityPersister) getClassMetadata(sessionFactory, entityClass)).getTableName();
	}
			
	/**
	 * 获取实体类型对应的表的主键名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param sessionFactory 
	 * @param entityClass
	 * @return
	 */
	public static String getidName(SessionFactory sessionFactory, Class<?> entityClass) {
		return getClassMetadata(sessionFactory, entityClass).getIdentifierPropertyName();
	}

}
