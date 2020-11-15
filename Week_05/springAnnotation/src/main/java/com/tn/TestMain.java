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
