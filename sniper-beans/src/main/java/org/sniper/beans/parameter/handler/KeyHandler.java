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
 * Create Date : 2015-7-1
 */

package org.sniper.beans.parameter.handler;

import java.util.Map;

/**
 * 键处理器接口
 * @author  Daniele
 * @version 1.0
 */
public interface KeyHandler {
	
	/**
	 * 将指定的键-值对PUT到映射集中
	 * @author Daniele 
	 * @param map
	 * @param key
	 * @param value
	 */
	public <K, V, K1 extends K, V1 extends V> void put(Map<K, V> map, K1 key, V1 value);
	
	/**
	 * 批量PUT到源映射集中
	 * @author Daniele 
	 * @param map
	 * @param puted
	 */
	public <K, V, K1 extends K, V1 extends V> void putAll(Map<K, V> map, Map<K1, V1> puted);

}
