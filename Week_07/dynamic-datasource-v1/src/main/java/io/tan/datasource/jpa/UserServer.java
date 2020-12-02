package io.tan.datasource.jpa;

import io.tan.datasource.dynamicData.TargetDataSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tn
 * @version 1
 * @ClassName UserServer
 * @description
 * @date 2020/12/3 0:22
 */
@Service
public class UserServer {

    @Resource
    private UserDao userDao;


    public List<UserEntity> findAllDef(){
        return userDao.findAll();
    }


    @TargetDataSource(name = "mysql1")
    public List<UserEntity> findAllDataSource_1(){
        return userDao.findAll();
    }

    @TargetDataSource(name = "mysql2")
    public List<UserEntity> findAllDataSource_2(){
        return userDao.findAll();
    }
}


