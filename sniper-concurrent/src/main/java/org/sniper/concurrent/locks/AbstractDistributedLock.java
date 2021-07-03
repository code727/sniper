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
 * Create Date : 2019年11月17日
 */

package org.sniper.concurrent.locks;

import org.sniper.commons.util.StringUtils;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractDistributedLock<K> implements KeyspaceLock<K> {
	
	/** 锁对象实例ID */
	protected final String instanceId;
	
	protected AbstractDistributedLock(String instanceId) {
		this.instanceId = (StringUtils.isNotBlank(instanceId) ? instanceId : StringUtils.unsignedUUID());
	}
	
//	protected 

}
