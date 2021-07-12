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
 * Create Date : 2018-6-25
 */

package org.sniper.concurrent.locks.jdk;

import java.util.concurrent.TimeUnit;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.concurrent.locks.Lock;
import org.sniper.concurrent.locks.KeyspaceLock;

/**
 * 键空间锁默认实现类
 * @author  Daniele
 * @version 1.0
 */
public final class DefaultKeyspaceLock<K> implements KeyspaceLock<K> {
	
	private static final int DEFAULT_LOCKSIZE = 16;
	
	private final Lock[] locks;
	
	public DefaultKeyspaceLock() {
		this(DEFAULT_LOCKSIZE);
	}
	
	public DefaultKeyspaceLock(int lockSize) {
		AssertUtils.assertTrue(lockSize > 0, "lock size must greater than 0");
		this.locks = new JdkReentrantLock[lockSize];
		for (int i = 0; i < lockSize; i++) {
			this.locks[i] = new JdkReentrantLock();
		}
	}
			
	public DefaultKeyspaceLock(Lock lock) {
		this(new Lock[] { lock != null ? lock : new JdkReentrantLock()});
	}
	
	public DefaultKeyspaceLock(Lock[] locks) {
		AssertUtils.assertNotEmpty(locks, "Locks must not be empty");
		this.locks = locks;
	}

	@Override
	public void lock(K key) {
		Lock lock = getLockByKey(key);
		lock.lock();
	}
	
	@Override
	public boolean tryLock(K key) {
		Lock lock = getLockByKey(key);
		return lock.tryLock();
	}
	
	@Override
	public boolean tryLock(K key, long acquireTime, TimeUnit unit) {
		Lock lock = getLockByKey(key);
		try {
			return lock.tryLock(acquireTime, unit);
		} catch (InterruptedException e) {
			return false;
		}
	}

	@Override
	public void unlock(K key) {
		Lock lock = getLockByKey(key);
		lock.unlock();
	}
	
	@Override
	public boolean isLocked(K key) {
		Lock lock = getLockByKey(key);
		return lock.isLocked();
	}
	
	/**
	 * 获取键对应的锁对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	private Lock getLockByKey(K key) {
		int hashCode = ObjectUtils.hashCode(key);
		return locks[Math.abs(hashCode % locks.length)];
	}
	
}
