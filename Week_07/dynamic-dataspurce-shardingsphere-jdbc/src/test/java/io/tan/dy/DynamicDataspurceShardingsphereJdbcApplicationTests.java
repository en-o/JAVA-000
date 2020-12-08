package io.tan.dy;

import io.tan.dy.jpa.UserDao;
import io.tan.dy.jpa.UserEntity;
import io.tan.dy.jpa.UserServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DynamicDataspurceShardingsphereJdbcApplicationTests {

    SnowFlake snowFlake = new SnowFlake(2, 3);

    @Autowired
    private UserServer userServer;

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void testJPASelect(){
        userDao.findAll().stream().
                forEachOrdered(
                        userEntity ->
                                System.out.println("userEntity = " + userEntity.toString())
                );
    }


    @Test
    public void testSSJInsert(){
        for (int i = 1; i <= 5; i++) {
            UserEntity userEntity = UserEntity.builder()
                    .userName("tan" + i)
                    .email(Math.round(13) + "@163.com")
                    .phone(String.valueOf(Math.round(13)))
                    .uuid(snowFlake.nextId())
                    .build();
            userDao.save(userEntity);
        }
    }

}
