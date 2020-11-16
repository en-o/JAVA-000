package com.tn.inter1.impl;

import com.tn.inter1.InterfaceTest;

/**
 * @author tn
 * @version 1
 * @ClassName AopTestImpl  接口实现类
 * @description
 * @date 2020/11/15 22:00
 */
public class InterfaceTestImpl implements InterfaceTest {

    @Override
    public void tn() {
        System.out.println("测试 接口代理");
    }
}
