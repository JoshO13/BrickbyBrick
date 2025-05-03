package com.it326.BrickByBrick;

import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectManager implements Manager<Project> {

    private Database db;
    private List<Project> projects = new ArrayList<>();
    private String oldName;

    /**
     * Constructor; initialized database
     * 
     * @throws SQLException
     */
    public ProjectManager() throws SQLException {
        db = Database.getInstance();
    }

    /**
     * create project in database
     * 
     * @param project
     */
    public void createInDatabase(Project project) {
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO project (name) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, project.getName());
            db.pushProjectQuery(statement);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete the project in database based on name
     * 
     * @return
     */
    public void deleteInDatabase(Project project) {
        try (Connection conn = db.getConnection()) {
            String sql = "DELETE FROM PROJECT (name) WHERE (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, project.getName());
            db.pushProjectQuery(statement);
            db.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * edit the project in database
     * Only thing possible to edit is name
     * 
     * @param project
     */
    public void editInDatabase(Project project) {
        String newName = project.getName();
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE SET (name) = (?) WHERE (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setString(2, oldName);
            db.pushProjectQuery(statement);
            db.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Create project and add to list
     * 
     * @param name
     * @param date
     * @param taskList
     * @param isCompleted
     */
    public void createProject(String name) {
        Project project = new Project(name);
        projects.add(project);
        createInDatabase(project);
        System.out.println("Project created.");
    }

    /**
     * Delete project method to delete; project passed as parameter
     * 
     * @param project
     */
    public void deleteProject(Project project) {
        for (Project p : projects) {
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
     * 
     * @param project
     */
    public void editProject(Project project) {
        // store old name for updating
        oldName = project.getName();
        System.out.println("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        // if choice is integer
        if ((Integer) choice instanceof Integer) {
            switch (choice) {
                // edit name
                case 1:
                    System.out.println("Enter new name: ");
                    String newName = scanner.next();
                    project.setName(newName);
                    break;
                // exit
                case 2:
                    System.out.println("Exiting");
                    break;
                // invalid
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } else {
            System.out.println("Enter a number (1,2,etc)");
        }
        editInDatabase(project);
        scanner.close();

    }

}
