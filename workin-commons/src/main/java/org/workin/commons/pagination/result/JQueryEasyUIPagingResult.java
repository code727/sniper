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

package org.workin.commons.pagination.result;

import java.util.List;

/**
 * JQuery EasyUI分页结果类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JQueryEasyUIPagingResult<T> extends SimplePagingResult<T> {
	
	private static final long serialVersionUID = -4715294078489576653L;

	/**
	 * 重写父类方法：</br>
	 * 1.JQuery EasyUI分页器是用"rows"字段返回数据列表的；</br>
	 * 2.这里需将"data"字段返回的数据清空，以防止JSON序列化时，将多余的data字段值也进行处理；</br>
	 * 3.此时返回的结果类似于{"data":null,"total":total,"rows":rowData}，data字段仍然存在，
	 *   如果要严格控制JQuery EasyUI分页结果的内容，可以在序列化结果前过滤掉data字段</br>
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	@Override
	public List<T> getData() {
		return null;
	}
	
	/**
	 * 获取分页结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<T> getRows() {
		// 从父类的分页结果列表中获取
		return super.getData();
	}

	/**
	 * 设置分页结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param rows
	 */
	public void setRows(List<T> rows) {
		super.setData(rows);
	}
	
}
