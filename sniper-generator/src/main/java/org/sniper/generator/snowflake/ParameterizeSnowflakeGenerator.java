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
 * Create Date : 2017-11-27
 */

package org.sniper.generator.snowflake;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.ParameterizeGenerator;
import org.sniper.generator.application.ShortLinkGenerator;

/**
 * 自定义生成器实现类，生成结果满足几点要求：</P>
 * 1.每一个独立的参数在指定的维度刻度内（默认为毫秒级维度）是唯一的；</P>
 * 2.每一个独立的参数随着维度值的递增而趋势递增的。</P>
 * 此实现类可应用在用户下单的情况下，作为每个用户，
 * 传入各自唯一的标识参数后可保证在某一个时间刻度内（默认为毫秒级维度）生成的订单编号是唯一的
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParameterizeSnowflakeGenerator extends AbstractSnowflakeGenerator<Long>
		implements ParameterizeGenerator<Object, Long> {
	
	private final SequenceGenerator<String> sequenceGenerator;
		
	private final TimeSequence timeSequence;
	
	private final ParameterizeGenerator<Object, ?> parameterGenerator;
	
	public ParameterizeSnowflakeGenerator() {
		this((ParameterizeGenerator<Object, ?>) null);
	}
	
	public ParameterizeSnowflakeGenerator(SequenceNode sequenceNode) {
		this(sequenceNode, null);
	}
	
	public ParameterizeSnowflakeGenerator(ParameterizeGenerator<Object, ?> parameterGenerator) {
		this(new SequenceNode(), parameterGenerator);
	}
	
	public ParameterizeSnowflakeGenerator(SequenceNode sequenceNode, ParameterizeGenerator<Object, ?> parameterGenerator) {
		super(sequenceNode);
		this.sequenceGenerator = new CustomizeSequenceGenerator();
		this.timeSequence = new RadomTimeSequence(sequenceMask);
		this.parameterGenerator = (parameterGenerator != null ? parameterGenerator : new ShortLinkGenerator(true));
	}
			
	@Override
	public Long generate() {
		return generate(null);
	}

	@Override
	public synchronized Long generate(Object parameter) {
		return Long.valueOf(sequenceGenerator.generate(this.timeSequence.update()) + generateByParameter(parameter));
	}

	@Override
	public synchronized List<Long> batchGenerate(Object parameter, int count) {
		checkBatchCount(count);
		
		List<Long> results = CollectionUtils.newArrayList(count);
		String parameterValue = generateByParameter(parameter);
		for (int i = 0; i < count; i++) {
			results.add(Long.valueOf(sequenceGenerator.generate(this.timeSequence.update()) + parameterValue));
		}
		
		return results;
	}
	
	@Override
	protected List<Long> doBatchGenerate(int count) {
		return batchGenerate(null, count);
	}
	
	/**
	 * 根据参数生成序列号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @return
	 */
	protected String generateByParameter(Object parameter) {
		String generatedValue = StringUtils.substringEnd(parameterGenerator.generate(parameter).toString(), 4);
		return StringUtils.beforeSupplement(generatedValue, 4, '0');
	}
				
}
