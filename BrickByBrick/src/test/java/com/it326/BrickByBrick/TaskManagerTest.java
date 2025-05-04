package com.it326.BrickByBrick;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TaskManagerTest {
    
    @Test
    void testCreateTask() throws SQLException {
       TaskManager tm = new TaskManager();
        Date date = new Date();
        String name = "test Task";
        tm.createTask("test Task",date, 5, 10);
        List<Task> tasks = tm.getTasks();
        for (Task t : tasks) {
            assertEquals("test Task", t.getName());
        }
    }
}
