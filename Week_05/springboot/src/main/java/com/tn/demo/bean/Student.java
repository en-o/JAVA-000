package com.tn.demo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tn
 * @version 1
 * @ClassName Student
 * @description 学生bean
 * @date 2020/11/13 13:53
 */
@Data
@Component
@ConfigurationProperties(prefix = "student")
public class Student {

    private int id;
    private String name;
}
