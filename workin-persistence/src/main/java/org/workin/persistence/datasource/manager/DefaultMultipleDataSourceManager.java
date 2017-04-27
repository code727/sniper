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
 * Create Date : 2015-5-13
 */

package org.workin.persistence.datasource.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.persistence.datasource.selector.MultipleDataSourceRandomSelector;
import org.workin.persistence.datasource.selector.MultipleDataSourceSelector;
import org.workin.spring.beans.CheckableInitializingBeanAdapter;

/**
 * 多数据源管理默认实现类，主要维护模式与多个数据源之间的关系
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultMultipleDataSourceManager extends CheckableInitializingBeanAdapter
		implements MultipleDataSourceManager {
		
	protected final Logger logger;
	
	/** 维护模式与数据源名称之间的关系 */
	private Map<String, String> patternAndDataSource;
	
	/** 维护模式多个数据源名称之间的关系 */
	private Map<String, String[]> patternAndNames;
	
	/** 多数据源选择器 */
	private MultipleDataSourceSelector multipleDataSourceSelector;
	
	public DefaultMultipleDataSourceManager() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	public Map<String, String> getPatternAndDataSource() {
		return patternAndDataSource;
	}

	public void setPatternAndDataSource(Map<String, String> patternAndDataSource) {
		this.patternAndDataSource = patternAndDataSource;
	}
	
	public MultipleDataSourceSelector getMultipleDataSourceSelector() {
		return multipleDataSourceSelector;
	}

	public void setMultipleDataSourceSelector(MultipleDataSourceSelector multipleDataSourceSelector) {
		this.multipleDataSourceSelector = multipleDataSourceSelector;
	}
	
	public Map<String, String[]> getPatternAndNames() {
		return patternAndNames;
	}

	@Override
	protected void checkProperties() {
		if (MapUtils.isEmpty(patternAndDataSource))
			throw new IllegalArgumentException("Property 'patternAndDataSource' is required");
	}
	
	@Override
	protected void init() throws Exception {
		/* 多数据源选择器为空时，默认为随机选择器 */
		if (multipleDataSourceSelector == null)
			this.multipleDataSourceSelector = new MultipleDataSourceRandomSelector();
		
		/* 将方法名称和数据源名称转换成一对多的关系 */
		this.patternAndNames = MapUtils.newLinkedHashMap();
		Iterator<Entry<String, String>> iterator = getPatternAndDataSource().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			this.patternAndNames.put(entry.getKey(), ArrayUtils.rbte(entry.getValue().split(",")));
		}
		
		initializeLog();
	}
	
	/**
	 * 打印初始化log
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void initializeLog() {
		StringBuilder message = new StringBuilder();
		Iterator<Entry<String, String>> iterator = patternAndDataSource.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> next = iterator.next();
			message.append(next.getKey()).append("=").append(next.getValue()).append("\n");
		}
		
		logger.info("Success initialize method pattern and datasource name:\n{}", message);
	}
	
	@Override
	public Object getDataSource(Object parameter) {
		for (String pattern : patternAndNames.keySet()) {
			// 字符串型参数匹配模式
			if (StringUtils.simpleMatch(pattern, parameter.toString())) {
				String[] sourceNames = patternAndNames.get(pattern);
				if (ArrayUtils.isNotEmpty(sourceNames))
					return multipleDataSourceSelector.select(sourceNames);
				
				continue;
			}
		}
		
		return null;
	}
	
}
