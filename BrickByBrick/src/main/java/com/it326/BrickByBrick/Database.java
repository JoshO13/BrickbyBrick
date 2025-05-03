package com.it326.BrickByBrick;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;

public class Database {

    // Replace these with your actual Oracle DB details
    private static final String DB_URL = "jdbc:oracle:thin:@10.110.10.90:1521:oracle";
    private static final String DB_USER = "IT326T04";
    private static final String DB_PASSWORD = "enemy96";
    private Connection connection = null;
    private static Database database = null;

    private Database() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static synchronized Database getInstance() throws SQLException {
            if (database == null) {
                database = new Database();
            }
                return database;
            
        
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public boolean pushTaskQuery(PreparedStatement statement) {

        try {
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            System.out.println("Updating task in database failed");
            exception.getMessage();
            return false;
        }

    }

    public boolean pushProjectQuery(PreparedStatement statement) {
        try {
            statement.executeUpdate();
            statement = null;
            return true;
        } catch (SQLException exception) {
            System.out.println("Updating Project failed");
            exception.printStackTrace();
            return false;
        }
    }

    public boolean pushAccountQuery(PreparedStatement statement) {
        try {
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            System.out.println("Updating account failed");
            exception.printStackTrace();
            return false;
        }
    }

    public void pushEntryQuery(PreparedStatement statement) {
        try {
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Updating entry failed");
            exception.getMessage();
        }
    }

    public Task retrieveTaskQuery(String sqlStatement) {
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(sqlStatement);
            if (rs.next()) {
                int taskID = rs.getInt("task_id");
                String name = rs.getString("task_name");
                int score = rs.getInt("score");
                int priority = rs.getInt("priority");
                Date dueDate = rs.getDate("due_date");
                String user = rs.getString("username");

                Task retrievedTask = new Task(name, dueDate, priority, score);
                return retrievedTask;
            }
        } catch (SQLException exception) {
            System.out.println("Retrieving task failed");
            exception.getMessage();
        }
        return null;
    }

    public Project retrieveProjectQuery(PreparedStatement sqlStatement) {
        Project retrievedProject= null;
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = sqlStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("project_name");
                String username = rs.getString("username_p");
                retrievedProject = new Project(name);
            }
            return retrievedProject;
        } catch (SQLException exception) {
            System.out.println("Retrieving Project failed");
            exception.printStackTrace();
        }
        return null;
    }

    public Account retrieveAccountQuery(String sqlStatement) {
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(sqlStatement);
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int orignalScore = rs.getInt("original_score");
                int totalScore = rs.getInt("total_score");
                Account account = new Account(username, password);
                return account;
            }
        } catch (SQLException exception) {
            System.out.println("Retrieving account failed");
            exception.getMessage();
        }
        return null;
    }

    public Entry retrieveEntry(String sqlStatement) {
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(sqlStatement);
            if (rs.next()) {
                String text = rs.getString("text_string");
                Date date = rs.getDate("entry_date");
                int feeling = rs.getInt("feeling");
                String user = rs.getString("username");
                Entry entry = new Entry(text, feeling);
                return entry;
            }
        } catch (SQLException exception) {
            System.out.println("Retrieving entry failed");
            exception.getMessage();
        }
        return null;
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
