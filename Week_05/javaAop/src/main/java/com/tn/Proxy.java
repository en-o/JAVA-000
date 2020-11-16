package com.tn;

import com.tn.inter1.InterfaceTest;
import com.tn.inter2.InterfaceTest2;
import com.tn.inter1.impl.InterfaceTestImpl;
import com.tn.inter2.impl.InterfaceTestImpl2;
import com.tn.dyProxy.ProxyDy;
import com.tn.stProxy.ProxySt;

/**
 * @author tn
 * @version 1
 * @ClassName Aop
 * @description
 * @date 2020/11/15 21:55
 */
public class Proxy {

    public static void main(String[] args) {

        InterfaceTest aopTest = new InterfaceTestImpl();
        InterfaceTest2 aopTest2 = new InterfaceTestImpl2();

        /*******************动态代理**************/
        ProxyDy handler = new ProxyDy();

        //代理
        InterfaceTest dyProxy = (InterfaceTest) handler.newProxyInstance(aopTest);
        InterfaceTest2 dyProxy2 = (InterfaceTest2) handler.newProxyInstance(aopTest2);
//        //调用方法
//        dyProxy.tn();
        dyProxy2.tn2();
        /*******************动态代理**************/

        System.out.println("\n");

        /*******************静态代理**************/
        ProxySt stProxy = new ProxySt(aopTest);
        stProxy.tn();
        /*******************静态代理**************/
    }

}
