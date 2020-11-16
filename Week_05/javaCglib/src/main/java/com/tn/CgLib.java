package com.tn;

import com.tn.inerceptor.MyInerceptor;
import com.tn.inter1.Student;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author tn
 * @version 1
 * @ClassName CgLib
 * @description
 * @date 2020/11/16 9:49
 */
public class CgLib {
    public static void main(String[] args) {
        // CGLIB
        Enhancer enhancer = new Enhancer();
        //把需要代理的类 放进去
        enhancer.setSuperclass(Student.class);
        //回调函数 把 自己的拦截器 - 增强处理
        enhancer.setCallback(new MyInerceptor());
        //创建一个新的类  - 代理类
        Student student = (Student)enhancer.create();
        //调用代理类的方法
        student.s1();

    }
}
