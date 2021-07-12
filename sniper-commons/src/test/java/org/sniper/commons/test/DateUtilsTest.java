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
 * Create Date : 2019年12月13日
 */

package org.sniper.commons.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.sniper.commons.util.DateUtils;
import org.sniper.test.junit.BaseTestCase;

/**
 * 日期工具测试类
 * @author  Daniele
 * @version 1.0
 */
public class DateUtilsTest extends BaseTestCase {
	
	@Test
	public void multiThreadFormat() {
		ExecutorService executor = Executors.newCachedThreadPool();
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println(DateUtils.dateToString(new Date(Math.abs(new Random().nextLong()))));
				} catch (Exception e) {
					logger.error("Format error cause:{}", e.getMessage());
					System.exit(1);
				}
			}
		};
		
		while (true) {
			executor.execute(task);
		}
		
		
	}

}
