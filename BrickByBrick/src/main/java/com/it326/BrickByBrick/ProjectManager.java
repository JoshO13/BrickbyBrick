package com.it326.BrickByBrick;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
public class ProjectManager implements Manager<Project> {

    private Database db;
    private List<Project> projects = new ArrayList<>();
    private Handler handler;
    public ProjectManager(Handler handler) {
        this.handler = handler;
    }
    public Connection getDatabaseConnection() throws SQLException {
        return handler.getDatabaseConnection();
    }
    public Project createProjectInDatabase(Project project){
       try (Connection conn = getDatabaseConnection()) {
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

    public Project deleteProjectFromDatabase(Project project){
        //TO-DO: implement this method
        //remove the below line when implemented
        return null;
    }
    public List<Project> getAllProjects() {
        //return all projects
    }
    
}
