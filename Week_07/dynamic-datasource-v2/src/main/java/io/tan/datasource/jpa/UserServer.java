package io.tan.datasource.jpa;

import io.tan.datasource.dynamic.annotation.TargetDataSource;
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

public interface UserServer {

    List<UserEntity> findAllDef();

    List<UserEntity> findAllDataSource_1();

    List<UserEntity> findAllDataSource_2();
}


