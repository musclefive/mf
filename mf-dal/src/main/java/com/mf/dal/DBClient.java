package com.mf.dal;

import com.mf.dal.configuration.DBRole;
import com.mf.dal.dbcp.DBConnectionPool;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by shixin on 3/12/14.
 */
class DBClient {
    private static final Logger logger = Logger.getLogger(DBClient.class);

    private DAOMethod daoMethod;

    public DBClient(DAOMethod daoMethod) {
        this.daoMethod = daoMethod;
    }

    public long executeUpdate() throws Exception {
        DBRole dbRole = this.daoMethod.getDBRole();
        String sql = this.daoMethod.getRealSql();
        Object[] arguments = this.daoMethod.getRealArguments();

        DBConnectionPool dbConnectionPool = DBAccesser.getInstance().lookupDBPool(
                this.daoMethod.getTabaleName(), dbRole
        );

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbConnectionPool.borrowObject();

            if (this.daoMethod.isReturnAotuGeneratedId()) {
                preparedStatement = connection.prepareStatement(sql, this.daoMethod.getAutoGeneratedKeyIndex());
            } else {
                preparedStatement = connection.prepareStatement(sql);
            }

            for (int i = 0; i < this.daoMethod.getParameterTypes().length; i++) {
                ParameterType parameterType = this.daoMethod.getParameterTypes()[i];
                Object argument = arguments[i];
                initPrepareStatement(preparedStatement, i + 1, argument, parameterType);
            }

            if (this.daoMethod.isReturnAotuGeneratedId()) {
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                return resultSet.next() ? resultSet.getLong(1) : 0l;
            } else {
                return preparedStatement.executeUpdate();
            }
        } finally {
            if (null != preparedStatement) {
                preparedStatement.close();
            }
            if (null != null) {
                dbConnectionPool.returnObject(connection);
            }
        }
    }

    public long executeUpdateForAllPartitions() throws Exception {
        String tableName = this.daoMethod.getTabaleName();
        String[] allTables = DBAccesser.getInstance().getAllPartitionedTables(tableName);

        String sql = this.daoMethod.getSql();
        Object[] arguments = this.daoMethod.getRealArguments();

        DBConnectionPool dbConnectionPool = DBAccesser.getInstance().lookupDBPool(tableName, this.daoMethod.getDBRole());
        long updateNum = 0l;

        for (String table : allTables) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                connection = dbConnectionPool.borrowObject();
                String realSql = sql.replaceFirst(String.format("\\b%s\\b", tableName), table);
                preparedStatement = connection.prepareStatement(realSql);

                for (int i = 0; i < this.daoMethod.getParameterTypes().length; i++) {
                    ParameterType parameterType = this.daoMethod.getParameterTypes()[i];
                    Object argument = arguments[i];
                    initPrepareStatement(preparedStatement, i + 1, argument, parameterType);
                }

                updateNum += preparedStatement.executeUpdate();
            } finally {
                if (null != preparedStatement) {
                    preparedStatement.close();
                }
                if (null != null) {
                    dbConnectionPool.returnObject(connection);
                }
            }
        }

        return updateNum;
    }

    private <R, T> R executeQuery(Converter<T> converter, ResultSetVisitor<R, T> resultSetVisitor) throws Exception {
        DBRole dbRole = this.daoMethod.getDBRole();
        String tableName = this.daoMethod.getTabaleName();
        String sql = this.daoMethod.getRealSql();
        Object[] arguments = this.daoMethod.getRealArguments();
        ParameterType[] parameterTypes = this.daoMethod.getParameterTypes();

        DBConnectionPool dbConnectionPool = DBAccesser.getInstance().lookupDBPool(tableName, dbRole);

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbConnectionPool.borrowObject();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < arguments.length; i++) {
                initPrepareStatement(preparedStatement, i + 1, arguments[i], parameterTypes[i]);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetVisitor.visit(resultSet, converter);
        } finally {
            if (null != preparedStatement) {
                preparedStatement.close();
            }
            if (null != connection) {
                connection.close();
            }
        }
    }

    public <T> T executeQueryToOne(Converter<T> converter) throws Exception {
        return executeQuery(converter, new QueryOneResultSetVisitor<T>());
    }

    public <T> List<T> executeQueryToList(Converter<T> converter) throws Exception {
        List<T> list = new ArrayList<T>();
        return executeQuery(converter, new QueryMultiResultSetVisitor<List<T>, T>(list));
    }

    public <T> Set<T> executeQueryToSet(Converter<T> converter) throws Exception {
        Set<T> set = new HashSet<T>();
        return executeQuery(converter, new QueryMultiResultSetVisitor<Set<T>, T>(set));
    }

    private void initPrepareStatement(PreparedStatement preparedStatement, int index, Object object, ParameterType parameterType) throws SQLException {
        switch (parameterType) {
            case BYTE:
                preparedStatement.setByte(index, (Byte) object);
                break;
            case SHORT:
                preparedStatement.setShort(index, (Short) object);
                break;
            case INT:
                preparedStatement.setInt(index, (Integer) object);
                break;
            case LONG:
                preparedStatement.setLong(index, (Long) object);
                break;
            case STRING:
                preparedStatement.setString(index, (String) object);
                break;
            case BYTE_ARRAY:
                preparedStatement.setBytes(index, (byte[]) object);
                break;
            case BIG_DECIMAL:
                preparedStatement.setBigDecimal(index, (BigDecimal) object);
                break;
            case FLOAT:
                preparedStatement.setFloat(index, (Float) object);
                break;
            case DOUBLE:
                preparedStatement.setDouble(index, (Double) object);
                break;
            case DATE:
                preparedStatement.setDate(index, (Date) object);
                break;
            case TIME:
                preparedStatement.setTime(index, (Time) object);
                break;
            case TIMESTAMP:
                preparedStatement.setTimestamp(index, (Timestamp) object);
                break;
            default:
                logger.error("Should never happen.");
                throw new RuntimeException("Met unexpected parameter type when initializing PreparedStatement.");
        }
    }

    private static class QueryOneResultSetVisitor<T> implements ResultSetVisitor<T, T> {
        @Override
        public T visit(ResultSet resultSet, Converter<T> converter) throws SQLException {
            return resultSet.next() ? converter.convert(resultSet) : null;
        }
    }

    private static class QueryMultiResultSetVisitor<C extends Collection<T>, T> implements ResultSetVisitor<C, T> {
        private C collection;

        private QueryMultiResultSetVisitor(C collection) {
            this.collection = collection;
        }

        @Override
        public C visit(ResultSet resultSet, Converter<T> converter) throws SQLException {
            while (resultSet.next()) {
                T t = converter.convert(resultSet);
                if (t != null) {
                    collection.add(t);
                }
            }
            return collection;
        }
    }
}