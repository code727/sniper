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
 * Create Date : 2018年5月18日
 */

package org.sniper.support.test.counter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.support.counter.AtomicLongIntervalCounter;
import org.sniper.test.junit.BaseTestCase;

/**
 * @author  Daniele
 * @version 1.0
 */
public class AtomicLongIntervalCounterTest extends BaseTestCase {
	
	private final long stepLength = 10;
	
	private final AtomicLongIntervalCounter counter = new AtomicLongIntervalCounter(stepLength);
	
	private int size = 100;
	
	@Test
	public void test() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		
		int thradSize = 2;
		List<Future<Set<Long>>> futures = CollectionUtils.newArrayList(thradSize);
		for (int i = 0; i < thradSize; i++) {
			futures.add(executor.submit(task));
		}
		
		Set<Long> totalSet = CollectionUtils.newLinkedHashSet();
		for (int i = 0; i < thradSize; i++) {
			Set<Long> set = futures.get(i).get();
			System.out.println("Set" + (i+1) + " size:" + set.size() + "," + set);
			totalSet.addAll(set);
		}
		
		System.out.println("Total set size:" + totalSet.size());
	}
	
	class Task implements Callable<Set<Long>> {
		
		@Override
		public Set<Long> call() throws Exception {
			Set<Long> set = CollectionUtils.newLinkedHashSet(size);
			for (int i = 0; i < size; i++) {
				set.add(counter.increment());
			}
			return set;
		}
	}

}
