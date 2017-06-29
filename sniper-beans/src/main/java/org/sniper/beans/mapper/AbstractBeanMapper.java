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
 * Create Date : 2015-11-15
 */

package org.sniper.beans.mapper;

import java.util.Set;

import org.sniper.beans.DefaultTypedBean;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ReflectionUtils;

/**
 * Java Bean映射器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractBeanMapper<S> extends DefaultTypedBean implements BeanMapper<S>, ConfigurableMapper {
	
	/** 映射器规则集 */
	protected Set<MapperRule> mapperRules;
	
	/** 是否自动进行规则以外的映射处理 */
	private boolean autoMapping = true;
	
	@Override
	public Set<MapperRule> getMapperRules() {
		return mapperRules;
	}

	@Override
	public void setMapperRules(Set<MapperRule> mapperRules) {
		this.mapperRules = mapperRules;
	}
	
	@Override
	public boolean isAutoMapping() {
		return autoMapping;
	}

	@Override
	public void setAutoMapping(boolean autoMapping) {
		this.autoMapping = autoMapping;
	}
	
	@Override
	public <T> T mapping(S source) throws Exception {
		return mapping(source, mapperRules);
	}
	
	public <T> T mapping(S source, Class<T> type) throws Exception {
		return (T) mapping(source, mapperRules, type);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T mapping(S source, Set<MapperRule> mapperRules) throws Exception {
		return (T) mapping(source, mapperRules, getType());
	}
	
	@Override
	public <T> T mapping(S source, Set<MapperRule> mapperRules, Class<T> type) throws Exception {
		AssertUtils.assertNotNull(type, "Mapped bean type must not be null");
		return doMapping(source, mapperRules, type);
	}
		
	/**
	 * 执行映射处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param source
	 * @param mapperRules
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T doMapping(S source, Set<MapperRule> mapperRules, Class<T> type) throws Exception;

	/**
	 * 映射出指定类型的目标Bean对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected <T> T createMappedBean(Class<T> beanType) throws Exception {
		return ReflectionUtils.newInstance(beanType);
	}
	
}
