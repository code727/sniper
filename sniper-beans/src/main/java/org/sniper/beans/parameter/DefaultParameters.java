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
 * Create Date : 2017-5-24
 */

package org.sniper.beans.parameter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sniper.commons.enums.logic.BooleanEnum;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 泛型参数默认实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefaultParameters<K, V> implements Parameters<K, V> {
	
	protected Map<K, V> items;
	
	public DefaultParameters() {
		this((Map<K, V>) null);
	}
	
	public DefaultParameters(Parameters<K, V> parameters) {
		this(parameters != null ? parameters.getItems() : null);
	}
	
	public DefaultParameters(Map<K, V> items) {
		setItems(items);
	}
	
	@Override
	public void setItems(Map<K, V> items) {
		if (items != null)
			this.items = items;
		else
			this.items = new LinkedHashMap<K,V>();
	}
	
	@Override
	public void add(K name, V value) {
		items.put(name, value);
	}
	
	@Override
	public void addAll(Map<K, V> items) {
		if (items != null)
			this.items.putAll(items);
	}
	
	@Override
	public V remove(K name) {
		return items.remove(name);
	}

	@Override
	public void clear() {
		items.clear();
	}
	
	@Override
	public Map<K, V> getItems() {
		return items;
	}

	@Override
	public V getValue(K name) {
		return items.get(name);
	}

	@Override
	public Set<K> getNames() {
		return items.keySet();
	}

	@Override
	public List<V> getValues() {
		return CollectionUtils.newArrayList(items.values());
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean isNotEmpty() {
		return !isEmpty();
	}
	
	@Override
	public String toString() {
		return items.toString();
	}

	@Override
	public String getString(K name) {
		return getString(name, null);
	}

	@Override
	public String getString(K name, String defaultValue) {
		V value = items.get(name);
		return value != null ? value.toString() : defaultValue;
	}
	
	@Override
	public String getNotEmptyStringt(K name) {
		return getNotEmptyStringt(name, null);
	}

	@Override
	public String getNotEmptyStringt(K name, String defaultValue) {
		String value = ObjectUtils.toString(items.get(name));
		return StringUtils.isNotEmpty(value) ? value : (StringUtils.isNotEmpty(defaultValue) ? defaultValue : StringUtils.NULL);
	}

	@Override
	public String getNotBlankStringt(K name) {
		return getNotBlankStringt(name, null);
	}

	@Override
	public String getNotBlankStringt(K name, String defaultValue) {
		String value = ObjectUtils.toString(items.get(name));
		return StringUtils.isNotBlank(value) ? value : (StringUtils.isNotBlank(defaultValue) ? defaultValue : StringUtils.NULL);
	}
	
	@Override
	public Boolean getBoolean(K name) {
		return getBoolean(name, null);
	}

	@Override
	public Boolean getBoolean(K name, Boolean defaultValue) {
		V value = items.get(name);
		
		if (value instanceof Boolean)
			return (Boolean) value;
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		return BooleanEnum.parse(stringValue);
	}

	@Override
	public boolean getBooleanValue(K name) {
		return getBooleanValue(name, false);
	}

	@Override
	public boolean getBooleanValue(K name, boolean defaultValue) {
		return getBoolean(name, defaultValue);
	}
	
	@Override
	public Byte getByte(K name) {
		return getByte(name, null);
	}

	@Override
	public Byte getByte(K name, Byte defaultValue) {
		V value = items.get(name);
		
		if (value instanceof Number)
			return ((Number) value).byteValue();
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		try {
			/* 这里不用Byte.parseByte(value.toString())的原因在于当value为字符串类型的小数时，最终的结果会抛出NumberFormatException。
			 * 因此这里为了与Number类型的小数(如Double、Float和BigDecimal)保持一致的转换结果，统一的先将字符串转换成BigDecimal后再获取最终结果。
			 * 如下getLong、getShort、getInteger、getBigInteger方法的处理方式类似。*/
			return new BigDecimal(stringValue).byteValue();
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Can not cast to byte, [name:" + name + ",value:" + stringValue + "]");
	    }
	}
	
	@Override
	public byte getByteValue(K name) {
		return getByteValue(name, (byte) 0);
	}

	@Override
	public byte getByteValue(K name, byte defaultValue) {
		return getByte(name, defaultValue);
	}

	@Override
	public Short getShort(K name) {
		return getShort(name, null);
	}

	@Override
	public Short getShort(K name, Short defaultValue) {
		V value = items.get(name);
				
		if (value instanceof Number)
			return ((Number) value).shortValue();
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		try {
			return new BigDecimal(stringValue).shortValue();
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Can not cast to short, [name:" + name + ",value:" + stringValue + "]");
	    }
	}
	
	@Override
	public short getShortValue(K name) {
		return getShortValue(name, (short) 0);
	}

	@Override
	public short getShortValue(K name, short defaultValue) {
		return getShort(name, defaultValue);
	}

	@Override
	public Integer getInteger(K name) {
		return getInteger(name, null);
	}

	@Override
	public Integer getInteger(K name, Integer defaultValue) {
		V value = items.get(name);
				
		if (value instanceof Number)
			return ((Number) value).intValue();
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		try {
			return new BigDecimal(stringValue).intValue();
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Can not cast to integer, [name:" + name + ",value:" + stringValue + "]");
	    }
	}
	
	@Override
	public int getIntegerValue(K name) {
		return getIntegerValue(name, 0);
	}

	@Override
	public int getIntegerValue(K name, int defaultValue) {
		return getInteger(name, defaultValue);
	}

	@Override
	public Long getLong(K name) {
		return getLong(name, 0L);
	}

	@Override
	public Long getLong(K name, Long defaultValue) {
		V value = items.get(name);
		
		if (value instanceof Number)
			return ((Number) value).longValue();
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		try {
			return new BigDecimal(stringValue).longValue();
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Can not cast to long, [name:" + name + ",value:" + stringValue + "]");
	    }
	}
	
	@Override
	public long getLongValue(K name) {
		return getLongValue(name, 0L);
	}

	@Override
	public long getLongValue(K name, long defaultValue) {
		return getLong(name, defaultValue);
	}

	@Override
	public Float getFloat(K name) {
		return getFloat(name, null);
	}

	@Override
	public Float getFloat(K name, Float defaultValue) {
		V value = items.get(name);
		
		if (value instanceof Number)
			return ((Number) value).floatValue();
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		try {
			return Float.parseFloat(stringValue);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Can not cast to float, [name:" + name + ",value:" + stringValue + "]");
	    }
	}
	
	@Override
	public float getFloatValue(K name) {
		return getFloatValue(name, 0f);
	}

	@Override
	public float getFloatValue(K name, float defaultValue) {
		return getFloat(name, defaultValue);
	}

	@Override
	public Double getDouble(K name) {
		return getDouble(name, null);
	}

	@Override
	public Double getDouble(K name, Double defaultValue) {
		V value = items.get(name);
		
		if (value instanceof Number)
			return ((Number) value).doubleValue();
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		try {
			return Double.parseDouble(stringValue);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Can not cast to double, [name:" + name + ",value:" + stringValue + "]");
	    }
	}
	
	@Override
	public double getDoubleValue(K name) {
		return getDoubleValue(name, 0d);
	}

	@Override
	public double getDoubleValue(K name, double defaultValue) {
		return getDouble(name, defaultValue);
	}

	@Override
	public BigInteger getBigInteger(K name) {
		return getBigInteger(name, null);
	}

	@Override
	public BigInteger getBigInteger(K name, BigInteger defaultValue) {
		V value = items.get(name);
		
		if (value instanceof BigInteger)
			 return (BigInteger) value;
		
		if (value instanceof BigDecimal)
			return ((BigDecimal) value).toBigInteger();
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		try {
			return new BigDecimal(stringValue).toBigInteger();
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Can not cast to big integer, [name:" + name + ",value:" + stringValue + "]");
	    }
	}

	@Override
	public BigInteger getBigIntegerValue(K name) {
		return getBigIntegerValue(name, new BigInteger("0"));
	}
	
	@Override
	public BigInteger getBigIntegerValue(K name, BigInteger defaultValue) {
		AssertUtils.assertNotNull(defaultValue, "Default big integer must not be null");
		return getBigInteger(name, defaultValue);
	}

	@Override
	public BigDecimal getBigDecimal(K name) {
		return getBigDecimal(name, null);
	}

	@Override
	public BigDecimal getBigDecimal(K name, BigDecimal defaultValue) {
		V value = items.get(name);
		
		if (value instanceof BigDecimal)
			return (BigDecimal) value;
		
		if (value instanceof BigInteger)
			return new BigDecimal((BigInteger) value);
		
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.isBlank(stringValue))
			return defaultValue;
		
		try {
			return new BigDecimal(stringValue);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Can not cast to big decimal, [name:" + name + ",value:" + stringValue + "]");
	    }
	}

	@Override
	public BigDecimal getBigDecimalValue(K name) {
		return getBigDecimalValue(name, new BigDecimal(0));
	}

	@Override
	public BigDecimal getBigDecimalValue(K name, BigDecimal defaultValue) {
		AssertUtils.assertNotNull(defaultValue, "Default big decimal must not be null");
		return getBigDecimal(name, defaultValue);
	}

	@Override
	public Date getDate(K name) {
		return getDate(name, (String) null);
	}
	
	@Override
	public Date getDate(K name, String pattern) {
		return getDate(name, pattern, null);
	}

	@Override
	public Date getDate(K name, Date defaultValue) {
		return getDate(name, null, defaultValue);
	}

	@Override
	public Date getDate(K name, String pattern, Date defaultValue) {
		V value = items.get(name);
		Date date = DateUtils.objectToDate(value, pattern);
		return date != null ? date : defaultValue;
	}

	@Override
	public Date getDateValue(K name) {
		return getDateValue(name, (String) null);
	}
	
	@Override
	public Date getDateValue(K name, String pattern) {
		return getDateValue(name, pattern, new Date());
	}

	@Override
	public Date getDateValue(K name, Date defaultValue) {
		return getDateValue(name, null, defaultValue);
	}

	@Override
	public Date getDateValue(K name, String pattern, Date defaultValue) {
		AssertUtils.assertNotNull(defaultValue, "Default big decimal must not be null");
		
		V value = items.get(name);
		Date date = DateUtils.objectToDate(value, pattern);
		return date != null ? date : defaultValue;
	}

}
