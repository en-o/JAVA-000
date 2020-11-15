package com.tn;

import com.tn.aop.AopTest;
import com.tn.aop.AopTest2;
import com.tn.aop.AopTestImpl;
import com.tn.aop.AopTestImpl2;
import com.tn.dyaop.AopDy;
import com.tn.dyaop.AopDy2;
import com.tn.staop.AopSt;

import javax.security.auth.Subject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author tn
 * @version 1
 * @ClassName Aop
 * @description
 * @date 2020/11/15 21:55
 */
public class Aop {

    public static void main(String[] args) {

        AopTest aopTest = new AopTestImpl();
        AopTest2 aopTest2 = new AopTestImpl2();

        /*******************动态代理**************/
        AopDy handler = new AopDy();

        //代理
        AopTest dyAop = (AopTest) handler.newProxyInstance(aopTest);
        AopTest2 dyAop2 = (AopTest2) handler.newProxyInstance(aopTest2);
//        //调用方法
//        dyAop.tn();
        dyAop2.tn2();
        /*******************动态代理**************/

        System.out.println("\n");

        /*******************静态代理**************/
        AopSt stAop = new AopSt(aopTest);
        stAop.tn();
        /*******************静态代理**************/
    }

}
