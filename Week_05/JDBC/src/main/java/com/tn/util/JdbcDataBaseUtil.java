package com.tn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcDataBaseUtil {
	/**
	 * 初始化配置 不要调用
	 */
	public static void config(String driver){
		try{
//			Properties prop= new Properties();
//
//			InputStream is= JdbcDataBaseUtil.class.getClassLoader().getResourceAsStream("database.properties");
//			prop.load(is);
			Class.forName(driver);

		}catch(Exception e){
			e.printStackTrace();
			System.err.printf("MySqlDataBaseUtil 有错  数据库登录名字：%S\n  数据库地址：%s\n",driver);
		}
	}
	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection(String uName,String pwd,String urls,String driver) {
		/*
		 * 通过连接池获取一个空闲连接
		 */
		config(driver);

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(urls, uName, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("MySqlDataBaseUtil数据库连接失败 =数据库登录名字：%S\n  数据库地址：%s\n",uName,urls);
		}

		return conn;
	}
	/**
	 * 关闭数据库连接
	 */
	public static void closeConnection(Connection conn){
		System.err.println("连接名:"+conn);
		try{
			if(conn!=null) {
				System.err.println("关闭连接:"+conn);
				conn.close();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
//
//		public static void main(String[] args) throws SQLException {
//			
//			
//
//		Connection getConn = JdbcDataBaseUtil.getConnection("userName","password","url","drive");
//
//	} 


}
