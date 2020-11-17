package com.tn.test;

import com.tn.util.HikariCP;
import com.tn.util.JdbcDataBaseUtil;

import java.sql.*;
import java.util.HashMap;

/**
 * @author tn
 * @version 1
 * @ClassName Demo
 * @description
 * @date 2020/11/17 22:28
 */
public class Demo {
    static String user = "root";
    static String password = "tn@123456";
    static String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false";
    static String Driver = "com.mysql.cj.jdbc.Driver";



    public static void main(String[] args) {
        //建立一个结果集，用来保存查询出来的结果
//        Connection getConn = JdbcDataBaseUtil.getConnection(user, password, url, Driver);
        Connection getConn = HikariCP.getConnection(user, password, url, Driver);
        Demo demo = new Demo();
        try {
//          demo.ysJDBC(getConn.createStatement());
            demo.prepareStatementJDBC(getConn);
        } finally {
            JdbcDataBaseUtil.closeConnection(getConn);
        }
    }

    public void prepareStatementJDBC(Connection getConn){
        //插入
        String insertSql= " insert into test_user(`name`) values(?) ";
        String selectSql = " select id,`name` from test_user " ;
        String updateSql = " update test_user set name=? where name = ? " ;
        String deleteSql = " delete from test_user where name = ? " ;
        try {
            PreparedStatement selectPs = getConn.prepareStatement(selectSql);
            // 查询
            printResultSet(selectPs.executeQuery());

            //在写到利用数据库查询数据的时候，会用到的语句rs.last()和rs.beforeFirst()，这俩个方法会报错，原因是微软的SQL没有这个方法，需要额外添加语句。
            PreparedStatement insertPs = getConn.prepareStatement(insertSql);
//            commit设置为false,不让它自动提交
            getConn.setAutoCommit(false);
            insertPs.setString(1,"谭宁");
            // 将 数据1 添加到此 PreparedStatement 中缓存。
            insertPs.addBatch();

            insertPs.setString(1,"谭宁2");
            // 将 数据2 添加到此 PreparedStatement 中缓存。
            insertPs.addBatch();

            insertPs.setString(1,"谭宁3");
            // 将 数据3 添加到此 PreparedStatement 中缓存。
            insertPs.addBatch();
            // 提交一批要执行的更新命令
            int[] ints = insertPs.executeBatch();
            for (int i = 0; i < ints.length; i++) {
                System.out.println("（数据"+i+"）执行插入"+ ((ints[i]>0)?true:false));
            }
            // 成功执行完所有的插入操作，进行手动提交 不提交 数据是不会到数据库中去的
            getConn.commit();
            insertPs.clearBatch();
            // 查询
            printResultSet(selectPs.executeQuery());



            PreparedStatement updatePs = getConn.prepareStatement(updateSql);
            getConn.setAutoCommit(false);
            updatePs.setString(1,"谭宁u1");
            updatePs.setString(2,"谭宁");
            updatePs.addBatch();
            updatePs.setString(1,"谭宁u2");
            updatePs.setString(2,"谭宁2");
            updatePs.addBatch();
            updatePs.setString(1,"谭宁u3");
            updatePs.setString(2,"谭宁3");
            updatePs.addBatch();
            int[] ints1 = updatePs.executeBatch();
            getConn.commit();
            for (int i = 0; i < ints1.length; i++) {
                System.out.println("（数据"+i+"）执行更新"+ ((ints1[i]>0)?true:false));
            }
            // 查询
            printResultSet(selectPs.executeQuery());


            PreparedStatement deletePs = getConn.prepareStatement(deleteSql);
            getConn.setAutoCommit(false);
            deletePs.setString(1,"谭宁u1");
            deletePs.addBatch();
            deletePs.setString(1,"谭宁u2");
            deletePs.addBatch();
            deletePs.setString(1,"谭宁u3");
            deletePs.addBatch();
            int[] ints2 = deletePs.executeBatch();
            getConn.commit();
            for (int i = 0; i < ints2.length; i++) {
                System.out.println("（数据"+i+"）执行删除"+ ((ints2[i]>0)?true:false));
            }
            // 查询
            printResultSet(selectPs.executeQuery());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void printResultSet(ResultSet resultSet){

        try {
            while (resultSet.next()) {
                System.out.println("\n查询成功："+"id:"+resultSet.getString("id")+",name:"+resultSet.getString("name"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


    }

    public void ysJDBC(Statement statement){

         String selectSql = " select id,`name` from test_user " ;
         String updateSql = " update test_user set name='张三' where id = 1 " ;
         String deleteSql = " delete from test_user where id = 1 " ;
         String insertSql = " insert into test_user values(1,'谭宁') " ;
        try {
            // 查询
            printResultSet(statement.executeQuery(selectSql));

            System.out.println("执行插入"+ ((statement.executeUpdate(insertSql)>0)?true:false));
            // 查询
            printResultSet(statement.executeQuery(selectSql));

            System.out.println("执行更新"+ ((statement.executeUpdate(updateSql)>0)?true:false));
            // 查询
            printResultSet(statement.executeQuery(selectSql));

            System.out.println("执行删除"+ statement.execute(deleteSql));
            // 查询
            printResultSet(statement.executeQuery(selectSql));


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


}
