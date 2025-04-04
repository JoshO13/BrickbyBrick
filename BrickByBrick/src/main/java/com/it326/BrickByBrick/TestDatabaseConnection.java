package com.it326.BrickByBrick;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            Statement stmt = conn.createStatement();

            // Simple test query for Oracle DB
            ResultSet rs = stmt.executeQuery("SELECT 'Connection Successful!' AS status FROM dual");

            if (rs.next()) {
                System.out.println(rs.getString("status"));
            }

        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

