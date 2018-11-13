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
 * Create Date : 2018-11-13
 */

package org.sniper.generator.application;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.keyspace.KeyspaceGenerator;
import org.sniper.generator.sequence.DateSequence;
import org.sniper.generator.sequence.DimensionSequence;

/**
 * 基于维度的流水号生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DimensionSerialNumberGenerator extends SerialNumberGenerator {
	
	/** 维度序列 */
	protected final DimensionSequence<?> dimensionSequence;
	
	public DimensionSerialNumberGenerator(KeyspaceGenerator<Object, ?> keyspaceGenerator) {
		this(keyspaceGenerator, null);
	}
	
	public DimensionSerialNumberGenerator(KeyspaceGenerator<Object, ?> keyspaceGenerator, Object defaultKeyspace) {
		this(keyspaceGenerator, null);
	}
	
	public DimensionSerialNumberGenerator(KeyspaceGenerator<Object, ?> keyspaceGenerator,
			DimensionSequence<?> dimensionSequence) {
		
		this(keyspaceGenerator, dimensionSequence, null);
	}
	
	public DimensionSerialNumberGenerator(KeyspaceGenerator<Object, ?> keyspaceGenerator,
			DimensionSequence<?> dimensionSequence, Object defaultKeyspace) {
		
		super(keyspaceGenerator, defaultKeyspace);
		this.dimensionSequence = (dimensionSequence != null ? dimensionSequence : new DateSequence());
	}
	
	@Override
	public String generateByParameter(Object parameter) {
		String safeParameter = ObjectUtils.toSafeString(parameter);
		String dimension = dimensionSequence.update().toString();
		String generated = keyspaceGenerator.generateByKey(createKey(safeParameter, dimension)).toString();
		return generateSequence(safeParameter, dimension, generated);
	}
	
	@Override
	public List<String> batchGenerateByParameter(Object parameter, int count) {
		String safeParameter = ObjectUtils.toSafeString(parameter);
		String dimension = dimensionSequence.update().toString();
		List<?> list = keyspaceGenerator.batchGenerateByKey(createKey(safeParameter, dimension), count);
		
		List<String> generates = CollectionUtils.newArrayList(list.size());
		for (Object element : list) {
			generates.add(generateSequence(safeParameter, dimension, element.toString()));
		}
		
		return generates;
	}
	
	/**
	 * 根据参数和维度值创建键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param safeParameter
	 * @param dimension
	 * @return
	 */
	private String createKey(String safeParameter, String dimension) {
		if (isParameterAsKey()) {
			if (isParameterAsKeyPrefix()) {
				// 格式:"参数_默认键_维度"
				return new StringBuilder(safeParameter).append(StringUtils.UNDER_LINE)
						.append(defaultKeyspace).append(StringUtils.UNDER_LINE).append(dimension).toString();
			}
			
			// 格式:"默认键_维度_参数"
			return new StringBuilder(defaultKeyspace.toString()).append(StringUtils.UNDER_LINE)
					.append(dimension).append(StringUtils.UNDER_LINE).append(safeParameter).toString();
		}
		
		return dimension;
	}
	
	/**
	 * 根据参数、维度值和预生成结果生成最终的序列
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param safeParameter
	 * @param dimension
	 * @param generated
	 * @return
	 */
	private String generateSequence(String safeParameter, String dimension, String generated) {
		// 当前序列的总长度="维度序列长度"+"预生成结果的长度"
		int sequenceLength = dimension.length() + generated.length();
		
		/* 将"序列部分的最小长度"与"当前序列的总长度"相减，如果大于0，
		 * 说明预生成结果的长度不够，需要补偿预生成结果的最小长度，使当前序列的总长度最小为sequenceLength*/
		int offset = getSequenceMinLength() - sequenceLength;
		StringBuilder sequence = new StringBuilder(dimension)
				.append(offset > 0 ? compensateGenerate(generated, offset) : generated);
		
		if (isParameterAsResult()) {
			if (isParameterAsResultPrefix()) {
				sequence.insert(0, safeParameter);
			} else {
				sequence.append(safeParameter);
			}
		}
		
		return sequence.toString();
	}

}
