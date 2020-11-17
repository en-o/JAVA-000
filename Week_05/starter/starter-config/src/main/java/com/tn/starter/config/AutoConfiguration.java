package com.tn.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author tn
 * @version 1
 * @ClassName AutoConfigrution
 * @description
 * @date 2020/11/17 20:47
 */
@Configuration
@Import({KlassConfiguration.class, SchoolConfiguration.class})
public class AutoConfiguration {
}
