package com.tn;

import com.tn.entity.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tn
 * @version 1
 * @ClassName MainTEST
 * @description
 * @date 2020/11/16 12:35
 */
public class MainTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("custom.xml");
        Student student =(Student) context.getBean("student");
        System.out.println(student.toString());
        Student student1 =(Student) context.getBean("student1");
        System.out.println(student1.toString());
    }
}
