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
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    // data members
    private Database db;
    private List<Task> tasks = new ArrayList<Task>();
    private Project project;
    private String oldName;
    AccountManager am = new AccountManager();

    // Constructor
    public TaskManager() throws SQLException {
        db = Database.getInstance();
    }

    /**
     * Create task in database
     * 
     * @return
     */
    public boolean createTaskInDatabase(Task task, Account account) {
        // TO-DO: create a task in the database
        String username = account.getLogin();
        String sql = "INSERT INTO task (name, priority, due_date, score, username_t, project) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, task.getName());
            pstmt.setDouble(2, task.getPriorityLevel());
            pstmt.setDate(3, new java.sql.Date(task.getDate().getTime()));
            pstmt.setInt(4, task.getScore());
            pstmt.setString(5, username);
            pstmt.setString(6, null);

            // int rowsInserted = pstmt.executeUpdate();
            // return rowsInserted > 0;
            db.pushTaskQuery(pstmt);
            return true;
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
    public boolean deleteTaskInDatabase(Task task) {
        // TO-DO: delete a task in the database
        String sql = "DELETE FROM TASK WHERE name = (?)";

        try (Connection conn = db.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, task.getName());
            db.pushTaskQuery(statement);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
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
    public boolean createTask(String name, Date date, int priorityLevel, int score, Account account) {
        Task task = new Task(name, date, priorityLevel, score);
        tasks.add(task);
        createTaskInDatabase(task, account);
        System.out.println("Task created.");
        return true;
    }

    /**
     * Delete task method to delete; task passed as parameter
     * 
     * @param task
     * @return
     */
    public boolean deleteTask(String taskName) {
        boolean flag = false;
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task t = iterator.next();
            if (t.getName().equals(taskName)) {
                iterator.remove();
                deleteTaskInDatabase(t);

                flag = true;
            }

        }
        return flag;

    }

    public boolean editInDatabase(Task task) {
        String newName = task.getName();
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE tasks SET (task_name) = (?) WHERE task_name = (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setString(2, oldName);
            db.pushTaskQuery(statement);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Complete task method to complete; task passed as parameter
     * 
     * @param task
     * @return
     */
    public boolean completeTask(String taskName) {
        Task task = searchTaskName(taskName);
        int taskScore = task.getScore();

        // How to do score from here
        taskScore = task.getScore() * (task.getScore() + task.getPriorityLevel() / 10);
        task.setScore(taskScore);
        boolean success = deleteTask(taskName);
        if (success) {
            System.out.println("Task completed!!");
            return true;
        }
        System.out.println("Task could not be completed");
        return false;
    }

    /**
     * Import task from file
     * 
     * @param filename
     */
    public boolean importTask(String filename, Account account) {
        int oldSize = tasks.size();
        Task task = readFile(filename);
        createTaskInDatabase(task, account);
        return true;
        // if (success && tasks.size() > oldSize) {
        // System.out.println("Task(s) imported.");
        // } else {
        // System.out.println("No new tasks imported.");
        // }
        // return success;
    }

    /**
     * Combine two tasks into one task. Tasks must be on the same day.
     * 
     * @param task1
     * @param task2
     */
    public boolean combineTasks(Task task1, Task task2, Account acc) {
        // Precondition: Must be the same day
        if (task1.getDate().equals(task2.getDate())) {
            Task newTask = new Task(task1.getName() + " & " + task2.getName(),
                    task1.getDate(), task1.getPriorityLevel(), task1.getScore() + task2.getScore());
            tasks.remove(task1);
            tasks.remove(task2);
            tasks.add(newTask);
            deleteTask(task1.getName());
            deleteTask(task2.getName());
            createTask(newTask.getName(), newTask.getDate(), newTask.getPriorityLevel(), newTask.getScore(), acc);
            System.out.println("Tasks combined.");
            return true;
        } else
            System.out.println("Tasks are not on the same day.");
        return false;
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

    // TO-DO: Implement this method
    public boolean shareTask(Task task) {
        String filename = task.getName() + ".txt";
        boolean fileWrittenSuccessfully = writeFile(filename, task);
        if (fileWrittenSuccessfully)
            System.out.println("Task shared successfully!");
        else
            System.out.println("Task was not able to be shared");
        return fileWrittenSuccessfully;
    }

    /**
     * Edit task method to edit; task passed as parameter
     * Menu to edit task. Can edit name, priority, date, difficulty
     * 
     * @param task
     */
    public boolean editTask(Task task) {
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
        editInDatabase(task);
        scanner.close();
        return true;

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
        boolean success = editTask(task);
        if (success)
            System.out.println("Task copied.");
        else
            System.out.println("Task was not able to be copied");

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
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format to your date strings
            while ((line = reader.readLine()) != null) {
                int curlyBrace = line.indexOf('{');
                if (curlyBrace == -1)
                    continue; // Skip malformed lines

                String taskName = line.substring(0, curlyBrace).trim();
                String attributes = line.substring(curlyBrace + 1, line.indexOf('}')).trim();
                int priority = 0;
                Date date = null;
                int score = 0;

                String[] parts = attributes.split(",");
                for (String part : parts) {
                    part = part.trim();
                    if (part.startsWith("Priority Level=")) {
                        priority = Integer.parseInt(part.substring("Priority Level=".length()).trim());
                    } else if (part.startsWith("Due Date=")) {
                        String dateStr = part.substring("Due Date=".length()).trim();
                        date = sdf.parse(dateStr);
                    } else if (part.startsWith("Score=")) {
                        score = Integer.parseInt(part.substring("Score=".length()).trim());
                    }
                }

                Task task = new Task(taskName, date, priority, score);
                tasks.add(task);
                return task;
            }
            return null;

        } catch (IOException | java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean writeFile(String filename, Task task) {
        // TO-DO: Implement this method
        // write with task toString method
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(task.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

}
