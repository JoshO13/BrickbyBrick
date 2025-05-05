package com.it326.BrickByBrick;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteTaskTest {

    private TaskManager taskManager;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() throws SQLException {
        taskManager = new TaskManager();
        task1 = new Task("Task 1", new Date(), 1, 10);
        task2 = new Task("Task 2", new Date(), 2, 20);
        taskManager.getTasks().add(task1);
        taskManager.getTasks().add(task2);
    }

    @Test
    void testDeleteTaskSuccess() {
        assertTrue(taskManager.deleteTask(task1.getName()));
        assertFalse(taskManager.getTasks().contains(task1));
        assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    void testDeleteTaskNotFound() {
        Task nonExistentTask = new Task("Non-existent", new Date(), 3, 30);
        assertFalse(taskManager.deleteTask(nonExistentTask.getName()));
        assertEquals(2, taskManager.getTasks().size());
    }
}
