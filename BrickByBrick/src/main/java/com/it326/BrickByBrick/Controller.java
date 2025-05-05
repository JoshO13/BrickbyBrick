//what works 

package com.it326.BrickByBrick;

import java.util.Scanner;
import java.sql.Date;
import java.sql.SQLException;


//controller class that checks the decisions given by the CMD class inputted by the user
public class Controller 
{

//declairing objects to later be used by the program
    private CMD cmd;
    private AccountManager am;
    private TaskManager tm;
    private ProjectManager pm;
    private Account acc;
    private String username;


//default constructior that initailizes the earlier declared objects 
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
//a method that generates the decisions for logging into a user account
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
                    acc = new Account(username, pw);
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
             
            default:
                System.out.println("Please enter a valid choice.");
        }
        return false;
    }

//a method that handles the decisions for the main menu of the program
    public boolean generateDecision(int input) 
    {
        //declaring variables for later use and scanner
        String taskname;
        String taskname2;
        String projectName;
        Task temp;
        Task temp1;
        Scanner sc = new Scanner(System.in);

        switch (input) 
        {
            case 1: // Task Management
                System.out.println("Task Menu: \n1. Create Task\n2. Delete Task\n3. Complete Task\n4. Share Task\n5. Import Task\n6. Combine Tasks\n7. Show all Tasks");
                int taskChoice = sc.nextInt();
                sc.nextLine(); 

                switch (taskChoice) 
                {

                    //create task
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

                        tm.createTask(name, date, priority, score, acc);
                        
                        break;
                        
                    //delete Task
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

                    //mark task as complete
                    case 3:
                        System.out.println("Enter task name to mark as complete:");
                        String compTask = sc.nextLine();
                        boolean completed = tm.completeTask(compTask);

            
                        break;

                    //share task
                    case 4:
                        System.out.println("Please enter the name of the task you would like to share.");
                        taskname = sc.nextLine();

                        temp = tm.searchTaskName(taskname);

                        if (temp != null) {
                            tm.shareTask(temp);
                        }
                        break;

                    //import task
                    case 5:
                        System.out.println("Please enter the filename of the task you would like to import.");
                        String filename = sc.nextLine();

                        tm.importTask(filename, acc);
                        break;

                        //combine tasks
                    case 6:
                        System.out.println("Please enter a task to combine.");
                        taskname = sc.nextLine();
                        temp = tm.searchTaskName(taskname);
                        if(temp != null)
                        {
                            System.out.println("Please enter the second task to combine.");
                            taskname2 = sc.nextLine();
                            temp1 = tm.searchTaskName(taskname2);
                            if (temp1 !=null) 
                            {
                                tm.combineTasks(temp, temp1, acc);
                            }
                        }

                    case 7: 
                        tm.printAllTasks();

                        break;
                    default:
                        System.out.println("Invalid option.");
                }
                break;

            case 2: // Project Management
                System.out.println("Project Menu: \n1. Create Project\n2. Delete Project\n3. Edit Project\n4. Add Task to Project");
                int projChoice = sc.nextInt();
                sc.nextLine();

                switch (projChoice) 
                {
                    //create project
                    case 1:
                        System.out.println("Enter project name:");
                        String pname = sc.nextLine();


                        pm.createProject(pname,acc);
                        
                        break;

                        //delete Project
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
                        
                        //edit project
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

                    //add entry
                    case 1:
                        System.out.println("Enter your journal content:");
                        String content = sc.nextLine();
                        System.out.println("Feeling scale (1-10):");
                        int feeling = sc.nextInt();
                        sc.nextLine();

                        // Need to get current user’s Journal instance
                        
                        boolean entryAdded = am.addEntry(content, feeling, acc);

                        if (entryAdded) {
                            System.out.println("Entry added successfully.");
                        }else{
                            System.out.println("Entry could not be added.");
                        }
                        
                        break;

                        //display average feelings
                    case 2:
                       
                        am.displayAverageFeelings();
                        
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
                break;

            case 4:
                
                String username1;
                System.out.println("1.Edit Password\n2. Account Summary");

                int manageAccount = sc.nextInt();
                sc.nextLine();
                switch (manageAccount) {
                    
                    //edit password
                    case 1:
                        System.out.println("Enter your new account password.");
                        username1 = sc.nextLine();

                        if (am.editAccountPassword(username1)) {
                            System.out.println("Account password successfully changed.");
                        }else{
                            System.out.println("Account password could not be changed.");
                        }
                        
                        break;                       

                    //account summary
                    case 2:
                        am.Summary();
                        break;
                    default:
                        System.out.println("Please enter a valid choice!");
                }

            //logout of the account
            case 5:
                am.logout(username);
                return false;
                

            default:
                System.out.println("Invalid category input.");
        }
        return false;
    }
}
