package com.it326.BrickByBrick;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//Account class
public class Account {
    private String login;
    private String password;
    private AccountManager accountManager;
    private int totalScore;
    private int initialScore;
    private boolean isLoggedIn;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
        this.totalScore = 0;
        this.initialScore = 0;
        this.isLoggedIn = false;
    }
    public void setAccountManager(AccountManager manager) {
        this.accountManager = manager;
    }
    //Logic to change the password of an account in the database, given a new password
    public boolean changePassword(String newPassword) {
        if (!isLoggedIn) {
            return false;
        }
        this.password = newPassword;
        if (accountManager != null) {
            try (Connection conn = accountManager.getDatabaseConnection()) {
                String sql = "UPDATE accounts SET password = ? WHERE username = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, newPassword);
                statement.setString(2, login);
                int rows = statement.executeUpdate();
                return rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean changeUsername(String newUsername) {
        //TO-DO: implement the changeUsername method
        return false;
    }

    public boolean login(String username, String password) {
        //TO-DO: implement the login method
        return false;
    }

    public boolean logout() {
        //TO-DO: implement the logout method
        return false;
    }

    public boolean deleteAccount() {
        //TO-DO: implement the deleteAccount method
        return false;
    }
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getIntialScore() {
        return intialScore;
    }

}
