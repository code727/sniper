/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-3-13
 */

package org.sniper.nosql.redis.model.xscan;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.sniper.commons.util.MapUtils;

/**
 * 已映射的扫描结果抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractMappedScanResult<T, V> extends AbstractScannableResult<Entry<T, V>>
		implements MappedScanResult<T, V> {
	
	/** 扫描出的结果项 */
	protected final Map<T, V> mapped;
	
	protected AbstractMappedScanResult(long cursorId, Map<T, V> mapped) {
		super(cursorId);
		this.mapped = (mapped != null ? mapped : MapUtils.newLinkedHashMap());
	}
	
	@Override
	public V get(Object key) {
		return this.mapped.get(key);
	}
	
	@Override
	public Set<T> keySet() {
		return this.mapped.keySet();
	}
	
	@Override
	public Collection<V> values() {
		return this.mapped.values();
	}

	@Override
	public int size() {
		return this.mapped.size();
	}

	@Override
	public boolean isEmpty() {
		return this.mapped.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.mapped.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.mapped.containsValue(value);
	}

	@Override
	public Iterator<Entry<T, V>> iterator() {
		return this.mapped.entrySet().iterator();
	}

}
