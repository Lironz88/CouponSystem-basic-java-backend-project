package db;

import enums.Category;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class JDBCUtils {
    public static void runQuery(String sql) throws SQLException {

        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ConnectionPool.getInstance().restoreConnection(connection);
    }

    public static void runQuery(String sql, Map<Integer, Object> map) throws SQLException {
        List<?> rows = null;
        Connection connection = ConnectionPool.getInstance().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement = supportParam(statement, map);
        statement.execute();
        ConnectionPool.getInstance().restoreConnection(connection);
    }

    public static List<?> runQueryWithResult(String sql) throws SQLException {
        List<?> rows = null;

        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        rows = ConvertUtils.toList(resultSet);
        resultSet.close();
        ConnectionPool.getInstance().restoreConnection(connection);
        return rows;
    }

    public static List<?> runQueryWithResult(String sql, Map<Integer, Object> params) throws SQLException {
        List<?> rows = null;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement = supportParam(statement, params);
        ResultSet resultSet = statement.executeQuery();
        rows = ConvertUtils.toList(resultSet);
        resultSet.close();
        ConnectionPool.getInstance().restoreConnection(connection);
        return rows;
    }

    private static PreparedStatement supportParam(PreparedStatement statement, Map<Integer, Object> param) throws SQLException {
        for (Map.Entry<Integer, Object> entry : param.entrySet()) {
            int key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Double) {
                statement.setDouble(key, (Double) value);
                continue;
            }
            if (value instanceof String) {
                statement.setString(key, (String) value);
                continue;
            }
            if (value instanceof Boolean) {
                statement.setBoolean(key, (Boolean) value);
                continue;
            }
            if (value instanceof Date) {
                statement.setDate(key, (Date) value);
                continue;
            }
            if (value instanceof Integer) {
                statement.setInt(key, (Integer) value);
                continue;
            }
            if (value instanceof Category) {
                statement.setInt(key, ((Category) value).ordinal());
            }

        }
        return statement;
    }
}