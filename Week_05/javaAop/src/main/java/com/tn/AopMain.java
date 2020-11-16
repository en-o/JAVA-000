package com.tn;

import com.tn.inter1.InterfaceTest;
import com.tn.inter2.InterfaceTest2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tn
 * @version 1
 * @ClassName AopMain
 * @description
 * @date 2020/11/16 10:32
 */
public class AopMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        InterfaceTest2 interfaceTest2 = context.getBean(InterfaceTest2.class);
        // aspectj
        InterfaceTest interfaceTest = context.getBean(InterfaceTest.class);
        interfaceTest.tn();
        System.out.println("\n");
        // aspectj
        interfaceTest2.tn2();
    }
}
