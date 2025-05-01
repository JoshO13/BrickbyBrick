package com.it326.BrickByBrick;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.crypto.Data;

public class Database{

    // Replace these with your actual Oracle DB details
    private static final String DB_URL = "jdbc:oracle:thin:@10.110.10.90:1521:oracle";
    private static final String DB_USER = "IT326T04";
    private static final String DB_PASSWORD = "enemy96";
    private Connection connection = null;
    public Database() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
    
    public Connection getConnection(){
        return this.connection;
    }

    public void close() throws SQLException{
        this.connection.close();
    }
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // If needed (usually optional in modern Java), load the Oracle driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection successful!");

        } catch (ClassNotFoundException e) {
            System.err.println("Could not find the Oracle JDBC driver. Make sure ojdbc.jar is on the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        } finally {
            // Close the connection if it was successfully opened
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Ignore or handle accordingly
                }
            }
        }
    }
}

