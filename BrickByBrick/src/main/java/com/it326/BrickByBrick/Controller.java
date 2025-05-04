package com.it326.BrickByBrick;

import java.util.Scanner;
import java.sql.Date;



public class Controller 
{

    private CMD cmd;
    private AccountManager am;
    private TaskManager tm;
    private ProjectManager pm;
    private Journal journal;
    private Account acc;

    public Controller(CMD cmd) 
    {
        this.cmd = cmd;      
    }

    public AccountManager getAccountManager() 
    {
        return am;
    }

    public boolean generateAccountDecsions(int input)
    {
        Scanner sc = new Scanner(System.in);

        switch (input) 
        {

            //login
            case 1:
                System.out.println("Enter username:");
                String userName = sc.nextLine();

                System.out.println("Enter password:");
                String pw = sc.nextLine();

                
                if (am.login(userName, pw)) 
                {
                    System.out.println("Successfully logged in!\n");
                    return true;
                } else 
                {
                    System.out.println("Username/Password was incorrect. Please try again.");
                    return false;
                }


                //register
            case 2:
                System.out.println("Enter new username:");
                String newUser = sc.nextLine();

                //need logic to check if a username has already been used
                if (am.findUser(newUser)) 
                {
                    System.out.println("Username already taken. Please try again.");
                    return false;
                } else 
                {
                    System.out.println("Enter new password:");
                    String newPw = sc.nextLine();
                    
                    am.createAccount(newUser, newPw);
                    return true;
                }
            
            case 3:

            default:
                System.out.println("Please enter a valid choice.");
        }
        return false;
    }


    public void generateDecision(int input) 
    {

        Scanner sc = new Scanner(System.in);

        switch (input) 
        {
            case 1: // Task Management
                System.out.println("Task Menu: \n1. Create Task\n2. Delete Task\n3. Complete Task");
                int taskChoice = sc.nextInt();
                sc.nextLine(); 

                switch (taskChoice) 
                {
                    case 1:
                        System.out.println("Enter task name:");
                        String name = sc.nextLine();

                        System.out.println("Enter priority (1-5):");
                        int priority = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Enter date (yyyy-mm-dd):");
                        String dateStr = sc.nextLine();
                        Date date = Date.valueOf(dateStr); 

                        System.out.println("Enter Priority Level:");
                        int score = sc.nextInt();

                        tm.createTask(name, date, priority, score);
                        System.out.println("Task created.");
                        break;
                    case 2:
                        System.out.println("Enter task name to delete:");
                        String delTask = sc.nextLine();



                        boolean deleted = tm.deleteTask(delTask);

                        if (deleted) {
                            System.out.println("Task has been deleted.");
                        }else{
                            System.out.println("Task could not be deleted.");
                        }
                        
                        break;


                    case 3:
                        System.out.println("Enter task name to mark as complete:");
                        String compTask = sc.nextLine();
                        boolean completed = tm.completeTask(compTask);

                        if (completed) {
                            System.out.println("Task marked as complete.");
                        }else{
                            System.out.println("Task could not marked as complete.");
                        }
                        
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
                break;

            case 2: // Project Management
                System.out.println("Project Menu: \n1. Create Project\n2. Delete Project");
                int projChoice = sc.nextInt();
                sc.nextLine();

                switch (projChoice) 
                {
                    case 1:
                        System.out.println("Enter project name:");
                        String pname = sc.nextLine();


                        pm.createProject(pname);
                        System.out.println("Project created.");
                        break;
                    case 2:
                        System.out.println("Enter project name to delete:");
                        String delProject = sc.nextLine();

                        boolean deleted = pm.deleteProject(delProject);

                        if (deleted) 
                        {
                            System.out.println("Project has been deleted.");
                        }else{
                            System.out.println("Project does not exist.");
                        }
                        
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
                break;

            case 3: // Journal Entry 
                System.out.println("Journal Menu: \n1. Add Entry\n2. Display Average Feelings\n");
                int journalChoice = sc.nextInt();
                sc.nextLine();

                switch (journalChoice) 
                {
                    case 1:
                        System.out.println("Enter your journal content:");
                        String content = sc.nextLine();
                        System.out.println("Feeling scale (1-10):");
                        int feeling = sc.nextInt();
                        sc.nextLine();

                        // Need to get current user’s Journal instance
                        
                        boolean entryAdded = am.addEntry(content, feeling);

                        if (entryAdded) {
                            System.out.println("Entry added successfully.");
                        }else{
                            System.out.println("Entry could not be added.");
                        }
                        
                        break;
                    case 2:
                       
                        am.getAverageFeeling();
                        
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
                break;

            default:
                System.out.println("Invalid category input.");
        }
    }
}
