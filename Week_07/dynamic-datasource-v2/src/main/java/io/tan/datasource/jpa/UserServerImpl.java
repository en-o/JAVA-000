package io.tan.datasource.jpa;

import io.tan.datasource.dynamic.annotation.TargetDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

/**
 * @author tn
 * @version 1
 * @ClassName UserServerImpl
 * @description
 * @date 2020/12/3 2:13
 */
@Service
public class UserServerImpl implements UserServer {

    @Autowired
    private UserDao userDao;


    @Override
    public List<UserEntity> findAllDef(){
        return userDao.findAll();
    }


    @Override
    @TargetDataSource(dbname = "slave1")
    public List<UserEntity> findAllDataSource_1(){
        return userDao.findAll();
    }

    @Override
    @TargetDataSource(dbname = "slave2")
    public List<UserEntity> findAllDataSource_2(){
        return userDao.findAll();
    }

}
