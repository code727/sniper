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

import org.junit.Before;
import org.junit.Test;
import org.sniper.commons.util.DateUtils;
import org.sniper.test.junit.BaseTestCase;

/**
 * 生成器单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class GeneratorTest extends BaseTestCase {
	
	protected final int size = 100;
	
	protected boolean uniquenessTest = true;
	
	protected boolean performanceTest;
	
	@Before
	public void init() {
		uniquenessTest = true;
		performanceTest = false;
	}
	
	/**
	 * 唯一性测试
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @throws Exception
	 */
	@Test
	public void uniquenessTest() throws Exception {
		if (uniquenessTest) {
			doUniquenessTest();
		}
	}
	
	/**
	 * 性能测试
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	@Test
	public void performanceTest() {
		if (performanceTest) {
			Date start = new Date();
			doPerformanceTest();
			System.out.println("Finshed performance test, total time:" + DateUtils.getIntervalMillis(start, new Date()) + "ms");
		}
	}
	
	protected abstract void doUniquenessTest() throws Exception;
	
	protected abstract void doPerformanceTest();
	

}
