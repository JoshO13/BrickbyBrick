package com.it326.BrickByBrick;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

public class ProjectManagerTest {

    @Test
    void testDeleteProject() throws SQLException {
        ProjectManager pm = new ProjectManager();
        Account acc = new Account("testUser", "testPass");
        pm.createProject("testProject", acc);
        List<Project> before = pm.getAllProjects();
        assertEquals(1, before.size(), "Should have one project before deletion");
        assertEquals("testProject", before.get(0).getName());
        boolean deleted = pm.deleteProject("testProject");
        assertTrue(deleted, "deleteProject should return true for existing project");
        List<Project> after = pm.getAllProjects();
        assertTrue(after.isEmpty(), "Project list should be empty after deletion");
    }


    @Test
    void testCreateProject() throws SQLException 
    {
        ProjectManager pm = new ProjectManager();
        Account acc = new Account("testUser", "testPass");

        List<Project> before = pm.getAllProjects();
        assertTrue(before.isEmpty(), "Project list should be empty before creation");

        boolean created = pm.createProject("testProject", acc);
        assertTrue(created, "createProject should return true");

        List<Project> after = pm.getAllProjects();
        assertEquals(1, after.size(), "Project list should have one project after creation");
        assertEquals("testProject", after.get(0).getName(), "Created project name should match");
}


}