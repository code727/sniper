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
 * Create Date : 2015年5月13日
 */

package org.workin.persistence.datasource.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.PatternMatchUtils;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.MapUtils;
import org.workin.persistence.datasource.selector.MultipleDataSourceSelector;

/**
 * @description 方法和多数据源关系管理实现类，主要维护方法名称与多个数据源之间的关系
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MethodAndMultipleDataSourceManager extends MethodAndDataSourceManager {
	
	/** 维护方法名称模式与多个数据源名称之间的关系 */
	private Map<String, String[]> patternAndNames;
	
	/** 多数据源选择器 */
	private MultipleDataSourceSelector multipleDataSourceSelector;
	
	public void setMultipleDataSourceSelector(
			MultipleDataSourceSelector multipleDataSourceSelector) {
		this.multipleDataSourceSelector = multipleDataSourceSelector;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		if (multipleDataSourceSelector == null)
			throw new IllegalArgumentException("Property 'multipleDataSourceSelector' is required");
		
		Iterator<Entry<String, String>> iterator = super
				.getMethodPatternAndDataSourceName().entrySet().iterator();
		
		this.patternAndNames = MapUtils.newHashMap();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			this.patternAndNames.put(entry.getKey(), ArrayUtils.rbte(entry.getValue().split(",")));
		}
	}
	
	@Override
	public String getDataSourceName(String methodName) {
		for (String pattern : super.getMethodPattern()) {
			if (PatternMatchUtils.simpleMatch(pattern, methodName)) {
				String[] sourceNames = this.patternAndNames.get(pattern);
				return sourceNames.length != 1 ? this.multipleDataSourceSelector
						.select(sourceNames) : sourceNames[0];
			}
		}
		
		return null;
	}
	
}
