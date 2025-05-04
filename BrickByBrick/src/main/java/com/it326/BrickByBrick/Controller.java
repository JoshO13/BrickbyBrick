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
        Database db = Database.getDatabase(); // singleton pattern
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

    public Handler getHandler() 
    {
        return new Handler(Database.getDatabase()); // used to assign to Account
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

                        tm.createTask(name, priority, date);
                        System.out.println("Task created.");
                        break;
                    case 2:
                        
                        System.out.println("Displaying all tasks:");

                        for (Task t : tm.getTasks()) {
                            t.getTaskName();
                        }
                        
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

                        System.out.println("Enter date (yyyy-mm-dd):");
                        String pdate = sc.nextLine();

                        pm.createProject(pname, Date.valueOf(pdate));
                        System.out.println("Project created.");
                        break;
                    case 2:
                        System.out.println("Enter project name to delete:");
                        String delProject = sc.nextLine();

                        // finding project then deleting
                        boolean deleted = false;
                        for (Project p : pm.getAllProjects()) 
                        {
                            if(p.getName().equals(delProject)) 
                            {
                                pm.deleteProject(p);
                                deleted = true;
                            }
                        }

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
