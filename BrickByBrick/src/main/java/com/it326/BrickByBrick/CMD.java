package com.it326.BrickByBrick;

import java.util.Scanner;

public class CMD 
{
    private Account acc;
    private AccountManager am;
    private boolean loop = true;
    private Controller controller;

    public CMD() 
    {
        
    }

    public void displayMenu() 
    {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Brick by Brick!");

        while (loop) 
        {
            // Login/register/quit
            while (acc == null) 
            {
                System.out.println("\n1. Login\n2. Register\n3. Quit");

                try {
                    int choice = input.nextInt();
                    input.nextLine(); 

                    switch (choice) {
                        case 1:
                            controller.generateAccountDecsions(choice);
                            break;

                        case 2:
                            controller.generateAccountDecsions(choice);
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

                    // clearing invalid input
                    input.nextLine(); 
                }
            }

            // App Functions
            while (acc != null) 
            {
                System.out.println("\nMain Menu:");
                System.out.println("1. Manage Tasks\n2. Manage Projects\n3. Manage Journal\n4. Logout");

                try {
                    int option = input.nextInt();
                    input.nextLine(); 

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
                            acc.logout();
                            acc = null;
                            System.out.println("You have been logged out.");
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a valid number.");
                    input.nextLine(); 
                }
            }
        }

        input.close();
    }

    public void getInput(int input) {
        controller.generateDecision(input);
    }

}
