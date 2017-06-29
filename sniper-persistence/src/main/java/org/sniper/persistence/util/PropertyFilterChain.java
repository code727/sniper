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
 * Create Date : 2015-2-12
 */

package org.sniper.persistence.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 持久化实体属性过滤器链
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PropertyFilterChain implements PersistencePropertyFilterChain {
	
	/** 有序的过滤器成员组 */
	private Map<String, List<PersistencePropertyFilter>> groups = MapUtils.newLinkedHashMap();
	
	/** 当前组与下一组之间的谓词关系 */
	private Map<String, Predicate> groupPredicate = MapUtils.newHashMap();
	
	public PersistencePropertyFilterChain add(String name, PersistencePropertyFilter filter) {
		AssertUtils.assertNotBlank(name, "Property filter group name can not be null or blank.");
		AssertUtils.assertNotNull(filter, "Property filter can not be null.");
		
		List<PersistencePropertyFilter> group = this.groups.get(name);
		if (group == null)
			group = CollectionUtils.newArrayList();
		group.add(filter);
		this.groups.put(name, group);
		return this;
	}
	
	public PersistencePropertyFilterChain remove(String name) {
		this.groups.remove(name);
		// 同时删除当前组的谓词关系
		this.groupPredicate.remove(name);
		return this;
	}
	
	public PersistencePropertyFilterChain remove(PersistencePropertyFilter filter) {
		if (filter != null && this.groups.size() > 0) {
			Collection<List<PersistencePropertyFilter>> members = this.groups.values();
			for (List<PersistencePropertyFilter> filters : members)
				filters.remove(filter);
		}
		return this;
	}
	
	public PersistencePropertyFilterChain remove(String name, PersistencePropertyFilter filter) {
		if (filter != null && this.groups.size() > 0) {
			List<PersistencePropertyFilter> member = this.groups.get(name);
			if (member != null)
				member.remove(filter);
		}
		return this;
	}
	
	public PersistencePropertyFilterChain setPredicate(String name, String predicate) {
		AssertUtils.assertNotBlank(name, "Property filter group name can not be null or blank.");
		AssertUtils.assertNotBlank(predicate, "Property filter group predicate can not be null or blank.");
		
		try {
			predicate = predicate.trim();
			this.groupPredicate.put(name, Enum.valueOf(Predicate.class, predicate.toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unknow predicate \"" + predicate + 
					"\", support predicate types [" + ArrayUtils.join(Predicate.values(), ",") + "].");
		}
		return this;
	}
	
	public PersistencePropertyFilterChain setPredicate(String name, Predicate predicate) {
		AssertUtils.assertNotBlank(name, "Property filter group name can not be null or blank.");
		AssertUtils.assertNotNull(predicate, "Property filter group predicate can not be null.");
		
		this.groupPredicate.put(name, predicate);
		return this;
	}
	
	public List<PersistencePropertyFilter> getGroup(String name) {
		return this.groups.get(name);
	}
	
	public Predicate getPredicate(String name) {
		Predicate predicate = this.groupPredicate.get(name);
		// 未设置时，默认当前组与下一组之间的关系为"AND"
		return predicate != null ? predicate : Predicate.AND;
	}
	
	public PersistencePropertyFilterChain removePredicate(String name) {
		this.groupPredicate.remove(name);
		return this;
	}
	
	/**
	 * 按顺序将整条链中所有组内过滤器中的属性-值拼接成查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String toQueryString() {
		if (this.groups.size() == 0)
			return StringUtils.EMPTY_STRING;
		
		StringBuilder query = new StringBuilder();
		List<String> names = CollectionUtils.newArrayList(this.groups.keySet());
		query.append(buildeGroupQuery(names));
		return query.toString();
	}
		
	/**
	 * 根据名称列表构建出组内所有过滤器成员的查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param names
	 * @return
	 */
	private String buildeGroupQuery(List<String> names) {
		int max = names.size() - 1;
		
		StringBuilder groupQuery = new StringBuilder();
		
		/* 多组时，先拼接出最后一个组之前的查询字符串 */
		if (max > 0) {
			for (int i = 0; i < max; i++) {
				String name = names.get(i);
				List<PersistencePropertyFilter> group = this.getGroup(name);
				int groupMax = group.size() - 1;
				groupQuery.setLength(0);
				groupQuery.append("(");
				for (int j = 0; j < groupMax; j++) 
					groupQuery.append(group.get(j)).append(StringUtils.SPACE_STRING)
						.append(group.get(j).getPredicate()).append(StringUtils.SPACE_STRING);
						
				// 忽略当前组最后一个属性的谓词，并添加组与组之间的谓词
				groupQuery.append(group.get(groupMax)).append(")").append(StringUtils.SPACE_STRING)
					.append(this.getPredicate(name)).append(StringUtils.SPACE_STRING);
			}
		}
		groupQuery.append(buildeLastGroupQuery(names));
		return groupQuery.toString();
	}
	
	/**
	 * 构建名称列表中对应的最后一组的查询字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param names
	 * @return
	 */
	private String buildeLastGroupQuery(List<String> names) {
		int max = names.size() - 1;
		String name = names.get(max);
		StringBuilder groupQuery = new StringBuilder();
		List<PersistencePropertyFilter> group = getGroup(name);
		int groupMax = group.size() - 1;
		groupQuery.append("(");
		for (int j = 0; j < groupMax; j++) 
			groupQuery.append(group.get(j)).append(StringUtils.SPACE_STRING)
				.append(group.get(j).getPredicate()).append(StringUtils.SPACE_STRING);
		
		groupQuery.append(group.get(groupMax)).append(")");
		return groupQuery.toString();
	}
	
}
