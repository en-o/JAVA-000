package com.tn.handler;

import com.tn.entity.Student;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
 
 
public class StudentBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class getBeanClass(Element element){
        return Student.class;
    }
    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean){
        String name = element.getAttribute("name");
//        String id = element.getAttribute("id");
        String studentId = element.getAttribute("studentId");
        if (StringUtils.hasText(name)) {
            bean.addPropertyValue("name",name);
        }
//        if (StringUtils.hasText(id)) {
//            bean.addPropertyValue("id",id);
//        }
        if (StringUtils.hasText(studentId)) {
            bean.addPropertyValue("studentId",studentId);
        }
    }
}