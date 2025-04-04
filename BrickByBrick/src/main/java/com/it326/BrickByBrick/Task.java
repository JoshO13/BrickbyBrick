package com.it326.BrickByBrick;
import java.util.Date;
//Task class
public class Task {

    private String taskName;
    private double priorityLevel;
    private Date date;
    private int score;
    private int difficultyLevel;
    private Handler handler;
    private TaskManager taskManager;
    private TaskSuggester taskSuggester;

    public Task(String taskName, double priorityLevel, Date date, int score, int difficultyLevel) {
        this.taskName = taskName;
        this.priorityLevel = priorityLevel;
        this.date = date;
        this.score = score;
        this.difficultyLevel = difficultyLevel;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public void setPriorityLevel(double priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
}
