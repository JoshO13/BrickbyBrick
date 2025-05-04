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


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalScore() {
        return totalScore;
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
}