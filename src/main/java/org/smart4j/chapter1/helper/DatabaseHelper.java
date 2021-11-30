package org.smart4j.chapter1.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure: ", e);
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
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure: ", e);
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
    public static <T> List<T> queryEntityList(Class<T> entityClass, Connection conn, String sql, Object... params) {
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass));
        } catch (SQLException e) {
            LOGGER.error("query entity list failure: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn);
        }
        return entityList;
    }

}
