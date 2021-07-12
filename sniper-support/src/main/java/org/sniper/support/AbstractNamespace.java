/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-11-17
 */

package org.sniper.support;

import org.sniper.commons.util.AssertUtils;

/**
 * 命名空间标识抽象类
 * @author  Daniele
 * @version 1.0
 * @param <K>
 */
public abstract class AbstractNamespace<K> implements Namespace<K> {
	
	/** 全局默认的空间ID */
	protected final K defaultSpaceId;
	
	protected AbstractNamespace(K defaultSpaceId) {
		checkSpace(defaultSpaceId);
		this.defaultSpaceId = defaultSpaceId;
	}

	@Override
	public K getDefaultSpaceId() {
		return defaultSpaceId;
	}
	
	/**
	 * 检查空间的合法性
	 * @author Daniele 
	 * @param spaceId
	 */
	protected void checkSpace(K spaceId) {
		AssertUtils.assertNotNull(spaceId, "Space id must not be null");
	}
	
}
