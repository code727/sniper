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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.sniper.commons.timer.ExpirationTime;
import org.sniper.lock.Lock;

/**
 * JDK重入锁实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JdkReentrantLock extends ExpirationTime implements Lock {
	
	private final ReentrantLock lock;
	
	public JdkReentrantLock() {
		this(false);
	}
	
	public JdkReentrantLock(boolean fair) {
		this(0, false);
	}
	
	public JdkReentrantLock(long expireTime) {
		this(expireTime, false);
	}
	
	public JdkReentrantLock(long expireTime, boolean fair) {
		this(expireTime, TimeUnit.SECONDS, false);
	}
	
	public JdkReentrantLock(long expireTime, TimeUnit timeUnit) {
		this(expireTime, timeUnit, false);
	}
	
	public JdkReentrantLock(long expireTime, TimeUnit timeUnit, boolean fair) {
		super(expireTime, timeUnit);
		this.lock = new ReentrantLock(fair);
	}

	@Override
	public void lock() {
		lock.lock();
	}
	
	@Override
	public boolean tryLock() {
		if (expireTime > 0) {
			try {
				// 在超时的时间单位内获取锁
				return lock.tryLock(expireTime, timeUnit);
			} catch (InterruptedException e) {
				return false;
			}
		}
		
		return lock.tryLock();
	}

	@Override
	public void unlock() {
		/* 解锁之前需要判断是否被锁定，否则如果没有处于锁定状态而直接解锁，
		 * 则会抛出java.lang.IllegalMonitorStateException */
		if (isLocked())
			lock.unlock();
	}
	
	@Override
	public boolean tryUnlock() {
		unlock();
		return !isLocked();
	}	
	
	@Override
	public boolean isLocked() {
		return lock.isLocked();
	}
		
}
