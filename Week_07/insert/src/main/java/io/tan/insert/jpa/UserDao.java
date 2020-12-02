package io.tan.insert.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tn
 * @version 1
 * @ClassName UserDao
 * @description
 * @date 2020/12/2 23:45
 */
public interface UserDao extends JpaRepository<UserEntity,Long> {
}
