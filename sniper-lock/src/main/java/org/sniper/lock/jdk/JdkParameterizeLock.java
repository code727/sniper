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

package org.sniper.lock.jdk;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import org.sniper.commons.timer.DefaultExpiredTimer;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.lock.Lock;
import org.sniper.lock.ParameterizeLock;

/**
 * JDK参数化锁实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class JdkParameterizeLock<P> extends DefaultExpiredTimer implements ParameterizeLock<P> {
	
	private static final int DEFAULT_LOCKSIZE = 16;
	
	private final Lock[] locks;
	
	public JdkParameterizeLock() {
		this(DEFAULT_LOCKSIZE);
	}
	
	public JdkParameterizeLock(int lockSize) {
		this(0, lockSize);
	}
	
	public JdkParameterizeLock(long expireTime) {
		this(expireTime, DEFAULT_LOCKSIZE);
	}
	
	public JdkParameterizeLock(long expireTime, int lockSize) {
		this(expireTime, TimeUnit.SECONDS, lockSize);
	}
	
	public JdkParameterizeLock(long expireTime, TimeUnit timeUnit) {
		this(expireTime, timeUnit, DEFAULT_LOCKSIZE);
	}
	
	public JdkParameterizeLock(long expireTime, TimeUnit timeUnit, int lockSize) {
		AssertUtils.assertTrue(lockSize > 0, "lock size must greater than 0");
		this.locks = new JdkReentrantLock[lockSize];
		for (int i = 0; i < lockSize; i++) {
			this.locks[i] = new JdkReentrantLock(expireTime, timeUnit);
		}
	}
	
	public JdkParameterizeLock(Lock lock) {
		this(new Lock[] { lock });
	}
	
	public JdkParameterizeLock(Lock[] locks) {
		AssertUtils.assertNotEmpty(locks, "Locks must not be empty");
		this.locks = locks;
	}

	@Override
	public void lock(P parameter) {
		Lock lock = getLockByParameter(parameter);
		AssertUtils.assertNotNull(lock, MessageFormat.format("Lock fail by parameter {0}", 
				ObjectUtils.toSafeNullString(parameter)));
		lock.lock();
	}
	
	@Override
	public boolean tryLock(P parameter) {
		Lock lock = getLockByParameter(parameter);
		return lock != null && lock.tryLock();
	}

	@Override
	public void unlock(P parameter) {
		Lock lock = getLockByParameter(parameter);
		AssertUtils.assertNotNull(lock, MessageFormat.format("Lock fail by parameter {0}", 
				ObjectUtils.toSafeNullString(parameter)));
		lock.unlock();
	}
	
	@Override
	public boolean tryUnlock(P parameter) {
		Lock lock = getLockByParameter(parameter);
		return lock != null && lock.tryUnlock();
	}

	@Override
	public boolean isLocked(P parameter) {
		Lock lock = getLockByParameter(parameter);
		return lock != null && lock.isLocked();
	}
	
	/**
	 * 根据参数获取对应的锁对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @return
	 */
	private Lock getLockByParameter(P parameter) {
		int hashCode = ObjectUtils.hashCode(parameter);
		return locks[Math.abs(hashCode % locks.length)];
	}
	
}
