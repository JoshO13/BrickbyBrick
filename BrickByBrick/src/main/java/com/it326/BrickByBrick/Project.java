package com.it326.BrickByBrick;
import java.util.List;
//Project class
public class Project{

    private String name;
    private List<Task> taskList;
    private boolean isCompleted;
    private ProjectManager projectManager;

    public Project(String name) {
        this.name = name;
        this.taskList = taskList;
        this.isCompleted = false;
    }
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

}