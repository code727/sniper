/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-11-13
 */

package org.sniper.beans.parameter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 泛型参数接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Parameter<K, V> {
	
	/**
	 * 新增参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param value
	 */
	public void add(K name, V value);
	
	/**
	 * 获取指定名称的参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public V getValue(K name);
	
	/**
	 * 获取指定名称和类型的参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public <V1> V1 getValue(K name, Class<V1> clazz);
	
	/**
	 * 设置参数映射
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameters
	 * @return
	 */
	public void setParameters(Map<K, V> parameters);
	
	/**
	 * 获取所有的参数映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<K, V> getParameters();
	
	/**
	 * 获取所有的参数名
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Set<K> getNames();
	
	/**
	 * 获取所有的参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<V> getValues();
	
	/**
	 * 删除指定名称的参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 */
	public void remove(K name);
	
	/**
	 * 清除所有参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	public void clear();
	
	/**
	 * 获取参数的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int size();
	
	/**
	 * 判断是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * 判断是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isNotEmpty();
	
	/**
	 * 获取字符串参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public String getString(K name);
	
	/**
	 * 获取字符串参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getString(K name, String defaultValue);
	
	/**
	 * 获取包装类布尔参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Boolean getBoolean(K name);
	
	/**
	 * 获取包装类布尔参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Boolean getBoolean(K name, Boolean defaultValue);
	
	/**
	 * 获取布尔参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public boolean getBooleanValue(K name);
	
	/**
	 * 获取包装类布尔参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public boolean getBooleanValue(K name, boolean defaultValue);
	
	/**
	 * 获取包装类比特参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Byte getByte(K name);
	
	/**
	 * 获取包装类比特参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Byte getByte(K name, Byte defaultValue);
	
	/**
	 * 获取比特参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public byte getByteValue(K name);
	
	/**
	 * 获取比特参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public byte getByteValue(K name, byte defaultValue);
	
	/**
	 * 获取包装类短整型参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Short getShort(K name);
	
	/**
	 * 获取包装类短整型参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Short getShort(K name, Short defaultValue);
	
	/**
	 * 获取短整型参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public short getShortValue(K name);
	
	/**
	 * 获取短整型参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public short getShortValue(K name, short defaultValue);
	
	/**
	 * 获取包装类整型参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Integer getInteger(K name);
	
	/**
	 * 获取包装类整型参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Integer getInteger(K name, Integer defaultValue);
	
	/**
	 * 获取整型参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public int getIntegerValue(K name);
	
	/**
	 * 获取整型参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public int getIntegerValue(K name, int defaultValue);
	
	/**
	 * 获取包装类长整型参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Long getLong(K name);
	
	/**
	 * 获取包装类长整型参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Long getLong(K name, Long defaultValue);
	
	/**
	 * 获取长整型参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public long getLongValue(K name);
	
	/**
	 * 获取长整型参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public long getLongValue(K name, long defaultValue);
	
	/**
	 * 获取包装类单精度浮点参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Float getFloat(K name);
	
	/**
	 * 获取包装类单精度浮点参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Float getFloat(K name, Float defaultValue);
	
	/**
	 * 获取单精度浮点参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public float getFloatValue(K name);
	
	/**
	 * 获取单精度浮点参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public float getFloatValue(K name, float defaultValue);
	
	/**
	 * 获取包装类双精度浮点参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public Double getDouble(K name);
	
	/**
	 * 获取包装类双精度浮点参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Double getDouble(K name, Double defaultValue);
	
	/**
	 * 获取双精度浮点参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public double getDoubleValue(K name);
	
	/**
	 * 获取双精度浮点参数值，未获取到时返回指定的默认值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public double getDoubleValue(K name, double defaultValue);

}
