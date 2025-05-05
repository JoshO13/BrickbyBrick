package com.it326.BrickByBrick;

import java.nio.file.PathMatcher;
import java.sql.SQLException;
import java.util.Scanner;

public class CMD 
{
    private Account acc;
    private AccountManager am;
    private boolean loop = true;
    private Controller controller;

    public CMD() throws SQLException
    {
        this.controller = new Controller(this);        
    }

    public void displayMenu() 
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Brick by Brick!");
        boolean loginAttempt = false;
        while (loop) 
        {
            // Login/register/quit
            while (loginAttempt == false) 
            {
                System.out.println("\n1. Login\n2. Register\n3. Quit");

                try {
                    int choice = input.nextInt();

                    switch (choice) {
                        case 1:
                            loginAttempt = controller.generateAccountDecsions(choice);
                            break;

                        case 2:
                            loginAttempt = controller.generateAccountDecsions(choice);
                            break;
                            
                        case 3:
                            System.out.println("Thank you for trying Brick by Brick!");
                            loop = false;
                            return;

                        default:
                            System.out.println("Invalid option.");
                    }
                } catch (Exception e) 
                {
                    System.out.println("Invalid input. Please enter a number.");
                    e.printStackTrace();

                    // clearing invalid input
                    input.nextLine(); 
                }
            }

            // App Functions
            

            while (loginAttempt) 
            {
                System.out.println("\nMain Menu:");
                System.out.println("1. Manage Tasks\n2. Manage Projects\n3. Manage Journal\n4.Manage Account\n5. Logout");

                try {
                    int option = input.nextInt();

                    switch (option) {
                        case 1:
                            controller.generateDecision(1);
                            break;
                        case 2:
                            controller.generateDecision(2);
                            break;
                        case 3:
                            controller.generateDecision(3);
                            break;
                        case 4:
                            loginAttempt = controller.generateDecision(4);
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a valid number.");
                    e.printStackTrace();
                    input.nextLine(); 
                }
            }
        }

        input.close();
    }

    public void getInput(int input) 
    {
        controller.generateDecision(input);
    }


    public static void main(String[] args) throws SQLException {
        CMD userInp = new CMD();
        userInp.displayMenu();
    }

}
