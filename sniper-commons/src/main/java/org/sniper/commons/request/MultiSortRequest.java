/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-7-28
 */

package org.sniper.commons.request;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * 多值排序请求接口
 * @author  Daniele
 * @version 1.0
 */
public interface MultiSortRequest extends Serializable {
	
	/**
	 * 获取排序请求集
	 * @author Daniele
	 * @return
	 */
	public LinkedHashSet<SortRequest> getSorts();
	
	/**
	 * 设置排序请求集
	 * @author Daniele
	 * @param sorts
	 */
	public void setSorts(LinkedHashSet<SortRequest> sorts);
	
	/**
	 * 添加排序请求
	 * @author Daniele
	 * @param request
	 */
	public MultiSortRequest add(SortRequest request);
	
	/**
	 * 清除所有的排序请求
	 * @author Daniele
	 * @return
	 */
	public MultiSortRequest clear();
	
}
