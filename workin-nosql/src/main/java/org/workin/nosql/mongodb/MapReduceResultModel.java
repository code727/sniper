/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-9-16
 */

package org.workin.nosql.mongodb;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.ObjectUtils;

/**
 * MongoDB MapReduce的结果集(results键)对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapReduceResultModel {
	
	/** _id键值 */
	private Object id;
	
	/** value键值 */
	private Object value;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getId(Class<T> clazz) {
		return (T) id;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(Class<T> clazz) {
		return (T) value;
	}
	
	public String getStringId() {
		return ObjectUtils.toString(id);
	}
	
	public double getDoubleId() {
		return NumberUtils.toDouble(id);
	}
	
	public double getDoubleId(double defaultValue) {
		return NumberUtils.toDouble(id, defaultValue);
	}
	
	public float getFloatId() {
		return NumberUtils.toFloat(id);
	}
	
	public float getFloatId(float defaultValue) {
		return NumberUtils.toFloat(id, defaultValue);
	}
	
	public int getIntegerId() {
		return NumberUtils.toInteger(id);
	}
	
	public int getIntegerId(int defaultValue) {
		return NumberUtils.toInteger(id, defaultValue);
	}
	
	public long getLongId() {
		return NumberUtils.toLong(id);
	}
	
	public long getLongId(long defaultValue) {
		return NumberUtils.toLong(id, defaultValue);
	}
	
	public short getShortId() {
		return NumberUtils.toShort(id);
	}
	
	public short getShortId(short defaultValue) {
		return NumberUtils.toShort(id, defaultValue);
	}
	
	public byte getByteId() {
		return NumberUtils.toByte(id);
	}
	
	public byte getByteId(byte defaultValue) {
		return NumberUtils.toByte(id, defaultValue);
	}
	
	public BigDecimal getBigDecimalId() {
		return NumberUtils.toBigDecimal(id);
	}
	
	public BigDecimal getBigDecimalId(BigDecimal defaultValue) {
		return NumberUtils.toBigDecimal(id, defaultValue);
	}
	
	public BigInteger getBigIntegerId() {
		return NumberUtils.toBigInteger(id);
	}
	
	public BigInteger getBigIntegerId(BigInteger defaultValue) {
		return NumberUtils.toBigInteger(id, defaultValue);
	}
	
	public String getStringValue() {
		return ObjectUtils.toString(value);
	}
	
	public double getDoubleValue() {
		return NumberUtils.toDouble(value);
	}
	
	public double getDoubleValue(double defaultValue) {
		return NumberUtils.toDouble(value, defaultValue);
	}
	
	public float getFloatValue() {
		return NumberUtils.toFloat(value);
	}
	
	public float getFloatValue(float defaultValue) {
		return NumberUtils.toFloat(value, defaultValue);
	}
	
	public int getIntegerValue() {
		return NumberUtils.toInteger(value);
	}
	
	public int getIntegerValue(int defaultValue) {
		return NumberUtils.toInteger(value, defaultValue);
	}
	
	public long getLongValue() {
		return NumberUtils.toLong(value);
	}
	
	public long getLongValue(long defaultValue) {
		return NumberUtils.toLong(value, defaultValue);
	}
	
	public short getShortValue() {
		return NumberUtils.toShort(value);
	}
	
	public short getShortValue(short defaultValue) {
		return NumberUtils.toShort(value, defaultValue);
	}
	
	public byte getByteValue() {
		return NumberUtils.toByte(value);
	}
	
	public byte getByteValue(byte defaultValue) {
		return NumberUtils.toByte(value, defaultValue);
	}
	
	public BigDecimal getBigDecimalValue() {
		return NumberUtils.toBigDecimal(value);
	}
	
	public BigDecimal getBigDecimalValue(BigDecimal defaultValue) {
		return NumberUtils.toBigDecimal(value, defaultValue);
	}
	
	public BigInteger getBigIntegerValue() {
		return NumberUtils.toBigInteger(value);
	}
	
	public BigInteger getBigIntegerValue(BigInteger defaultValue) {
		return NumberUtils.toBigInteger(value, defaultValue);
	}

}
