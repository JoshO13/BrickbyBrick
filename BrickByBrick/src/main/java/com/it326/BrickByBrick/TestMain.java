package com.it326.BrickByBrick;

import java.sql.SQLException;
import java.util.Scanner;

public class TestMain {
    
    public static void main(String[] args)throws SQLException {
        Scanner scan = new Scanner(System.in);
        AccountManager am = new AccountManager();
        ProjectManager pm;
            pm = new ProjectManager();
            // TODO Auto-generated catch block
        System.out.println("Testing main");
        System.out.println("Create a project");
        //System.out.println("Enter name");
        //String name = scan.nextLine();
        Account account = new Account("usernameTest8", "PasswordTest8");
        am.createInDatabase(account);
        //pm.createProject(name, account);
        Project p = pm.retrieveProject("School2");
        pm.deleteInDatabase(p);

    }
}
