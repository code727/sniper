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

/**
 * 可扫描迭代的结果接口
 * @author  Daniele
 * @version 1.0
 */
public interface ScannableResult<T> extends Iterable<T> {
	
	/**
	 * 获取当前游标ID
	 * @author Daniele 
	 * @return
	 */
	public long getCursorId();
	
	/**
	 * 判断扫描是否完成
	 * @author Daniele 
	 * @return
	 */
	public boolean completed();
	
	/**
	 * 获取结果个数
	 * @author Daniele 
	 * @return
	 */
	public int size();
	
	/**
	 * 判断结果是否为空
	 * @author Daniele 
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * 判断结果是否不为空
	 * @author Daniele 
	 * @return
	 */
	public boolean isNotEmpty();
	
}
