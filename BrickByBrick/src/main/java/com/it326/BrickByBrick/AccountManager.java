package com.it326.BrickByBrick;

import java.sql.SQLException;
//Account Handler Interface


public class AccountManager implements Manager<Account> {
    private Account account;
    private Database database;
    private Controller controller;

    public AccountManager(Controller controller) {
       this.controller = controller;
       this.database = Database.getDatabase();
    }

    /**
     * Inserts a new account in the database
     * @param acc - account to be added
     * @return bool - whether the account was created (true) or not (false)
     */
    public boolean createInDatabase(Account acc) {
        if (acc == null) {
            return false;
        }
        String sql = String.format("INSERT INTO accounts (username,password,is_logged_in) " + "Values ('%s','%s',%b)",
                acc.getLogin(), acc.getPassword(), acc.isLoggedIn());
        boolean ok = database.pushAccountQuery(sql);
        if (ok) {
            this.account = acc;
            acc.setAccountManager(this);
        }
        return ok;
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
        String sql = String.format("DELETE FROM accounts WHERE username='%s'", acc.getUsername());
        boolean ok = database.pushAccountQuery(sql);
        if (ok && this.account = acc) {
            this.account = null;
        }
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
        String sql = String.format("UPDATE accounts SET password='%s',is_logged_in=%b " + "WHERE username='%s'", acc.getPassword(), acc.isLoggedIn(), acc.getLogin());
        return database.pushAccountQuery(sql);
    }

    /**
     * Updates an account password in the database
     * @param acc - account to be updated
     * @param newPassword - new password
     * @return bool - whether the password was updated or not
     */
    public boolean editAccountPassword(Account acc, String newPassword) {
        if (acc == null) {
            return false;
        }
        String sql = String.format("UPDATE accounts SET password='%s' WHERE username='%s'", newPassword, acc.getLogin());
        boolean ok = database.pushAccountQuery(sql);
        if (ok) {
            acc.setPassword(newPassword);
        }
        return ok;
    }

    /**
     * Updates an account username in the database
     * @param acc - account to be updated
     * @param newUsername - new username
     * @return bool - whether the username was updated or not
     */
    public boolean editAccountUsername(Account acc, String newUsername) {
        if (acc == null) {
            return false;
        }
        String sql = String.format("UPDATE accounts SET username='%s' WHERE username='%s'", newUsername, acc.getLogin());
        boolean ok = database.pushAccountQuery(sql);
        if (ok) {
            acc.setLogin(newUsername);
        }
        return ok;
    }

    
    /**
     * Updates an existing entry to the current state
     * @param entry - Entry to be updated
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean createEntryInDatabase(Entry entry) {
        if (entry == null) {
            return false;
        }
        String sql = String.format(
            "INSERT INTO ENTRY (text_string, entry_date, feeeling, username_e) " +
            "VALUES ('%s', '%s', %d, '%s')",
            entry.getContent(), entry.getDate(), entry.getFeeling(), account.getLogin()
        );
        boolean ok = database.pushEntryQuery(sql);
        if (ok) {
            this.entry = entry;
        }
        return ok;
    }


    /**
     * Deletes an existing entry 
     * @param entry - Entry to be deleted
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean deleteEntryInDatabase(Entry entry) {
        if (entry == null) {
            return false;
        }
        String sql = String.format(
            "DELETE FROM ENTRY WHERE text_string='%s'",
            entry.getContent()
        );
        boolean ok = database.pushEntryQuery(sql);
        if (ok && this.entry == entry) {
            this.entry = null;
        }
        return ok;
    }

    /**
     * Edits an existing entry 
     * @param entry - Entry to be changed
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean editEntryInDatabase(Entry entry) {
        booolean ok = deleteEntryInDatabase(entry);
        ok  = createEntryInDatabase(entry);
        return ok;
    }  
}
