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

/**
 * 已索引的扫描结果
 * @author  Daniele
 * @version 1.0
 */
public interface IndexedScanResult<T> extends ScannableResult<T> {
	
	/**
	 * 获取指定下标位的结果
	 * @author Daniele 
	 * @param index
	 * @return
	 */
	public T get(int index);
	
	/**
	 * 判断指定的对象是否存在于结果中
	 * @author Daniele 
	 * @param obj
	 * @return
	 */
	public boolean contains(Object obj);
	
	/**
	 * 判断指定的集合是否存在于结果中
	 * @author Daniele 
	 * @param c
	 * @return
	 */
	public boolean containsAll(Collection<?> c);

}
