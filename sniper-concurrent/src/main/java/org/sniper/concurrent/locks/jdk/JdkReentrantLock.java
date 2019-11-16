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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.sniper.concurrent.locks.Lock;

/**
 * JDK重入锁实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JdkReentrantLock implements Lock {
	
	private final ReentrantLock lock;
	
	public JdkReentrantLock() {
		this(false);
	}
	
	public JdkReentrantLock(boolean fair) {
		this.lock = new ReentrantLock(fair);
	}
	
	@Override
	public void lock() {
		lock.lock();
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		lock.lockInterruptibly();
	}
	
	@Override
	public boolean tryLock() {
		return lock.tryLock();
	}
	
	@Override
	public boolean tryLock(long acquireTime, TimeUnit unit) {
		try {
			/* 在单位时间范围内尝试加锁操作，如果成功加锁立即返回true
			 * 如果超时后未成功加锁，则返回false */
			return lock.tryLock(acquireTime, unit);
		} catch (InterruptedException e) {
			return false;
		}
	}
		
	@Override
	public void unlock() {
		// 如果没有处于锁定状态而直接解锁，则会抛出java.lang.IllegalMonitorStateException 
		lock.unlock();
	}
	
	@Override
	public Condition newCondition() {
		return lock.newCondition();
	}
	
	@Override
	public boolean isLocked() {
		return lock.isLocked();
	}
		
}
