/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-28
 */

package org.sniper.commons.request;

import java.util.LinkedHashSet;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;

/**
 * 多值排序请求
 * @author  Daniele
 * @version 1.0
 */
public class MultiSortRequest implements MultiSort {

	private static final long serialVersionUID = 2716306600512459816L;
	
	/** 排序请求集 */
	private LinkedHashSet<SortRequest> sorts;
	
	@Override
	public LinkedHashSet<SortRequest> getSorts() {
		return sorts;
	}

	@Override
	public void setSorts(LinkedHashSet<SortRequest> sorts) {
		this.sorts = sorts;
	}

	@Override
	public MultiSort add(SortRequest request) {
		AssertUtils.assertNotNull(request, "Sort request must not be null");
		if (this.sorts == null)
			this.sorts = CollectionUtils.newLinkedHashSet();
		
		this.sorts.add(request);
		return this;
	}
	
	@Override
	public MultiSort clear() {
		if (this.sorts != null)
			this.sorts.clear();
		
		return this;
	}

}
