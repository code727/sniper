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
 * Create Date : 2017-8-31
 */

package org.sniper.beans.parameter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sniper.commons.util.MapUtils;

/**
 * 默认不可修改的泛型参数实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultUnmodifiableParameters<K, V> implements UnmodifiableParameters<K, V> {
	
	/** 参数映射项 */
	private final Parameters<K, V> parameters;
	
	public DefaultUnmodifiableParameters() {
		this((Map<K, V>) null);
	}
		
	public DefaultUnmodifiableParameters(Parameters<K, V> parameters) {
		this(parameters != null ? parameters.getParameterItems() : null);
	}
	
	public DefaultUnmodifiableParameters(Map<K, V> parameters) {
		this.parameters = new DefaultParameters<K, V>(MapUtils.newUnmodifiableMap(buildParameters(parameters)));
	}
	
	/**
	 * 构建参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameters
	 * @return
	 */
	protected Map<K, V> buildParameters(Map<K, V> parameters) {
		return parameters;
	}
	
	@Override
	public Map<K, V> getParameterItems() {
		return parameters.getParameterItems();
	}

	@Override
	public V getValue(K name) {
		return parameters.getValue(name);
	}

	@Override
	public Set<K> getNames() {
		return parameters.getNames();
	}

	@Override
	public List<V> getValues() {
		return parameters.getValues();
	}

	@Override
	public int size() {
		return parameters.size();
	}

	@Override
	public boolean isEmpty() {
		return parameters.isEmpty();
	}

	@Override
	public boolean isNotEmpty() {
		return parameters.isNotEmpty();
	}
	
	@Override
	public String toString() {
		return parameters.toString();
	}

	@Override
	public String getString(K name) {
		return parameters.getString(name);
	}

	@Override
	public String getString(K name, String defaultValue) {
		return parameters.getString(name, defaultValue);
	}
	
	@Override
	public String getNotEmptyStringt(K name) {
		return parameters.getNotEmptyStringt(name);
	}

	@Override
	public String getNotEmptyStringt(K name, String defaultValue) {
		return parameters.getNotEmptyStringt(name, defaultValue);
	}

	@Override
	public String getNotBlankStringt(K name) {
		return parameters.getNotBlankStringt(name);
	}

	@Override
	public String getNotBlankStringt(K name, String defaultValue) {
		return parameters.getNotBlankStringt(name, defaultValue);
	}
	
	@Override
	public Boolean getBoolean(K name) {
		return parameters.getBoolean(name);
	}

	@Override
	public Boolean getBoolean(K name, Boolean defaultValue) {
		return parameters.getBoolean(name, defaultValue);
	}

	@Override
	public boolean getBooleanValue(K name) {
		return parameters.getBooleanValue(name);
	}

	@Override
	public boolean getBooleanValue(K name, boolean defaultValue) {
		return parameters.getBooleanValue(name, defaultValue);
	}
	
	@Override
	public Byte getByte(K name) {
		return parameters.getByte(name);
	}

	@Override
	public Byte getByte(K name, Byte defaultValue) {
		return parameters.getByte(name, defaultValue);
	}
	
	@Override
	public byte getByteValue(K name) {
		return parameters.getByteValue(name);
	}

	@Override
	public byte getByteValue(K name, byte defaultValue) {
		return parameters.getByteValue(name, defaultValue);
	}

	@Override
	public Short getShort(K name) {
		return parameters.getShort(name);
	}

	@Override
	public Short getShort(K name, Short defaultValue) {
		return parameters.getShort(name, defaultValue);
	}
	
	@Override
	public short getShortValue(K name) {
		return parameters.getShortValue(name);
	}

	@Override
	public short getShortValue(K name, short defaultValue) {
		return parameters.getShortValue(name, defaultValue);
	}

	@Override
	public Integer getInteger(K name) {
		return parameters.getInteger(name);
	}

	@Override
	public Integer getInteger(K name, Integer defaultValue) {
		return parameters.getInteger(name, defaultValue);
	}
	
	@Override
	public int getIntegerValue(K name) {
		return parameters.getIntegerValue(name);
	}

	@Override
	public int getIntegerValue(K name, int defaultValue) {
		return parameters.getIntegerValue(name, defaultValue);
	}

	@Override
	public Long getLong(K name) {
		return parameters.getLong(name);
	}

	@Override
	public Long getLong(K name, Long defaultValue) {
		return parameters.getLong(name, defaultValue);
	}
	
	@Override
	public long getLongValue(K name) {
		return parameters.getLongValue(name);
	}

	@Override
	public long getLongValue(K name, long defaultValue) {
		return parameters.getLongValue(name, defaultValue);
	}

	@Override
	public Float getFloat(K name) {
		return parameters.getFloat(name);
	}

	@Override
	public Float getFloat(K name, Float defaultValue) {
		return parameters.getFloat(name, defaultValue);
	}
	
	@Override
	public float getFloatValue(K name) {
		return parameters.getFloatValue(name);
	}

	@Override
	public float getFloatValue(K name, float defaultValue) {
		return parameters.getFloatValue(name, defaultValue);
	}

	@Override
	public Double getDouble(K name) {
		return parameters.getDouble(name);
	}

	@Override
	public Double getDouble(K name, Double defaultValue) {
		return parameters.getDouble(name, defaultValue);
	}
	
	@Override
	public double getDoubleValue(K name) {
		return parameters.getDoubleValue(name);
	}

	@Override
	public double getDoubleValue(K name, double defaultValue) {
		return parameters.getDoubleValue(name, defaultValue);
	}

	@Override
	public BigInteger getBigInteger(K name) {
		return parameters.getBigInteger(name);
	}

	@Override
	public BigInteger getBigInteger(K name, BigInteger defaultValue) {
		return parameters.getBigInteger(name, defaultValue);
	}

	@Override
	public BigInteger getBigIntegerValue(K name) {
		return parameters.getBigIntegerValue(name);
	}
	
	@Override
	public BigInteger getBigIntegerValue(K name, BigInteger defaultValue) {
		return parameters.getBigIntegerValue(name, defaultValue);
	}

	@Override
	public BigDecimal getBigDecimal(K name) {
		return parameters.getBigDecimal(name);
	}

	@Override
	public BigDecimal getBigDecimal(K name, BigDecimal defaultValue) {
		return parameters.getBigDecimal(name, defaultValue);
	}

	@Override
	public BigDecimal getBigDecimalValue(K name) {
		return parameters.getBigDecimalValue(name);
	}

	@Override
	public BigDecimal getBigDecimalValue(K name, BigDecimal defaultValue) {
		return parameters.getBigDecimalValue(name, defaultValue);
	}

}
