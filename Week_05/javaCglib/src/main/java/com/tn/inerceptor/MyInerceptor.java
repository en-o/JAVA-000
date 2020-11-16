package com.tn.inerceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author tn
 * @version 1
 * @ClassName MyInerceptor
 * @description
 * @date 2020/11/16 9:42
 */
public class MyInerceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("操作前 - 我要拦截你，带上我的东西出去");
        return methodProxy.invokeSuper(o,objects);
    }
}
