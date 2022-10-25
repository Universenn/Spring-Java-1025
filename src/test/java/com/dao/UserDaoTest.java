package com.dao;

import com.domain.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
//        인터페이스 불러오기
//        UserDao userDao = new UserDao(new AwsConnectionMaker());
//      UserDaoFactory 불러오기
        UserDao userDao = new UserDaoFactory().awsUserDao();
        User user = new User("1","11","111");
//        userDao.add(user);
        User findId = userDao.findById(user.getId());
        assertEquals(user.getId(),findId.getId());
    }
}