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

package org.sniper.persistence.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 持久化辅助工具类
 * @author <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PersistenceUtils {

	private static final Logger logger = LoggerFactory.getLogger(PersistenceUtils.class);
	
	/**
	 * 构建指定类型的查询语句
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 * @param queryCount 是否构建count查询语句
	 * @param clazz 实体类型
	 * @return
	 */
	public static StringBuilder buildQueryString(boolean queryCount, Class<?> clazz) {
		StringBuilder queryBuilder = new StringBuilder();

		String alias = clazz.getSimpleName() + "_0";
		if (queryCount) 
			queryBuilder.append("SELECT COUNT(*) as totalCount FROM ");
		else 
			queryBuilder.append("SELECT ").append(alias).append(" FROM ");

		queryBuilder.append(clazz.getName());
		queryBuilder.append(StringUtils.SPACE_STRING).append(alias).append(StringUtils.SPACE_STRING);

		return queryBuilder;
	}
	
	/**
	 * 构建指定类型带属性占位符(?)条件的查询语句，属性条件之间为AND关系
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryCount 是否构建count查询语句
	 * @param clazz 实体类型
	 * @param propertyNames 属性名称组
	 * @return
	 */
	public static String buildQueryString(boolean queryCount, Class<?> clazz, String[] propertyNames) {
		StringBuilder queryBuilder = buildQueryString(queryCount, clazz);
		
		if (ArrayUtils.isNotEmpty(propertyNames)) {
			StringBuilder whereBuilder = new StringBuilder("WHERE ");
			for (String name : propertyNames) {
				if (StringUtils.isNotBlank(name))
					whereBuilder.append(name).append(" = ? AND ");
			}
			
			// 删除where条件部分最后一个AND关键字后拼接到查询语句后面
			if (whereBuilder.lastIndexOf(" AND ") == (whereBuilder.length() - 5)) {
				whereBuilder.delete(whereBuilder.length() - 5, whereBuilder.length());
				queryBuilder.append(whereBuilder);
			}
		}
		return queryBuilder.toString();
	}
	
	/**
	 * 构建指定类型带属性命名(=:name)条件的查询语句，属性条件之间为AND关系
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryCount 是否构建count查询语句
	 * @param clazz 实体类型
	 * @param propertyNames 属性名称组
	 * @return
	 */
	public static String buildNamedQueryString(boolean queryCount, Class<?> clazz, String[] propertyNames) {
		StringBuilder queryBuilder = buildQueryString(queryCount, clazz);
		
		if (ArrayUtils.isNotEmpty(propertyNames)) {
			StringBuilder whereBuilder = new StringBuilder("WHERE ");
			for (String name : propertyNames) {
				if (StringUtils.isNotBlank(name))
					whereBuilder.append(name).append(" = :").append(name).append(" AND ");
			}
			
			// 删除where条件部分最后一个AND关键字后拼接到查询语句后面
			if (whereBuilder.lastIndexOf(" AND ") == (whereBuilder.length() - 5)) {
				whereBuilder.delete(whereBuilder.length() - 5, whereBuilder.length());
				queryBuilder.append(whereBuilder);
			}
		}
		logger.debug("Build named query: {}", queryBuilder.toString());
		return queryBuilder.toString();
	}
	
	/**
	 * 构建指定类型带属性命名(=:name)条件的查询语句，属性条件之间为AND关系
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryCount 是否构建count查询语句
	 * @param clazz 实体类型
	 * @param propertyNames 属性名称集
	 * @return
	 */
	public static String buildNamedQueryString(boolean queryCount, Class<?> clazz, Collection<String> propertyNames) {
		StringBuilder queryBuilder = buildQueryString(queryCount, clazz);
		
		if (CollectionUtils.isNotEmpty(propertyNames)) {
			StringBuilder whereBuilder = new StringBuilder("WHERE ");
			for (String name : propertyNames) {
				if (StringUtils.isNotBlank(name))
					whereBuilder.append(name).append(" = :").append(name).append(" AND ");
			}
			
			// 删除where条件部分最后一个AND关键字后拼接到查询语句后面
			if (whereBuilder.lastIndexOf(" AND ") == (whereBuilder.length() - 5)) {
				whereBuilder.delete(whereBuilder.length() - 5, whereBuilder.length());
				queryBuilder.append(whereBuilder);
			}
		}
		logger.debug("Build named query: {}", queryBuilder.toString());
		return queryBuilder.toString();
	}
	
	/**
	 * 构建指定类型带属性命名(=:name)条件的查询语句，属性条件之间为AND关系
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryCount 是否构建count查询语句
	 * @param clazz 实体类型
	 * @param paramMap 带属性名称的参数映射集
	 * @return
	 */
	public static String buildNamedQueryString(boolean queryCount, Class<?> clazz, Map<String, ?> paramMap) {
		return MapUtils.isNotEmpty(paramMap) ? 
				buildNamedQueryString(queryCount, clazz, paramMap.keySet()) : 
					buildQueryString(queryCount, clazz).toString();
	}
	
	/**
	 * 根据属性过滤器链来构建指定类型的查询语句
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryCount 是否构建count查询语句
	 * @param clazz 实体类型
	 * @param chain 过滤器链
	 * @return
	 */
	public static String buildQueryStringByFilterChain(boolean queryCount,
			Class<?> clazz, PersistencePropertyFilterChain chain) {
		StringBuilder queryBuilder = buildQueryString(queryCount, clazz);
		String queryString;
		if (chain != null && StringUtils.isNotBlank(queryString = chain.toQueryString())) 
			queryBuilder.append("WHERE ").append(queryString);
		return queryBuilder.toString();
	}
	
	/**
	 * 根据属性过滤器列表来构建指定类型的查询语句
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param queryCount 是否构建count查询语句
	 * @param clazz 实体类型
	 * @param filterList 过滤器列表
	 * @return
	 */
	public static String buildQueryStringByFilterList(boolean queryCount,
			Class<?> clazz, List<PersistencePropertyFilter> filterList) {
		StringBuilder queryBuilder = buildQueryString(queryCount, clazz);
		if (CollectionUtils.isNotEmpty(filterList)) {
			PropertyFilterChain chain = new PropertyFilterChain();
			for (PersistencePropertyFilter filter : filterList) {
				if (filter != null)
					chain.add("worin_property_filter_group", filter);
			}
			String queryString = chain.toQueryString();
			if (StringUtils.isNotBlank(queryString))
				queryBuilder.append("WHERE ").append(queryString);
		}
		return queryBuilder.toString();
	}
	
}
