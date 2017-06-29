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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;

/**
 * 错误重复键处理器，当put键出现重复时将报错
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ErrorDulicateKeyHandler implements KeyHandler {

	@Override
	public <K, V, K1 extends K, V1 extends V> void put(Map<K, V> map, K1 key, V1 value) {
		AssertUtils.assertTrue(!map.containsKey(key), "Dulicate map key [ " + key + "].");
		map.put(key, value);
	}

	
	@Override
	public <K, V, K1 extends K, V1 extends V> void putAll(Map<K, V> map, Map<K1, V1> puted) {
		if (MapUtils.isEmpty(puted))
			return;
		
		Iterator<Entry<K1, V1>> iterator = puted.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<K1, V1> item = iterator.next();
			this.put(map, item.getKey(), item.getValue());
		}
	}

}
