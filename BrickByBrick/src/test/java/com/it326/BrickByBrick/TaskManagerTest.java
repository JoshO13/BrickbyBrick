package com.it326.BrickByBrick;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TaskManagerTest {
    Database db;

    @Test
    void testCreateTask() throws SQLException {
        TaskManager tm = new TaskManager();
        Date date = new Date();
        tm.createTask("test Task", date, 10, 10);
        List<Task> tasks = tm.getTasks();
        tasks.add(tm.searchTaskName("test Task"));
        for (Task t : tasks) {
            assertEquals("test Task", t.getName());
        }
    }
}
