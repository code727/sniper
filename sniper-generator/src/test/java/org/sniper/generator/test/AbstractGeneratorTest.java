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
 * Create Date : 2017-11-9
 */

package org.sniper.generator.test;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.test.junit.BaseTestCase;

/**
 * 生成器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractGeneratorTest<T> extends BaseTestCase {
	
	private final Logger logger;
	
	/** 线程池大小 */
	protected int thradPoolSize = 10;
	
	/** 线程任务执行次数 */
	protected int threadTaskExecuteSize = 1000000;
	
	/** 单线程任务执行次数 */
	protected int size = thradPoolSize * threadTaskExecuteSize;
	
	protected final boolean uniquenessTest;
	
	protected final boolean performanceTest;
	
	protected AbstractGeneratorTest(boolean uniquenessTest, boolean performanceTest) {
		this.uniquenessTest = uniquenessTest;
		this.performanceTest = performanceTest;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@Test
	public void test() throws Exception {
		if (this.uniquenessTest) {
			uniquenessTest();
		}
		
		if (this.performanceTest) {
			performanceTest();
		}
	}
	
	/**
	 * 执行唯一性测试
	 * @author Daniele 
	 * @throws Exception
	 */
	protected void uniquenessTest() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		Callable<Set<T>> task = new Callable<Set<T>>() {

			@Override
			public Set<T> call() throws Exception {
				Set<T> set = CollectionUtils.newLinkedHashSet(threadTaskExecuteSize);
				for (int i = 0; i < threadTaskExecuteSize; i++) {
					set.add(generate());
				}
				return set;
			}
		};
		
		List<Future<Set<T>>> futures = CollectionUtils.newArrayList(thradPoolSize);
		for (int i = 0; i < thradPoolSize; i++) {
			futures.add(executor.submit(task));
		}
		
		Set<T> totalSet = CollectionUtils.newLinkedHashSet();
		for (int i = 0; i < thradPoolSize; i++) {
			totalSet.addAll(futures.get(i).get());
		}
		
		assertEquals(thradPoolSize * threadTaskExecuteSize, totalSet.size());
		logger.info("Uniqueness test execute success,total size:{}", totalSet.size());
	}
	
	/**
	 * 执行性能测试
	 * @author Daniele
	 */
	protected void performanceTest() {
		Date start = new Date();
		for (int i = 0; i < size; i++) {
			generate();
		}
		logger.info("Performance test execute success,execute count:{},execute time:{}ms", 
				size, DateUtils.getIntervalMillis(start, new Date()));
	}
	
	/**
	 * 生成结果
	 * @author Daniele 
	 * @return
	 */
	protected abstract T generate();
		
}
