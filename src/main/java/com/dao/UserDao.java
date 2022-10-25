package com.dao;

import com.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {

    private ConnectionMaker connectionMaker;

    private Connection c;
    private PreparedStatement ps;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        c = connectionMaker.makeConnection();

        ps = c.prepareStatement("delete from users");

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        c = connectionMaker.makeConnection();
        ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        if(user == null) throw new EmptyResultDataAccessException(1);

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User findById(String id) throws ClassNotFoundException, SQLException {
        c = connectionMaker.makeConnection();

        ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        c = connectionMaker.makeConnection();
        ps = c.prepareStatement("SELECT count(*) FROM users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao(new AwsConnectionMaker());
        User user = new User("id", "name", "password");
        userDao.add(user);
        user = userDao.findById("id");
        System.out.println(user.getName());
    }
}