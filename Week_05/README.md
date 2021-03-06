# 1、（选做）使Java里的动态代理，实现一个简单的AOP。
   ## [接口代理代码](https://github.com/en-o/JAVA-000/tree/main/Week_05/javaAop/src/main/java/com/tn/Proxy)
   ## [aspectj](https://github.com/en-o/JAVA-000/tree/main/Week_05/javaAop/src/main/java/com/tn/AopMain)
   ## [cglib代理代码](https://github.com/en-o/JAVA-000/tree/main/Week_05/javaCglib/src/main/java/com/tn/CgLib)
   ![springAop](https://github.com/en-o/JAVA-000/blob/main/Week_05/resources/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202020-11-16%20095418.png)
    
    - 静态代理是在实现类上封装一层（代理类），调用者不直接使用是实现类而是使用代理类
    - 动态代理利用反射实现静态代理。让一个代理类就能处理N个接口的的处理
    - filter 跟 Interceptor 就是一个动态代理类        
    - aop 就是 一个代理
# 2、（必做）写代码实现Spring Bean的装配，方式越多越好（XML、Annotation都可以）,提交到Github。
- xml 跟 Annotation 
    - 他们的实现方式一摸一样，除了多加个注解 配置文件多加据注解处理器之外 有什么不同？？？
    - 百度回答的是处理 bean中属性装配的问题，如果一个bean中有很多嵌套，那就完了要写好长好长的<bean/>,注解方式就是用来解决他的，我测试了真是这样
    ```xml
      xml 方式
          <bean id="studentDao" class="com.tn.dao.StudentDao">
              <property name="student" >
                  <bean id="student" class="com.tn.bean.Student"/>
              </property>
          </bean>
      annotation方式 1 处理xml的多级嵌套
          <context:annotation-config></context:annotation-config>
          <bean id="student" class="com.tn.bean.Student"/>
          <bean id="studentDao" class="com.tn.dao.StudentDao" />
    ```
- Annotation的自动专配 配 springboot完美实现 一个xml配置都不需要了 
    - @SpringBootApplication中的ComponentScan帮你完成了 手动写<context:component-scan base-package="com.tn" />的过程

    
## [spring Annotation 的方式](https://github.com/en-o/JAVA-000/tree/main/Week_05/springAnnotation)
```xml
 <!-- 
    使用注解功能时 是要注意xml中头部信息是否配置了 
    xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

注意： 使用 context:compent-scan 扫描的话只需要 加个扫描路径就行了
如果使用 bean 的话 还需要配置 <context:annotation-config></context:annotation-config> 要不然功能无法实现
 -->

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
<!--  <context:annotation-config></context:annotation-config> -->
<!--    <bean id="studentDao" class="com.tn.dao.StudentDao" />-->
<!--    <bean id="student" class="com.tn.bean.Student"/>-->
    <context:component-scan base-package="com.tn" />
</beans>
```

```java
package com.tn.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author tn
 * @version 1
 * @ClassName Student
 * @description 学生表
 * @date 2020/11/13 15:03
 */
@Component("student")
@Data
@ToString
public class Student {
    private int id;
    private String name;
}

package com.tn.dao;

import com.tn.bean.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author tn
 * @version 1
 * @ClassName Student
 * @description 学生表
 * @date 2020/11/13 15:03
 */
@Repository("studentDao")
public class StudentDao {

    @Resource(name = "student")
    private Student student;

    public void print(){
         student.setId(1);
        student.setName("谭宁");
        System.err.println("你好："+student.toString());
    }
}

package com.tn;

import com.tn.dao.StudentDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tn
 * @version 1
 * @ClassName TestMain
 * @description 测试类
 * @date 2020/11/13 15:13
 */
public class TestMain {

    public static void main(String[] args) {

        // 装配bean的过程
        // 通过配置文件将bean加载到容器
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        // 容器 创建 对象
        StudentDao student1 = (StudentDao) context.getBean("studentDao");
        student1.print();

    }
}

信息: Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@506e1b77: startup date [Sun Nov 15 20:57:47 CST 2020]; root of context hierarchy
你好：Student(id=1, name=谭宁)

```


## [spring xml 的方式](https://github.com/en-o/JAVA-000/tree/main/Week_05/springXml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.tn.bean.Student" >
        <property name="id" value="1"></property>
        <property name="name" value="谭宁"></property>
    </bean>

    <bean id="student1" class="com.tn.bean.Student" >
        <property name="id" value="2"></property>
        <property name="name" value="谭宁1"></property>
    </bean>
</beans>
```
```java
package com.tn.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tn
 * @version 1
 * @ClassName Student
 * @description 学生表
 * @date 2020/11/13 15:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String name;
}

package com.tn.dao;

import com.tn.bean.Student;
import lombok.Data;

import javax.annotation.Resource;

/**
 * @author tn
 * @version 1
 * @ClassName StudenDap
 * @description
 * @date 2020/11/15 21:25
 */
@Data
public class StudentDao {

    private Student student;

    public void print(){
        student.setId(2);
        student.setName("谭宁");
        System.err.println("你好："+student.toString());
    }
}


