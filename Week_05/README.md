# 2、（必做）写代码实现Spring Bean的装配，方式越多越好（XML、Annotation都可以）,提交到Github。
## [springboot 的方式]()
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
@ConfigurationProperties(prefix = "student")
public class Student {

    private int id;
    private String name;
}

package com.tn.demo;
import com.tn.demo.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
@SpringBootApplication
public class DemoApplication {
    @Autowired
    private Student student;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void demo(){
        System.out.println(student.toString());
    }
}
```
```properties
server.port=8007
student.id=1
student.name=谭宁

2020-11-13 14:22:16.539  INFO 20144 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 843 ms
Student(id=1, name=谭宁)
2020-11-13 14:22:16.695  INFO 20144 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'


```
## [spring Annotation 的方式]()
