package com.tn.staop;

import com.tn.aop.AopTest;

/**
 * @author tn
 * @version 1
 * @ClassName AopTestImpl  接口实现类
 * @description
 * @date 2020/11/15 22:00
 */
public class AopSt implements AopTest {

    private AopTest aopTest;

    public AopSt(AopTest aopTest) {
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
