package com.tn.aop;


import org.aspectj.lang.ProceedingJoinPoint;

public class Aop1 {
    
        //前置通知
        public void startTransaction(){
            System.out.println("    ====>前置通知... ");
        }
        
        //后置通知
        public void commitTransaction(){
            System.out.println("    ====>后置通知... ");
        }
        
        //环绕通知
        public void around(ProceedingJoinPoint joinPoint) throws Throwable{
            System.out.println("    ====>环绕通知前");
            //调用process()方法才会真正的执行实际被代理的方法
            joinPoint.proceed();
            
            System.out.println("    ====>环绕通知后");
        }
        
}
