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
 * Create Date : 2015年11月13日
 */

package org.workin.support.parameter.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 参数名适配器简单实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SimpleNameAdapter<K> implements NameAdapter<K> {
	
	private Map<K, NameAdaptationRule<K>> adapterMap;
	
	public SimpleNameAdapter(List<NameAdaptationRule<K>> rules) {
		AssertUtils.assertTrue(CollectionUtils.isNotEmpty(rules), "NameAdaptationRule list must not be null or empty");
		this.adapterMap = MapUtils.newHashMap();
		for (NameAdaptationRule<K> rule : rules) {
			this.adapterMap.put(rule.getOriginalName(), rule);
		}
	}

	@Override
	public K getAdaptationName(K originalName) {
		NameAdaptationRule<K> rule = this.adapterMap.get(originalName);
		return rule != null ? rule.getAdaptationName() : null;
	}

	@Override
	public boolean hasAdaptationName(K originalName) {
		return StringUtils.isNotEmpty(StringUtils.toString(getAdaptationName(originalName)));
	}

	@Override
	public boolean isOriginalName(K name) {
		return this.adapterMap.containsKey(name);
	}

	@Override
	public Set<K> getAdaptationNames() {
		Set<K> adaptationNames = CollectionUtils.newHashSet();
		Collection<NameAdaptationRule<K>> rules = this.adapterMap.values();
		for (NameAdaptationRule<K> rule : rules) {
			// 只加入不重复的适配名
			if (!adaptationNames.contains(rule.getAdaptationName()))
				adaptationNames.add(rule.getAdaptationName());
		}
		return adaptationNames;
	}

	@Override
	public Set<K> getOriginalNames() {
		return this.adapterMap.keySet();
	}

}
