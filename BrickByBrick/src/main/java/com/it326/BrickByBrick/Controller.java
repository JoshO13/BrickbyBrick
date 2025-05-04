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

    public Controller(CMD cmd) 
    {
        this.cmd = cmd;
        Database db = Database.getDatabase(); 
        this.am = new AccountManager(new Handler(db));
        this.tm = new TaskManager();
        this.pm = new ProjectManager();

        tm.setDatabase(db);
        pm.setDatabase(db);
    }

    public AccountManager getAccountManager() 
    {
        return am;
    }

    public void generateAccountDecsions(int input)
    {
        Scanner sc = new Scanner(System.in);

        switch (input) 
        {
            case 1:
                System.out.println("Enter username:");
                String userName = sc.nextLine();

                System.out.println("Enter password:");
                String pw = sc.nextLine();

                Account loginAttempt = new Account(userName, pw);
                if (loginAttempt.login(userName, pw)) 
                {
                    
                    System.out.println("Successfully logged in!\n");
                } else 
                {
                    System.out.println("Username/Password was incorrect. Please try again.");
                }
                break;
            case 2:
                System.out.println("Enter new username:");
                            String newUser = sc.nextLine();

                            //need logic to check if a username has already been used
                            if (am.findUser(newUser)) 
                            {
                                System.out.println("Username already taken. Please try again.");
                            } else 
                            {
                                System.out.println("Enter new password:");
                                String newPw = sc.nextLine();
                                
                                am.createAccount(newUser, newPw);
                            }
                break;
            case 3:

            default:
                System.out.println();
        }
    }


    public void generateDecision(int input) 
    {

        Scanner sc = new Scanner(System.in);

        switch (input) 
        {
            case 1: // Task Management
                System.out.println("Task Menu: \n1. Create Task\n2. View Tasks\n3. Delete Task\n4. Complete Task");
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
                        
                        System.out.println("Displaying all tasks:");

                        tm.displayAllTasks();
                        
                        break;
                    case 3:
                        System.out.println("Enter task name to delete:");
                        String delTask = sc.nextLine();



                        tm.deleteTask(delTask);
                        System.out.println("Task deleted if it existed.");
                        break;


                    case 4:
                        System.out.println("Enter task name to mark as complete:");
                        String compTask = sc.nextLine();
                        tm.completeTask(compTask);

                        System.out.println("Task marked as complete.");
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
                System.out.println("Journal Menu: \n1. Add Entry\n2. View Feelings\n3. Average Feeling");
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
                        
                        journal.addEntry(content, feeling);
                        System.out.println("Entry added.");
                        break;
                    case 2:
                       
                        journal.displayFeelingScale();
                        break;
                    case 3:
                        
                        double avg = journal.getAverageFeeling();
                        System.out.println("Average Feeling: " + avg);
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
