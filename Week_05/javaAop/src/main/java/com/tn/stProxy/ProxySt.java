package com.tn.stProxy;

import com.tn.inter1.InterfaceTest;

/**
 * @author tn
 * @version 1
 * @ClassName AopTestImpl  接口实现类
 * @description
 * @date 2020/11/15 22:00
 */
public class ProxySt implements InterfaceTest {

    private InterfaceTest aopTest;

    public ProxySt(InterfaceTest aopTest) {
        this.aopTest = aopTest;
    }

    @Override
    public void tn() {
        System.out.println("静态代理 - 调用之前");

        //调用
        aopTest.tn();

        System.out.println("静态代理 - 调用之后");

    }
}
