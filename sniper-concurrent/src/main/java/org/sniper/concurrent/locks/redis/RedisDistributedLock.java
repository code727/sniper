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

package org.sniper.concurrent.locks.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.sniper.commons.util.AssertUtils;
import org.sniper.concurrent.locks.KeyspaceLock;
import org.sniper.nosql.redis.command.RedisCommands;

/**
 * Redis分布式锁实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisDistributedLock<K> implements KeyspaceLock<K> {
	
	private final RedisCommands redisCommands;
	
	public RedisDistributedLock(RedisCommands redisCommands) {
		AssertUtils.assertNotNull(redisCommands, "Property 'redisCommands' must not be null");
		this.redisCommands = redisCommands;
	}
	
	@Override
	public void lock(K key) {
		ReentrantLock lock = redisCommands.get(key);
		if (lock == null) {
			lock = new ReentrantLock();
			lock.lock();
			redisCommands.setNX(key, lock, 10);
		} else {
			lock.lock();
			redisCommands.set(key, lock, 10);
		}
	}
		
	@Override
	public boolean tryLock(K key) {
		
//		ReentrantLock lock = new ReentrantLock();
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean tryLock(K key, long acquireTime, TimeUnit unit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock(K key) {
		ReentrantLock lock = redisCommands.get(key);
		if (lock != null) {
			lock.unlock();
			
			if (!lock.isLocked())
				redisCommands.del(key);
		}
	}

	@Override
	public boolean isLocked(K key) {
		return redisCommands.exists(key);
	}
	
	public static void main(String[] args) {
	}

}
