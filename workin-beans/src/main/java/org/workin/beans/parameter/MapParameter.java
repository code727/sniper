/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-5-24
 */

package org.workin.beans.parameter;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;

/**
 * Map参数实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapParameter<K, V> implements Parameter<K, V> {
	
	/** 参数映射集 */
	protected Map<K, V> parameters;
	
	public MapParameter() {
		this((Map<K, V>) null);
	}
	
	public MapParameter(Parameter<K, V> parameter) {
		this(parameter != null ? parameter.getParameters() : null);
	}
	
	public MapParameter(Map<K, V> parameters) {
		this.parameters = MapUtils.newLinkedHashMap(parameters);
	}
	
	@Override
	public void setParameters(Map<K, V> parameters) {
		this.parameters.putAll(parameters);
	}

	@Override
	public Map<K, V> getParameters() {
		return this.parameters;
	}

	@Override
	public void add(K name, V value) {
		this.parameters.put(name, value);
	}

	@Override
	public V getValue(K name) {
		return this.parameters.get(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V1> V1 getValue(K name, Class<V1> clazz) {
		return (V1) this.getValue(name);
	}
	
	@Override
	public Set<K> getNames() {
		return this.parameters.keySet();
	}

	@Override
	public List<V> getValues() {
		return CollectionUtils.newArrayList(this.parameters.values());
	}

	@Override
	public void remove(K name) {
		this.parameters.remove(name);
	}

	@Override
	public void clear() {
		this.parameters.clear();
	}

	@Override
	public int size() {
		return this.parameters.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean isNotEmpty() {
		return !isEmpty();
	}
	
	@Override
	public String toString() {
		return this.parameters.toString();
	}
	
}
