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

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.dimension.DateDimensionGenerator;
import org.sniper.generator.dimension.DimensionGenerator;

/**
 * 参数化生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractParameterizeGenerator implements ParameterizeGenerator<String, String> {
	
	/** 前缀 */
	protected String prefix = StringUtils.EMPTY;
	
	/** 是否将前缀作为最终结果的一部分进行返回 */
	protected boolean prefixAsResult;
		
	/** 最终生成结果的最小长度 */
	protected int minLength = 16;
	
	/** 维度生成器 */
	protected DimensionGenerator<?> dimensionGenerator = new DateDimensionGenerator();
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isPrefixAsResult() {
		return prefixAsResult;
	}

	public void setPrefixAsResult(boolean prefixAsResult) {
		this.prefixAsResult = prefixAsResult;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		AssertUtils.assertTrue(minLength > 0, "Generator minimum length must greater than 0");
		this.minLength = minLength;
	}

	public DimensionGenerator<?> getDimensionGenerator() {
		return dimensionGenerator;
	}

	public void setDimensionGenerator(DimensionGenerator<?> dimensionGenerator) {
		AssertUtils.assertNotNull(dimensionGenerator, "Dimension generator must not be null");
		this.dimensionGenerator = dimensionGenerator;
	}

	@Override
	public String generate() {
		// 根据设置的全局前缀来生成
		return generate(this.prefix);
	}
	
	@Override
	public String generate(String prefix) {
		String dimension = dimensionGenerator.create().toString();
		String dimensionKey = StringUtils.safeString(prefix) + dimension;
		String generated = generateByDimension(dimensionKey).toString();
		
		if (prefixAsResult) {
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
