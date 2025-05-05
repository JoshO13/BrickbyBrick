package com.it326.BrickByBrick;

import java.util.Scanner;
import java.sql.Date;
import java.sql.SQLException;



public class Controller 
{

    private CMD cmd;
    private AccountManager am;
    private TaskManager tm;
    private ProjectManager pm;
    private Journal journal;
    private Account acc;
    private String username;
    private Account account;

    public Controller(CMD cmd) throws SQLException 
    {
        this.cmd = cmd;     
        this.am = new AccountManager(); 
        this.tm = new TaskManager();
        this.pm = new ProjectManager();
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
                username = sc.nextLine();

                System.out.println("Enter password:");
                String pw = sc.nextLine();

                
                if (am.login(username, pw)) 
                {
                    System.out.println("Successfully logged in!\n");
                    account = new Account(username, pw);
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
                if (!am.findUser(newUser)) 
                {
                    System.out.println("Username already taken. Please try again.");
                    return false;
                } else 
                {
                    System.out.println("Enter new password:");
                    String newPw = sc.nextLine();
                    
                    am.createAccount(newUser, newPw);
                    return false;
                }
            
            case 3:

            default:
                System.out.println("Please enter a valid choice.");
        }
        return false;
    }


    public boolean generateDecision(int input) 
    {
        String taskname;
        String taskname2;
        String projectName;
        Task temp;
        Task temp1;
        Scanner sc = new Scanner(System.in);

        switch (input) 
        {
            case 1: // Task Management
                System.out.println("Task Menu: \n1. Create Task\n2. Delete Task\n3. Complete Task\n4. Edit Task\n5. Share Task\n6. Import Task\n7. Combine Tasks");
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

                        System.out.println("Enter score:");
                        int score = sc.nextInt();

                        tm.createTask(name, date, priority, score, account);
                        System.out.println("Task created.");
                        break;
                    case 2:
                        System.out.println("Enter task name to delete:");
                        String delTask = sc.nextLine();

                        boolean deleted = tm.deleteTask(delTask);

                        if (deleted) 
                        {
                            System.out.println("Task has been deleted.");
                        }else{
                            System.out.println("Task could not be deleted.");
                        }
                        
                        break;


                    case 3:
                        System.out.println("Enter task name to mark as complete:");
                        String compTask = sc.nextLine();
                        boolean completed = tm.completeTask(compTask);

                        if (completed) 
                        {
                            System.out.println("Task marked as complete.");
                        }else{
                            System.out.println("Task could not marked as complete.");
                        }
                        
                        break;

                    case 4:
                        System.out.println("Please enter the name of the task you would like to edit.");
                        taskname = sc.nextLine();

                        temp = tm.searchTaskName(taskname);

                        if (tm.editTask(temp)) {
                            System.out.println("Task edited successfully.");
                        }
                       
                        break;
                    case 5:
                        System.out.println("Please enter the name of the task you would like to share.");
                        taskname = sc.nextLine();

                        temp = tm.searchTaskName(taskname);

                        if (temp != null) {
                            tm.shareTask(temp);
                        }
                        break;
                    
                    case 6:
                        System.out.println("Please enter the filename of the task you would like to import.");
                        String filename = sc.nextLine();

                        tm.importTask(filename);
                        break;
                    case 7:
                        System.out.println("Please enter a task to combine.");
                        taskname = sc.nextLine();
                        temp = tm.searchTaskName(taskname);
                        if(temp != null)
                        {
                            System.out.println("Please enter the second task to combine.");
                            taskname = sc.nextLine();
                            temp1 = tm.searchTaskName(taskname);
                            if (temp1 !=null) 
                            {
                                tm.combineTasks(temp, temp1);
                            }
                        }

                        break;
                    default:
                        System.out.println("Invalid option.");
                }
                break;

            case 2: // Project Management
                System.out.println("Project Menu: \n1. Create Project\n2. Delete Project\n3. Edit Project\n4.Add Task to Project");
                int projChoice = sc.nextInt();
                sc.nextLine();

                switch (projChoice) 
                {
                    case 1:
                        System.out.println("Enter project name:");
                        String pname = sc.nextLine();


                        pm.createProject(pname,account);
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
                        
                        case 3:
                            System.out.println("Enter the name of the project you want to edit.");
                            projectName = sc.nextLine();
                            Project tempProject = pm.retrieveProject(projectName);

                            if (tempProject != null) 
                            {
                                pm.editProject(tempProject);
                            }else{
                                System.out.println("Project could not be found.");
                            }
                        break;

                        case 4: 
                            System.out.println("Enter the name of the Project you would like to add to.");
                            projectName = sc.nextLine();

                            System.out.println("Enter the name of the pre-existing task you would like to add to the project.");
                            taskname = sc.nextLine();
                            
                            tempProject = pm.retrieveProject(projectName);
                            temp = tm.searchTaskName(taskname);

                            if (pm.addTaskToProject(taskname, projectName)) {
                                System.out.println("Task added successfully.");
                            }else{
                                System.out.println("Task could not be added.");
                            }


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
                        
                        boolean entryAdded = am.addEntry(content, feeling, account);

                        if (entryAdded) {
                            System.out.println("Entry added successfully.");
                        }else{
                            System.out.println("Entry could not be added.");
                        }
                        
                        break;
                    case 2:
                       
                        am.displayAverageFeelings();
                        
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
                break;

            case 4:
                int manageAccount = sc.nextInt();
                String username1;
                System.out.println("1.Edit Username\n2. Edit Password\n3. Account Summary\n4. Delete Account");

                switch (manageAccount) {
                    case 1:
                        System.out.println("Enter your new account username.");
                        username1 = sc.nextLine();

                        if (am.editAccountUsername(username1)) {
                            System.out.println("Account username successfully changed.");
                        }else{
                            System.out.println("Account username could not be changed.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter your new account password.");
                        username1 = sc.nextLine();

                        if (am.editAccountPassword(username1)) {
                            System.out.println("Account username successfully changed.");
                        }else{
                            System.out.println("Account username could not be changed.");
                        }
                        
                        break;
                    case 3:
                        System.out.println("Deleting account...");

                        am.deleteAccount(username);

                        System.out.println("Account has been deleted.");
                        return false;

                    case 4:
                        am.Summary();
                        break;
                    default:
                        System.out.println("Please enter a valid choice!");
                }

            case 5:
                am.logout(username);
                return false;
                

            default:
                System.out.println("Invalid category input.");
        }
        return false;
    }
}
