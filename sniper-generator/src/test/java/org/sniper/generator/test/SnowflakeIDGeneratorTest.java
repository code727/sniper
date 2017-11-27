/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017年11月9日
 */

package org.sniper.generator.test;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.generator.Generator;
import org.sniper.generator.application.SnowflakeGenerator;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SnowflakeIDGeneratorTest extends GeneratorTest {

	private Generator<Long> generator = new SnowflakeGenerator(0, 0);
	
	@Override
	public void init() {
		uniquenessTest = false;
		performanceTest = true;
	}
	
	@Override
	protected void doUniquenessTest() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<Set<Long>> task = new Callable<Set<Long>>() {

			@Override
			public Set<Long> call() throws Exception {
				Set<Long> set = CollectionUtils.newLinkedHashSet(size);
				for (int i = 0; i < size; i++) {
					set.add((Long) generator.generate());
				}
				return set;
			}
		};

		Future<Set<Long>> f1 = executor.submit(task);
		Future<Set<Long>> f2 = executor.submit(task);

		Set<Long> set1 = f1.get();
		Set<Long> set2 = f2.get();
		Set<Long> set3 = CollectionUtils.newLinkedHashSet();
		set3.addAll(set1);
		set3.addAll(set2);
		
		int size1 = set1.size();
		int size2 = set2.size();
		int size3 = set3.size();
		
		assertTrue(size1 == size);
		assertTrue(size2 == size);
		assertTrue(size3 == (size * 2));

		System.out.println("set1 size:" + size1);
		System.out.println("set2 size:" + size2);
		System.out.println("set3 size:" + size3);
	}

	@Override
	protected void doPerformanceTest() {
		for (int i = 0; i < size; i++) {
			generator.generate();
		}
	}

}
