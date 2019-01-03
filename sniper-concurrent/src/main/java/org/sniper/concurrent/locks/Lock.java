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

package org.sniper.concurrent.locks;

/**
 * 锁接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Lock {
	
	/**
	 * 加锁操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void lock();
	
	/**
	 * 尝试加锁操作后返回是否加锁成功
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean tryLock();
	
	/**
	 * 解锁操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void unlock();
	
	/**
	 * 尝试解锁操作后返回是否解锁成功
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean tryUnlock();
	
	/**
	 * 判断是否被锁定
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isLocked();

}
