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

import org.sniper.commons.util.StringUtils;
import org.sniper.generator.snowflake.SnowflakeParameterizeGenerator;
import org.sniper.generator.test.AbstractGeneratorTest;

/**
 * Snowflake参数化序列生成器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class SnowflakeParameterizeGeneratorTest extends AbstractGeneratorTest<Long> {
	
	private SnowflakeParameterizeGenerator generator;
	
	public SnowflakeParameterizeGeneratorTest() {
		this(false, true);
	}

	protected SnowflakeParameterizeGeneratorTest(boolean uniquenessTest, boolean performanceTest) {
		super(uniquenessTest, performanceTest);
		this.generator = new SnowflakeParameterizeGenerator();
	}
	
	@Override
	protected Long generate() {
		return generator.generateByParameter(StringUtils.UUID());
	}
	
	public static void main(String[] args) {
		SnowflakeParameterizeGenerator generator = new SnowflakeParameterizeGenerator();
		
		Long result = generator.generate();
		System.out.println(result = generator.generateByParameter(1));
		System.out.println(result = generator.generate());
		System.out.println(result = generator.generate());
		System.out.println(result.toString().length());
	}

}
