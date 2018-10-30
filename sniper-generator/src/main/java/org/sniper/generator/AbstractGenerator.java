/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-10-26
 */

package org.sniper.generator;

import org.sniper.commons.util.AssertUtils;

/**
 * 生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractGenerator<T> implements Generator<T> {
	
	private static final String BATCH_COUNT_ERROR_MESSAGE = "%s batch generation count '%s' must greater than 0";
	
	/**
	 * 检查批量生成的个数是否合法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param count
	 */
	protected void checkBatchCount(int count) {
		AssertUtils.assertTrue(count > 0, String.format(BATCH_COUNT_ERROR_MESSAGE, this.getClass().getName(), count));
	}
	
}
