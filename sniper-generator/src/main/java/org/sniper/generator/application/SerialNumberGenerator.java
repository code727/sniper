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
 * Create Date : 2018-11-12
 */

package org.sniper.generator.application;

import java.math.BigInteger;
import java.util.List;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.generator.keyspace.AbstractKeyspaceParameterizeGenerator;
import org.sniper.generator.keyspace.KeyspaceGenerator;

/**
 * 流水号生成器
 * @author  Daniele
 * @version 1.0
 */
public class SerialNumberGenerator extends AbstractKeyspaceParameterizeGenerator<Object, Object, String> {
	
	public static final String DEFAULT_KEYSPACE = "serial_number_key";
	
	/** 键空间生成器 */
	protected final KeyspaceGenerator<Object, ?> keyspaceGenerator;
		
	/** 是否将参数作为最终结果的一部分进行返回 */
	private boolean parameterAsResult = true;
	
	/** 是否将参数作为最终结果的前缀进行返回 */
	private boolean parameterAsResultPrefix;
	
	/** 序列部分的最小长度 */
	private int sequenceMinLength = 16;
	
	public SerialNumberGenerator(KeyspaceGenerator<Object, ?> keyspaceGenerator) {
		this(keyspaceGenerator, null);
	}
	
	public SerialNumberGenerator(KeyspaceGenerator<Object, ?> keyspaceGenerator, Object defaultKeyspace) {
		super(defaultKeyspace != null ? defaultKeyspace : DEFAULT_KEYSPACE);
		AssertUtils.assertNotNull(keyspaceGenerator, "Keyspace generator must not be null");
		this.keyspaceGenerator = keyspaceGenerator;
	}
		
	public boolean isParameterAsResult() {
		return parameterAsResult;
	}

	public void setParameterAsResult(boolean parameterAsResult) {
		this.parameterAsResult = parameterAsResult;
	}

	public boolean isParameterAsResultPrefix() {
		return parameterAsResultPrefix;
	}

	public void setParameterAsResultPrefix(boolean parameterAsResultPrefix) {
		this.parameterAsResultPrefix = parameterAsResultPrefix;
	}

	public KeyspaceGenerator<Object, ?> getKeyspaceGenerator() {
		return keyspaceGenerator;
	}

	public int getSequenceMinLength() {
		return sequenceMinLength;
	}

	public void setSequenceMinLength(int sequenceMinLength) {
		AssertUtils.assertTrue(sequenceMinLength > 0, "Property 'sequenceMinLength' must greater than 0");
		this.sequenceMinLength = sequenceMinLength;
	}
	
	@Override
	protected String doGenerate(Object key, Object parameter) {
		String generated = keyspaceGenerator.generateByKey(key).toString();
		return generateSequence(generated, ObjectUtils.toSafeString(parameter));
	}

	@Override
	protected List<String> doBatchGenerate(Object key, Object parameter, int count) {
		List<?> list = keyspaceGenerator.batchGenerateByKey(key, count);
		
		List<String> generates = CollectionUtils.newArrayList(list.size());
		String safeParameter = ObjectUtils.toSafeString(parameter);
		for (Object element : list) {
			generates.add(generateSequence(element.toString(), safeParameter));
		}
		
		return generates;
	}

	/**
	 * 补偿生成字符串的结果，使其长度最小为minLength
	 * @author Daniele 
	 * @param generated
	 * @param minLength
	 * @return
	 */
	protected String compensateGenerate(String generated, int minLength) {
		// 要求generated参数必须为字符串类型的数字，否则会抛出IllegalArgumentException
		return NumberUtils.format(new BigInteger(generated), minLength);
	}
		
	/**
	 * 根据预生成结果和参数生成最终的序列
	 * @author Daniele 
	 * @param generated
	 * @param safeParameter
	 * @return
	 */
	private String generateSequence(String generated, String safeParameter) {
		/* 将"序列部分的最小长度"与"预生成结果的长度"相减，如果大于0，
		 * 说明预生成结果的长度不够，需要补偿预生成结果的最小长度为sequenceMinLength后得到最终的序列*/
		if (sequenceMinLength - generated.length() > 0) 
			generated = compensateGenerate(generated, sequenceMinLength);
		
		return (safeParameter.length() > 0 && parameterAsResult)
				? (parameterAsResultPrefix ? safeParameter + generated : generated + safeParameter) : generated;
	}

}
