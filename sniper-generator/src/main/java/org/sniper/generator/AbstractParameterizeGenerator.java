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

import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.dimension.DateDimensionGenerator;
import org.sniper.generator.dimension.DimensionGenerator;

/**
 * 参数化生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractParameterizeGenerator implements ParameterizeGenerator<Object, String> {
	
	/** 全局参数 */
	protected Object parameter = StringUtils.EMPTY;
	
	/** 是否将参数作为维度键的前缀 */
	protected boolean parameterAsDimensionKeyPrefix = true;
	
	/** 是否将参数作为最终结果的一部分进行返回 */
	protected boolean parameterAsResult;
	
	/** 维度生成器 */
	protected DimensionGenerator<?> dimensionGenerator = new DateDimensionGenerator();
		
	/** 最终结果的最小长度 */
	protected int minLength = 16;
	
	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	public boolean isParameterAsDimensionKeyPrefix() {
		return parameterAsDimensionKeyPrefix;
	}

	public void setParameterAsDimensionKeyPrefix(boolean parameterAsDimensionKeyPrefix) {
		this.parameterAsDimensionKeyPrefix = parameterAsDimensionKeyPrefix;
	}

	public boolean isParameterAsResult() {
		return parameterAsResult;
	}

	public void setParameterAsResult(boolean parameterAsResult) {
		this.parameterAsResult = parameterAsResult;
	}

	public DimensionGenerator<?> getDimensionGenerator() {
		return dimensionGenerator;
	}

	public void setDimensionGenerator(DimensionGenerator<?> dimensionGenerator) {
		this.dimensionGenerator = dimensionGenerator;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	@Override
	public String generate() {
		// 根据设置的全局参数来生成
		return generate(this.parameter);
	}
	
	@Override
	public String generate(Object parameter) {
		String dimension = dimensionGenerator.create().toString();
		String dimensionKey = (parameterAsDimensionKeyPrefix ? ObjectUtils.toSafeString(parameter) + dimension
				: dimension + ObjectUtils.toSafeString(parameter));
		String generated = generateByDimension(dimensionKey).toString();
		
		if (parameterAsResult) {
			int length = dimensionKey.length() + generated.length();
			int offset = minLength - length;
			return offset > 0 ? dimensionKey + makeupGenerate(generated, offset) : dimensionKey + generated;
		}
				
		int length = dimension.length() + generated.length();
		int offset = minLength - length;
		return offset > 0 ? dimension + makeupGenerate(generated, offset) : dimension + generated;
	}
	
	/**
	 * 根据维度键生成可变结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dimensionKey
	 * @return
	 */
	protected abstract <T> T generateByDimension(String dimensionKey);
	
	/**
	 * 补偿生成指定字符串长度+offset长度的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param generated
	 * @param offset
	 * @return
	 */
	protected abstract String makeupGenerate(String generated, int offset);
	
}
