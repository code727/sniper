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
 * Create Date : 2018年6月25日
 */

package org.sniper.concurrent.locks;

import java.util.concurrent.TimeUnit;

/**
 * 键空间锁接口
 * @author  Daniele
 * @version 1.0
 */
public interface KeyspaceLock<K> {
	
	/**
	 * 对键进行加锁操作
	 * @author Daniele 
	 * @param key
	 */
	public void lock(K key);
	
	/**
	 * 尝试对键进行加锁操作后返回是否加锁成功
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public boolean tryLock(K key);
	
	/**
	 * 在单位时间范围内尝试加锁操作：</P>
	 * 1.如果成功加锁立即返回true</P>
	 * 2.如果超时后未成功加锁，则返回false</P>
	 * @author Daniele 
	 * @param key
	 * @param acquireTime
	 * @param unit
	 * @return
	 */
	public boolean tryLock(K key, long acquireTime, TimeUnit unit);
	
	/**
	 * 对键进行解锁操作
	 * @author Daniele 
	 * @param key
	 */
	public void unlock(K key);
		
	/**
	 * 判断键是否被锁定
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public boolean isLocked(K key);

}
