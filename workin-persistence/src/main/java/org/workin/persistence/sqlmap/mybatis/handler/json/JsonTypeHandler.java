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

package org.workin.persistence.sqlmap.mybatis.handler.json;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.workin.commons.util.ClassUtils;
import org.workin.commons.util.CodecUtils;
import org.workin.commons.util.StringUtils;
import org.workin.serialization.json.JsonSerializer;
import org.workin.serialization.json.codehaus.JacksonSerializer;

/**
 * @description JSON类型处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
	
	private JsonSerializer jsonSerializer;
	
	public JsonTypeHandler() {
		this(null);
	}
	
	public JsonTypeHandler(JsonSerializer jsonSerializer) {
		this.jsonSerializer = (jsonSerializer != null ? jsonSerializer : new JacksonSerializer());
	}
	
	public JsonSerializer getJsonSerializer() {
		return jsonSerializer;
	}

	public void setJsonSerializer(JsonSerializer jsonSerializer) {
		this.jsonSerializer = jsonSerializer;
	}

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

	@SuppressWarnings("unchecked")
	private T getResult(String jsonString) {
		
		if (StringUtils.isBlank(jsonString))
			return null;
		
		// 如果直接使用这个实现类，则反序列化JSON字符串后，只能用Map对象来接收最终结果
		return (T) jsonSerializer.deserialize(jsonString, ClassUtils.getSuperClassGenricType(this.getClass()));
	}
	
}
