package com.tn.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tn
 * @version 1
 * @ClassName Student
 * @description 学生表
 * @date 2020/11/13 15:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String name;
}
