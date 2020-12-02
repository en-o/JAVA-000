package io.tan.insert;

import io.tan.insert.jpa.UserDao;
import io.tan.insert.jpa.UserEntity;
import io.tan.insert.util.HikariCP;
import io.tan.insert.util.JdbcDataBaseUtil;
import io.tan.insert.util.SnowFlake;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class InsertApplicationTests {


	@Resource
	private UserDao userDao;

	static String user = "root";
	static String password = "tn@123456";
	static String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false";
	static String Driver = "com.mysql.cj.jdbc.Driver";


	static String user2 = "root";
//	static String password2 = "tn@123456";
	static String url2 = "jdbc:mysql://127.0.0.1:3336/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false";
	static String Driver2 = "com.mysql.cj.jdbc.Driver";


	@Test
	void contextLoads() {
		UserEntity.UserEntityBuilder builder = UserEntity.builder();
		List<UserEntity> listUser = new ArrayList<>();
		UserEntity build ;
		Long startTime = System.currentTimeMillis();
		for (int i = 1; i <= 1000000; i++) {
			build = builder.userName("谭" + i).email((int) Math.round(13) + "@163.com").phone(Math.round(13) + "").build();
			listUser.add(build);
		}
		Long endTime = System.currentTimeMillis();
		System.out.println("用时：" + (endTime - startTime));
		userDao.saveAll(listUser);
	}



	public void valuse(){
		SnowFlake snowFlake = new SnowFlake(2, 3);
		String insertSql= " INSERT INTO `test`.`tb_user`( `uuid`, `user_name`, `phone`, `email`) VALUES ( ?, ?, ?, ?);\n ";
//		Connection getConn = HikariCP.getConnection(user, password, url, Driver);
		Connection getConn = JdbcDataBaseUtil.getConnection(user, password, url, Driver);
		try {
			PreparedStatement insertPs = getConn.prepareStatement(insertSql);
			getConn.setAutoCommit(false);
			Long startTime = System.currentTimeMillis();
			for (int i = 1; i <= 1000000; i++) {
				insertPs.setLong(1, snowFlake.nextId());
				insertPs.setString(2, "谭"+i);
				insertPs.setInt(3, (int)Math.round(13));
				insertPs.setString(4, (int)Math.round(13)+"@163.com");
				insertPs.addBatch();
				if (i % 100000 == 0) {
					insertPs.executeBatch();
					getConn.commit();
					insertPs.clearBatch();
				}
			}
			insertPs.executeBatch();
			getConn.commit();
			insertPs.clearBatch();
			Long endTime = System.currentTimeMillis();
			System.out.println("用时：" + (endTime - startTime));
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			JdbcDataBaseUtil.closeConnection(getConn);
		}
	}
	public void values2(){
		SnowFlake snowFlake = new SnowFlake(2, 3);
		String insertSql= " INSERT INTO `test`.`tb_user`( `uuid`, `user_name`, `phone`, `email`) VALUES ( ?, ?, ?, ?);\n ";
//		Connection getConn = HikariCP.getConnection(user, password, url, Driver);
		Connection getConn = JdbcDataBaseUtil.getConnection(user, password, url, Driver);
		try {
			PreparedStatement insertPs = getConn.prepareStatement(insertSql);

			Long startTime = System.currentTimeMillis();
			for (int i = 1; i <= 1000000; i++) {
				insertPs.setLong(1, snowFlake.nextId());
				insertPs.setString(2, "谭"+i);
				insertPs.setInt(3, (int)Math.round(13));
				insertPs.setString(4, (int)Math.round(13)+"@163.com");
				insertPs.executeUpdate();
			}
			Long endTime = System.currentTimeMillis();
			System.out.println("用时：" + (endTime - startTime));
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			JdbcDataBaseUtil.closeConnection(getConn);
		}
	}
}
