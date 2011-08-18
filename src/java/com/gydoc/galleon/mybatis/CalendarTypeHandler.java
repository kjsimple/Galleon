package com.gydoc.galleon.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.Calendar;

/**
 *
 */
public class CalendarTypeHandler extends BaseTypeHandler implements TypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, new Timestamp(((Calendar) parameter).getTimeInMillis()));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        java.sql.Timestamp timestamp = rs.getTimestamp(columnName);
        if (timestamp != null) {
            Calendar.getInstance().setTimeInMillis(timestamp.getTime());
        }
        return null;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        java.sql.Timestamp timestamp = cs.getTimestamp(columnIndex);
        if (timestamp != null) {
            Calendar.getInstance().setTimeInMillis(timestamp.getTime());
        }
        return null;
    }
    
}
