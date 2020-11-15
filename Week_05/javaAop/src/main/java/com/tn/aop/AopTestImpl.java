package com.tn.aop;

/**
 * @author tn
 * @version 1
 * @ClassName AopTestImpl  接口实现类
 * @description
 * @date 2020/11/15 22:00
 */
public class AopTestImpl implements AopTest {

    @Override
    public void tn() {
        System.out.println("测试 接口代理");
    }
}
