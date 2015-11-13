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

package org.workin.support.parameter.adapter;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.support.parameter.ConcurrentParameter;

/**
 * @description 可适配的并发泛型参数实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AdaptableConcurrentParameter<K, V> extends
		ConcurrentParameter<K, V> implements AdaptableParameter<K, V> {
	
	private NameAdapter<K> nameAdapter;

	@Override
	public void setNameAdapter(NameAdapter<K> nameAdapter) {
		this.nameAdapter = nameAdapter;
	}
	
	@Override
	public NameAdapter<K> getNameAdapter() {
		return this.nameAdapter;
	}
	
	@Override
	public void add(K name, V value) {
		// 如果添加的参数名称是原始的，则同时添加一个"原始名-值"和一个"适配名-值备份"映射集，否则抛出异常
		AssertUtils.assertTrue(this.nameAdapter.isOriginalName(name), 
				"Parameter [" + name + "] is a adaptation name, can not be directly add.");
		super.add(name, value);	
		super.add(this.nameAdapter.getAdaptationName(name), value);
	}

	@Override
	public V getAdaptationValue(K adaptationName) {
		return !this.nameAdapter.isOriginalName(adaptationName) ? super.getValue(adaptationName) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V1> V1 getAdaptationValue(K adaptationName, Class<V1> clazz) {
		return (V1) getAdaptationValue(adaptationName);
	}
	
	@Override
	public Map<K, V> getAdaptationParameters() {
		Map<K, V> parameters = getParameters();
		Set<K> names = parameters.keySet();
		for (K name : names) {
			// 删除是原始的参数映射
			if (this.nameAdapter.isOriginalName(name))
				parameters.remove(name);
		}
		return parameters;
	}
	
	@Override
	public Set<K> getAdaptationNames() {
		return this.nameAdapter.getAdaptationNames();
	}

	@Override
	public List<V> getAdaptationValues() {
		List<V> adaptationValues = CollectionUtils.newArrayList();
		Set<K> names = getNames();
		for (K name : names) {
			if (!this.nameAdapter.isOriginalName(name))
				adaptationValues.add(getParameters().get(name));
		}
		return adaptationValues;
	}

	@Override
	public V getOriginalValue(K originalName) {
		return this.nameAdapter.isOriginalName(originalName) ? super.getValue(originalName) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V1> V1 getOriginalValue(K originalName, Class<V1> clazz) {
		return (V1) getOriginalValue(originalName);
	}

	@Override
	public Map<K, V> getOriginalParameters() {
		Map<K, V> parameters = getParameters();
		Set<K> names = parameters.keySet();
		for (K name : names) {
			// 删除不是原始的参数映射
			if (!this.nameAdapter.isOriginalName(name))
				parameters.remove(name);
		}
		return parameters;
	}

	@Override
	public Set<K> getOriginalNames() {
		return this.nameAdapter.getOriginalNames();
	}

	@Override
	public List<V> getOriginalValues() {
		List<V> originalValues = CollectionUtils.newArrayList();
		Set<K> names = getNames();
		for (K name : names) {
			if (this.nameAdapter.isOriginalName(name))
				originalValues.add(getParameters().get(name));
		}
		return originalValues;
	}

}
