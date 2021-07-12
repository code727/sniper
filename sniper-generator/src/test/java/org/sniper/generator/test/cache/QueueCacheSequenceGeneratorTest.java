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
 * Create Date : 2018-10-30
 */

package org.sniper.generator.test.cache;

import org.sniper.generator.keyspace.QueueCacheSequenceGenerator;
import org.sniper.generator.test.AbstractSpringGeneratorTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基于队列的缓存序列生成器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class QueueCacheSequenceGeneratorTest extends AbstractSpringGeneratorTest<Long> {
	
	@Autowired
	private QueueCacheSequenceGenerator<Long> queueCacheSequenceGenerator;
	
	public QueueCacheSequenceGeneratorTest() {
		this(false, true);
	}
	
	protected QueueCacheSequenceGeneratorTest(boolean uniquenessTest, boolean performanceTest) {
		super(uniquenessTest, performanceTest);
	}

	@Override
	protected Long generate() {
		return queueCacheSequenceGenerator.generate();
	}
	
//	@Test
	public void testGenerate() {
		System.out.println(queueCacheSequenceGenerator.generate());
		System.out.println(queueCacheSequenceGenerator.generate());
		System.out.println(queueCacheSequenceGenerator.generate());
	}
	
//	@Test
	public void testBatchGenerate() {
		System.out.println(queueCacheSequenceGenerator.batchGenerate(3));
		System.out.println(queueCacheSequenceGenerator.batchGenerate(3));
	}

}
