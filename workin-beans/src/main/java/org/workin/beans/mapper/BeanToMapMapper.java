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

package org.workin.beans.mapper;

import java.util.Map;
import java.util.Set;

import org.workin.beans.BeanUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;

/**
 * Java Bean对象与Map对象之间的映射转换
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BeanToMapMapper<S, V> extends AbstractMapper<S, Map<String, V>> {
	
	protected BeanToMapMapper() {
		this(null);
	}
	
	protected BeanToMapMapper(Set<MapperRule> mapperRules) {
		this.mapperRules = mapperRules;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, V> mapping(S source) throws Exception {
		if (source == null)
			return null;

		Map<String, V> result = MapUtils.newLinkedHashMap();
		if (CollectionUtils.isNotEmpty(mapperRules)) {
			for (MapperRule rule : mapperRules) 
				result.put(rule.getMappedName(), (V) BeanUtils.get(source, rule.getOriginalName()));
		} else 
			result.putAll((Map<String, V>) BeanUtils.create(source));
			
		return result;
	}
	
}