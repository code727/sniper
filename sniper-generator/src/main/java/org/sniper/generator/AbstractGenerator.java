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

package org.sniper.generator;

import org.sniper.generator.dimension.DateDimensionGenerator;
import org.sniper.generator.dimension.DimensionGenerator;

/**
 * 生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractGenerator implements Generator<String> {
		
	/** 最终生成结果的最小长度 */
	protected final int minLength;
	
	/** 维度生成器 */
	protected final DimensionGenerator<?> dimensionGenerator;
	
	protected AbstractGenerator() {
		this(0);
	}
	
	protected AbstractGenerator(int minLength) {
		this(minLength, null);
	}
	
	protected AbstractGenerator(DimensionGenerator<?> dimensionGenerator) {
		this(0, new DateDimensionGenerator());
	}
	
	protected AbstractGenerator(int minLength, DimensionGenerator<?> dimensionGenerator) {
		if (minLength > 0)
			this.minLength = minLength;
		else
			// 默认最小长度为16位
			this.minLength = 16;
		
		if (dimensionGenerator != null)
			this.dimensionGenerator = dimensionGenerator;
		else
			// 默认的维度采用日期维度生成器
			this.dimensionGenerator = new DateDimensionGenerator(); 
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
	protected abstract <T> T generateByDimension(Object dimension);
	
	/**
	 * 补偿生成指定字符串长度+offset长度的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param generatedString
	 * @param offset
	 * @return
	 */
	protected abstract String makeupGenerate(String generatedString, int offset);
	
}
