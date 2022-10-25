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

// 스프링의 테스트 컨텍스트 프레임워크의 JUnit 확장기능 지정
@ExtendWith(SpringExtension.class)
// 테스트 컨텍스트가 자동으로 만들어줄 애플리케이션 컨텍스트의 위치 고정
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    private UserDao userDao;
    User user1;
    User user2;
    User user3;
    @Autowired
    // 테스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 값이 주입된다
    private ApplicationContext context;

//    UserDao dao = context.getBean("awsUserDao", UserDao.class);
    @BeforeEach
    @DisplayName("테스트 시작")
    void setUp(){
        // Spring 도입
        userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("1","11","111");
        this.user2 = new User("2","22","222");
        this.user3 = new User("3","33","333");
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