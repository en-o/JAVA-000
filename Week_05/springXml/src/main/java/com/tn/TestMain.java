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