package com.tn;


import com.tn.bean.Student;
import com.tn.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author tn
 * @version 1
 * @ClassName TestMain
 * @description 测试类
 * @date 2020/11/13 15:13
 */
public class TestMain {

    public static void main(String[] args) {

        // 装配bean的过程
        // 通过配置文件将bean加载到容器
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        // 容器 创建 对象
        Student student1 = (Student) context.getBean("student");

        System.out.println(student1.toString());

        Student student2 = (Student) context.getBean("student1");
        System.out.println(student2.toString());

        StudentDao studentDao = (StudentDao) context.getBean("studentDao");
        studentDao.print();

    }

}


十一月 15, 2020 9:33:00 下午 org.springframework.context.support.AbstractApplicationContext prepareRefresh
信息: Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@506e1b77: startup date [Sun Nov 15 21:33:00 CST 2020]; root of context hierarchy
Student(id=1, name=谭宁)
Student(id=2, name=谭宁1)
你好：Student(id=2, name=谭宁)

我把 bean.xml 中配置的某一个 bean  注释了就找不到这个bean了
Student(id=1, name=谭宁)
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'student1' available

```


## [springboot 的方式](https://github.com/en-o/JAVA-000/tree/main/Week_05/springboot)
```xml
  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```
```java

package com.tn.demo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tn
 * @version 1
 * @ClassName Student
 * @description 学生bean
 * @date 2020/11/13 13:53
 */
@Data
@Component
public class Student {

    private int id;
    private String name;
}


package com.tn.demo;

import ch.qos.logback.core.util.ContextUtil;
import com.tn.demo.bean.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication implements ApplicationContextAware {
    @Autowired
    private Student student;

    private static ApplicationContext context;

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
        // 通过配置文件将bean加载到容器
        Student student = (Student) context.getBean("student");
        student.setId(3);
        student.setName("谭宁");
        System.out.println(student.toString());

    }

    @PostConstruct
    public void demo(){
        student.setId(2);
        student.setName("谭宁");
        System.out.println(student.toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(context == null) {
            context = applicationContext;
        }
    }
}


Student(id=2, name=谭宁)
2020-11-15 21:14:44.658  INFO 7132 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-11-15 21:14:44.785  INFO 7132 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8007 (http) with context path ''
2020-11-15 21:14:44.802  INFO 7132 --- [           main] com.tn.demo.DemoApplication              : Started DemoApplication in 1.348 seconds (JVM running for 2.12)
Student(id=3, name=谭宁)
```
# 3、（选做）实现一个Spring XML自定义配置，配置一组Bean，例如Student/Klass/School。
 [参考的文章](https://www.cnblogs.com/huan30/p/12790406.html)
    
    1.设计配置属性和JavaBean
    2.编写XSD(XML  Schema)文件,它就是一个校验XML，定义了一些列的语法来规范XML 
    3.编写NameSpaceHandler和BeanDefinitionParser完成解析工作
    4.编写spring。handlers和spring.schemas串联起所有部件
    5.在Bean文件中应用
    
 [我的应用](https://github.com/en-o/JAVA-000/blob/main/Week_05/javaBean/src/main/resources/custom.xml)
 
        1.设计配置属性和JavaBean
           参考文章中的ID 有误导性， bean中不需要定义id id是<bean的默认值> 作为对象的名字（类似uuid）
        2.编写XSD(XML  Schema)文件,它就是一个校验XML，定义了一些列的语法来规范XML 
            其中的  地址可以乱写，但是下面的四个要写成一样
                targetNamespace="http://www.tn/student"
                xmlns="http://www.tn/student"
                xmlns:student="http://www.tn/student"
                xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                               http://www.tn/student    http://www.tn/student.xsd ">
        3.编写NameSpaceHandler和BeanDefinitionParser完成解析工作
            断点  发现 首先第一步是使用（NamespaceHandlerSupport）注册bean ，在是解析bean(doParse)
        4.编写spring。handlers和spring.schemas串联起所有部件
            跟spring说  http://www.tn/student.xsd 第文件在META-INF/student.xsd里
            http\://www.tn/student.xsd=META-INF/student.xsd
            http\://www.tn/student=com.tn.handler.MyNamespaceHandler
        5.在Bean文件中应用
# 4（必做）给前面课程提供的Student/Klass/School 实现自动配置和Starter

## 1. 让student 自动配置 且从配置文件获取数据

- 我很久之前在尝试搭建自己的组件时就接触到了[自动配置 我的笔记](https://www.yuque.com/tanning/epv4c9/yqqygr)
    - 遇到他的原因时 我写好的模块 打成jar之后 使用时引入该jar每次都要手动的在启动类上加入扫描，很麻烦，然后无意间看到了这个，便开始了尝试，尝试的结果很满意，再也不用在使用时扫描指定包路径了
    - 同时 关于 starter ，在自动配置时也看到了他，我参考别人的限定性框架时也看到了他，一直都没明白这个是什么，也没有去深究
        - 现在大致明白了，他就是一个集合，一个类似工具类的集合，我现在就在着手把我封装的一些限定性的模块，根据类别名称一个个starter 让开发时根据需求直接引入这个依赖集合，就不用在一个个的去引入依赖了
    ![我现在在弄得一个限定性框架](https://github.com/en-o/JAVA-000/blob/main/Week_05/resources/image2.png)

```
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Configuration
@ConfigurationProperties(prefix = "student")
public class Student implements Serializable {
    
