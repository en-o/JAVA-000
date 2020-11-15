package com.tn.dyaop;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author tn
 * @version 1
 * @ClassName InvocationHandlerImpl
 * @description
 * @date 2020/11/15 22:04
 */
public class AopDy2 implements InvocationHandler {

    private Object object;

    public AopDy2(Object object) {
        this.object = object;
    }

    /**
     * 负责集中处理动态代理类上的所有方法调用。
     * 调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行
     * @param proxy  代理类实例
     * @param method 被调用的方法对象
     * @param args   调用参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理 - 调用之前");


        //调用目标方法
        Object returnValue = method.invoke(object, args);



        System.out.println("动态代理 - 调用之后");

        return returnValue;
    }
}
