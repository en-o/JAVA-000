package io.tan.dy.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<UserEntity> findAllDataSource_1(){
        return userDao.findAll();
    }

    @Override
    public List<UserEntity> findAllDataSource_2(){
        return userDao.findAll();
    }

}
