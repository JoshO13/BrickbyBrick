package com.it326.BrickByBrick;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

public class TaskManagerTest {
    Database db;

    @Test
    void testCreateTask() {
        TaskManager tm = new TaskManager();
        Date date = new Date();
        Task testTask = tm.createTask("test Task", 10, date, 10);
        List<Task> tasks = tm.getTasks();
        tasks.add(testTask);
        for (Task t : tasks) {
            assertEquals("test Task", t.getName());
        }
    }
}
