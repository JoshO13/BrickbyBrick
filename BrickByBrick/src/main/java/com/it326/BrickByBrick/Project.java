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
    


}