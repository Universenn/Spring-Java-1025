package com.dao;

import com.connectionmaker.AwsConnectionMaker;
import com.connectionmaker.ConnectionMaker;
import com.statementstrategy.AddStrategy;
import com.statementstrategy.DeleteAllStrategy;
import com.statementstrategy.StatementStrategy;
import com.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

//    private ConnectionMaker connectionMaker;

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection c;
    private PreparedStatement ps;

//    public UserDao(ConnectionMaker connectionMaker) {
//        this.connectionMaker = connectionMaker;
//    }

    public void jdbcContextStatementStrategy(StatementStrategy stmst){
        c = null;
        ps = null;
        try {
            c = dataSource.getConnection();
            ps = stmst.getStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
            if (c != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
        }
    }
    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContextStatementStrategy(new DeleteAllStrategy());
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        jdbcContextStatementStrategy(new AddStrategy(user));
    }

    public User findById(String id) throws ClassNotFoundException, SQLException {
        c = dataSource.getConnection();
//        c = connectionMaker.makeConnection();

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
        c = null;
        ps = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();
//            c = connectionMaker.makeConnection();
            ps = c.prepareStatement("SELECT count(*) FROM users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
            if (c != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
        }
//        return count;
    }

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        UserDao userDao = new UserDao(new AwsConnectionMaker());
//        User user = new User("id", "name", "password");
//        userDao.add(user);
//        user = userDao.findById("id");
//        System.out.println(user.getName());
//    }
}