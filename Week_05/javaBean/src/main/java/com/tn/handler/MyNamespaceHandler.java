package com.tn.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
 
public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("student",new StudentBeanDefinitionParser());
    }
}