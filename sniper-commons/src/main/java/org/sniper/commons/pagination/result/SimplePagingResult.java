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
 * Create Date : 2015-1-14
 */

package org.sniper.commons.pagination.result;

import java.util.List;

import org.sniper.commons.pagination.PagingResult;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.NumberUtils;

/**
 * 简单的分页结果实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SimplePagingResult<T> implements PagingResult<T> {
	
	private static final long serialVersionUID = -5209318203792180797L;

	/** 返回的数据列表 */
	private List<T> data;
	
	/** 符合分页条件的记录总数 */
	private long total;

	@Override
	public List<T> getData() {
		return this.data;
	}

	@Override
	public void setData(List<T> data) {
		this.data = data;
	}

	@Override
	public long getTotal() {
		int dataSize = CollectionUtils.size(data);
		return this.total > dataSize ? this.total : dataSize;
	}

	@Override
	public void setTotal(long total) {
		this.total = NumberUtils.minLimit(total, 0);
	}
		
}
