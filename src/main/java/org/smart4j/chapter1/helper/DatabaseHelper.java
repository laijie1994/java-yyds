package org.smart4j.chapter1.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.util.CollectionUtil;
import org.smart4j.chapter1.util.PropsUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName DatabaseHelper
 * @Description 数据库操作助手类
 * @Author jeremy.lai
 * @Date 2021/11/30 7:24 下午
 * @Version 1.0
 **/
public final class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
    private static final BasicDataSource DATA_SOURCE = new BasicDataSource();

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties config = PropsUtil.loadProps("config.properties");
        DRIVER = config.getProperty("jdbc.driver");
        URL = config.getProperty("jdbc.url");
        USERNAME = config.getProperty("jdbc.username");
        PASSWORD = config.getProperty("jdbc.password");

        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver: ", e);
        }
    }

    /**
     * @return java.sql.Connection
     * @MethodName getConnection
     * @Description 获取数据库连接
     * @Param []
     * @Author jeremy.lai
     * @Date 7:31 下午 2021/11/30
     **/
    public static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection == null) {
            try {
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure: ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    /**
     * @return void
     * @MethodName closeConnection
     * @Description 关闭数据库连接
     * @Param [connection]
     * @Author jeremy.lai
     * @Date 7:34 下午 2021/11/30
     **/
    public static void closeConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure: ", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * @return java.util.List<T>
     * @MethodName queryEntityList
     * @Description 查询实体列表
     * @Param [entityClass, sql, params]
     * @Author jeremy.lai
     * @Date 7:43 下午 2021/11/30
     **/
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass));
        } catch (SQLException e) {
            LOGGER.error("query entity list failure: ", e);
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /**
     * @return T
     * @MethodName queryEntity
     * @Description 查询实体（单个）
     * @Param [entityClass, sql, params]
     * @Author jeremy.lai
     * @Date 11:25 上午 2021/12/1
     **/
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection connection = getConnection();
            entity = QUERY_RUNNER.query(connection, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure: ", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @MethodName executeQuery
     * @Description 执行查询语句（复查sql，连表查询等）
     * @Param [sql, params]
     * @Author jeremy.lai
     * @Date 2:51 下午 2021/12/1
     **/
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection connection = getConnection();
            result = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("execute query failure: ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * @return int 返回受影响的行数
     * @MethodName executeUpdate
     * @Description 执行更新语句（包括update，insert，delete）
     * @Param [sql, params]
     * @Author jeremy.lai
     * @Date 2:57 下午 2021/12/1
     **/
    public static int executeUpdate(String sql, Object... params) {
        LOGGER.info("executeUpdate sql : {}", sql);
        int rows = 0;
        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure: ", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * @return boolean
     * @MethodName insertEntity
     * @Description 插入实体
     * @Param [entityClass, fieldMap]
     * @Author jeremy.lai
     * @Date 3:13 下午 2021/12/1
     **/
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {

        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity,:fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }

        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");
        sql += columns + " VALUES " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * @return boolean
     * @MethodName updateEntity
     * @Description 更新实体
     * @Param [entityClass, fieldMap]
     * @Author jeremy.lai
     * @Date 3:35 下午 2021/12/1
     **/
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity: fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }

        sql += columns.substring(0, columns.lastIndexOf(",")) + " WHERE id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();

        return executeUpdate(sql, params) == 1;
    }

    /**
     * @return boolean
     * @MethodName deleteEntity
     * @Description 删除实体
     * @Param [entityClass, id]
     * @Author jeremy.lai
     * @Date 4:05 下午 2021/12/1
     **/
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * @return java.lang.String
     * @MethodName getTableName
     * @Description 获取实体类对应的表名
     * @Param [entityClass]
     * @Author jeremy.lai
     * @Date 3:30 下午 2021/12/1
     **/
    private static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

    /**
     * @return void
     * @MethodName executeSqlFile
     * @Description 执行指定的sql文件
     * @Param [filePath]
     * @Author jeremy.lai
     * @Date 4:47 下午 2021/12/1
     **/
    public static void executeSqlFile(String filePath) {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        LOGGER.error("stream: {}", stream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        LOGGER.error("reader: {}", reader);
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (Exception e) {
            LOGGER.error("execute sql file failure: ", e);
            throw new RuntimeException(e);
        }
    }


}
