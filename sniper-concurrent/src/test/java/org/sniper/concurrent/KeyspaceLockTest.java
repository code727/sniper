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

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.test.spring.JUnit4SpringContextTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 键空间锁单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class KeyspaceLockTest extends JUnit4SpringContextTestCase {
	
	@Autowired
	private KeyspaceLockDemo parameterizeLockDemo;
	
	protected int threadTaskExecuteSize = 1000;
	
	protected int thradPoolSize = 10;
	
	@Test
	public void test() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<Set<Integer>> task = new Callable<Set<Integer>>() {

			@Override
			public Set<Integer> call() throws Exception {
				Set<Integer> set = CollectionUtils.newLinkedHashSet(threadTaskExecuteSize);
				for (int i = 0; i < threadTaskExecuteSize; i++) {
					set.add(parameterizeLockDemo.add("test"));
				}
				return set;
			}
		};
		
		List<Future<Set<Integer>>> futures = CollectionUtils.newArrayList(thradPoolSize);
		for (int i = 0; i < thradPoolSize; i++) {
			futures.add(executor.submit(task));
		}
		
		Set<Integer> totalSet = CollectionUtils.newLinkedHashSet();
		for (int i = 0; i < thradPoolSize; i++) {
			totalSet.addAll(futures.get(i).get());
		}
		
		assertEquals(thradPoolSize * threadTaskExecuteSize, totalSet.size());
		logger.info("Uniqueness test execute success,total size:{}", totalSet.size());
	}

}
