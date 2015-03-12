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

package org.workin.persistence.hibernate.v3;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;




import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Table;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description Hibernate3 DAO支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class Hibernate3DaoSupport extends HibernateDaoSupport {
	
	private static Configuration config;
	
	private static Object lock = new Object();
	
	/**
	 * @description 获取Hibernate配置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @return
	 */
	private static Configuration getHibernateConf() {
		if (config == null) {
			synchronized (lock) {
				if (config == null) {
					config = new Configuration().configure();
					// 注解方式必须的
					config.buildSessionFactory();
				}
			}
		}
		return config;
	}
	
	/**
	 * @description 获取指定类型的持久化类
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param clazz
	 * @return
	 */
	private static PersistentClass getPersistentClass(Class<?> clazz) {
		return getHibernateConf().getClassMapping(clazz.getName());
	}
	
	/**
	 * @description 获取类型对应的表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	protected static Table getTable(Class<?> clazz) {
		return getPersistentClass(clazz).getTable();
	}

	/**
	 * @description 获取类型对应的表名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	protected static String getTableName(Class<?> clazz) {
		return getTable(clazz).getName();
	}
	
	/**
	 * @description 获取类型对应的表的主键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	protected static PrimaryKey getPrimaryKey(Class<?> clazz) {
		return getTable(clazz).getPrimaryKey();
	}
	
	/**
	 * @description  获取类型对应的表的主键名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @return
	 */
	protected static String getPrimaryKeyName(Class<?> clazz) {
		return getPrimaryKey(clazz).getName();
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
	
	/**
	 * @description 打开一个
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected Session openSession() {
		return getSessionFactory().openSession();
	}
	
	/**
	 * @description 获取当前Session
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected Session getCurrentSession() {
		return getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

}
