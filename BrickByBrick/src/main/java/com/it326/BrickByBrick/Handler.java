package com.it326.BrickByBrick;

import java.util.List;
import java.util.Date;

public class Handler {

    private List<Task> tasks;
    private List<Project> projects;
    private Database database;
    private AccountManager accountManager = new AccountManager();


    /**
     * Delete task method to delete; task passed as parameter
     * @param task
     * @return
     */
    public void deleteTask(Task task) {
        for (Task t : tasks) {
            if (t.getName().equals(task.getName())) {
                tasks.remove(t);
                break;
            }
        }
    }
    /**
     * Delete project method to delete; project passed as parameter
     * @param project
     */
    public void deleteProject(Project project) {
       for(Project p : projects){
        if (p.getName().equals(project.getName())) {
            projects.remove(p);
            break;
        }
       }
    }
    /**
     * Complete task method to complete; task passed as parameter
     * @param task
     * @return
     */
    public boolean completeTask(Task task) {
        int taskScore = task.getScore();
        accountManager.geAccount().setTotalScore(taskScore);
        deleteTask(task);
        System.out.println("Task completed!!");
        return true;
    }
    /**
     * Complete project method to complete; project passed as parameter
     * @param project
     * @param score
     * @return
     */
    public boolean completeProject(Project project) {
        deleteProject(project);
        System.out.println("Project completed!!");
        return true;
    }
    
    public void importTask(Task task) {
        //TO-DO: Implement this method
        //txt file
    }

    /**
     * Combine two tasks into one task. Tasks must be on the same day.
     * @param task1
     * @param task2
     */
    public void combineTasks(Task task1, Task task2) {
        //Precondition: Must be the same day
        if(task1.getDate().equals(task2.getDate())){
            Task newTask = new Task(task1.getName() + " & " + task2.getName(), task1.getPriorityLevel(), task1.getDate(), task1.getScore() + task2.getScore(), task1.getDifficultyLevel());
            tasks.remove(task1);
            tasks.remove(task2);
            tasks.add(newTask);
            System.out.println("Tasks combined.");
        }else
            System.out.println("Tasks are not on the same day.");
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

    public List<Task> getTasks() {
        return tasks;
    }   
}
