package com.tn.starter.entity;

import com.tn.starter.intr.ISchool;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class School implements ISchool {

    @Autowired
    Klass class1;
    
    @Resource
    Student student100;
    
    @Override
    public void ding(){
    
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
        System.out.println(student100.toString());
        class1.dong();
    }
    
}
