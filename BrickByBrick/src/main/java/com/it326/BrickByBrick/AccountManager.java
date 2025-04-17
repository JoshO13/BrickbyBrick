package com.it326.BrickByBrick;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//Account Handler Interface


public class AccountManager implements Manager<Account> {
    private Account account;
    private Handler handler;

    public AccountManager(Handler handler) {
       this.handler = handler;
    }
    //obtain db connection through the handler
    public Connection getDatabaseConnection() throws SQLException {
        return handler.getDatabaseConnection();
    }
    //Logic to create an account in the database from an account object
    public Account createAccountInDatabase(Account acc) {
       if (acc == null) {
           System.out.println("No account provided to create.");
           return null;
       }
       try (Connection conn = getDatabaseConnection()) {
           String sql = "INSERT INTO accounts (username, password, is_logged_in) VALUES (?, ?, ?)"
           PreparedStatement statement = conn.prepareStatement(sql);
           statement.setString(1, acc.getLogin());
           statement.setString(2, acc.getPassword());
           //cont
           int rows = statement.executeUpdate();
           if (rows > 0) {
               this.account = acc;
               acc.setAccountManager(this);
               return acc;
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
    }
    //Logic to delete an account in the database from an account object
    public Account deleteAccountInDatabase(Account acc) {
        if (acc == null) {
            System.out.println("No account provided to delete.");
            return null;
        }
        try (Connection conn = getDatabaseConnection()) {
            String sql = "DELETE FROM accounts WHERE login = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            stmt.setString(1, acc.getLogin());
            int rows = statement.executeUpdate();
            if (rows > 0) {
                Account deleted = acc;
                if (this.account == acc) {
                    this.account = null;
                }
                return deleted;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
