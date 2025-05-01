package com.it326.BrickByBrick;
import java.util.Date;
//Task class
public class Task implements Cloneable{

    private String taskName;
    private int priorityLevel;
    private Date date;
    private int score;
    private TaskManager taskManager;
    private TaskSuggester taskSuggester;

    public Task(String taskName, Date date, int priorityLevel, int score) {
        this.taskName = taskName;
        this.date = date;
        this.priorityLevel = priorityLevel;
        this.score = score;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public Date getDate(){
        return date;
    }

    public String getName() {
        return taskName;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }
    @Override
    public Task clone(Task task) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clone'");
    }

    
}
