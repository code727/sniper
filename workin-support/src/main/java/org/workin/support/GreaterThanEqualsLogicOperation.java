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
 * Create Date : 2015-1-20
 */

package org.workin.support;

import java.math.BigDecimal;

/**
 * 大于等于逻辑运算操作
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GreaterThanEqualsLogicOperation implements LogicOperation<Object, Object> {

	@Override
	public boolean execute(Object value1, Object value2) {
		if (value1 == null || value2 == null)
			return false;
		
		try {
			BigDecimal v1 = new BigDecimal(value1.toString());
			BigDecimal v2 = new BigDecimal(value2.toString());
			return v1.compareTo(v2) > -1;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
