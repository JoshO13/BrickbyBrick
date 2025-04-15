package com.it326.BrickByBrick;

import java.util.List;
import java.util.Date;
import java.sql.SQLException;
public class Handler {

    private int score;
    private List<Task> tasks;
    private List<Project> projects;
    private Database database;
    private AccountManager accountManager;

    public Connection getDatabaseConnection() throws SQLException {
        //return database.connection();
        //implement when database instance available
    }

    public boolean deleteTask(Task task) {
        //TO-DO: Implement this method
        return false;
    }
    public boolean completeTask(Task task) {
        //TO-DO: Implement this method
        return false;
    }
    public boolean completeTask(Task task, int score){
        //TO-DO: Implement this method
        return false;
    }

    public boolean completeProject(Project project, int score) {
        //TO-DO: Implement this method
        return false;
    }

    public void importTask(Task task) {
        //TO-DO: Implement this method
    }

    public Task combineTasks(Task task1, Task task2) {
        //TO-DO: Implement this method
        return null;
    }
    public Task searchTaskName(String name) {
        //TO-DO: Implement this method
        return null;
    }

    public Task filterByDate(Date date){
        //TO-DO: Implement this method
        return null;
    }

    public Task filterByPriority(int priority){
        //TO-DO: Implement this method
        return null;
    }

    public void shareTask(Task task) {
        //TO-DO: Implement this method
    }

    public void summary() {
        //TO-DO: Implement this method
    }
    
    public Task editTask(Task task){
        //TO-DO: Implement this method
        return null;
    }

    public Project editProject(Project project){
        //TO-DO: Implement this method
        return null;
    }

    public void copyTask(Task task) {
        //TO-DO: Implement this method
    }
}
