package com.it326.BrickByBrick;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//Account Handler Interface
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AccountManager implements Manager<Account> {
    private Account account;
    private Database database;
    private Controller controller;
    private Journal journal;

    public AccountManager() throws SQLException{
        this.controller = controller;
        this.database = Database.getInstance();
        this.journal = new Journal();
    }

    /**
     * Method to create an account locally, and uses createInDatabase as a helper to create it in the database
     * @param username - username
     * @param password - password
     * @return account object created
     */
    public Account createAccount(String username, String password) {
        Account a1 = new Account(username, password);
        boolean ok = createInDatabase(a1, a1);
        if (ok) {
            a1.setAccountManager(this);
            return a1;
        }
        System.err.println("Failed");
        return null;
    }

    /**
     * Inserts a new account in the database
     * @param acc - account to be added
     * @return bool - whether the account was created (true) or not (false)
     */
    public boolean createInDatabase(Account acc, Account dummy) {
        if (acc == null) {
            return false;
        }
        String sql = "INSERT INTO account (username, password1, original_score, total_score) VALUES (?, ?, ?, ?)";
        try (Connection conn = database.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, acc.getLogin());
            statement.setString(2, acc.getPassword());
            statement.setInt(3, 0);
            statement.setInt(4, acc.getTotalScore());
            boolean ok = database.pushAccountQuery(statement);
            if (ok) {
                this.account = acc;
                acc.setAccountManager(this);
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an existing account in the database
     * @param acc - account to be deleted
     * @return bool - whether the account was deleted (true) or not (false)
     */
    public boolean deleteInDatabase(Account acc) {
        if (acc == null) {
            return false;
        }
        String sql = "DELETE FROM account WHERE username = ?";
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, acc.getLogin());
            boolean ok = database.pushAccountQuery(statement);
            if (ok && this.account == acc) {
                this.account = null;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to delete an account locally, and uses deleteInDatabase as a helper
     * @param username - username of account to be deleted
     * @return bool - created or not
     */
    public boolean deleteAccount(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        Account dummy = new Account(username,"");
        dummy.setAccountManager(this);
        boolean ok = deleteInDatabase(dummy);
        return ok;
    }

    /**
     * Updates an existing account to the current state
     * @param acc - Account to be updated
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean editInDatabase(Account acc) {
        if (acc == null) {
            return false;
        }
        String sql = "UPDATE account SET password1 = ?, total_score = ? WHERE username = ?";
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, acc.getPassword());
            statement.setInt(2, acc.getTotalScore());
            statement.setString(3, acc.getLogin());
            return database.pushAccountQuery(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to login to an account
     * @param login - username to login
     * @param password - password to login
     * @return bool - logged in or not
     */
    public boolean login(String login, String password) {
        try (Connection conn = database.getConnection()) {
            String sql = "SELECT * FROM ACCOUNT WHERE (username) = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, login);
            this.account = database.retrieveAccountQuery(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        if (account.getLogin().equals(login) && account.getPassword().equals(password)) {
            this.account.setLogin(login);
            this.account.setLoggedIn(true);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method to logout of an account
     * @param username - username to be logged out
     * @return bool - logged out or not
     */
    public boolean logout(String username) {
        try (Connection conn = database.getConnection()) {
            String sql = "SELECT * FROM ACCOUNT WHERE (username) = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            this.account = database.retrieveAccountQuery(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if (this.account.getLogin().equals(username) && this.account.isLoggedIn()) {
            this.account.setLoggedIn(false);
            return true;
        }
        return false;
    }

    /**
     * Updates an account password in the database
     * @param newPassword - new password
     * @return bool - whether the password was updated or not
     */
    public boolean editAccountPassword(String newPassword) {
        if (this.account == null) {
            return false;
        }
        String sql = "UPDATE account SET password1 = ? WHERE username = ?";
        try (Connection conn = database.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setString(2, this.account.getLogin());
            boolean ok = database.pushAccountQuery(statement);
            if (ok) {
                this.account.setPassword(newPassword);
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
     }

    

    /**
     * Updates an account username in the database
     * @param newUsername - new username
     * @return bool - whether the username was updated or not
     */
    public boolean editAccountUsername(String newUsername) {
        if (this.account == null) {
            return false;
        }
        String sql = "UPDATE account SET username = ? WHERE username = ?";
        try (Connection conn = database.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newUsername);
            statement.setString(2, this.account.getLogin());
            boolean ok = database.pushAccountQuery(statement);
            if (ok) {
                this.account.setLogin(newUsername);
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void Summary() {
        System.out.println(account);
        displayAverageFeelings();
    }


    /**
     * Updates an existing entry to the current state
     * @param entry - Entry to be updated
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean createInDatabase(Entry entry, Account dummy) {
        try (Connection conn = database.getConnection()) {
            String sql = "INSERT INTO ENTRY (text_string, entry_date, feeeling, username_e) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entry.getContent());
            statement.setDate(2, java.sql.Date.valueOf(entry.getDate()));
            statement.setInt(3, entry.getFeeling());
            statement.setString(4, account.getLogin());
            database.pushEntryQuery(statement);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an existing entry
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean deleteInDatabase(LocalDate date) {
        try (Connection conn = database.getConnection()) {
            String sql = "DELETE FROM ENTRY WHERE entry_date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, java.sql.Date.valueOf(date));
            database.pushEntryQuery(statement);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }


    /**
     * Edits an existing entry
     * @param entry - Entry to be changed
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean editInDatabase(Entry entry, Account account) {
        boolean ok = deleteInDatabase(entry.getDate());
        ok  = createInDatabase(entry, account);
        return ok;
    }

    /**
     * Method to retrieve a journal entry
     * @param date - primary key to select an entry
     * @return - returns the entry
     */
    public Entry retrieveEntry(LocalDate date){
        try(Connection conn = database.getConnection()){
            String sql = "SELECT * FROM ENTRY WHERE (entry_date) = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, java.sql.Date.valueOf(date));
            Entry entry2 = database.retrieveEntryQuery(statement);
            return entry2;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Method ot retrieve acc
     * @return - account
     */
    public Account getAccount() {
        return this.account;
    }

    /**
     * Method to retrieve an account
     * @param name - username to search for
     * @return bool - account found or not
     */
    public boolean findUser(String name){
        try(Connection conn = database.getConnection()){
            String sql = "SELECT username FROM ACCOUNT WHERE (username) = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            database.retrieveAccountQuery(statement);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to add new entry
     * @param content - content of the journal entry
     * @param feeling - feeling of that day
     * @param account - account to link to the entry
     * @return bool - entry added or not
     */
    public boolean addEntry(String content, int feeling, Account account){
        Entry e = new Entry (content,feeling);
        journal.addEntry(e);
        createInDatabase(e, account);
        return true;
    }

    /**
     * Displays average feeling
     */
    public void displayAverageFeelings(){
        journal.displayFeelingScale();
    }

    /**
     * Combines journal entries
     * @param date - primary key to combine entries
     */
    public void combineEntries(LocalDate date){
        journal.combineAllPastEntries();
        List<Entry> entries = journal.getEntriesByDate(date);
        for (int i = 0; i < entries.size();i++){
            deleteInDatabase(date);
            createInDatabase(entries.get(i), account);
        }
    }
}