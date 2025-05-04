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
    private boolean isLoggedIn;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
        this.totalScore = 0;
        this.isLoggedIn = false;
    }
    public void setAccountManager(AccountManager manager) {
        this.accountManager = manager;
    }
    //Logic to change the password of an account in the database, given a new password
    public boolean changePassword(String newPassword) {
        if (!isLoggedIn || accountManager == null) {
            return false;
        }
        boolean ok = accountManager.editAccountPassword(this, newPassword);
        if (ok) {
            this.password = newPassword;
        }
        return ok;
    }

    public boolean changeUsername(String newUsername) {
        if (!isLoggedIn || accountManager == null) {
            return false;
        }
        boolean ok = accountManager.editAccountUsername(this, newUsername);
        if (ok) {
            this.login = newUsername;
        }
        return ok;
    }
    //Logic to log a user in
    //Want to ensure login/logout functionality before introducing password hashing
    public boolean login(String username, String password) {
        if (this.login == username && this.password == password) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }
    //Logic to log a user out
    public boolean logout() {
        if (!isLoggedIn) {
            return false;
        }
        this.isLoggedIn = false;
        if (accountManager != null) {
            accountManager.editInDatabase(this);
        }
        return true;
    }
    //Logic to request the manager to delete the account
    public boolean deleteAccount() {
        if (accountManager == null) {
            return false;
        }
        return accountManager.deleteInDatabase(this);
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

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setInitialScore(int initialScore) {
        this.initialScore = initialScore;
    }
}
