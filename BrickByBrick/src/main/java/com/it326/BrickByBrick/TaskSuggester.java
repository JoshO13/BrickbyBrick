package com.it326.BrickByBrick;
import java.util.Date;
public class TaskSuggester {

    private String weather;
    private String location;
    private Date date;

    public TaskSuggester(String weather, String location, Date date) {
        this.weather = weather;
        this.location = location;
        this.date = date;
    }
    public Date suggestDifferDate(Task task) {
        //TO-DO: suggest a different date
        return null;
    }

    public TaskSuggester getTaskSuggester() {
        return this;
    }
    
}
