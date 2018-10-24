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
 * Create Date : 2018-10-23
 */

package org.sniper.generator.test.snowflake;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.generator.snowflake.ParameterizeSnowflakeGenerator;
import org.sniper.generator.snowflake.SequenceNode;
import org.sniper.generator.test.GeneratorTest;

/**
 * 
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParameterizeSnowflakeGeneratorTest extends GeneratorTest {
	
	private ParameterizeSnowflakeGenerator generator ;
	
	@Override
	public void init() {
		this.generator = new ParameterizeSnowflakeGenerator(new SequenceNode());
		
		uniquenessTest = true;
		performanceTest = false;
	}

	@Override
	protected void doUniquenessTest() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Callable<Set<Long>> task = new Callable<Set<Long>>() {

			@Override
			public Set<Long> call() throws Exception {
				Set<Long> set = CollectionUtils.newLinkedHashSet(threadTaskExecuteSize);
				for (int i = 0; i < threadTaskExecuteSize; i++) {
					set.add(generator.generate());
				}
				return set;
			}
		};
		
		List<Future<Set<Long>>> futures = CollectionUtils.newArrayList(thradPoolSize);
		for (int i = 0; i < thradPoolSize; i++) {
			futures.add(executor.submit(task));
		}
		
		Set<Long> totalSet = CollectionUtils.newLinkedHashSet();
		for (int i = 0; i < thradPoolSize; i++) {
			totalSet.addAll(futures.get(i).get());
		}
		
		assertEquals(thradPoolSize * threadTaskExecuteSize, totalSet.size());
		System.out.println("Total set size:" + totalSet.size());
	}
	
	
	@Override
	protected void doPerformanceTest() {
		for (int i = 0; i < size; i++) {
			System.out.println(generator.generate());;
		}
	}
	
	public static void main(String[] args) {
		ParameterizeSnowflakeGenerator generator = new ParameterizeSnowflakeGenerator();
//		System.out.println(generator.generate());
//		System.out.println(generator.generate());
		System.out.println(generator.batchGenerate(5));
		
//		System.out.println(String.format("%03d", 12));
	}

}
