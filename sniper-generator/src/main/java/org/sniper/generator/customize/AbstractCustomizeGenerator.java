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
 * Create Date : 2017-11-13
 */

package org.sniper.generator.customize;

import org.sniper.commons.util.AssertUtils;
import org.sniper.generator.Generator;
import org.sniper.generator.dimension.DateDimensionGenerator;
import org.sniper.generator.dimension.DimensionGenerator;

/**
 * 自定义生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractCustomizeGenerator<T> implements Generator<String> {
	
	protected final static int DEFAULT_MIN_LENGTH = 16;
	
	protected final static DimensionGenerator<?> DEFAULT_DIMENSION_GENERATOR = new DateDimensionGenerator();
	
	/** 最终生成结果的最小长度 */
	protected final int minLength;
	
	/** 维度生成器 */
	protected final DimensionGenerator<?> dimensionGenerator;
	
	protected AbstractCustomizeGenerator() {
		this(DEFAULT_MIN_LENGTH);
	}
	
	protected AbstractCustomizeGenerator(int minLength) {
		this(minLength, DEFAULT_DIMENSION_GENERATOR);
	}
	
	protected AbstractCustomizeGenerator(DimensionGenerator<?> dimensionGenerator) {
		this(DEFAULT_MIN_LENGTH, DEFAULT_DIMENSION_GENERATOR);
	}
	
	protected AbstractCustomizeGenerator(int minLength, DimensionGenerator<?> dimensionGenerator) {
		AssertUtils.assertTrue(minLength > 0, "Generator minimum length must greater than 0");
		AssertUtils.assertNotNull(dimensionGenerator, "Dimension generator must not be null");
		
		this.minLength = minLength;
		this.dimensionGenerator = dimensionGenerator;
	}
	
	public int getMinLength() {
		return minLength;
	}

	public DimensionGenerator<?> getDimensionGenerator() {
		return dimensionGenerator;
	}

	@Override
	public String generate() {
		Object dimension = dimensionGenerator.create();
		Object generated = generateByDimension(dimension);
		
		String dimensionString = dimension.toString();
		String generatedString = generated.toString();
		
		int length = dimensionString.length() + generatedString.length();
		int offset = minLength - length;
		
		return offset > 0 ? dimensionString + makeupGenerate(generatedString, offset) : dimensionString + generatedString;
	}
	
	/**
	 * 根据维度生成
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dimension
	 * @return
	 */
	protected abstract T generateByDimension(Object dimension);
	
	/**
	 * 补偿生成指定字符串长度+offset长度的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param generatedString
	 * @param offset
	 * @return
	 */
	protected abstract String makeupGenerate(String generatedString, int offset);
	
}
