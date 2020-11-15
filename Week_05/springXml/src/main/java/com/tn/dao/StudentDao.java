package com.tn.dao;

import com.tn.bean.Student;
import lombok.Data;

import javax.annotation.Resource;

/**
 * @author tn
 * @version 1
 * @ClassName StudenDap
 * @description
 * @date 2020/11/15 21:25
 */
@Data
public class StudentDao {

    private Student student;

    public void print(){
        student.setId(2);
        student.setName("谭宁");
        System.err.println("你好："+student.toString());
    }
}
