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
 * Create Date : 2019年1月17日
 */

package org.sniper.nosql.redis.option;

import org.sniper.nosql.redis.enums.ZStoreAggregate;

/**
 * 有序集合存储可选项接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ZStoreOptional {
	
	/** 默认的乘法因子值 */
	public static final int DEFAULT_WEIGHT = 1;
	
	/** 有序集合默认聚合方式 */
	public static final ZStoreAggregate DEFAULT_AGGREGATE = ZStoreAggregate.SUM;
	
	/**
	 * 获取乘法因子数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int[] getWeights();

	/**
	 * 有序集合聚合方式枚举
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public ZStoreAggregate getAggregate();

}
