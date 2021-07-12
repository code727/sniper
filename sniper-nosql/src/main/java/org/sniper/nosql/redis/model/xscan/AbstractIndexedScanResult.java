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
 * Create Date : 2019-3-12
 */

package org.sniper.nosql.redis.model.xscan;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.sniper.commons.util.CollectionUtils;

/**
 * 已索引的扫描结果抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractIndexedScanResult<T> extends AbstractScannableResult<T> implements IndexedScanResult<T> {
	
	/** 扫描出的结果项 */
	protected final List<T> items;
	
	protected AbstractIndexedScanResult(long cursorId, List<T> items) {
		super(cursorId);
		this.items = (items != null ? items : CollectionUtils.newArrayList());
	}
	
	@Override
	public T get(int index) {
		return this.items.get(index);
	}

	@Override
	public int size() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		return this.items.isEmpty();
	}

	@Override
	public boolean contains(Object obj) {
		return this.items.contains(obj);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.items.containsAll(c);
	}
	
	@Override
	public Iterator<T> iterator() {
		return this.items.iterator();
	}

}
