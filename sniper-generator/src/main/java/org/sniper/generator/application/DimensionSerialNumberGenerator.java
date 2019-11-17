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
	
	/** 是否将自定义的键作为维度的前缀 */
	private boolean keyAsDimensionPrefix;
	
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
	
	public DimensionSequence<?> getDimensionSequence() {
		return dimensionSequence;
	}
	
	public boolean isKeyAsDimensionPrefix() {
		return keyAsDimensionPrefix;
	}

	public void setKeyAsDimensionPrefix(boolean keyAsDimensionPrefix) {
		this.keyAsDimensionPrefix = keyAsDimensionPrefix;
	}

	@Override
	protected String doGenerate(Object key, Object parameter) {
		String dimension = dimensionSequence.update().toString();
		String generated = keyspaceGenerator.generateByKey(createKey(dimension, key)).toString();
		
		return generateSequence(dimension, generated, ObjectUtils.toSafeString(parameter));
	}
	
	@Override
	protected List<String> doBatchGenerate(Object key, Object parameter, int count) {
		String safeParameter = ObjectUtils.toSafeString(parameter);
		String dimension = dimensionSequence.update().toString();
		List<?> list = keyspaceGenerator.batchGenerateByKey(createKey(dimension, key), count);
		
		List<String> generates = CollectionUtils.newArrayList(list.size());
		for (Object element : list) {
			generates.add(generateSequence(dimension, element.toString(), safeParameter));
		}
		
		return generates;
	}
			
	/**
	 * 根据维度值和自定义的键创建键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dimension
	 * @param key
	 * @return
	 */
	private String createKey(String dimension, Object key) {
		if (keyAsDimensionPrefix) 
			// 格式:自定义键_维度值
			return new StringBuilder(key.toString()).append(StringUtils.UNDER_LINE).append(dimension).toString();
		
		// 格式:维度值_自定义键
		return new StringBuilder(dimension).append(StringUtils.UNDER_LINE).append(key).toString();
	}
	
	/**
	 * 根据维度值、预生成结果和参数生成最终的序列
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dimension
	 * @param generated
	 * @param safeParameter
	 * @return
	 */
	private String generateSequence(String dimension, String generated, String safeParameter) {
		// 当前序列的总长度="维度序列长度"+"预生成结果的长度"
		int sequenceLength = dimension.length() + generated.length();
		
		/* 将"序列部分的最小长度"与"当前序列的总长度"相减，如果大于0，说明预生成结果的长度不够，
		 * 需要补偿预生成结果的最小长度，使预生成结果的长度最小为generated.length() + offset，
		 * 从而在补偿后使"维度序列长度"+"预生成结果的长度"的长度最小为sequenceMinLength */
		int offset = getSequenceMinLength() - sequenceLength;
		StringBuilder sequence = new StringBuilder(dimension)
				.append(offset > 0 ? compensateGenerate(generated, generated.length() + offset) : generated);
		
		if (safeParameter.length() > 0 && isParameterAsResult()) {
			if (isParameterAsResultPrefix()) 
				sequence.insert(0, safeParameter);
			else
				sequence.append(safeParameter);
		}
		
		return sequence.toString();
	}
	
}
