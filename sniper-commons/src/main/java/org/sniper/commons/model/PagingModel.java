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

package org.sniper.commons.model;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.NumberUtils;

/**
 * 基本的分页结果模型
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PagingModel<T> implements PagingResult<T> {
	
	private static final long serialVersionUID = -5209318203792180797L;

	/** 返回的数据列表 */
	private List<T> data;
	
	/** 符合分页条件的记录总数 */
	private long count;

	@Override
	public List<T> getData() {
		return this.data;
	}

	@Override
	public void setData(List<T> data) {
		this.data = data;
	}
	
	@Override
	public long getCount() {
		return NumberUtils.minLimit(count, CollectionUtils.size(data));
	}

	@Override
	public void setCount(long count) {
		// 如果count小于data列表长度，说明count值不符合逻辑，例如：
		// 1.上层应用只查询数据而不查询记录总数时，count=0，显然实际的记录总数应该是data列表长度；
		// 2.上层应用设置的count值小于data列表长度时，说明count设置有误，因为符合分页查询条件的记录总数只可能大于或等于data列表长度
		// 因此针对上述两种情况，这里要限制count值不能小于data列表长度
		this.count = NumberUtils.minLimit(count, CollectionUtils.size(data));
	}
		
}
