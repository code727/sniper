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
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.AbstractParameterizeGenerator;
import org.sniper.generator.keyspace.Keyspace;
import org.sniper.generator.keyspace.KeyspaceGenerator;

/**
 * 流水号生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SerialNumberGenerator extends AbstractParameterizeGenerator<Object, String> implements Keyspace<Object> {
	
	public static final String DEFAULT_KEYSPACE = "serial_number_key";
	
	/** 键空间生成器 */
	protected final KeyspaceGenerator<Object, ?> keyspaceGenerator;
	
	/** 全局键空间 */
	protected final Object defaultKeyspace;
		
	/** 是否将参数作为键的一部分 */
	private boolean parameterAsKey = true;
	
	/** 是否将参数作为键的一前缀 */
	private boolean parameterAsKeyPrefix;
	
	/** 是否将参数作为最终结果的一部分进行返回 */
	private boolean parameterAsResult;
	
	/** 是否将参数作为最终结果的前缀进行返回 */
	private boolean parameterAsResultPrefix;
	
	/** 序列部分的最小长度 */
	private int sequenceMinLength = 16;
	
	public SerialNumberGenerator(KeyspaceGenerator<Object, ?> keyspaceGenerator) {
		this(keyspaceGenerator, null);
	}
	
	public SerialNumberGenerator(KeyspaceGenerator<Object, ?> keyspaceGenerator, Object defaultKeyspace) {
		AssertUtils.assertNotNull(keyspaceGenerator, "Keyspace generator must not be null");
		this.keyspaceGenerator = keyspaceGenerator;
		this.defaultKeyspace = (defaultKeyspace != null ? defaultKeyspace : DEFAULT_KEYSPACE);
	}
	
	@Override
	public Object getDefaultKeyspace() {
		return defaultKeyspace;
	}
	
	public boolean isParameterAsKey() {
		return parameterAsKey;
	}

	public void setParameterAsKey(boolean parameterAsKey) {
		this.parameterAsKey = parameterAsKey;
	}

	public boolean isParameterAsKeyPrefix() {
		return parameterAsKeyPrefix;
	}

	public void setParameterAsKeyPrefix(boolean parameterAsKeyPrefix) {
		this.parameterAsKeyPrefix = parameterAsKeyPrefix;
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
	public String generateByParameter(Object parameter) {
		String safeParameter = ObjectUtils.toSafeString(parameter);
		String generated = keyspaceGenerator.generateByKey(createKeyByParameter(safeParameter)).toString();
		return generateSequence(safeParameter, generated);
	}
	
	@Override
	public List<String> batchGenerateByParameter(Object parameter, int count) {
		String safeParameter = ObjectUtils.toSafeString(parameter);
		List<?> list = keyspaceGenerator.batchGenerateByKey(createKeyByParameter(safeParameter), count);
		
		List<String> generates = CollectionUtils.newArrayList(list.size());
		for (Object element : list) {
			generates.add(generateSequence(safeParameter, element.toString()));
		}
		
		return generates;
	}
	
	/**
	 * 补偿生成指定字符串长度+offset长度的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param generated
	 * @param offset
	 * @return
	 */
	protected String compensateGenerate(String generated, int offset) {
		// 要求generated参数必须为字符串类型的数字，否则会抛出IllegalArgumentException
		return NumberUtils.format(new BigInteger(generated), generated.length() + offset);
	}
	
	/**
	 * 根据指定的参数创建键
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param safeParameter
	 * @return
	 */
	private String createKeyByParameter(String safeParameter) {
		if (parameterAsKey) {
			if (parameterAsKeyPrefix) {
				// 格式:"参数_默认键"
				return new StringBuilder(safeParameter)
						.append(StringUtils.UNDER_LINE).append(defaultKeyspace).toString();
			}
			
			// 格式:"默认键_参数"
			return new StringBuilder(defaultKeyspace.toString())
					.append(StringUtils.UNDER_LINE).append(safeParameter).toString();
		} 
		
		return defaultKeyspace.toString();
	}
	
	/**
	 * 根据参数和预生成结果生成最终的序列
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param safeParameter
	 * @param generated
	 * @return
	 */
	private String generateSequence(String safeParameter, String generated) {
		/* 将"序列部分的最小长度"与"预生成结果的长度"相减，如果大于0，
		 * 说明预生成结果的长度不够，需要补偿预生成结果的最小长度为sequenceMinLength后得到最终的序列*/
		int offset = sequenceMinLength - generated.length();
		if (offset > 0) {
			generated = compensateGenerate(generated, offset);
		}
		
		return parameterAsResult ? (parameterAsResultPrefix ? 
				safeParameter + generated : generated + safeParameter) : generated;
	}

}
