package com.it326.BrickByBrick;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import oracle.jdbc.driver.parser.util.Array;
public class TaskManager {

    //data members
    private Database db;
    private List<Task> tasks = new ArrayList<Task>();

    //Constructor
    public TaskManager() {
        
    }

    /**
     * Create task in database
     * @return
     */
    Task createTaskInDatabase(){
       //TO-DO: create a task in the database
        return null;
    }

    /**
     * Delete task in the database
     * @return
     */
    Task deleteTaskInDatabase(){
        //TO-DO: delete a task in the database
        return null;
    }

     /**
     * Create task and add to list
     * @param name
     * @param priorityLevel
     * @param date
     * @param score
     * @param difficultyLevel
     */
    public Task createTask(String name, int priorityLevel, Date date, int score) {
        Task task = new Task(name, priorityLevel, date, score);
        tasks.add(task);
        System.out.println("Task created.");
        return task;
    }

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
     * Complete task method to complete; task passed as parameter
     * @param task
     * @return
     */
    public boolean completeTask(Task task) {
        int taskScore = task.getScore();
        //How to do score from here
        //task.setScore(taskScore);
        deleteTask(task);
        System.out.println("Task completed!!");
        return true;
    }

    /**
     * Import task from file
     * @param filename
     */
    public void importTask(String filename) {
        //TO-DO: Implement this method
        //txt file
        Task newTask = readFile(filename);
        tasks.add(newTask);
        System.out.println("Task imported.");
    }

     /**
     * Combine two tasks into one task. Tasks must be on the same day.
     * @param task1
     * @param task2
     */
    public void combineTasks(Task task1, Task task2) {
        //Precondition: Must be the same day
        if(task1.getDate().equals(task2.getDate())){
            Task newTask = new Task(task1.getName() + " & " + task2.getName(), task1.getPriorityLevel(), task1.getDate(), task1.getScore() + task2.getScore());
            tasks.remove(task1);
            tasks.remove(task2);
            tasks.add(newTask);
            System.out.println("Tasks combined.");
        }else
            System.out.println("Tasks are not on the same day.");
    }

    /**
     * Search task by name in task list
     * @param name
     * @return
     */
    public Task searchTaskName(String name) {
        for(int i =0; i < tasks.size(); i++){
            if(tasks.get(i).getName().equals(name)){
                return tasks.get(i);
            }
        }
        System.out.println("Task not found.");
        return null;
    }

    /**
     * Filter task by date
     * @param date
     * @return
     */
    public Task filterByDate(Date date){
        public List<Task> filterByDate(Date date){
        List<Task> result = new ArrayList<>();
        for(Task t : tasks){
            if(t.getDate().equals(date)){
                result.add(t);
            }
        }
        return result;
    }

    public Task filterByPriority(int priority){
         List<Task> result = new ArrayList<>();
        for(Task t : tasks){
            if(t.getPriorityLevel() == priority){
                result.add(t);
            }
        }
        return result;
        return null;
    }

    public void shareTask(Task task) {
        //TO-DO: Implement this method
        String filename = task.getName() + ".txt";
        writeFile(filename);
    }

    /**
     * Edit task method to edit; task passed as parameter
     * Menu to edit task. Can edit name, priority, date, difficulty
     * @param task
     */
    public void editTask(Task task){
        System.out.println("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Priority");
        System.out.println("3. Date");
        System.out.println("4. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            //edit name
            case 1:
                System.out.println("Enter new name: ");
                String newName = scanner.next();
                task.setTaskName(newName);
                break;
            //edit priority
            case 2:
                System.out.println("Enter new priority: ");
                int newPriority = scanner.nextInt();
                task.setPriorityLevel(newPriority);
                break;
            //edit date
            case 3:
                System.out.println("Enter new date: ");
                Date newDate = new Date(scanner.nextLong());
                task.setDate(newDate);
                break;
            //exit
            case 4:
                System.out.println("Exiting");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        
    }

     /**
     * method to copy task using clone method
     * @param task
     * @throws CloneNotSupportedException
     */
    public void copyTask(Task task) throws CloneNotSupportedException {
        Task newTask = (Task) task.clone(task);
        tasks.add(newTask);
        System.out.println("Task copied.");
    }

    /**
     * Read file for to import tasks from a txt file
     * @param filename
     */
    private Task readFile(String filename) {
        //TO-DO: Implement this method
        //Need a specific format for the file
        //task should have toString method
        return null;
    }

    private void writeFile(String filename) {
        //TO-DO: Implement this method
        //write with task toString method
    }

    public List<Task> getTasks() {
        return tasks;
    }

    
}

