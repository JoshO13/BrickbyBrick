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
        String sql = "INSERT INTO accounts (username, password, total_score) " +
                "VALUES (?, ?, ?)";
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, acc.getLogin());
            statement.setString(2, acc.getPassword());
            statement.setString(3, acc.getTotalScore());
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
        String sql = "DELETE FROM accounts WHERE username = ?";
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getUsername());
            boolean ok = database.pushAccountQuery(ps);
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
     * Updates an existing account to the current state
     * @param acc - Account to be updated
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean editInDatabase(Account acc) {
        if (acc == null) {
            return false;
        }
        String sql =
                "UPDATE accounts SET password = ?, total_score = ? WHERE username = ?";
        try (Connection conn = database.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, acc.getPassword());
            statement.setInt(2, acc.getTotalScore());
            statement.setString(3, acc.getUsername());
            return database.pushAccountQuery(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO ENTRY (text_string, entry_date, feeeling, username_e) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entry.getContent());
            statement.setDate(2, java.sql.Date.valueOf(entry.getDate()));
            statement.setInt(3, entry.getFeeling());
            statement.setString(4, account.getLogin());

            db.pushEntryQuery(statement);
            conn.close();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an existing entry
     * @param entry - Entry to be deleted
     * @return bool - whether the changes were successfully pushed or not
     */
    public boolean deleteEntryInDatabase(Entry entry) {
        try (Connection conn = db.getConnection()) {
            String sql = "DELETE FROM ENTRY WHERE text_string = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entry.getContent());
            db.pushEntryQuery(statement);
            conn.close();
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
    public boolean editEntryInDatabase(Entry entry) {
        booolean ok = deleteEntryInDatabase(entry);
        ok  = createEntryInDatabase(entry);
        return ok;
    }
}
