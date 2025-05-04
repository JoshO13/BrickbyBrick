package com.it326.BrickByBrick;

import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectManager implements Manager<Project> {

    private Database db;
    private List<Project> projects = new ArrayList<>();
    AccountManager accManager = new AccountManager();
    String oldName;

    /**
     * Constructor; initialized database
     * 
     * @throws SQLException
     */
    public ProjectManager() throws SQLException {
        this.db = Database.getInstance();
    }

    /**
     * create project in database
     * 
     * @param project
     */
    public boolean createInDatabaseTest(Project project, Account account) {
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO project (username_p, project_name) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, account.getLogin());
            statement.setString(2, project.getName());
            db.pushProjectQuery(statement);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createInDatabase(Project project) {
        // Need get account??
        String username = accManager.getAccount().getUsername();
        String password = accManager.getAccount().getPassword();
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO project (username_p, project_name) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, project.getName());
            db.pushProjectQuery(statement);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * delete the project in database based on name
     * 
     * @return
     */
    public boolean deleteInDatabase(Project project) {
        //String username = accManager.getAccount().getUsername();
        //String password = accManager.getAccount().getPassword();
        try (Connection conn = db.getConnection()) {
            // Need to have where username??
            String sql = "DELETE FROM PROJECT WHERE project_name = (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, project.getName());
            db.pushProjectQuery(statement);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * edit the project in database
     * Only thing possible to edit is name
     * 
     * @param project
     */
    public boolean editInDatabase(Project p) {
        String newName = p.getName();
                try (Connection conn = db.getConnection()) {
            String sql = "UPDATE project SET (project_name) = (?) WHERE project_name = (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setString(2, oldName);
            db.pushProjectQuery(statement);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
 
    } 
    public Project retrieveProject(String retrieveName) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM project WHERE (project_name) = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, retrieveName);
            Project project = db.retrieveProjectQuery(statement);
            return project;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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
    public boolean createProject(String name, Account account) {
        Project project = new Project(name);
        projects.add(project);
        System.out.println(projects.toString());
        boolean flag = createInDatabaseTest(project, account);
        if (flag){
            System.out.println("Project created.");
            return true;
        }
        else {
            System.out.println("Creating project in database failed");
            return false;    
        }
    }

    /**
     * Delete project method to delete; project passed as parameter
     * 
     * @param project
     */
    public boolean deleteProject(String name) {
        Iterator<Project> iterator = projects.iterator();
        while (iterator.hasNext()) {
            Project p = iterator.next();
            if (p.getName().equals(name)) {
                iterator.remove();
                p = new Project(name);
                deleteInDatabase(p);
                System.out.println("Project deleted.");
                return true;
            }
        }
        System.out.println("Could not find project");
        return false;
    }

    /**
     * Edit project method to edit; project passed as parameter
     * Menu to edit project. Can edit name, date
     * 
     * @param project
     */
    public boolean editProject(Project project) {
        // store old name for updating
        oldName = project.getName();
        System.out.println("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
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
        editInDatabase(project);
        scanner.close();
        return true;
    }

    public List<Project> getAllProjects(){
        return projects;
    }
}
