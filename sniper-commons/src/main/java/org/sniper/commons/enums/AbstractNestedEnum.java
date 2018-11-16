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
 * 嵌套枚举抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractNestedEnum<K1, K2, V> extends
		AbstractEnum<K1, Enum<K2, V>> implements NestableEnum<K1, K2, V> {

	private static final long serialVersionUID = 9131831227687610196L;

	protected AbstractNestedEnum(K1 key, Enum<K2, V> value) {
		super(key, value);
	}
	
	@Override
	public K2 getNestedKey() {
		return value.getKey();
	}

	@Override
	public V getNestedValue() {
		return value.getValue();
	}
	
}
