# （必做）按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
## 工具
[HikariCP](src/main/java/io/tan/insert/util/HikariCP.java)
[JdbcDataBaseUtil](src/main/java/io/tan/insert/util/JdbcDataBaseUtil.java)
[SnowFlake](src/main/java/io/tan/insert/util/SnowFlake.java)
## jdbc 的使用时间是 System.currentTimeMillis() 的输出
- 一个连接的插入跟线程池没有关系
- 批量插入时间缩短到了1-2min,但是每次插入 100，1000，10000，100000的差距都不是很大
## 存储过程
- 循环插入花费的时间跟jdbc直接插入差不多
## JPA
- 循环插入花费的时间跟jdbc直接插入差不多
## 结论
- 直接插入的 (jpa,jdbc,sql)多种方式 效果都差不多 ，可能用多线程会快一点，还没试过

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

## jpa 
- 用时： 85157ms （≈1.4min）
```java
	@Test
	void contextLoads() {
		SnowFlake snowFlake = new SnowFlake(2, 3);
		UserEntity.UserEntityBuilder builder = UserEntity.builder();
		List<UserEntity> listUser = new ArrayList<>();
		UserEntity build ;
		Long startTime = System.currentTimeMillis();
		for (int i = 1; i <= 1000000; i++) {
			build = builder.userName("谭" + i).email((int) Math.round(13) + "@163.com")
					.phone(Math.round(13) + "").uuid(snowFlake.nextId()).build();
			listUser.add(build);
		}
		Long endTime = System.currentTimeMillis();
		userDao.saveAll(listUser);
		System.out.println("用时：" + (endTime - startTime));
	}
```

# （必做）读写分离-动态切换数据源版本1.0
## [动态切换数据源版本1.0_springboot1.5](https://github.com/en-o/JAVA-000/tree/main/Week_07/dynamic-datasource-v2/src/main/java/io/tan/datasource/jpa)
## [动态切换数据源版本1.0_springboot2.3.x-2.4.x都试过](https://github.com/en-o/JAVA-000/tree/main/Week_07/dynamic-datasource-v1/src/main/java/io/tan/datasource/jpa)
- 在同一个事务中会发现数据源切换失效 (失效的原因是因为事务导致了后面的设置无效具体解决方法还在摸索)
```java
   /**
    *
    *三个sever放在一起调用就会默认使用第一个的数据源
   
    2020-12-03 02:34:26.433  INFO 13652 --- [nio-8080-exec-8] i.t.d.dynamic.config.DynamicDataSource   : 当前数据源是：master
    findAllDefUserEntity(id=1, uuid=1, userName=8.0, email=8.0, phone=3306)
    ======================================
    2020-12-03 02:34:26.436  INFO 13652 --- [nio-8080-exec-8] i.t.d.d.c.DynamicDataSourceContextHolder : 切换至slave1数据源
    findAllDataSource_1UserEntity(id=1, uuid=1, userName=8.0, email=8.0, phone=3306)
    ======================================
    2020-12-03 02:34:26.438  INFO 13652 --- [nio-8080-exec-8] i.t.d.d.c.DynamicDataSourceContextHolder : 切换至slave2数据源
    findAllDataSource_2UserEntity(id=1, uuid=1, userName=8.0, email=8.0, phone=3306)
    */
    @GetMapping("/testDataSource")
    public void testDataSource() {
        userServer.findAllDef().forEach((it) -> System.out.println("findAllDef"+it.toString()));
        System.out.println("======================================");
        userServer.findAllDataSource_1().forEach((it) -> System.out.println("findAllDataSource_1"+it.toString()));
        System.out.println("======================================");
        userServer.findAllDataSource_2().forEach((it) -> System.out.println("findAllDataSource_2"+it.toString()));
    }

    /**
        单独写就不会
        2020-12-03 02:36:06.493  INFO 13652 --- [nio-8080-exec-9] i.t.d.dynamic.config.DynamicDataSource   : 当前数据源是：master
        findAllDefUserEntity(id=1, uuid=1, userName=8.0, email=8.0, phone=3306)
    */
    @GetMapping("/findAllDef")
    public void findAllDef() {
        userServer.findAllDef().forEach((it) -> System.out.println("findAllDef"+it.toString()));
    }

   /**
    单独写就不会
    2020-12-03 02:36:35.505  INFO 13652 --- [nio-8080-exec-1] i.t.d.d.c.DynamicDataSourceContextHolder : 切换至slave1数据源
    2020-12-03 02:36:35.505  INFO 13652 --- [nio-8080-exec-1] i.t.d.dynamic.config.DynamicDataSource   : 当前数据源是：slave1
    findAllDataSource_1UserEntity(id=1, uuid=1, userName=5.7, email=5.7, phone=3326) */
    @GetMapping("/findAllDataSource_1")
    public void findAllDataSource_1() {
        System.out.println("======================================");
        userServer.findAllDataSource_1().forEach((it) -> System.out.println("findAllDataSource_1"+it.toString()));
    }
 

```
- 每个版本的配置都有稍微的改变，升级很麻烦
    - eg: spring 1.5 读取配置文件用RelaxedPropertyResolver这个，但是2.x没了
    
    
# （必做）读写分离-数据库框架版本2.0
~~~ 待续
