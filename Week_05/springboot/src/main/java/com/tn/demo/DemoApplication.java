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
