package com.dao;

import com.connectionmaker.AwsConnectionMaker;
import com.connectionmaker.ConnectionMaker;
import com.statementstrategy.AddStrategy;
import com.statementstrategy.DeleteAllStrategy;
import com.statementstrategy.StatementStrategy;
import com.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private Connection c;
    private PreparedStatement ps;

    public void deleteAll(){
//        this.dataSource = dataSource;
        this.jdbcTemplate.update("delete from users");
    }

    public void add(User user){
        this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES (?, ?, ?)",user.getId(),user.getName(),user.getPassword());

    }

    public User findById(String id){
        String sql = "SELECT * FROM users WHERE id = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
                return user;
            }
        };
        return jdbcTemplate.queryForObject(sql,rowMapper,id);
    }


    public int getCount(){
        String sql = "SELECT count(*) FROM users";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }


}