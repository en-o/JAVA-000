package com.tn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tn
 * @version 1
 * @ClassName WebAppliction
 * @description
 * @date 2020/11/3 21:43
 */
@SpringBootApplication
@RestController
public class WebAppliction {
    @Value("${server.port:8080}")
    String port;

    public static void main(String[] args) {
        SpringApplication.run(WebAppliction.class, args);
    }

    @GetMapping(value = "/",produces = "application/json;charset=utf-8")
    public String test(HttpServletRequest request, HttpServletResponse response){
        String msg = "hello nio " + port;
        String nio =  request.getHeader("nio");
        System.out.println("请求头里内容是："+nio);
        return msg;
    }
}
