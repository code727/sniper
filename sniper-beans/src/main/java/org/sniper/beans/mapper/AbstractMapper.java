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

package org.sniper.beans.mapper;

import java.util.Set;

/**
 * 抽象映射器
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractMapper<S, T> implements Mapper<S, T>, ConfigurableMapper {
	
	/** 映射器规则集 */
	protected Set<MapperRule> mapperRules;
	
	/** 是否自动进行规则以外的映射处理 */
	public boolean autoMapping = true;
	
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
	public T mapping(S source) throws Exception {
		return mapping(source, mapperRules);
	}
	
}
