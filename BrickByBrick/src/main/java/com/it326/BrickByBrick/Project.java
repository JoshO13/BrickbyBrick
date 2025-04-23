package com.it326.BrickByBrick;
import java.util.List;
//Project class
public class Project{

    private String name;
    private List<Task> taskList;
    private boolean isCompleted;
    private Handler handler;
    private ProjectManager projectManager;

    public Project(String name, List<Task> taskList, boolean isCompleted, Handler handler, ProjectManager projectManager) {
        this.name = name;
        this.taskList = taskList;
        this.isCompleted = isCompleted;
        this.handler = handler;
        this.projectManager = projectManager;
    }
    public Project(String name, List<Task> taskList) {
        this.name = name;
        this.taskList = taskList;
    }
    public Project(String name) {
        this.name = name;
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

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }
}