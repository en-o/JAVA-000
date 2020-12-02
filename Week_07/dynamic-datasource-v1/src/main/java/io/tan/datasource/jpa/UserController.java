package io.tan.datasource.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tn
 * @version 1
 * @ClassName UserController
 * @description
 * @date 2020/12/3 0:44
 */
@RestController
public class UserController {


    @Autowired
    private UserServer userServer;


    @GetMapping("/testDataSource")
    public void testDataSource() {
        userServer.findAllDef().forEach((it) -> System.out.println("findAllDef"+it.toString()));
        System.out.println("======================================");
        userServer.findAllDataSource_1().forEach((it) -> System.out.println("findAllDataSource_1"+it.toString()));
        System.out.println("======================================");
        userServer.findAllDataSource_2().forEach((it) -> System.out.println("findAllDataSource_2"+it.toString()));
    }


    @GetMapping("/findAllDef")
    public void findAllDef() {
        userServer.findAllDef().forEach((it) -> System.out.println("findAllDef"+it.toString()));
    }

    @GetMapping("/findAllDataSource_1")
    public void findAllDataSource_1() {
        System.out.println("======================================");
        userServer.findAllDataSource_1().forEach((it) -> System.out.println("findAllDataSource_1"+it.toString()));
    }

    @GetMapping("/findAllDataSource_2")
    public void findAllDataSource_2() {
        System.out.println("======================================");
        userServer.findAllDataSource_2().forEach((it) -> System.out.println("findAllDataSource_1"+it.toString()));
    }
}
