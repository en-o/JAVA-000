package io.tan.datasource.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author tn
 * @version 1
 * @ClassName UserDao
 * @description
 * @date 2020/12/2 23:45
 */
public interface UserDao extends JpaRepository<UserEntity,Long> {
}
