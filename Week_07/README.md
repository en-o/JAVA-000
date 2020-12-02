# （必做）按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
## 工具
[HikariCP](src/main/java/io/tan/insert/util/HikariCP.java)
[JdbcDataBaseUtil](src/main/java/io/tan/insert/util/JdbcDataBaseUtil.java)
[SnowFlake](src/main/java/io/tan/insert/util/SnowFlake.java)
## jdbc 的使用时间是 System.currentTimeMillis() 的输出
- 一个连接的插入跟线程池没有关系
- 批量插入时间缩短到了1-2min,但是每次插入 100，1000，10000，100000的差距都不是很大
## 存储过程
- 循环插入花费的时间跟jdbc直接查询差不多

## 结论
- 直接插入的 多种方式 效果都差不多 ，可能用多线程会快一点，还没试过

## jdbc hikari 
- 用时：899952ms (≈15min)
```java
public static void main(String[] args) {
		SnowFlake snowFlake = new SnowFlake(2, 3);
		String insertSql= " INSERT INTO `test`.`tb_user`( `uuid`, `user_name`, `phone`, `email`) VALUES ( ?, ?, ?, ?);\n ";
		Connection getConn = HikariCP.getConnection(user, password, url, Driver);
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
```


## jdbc jdbc 
- 用时：896012ms (≈15min)
```java
public static void main(String[] args) {
		SnowFlake snowFlake = new SnowFlake(2, 3);
		String insertSql= " INSERT INTO `test`.`tb_user`( `uuid`, `user_name`, `phone`, `email`) VALUES ( ?, ?, ?, ?);\n ";
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
```

## jdbc 事务提交 (一次)
- 用时：69030ms (≈1.1min)
```java
	public static void main(String[] args) {
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
```

## jdbc 事务提交 （分批）
|次数|时间|
|---|---|
|10|65551ms (≈1.09min)|
|100|64608ms (≈1.07min)|
|1000|65329ms (≈1.08min)|
|10000|74484ms (≈1.2min)|
```java
	public static void main(String[] args) {
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
                if (i % 10000 == 0) {
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
```


## 存储过程
- 用时： 896.753s （≈14min）
```sql
# 参考： https://www.cnblogs.com/javallh/p/11845242.html
drop procedure if exists round_test;
# delimiter //
create procedure round_test()
begin
  DECLARE n int DEFAULT 0;
    WHILE n < 1000000 DO
				INSERT INTO `test`.`tb_user`( `uuid`, `user_name`, `phone`, `email`) VALUES ( n, 'tan', n, '@163.com');
            set n = n + 1;
  END WHILE;
  commit;
end;
call round_test();
```