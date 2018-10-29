///*
// * Copyright 2018 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * 
// * Create Date : 2018-5-8
// */
//
//package org.sniper.generator;
//
//import java.math.BigInteger;
//
//import org.sniper.commons.util.AssertUtils;
//import org.sniper.commons.util.NumberUtils;
//import org.sniper.commons.util.ObjectUtils;
//import org.sniper.generator.dimension.DimensionGenerator;
//
///**
// * 可补偿的参数化生成器抽象类
// * @author  <a href="mailto:code727@gmail.com">杜斌</a>
// * @version 1.0
// */
//public abstract class AbstractCompensableParameterizeGenerator<P, T> extends AbstractParameterizeGenerator<P, String> {
//	
//	/** 维度生成器 */
//	protected DimensionGenerator<?> dimensionGenerator;
//	
//	/** 是否将参数作为维度键的前缀 */
//	protected boolean parameterAsDimensionKeyPrefix = true;
//	
//	/** 是否将参数作为最终结果的一部分进行返回 */
//	protected boolean parameterAsResult;
//	
//	/** 最终结果的最小长度 */
//	protected int minLength = 16;
//	
//	public DimensionGenerator<?> getDimensionGenerator() {
//		return dimensionGenerator;
//	}
//
//	public void setDimensionGenerator(DimensionGenerator<?> dimensionGenerator) {
//		this.dimensionGenerator = dimensionGenerator;
//	}
//	
//	public boolean isParameterAsDimensionKeyPrefix() {
//		return parameterAsDimensionKeyPrefix;
//	}
//
//	public void setParameterAsDimensionKeyPrefix(boolean parameterAsDimensionKeyPrefix) {
//		this.parameterAsDimensionKeyPrefix = parameterAsDimensionKeyPrefix;
//	}
//	
//	public boolean isParameterAsResult() {
//		return parameterAsResult;
//	}
//
//	public void setParameterAsResult(boolean parameterAsResult) {
//		this.parameterAsResult = parameterAsResult;
//	}
//
//	public int getMinLength() {
//		return minLength;
//	}
//	
//	public void setMinLength(int minLength) {
//		AssertUtils.assertTrue(minLength > 0, "Generator min length must greater than 0");
//		this.minLength = minLength;
//	}
//
//	@Override
//	public String generate(Object parameter) {
//		String safeParameter = ObjectUtils.toSafeString(parameter);
//		
//		if (dimensionGenerator != null) {
//			String dimension = dimensionGenerator.create().toString();
//			String dimensionKey = (parameterAsDimensionKeyPrefix ? safeParameter + dimension : dimension + safeParameter);
//			String generated = generateByKey(dimensionKey).toString();
//			
//			int length = dimension.length() + generated.length();
//			// 参数的长度不参与补偿长度的计算
//			int offset = minLength - length;
//			
//			String result = (offset > 0 ? dimension + compensateGenerate(generated, offset) : dimension + generated);
//			return parameterAsResult ? safeParameter + result : result;
//		} 
//		
//		String generated = generateByKey(safeParameter).toString();
//		int offset = minLength - generated.length();
//		String result = (offset > 0 ? compensateGenerate(generated, offset) : generated);
//		return parameterAsResult ? safeParameter + result : result;
//	}
//	
//	/**
//	 * 补偿生成指定字符串长度+offset长度的结果
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param generated
//	 * @param offset
//	 * @return
//	 */
//	protected String compensateGenerate(String generated, int offset) {
//		// 要求generated参数必须为字符串类型的数字，否则会抛出IllegalArgumentException
//		return NumberUtils.format(new BigInteger(generated), generated.length() + offset);
//	}
//	
//	/**
//	 * 根据键生成可变结果
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param key
//	 * @return
//	 */
//	protected abstract T generateByKey(String key);
//	
//}
