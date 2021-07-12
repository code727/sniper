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

package org.sniper.support.operation.logic;

/**
 * 小于等于逻辑运算操作
 * @author  Daniele
 * @version 1.0
 */
public class LessThanEqualsLogicOperation extends AbstractLogicOperation<Object, Object> {

	@Override
	public boolean execute(Object value1, Object value2) {
		if (value1 == null || value2 == null)
			return value1 == value2;
		
		try {
			return toBigDecimal(value1).compareTo(toBigDecimal(value2)) < 1;
		} catch (NumberFormatException e) {
			return false;
		}
	}
		
}
