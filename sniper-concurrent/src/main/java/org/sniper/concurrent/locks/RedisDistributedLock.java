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
 * Create Date : 2018-6-26
 */

package org.sniper.concurrent.locks;

/**
 * Redis分布式锁实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisDistributedLock<P> implements ParameterizeLock<P> {
	
//	private final RedisCommands redisCommands;
	
	@Override
	public void lock(P parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock(P parameter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock(P parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryUnlock(P parameter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLocked(P parameter) {
		// TODO Auto-generated method stub
		return false;
	}

}
