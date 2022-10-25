package com.dao;

import com.statementstrategy.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void executeQuery(String query) throws SQLException {
        jdbcContextStatementStategy(new StatementStrategy() {
            @Override
            public PreparedStatement getStatement(Connection c) throws SQLException {
                return c.prepareStatement(query);
            }
        });
    }
    public void jdbcContextStatementStategy(StatementStrategy stmst) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;
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
}
