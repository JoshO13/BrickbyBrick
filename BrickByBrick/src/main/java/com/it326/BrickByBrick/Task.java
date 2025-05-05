package com.it326.BrickByBrick;

import java.util.Date;

//Task class
public class Task implements Cloneable {

    private String taskName;
    private int priorityLevel;
    private Date date;
    private int score;

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

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
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
        try {
            Task clone = (Task) super.clone();
            // Deep copy the Date object to prevent shared references
            clone.date = date != null ? new Date(date.getTime()) : null;
            // No deep copy needed for primitives/Strings
            return clone;
        } catch (CloneNotSupportedException e) {
            // This shouldn't happen since we implement Cloneable
            throw new AssertionError();
        }
    }

    public String toString() {
        return taskName + "{" +
                " Priority Level= " + priorityLevel +
                ", Due Date= " + date +
                ", Score= " + score +
                '}';
    }

}
