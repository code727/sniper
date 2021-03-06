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
 * Create Date : 2018-11-12
 */

package org.sniper.generator.test.application;

import org.sniper.generator.application.SerialNumberGenerator;
import org.sniper.generator.test.AbstractSpringGeneratorTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 流水号生成器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class SerialNumberGeneratorTest extends AbstractSpringGeneratorTest<String> {
	
	@Autowired
	private SerialNumberGenerator serialNumberGenerator;

	public SerialNumberGeneratorTest() {
		this(false, true);
	}
	
	protected SerialNumberGeneratorTest(boolean uniquenessTest, boolean performanceTest) {
		super(uniquenessTest, performanceTest);
	}

	@Override
	protected String generate() {
		return serialNumberGenerator.generateByParameter("A");
	}
			
}
