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
 * Create Date : 2018-11-1
 */

package org.sniper.support;

/**
 * 命名空间标识接口
 * @author  Daniele
 * @version 1.0
 */
public interface Namespace<K> {
	
	/**
	 * 获取全局默认的空间ID
	 * @author Daniele 
	 * @return
	 */
	public K getDefaultSpaceId();
		
}
