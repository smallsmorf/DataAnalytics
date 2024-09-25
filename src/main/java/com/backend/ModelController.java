package com.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ModelController {
    protected Connection connect() {
        String url = "jdbc:sqlite:src/main/java/com/backend/assignment2.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
