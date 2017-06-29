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
 * Create Date : 2015-11-17
 */

package org.sniper.commons.enums;

/**
 * 可嵌套的枚举对象抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractNestableEnums<K1, K2, V2> extends
		AbstractEnums<K1, Enums<K2, V2>> implements NestableEnums<K1, K2, V2> {

	protected AbstractNestableEnums(K1 key, Enums<K2, V2> value) {
		super(key, value);
	}

	@Override
	public K2 getNestedKey(K1 key) {
		Enums<K2, V2> nested = getValue();
		return nested.getKey();
	}

	@Override
	public V2 getNestedValue(K1 key) {
		Enums<K2, V2> nested = getValue();
		return nested.getValue();
	}

}