    private int id;
    private String name;
    
    public void init(){
        System.out.println("hello...........");
    }
    
    public Student create(){
        return new Student(101,"KK101");
    }
}
```
### 1.2 引入starter-use 使用
- 没有实现自动配置
```
Description:

A component required a bean of type 'com.tn.starter.entity.Student' that could not be found.


Action:

Consider defining a bean of type 'com.tn.starter.entity.Student' in your configuration.


Process finished with exit code 1

OR

Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.tn.starter.entity.Student' available

```

- 实现自动配置 创建文件 resources/META-INF/spring.factories

```
自动装配  = 需要专配的文件
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.tn.starter.entity.Student

2020-11-17 21:17:48.182  INFO 8256 --- [           main] com.tn.starteruse.StarterUseApplication  : Started StarterUseApplication in 0.756 seconds (JVM running for 1.576)
Student(id=1, name=谭宁)
```

- 其实也可以手动装配 在 spring的启动类上加入 包路径扫描 
```java
@SpringBootApplication(scanBasePackages = "com.tn")
```

## 2. 让 Klass 自动配置
```java
@Data
public class Klass { 

    //一定要写  要不然list注入不进来
    @Autowired
    List<Student> students;
    
    public void dong(){
        System.out.println(this.getStudents());
    }

}
//构建配置文件
@Configuration
@Import({Student.class,Klass.class})
public class KlassConfiguration {

}
# 自动装配
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.tn.starter.config.KlassConfiguration

```

## 3. 让 School 同上

## 4. starter  就是将School Klass Student 三个配置 集中到一个地方去，然后引入这个starter就可以直接用这三个配置

# 5. 研究一下JDBC 接口和数据库连接池，掌握它们的设计和用法
**[代码](https://github.com/en-o/JAVA-000/blob/main/Week_05/JDBC/src/main/java/com/tn/test/Demo.java) **
- jdbc 
```java
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

```

- hikariCp
```java
package com.tn.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
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

```

## 5.1）使用JDBC 原生接口，实现数据库的增删改查操作。1）使用JDBC 原生接口，实现数据库的增删改查操作。 
```java
package com.tn.test;

import com.tn.util.JdbcDataBaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    static String password = "root";
    static String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false";
    static String Driver = "com.mysql.cj.jdbc.Driver";

    static String selectSql = " select id,`name` from test_user " ;
    static String updateSql = " update test_user set name='张三' where id = 1 " ;
    static String deleteSql = " delete from test_user where id = 1 " ;
    static String insertSql = " insert into test_user values(1,'谭宁') " ;

    public static void main(String[] args) {
        //建立一个结果集，用来保存查询出来的结果
        Connection getConn = JdbcDataBaseUtil.getConnection(user, password, url, Driver);

        try {
           new Demo().ysJDBC(getConn.createStatement());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
             JdbcDataBaseUtil.closeConnection(getConn);
        }
    }


    public void ysJDBC(Statement statement){
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


    public static void printResultSet(ResultSet resultSet){

        try {
            if (resultSet.next()) {
                System.out.println("\n查询成功："+"id:"+resultSet.getString("id")+",name:"+resultSet.getString("name"));
            }else {
                System.out.println("\n没有数据");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


    }
}

```

## 5.2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。

- 可以用 getConn.setAutoCommit(false); 把sql执行挂起
- 可以用 insertPs.addBatch(); 把需要执行的若干sql语句装载到一起
- 可以用 insertPs.executeBatch(); 进行批量处理 - 
    - 03 20 69 6e 73 65 72 74     .   i n s e r t
      20 69 6e 74 6f 20 74 65       i n t o   t e
      73 74 5f 75 73 65 72 28     s t _ u s e r (
      60 6e 61 6d 65 60 29 20     ` n a m e ` )  
      76 61 6c 75 65 73 28 27     v a l u e s ( '
      e8 b0 ad e5 ae 81 27 29     . . . . . . ' )
      20                           

- 最后必须手动提交  getConn.commit(); 才能真正的对数据库进行操作
- 最好 清空一下Batch 小心出现脏数据 insertPs.clearBatch();

```java
public class Demo {
    static String user = "root";
    static String password = "tn@123456";
    static String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false";
    static String Driver = "com.mysql.cj.jdbc.Driver";



    public static void main(String[] args) {
        //建立一个结果集，用来保存查询出来的结果
        Connection getConn = JdbcDataBaseUtil.getConnection(user, password, url, Driver);
        Demo demo = new Demo();
        try {
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
```

## 5.3 配置Hikari 连接池，改进上述操作。提交代码到Github。
```java
//        Connection getConn = JdbcDataBaseUtil.getConnection(user, password, url, Driver);
        Connection getConn = HikariCP.getConnection(user, password, url, Driver);
```
