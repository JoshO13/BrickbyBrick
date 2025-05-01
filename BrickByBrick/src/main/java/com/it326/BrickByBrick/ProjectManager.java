package com.it326.BrickByBrick;

import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
public class ProjectManager implements Manager<Project> {

    private Database db;
    private List<Project> projects = new ArrayList<>();
    
    public ProjectManager() {
    }
    public Project createInDatabase(Project project){
       try (Connection conn = db.getConnection()){
           String sql = "INSERT INTO projects (name, is_completed) VALUES (?, ?)";
           PreparedStatement statement = conn.prepareStatement(sql);
           statement.setString(1, project.getName());
           statement.setString(2, project.isCompleted());
           if (statement.executeUpdate() > 0) {
               project.setHandler(handler);
               project.setProjectManager(this);
               projects.add(project); //adds project to list of projects
               return project;
           }
       } catch(SQLException e) {
           e.printStackTrace();
       }
        return null;
    }

    public Project deleteInDatabase(Project project){
        //TO-DO: implement this method
        //remove the below line when implemented
        return null;
    }
    public List<Project> getAllProjects() {
        //return all projects
        return projects;
    }
     /**
     * Create project and add to list
     * @param name
     * @param date
     * @param taskList
     * @param isCompleted
     */
    public void createProject(String name, List<Task> taskList, boolean isCompleted) {
        Project project = new Project(name, taskList);
        projects.add(project);
        createInDatabase(project);
        System.out.println("Project created.");
    }

    /**
     * Delete project method to delete; project passed as parameter
     * @param project
     */
    public void deleteProject(Project project) {
        for(Project p : projects){
         if (p.getName().equals(project.getName())) {
             projects.remove(p);
             deleteInDatabase(project);
             System.out.println("Project deleted.");
             break;
         }
        }
     }

     /**
     * Edit project method to edit; project passed as parameter
     * Menu to edit project. Can edit name, date
     * @param project
     */
    public void editProject(Project project){
        System.out.println("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Date");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            //edit name
            case 1:
                System.out.println("Enter new name: ");
                String newName = scanner.next();
                project.setName(newName);
                break;
            //edit date No more date??
            // case 2:
            //     System.out.println("Enter new date: ");
            //     Date newDate = new Date(scanner.nextLong());
            //     project.setDate(newDate);
            //     break;
            //exit
            case 3:
                System.out.println("Exiting");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        
    }
    
}
