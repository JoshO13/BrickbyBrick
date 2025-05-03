package com.it326.BrickByBrick;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import oracle.jdbc.driver.parser.util.Array;

public class TaskManager {

    // data members
    private Database db;
    private List<Task> tasks = new ArrayList<Task>();

    // Constructor
    public TaskManager(Database db) throws SQLException {
        db = Database.getInstance();
    }

    /**
     * Create task in database
     * 
     * @return
     */
    public boolean createTaskInDatabase(Task task) {
        // TO-DO: create a task in the database
        String sql = "INSERT INTO tasks (taskName, priorityLevel, date, score) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getName());
            pstmt.setDouble(2, task.getPriorityLevel());
            pstmt.setDate(3, new java.sql.Date(task.getDate().getTime()));
            pstmt.setInt(4, task.getScore());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete task in the database
     * 
     * @return
     */
    Task deleteTaskInDatabase() {
        // TO-DO: delete a task in the database
        return null;
    }

    /**
     * Create task and add to list
     * 
     * @param name
     * @param priorityLevel
     * @param date
     * @param score
     * @param difficultyLevel
     */
    public Task createTask(String name, Date date, int priorityLevel, int score) {
        Task task = new Task(name, date, priorityLevel, score);
        tasks.add(task);
        System.out.println("Task created.");
        return task;
    }

    /**
     * Delete task method to delete; task passed as parameter
     * 
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
     * 
     * @param task
     * @return
     */
    public boolean completeTask(Task task) {
        int taskScore = task.getScore();
        // How to do score from here
        taskScore = task.getScore() * (task.getScore() + task.getPriorityLevel() / 10);
        task.setScore(taskScore);
        deleteTask(task);
        System.out.println("Task completed!!");
        return true;
    }

    /**
     * Import task from file
     * 
     * @param filename
     */
    public void importTask(String filename) {
        // TO-DO: Implement this method
        // txt file
        Task newTask = readFile(filename);
        tasks.add(newTask);
        System.out.println("Task imported.");
    }

    /**
     * Combine two tasks into one task. Tasks must be on the same day.
     * 
     * @param task1
     * @param task2
     */
    public void combineTasks(Task task1, Task task2) {
        // Precondition: Must be the same day
        if (task1.getDate().equals(task2.getDate())) {
            Task newTask = new Task(task1.getName() + " & " + task2.getName(),
                    task1.getDate(), task1.getPriorityLevel(), task1.getScore() + task2.getScore());
            tasks.remove(task1);
            tasks.remove(task2);
            tasks.add(newTask);
            System.out.println("Tasks combined.");
        } else
            System.out.println("Tasks are not on the same day.");
    }

    /**
     * Search task by name in task list
     * 
     * @param name
     * @return
     */
    public Task searchTaskName(String name) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getName().equals(name)) {
                return tasks.get(i);
            }
        }
        System.out.println("Task not found.");
        return null;
    }

    /**
     * Filter task by date
     * 
     * @param date
     * @return
     */
    public List<Task> filterByDate(Date date) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDate().equals(date)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Task> filterByPriority(int priority) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getPriorityLevel() == priority) {
                result.add(t);
            }
        }
        return result;
    }

    public void shareTask(Task task) {
        // TO-DO: Implement this method
        String filename = task.getName() + ".txt";
        writeFile(filename);
    }

    /**
     * Edit task method to edit; task passed as parameter
     * Menu to edit task. Can edit name, priority, date, difficulty
     * 
     * @param task
     */
    public void editTask(Task task) {
        System.out.println("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Priority");
        System.out.println("3. Date");
        System.out.println("4. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            // edit name
            case 1:
                System.out.println("Enter new name: ");
                String newName = scanner.next();
                task.setTaskName(newName);
                break;
            // edit priority
            case 2:
                System.out.println("Enter new priority: ");
                int newPriority = scanner.nextInt();
                task.setPriorityLevel(newPriority);
                break;
            // edit date
            case 3:
                System.out.println("Enter new date: ");
                Date newDate = new Date(scanner.nextLong());
                task.setDate(newDate);
                break;
            // exit
            case 4:
                System.out.println("Exiting");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
        scanner.close();

    }

    /**
     * method to copy task using clone method
     * 
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
     * 
     * @param filename
     */
    private Task readFile(String filename) {
        // TO-DO: Implement this method
        // Need a specific format for the file
        // task should have toString method
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String taskName;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format to your date strings
            while ((taskName = reader.readLine()) != null) {
                String dateLine = reader.readLine();
                String priorityLine = reader.readLine();
                String scoreLine = reader.readLine();
                if (dateLine == null || priorityLine == null || scoreLine == null) {
                    break; // Incomplete task entry
                }
                Date date = sdf.parse(dateLine);
                int priority = Integer.parseInt(priorityLine);
                int score = Integer.parseInt(scoreLine);

                // Assuming default difficultyLevel as 0, adjust if needed
                Task task = new Task(taskName, date, priority, score);
                tasks.add(task);
            }
        } catch (IOException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeFile(String filename) {
        // TO-DO: Implement this method
        // write with task toString method
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine(); // Add a blank line between tasks (optional)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

}
