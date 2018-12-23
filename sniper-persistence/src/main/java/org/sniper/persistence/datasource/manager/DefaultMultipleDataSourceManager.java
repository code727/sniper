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

package org.sniper.persistence.datasource.manager;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.persistence.datasource.selector.MultipleDataSourceRandomSelector;
import org.sniper.persistence.datasource.selector.MultipleDataSourceSelector;
import org.sniper.spring.beans.CheckableInitializingBeanAdapter;

/**
 * 多数据源管理默认实现类，主要维护模式与多个数据源之间的关系
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultMultipleDataSourceManager extends CheckableInitializingBeanAdapter
		implements MultipleDataSourceManager {
		
	protected final Logger logger;
	
	/** 目标模式与数据源名称之间的关系 */
	private Map<String, String> patternNames;
	
	/** 目标模式与多个数据源之间的关系 */
	private Map<String, Object[]> patternDataSources;
	
	/** 多数据源选择器 */
	private MultipleDataSourceSelector multipleDataSourceSelector;
	
	public DefaultMultipleDataSourceManager() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	public Map<String, String> getPatternNames() {
		return patternNames;
	}

	public void setPatternNames(Map<String, String> patternNames) {
		this.patternNames = patternNames;
	}

	public Map<String, Object[]> getPatternDataSources() {
		return patternDataSources;
	}

	public void setPatternDataSources(Map<String, Object[]> patternDataSources) {
		this.patternDataSources = patternDataSources;
	}

	public MultipleDataSourceSelector getMultipleDataSourceSelector() {
		return multipleDataSourceSelector;
	}

	public void setMultipleDataSourceSelector(MultipleDataSourceSelector multipleDataSourceSelector) {
		this.multipleDataSourceSelector = multipleDataSourceSelector;
	}
	
	@Override
	protected void checkProperties() {
		// patternNames和patternDataSources至少有一项不能空
		if (MapUtils.isEmpty(patternNames) && MapUtils.isEmpty(patternDataSources))
			throw new IllegalArgumentException("Property 'patternNames' or 'patternDataSources' is required");
	}
	
	@Override
	protected void init() throws Exception {
		Date start = new Date();
		
		/* 多数据源选择器为空时，默认为随机选择器 */
		if (multipleDataSourceSelector == null)
			multipleDataSourceSelector = new MultipleDataSourceRandomSelector();
		
		if (MapUtils.isEmpty(patternDataSources)) 
			patternDataSources = MapUtils.newLinkedHashMap();
		
		/* 将模式和数据源名称转换成一对多的关系 */
		for (Entry<String, String> pattern : patternNames.entrySet()) 
			this.patternDataSources.put(pattern.getKey(), ArrayUtils.rbte(pattern.getValue().split(",")));
		
		initializeLog(start);
	}
	
	/**
	 * 打印初始化log
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected void initializeLog(Date start) {
		StringBuilder message = new StringBuilder();
		for (Entry<String, Object[]> entry : patternDataSources.entrySet()) 
			message.append(entry.getKey()).append("=").append(ArrayUtils.toString(entry.getValue())).append("\n");
		
		logger.info("Success initialize pattern and datasources on {}ms:\n{}",
				DateUtils.getIntervalMillis(new Date(), start), message);
	}
	
	/**
	 * 根据参数匹配模式后获取对应的数据源
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @return
	 */
	@Override
	public Object getDataSource(Object parameter) {
		for (Entry<String, Object[]> entry : patternDataSources.entrySet()) {
			/* 模式与参数字符串匹配时，从当前模式对应的多个数据源中选择一个后返回结果 */
			if (StringUtils.simpleMatch(entry.getKey(), parameter.toString())) {
				Object[] sources = entry.getValue();
				if (ArrayUtils.isNotEmpty(sources))
					return multipleDataSourceSelector.select(sources);
			}
		}
		
		return null;		
	}
	
}
