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
 * Create Date : 2015-11-13
 */

package org.workin.beans.mapper;

import java.util.Set;

/**
 * 抽象映射器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractMapper<S, T> implements Mapper<S, T> {
	
	/** 映射器规则集 */
	protected Set<MapperRule> mapperRules;
	
	protected AbstractMapper() {
		this(null);
	}
	
	protected AbstractMapper(Set<MapperRule> mapperRules) {
		this.mapperRules = mapperRules;
	}
	
	@Override
	public Set<MapperRule> getMapperRules() {
		return mapperRules;
	}

	@Override
	public void setMapperRules(Set<MapperRule> mapperRules) {
		this.mapperRules = mapperRules;
	}
	
}
