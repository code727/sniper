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
 * Create Date : 2017年11月14日
 */

package org.sniper.generator.test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.generator.redis.RedisSerialNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Redis流水号生成器单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a> 
 * @version 1.0
 */
public class RedisSerialNumberGeneratorTest extends SpringGeneratorTest {
	
	@Autowired
	private RedisSerialNumberGenerator<?> redisSerialNumberGenerator;
	
	@Override
	public void init() {
		uniquenessTest = false;
		performanceTest = true;
	}

	@Override
	protected void doUniquenessTest() throws Exception {
		redisSerialNumberGenerator.setParameterAsResult(false);
		redisSerialNumberGenerator.setParameterAsDimensionKeyPrefix(true);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<Set<String>> task = new Callable<Set<String>>() {

			@Override
			public Set<String> call() throws Exception {
				Set<String> set = CollectionUtils.newLinkedHashSet(size);
				for (int i = 0; i < size; i++) {
					set.add(redisSerialNumberGenerator.generate());
				}
				return set;
			}
		};
		
		int thradSize = 100;
		List<Future<Set<String>>> futures = CollectionUtils.newArrayList(thradSize);
		for (int i = 0; i < thradSize; i++) {
			futures.add(executor.submit(task));
		}
		
		Set<String> totalSet = CollectionUtils.newLinkedHashSet();
		for (int i = 0; i < thradSize; i++) {
			Set<String> set = futures.get(i).get();
			System.out.println("Set" + (i+1) + " size:" + set.size() + "," + set);
			totalSet.addAll(set);
		}
		
		System.out.println("Total set size:" + totalSet.size());
	}

	@Override
	protected void doPerformanceTest() {
		for (int i = 0; i < size; i++) {
			redisSerialNumberGenerator.generate();
		}
	}

}
