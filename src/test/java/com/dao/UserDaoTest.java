package com.dao;

import com.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    private UserDao userDao;
    User user1;
    User user2;
    User user3;
    @Autowired
    ApplicationContext context;
//    UserDao dao = context.getBean("awsUserDao", UserDao.class);
    @BeforeEach
    @DisplayName("테스트 시작")
    void setUp(){
        // Spring 도입
        userDao = context.getBean("awsUserDao", UserDao.class);
        user1 = new User("1","11","111");
        user2 = new User("2","22","222");
        user3 = new User("3","33","333");
    }
    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
//        인터페이스 불러오기
//        userDao = new UserDao(new AwsConnectionMaker());
//        UserDaoFactory 불러오기
//        userDao = new UserDaoFactory().awsUserDao();
        userDao.deleteAll();
        userDao.add(user1);

        User findId = userDao.findById(user1.getId());
        assertEquals(user1.getName(),findId.getName());
    }
    @Test
    void delete() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        User findId = userDao.findById(user1.getId());
        assertEquals(user1.getName(),findId.getName());
    }
    @Test
    void count() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertEquals(userDao.getCount(),0);
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        assertEquals(userDao.getCount(),3);
    }
}