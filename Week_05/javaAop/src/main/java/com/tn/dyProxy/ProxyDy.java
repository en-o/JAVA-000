package com.tn.dyProxy;


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
public class ProxyDy implements InvocationHandler {

    private Object object;

    public Object newProxyInstance(Object object) {
        this.object = object;

        ClassLoader loader = object.getClass().getClassLoader();
        Class[] interfaces = object.getClass().getInterfaces();

//        return Proxy.newProxyInstance(loader,
//                interfaces,new AopDy(object));

        // 如果两个方法同时使用这个代理类  那下面的this可能报错
            // Exception in thread "main" java.lang.IllegalArgumentException: object is not an instance of declaring class
            // 从我测试的结果来看，如果用this的情况下 同时调用了 2+ 次该类，那 此时的this指向最后一次调用的内存地址，导致以前的地址失效，从而报错
        return Proxy.newProxyInstance(loader,
                interfaces,this);
    }

    public ProxyDy(Object object) {
        this.object = object;
    }

    public ProxyDy() {
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
