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
 * Create Date : 2018-5-8
 */

package org.sniper.generator;

import java.util.Map;

/**
 * 可缓存的参数化生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface CacheableParameterizeGenerator<K, V, P, T> extends ParameterizeGenerator<P, T> {
	
	/**
	 * 获取缓存对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<K, V> getCache();
	
	/**
	 * 获取缓存大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getCacheSize();

	/**
	 * 设置缓存大小
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param cacheSize
	 */
	public void setCacheSize(int cacheSize);

}
