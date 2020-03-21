package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.PointWithDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectActivityDTOTest {

    private ProjectActivityDTO projectActivityDTO;
    private List<PointWithDate> pointsWithDate;
    private PointWithDate point1;

    @BeforeEach
    void setUp() {
        point1 = new PointWithDate(3.0, new CustomDate());
        pointsWithDate = List.of(point1);
        projectActivityDTO = new ProjectActivityDTO(2.0, pointsWithDate);

    }

    @Test
    void testConstructor() {

        assertEquals(Double.valueOf(2.0), projectActivityDTO.getProjectActivityBasedOnUserScore());
        assertArrayEquals(pointsWithDate.toArray(), projectActivityDTO.getProjectActivitiesOverTime());
    }

    @Test
    void testSetAndGetProjectActivityBasedOnUserScore() {
        projectActivityDTO.setProjectActivityBasedOnUserScore(22.0);
        assertEquals(Double.valueOf(22.0), projectActivityDTO.getProjectActivityBasedOnUserScore());
    }

    @Test
    void setAndGetProjectActivitiesOverTime() {
        PointWithDate[] pointsWithDateArray = new PointWithDate[1];
        pointsWithDateArray[0] = point1;
        projectActivityDTO.setProjectActivitiesOverTime(pointsWithDateArray);
        assertArrayEquals(pointsWithDateArray, projectActivityDTO.getProjectActivitiesOverTime());
    }

    @Test
    void testToString() {
        PointWithDate[] pointsWithDateArray = new PointWithDate[1];
        pointsWithDateArray[0] = point1;
        projectActivityDTO.setProjectActivitiesOverTime(pointsWithDateArray);
        projectActivityDTO.setProjectActivityBasedOnUserScore(22.0);

        String expected = "ProjectActivityDTO{projectActivityToday=22.0, projectActivitiesOverTime=[PointWithDate{date='2020-03-21, pointsScore=3.0'}]}";
        assertEquals(expected, projectActivityDTO.toString());
    }
}
