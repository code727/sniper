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
 * Create Date : 2019年11月16日
 */

package org.sniper.concurrent;

import org.sniper.concurrent.locks.KeyspaceLock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 键空间锁对象示例
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class KeyspaceLockDemo {
	
	@Autowired
	private KeyspaceLock<String> parameterizeLock;
	
	private int i;
	
	public KeyspaceLock<String> getParameterizeLock() {
		return parameterizeLock;
	}

	public void setParameterizeLock(KeyspaceLock<String> parameterizeLock) {
		this.parameterizeLock = parameterizeLock;
	}

	public int add(String key) {
		parameterizeLock.lock(key);
		parameterizeLock.lock(key);
		try {
			return ++i;
		} finally {
			parameterizeLock.unlock(key);
			parameterizeLock.unlock(key);
		}
	}

}
