package com.statementstrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy{

    @Override
    public PreparedStatement getStatement(Connection c) throws SQLException {
        return c.prepareStatement("delete from users");
    }
}
