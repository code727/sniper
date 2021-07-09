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
 * Create Date : 2017-3-20
 */

package org.sniper.sqlmap.mybatis.handler.json;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.serialization.json.JsonSerializer;

/**
 * JSON类型处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractJsonTypeHandler<T> extends BaseTypeHandler<T> {
	
	private JsonSerializer jsonSerializer;
	
	/** 返回类型 */
	protected final Class<T> type;
	
	@SuppressWarnings("unchecked")
	public AbstractJsonTypeHandler() {
		this.jsonSerializer = initJsonSerializer();
		AssertUtils.assertNotNull(jsonSerializer, "Json serializer must not be null.");
		
		this.type = (Class<T>) ClassUtils.getSuperclassGenricType(this.getClass());
	}
	
	/**
	 * 初始化JSON序列化器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected abstract JsonSerializer initJsonSerializer();

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			T parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null)
			ps.setString(i, CodecUtils.bytesToString(jsonSerializer.serialize(parameter)));
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return getResult(rs.getString(columnName));
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return getResult(rs.getString(columnIndex));
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getResult(cs.getString(columnIndex));
	}

	private T getResult(String jsonString) {
		
		if (StringUtils.isBlank(jsonString))
			return null;
		
		return (T) jsonSerializer.deserialize(jsonString, type);
	}
	
}
