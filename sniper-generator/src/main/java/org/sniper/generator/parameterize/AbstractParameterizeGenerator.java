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

package org.sniper.generator.parameterize;

import org.sniper.generator.AbstractGenerator;
import org.sniper.generator.dimension.DimensionGenerator;

/**
 * 参数化生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractParameterizeGenerator<K> extends AbstractGenerator
		implements ParameterizeGenerator<K, String> {
	
	/** 是否将generate方法中传递来的键作为维度值的一部分 */
	private boolean dimensionKey;
	
	protected AbstractParameterizeGenerator() {
		super();
	}
	
	protected AbstractParameterizeGenerator(int minLength) {
		super(minLength);
	}
	
	protected AbstractParameterizeGenerator(DimensionGenerator<?> dimensionGenerator) {
		super(dimensionGenerator);
	}
	
	protected AbstractParameterizeGenerator(int minLength, DimensionGenerator<?> dimensionGenerator) {
		super(minLength, dimensionGenerator);
	}
	
	public boolean isDimensionKey() {
		return dimensionKey;
	}

	public void setDimensionKey(boolean dimensionKey) {
		this.dimensionKey = dimensionKey;
	}

	@Override
	public String generate() {
		return generate(null);
	}
	
	@Override
	public String generate(K key) {
		Object dimension = dimensionGenerator.create();
		Object generated = generateByDimension(dimension);
		
		String dimensionString = dimension.toString();
		String generatedString = generated.toString();
		
		int length = dimensionString.length() + generatedString.length();
		int offset = minLength - length;
		
		return offset > 0 ? dimensionString + makeupGenerate(generatedString, offset) : dimensionString + generatedString;
	}

}
