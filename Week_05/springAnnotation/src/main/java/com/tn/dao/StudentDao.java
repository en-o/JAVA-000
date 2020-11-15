package com.tn.dao;

import com.tn.bean.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author tn
 * @version 1
 * @ClassName Student
 * @description 学生表
 * @date 2020/11/13 15:03
 */
@Repository("studentDao")
public class StudentDao {

    @Resource(name = "student")
    private Student student;

    public void print(){
        student.setId(1);
        student.setName("谭宁");
        System.err.println("你好："+student.toString());
    }
}
