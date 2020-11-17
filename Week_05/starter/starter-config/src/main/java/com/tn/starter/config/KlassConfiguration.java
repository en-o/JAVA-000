package com.tn.starter.config;

import com.tn.starter.entity.Klass;
import com.tn.starter.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 *  自动装配
 * @author tn
 * @version 1
 * @ClassName AutoConfigrution
 * @description
 * @date 2020/11/17 20:29
 */
@Configuration
@Import({Student.class,Klass.class})
public class KlassConfiguration {

}
