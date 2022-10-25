package com.dao;

import com.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {

    ConnectionMaker connectionMaker;

    public void add(User user) throws SQLException {
        Map<String, String> env = System.getenv();
        Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                env.get("DB_USER"), env.get("DB_PASSWORD"));

        // Query문 작성
        PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());

        // Query문 실행
        pstmt.executeUpdate();

        pstmt.close();
        c.close();
    }

    public User findById(String id) throws SQLException {
        Map<String, String> env = System.getenv();
        Connection c;
        // DB접속 (ex sql workbeanch실행)
        c = DriverManager.getConnection(env.get("DB_HOST"),
                env.get("DB_USER"), env.get("DB_PASSWORD"));

        // Query문 작성
        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
        pstmt.setString(1, id);

        // Query문 실행
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"),
                rs.getString("password"));

        rs.close();
        pstmt.close();
        c.close();
        return user;

    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }
}