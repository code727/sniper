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

package org.sniper.generator.test.application;

import org.sniper.commons.util.StringUtils;
import org.sniper.generator.application.ShortLinkGenerator;
import org.sniper.generator.test.AbstractGeneratorTest;

/**
 * 短链接生成器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class ShortLinkGeneratorTest extends AbstractGeneratorTest<String> {
	
	private final ShortLinkGenerator generator;
	
	public ShortLinkGeneratorTest() {
		this(false, true);
	}
	
	protected ShortLinkGeneratorTest(boolean uniquenessTest, boolean performanceTest) {
		super(uniquenessTest, performanceTest);
		this.generator = new ShortLinkGenerator(true);
	}

	@Override
	protected String generate() {
		return generator.generateByParameter(StringUtils.UUID());
	}
		
	public static void main(String[] args) {
		ShortLinkGenerator generator1 = new ShortLinkGenerator(false);
		ShortLinkGenerator generator2 = new ShortLinkGenerator(true);
		
		String result;
		System.out.println(result = generator1.generate());
		System.out.println(result = generator2.generateByParameter(1));
		System.out.println(result = generator2.generateByParameter(2));
		System.out.println(result.toString().length());
	}

}
