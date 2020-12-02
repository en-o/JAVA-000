package io.tan.insert.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author tn
 * @version 1
 * @ClassName HikariCP
 * @description
 * @date 2020/11/18 0:00
 */
public class HikariCP {


    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String uName, String pwd, String urls, String driver) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(urls);
        hikariConfig.setUsername(uName);
        hikariConfig.setPassword(pwd);

//        最小空闲连接数量
        hikariConfig.addDataSourceProperty("minimumIdle", 5);
//        空闲连接存活最大时间， /S
        hikariConfig.addDataSourceProperty("idleTimeout", 600000);
//        连接池最大连接数，默认是10
        hikariConfig.addDataSourceProperty("maximumPoolSize", 10);
//        连接池名称
        hikariConfig.addDataSourceProperty("poolName", "jdbcHikari");
//        数据库连接超时时间,默认30秒，即30000
        hikariConfig.addDataSourceProperty("connectionTimeout", 30000);
        hikariConfig.addDataSourceProperty("connectionTestQuery", "SELECT 1");
        // HikariDataSource 也可以配置
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.printf("MySqlDataBaseUtil数据库连接失败 =数据库登录名字：%S\n  数据库地址：%s\n",uName,urls);
        }

        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection( HikariDataSource dataSource){
        System.err.println("连接名:"+dataSource);
        try{
            if(dataSource!=null) {
                System.err.println("关闭连接:"+dataSource);
                dataSource.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
