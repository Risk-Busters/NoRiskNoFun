package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectActivityOverTimeTest {

    private ProjectActivityOverTime projectActivityOverTime;

    @BeforeEach
    void setUp() {
        projectActivityOverTime = new ProjectActivityOverTime();
    }

    @Test
    void testConstrucctorWithParams() {
        Project project = new Project();
        project.setId(1L);
        CustomDate date = new CustomDate();
        ProjectActivityOverTime activity = new ProjectActivityOverTime(project, 2.0, date);
        assertEquals(Long.valueOf(1L), activity.getProject().getId());
        assertEquals(Double.valueOf(2.0), activity.getProjectActivityScoreAtThisDay());
        assertEquals(date, activity.getDate());
    }

    @Test
    void testGetSerialVersionUID() {
        assertEquals(1L, ProjectActivityOverTime.getSerialVersionUID());
    }

    @Test
    void testSetAndGetId() {
        projectActivityOverTime.setId(2L);
        assertEquals(Long.valueOf(2L), projectActivityOverTime.getId());
    }

    @Test
    void testSetAndGetProject() {
        Project project = new Project();
        projectActivityOverTime.setProject(project);
        assertEquals(project, projectActivityOverTime.getProject());
    }

    @Test
    void testSetAndGetDate() {
        CustomDate date = new CustomDate();
        projectActivityOverTime.setDate(date);
        assertEquals(date, projectActivityOverTime.getDate());
    }

    @Test
    void testSetAndGetProjectActivityScoreAtThisDay() {
        projectActivityOverTime.setProjectActivityScoreAtThisDay(42.0);
        assertEquals(Double.valueOf(42.0), projectActivityOverTime.getProjectActivityScoreAtThisDay());
    }

    @Test
    void testToString() {
        Project project = new Project();
        project.setId(1L);
        CustomDate date = new CustomDate();
        ProjectActivityOverTime activity = new ProjectActivityOverTime(project, 2.0, date);
        activity.setId(3L);

        String expected = "ProjectActivity{id=3, projectId=1, date=2020-03-21, activityAtThisDay=2.0}";
        assertEquals(expected, activity.toString());
    }
}
