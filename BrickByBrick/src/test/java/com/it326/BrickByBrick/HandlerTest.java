package com.it326.BrickByBrick;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class HandlerTest {
    
    @Test
    void testDeleteTask() {
        Handler handler = new Handler();
        Task task = new Task("Test Task", 10, new Date(), 10, 10);
        handler.deleteTask(task);
        for (Task t : handler.getTasks()) {
            assertNotEquals(task.getName(), t.getName());
        }
    }
}
