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

package org.sniper.persistence.sqlmap.mybatis.handler.array;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 数组类型处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 * @param <T>
 */
public class ArrayTypeHandler<T> extends BaseTypeHandler<T[]> {
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T[] parameter, JdbcType jdbcType) throws SQLException {
		if (ArrayUtils.isNotEmpty(parameter)) 
			ps.setString(i, ArrayUtils.toString(parameter));
	}
	
	@Override
	public T[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return getResult(rs.getString(columnName));
	}
	
	@Override
	public T[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return getResult(rs.getString(columnIndex));
	}
	
	@Override
	public T[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getResult(cs.getString(columnIndex));
	}

	private T[] getResult(String strResult) {
		if (strResult == null) 
            return null;

		return handResult(StringUtils.split(strResult, ","));
	}
	
	/**
	 * @description 将字符串的结果处理转换为数组对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param resultArray
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T[] handResult(String[] resultArray) {
		// 如果直接使用这个实现类，则用字符串数组即可接收到最终的处理结果
		return (T[]) resultArray;
	}

}
