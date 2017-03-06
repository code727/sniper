package org.workin.persistence.sqlmap.mybatis.handler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class ArrayTypeHandler implements TypeHandler<Object> {

	public Object getResult(ResultSet resultSet, String columnName)
			throws SQLException {
		return resultSet.getObject(columnName);
	}

	public Object getResult(CallableStatement statement, int i)
			throws SQLException {
		return statement.getArray(i).getArray();

	}

	public void setParameter(PreparedStatement ps, int i, Object param,
			JdbcType jdbctype) throws SQLException {
		if (param == null) {
			ps.setNull(i, Types.ARRAY);
		} else {
			Connection conn = ps.getConnection();
			Array loc = conn.createArrayOf("RESOURCELIST", (Object[]) param);
			ps.setArray(i, loc);
		}

	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}