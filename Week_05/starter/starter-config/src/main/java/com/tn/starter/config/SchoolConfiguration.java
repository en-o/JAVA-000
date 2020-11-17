package com.tn.starter.config;

import com.tn.starter.entity.Klass;
import com.tn.starter.entity.School;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  自动装配
 * @author tn
 * @version 1
 * @ClassName AutoConfigrution
 * @description
 * @date 2020/11/17 20:29
 */
@Configuration
public class SchoolConfiguration {
    @Bean
    public School school(){
        return new School();
    }
}
