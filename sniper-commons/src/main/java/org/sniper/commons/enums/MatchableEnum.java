/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-27
 */

package org.sniper.commons.enums;

/**
 * 可匹配的枚举接口
 * @author  Daniele
 * @version 1.0
 */
public interface MatchableEnum<K> {
	
	/**
	 * 判断指定的键是否匹配当前枚举对象
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public boolean match(K key);

	/**
	 * 判断指定的名称是否匹配当前枚举对象
	 * @author Daniele 
	 * @param name
	 * @return 
	 */
	public boolean match(String name);

}
