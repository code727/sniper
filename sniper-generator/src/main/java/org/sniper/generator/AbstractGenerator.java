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
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractGenerator<T> implements Generator<T> {
	
	/**
	 * 检查批量生成的个数是否合法
	 * @author Daniele 
	 * @param count
	 */
	protected void checkBatchCount(int count) {
		AssertUtils.assertTrue(count > 0, String.format(
				"%s batch generation count '%d' must greater than 0", this.getClass().getName(), count));
	}
	
}
